package com.example.demo.LSP;

import java.io.File;
import java.util.List;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.bean.Constraint;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileOperation;
import com.example.demo.service.FileService;
import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TimeTree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

public abstract class LSPObserver {

	@Autowired // 自动装配
	FileService fileService = new FileService();
	ASTService astService = new ASTService();
	FileOperation fileOperation = new FileOperation();

	protected Session session;
	protected String editorType;
	protected String uri;

	protected String lastestProjectAddress;
	protected String rootAddress;
	protected String orgPath;
	protected String oldPath;
	protected String locPath;
	protected String mergePath;

	protected TreeContext orgContext;
	protected TreeContext oldContext;
	protected TreeContext locContext;

	protected ITree orgTree;
	protected ITree oldTree;
	protected ITree locTree;

	// ==========================初始化====================
	public LSPObserver(Session session, String editorType) {
		super();
		this.session = session;
		this.editorType = editorType;
	}

	public LSPObserver(Session session, String uri, String editorType) {
		super();
		this.session = session;
		this.uri = uri;
		this.editorType = editorType;
		lastestProjectAddress = AddressService.lastestProjectAddress;
		rootAddress = lastestProjectAddress + uri + "/" + session.getId() + "/";
	}

	public Session getSession() {
		return this.session;
	}

	// if not first observer,setTree will be called
	public void setTree(ITree org, ITree old) {
		orgTree = org;
		oldTree = old;
	}

	public void generateOrgAST(long time) {
		// 生成loc.xml
		saveLastestData();

		// 将loc.xml重命名为 orgin.xml
		Rename(locPath, orgPath);

		// 生成 AST
		orgContext = generateAST(orgPath);
		orgTree = orgContext.getRoot();
		orgTree = addTime((Tree) orgTree, time);
		orgContext.setRoot(orgTree);
	}

	public void generateOldAST(long time) {
		// 生成loc.xml
		saveLastestData();

		// 将loc.xml重命名为 orgin.xml
		Rename(locPath, oldPath);

		// 生成 AST
		oldContext = generateAST(oldPath);
		oldTree = oldContext.getRoot();
		oldTree = addTime((Tree) oldTree, time);
		oldContext.setRoot(oldTree);
	}

	public void generateLocAST(long time) {
		// 生成loc.xml
		saveLastestData();

		// 生成 AST
		locContext = generateAST(locPath);
		locTree = locContext.getRoot();
		locTree = addTime((Tree) locTree, time);
		locContext.setRoot(locTree);
	}

	protected abstract void saveLastestData();

	public static void Rename(String oldPath, String newPath) {
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		if (newFile.exists())
			newFile.delete();
		oldFile.renameTo(newFile);
	}

	public abstract TreeContext generateAST(String filePath);

	public abstract void notifyClient();

	// ==============change==============

	public abstract void change(String message);

	// change old AST and set time
	public void changeOldAST() {

		// org diff old for delete
		Matcher matcher_org_old = Matchers.getInstance().getMatcher(orgTree, oldTree);
		matcher_org_old.match();
		MappingStore mappings_org_old = matcher_org_old.getMappings();

		// generate loc tree
		long time = System.currentTimeMillis();
		generateLocAST(time);

		// old diff loc for add,move,update
		Matcher matcher_old_loc = Matchers.getInstance().getMatcher(oldTree, locTree);
		matcher_old_loc.match();
		MappingStore mappings_old_loc = matcher_old_loc.getMappings();
		ActionGenerator actionGenerator = new ActionGenerator(oldTree, locTree, mappings_old_loc);
		actionGenerator.generate();
		List<Action> actions_old_loc = actionGenerator.getActions();

//		System.out.println("===================actions_old_loc=================");
//		for(Action action:actions_old_loc)
//			System.out.println(action.toString());
//		System.out.println("==================end actions_old_loc=================");
//		TreeDifferences app = new TreeDifferences(oldContext, locContext, actions_old_loc, mappings_old_loc);
//		app.setVisible(true);

		// 构建time-AST
		oldTree = generateTimeAST(orgTree, oldTree, locTree, mappings_org_old, mappings_old_loc, actions_old_loc);
		oldContext.setRoot(oldTree);
	}

	// add time for locTime
	public ITree addTime(Tree tree, long time) {
		return TimeTree.deepCopy(tree, time);
	}

