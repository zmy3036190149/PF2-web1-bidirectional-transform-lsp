package com.example.demo.bean;
import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileService;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;
public class LSPTextSubject extends LSPSubject{	

	@Autowired	//自动装配
	FileService fileService;
	private Project project = null;

	private String lastestProjectAddress = AddressService.lastestProjectAddress;
	String rootAddress = lastestProjectAddress + getUri() +"/";
    String oldPath =rootAddress + "old.pf";//原始版

	public LSPTextSubject(String uri, LSPObserver observer) {
		super(uri,"text",observer);
		System.out.println("new LSPTextSubject,uri = "+ uri);
	}
	public LSPTextSubject(String uri, Project project,LSPObserver observer) {
		super(uri,"text",observer);
		this.project = project;
		System.out.println("new LSPTextSubject,uri = "+ uri);
	}
//	@Override
//	public void setValue(String message) {
//		notifyObserver(message);
//	}
//	@Override
//	public void setValue(ITree org,ITree old) {
//		//生成 old.pf
//		astService.generatePf(old,oldPath);
//		orgTree = org.deepCopy();
//		oldTree = old.deepCopy();
//		notifyObserver(org, old);
//		
//	}
//	@Override
//	public void setValue(String type, ITree org,ITree old) {
//		//生成 old.pf
//		if(type.contentEquals("text")) {
//			astService.generatePf(old,oldPath);
//			orgTree = org.deepCopy();
//			oldTree = old.deepCopy();
//			notifyObserver(type, org, old);
//		}		
//	}
	@Override
	public void setValue(String type, ITree org,ITree old,Session session) {
		if(type == "text") {
			//生成 old.pf
			astService.generatePf(old,oldPath);
			notifyObserver(type, org, old,session);
		}
	}
//	public void notifyObserver(String message){
//		System.out.println("observer数目：		"+getObserverSet().size());
//		for(LSPObserver observer: getObserverSet()) {
//			observer.update(message);
//		}
//	}	
//	public void notifyObserver(ITree org,ITree old){
//		System.out.println("observer数目：		"+getObserverSet().size());
//		for(LSPObserver observer: getObserverSet()) {
//			observer.update(org, old);
//		}
//	}
//	public void notifyObserver(String type, ITree org,ITree old){
//		System.out.println("observer数目：		"+getObserverSet().size());
//		for(LSPObserver observer: getObserverSet()) {
//			observer.update(type, org, old);
//		}
//	}
	public void notifyObserver(String type, ITree org,ITree old,Session session){
		System.out.println("LSPTextSubject "+getUri()+"observer数目：		"+getObserverSet().size());
		super.notifyObserver(type, org, old, session);
	}
}
