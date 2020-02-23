package com.example.demo.bean;

import javax.websocket.Session;
public abstract class LSPObserver {
	protected Session session;
	protected String editorType;
	protected String uri;
	public LSPObserver(Session session,String editorType) {
		super();
		this.session = session;
		this.editorType = editorType;
	}
	public LSPObserver(Session session,String uri,String editorType) {
		super();
		this.session = session;
		this.uri = uri;
		this.editorType = editorType;
	}
	public abstract void update(String message);
	public abstract void change(String message);
	public Session getSession() {
		return this.session;
	}
}