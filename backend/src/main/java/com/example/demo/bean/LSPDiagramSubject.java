package com.example.demo.bean;
import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileService;
import com.github.gumtreediff.tree.ITree;
public class LSPDiagramSubject extends LSPSubject{	

	@Autowired	//自动装配
	FileService fileService;
	ASTService astService = new ASTService();
	private Project project = null;
	

	private String lastestProjectAddress = AddressService.lastestProjectAddress;
	String rootAddress = lastestProjectAddress + getUri() +"/";
    String oldPath =rootAddress + "old.xml";//原始版
    
	public LSPDiagramSubject(String uri, LSPObserver observer) {
		super(uri,"diagram",observer);
		System.out.println("new LSPDiagramSubject,uri = "+ uri);
	}
	public LSPDiagramSubject(String uri, Project project,LSPObserver observer) {
		super(uri,"diagram",observer);
		this.project = project;
		System.out.println("new LSPDiagramSubject,uri = "+ uri);
	}

	public void detach(Session session) {
		super.detach(session);
		System.out.println("removeSession");
		System.out.println(this.observerSet);
	}
	@Override
	public void setValue(String message) {

		notifyObserver(message);
	}
	@Override
	public void setValue(ITree org,ITree old) {

		//生成 old.xml
		astService.generateXml(old,oldPath);
		notifyObserver(org, old);
		
	}
	public void notifyObserver(String message){
		System.out.println("observer数目：		"+getObserverSet().size());
		for(LSPObserver observer: getObserverSet()) {
			observer.update(message);
		}
	}	
	public void notifyObserver(ITree org,ITree old){
		System.out.println("observer数目：		"+getObserverSet().size());
		for(LSPObserver observer: getObserverSet()) {
			observer.update(org, old);
		}
	}
}
