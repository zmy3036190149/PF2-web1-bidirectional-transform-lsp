package com.example.demo.bean;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


import javax.websocket.Session;
public abstract class Subject {
	private String title;
	private String version;	
	private String editorType;	
	protected int id;
	protected CopyOnWriteArraySet<Observer> observerSet = new CopyOnWriteArraySet<Observer>();
	
	public Subject(String title, String version,String editorType, Observer observer) {
		super();
		this.title = title;
		this.version = version;
		this.editorType = editorType;
		this.observerSet.add(observer);
	}
	
	public  void attach(Observer observer) {
		observerSet.add(observer);
	}
	public  void detach(Session session) {
		for(Observer o:observerSet) {
			if(o.session==session) {
				observerSet.remove(o);
			}
		}
	}
	public  void detach(Session session,String from) {
		for(Observer o:observerSet) {
			if(o.session==session && o.editorType==from) {
				observerSet.remove(o);
			}
		}
	}
	public abstract void setValue(String message);
	public abstract String transfer(String message);
	public String getTitle() {
		return title;
	}
	public void setTitle(String titel) {
		this.title = titel;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEditorType() {
		return editorType;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEditorType(String content) {
		this.editorType = content;
	}
	public CopyOnWriteArraySet<Observer> getObserverSet() {
		return observerSet;
	}
	public void setObserverSet(CopyOnWriteArraySet<Observer> observerSet) {
		this.observerSet = observerSet;
	}
}