	// change time of loc tree to get new oldTree
	public ITree generateTimeAST(ITree org, ITree old, ITree loc, MappingStore map_org_old, MappingStore map_old_loc,
			List<Action> action_old_loc) {
		// set time of orgTree
		for (Action action : action_old_loc) {
			if (action instanceof Delete) {
				ITree orgNode = map_org_old.getSrc(action.getNode());
				if (orgNode != null) {
					long time = ((TimeTree) loc).getTime();
					((TimeTree) orgNode).setTime(time);
				}
			}
		}
		// set time for locTime
		for (ITree t : loc.getTrees()) {
			boolean inActions = false;
			for (Action action : action_old_loc) {
				if (action instanceof Insert || action instanceof Move || action instanceof Update) {
					ITree oldNode = action.getNode();
					ITree dstNode = map_old_loc.getDst(oldNode);
					if (t == dstNode) {
						inActions = true;
						break;
					}
				}

			}
			// if not in actions, set loc tree's time equal old tree's
			if (!inActions) {
				ITree oldNode = map_old_loc.getSrc(t);
				if (oldTree != null) {
					long time = ((TimeTree) old).getTime();
					((TimeTree) t).setTime(time);
				}
			}
		}
		return loc.deepCopy();
	}

	// ==============================================merge====================================
	public void update(String type, ITree org, ITree old, Session session) {
		if (session.equals(this.session)) {
			System.out.println(session + "==" + this.session);
			return;
		}

		System.out.println(session + "!=" + this.session);
		update(type, org, old);
	}

	public abstract void update(String type, ITree org, ITree old);

