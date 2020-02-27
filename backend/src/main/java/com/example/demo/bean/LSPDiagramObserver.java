package com.example.demo.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

import org.antlr.runtime.tree.CommonTree;
import org.springframework.beans.factory.annotation.Autowired;

import com.alexander.solovyov.TreeDifferences;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.LSP.DiagramContentChangeEvent;
import com.example.demo.LSP.LineInfo;
import com.example.demo.controller.WebSocket;
import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileService;
//import com.example.demo.service.AddressService;
import com.example.demo.service.ProblemEditor;
import com.example.demo.service.TransXML;
import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.gen.antlr3.xml.XmlTreeGenerator;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.AbstractTree;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TimeTree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;
import com.github.gumtreediff.tree.TreeUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import com.github.gumtreediff.actions.model.*;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.AbstractTree;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class LSPDiagramObserver extends LSPObserver {

	@Autowired	//自动装配
	FileService fileService = new FileService();
	ASTService astService = new ASTService();
	private Project project = null;
	private String lastestProjectAddress = AddressService.lastestProjectAddress;

	String rootAddress = lastestProjectAddress + uri +"/"+session +"/";
    String orgPath =rootAddress + "org.xml";//原始版
    String locPath =rootAddress + "loc.xml";//客户端传来的最新版
    String oldPath =rootAddress + "old.xml";//合成的最新版
    String mergePath =rootAddress + "merge.xml";//合并版
    ITree orgTree;
    ITree oldTree;
    private int i = 0;
    public LSPDiagramObserver(Session session,String uri,Project project) {
		super(session,uri,"diagram");
		this.project = project;
		generateOrgAST();		
		System.out.println("new LSPDiagramObserver		");
	}
	public LSPDiagramObserver(Session session,String uri,String title,ContextDiagram cd) {
		super(session,uri,"diagram");		
		project = new Project();
		project.setTitle(title);
		project.setContextDiagram(cd);
		ProblemDiagram pd = new ProblemDiagram();
		pd.setTitle("ProblemDiagram");
		project.setProblemDiagram(pd);
		System.out.println("new LSPDiagramObserver		");
	}

	public void setProject(ProblemDiagram pd) {
		project.setProblemDiagram(pd);
		generateOrgAST();		
	}
	
	@Deprecated
	public void setRequirementList(List<Requirement> requirementList) {
		project.getProblemDiagram().setRequirementList(requirementList);
		
	}
	
	@Deprecated
	public void setReferenceList(List<Reference> referenceList) {
		project.getProblemDiagram().setReferenceList(referenceList);
	}
	
	@Deprecated
	public void setConstraintList(List<Constraint> constraintList) {
		project.getProblemDiagram().setConstraintList(constraintList);
	}
	
	//save org.xml; generate orgTimeAST and oldTimeAST
	public void generateOrgAST() {
		//生成loc.xml 
		 saveLastestProject();
		 
		//将loc.xml重命名为 orgin.xml
		File loc = new File(locPath);
		File org = new File(orgPath);
		if(org.exists())
			org.delete();
		loc.renameTo(org);
		
		//生成 timeAST
	    XmlTreeGenerator orgGenerator = new XmlTreeGenerator();
	    try {
			orgTree = orgGenerator.generateFromFile(orgPath).getRoot();
			//remove xml element
//		    orgTree = getProjectParent(orgTree);
			orgTree = addTime((Tree)orgTree);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    oldTree = orgTree.deepCopy();
	}
		
	////////////////////////////change//////////////////////////////
	//更新本地project并发送给客户端
	public void change(String message){
		//更新本地project
		dealWithMessage(message);

//		//将原有xml 重命名为 orgin.xml
//		File loc = new File(locPath);
//		File org = new File(orgPath);
//		if(org.exists())
//			org.delete();
//		loc.renameTo(org);
		
		//将当前 project 存为loc.xml文件
		saveLastestProject();
		//生成 newTree 
		try {
			// org diff old 		for delete
		    Matcher matcher1 = Matchers.getInstance().getMatcher(orgTree, oldTree);
		    matcher1.match();
		    
		    // generate loc tree
		    XmlTreeGenerator locGenerator = new XmlTreeGenerator();
		    ITree locTree = locGenerator.generateFromFile(locPath).getRoot();
		    locTree = addTime((Tree)locTree);

		    // old diff loc		for add,move,update
		    Matcher matcher2 = Matchers.getInstance().getMatcher(oldTree, locTree);
		    matcher2.match();		    
		    ActionGenerator actionGenerator = new ActionGenerator(oldTree, locTree, matcher2.getMappings());
		    actionGenerator.generate();
		    List<Action> actions = actionGenerator.getActions();
		    
		    //show  locTree & new oldTree
//		    TreeContext sourceContext = new TreeContext();
//		    sourceContext.setRoot(oldTree);
//		    TreeContext destContext = new TreeContext();
//		    destContext.setRoot(locTree);
//			for (Action action : actions) {
//				System.out.print(action.getName()+"\t\t");
//				ITree tree = action.getNode();
//				System.out.print(tree.toShortString()+","+tree.getId()+"\t\t parent \t\t");
//				if(tree.getParent()!=null)
//					System.out.println(tree.getParent().toShortString()+","+tree.getParent().getId());
//				else
//					System.out.println("root");
//			}
//		    TreeDifferences app = new TreeDifferences(sourceContext, destContext,actions,matcher2.getMappings());
//	        app.setVisible(true);

		    //构建time-AST
		    oldTree = generateTimeAST(orgTree,oldTree,locTree,matcher1.getMappings(),matcher2.getMappings(),actions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//生成 old.xml
		astService.generateXml(oldTree,oldPath);
		
		//发送给远端
		session.getAsyncRemote().sendText(message);
		
		//发送给subject		
		for(LSPSubject subject:LSPSubjects.getSubjectSet()) {
			if(subject.getUri().contentEquals(uri)) {
				subject.setValue(orgTree, oldTree);
			}
		}
		//set new orgTree
		orgTree = oldTree;		
	}
	
	//生成oldAST 
	//change time of loc tree:	if not in actions,make loc tree's time equal old tree
	//org:for delete		old:last version		loc:lastest version		mappings: org - old	(to find org tree for delete) 
	public ITree generateTimeAST(ITree org, ITree old, ITree loc, MappingStore map_org_old,MappingStore map_old_loc,List<Action> actions ) {
		for (ITree t: old.getTrees()) {
			boolean inActions = false;
			for (Action action : actions) {
				if(t==action.getNode()) {
					//set delete time in org tree
					if(action instanceof Delete) {
						ITree orgTree = map_org_old.getSrc(action.getNode());
						if(orgTree!=null) {
							((TimeTree)orgTree).setTime(((TimeTree)loc).getTime());
						}
					}
					inActions = true;
					break;
				}					
			}
			//set loc tree's time equal old tree's
			if(!inActions) {
				ITree oldTree = map_old_loc.getSrc(t);
				if(oldTree!=null) {
					((TimeTree)t).setTime(((TimeTree)oldTree).getTime());
				}
			}
		}        
		return loc;
	}

	//org:for delete		src:old		dst:loc		map1: org - old	  actions: old - loc
	public ITree generateTimeAST1(ITree org, ITree old, ITree loc, MappingStore mappings,List<Action> actions ) {
		ITree newSrc = old.deepCopy();
		
	    TIntObjectMap<ITree> origSrcTrees = new TIntObjectHashMap<>();
		for (ITree t: old.getTrees())
		      origSrcTrees.put(t.getId(), t);

	    TIntObjectMap<ITree> newSrcTrees = new TIntObjectHashMap<>();
        for (ITree t: newSrc.getTrees())
          newSrcTrees.put(t.getId(), t);
        
		for (Action action : actions) {
		      if(action instanceof Insert) {
		    	  /*
		    	   * oldAST locAST    newAST       
		    	   *   z ...  y			z1  
		    	   *   |      |  ->		|		
		    	   *     ...  x			w	
		    	   * 
		    	   * if z == null (x),
		    	   * */
		    	  ITree x = action.getNode();
		    	  ITree z = ((Insert) action).getParent();
		    	  
		    	  // if x's parent is a insert action , continue
		    	  boolean isFind=false;
		    	  for(Action act:actions) {
		    		  if(act.getNode() == x.getParent()) {
		    			  isFind = true;
		    			  break;
		    		  }
		    	  }
//		    	  System.out.println("ins\t");
//			      System.out.print("\t"+x.toShortString()+","+x.getId()+"\t\t");
//					if(x.getParent()!=null)
//						System.out.println(x.getParent().toShortString()+","+x.getParent().getId());		
//					else
//						System.out.println("root");
//				  System.out.println("\tz= "+ z.toShortString()+","+z.getId()+"\t\t");
		    	  if(isFind) {
//				      System.out.println("\tcontinue");
		    		  continue;
		    	  }
//		    	  System.out.println("\tinsert to newTree");
		    	  ITree w = x.deepCopy();
//		    	  ITree w = new TimeTree((TimeTree)x);
		    	  ITree z1 = newSrcTrees.get(z.getId());
		    	  w.setParent(z1);
		    	  z1.addChild(w);
		      }else if(action instanceof Update) {
		    	  /*
		    	   * oldAST locAST    newAST      
		    	   *   .      .
		    	   *   |      |
		    	   *   w ...  x		w1(x.value)
		    	   * 
		    	   * */
		    	  ITree w = action.getNode();
		    	  ITree w1 = newSrcTrees.get(w.getId());
		    	  w1.setLabel(((Update) action).getValue());
		      }else if(action instanceof Move) {
		    	  /*
                     Action mv = new Move(origSrcTrees.get(w.getId()), origSrcTrees.get(z.getId()), k);
		    	   * oldAST 	locAST    newAST
		    	   * v(old)        		
		    	   *  \   z(new)   y 		Z1
		    	   *   \          |			|
		    	   *    w ......   x		W1
		    	   * 
                        int oldk = w.positionInParent();
                        z.getChildren().add(k, w);
                        w.getParent().getChildren().remove(oldk);
                        w.setParent(z);
		    	   * */
		    	  ITree w = action.getNode();
		    	  ITree z = ((Move) action).getParent();

                  ITree w1 = newSrcTrees.get(w.getId());
                  ITree z1 = newSrcTrees.get(z.getId());
		    	  
                  w1.getParent().getChildren().remove(w1);               
		    	  z1.getChildren().add(w1);
		    	  w1.setParent(z1);
		      }else if(action instanceof Delete) {
		    	  /*
		    	   * oldAST		  newAST
		    	   * 	 	... 	v1
		    	   * 		...		|
		    	   *   w	...		
		    	   */
		    	  ITree w = action.getNode();
		    	  ITree w1 = newSrcTrees.get(w.getId());
		    	  ITree v1 = w1.getParent();
		    	  int k = w1.positionInParent();
		    	  v1.getChildren().remove(k);
		    	  //set newTime in orgAST
		    	  long time = ((TimeTree)loc).getTime();
		    	  if(((TimeTree)mappings.getSrc(w))!=null) {
		    		  ((TimeTree)mappings.getSrc(w)).setTime(time);
		    	  }
		    	  
		      }
		    }
		
		return newSrc;
	}

	//为整棵树设置当前时间
	public ITree addTime(Tree tree){
		long time = System.currentTimeMillis();
		return TimeTree.deepCopy(tree,time);
	}
	
	//save lastestProject to lastestProject/{uri}/loc.xml
	public void saveLastestProject() {
		File dir = new File(rootAddress);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		fileService.saveLastestProject(rootAddress,uri,project);
	}
	
	public void dealWithMessage(String message) {
		JSONObject json = JSONObject.parseObject(message); 
		JSONObject params = (JSONObject) json.get("params");
		JSONArray contentChanges = params.getJSONArray("contentChanges");
		for(Object contentChangeEvent: contentChanges) {
			JSONObject jcontentChange = (JSONObject)contentChangeEvent;			
			String shapeType = jcontentChange.getString("shapeType");
			String changeType = jcontentChange.getString("changeType");				
		    switch(changeType){
		      case "add": 
		        add(shapeType,jcontentChange);
		        break;
		      case "delete": 
		        delete(shapeType,jcontentChange);
		        break;
		      case "change":
		        change(shapeType,jcontentChange);
		        break;
		    }
		}
	}

	public void add(String shape,JSONObject jcontentChange) {
		String snewShape = jcontentChange.getString("newShape");
		String slineInfo = jcontentChange.getString("lineInfo");
		String snewPhenomenon= jcontentChange.getString("newPhenomenon");		
		Shape newShape;	
	    switch(shape){
	      case "mac":	
	    	  newShape = JSON.parseObject(snewShape, Machine.class);
	    	  if(project == null)
	    		  System.out.println("project==null");
	    	  else if(  project.getContextDiagram()==null)
	    		  System.out.println(" project.getContextDiagram()==null");
	    	  else
	    		  project.getContextDiagram().setMachine((Machine)newShape);
	    	  break;
	      case "pro":
	    	  newShape = JSON.parseObject(snewShape, ProblemDomain.class);
	    	  project.getContextDiagram().getProblemDomainList().add((ProblemDomain)newShape);
	          break;
	      case "req":
	    	  newShape = JSON.parseObject(snewShape, Requirement.class);
	    	  project.getProblemDiagram().getRequirementList().add((Requirement)newShape);
	        break;
	      case "int":
	    	  newShape = JSON.parseObject(snewShape, Interface.class);
	    	  project.getContextDiagram().getInterfaceList().add((Interface)newShape);
	        break;
	      case "ref":
	    	  newShape = JSON.parseObject(snewShape, Reference.class);
	    	  project.getProblemDiagram().getReferenceList().add((Reference)newShape);
	        break;
	      case "con":
	    	  newShape = JSON.parseObject(snewShape, Constraint.class);
	    	  project.getProblemDiagram().getConstraintList().add((Constraint)newShape);
	        break;
	      case "phe":	    	  
				LineInfo lineInfo = JSON.parseObject(slineInfo, LineInfo.class);
				addPhenomenon(lineInfo,snewPhenomenon);
	    }
	}

	public void addPhenomenon(LineInfo lineInfo,String snewPhenomenon) {
		String lineType = lineInfo.getType();
		switch(lineType) {
		case "int":
			Phenomenon newPhenomenon = JSON.parseObject(snewPhenomenon, Phenomenon.class);
			for(Interface intf : project.getContextDiagram().getInterfaceList()) {
				if(intf.getName().contentEquals(lineInfo.getName())) {
					intf.getPhenomenonList().add(newPhenomenon);
				}
			}
			break;
		case "ref":
			RequirementPhenomenon newRefPhenomenon = JSON.parseObject(snewPhenomenon, RequirementPhenomenon.class);
			for(Reference ref : project.getProblemDiagram().getReferenceList()) {
				if(ref.getName().contentEquals(lineInfo.getName())) {
					ref.getPhenomenonList().add(newRefPhenomenon);
				}
			}
			break;
		case "con":
			RequirementPhenomenon newConPhenomenon = JSON.parseObject(snewPhenomenon, RequirementPhenomenon.class);
			for(Constraint con : project.getProblemDiagram().getConstraintList()) {
				if(con.getName().contentEquals(lineInfo.getName())) {
					con.getPhenomenonList().add(newConPhenomenon);
				}
			}
			break;
		}
	}

	public void delete(String shape,JSONObject jcontentChange) {		
		String soldShape = jcontentChange.getString("oldShape");
		String slineInfo = jcontentChange.getString("LineInfo");
		String soldPhenomenon= jcontentChange.getString("oldPhenomenon");		
		Shape oldShape;	
	    switch(shape){
	      case "mac":
	    	oldShape = JSON.parseObject(soldShape, Machine.class);
	        project.getContextDiagram().setMachine(null);
	        break;
	      case "pro":
	    	  oldShape = JSON.parseObject(soldShape, ProblemDomain.class);
	    	  delete((ProblemDomain)oldShape,project.getContextDiagram().getProblemDomainList());
	          break;
	      case "req":
		    	oldShape = JSON.parseObject(soldShape, Requirement.class);
	    	  delete((Requirement)oldShape,project.getProblemDiagram().getRequirementList());
	        break;
	      case "int":
		    	oldShape = JSON.parseObject(soldShape, Interface.class);
	    	  delete((Interface)oldShape,project.getContextDiagram().getInterfaceList());
	        break;
	      case "ref":
		    	oldShape = JSON.parseObject(soldShape, Reference.class);
	    	  delete((Reference)oldShape,project.getProblemDiagram().getReferenceList());
	        break;
	      case "con":
		    	oldShape = JSON.parseObject(soldShape, Constraint.class);
	    	  delete((Constraint)oldShape,project.getProblemDiagram().getConstraintList());
	        break;
	      case "phe":	    	  
				LineInfo lineInfo = JSON.parseObject(slineInfo, LineInfo.class);
				Phenomenon oldPhenomenon = JSON.parseObject(soldPhenomenon, Phenomenon.class);
	    }
	}
	public void delete(ProblemDomain shape,List<ProblemDomain> list) {
	    for (Shape item: list) {
	      if (item.getName().contentEquals(shape.getName())) {
	        list.remove(item);
	        break;
	      }
	    }
	}
	public void delete(Requirement shape,List<Requirement> list) {
	    for (Shape item: list) {
	      if (item.getName().contentEquals(shape.getName())) {
	        list.remove(item);
	        break;
	      }
	    }
	}
	public void delete(Reference shape,List<Reference> list) {
	    for (Shape item: list) {
	      if (item.getName().contentEquals(shape.getName())) {
	        list.remove(item);
	        break;
	      }
	    }
	}
	public void delete(Interface shape,List<Interface> list) {
	    for (Shape item: list) {
	      if (item.getName().contentEquals(shape.getName())) {
	        list.remove(item);
	        break;
	      }
	    }
	}
	public void delete(Constraint shape,List<Constraint> list) {
	    for (Shape item: list) {
	      if (item.getName().contentEquals(shape.getName())) {
	        list.remove(item);
	        break;
	      }
	    }
	}
	
	public void change(String shape,JSONObject jcontentChange) {
		String soldShape = jcontentChange.getString("oldShape");
		String snewShape = jcontentChange.getString("newShape");			
		String slineInfo = jcontentChange.getString("LineInfo");
		String soldPhenomenon= jcontentChange.getString("oldPhenomenon");
		String snewPhenomenon= jcontentChange.getString("newPhenomenon");		
		Shape  oldShape;
		Shape  newShape;
		Phenomenon  oldPhenomenon;
		Phenomenon  newPhenomenon;
		switch(shape){
	      case "mac":
	    	newShape = JSON.parseObject(snewShape, Machine.class);
	    	oldShape = JSON.parseObject(soldShape, Machine.class);
	        change((Machine)oldShape,(Machine)newShape);
	        break;
	      case "pro":
	    	  oldShape = JSON.parseObject(soldShape, ProblemDomain.class);
	    	  newShape = JSON.parseObject(snewShape, ProblemDomain.class);
	          change((ProblemDomain)oldShape,(ProblemDomain)newShape);
	          break;
	      case "req":
		    oldShape = JSON.parseObject(soldShape, Requirement.class);
	    	newShape = JSON.parseObject(snewShape, Requirement.class);
	        change((Requirement)oldShape,(Requirement)newShape);
	        break;
	      case "int":
			    oldShape = JSON.parseObject(soldShape, Interface.class);
		    	newShape = JSON.parseObject(snewShape, Interface.class);
	        change((Interface)oldShape,(Interface)newShape);
	        break;
	      case "ref":
			    oldShape = JSON.parseObject(soldShape, Reference.class);
		    	newShape = JSON.parseObject(snewShape, Reference.class);
	        change((Reference)oldShape,(Reference)newShape);
	        break;
	      case "con":
			    oldShape = JSON.parseObject(soldShape, Constraint.class);
		    	newShape = JSON.parseObject(snewShape, Constraint.class);
		    	change((Constraint)oldShape,(Constraint)newShape);
	        break;
	    }
	}
	public void change(Machine old, Machine new1) {
        project.getContextDiagram().setMachine((Machine)new1);
        project.getProblemDiagram().getContextDiagram().setMachine((Machine)new1);
	}
	public void change(ProblemDomain old, ProblemDomain new1) {
		int i = 0;
		for(ProblemDomain item: project.getContextDiagram().getProblemDomainList()){
			if(item.getNo() == old.getNo()){
				project.getContextDiagram().getProblemDomainList().set(i,new1);
				if(!item.getShortname().contentEquals(old.getShortname())){
					changeRelatedLink(old.getShortname(),new1.getShortname());
					
				}
				break;
			}	
			i++;		
		}
	}
	public void change(Requirement old, Requirement new1) {
		int i = 0;
		for(Requirement item: project.getProblemDiagram().getRequirementList()){
			if(item.getNo() == old.getNo()){
				project.getProblemDiagram().getRequirementList().set(i,new1);
				if(!item.getShortname().contentEquals(old.getShortname())){
					changeRelatedLink(old.getShortname(),new1.getShortname());					
				}
				break;
			}	
			i++;		
		}
	}
	public void change(Reference old, Reference new1) {
		int i = 0;
		for(Reference item: project.getProblemDiagram().getReferenceList()){
			if(item.getNo() == old.getNo()){
				project.getProblemDiagram().getReferenceList().set(i,new1);				
				break;
			}	
			i++;		
		}
	}	
	public void change(Interface old, Interface new1) {
		int i = 0;
		for(Interface item: project.getContextDiagram().getInterfaceList()){
			if(item.getNo() == old.getNo()){
				project.getContextDiagram().getInterfaceList().set(i,new1);				
				break;
			}	
			i++;		
		}
	}
	public void change(Constraint old, Constraint new1) {
		int i = 0;
		for(Constraint item: project.getProblemDiagram().getConstraintList()){
			if(item.getNo() == old.getNo()){
				project.getProblemDiagram().getConstraintList().set(i,new1);				
				break;
			}	
			i++;		
		}
	}	
	
	public void changeRelatedLink(String oldShortName,String newShortName) {}

	@Override
	//处理其他project传来的信息
	public void update(String message) {
		// TODO Auto-generated method stub
		System.out.print("diagramobserver update		");
		try {
			//发送给远端 。。。暂时
			System.out.println(message);				
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//update old ast since other editor change
	@Override
	public void update(ITree rem_orgTree, ITree rem_oldTree) {
		// delta1 loc - org
	    Matcher matcher1 = Matchers.getInstance().getMatcher(orgTree, oldTree);
	    matcher1.match();
	    MappingStore mappings_locOrg_locOld = matcher1.getMappings();
	    ActionGenerator actionGenerator1 = new ActionGenerator(orgTree, oldTree, mappings_locOrg_locOld);
	    actionGenerator1.generate();
	    List<Action> actions_locOrg_locOld = actionGenerator1.getActions();
	    List<Action> actionsmerge = new ArrayList<Action>(actions_locOrg_locOld);

	    // delta2 org remold
	    Matcher matcher2 = Matchers.getInstance().getMatcher(orgTree, rem_oldTree);
	    matcher2.match();
	    MappingStore mappings_locOrg_remOld = matcher2.getMappings();
	    ActionGenerator actionGenerator2 = new ActionGenerator(orgTree, rem_oldTree, mappings_locOrg_remOld);
	    actionGenerator2.generate();
	    List<Action> actions_locOrg_remOld = actionGenerator2.getActions();
	    
	    // delta3 org  rem_org
	    Matcher matcher3 = Matchers.getInstance().getMatcher(orgTree, rem_orgTree);
	    matcher3.match();
	    MappingStore mappings_locOrg_remOrg = matcher3.getMappings();
	    boolean isChange = false;
	    boolean conflict = false;
	    for(Action action_locOrg_remOld:actions_locOrg_remOld) {
    		//get rem time
    		long time_rem=0;
    		if(action_locOrg_remOld instanceof Delete) {
    			TimeTree src = (TimeTree)action_locOrg_remOld.getNode();
    			TimeTree rem_src = (TimeTree)mappings_locOrg_remOrg.getDst(src);		
    			if(rem_src != null)
    				time_rem = rem_src.getTime();
    			else
    				time_rem = src.getTime();
    		}else if(action_locOrg_remOld instanceof Insert) {
    			time_rem = ((TimeTree)action_locOrg_remOld.getNode()).getTime();
    		}else {//Update Move
    			TimeTree src = (TimeTree)action_locOrg_remOld.getNode();
    			TimeTree dst = (TimeTree)mappings_locOrg_remOld.getDst(src);
    			if(dst!=null)
    				time_rem = dst.getTime();
    		}
    		
	    	for(Action action_locOrg_locOld:actions_locOrg_locOld) {
	    		//conflict   
		    	if(action_locOrg_locOld.getNode() == action_locOrg_remOld.getNode()) {
		    		long time_loc=0;
		    		//get loc time 
		    		if(action_locOrg_locOld instanceof Delete) {
		    			TimeTree src = (TimeTree)action_locOrg_locOld.getNode();
		    			time_loc = src.getTime();
		    		}else if(action_locOrg_locOld instanceof Insert) {
		    			time_loc = ((TimeTree)action_locOrg_locOld.getNode()).getTime();
		    		}else {//Update Move
		    			TimeTree src = (TimeTree)action_locOrg_locOld.getNode();
		    			TimeTree dst = (TimeTree)mappings_locOrg_locOld.getDst(src);
		    			time_loc = dst.getTime();
		    		}

		    		//rem change is new,ignore action1(loc), and set rem action2
		    		if(time_loc < time_rem) {
//			    		System.out.println("rem change is new, locNode="+action_locOrg_locOld.getNode().toShortString()+
//			    				", remNode="+action_locOrg_remOld.getNode().toShortString());
		    			System.out.println(time_loc + "<" +time_rem);
			    		System.out.println("rem change is new,\n\t locAction="+
			    				action_locOrg_locOld.toString()+
			    				",\n\t remAction="+action_locOrg_remOld.toString());
			    		
		    			actionsmerge.remove(action_locOrg_locOld);
		    			actionsmerge.add(action_locOrg_remOld);	
		    			//change old ast
		    			resetAST(action_locOrg_locOld,mappings_locOrg_locOld);
		    			changeAST(actions_locOrg_remOld,action_locOrg_remOld,mappings_locOrg_locOld,mappings_locOrg_remOrg);
		    			isChange = true;
		    		}
		    		conflict = true;
		    		break;
		    	}
		    }
	    	
	    	if(!conflict) {//no conflict
//	    		System.out.println("no conflict, remNode="+action_locOrg_remOld.getNode().toShortString()+", "+action_locOrg_remOld.getName());
	    		System.out.println("no conflict, remAction="+ action_locOrg_remOld.toString());
	    		actionsmerge.add(action_locOrg_remOld);
    			changeAST(actions_locOrg_remOld,action_locOrg_remOld,mappings_locOrg_locOld,mappings_locOrg_remOrg);
	    	}
	    	conflict = false;
	    }
	    //make org = old 
	    orgTree = oldTree;
	    //generate merge.xml
		astService.generateXml(oldTree,mergePath);
		//生成新的project
		project = astService.getProject(mergePath);
		//通知客户端

		JSONObject message = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject diagram = new JSONObject();
		JSONArray contentChanges = new JSONArray();
		
		diagram.put("uri",uri);
		diagram.put("version",0);
		
		JSONObject contentChange = new JSONObject();
		contentChange.put("shapeType","project");
		contentChange.put("newProject",project);
		contentChanges.add(contentChange);
		
		params.put("diagram",diagram);
		params.put("contentChanges",contentChanges);

        message.put("jsonrpc", "2.0");
        message.put("id",0); 
        message.put("method","Diagram/didChange"); 
        message.put("params",params);

		session.getAsyncRemote().sendText(JSON.toJSONString(message));
        
	}
	
	public void resetAST(Action action,MappingStore mappings){
		if(action instanceof Insert) {
			//remove node and its children
			/* srcParent		|	dstParent
			 *   				|		|
			 *  				|		|
			 *  				|	   dst
			 * */
			TimeTree dst = (TimeTree)action.getNode();
			ITree dstParent = dst.getParent();
			dstParent.getChildren().remove(dst);
			
		}else if(action instanceof Delete) {
			//add node
			/* srcParent		|	dstParent
			 * |  				|		
			 * | 				|	
			 * src				|	
			 * */
			TimeTree src = (TimeTree)action.getNode();
			ITree srcParent = src.getParent();
			ITree dstParent = mappings.getDst(srcParent);
			ITree dst = new TimeTree(src);
			
			dst.setParent(dstParent);
			dstParent.addChild(dst);
			
		}else if(action instanceof Move) {
			//move node
			/* srcParent		|	oldParent
			 * |  	parent		|		dstParent
			 * | 				|		/
			 * src				|	dst
			 * */
			ITree src = action.getNode();
			ITree parent = ((Move) action).getParent();
			ITree srcParent = src.getParent();
			
			ITree dst = mappings.getDst(src);
			ITree dstParent = dst.getParent();
			ITree oldParent = mappings.getDst(srcParent);
			
			dstParent.getChildren().remove(dst);
			oldParent.getChildren().add(dst);
			dst.setParent(oldParent);
			
		}else if(action instanceof Update) {
			//Update node
			/* 				
			 * src					dst
			 * */
			ITree src = action.getNode();
			ITree dst = mappings.getDst(src);
			String label = src.getLabel();
			dst.setLabel(label);
		}
	}
	
	//mappings: org & old		map: org remOrg
	public void changeAST(List<Action> actions_locOrg_remOld, Action action_locOrg_remOld,MappingStore map_locOrg_locOld,MappingStore map_locOrg_remOrg){
		System.out.println("changeAST");
		//根据远程action 修改本地 old
		      if(action_locOrg_remOld instanceof Insert) {
		    	  /*
		    	   * orgAST remAST    oldAST       
		    	   *   z ...  y			z1  
		    	   *   |      |  ->		|		
		    	   *     ...  x			w	
		    	   * 
		    	   * */
		    	  ITree x = action_locOrg_remOld.getNode();
		    	  ITree z = ((Insert) action_locOrg_remOld).getParent();
		    	  
		    	  //if parent in actions and is a insert ,ignore
		    	  boolean isFind=false;
		    	  for(Action act:actions_locOrg_remOld) {
		    		  if(act.getNode() == x.getParent()) {
		    			  isFind = true;
		    			  break;
		    		  }
		    	  }
		    	  if(isFind) {
		    		  return;
		    	  }
//		    	  ITree w = new TimeTree((TimeTree)x);
		    	  ITree w = (TimeTree)x.deepCopy();		    	  
		    	  ITree z1 = map_locOrg_locOld.getDst(z);
		    	  w.setParent(z1);
		    	  z1.addChild(w);

	    		  System.out.println("=======insert=====");
		    	  for(ITree tree : w.getTrees())
		    		  System.out.println(tree.toShortString());
	    		  System.out.println("=======insert=====");
		      }else if(action_locOrg_remOld instanceof Update) {
		    	  /*
		    	   * orgAST remAST    newAST(locOld)      
		    	   *   .      .
		    	   *   |      |
		    	   *   w ...  x		w1(x.value)
		    	   * 
		    	   * */
		    	  ITree w = action_locOrg_remOld.getNode();
		    	  ITree w1 = map_locOrg_locOld.getDst(w);
		    	  String label = ((Update) action_locOrg_remOld).getValue();
		    	  System.out.println("\t"+w1.toShortString() + "\tsetLabel \t" + label);
		    	  w1.setLabel(label);
		      }else if(action_locOrg_remOld instanceof Move) {
		    	  /*
                   Action mv = new Move(origSrcTrees.get(w.getId()), origSrcTrees.get(z.getId()), k);
                   		|-------mappings-----|
		    	   * orgAST 	remAST    oldAST
		    	   * 
		    	   * v(old)        		
		    	   *  \   z(new)   y 		Z1
		    	   *   \           |		|
		    	   *    w ......   x		W1
		    	   * 
		    	   * */
		    	  ITree w = action_locOrg_remOld.getNode();
		    	  ITree z = ((Move) action_locOrg_remOld).getParent();

		    	  ITree w1 = map_locOrg_locOld.getDst(w);
                  ITree z1 = map_locOrg_locOld.getDst(z);
		    	  
                  w1.getParent().getChildren().remove(w1);               
		    	  z1.getChildren().add(w1);
		    	  w1.setParent(z1);
		      }else if(action_locOrg_remOld instanceof Delete) {
		    	  /*
		    	   * orgAST	  	  oldAST	|	rem_orgAST	  	  remAST			old
		    	   * 	 	... 	v1		|									 	v1
		    	   * 		...		|		|								=>
		    	   *   w	...		w1		|       w2(time)							 
		    	   */
		    	  TimeTree w = (TimeTree)action_locOrg_remOld.getNode();
		    	  ITree w1 = map_locOrg_locOld.getDst(w);
		    	  ITree v1 = w1.getParent();
		    	  v1.getChildren().remove(w1);
		    	  
		    	  //set newTime in orgAST
		    	  TimeTree w2 = (TimeTree)map_locOrg_remOrg.getDst(w);
		    	  if(w2!=null) {
		    		  long time = w2.getTime();
		    		  w.setTime(time);
		    	  }
		      }
		    

	}
	@Override
	public void deleteFile() {
		// TODO Auto-generated method stub
		
		
	}
}
