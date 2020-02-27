package com.example.demo.bean;

import java.util.List;

import javax.websocket.Session;

import com.github.gumtreediff.tree.ITree;
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
	public abstract void update(ITree org,ITree old);
	public abstract void change(String message);
	public Session getSession() {
		return this.session;
	}
	public abstract void setProject(ProblemDiagram problemDiagram);
	public abstract void setReferenceList(List<Reference> referenceList);
	public abstract void setConstraintList(List<Constraint> constraintList);
	public abstract void setRequirementList(List<Requirement> requirementList);
	public abstract void deleteFile();
}