	// 根据远端传来的AST修改本地AST
	public void update(ITree rem_orgTree, ITree rem_oldTree) {
		// delta1 locOrg locOld
		Matcher matcher_locOrg_locOld = Matchers.getInstance().getMatcher(orgTree, oldTree);
		matcher_locOrg_locOld.match();
		MappingStore mappings_locOrg_locOld = matcher_locOrg_locOld.getMappings();
		ActionGenerator actionGenerator_locOrg_locOld = new ActionGenerator(orgTree, oldTree, mappings_locOrg_locOld);
		actionGenerator_locOrg_locOld.generate();
		List<Action> actions_locOrg_locOld = actionGenerator_locOrg_locOld.getActions();
//		List<Action> actionsmerge = new ArrayList<Action>(actions_locOrg_locOld);
//		TreeDifferences app1 = new TreeDifferences("locOrg locOld", orgContext, oldContext, 
//				actions_locOrg_locOld, mappings_locOrg_locOld);
//		app1.setVisible(true);

		// delta2 locOrg remOld
		Matcher matcher2 = Matchers.getInstance().getMatcher(orgTree, rem_oldTree);
		matcher2.match();
		MappingStore mappings_locOrg_remOld = matcher2.getMappings();
		ActionGenerator actionGenerator2 = new ActionGenerator(orgTree, rem_oldTree, mappings_locOrg_remOld);
		actionGenerator2.generate();
		List<Action> actions_locOrg_remOld = actionGenerator2.getActions();
		TreeContext remOldContext = new TreeContext();
		remOldContext.setRoot(rem_oldTree);
//		TreeDifferences app2 = new TreeDifferences("locOrg remOld",orgContext, remOldContext, 
//				actions_locOrg_remOld, mappings_locOrg_remOld);
//		app2.setVisible(true);

		// delta3 locOrg remOrg
		Matcher matcher3 = Matchers.getInstance().getMatcher(orgTree, rem_orgTree);
		matcher3.match();
		MappingStore mappings_locOrg_remOrg = matcher3.getMappings();
		ActionGenerator actionGenerator3 = new ActionGenerator(orgTree, rem_orgTree, mappings_locOrg_remOrg);
		actionGenerator3.generate();
		List<Action> actions_locOrg_remOrg = actionGenerator3.getActions();

		TreeContext remOrgContext = new TreeContext();
		remOrgContext.setRoot(rem_orgTree);
//		TreeDifferences app3 = new TreeDifferences("locOrg remOrg",orgContext, remOrgContext, 
//				actions_locOrg_remOrg, mappings_locOrg_remOrg);
//		app3.setVisible(true);

		boolean isChange = false;
		boolean conflict = false;
		for (Action action_locOrg_remOld : actions_locOrg_remOld) {
			// get rem time
			long time_rem = 0;
			if (action_locOrg_remOld instanceof Delete) {
				TimeTree src = (TimeTree) action_locOrg_remOld.getNode();
				TimeTree rem_src = (TimeTree) mappings_locOrg_remOrg.getDst(src);
				if (rem_src != null)
					time_rem = rem_src.getTime();
				else
					time_rem = src.getTime();
			} else if (action_locOrg_remOld instanceof Insert) {
				time_rem = ((TimeTree) action_locOrg_remOld.getNode()).getTime();
			} else {// Update Move
				System.out.println("action_locOrg_remOld" + action_locOrg_remOld.toString());
				TimeTree src = (TimeTree) action_locOrg_remOld.getNode();
				TimeTree dst = (TimeTree) mappings_locOrg_remOld.getDst(src);
				if (dst != null)
					time_rem = dst.getTime();
			}

			for (Action action_locOrg_locOld : actions_locOrg_locOld) {

				// conflict ( delete, move, update )
				if (action_locOrg_locOld.getNode() == action_locOrg_remOld.getNode()) {
					long time_loc = 0;

					if (action_locOrg_locOld instanceof Delete) {
						TimeTree src = (TimeTree) action_locOrg_locOld.getNode();
						time_loc = src.getTime();
					} else if (action_locOrg_locOld instanceof Insert) {
						time_loc = ((TimeTree) action_locOrg_locOld.getNode()).getTime();
					} else {// Update Move
						TimeTree src = (TimeTree) action_locOrg_locOld.getNode();
						TimeTree dst = (TimeTree) mappings_locOrg_locOld.getDst(src);
						time_loc = dst.getTime();
					}

					// rem change is new,ignore action1(loc), and set rem action2
					if (time_loc < time_rem) {
//			    		System.out.println("rem change is new, locNode="+action_locOrg_locOld.getNode().toShortString()+
//			    				", remNode="+action_locOrg_remOld.getNode().toShortString());
//		    			System.out.println(time_loc + "<" +time_rem);
//			    		System.out.println("rem change is new,\n\t locAction="+
//			    				action_locOrg_locOld.toString()+
//			    				",\n\t remAction="+action_locOrg_remOld.toString());

//						actionsmerge.remove(action_locOrg_locOld);
//						actionsmerge.add(action_locOrg_remOld);

						// change old ast
						resetAST(action_locOrg_locOld, mappings_locOrg_locOld);
						changeAST(actions_locOrg_remOld, action_locOrg_remOld, mappings_locOrg_locOld,
								mappings_locOrg_remOrg);
						isChange = true;
					}
//					else if (time_loc > time_rem) {
//						System.out.println("loc change is new,\n\t locAction=" + action_locOrg_locOld.toString()
//								+ ",\n\t remAction=" + action_locOrg_remOld.toString());
//					}
					conflict = true;
					break;
				}
			}

			if (!conflict) {// no conflict
//	    		System.out.println("no conflict, remNode="+action_locOrg_remOld.getNode().toShortString()+", "+action_locOrg_remOld.getName());
//	    		System.out.println("no conflict, remAction="+ action_locOrg_remOld.toString());
//				actionsmerge.add(action_locOrg_remOld);
				changeAST(actions_locOrg_remOld, action_locOrg_remOld, mappings_locOrg_locOld, mappings_locOrg_remOrg);
				isChange = true;
			}
			conflict = false;
		}
		// reset id
		if (isChange) {
			ASTService.resetId(oldTree, 0);
			oldContext.setRoot(oldTree);
		}

		// make org = old
		orgTree = oldTree.deepCopy();
		orgContext.setRoot(orgTree);

	}

	// 若loc修改与rem修改有冲突，且rem修改较新，则删除loc修改
	public void resetAST(Action action, MappingStore mappings) {
		if (action instanceof Insert) {
			// remove node and its children
			TimeTree dst = (TimeTree) action.getNode();
			ITree dstParent = dst.getParent();
			dstParent.getChildren().remove(dst);

		} else if (action instanceof Delete) {
			// add node
			TimeTree src = (TimeTree) action.getNode();
			ITree srcParent = src.getParent();
			ITree dstParent = mappings.getDst(srcParent);
			ITree dst = new TimeTree(src);
			dst.setParent(dstParent);
			dstParent.addChild(dst);

		} else if (action instanceof Move) {
			// move node
			ITree src = action.getNode();
			ITree srcParent = src.getParent();

			ITree dst = mappings.getDst(src);
			ITree dstParent = dst.getParent();
			ITree oldParent = mappings.getDst(srcParent);

			dstParent.getChildren().remove(dst);
			oldParent.getChildren().add(dst);
			dst.setParent(oldParent);

		} else if (action instanceof Update) {
			// Update node
			ITree src = action.getNode();
			ITree dst = mappings.getDst(src);
			String label = src.getLabel();
			dst.setLabel(label);
		}
	}

