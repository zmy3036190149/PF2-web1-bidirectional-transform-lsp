package com.example.demo.LSP;

import java.io.File;
import java.util.List;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.bean.Constraint;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.Project;
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

	protected Project project = null;
	protected String pf = null;

	protected String lastestProjectAddress;
	protected String rootAddress;

	// ===============old================
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

	// ===============text================
	protected String text_orgPath;
	protected String text_oldPath;
	protected String text_locPath;
	protected String text_mergePath;

	protected TreeContext text_orgContext;
	protected TreeContext text_oldContext;
	protected TreeContext text_locContext;

	protected ITree text_orgTree;
	protected ITree text_oldTree;
	protected ITree text_locTree;

	// ===============diagram================
	protected String diagram_orgPath;
	protected String diagram_oldPath;
	protected String diagram_locPath;
	protected String diagram_mergePath;

	protected TreeContext diagram_orgContext;
	protected TreeContext diagram_oldContext;
	protected TreeContext diagram_locContext;

	protected ITree diagram_orgTree;
	protected ITree diagram_oldTree;
	protected ITree diagram_locTree;

	protected boolean hasSpace = false;

	// ==========================初始化====================
	@Deprecated
	public LSPObserver(Session session, String editorType) {
		super();
		this.session = session;
		this.editorType = editorType;
	}

	@Deprecated
	public LSPObserver(Session session, String uri, String editorType) {
		super();
		this.session = session;
		this.uri = uri;
		this.editorType = editorType;
		lastestProjectAddress = AddressService.lastestProjectAddress;
		rootAddress = lastestProjectAddress + uri + "/" + session.getId() + "/";
	}

	public LSPObserver(Session session, String uri, String editorType, Project project, String pf) {
		super();
		this.session = session;
		this.uri = uri;
		this.editorType = editorType;
		this.project = project;
		this.pf = pf;
		lastestProjectAddress = AddressService.lastestProjectAddress;
		rootAddress = lastestProjectAddress + uri + "/" + session.getId() + "/";

		text_orgPath = rootAddress + "org.pf";
		text_oldPath = rootAddress + "old.pf";
		text_locPath = rootAddress + "loc.pf";
		text_mergePath = rootAddress + "merge.pf";

		diagram_orgPath = rootAddress + "org.xml";// 原始版
		diagram_locPath = rootAddress + "loc.xml";// 客户端传来的最新版
		diagram_oldPath = rootAddress + "old.xml";// 合成的最新版
		diagram_mergePath = rootAddress + "merge.xml";// 合并版

	}

	public Session getSession() {
		return this.session;
	}

	// -----------------------old---------------------------
	@Deprecated
	// if not first observer,setTree will be called
	public void setTree(ITree org, ITree old) {
		orgTree = org;
		oldTree = old;
	}

	// save lastest data and generate org AST with time
	@Deprecated
	public void generateOrgAST(long time) {
		// 生成loc.*
		saveLastestData();

		// 将loc.xml重命名为 orgin.*
		Rename(locPath, orgPath);

		// 生成 AST
		orgContext = generateAST(orgPath);
		orgTree = orgContext.getRoot();
		orgTree = ASTService.addTime((Tree) orgTree, time);
		orgContext.setRoot(orgTree);
	}

	@Deprecated
	public void generateOldAST(long time) {
		// 生成loc.xml
		saveLastestData();

		// 将loc.xml重命名为 orgin.xml
		Rename(locPath, oldPath);

		// 生成 AST
		oldContext = generateAST(oldPath);
		oldTree = oldContext.getRoot();
		oldTree = ASTService.addTime((Tree) oldTree, time);
		oldContext.setRoot(oldTree);
	}

	@Deprecated
	public void generateLocAST(long time) {
		// 生成loc.xml
		saveLastestData();

		// 生成 AST
		locContext = generateAST(locPath);
		locTree = locContext.getRoot();
		locTree = ASTService.addTime((Tree) locTree, time);
		locContext.setRoot(locTree);
	}

	@Deprecated
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

	@Deprecated
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

	// -----------new : two kind of AST-----------------
	// if not first observer,setTree will be called

	// -----------------------new---------------------------
	public void setTree(ITree text_org, ITree text_old, ITree diagram_org, ITree diagram_old) {
		text_orgTree = text_org;
		text_oldTree = text_old;
		diagram_orgTree = diagram_org;
		diagram_oldTree = diagram_old;
		// generate new Project and Pf
		pf = ASTService.getPf(text_old);
		ASTService.generateXmlFile(diagram_old, diagram_oldPath);
		project = ASTService.getProject(diagram_oldPath);
	}

	public boolean generateTextOrgAST(long time) {
		saveLastestPf();
		Rename(text_locPath, text_orgPath);
		text_orgContext = ASTService.generateNoErrorPfAST(text_orgPath);
		if (text_orgContext == null)
			return false;
		text_orgTree = text_orgContext.getRoot();
		if (text_orgTree == null)
			return false;
		text_orgTree = ASTService.addTime((Tree) text_orgTree, time);
		text_orgContext.setRoot(text_orgTree);
		return true;
	}

	public boolean generateTextOldAST(long time) {
		saveLastestPf();
		Rename(text_locPath, text_oldPath);
		text_oldContext = ASTService.generateNoErrorPfAST(text_oldPath);
		if (text_oldContext == null)
			return false;
		text_oldTree = text_oldContext.getRoot();
		text_oldTree = ASTService.addTime((Tree) text_oldTree, time);
		text_oldContext.setRoot(text_oldTree);
		return true;
	}

	public boolean generateTextLocAST(long time) {
		saveLastestPf();
		TreeContext tc = ASTService.generateNoErrorPfAST(text_locPath);
		if (tc == null)
			return false;
		text_locContext = tc;
		text_locTree = text_locContext.getRoot();
		text_locTree = ASTService.addTime((Tree) text_locTree, time);
		text_locContext.setRoot(text_locTree);
		return true;
	}

	public void generateDiagramOrgAST(long time) {
		saveLastestProject();
		Rename(diagram_locPath, diagram_orgPath);
		diagram_orgContext = ASTService.generateXmlAST(diagram_orgPath);
		diagram_orgTree = diagram_orgContext.getRoot();
		diagram_orgTree = ASTService.addTime((Tree) diagram_orgTree, time);
		diagram_orgContext.setRoot(diagram_orgTree);
	}

	public void generateDiagramOldAST(long time) {
		saveLastestProject();
		Rename(diagram_locPath, diagram_oldPath);
		diagram_oldContext = ASTService.generateXmlAST(diagram_oldPath);
		diagram_oldTree = diagram_oldContext.getRoot();
		diagram_oldTree = ASTService.addTime((Tree) diagram_oldTree, time);
		diagram_oldContext.setRoot(diagram_oldTree);
	}

	public void generateDiagramLocAST(long time) {
		saveLastestProject();
		diagram_locContext = ASTService.generateXmlAST(diagram_locPath);
		diagram_locTree = diagram_locContext.getRoot();
		diagram_locTree = ASTService.addTime((Tree) diagram_locTree, time);
		diagram_locContext.setRoot(diagram_locTree);
	}

	// save lastestProject to loc.xml
	public void saveLastestProject() {
		File dir = new File(rootAddress);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		LSPFileService.saveLastestProject(rootAddress, uri, project);
	}

	// save lastestProject to loc.pf
	public void saveLastestPf() {
		FileOperation.writeToFile(pf, rootAddress, text_locPath);
	}

	// ==============change==============
	public abstract void change_old(String message);

	public abstract void change(String message);

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

	public ITree changeOldAST(ITree orgTree, ITree oldTree, ITree locTree) {
		// org diff old for delete
		Matcher matcher_org_old = Matchers.getInstance().getMatcher(orgTree, oldTree);
		matcher_org_old.match();
		MappingStore mappings_org_old = matcher_org_old.getMappings();

		// old diff loc for add,move,update
		Matcher matcher_old_loc = Matchers.getInstance().getMatcher(oldTree, locTree);
		matcher_old_loc.match();
		MappingStore mappings_old_loc = matcher_old_loc.getMappings();
		ActionGenerator actionGenerator = new ActionGenerator(oldTree, locTree, mappings_old_loc);
		actionGenerator.generate();
		List<Action> actions_old_loc = actionGenerator.getActions();

		// 判断变化的内容是否有空格，回车键
		hasSpace = false;
		for (Action action_old_loc : actions_old_loc) {
			String label = action_old_loc.getNode().getLabel();
			if (label.contains(" ") || label.contains("\n")) {
				hasSpace = true;
				break;
			}
		}
		// 构建time-AST
		return generateTimeAST(orgTree, oldTree, locTree, mappings_org_old, mappings_old_loc, actions_old_loc);

	}

	// ==============================================merge====================================
	// ----------------- old -----------------
