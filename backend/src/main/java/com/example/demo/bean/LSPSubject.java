package com.example.demo.bean;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


import javax.websocket.Session;
public abstract class LSPSubject {
	private String uri;
	private String editorType;	
	protected int id;
	protected CopyOnWriteArraySet<LSPObserver> observerSet = new CopyOnWriteArraySet<LSPObserver>();
	
	public LSPSubject(String uri,String editorType, LSPObserver observer) {
		super();
		this.uri = uri;
		this.editorType = editorType;
		this.observerSet.add(observer);
	}
	
	public  void attach(LSPObserver observer) {
		observerSet.add(observer);
	}
	public  void detach(Session session) {
		for(LSPObserver o:observerSet) {
			if(o.session==session) {
				observerSet.remove(o);
			}
		}
	}
	public abstract void setValue(String message);
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
}
