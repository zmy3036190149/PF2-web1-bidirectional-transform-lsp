package com.example.demo.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import javax.websocket.Session;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.AddressService;
//import com.example.demo.service.AddressService;
import com.example.demo.service.FileService;
import com.example.demo.service.GitUtil;
import com.example.demo.service.ProblemEditor;
import com.example.demo.service.ProjectService;

import pf.PfStandaloneSetup;

import com.alibaba.fastjson.JSONArray; 
import com.alibaba.fastjson.JSONObject;
public class DiagramSubject extends Subject{	

	@Autowired	//自动装配
	FileService fileService;
	private Project project;
	private Project newProject = new Project();
	private String newText = "";
	private int idStart;
	private int idEnd;
	private ArrayList<String> message = new ArrayList<String>();
	
	
	private String transferMessage = "";
//	private String rootAddress = AddressService.rootAddress;

	private String rootAddress = AddressService.rootAddress;
	public ArrayList<String> getMessages() {
		return message;
	}
	public String getMessage(int index) {
		return message.get(index);
	}
	public int getMessagesLen() {
		return message.size();
	}
	public void addMessage(String mes) {
//		System.out.println("addMessage, id = "+id+"message="+mes);
		message.add(mes);
		id ++;
	}
	public int getId() {
		return id;
	}	
	public DiagramSubject(String title, String version,Project project,Observer observer) {
		super(title,version,"diagram",observer);
		this.project = project;
//		if(project == null)
//			System.out.println("project=null");
		System.out.println("new DiagramSubject,title = "+ title);
//		System.out.println(this.observerSet);
	}

