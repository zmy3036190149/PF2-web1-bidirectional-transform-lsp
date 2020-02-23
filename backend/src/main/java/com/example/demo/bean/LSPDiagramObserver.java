package com.example.demo.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.LSP.DiagramContentChangeEvent;
import com.example.demo.LSP.LineInfo;
import com.example.demo.controller.WebSocket;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileService;
//import com.example.demo.service.AddressService;
import com.example.demo.service.ProblemEditor;
import com.example.demo.service.TransXML;

public class LSPDiagramObserver extends LSPObserver {

	@Autowired	//自动装配
	FileService fileService = new FileService();
	private Project project;
	private String lastestProjectAddress = AddressService.lastestProjectAddress;
	public LSPDiagramObserver(Session session,String uri,Project project) {
		super(session,uri,"diagram");
		this.project = project;
		System.out.print("new LSPDiagramObserver		");

	}
	////更新本地project并发送给客户端
	public void change(String message){
		System.out.print("diagramobserver update		");
		//更新本地project
		dealWithMessage(message);
		
		//转为xml并保存（但是没有git操作）
		String rootAddress = lastestProjectAddress + uri +"/";
		File dir = new File(rootAddress);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		fileService.saveProject(rootAddress,uri,project);
		
		//发送给远端
		System.out.println(message);
		session.getAsyncRemote().sendText(message);
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
		String slineInfo = jcontentChange.getString("LineInfo");
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
	    	  for(ProblemDomain pd:project.getContextDiagram().getProblemDomainList()) {
	    		  System.out.println(pd);
	    	  }	    	  
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
				Phenomenon newPhenomenon = JSON.parseObject(snewPhenomenon, Phenomenon.class);
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

}
