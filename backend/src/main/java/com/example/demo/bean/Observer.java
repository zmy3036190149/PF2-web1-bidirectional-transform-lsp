package com.example.demo.bean;

import javax.websocket.Session;
public abstract class Observer {
protected Session session;
protected String editorType;
protected String title;
protected String version;

public Observer(Session session,String editorType) {
	super();
	this.session = session;
	this.editorType = editorType;
}
public Observer(Session session,String title,String version,String editorType) {
	super();
	this.session = session;
	this.title = title;
	this.version = version;
	this.editorType = editorType;
}
public abstract void update(String message);
public Session getSession() {
	return this.session;
}
}
