package com.example.demo.bean;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


import javax.websocket.Session;

import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileOperation;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;
public abstract class LSPSubject {

	ASTService astService = new ASTService();

	private String uri;
	private String editorType;	
	protected int id;
	protected String rootAddress;

	protected TreeContext orgContext;
	protected TreeContext oldContext;
	protected TreeContext locContext;

	protected ITree orgTree=null;
	protected ITree oldTree=null;
	protected ITree locTree;

	protected CopyOnWriteArraySet<LSPObserver> observerSet = new CopyOnWriteArraySet<LSPObserver>();
	
	public LSPSubject(String uri,String editorType, LSPObserver observer) {
		super();
		this.uri = uri;
		this.editorType = editorType;
		this.observerSet.add(observer);
		rootAddress = AddressService.lastestProjectAddress + uri +"/";
	}
	
	public  void attach(LSPObserver observer) {
		observerSet.add(observer);
		if(orgTree != null && oldTree != null) {
			observer.setTree(orgTree,oldTree);					
			observer.notifyClient();
		}
		System.out.println(observer.getClass().getSimpleName() + " " + observer.getSession().getId()+ " attach to " + this.getClass().getSimpleName() + this.uri);
		System.out.println(this.observerSet);
	}
	
	public  void detach(Session session) {
		for(LSPObserver o:observerSet) {
			if(o.session==session) {
				observerSet.remove(o);
			}
		}
		System.out.println(session.getId()+" detach from "+getUri());
		System.out.println(this.observerSet);
	}
//	public abstract void setValue(String message);
//	public abstract String transfer(String message);

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEditorType() {
		return editorType;
	}
	
	public void setEditorType(String content) {
		this.editorType = content;
	}
	
	public CopyOnWriteArraySet<LSPObserver> getObserverSet() {
		return observerSet;
	}
	
	public void setObserverSet(CopyOnWriteArraySet<LSPObserver> observerSet) {
		this.observerSet = observerSet;
	}

//	public void setValue(ITree org, ITree old) {
//		// TODO Auto-generated method stub
//		
//	}
//	public void setValue(String type,ITree org, ITree old) {
//		// TODO Auto-generated method stub
//		
//	}
	
	public abstract void setValue(String type,ITree org, ITree old,Session session);
	public void notifyObserver(String type, ITree org,ITree old,Session session){
		for(LSPObserver observer: getObserverSet()) {
			observer.update(type,org, old,session);
		}
	}
	public void deleteFile() {
		FileOperation  fileOperation= new FileOperation();
		fileOperation.DeleteFolder(rootAddress);
	}
}
