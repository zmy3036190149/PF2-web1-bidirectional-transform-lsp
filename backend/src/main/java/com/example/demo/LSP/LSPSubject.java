package com.example.demo.LSP;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;

import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileOperation;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

public class LSPSubject {

	ASTService astService = new ASTService();

	private String uri;
	private String editorType;
	protected int id;
	protected String rootAddress;
	protected String oldPath;
	protected String text_oldPath;
	protected String diagram_oldPath;

	protected TreeContext orgContext;
	protected TreeContext oldContext;
	protected TreeContext locContext;

	protected ITree orgTree = null;
	protected ITree oldTree = null;
	protected ITree locTree = null;

	protected TreeContext text_orgContext;
	protected TreeContext text_oldContext;
	protected TreeContext text_locContext;

	protected ITree text_orgTree = null;
	protected ITree text_oldTree = null;
	protected ITree text_locTree = null;

	protected TreeContext diagram_orgContext;
	protected TreeContext diagram_oldContext;
	protected TreeContext diagram_locContext;

	protected ITree diagram_orgTree = null;
	protected ITree diagram_oldTree = null;
	protected ITree diagram_locTree = null;
	protected CopyOnWriteArraySet<LSPObserver> observerSet = new CopyOnWriteArraySet<LSPObserver>();

	public LSPSubject(String uri, LSPObserver observer) {
		super();
		this.uri = uri;
		this.observerSet.add(observer);
		rootAddress = AddressService.lastestProjectAddress + uri + "/";
		text_oldPath = rootAddress + "old.pf";
		diagram_oldPath = rootAddress + "old.xml";
	}

	@Deprecated
	public LSPSubject(String uri, String editorType, LSPObserver observer) {
		super();
		this.uri = uri;
		this.editorType = editorType;
		this.observerSet.add(observer);
		rootAddress = AddressService.lastestProjectAddress + uri + "/";
	}

	@Deprecated
	public void attach_old(LSPObserver observer) {
		observerSet.add(observer);
		if (orgTree != null && oldTree != null) {
			observer.setTree(orgTree, oldTree);
			observer.notifyClient();
		}
		System.out.println(observer.getClass().getSimpleName() + " " + observer.getSession().getId() + " attach to "
				+ this.getClass().getSimpleName() + this.uri);
		System.out.println(this.observerSet);
	}

	public void detach(Session session) {
		for (LSPObserver o : observerSet) {
			if (o.session == session) {
				observerSet.remove(o);
			}
		}
		System.out.println(session.getId() + " detach from " + getUri());
		System.out.println(this.observerSet);
	}

	public void attach(LSPObserver observer) {
		observerSet.add(observer);
		if (text_orgTree != null && text_oldTree != null && diagram_orgTree != null && diagram_oldTree != null) {
			observer.setTree(text_orgTree, text_oldTree, diagram_orgTree, diagram_oldTree);
			observer.notifyClient();
		}
		System.out.println(observer.getClass().getSimpleName() + " " + observer.getSession().getId() + " attach to "
				+ this.getClass().getSimpleName() + this.uri);
		System.out.println(this.observerSet);
	}

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

	// public abstract void setValue(String message);

	// public abstract String transfer(String message);

	// public void setValue(ITree org, ITree old) {
	// // TODO Auto-generated method stub
	//
	// }
	// public void setValue(String type,ITree org, ITree old) {
	// // TODO Auto-generated method stub
	//
	// }

	@Deprecated
	public void setValue(String type, ITree org, ITree old, Session session) {
	}

	public void setValue(ITree diagram_org, ITree diagram_old, ITree text_org, ITree text_old, Session session) {
		// 生成 old.pf (for test)
		ASTService.generatePfFile(text_old, rootAddress, text_oldPath);
		// 生成 old.xml (for test)
		ASTService.generateXmlFile(diagram_old, diagram_oldPath);
		diagram_orgTree = diagram_org;
		diagram_oldTree = diagram_old;
		text_orgTree = text_org;
		text_oldTree = text_old;
		notifyObserver(text_org, text_old, diagram_org, diagram_old, session);

	}

//	@Deprecated
//	public void notifyObserver(String type, ITree org, ITree old, Session session) {
//		for (LSPObserver observer : getObserverSet()) {
//			observer.update(type, org, old, session);
//		}
//	}

	public void notifyObserver(ITree text_org, ITree text_old, ITree diagram_org, ITree diagram_old, Session session) {
		for (LSPObserver observer : getObserverSet()) {
			observer.update(text_org, text_old, diagram_org, diagram_old, session);
		}
	}

	public void deleteFile() {
		FileOperation fileOperation = new FileOperation();
		fileOperation.DeleteFolder(rootAddress);
	}
}