//	public void update(String type, ITree org, ITree old, Session session) {
//		if (session.equals(this.session)) {
//			System.out.println(session + "==" + this.session);
//			return;
//		}
//
//		System.out.println(session + "!=" + this.session);
//		update(type, org, old);
//	}

//	public abstract void update(String type, ITree org, ITree old);

	// ---------------- new ------------------
	public void update(ITree text_org, ITree text_old, ITree diagram_org, ITree diagram_old, Session session) {
		if (session.equals(this.session)) {
			// System.out.println(session + "==" + this.session);
			return;
		}
		// System.out.println(session + "!=" + this.session);
		update(text_org, text_old, diagram_org, diagram_old);
	}

	public void update(ITree text_org, ITree text_old, ITree diagram_org, ITree diagram_old) {
		updateText(text_org, text_old);
		updateDiagram(diagram_org, diagram_old);
		notifyClient();
	}

	// 更新文本， 生成merge.pf,及新的pf
	public void updateText(ITree rem_org, ITree rem_old) {
		System.out.println(session.getId() + " update text old ast since another editor change===================");

		// update text_orgTree and text_oldTree
		updateTree(rem_org, rem_old, text_orgContext, text_oldContext);

		// generate merge.pf
		ASTService.generatePfFile(text_oldTree, rootAddress, text_mergePath);

		// 生成新的pf
		pf = ASTService.getPf(text_oldTree);

		System.out.println(" update text old ast end ========================================");
	}

	// 更新图形，生成merge.xml,及新的project
	public void updateDiagram(ITree rem_org, ITree rem_old) {
		System.out.println(session.getId() + " update old ast since another editor change===================");

		// update diagram_oldTree
		updateTree(rem_org, rem_old, diagram_orgContext, diagram_oldContext);

		// generate merge.xml
		astService.generateXmlFile(diagram_oldTree, diagram_mergePath);

		// 生成新的project
		project = astService.getProject(diagram_mergePath);

		System.out.println(" update end ========================================");
	}

	// 根据远端传来的orgAST和oldAST,修改本地AST
	public void updateTree(ITree rem_orgTree, ITree rem_oldTree, TreeContext orgContext, TreeContext oldContext) {
		ITree orgTree;
		ITree oldTree;
		if (orgContext == null) {
			orgTree = rem_orgTree.deepCopy();
			orgContext = new TreeContext();
			orgContext.setRoot(orgTree);
			oldTree = rem_oldTree.deepCopy();
			oldContext = new TreeContext();
			oldContext.setRoot(orgTree);
		} else {
			orgTree = orgContext.getRoot();
			oldTree = oldContext.getRoot();
		}

		// delta1 locOrg locOld
		Matcher matcher_locOrg_locOld = Matchers.getInstance().getMatcher(orgTree, oldTree);
		matcher_locOrg_locOld.match();
		MappingStore mappings_locOrg_locOld = matcher_locOrg_locOld.getMappings();
		ActionGenerator actionGenerator_locOrg_locOld = new ActionGenerator(orgTree, oldTree, mappings_locOrg_locOld);
		actionGenerator_locOrg_locOld.generate();
		List<Action> actions_locOrg_locOld = actionGenerator_locOrg_locOld.getActions();
//		TreeDifferences app1 = new TreeDifferences("locOrg locOld", orgContext, oldContext, actions_locOrg_locOld,
//				mappings_locOrg_locOld);
//		if (orgContext == text_orgContext)
//			app1.setVisible(true);

		// delta2 locOrg remOld
		Matcher matcher2 = Matchers.getInstance().getMatcher(orgTree, rem_oldTree);
		matcher2.match();
		MappingStore mappings_locOrg_remOld = matcher2.getMappings();
		ActionGenerator actionGenerator2 = new ActionGenerator(orgTree, rem_oldTree, mappings_locOrg_remOld);
		actionGenerator2.generate();
		List<Action> actions_locOrg_remOld = actionGenerator2.getActions();
		for (Action action : actions_locOrg_remOld) {
			System.out.println(action.toString());
		}
		TreeContext remOldContext = new TreeContext();
		remOldContext.setRoot(rem_oldTree);
//		TreeDifferences app2 = new TreeDifferences("locOrg remOld", orgContext, remOldContext, actions_locOrg_remOld,
//				mappings_locOrg_remOld);
//		if (orgContext == text_orgContext)
//			app2.setVisible(true);

		// delta3 locOrg remOrg
		Matcher matcher3 = Matchers.getInstance().getMatcher(orgTree, rem_orgTree);
		matcher3.match();
		MappingStore mappings_locOrg_remOrg = matcher3.getMappings();
		ActionGenerator actionGenerator3 = new ActionGenerator(orgTree, rem_orgTree, mappings_locOrg_remOrg);
		actionGenerator3.generate();
		List<Action> actions_locOrg_remOrg = actionGenerator3.getActions();

		TreeContext remOrgContext = new TreeContext();
		remOrgContext.setRoot(rem_orgTree);
//		TreeDifferences app3 = new TreeDifferences("locOrg remOrg", orgContext, remOrgContext, actions_locOrg_remOrg,
//				mappings_locOrg_remOrg);
//		if (orgContext == text_orgContext)
//			app3.setVisible(true);

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

						// both actions are delete
						if (action_locOrg_locOld instanceof Delete && actions_locOrg_remOld instanceof Delete) {

						} else {
							// change old ast
							resetAST(action_locOrg_locOld, mappings_locOrg_locOld);
							changeAST(actions_locOrg_remOld, action_locOrg_remOld, mappings_locOrg_locOld,
									mappings_locOrg_remOrg, oldContext);
						}

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
				System.out.println("no conflict, remAction=" + action_locOrg_remOld.toString());
				changeAST(actions_locOrg_remOld, action_locOrg_remOld, mappings_locOrg_locOld, mappings_locOrg_remOrg,
						oldContext);
				isChange = true;

//				Matcher matcher_locOrg_locOld1 = Matchers.getInstance().getMatcher(orgTree, oldTree);
//				matcher_locOrg_locOld1.match();
//				MappingStore mappings_locOrg_locOld1 = matcher_locOrg_locOld1.getMappings();
//				ActionGenerator actionGenerator_locOrg_locOld1 = new ActionGenerator(orgTree, oldTree,
//						mappings_locOrg_locOld1);
//				actionGenerator_locOrg_locOld1.generate();
//				List<Action> actions_locOrg_locOld1 = actionGenerator_locOrg_locOld1.getActions();
//				oldContext.setRoot(oldTree);
//				TreeDifferences app4 = new TreeDifferences("locOrg locOld", orgContext, oldContext,
//						actions_locOrg_locOld1, mappings_locOrg_locOld1);
//				app4.setVisible(true);
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
			MappingStore map_locOrg_locOld, MappingStore map_locOrg_remOrg, TreeContext loc_oldContext) {
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
			if (z1 == null) {// 若无匹配，则添加到根节点上
				ITree loc_oldTree = loc_oldContext.getRoot();
				z1 = loc_oldTree;
			}
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
			if (w1 != null) {
				System.out.println("\t" + w1.toShortString() + "\tsetLabel \t" + label);
				w1.setLabel(label);
			}
		} else if (action_locOrg_remOld instanceof Move) {
			ITree w = action_locOrg_remOld.getNode();
			ITree z = ((Move) action_locOrg_remOld).getParent();

			ITree w1 = map_locOrg_locOld.getDst(w);
			ITree z1 = map_locOrg_locOld.getDst(z);
			ITree v1 = w1.getParent();
			if (v1 != null)
				v1.getChildren().remove(w1);
			int k = ((Move) action_locOrg_remOld).getPosition();
			if (z1 == null) {
				System.err.println("z1==null");
			} else {
				if (z1 != null && z1.getChildren().size() > k)
					z1.insertChild(w1, k);
				else
					z1.getChildren().add(w1);
				w1.setParent(z1);
			}

		} else if (action_locOrg_remOld instanceof Delete) {

			TimeTree w = (TimeTree) action_locOrg_remOld.getNode();
			ITree w1 = map_locOrg_locOld.getDst(w);
			if (w1 != null) {// in case of w1 is a root node
				ITree v1 = w1.getParent();
				if (v1 != null)
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

//	public static void main(String[] args) {
//		String src = "file1.pf";
//		String dst = "file2.pf";
//
//		long time = System.currentTimeMillis();
//		TreeContext text_orgContext = generatePfAST(src);
//		ITree text_orgTree = text_orgContext.getRoot();
//		text_orgTree = addTime((Tree) text_orgTree, time);
//		text_orgContext.setRoot(text_orgTree);
//		TreeContext text_oldContext = generatePfAST(dst);
//		ITree text_oldTree = text_orgContext.getRoot();
//		text_oldTree = addTime((Tree) text_oldTree, time);
//		text_oldContext.setRoot(text_oldTree);
//
//		Matcher matcher_locOrg_locOld = Matchers.getInstance().getMatcher(text_orgTree, text_oldTree);
//		matcher_locOrg_locOld.match();
//		MappingStore mappings_locOrg_locOld = matcher_locOrg_locOld.getMappings();
//		ActionGenerator actionGenerator_locOrg_locOld = new ActionGenerator(text_orgTree, text_oldTree,
//				mappings_locOrg_locOld);
//		actionGenerator_locOrg_locOld.generate();
//		List<Action> actions_locOrg_locOld = actionGenerator_locOrg_locOld.getActions();
//		TreeDifferences app1 = new TreeDifferences("locOrg locOld", text_orgContext, text_oldContext,
//				actions_locOrg_locOld, mappings_locOrg_locOld);
//		app1.setVisible(true);
//	}
}