	// 根据actions修改oldTree,使其变为merge版本
	public void changeAST(List<Action> actions_locOrg_remOld, Action action_locOrg_remOld,
			MappingStore map_locOrg_locOld, MappingStore map_locOrg_remOrg) {
//		System.out.println("changeAST");
		// 根据远程action 修改本地 old
		if (action_locOrg_remOld instanceof Insert) {
			ITree x = action_locOrg_remOld.getNode();
			ITree z = ((Insert) action_locOrg_remOld).getParent();

			// if parent in actions and is a insert ,ignore
			boolean isFind = false;
			for (Action act : actions_locOrg_remOld) {
				if (act.getNode() == x.getParent()) {
					if (act instanceof Insert) {
						isFind = true;
						break;
					} else if (act instanceof Delete) {
						System.out.println("!!!!!!!!!!!!");
					}
				}
			}
			if (isFind) {
				return;
			}
			for (ITree t : x.getTrees()) {
				for (Action act : actions_locOrg_remOld) {
					if (act.getNode() == t && act instanceof Insert) {
						isFind = true;
						break;
					}
				}
				if (!isFind) {
					System.out.println("插入节点的后代节点不是插入节点!!!!!!!!!!!");
				}
			}
			ITree w = (TimeTree) x.deepCopy();
			ITree z1 = map_locOrg_locOld.getDst(z);
			int k = ((Insert) action_locOrg_remOld).getPosition();
			if (z1.getChildren().size() > k)
				z1.insertChild(w, k);
			else
				z1.addChild(w);
			w.setParent(z1);

//
//	    		  System.out.println("=======insert=====");
//		    	  for(ITree tree : w.getTrees())
//		    		  System.out.println(tree.toShortString());
//	    		  System.out.println("=======insert end =====");
		} else if (action_locOrg_remOld instanceof Update) {
			ITree w = action_locOrg_remOld.getNode();
			ITree w1 = map_locOrg_locOld.getDst(w);
			String label = ((Update) action_locOrg_remOld).getValue();
			System.out.println("\t" + w1.toShortString() + "\tsetLabel \t" + label);
			w1.setLabel(label);
		} else if (action_locOrg_remOld instanceof Move) {
			ITree w = action_locOrg_remOld.getNode();
			ITree z = ((Move) action_locOrg_remOld).getParent();

			ITree w1 = map_locOrg_locOld.getDst(w);
			ITree z1 = map_locOrg_locOld.getDst(z);
			ITree v1 = w1.getParent();
			if (v1 != null)
				v1.getChildren().remove(w1);
			int k = ((Move) action_locOrg_remOld).getPosition();
			if (z1.getChildren().size() > k)
				z1.insertChild(w1, k);
			else
				z1.getChildren().add(w1);
			w1.setParent(z1);
		} else if (action_locOrg_remOld instanceof Delete) {
			TimeTree w = (TimeTree) action_locOrg_remOld.getNode();
			ITree w1 = map_locOrg_locOld.getDst(w);
			if (w1 != null) {// in case of w1 is a root node
				ITree v1 = w1.getParent();
				v1.getChildren().remove(w1);
			}

			// set newTime in orgAST
			TimeTree w2 = (TimeTree) map_locOrg_remOrg.getDst(w);
			if (w2 != null) {
				long time = w2.getTime();
				w.setTime(time);
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public abstract void setProject(ProblemDiagram problemDiagram);

	public abstract void setReferenceList(List<Reference> referenceList);

	public abstract void setConstraintList(List<Constraint> constraintList);

	public abstract void setRequirementList(List<Requirement> requirementList);

	public void deleteFile() {
		System.out.println("delete file " + rootAddress + " .....");
		fileOperation.DeleteFolder(rootAddress);
	}

}