	//将observer添加到observerSet，发送registered
	public void attach(Observer observer) {
		super.attach(observer);
		if(observer.editorType.contentEquals("text")) {
			System.out.println("diagramsubject attach(text observer)	title: "+getTitle());
		}else
			try {
				System.out.println("diagramsubject attach(diagram observer)	title: "+getTitle());
				int len = getMessagesLen();
				for(int i =id;i<len;i++) {
					System.out.println(getMessage(i));
					observer.session.getBasicRemote().sendText(getMessage(i));
				}					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void detach(Session session) {
		super.detach(session);
		System.out.println("removeSession");
		System.out.println(this.observerSet);
	}
	@Override
	public void setValue(String message) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.parseObject(message); 
		String from = (String) json.get("from");	
		if(from.contentEquals("text"))
			return;
		json.put("id",id++);
		addMessage(message);
//		changeProject(message);
//		System.out.println("setValue"+message);
		notifyObserver(message);
	}
	public void notifyObserver(String message){
		System.out.println("observer数目：		"+getObserverSet().size());
		for(Observer observer: getObserverSet()) {
			observer.update(message);
		}
	}

	//根据消息修改Project
	public void changeProject(String message){
		JSONObject json = JSONObject.parseObject(message); 
		String shape = (String)json.get("shape");		
	    switch((String)json.get("type")){
	      case "add": 
	        add(shape,json);
	        break;
	      case "delete": 
	        delete(shape,json);
	        break;
	      case "change":
	        change(shape,json);
	        break;
	    }
	}
	public void add(String shape,JSONObject json) {
		JSONObject  newObject = (JSONObject)json.get("new");
	    switch(shape){
	      case "mac":
	    	  String name = (String)newObject.get("name");
	    	  String shortName = (String)newObject.get("name");
	    	  int x = (int)newObject.get("x");
	    	  int y = (int)newObject.get("y");
	    	  int w = (int)newObject.get("w");
	    	  int h = (int)newObject.get("h");
	    	  Machine machine = new Machine(name,shortName,x,y,w,h);
	    	  if(project == null)
	    		  System.out.println("project==null");
	    	  else if(  project.getContextDiagram()==null)
	    		  System.out.println(" project.getContextDiagram()==null");
	    	  else
	    		  project.getContextDiagram().setMachine(machine);
	    	  break;
	      case "pro":
	    	  project.getContextDiagram().getProblemDomainList().add((ProblemDomain)json.get("new"));
	          break;
	      case "req":
	    	  project.getProblemDiagram().getRequirementList().add((Requirement)json.get("new"));
	        break;
	      case "int":
	    	  project.getContextDiagram().getInterfaceList().add((Interface)json.get("new"));
	        break;
	      case "ref":
	    	  project.getProblemDiagram().getReferenceList().add((Reference)json.get("new"));
	        break;
	      case "con":
	    	  project.getProblemDiagram().getConstraintList().add((Constraint)json.get("new"));
	        break;
	    }
	}
	
	public void delete(String shape,JSONObject json) {
		Shape  old = (Shape)json.get("old");
	    switch(shape){
	      case "mac":
	        project.getContextDiagram().setMachine(null);
	        break;
	      case "pro":
	    	  delete((ProblemDomain)old,project.getContextDiagram().getProblemDomainList());
	          break;
	      case "req":
	    	  delete((Requirement)old,project.getProblemDiagram().getRequirementList());
	        break;
	      case "int":
	    	  delete((Interface)old,project.getContextDiagram().getInterfaceList());
	        break;
	      case "ref":
	    	  delete((Reference)old,project.getProblemDiagram().getReferenceList());
	        break;
	      case "con":
	    	  delete((Constraint)old,project.getProblemDiagram().getConstraintList());
	        break;
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
	
	public void change(String shape,JSONObject json) {

		Shape  old = (Shape)json.get("old");

		Shape  new1 = (Shape)json.get("new");
		switch(shape){
	      case "mac":
	        change((Machine)old,(Machine)new1);
	        break;
	      case "pro":
	          change((ProblemDomain)old,(ProblemDomain)new1);
	          break;
	      case "req":
	        change((Requirement)old,(Requirement)new1);
	        break;
	      case "int":        
	        change((Interface)old,(Interface)new1);
	        break;
	      case "ref":
	        change((Reference)old,(Reference)new1);
	        break;
	      case "con":
	        change((Constraint)old,(Constraint)new1);
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
				if(!item.getShortName().contentEquals(old.getShortName())){
					changeRelatedLink(old.getShortName(),new1.getShortName());
					
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
				if(!item.getShortName().contentEquals(old.getShortName())){
					changeRelatedLink(old.getShortName(),new1.getShortName());					
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
	
	//根据新的pf文件转为project,并且返回消息
	public String  transfer(String message) {
		JSONObject json = JSONObject.parseObject(message); 
		String from = (String) json.get("from");	
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
		String new1 = (String) json.get("text");	
		ArrayList<String> messages = new ArrayList<String>();
		if(new1.contentEquals(newText))
			return transferMessage;		
//		try {
//			GitUtil.gitCheckout(title, rootAddress);  //切换分支
//			GitUtil.currentBranch(rootAddress);			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//保存pf文件
		String fileName = getTitle() + ".pf";
		String filePath = "pf/" + fileName;
		File file = new File(filePath);
        PrintStream ps;
		try {
			ps = new PrintStream(new FileOutputStream(file));
			ps.println(new1);// 往文件里写入字符串
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//转为project
		File xmlFile = ProblemEditor.performSave(fileName);
		Project tempProject = ProblemEditor.transformXML(xmlFile);
		//返回project
		newText = new1;		

		json.put("text",null);
		json.put("pro",tempProject);
		json.put("to","diagram");
		transferMessage = json.toJSONString();
		addMessage(message);
		return  transferMessage;
	}
	//比较两个project不同，将其转换为messageList
	public  ArrayList<String> getChangeMessages(Project old,Project new1) {
		ArrayList<String> messages= new ArrayList<String>();
		getMachineChangeMessages(messages, old.getContextDiagram().getMachine(), new1.getContextDiagram().getMachine());
		List<ProblemDomain> oldPdList = old.getContextDiagram().getProblemDomainList();
		List<ProblemDomain> newPdList = new1.getContextDiagram().getProblemDomainList();
		getProblemDomainChangeMessages(messages, oldPdList, newPdList);		
		return messages;
	}
	public  ArrayList<String> getMachineChangeMessages(ArrayList<String> messages,Machine old,Machine new1) {
//		ArrayList<String> messages= new ArrayList<String>();
		if(!old.getName().contentEquals(new1.getName())
			|| 	!old.getShortName().contentEquals(new1.getShortName())) {
			messages.add(getChangeMessage("change","mac",old,new1));
		}
		return messages;
	}
	public  ArrayList<String> getProblemDomainChangeMessages(ArrayList<String> messages,List<ProblemDomain> oldList,List<ProblemDomain> new1List) {
//		ArrayList<String> messages= new ArrayList<String>();
		//先找修改的和新增的
		for(ProblemDomain new1:new1List) {
			String flag = "nofind";
			for(ProblemDomain old:oldList) {
				if(new1.getNo()==old.getNo()) {
					if(!new1.getName().contentEquals(old.getName()) ||
							!new1.getShortName().contentEquals(old.getShortName()) ||
							new1.getX()!=old.getX()||
							new1.getY()!=old.getY()) {
						messages.add(getChangeMessage("change","pro",old,new1));
						flag = "change";
					}else {
						flag = "nochange";
					}
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("add","pro",null,new1));
			}
		}
		//再找删除的
		for(ProblemDomain old:new1List) {
			String flag = "nofind";
			for(ProblemDomain new1:oldList) {
				if(new1.getNo()==old.getNo()) {					
						flag = "find";
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("delete","pro",old,null));
			}
		}
		return messages;
	}
	public  ArrayList<String> getRequirementChangeMessages(ArrayList<String> messages,List<Requirement> oldList,List<Requirement> new1List) {
//		ArrayList<String> messages= new ArrayList<String>();
		//先找修改的和新增的
		for(Requirement new1:new1List) {
			String flag = "nofind";
			for(Requirement old:oldList) {
				if(new1.getNo()==old.getNo()) {
					if(!new1.getName().contentEquals(old.getName()) ||
							new1.getX()!=old.getX()||
							new1.getY()!=old.getY()) {
						messages.add(getChangeMessage("change","req",old,new1));
						flag = "change";
					}else {
						flag = "nochange";
					}
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("add","req",null,new1));
			}
		}
		//再找删除的
		for(Requirement old:new1List) {
			String flag = "nofind";
			for(Requirement new1:oldList) {
				if(new1.getNo()==old.getNo()) {					
						flag = "find";
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("delete","req",old,null));
			}
		}
		return messages;
	}
	public  ArrayList<String> getInterfaceChangeMessages(ArrayList<String> messages,List<Interface> oldList,List<Interface> new1List) {
//		ArrayList<String> messages= new ArrayList<String>();
		//先找修改的和新增的
		for(Interface new1:new1List) {
			String flag = "nofind";
			for(Interface old:oldList) {
				if(new1.getNo()==old.getNo()) {
					if(!new1.getName().contentEquals(old.getName()) ||
							new1.getFrom().contentEquals(old.getFrom())||
							new1.getTo().contentEquals(old.getTo()) ||
							!equal(new1.getPhenomenonList(),old.getPhenomenonList())) {
						messages.add(getChangeMessage("change","int",old,new1));
						flag = "change";
					}else {
						flag = "nochange";
					}
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("add","int",null,new1));
			}
		}
		//再找删除的
		for(Interface old:new1List) {
			String flag = "nofind";
			for(Interface new1:oldList) {
				if(new1.getNo()==old.getNo()) {					
						flag = "find";
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("delete","int",old,null));
			}
		}
		return messages;
	}
	public  ArrayList<String> getReferenceChangeMessages(ArrayList<String> messages,List<ProblemDiagramLine> oldList,List<ProblemDiagramLine> new1List) {
		return getProblemDiagramLineChangeMessages(messages,oldList,new1List,"ref");
	}
	public  ArrayList<String> getConstraintChangeMessages(ArrayList<String> messages,List<ProblemDiagramLine> oldList,List<ProblemDiagramLine> new1List) {
		return getProblemDiagramLineChangeMessages(messages,oldList,new1List,"con");
	}
	public  ArrayList<String> getProblemDiagramLineChangeMessages(ArrayList<String> messages,List<ProblemDiagramLine> oldList,List<ProblemDiagramLine> new1List,String type) {
//		ArrayList<String> messages= new ArrayList<String>();
		//先找修改的和新增的
		for(ProblemDiagramLine new1:new1List) {
			String flag = "nofind";
			for(ProblemDiagramLine old:oldList) {
				if(new1.getNo()==old.getNo()) {
					if(!new1.getName().contentEquals(old.getName()) ||
							new1.getFrom().contentEquals(old.getFrom())||
							new1.getTo().contentEquals(old.getTo()) ||
							!equal1(new1.getPhenomenonList(),old.getPhenomenonList())) {
						messages.add(getChangeMessage("change",type,old,new1));
						flag = "change";
					}else {
						flag = "nochange";
					}
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("add",type,null,new1));
			}
		}
		//再找删除的
		for(ProblemDiagramLine old:new1List) {
			String flag = "nofind";
			for(ProblemDiagramLine new1:oldList) {
				if(new1.getNo()==old.getNo()) {					
						flag = "find";
				}
			}
			if(flag.contentEquals("nofind")) {
				messages.add(getChangeMessage("delete","int",old,null));
			}
		}
		return messages;
	}

	public boolean equal(List<Phenomenon> oldList,List<Phenomenon> newList) {
		return true;
	}
	public boolean equal1(List<RequirementPhenomenon> oldList,List<RequirementPhenomenon> newList) {
		return true;
	}
	public <T> String getChangeMessage (String type,String shape,T old, T new1) {
		JSONObject json = new JSONObject();
		json.put("type",type);
		json.put("title",getTitle());
		json.put("version",getVersion());
		json.put("id",id++);
		json.put("old",old);
		json.put("new", new1);
		json.put("to","diagram");
		return json.toJSONString();
		
	}

//	public String getProject() {
//	return project;
//}
//public void setProject(String project) {
//	this.project = project;
//}
//	private Project project=new Project();
//	private PFText pf_text;	

//	public Project getProject() {
//		return project;
//	}		
//	public void setProject(Project project) {
//		this.project = project;
//	}
//	public PFText getPf_text() {
//		return pf_text;
//	}
//	public void setPf_text(PFText pf_text) {
//		this.pf_text = pf_text;
//	}
	
}
