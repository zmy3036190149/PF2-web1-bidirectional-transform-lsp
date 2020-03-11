package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.LSP.LSPObserver;
import com.example.demo.LSP.LSPObservers;
import com.example.demo.LSP.LSPSubject;
import com.example.demo.LSP.LSPSubjects;
import com.example.demo.LSP.LSPTextObserver;
import com.example.demo.LSP.LSPTextSubject;
import com.example.demo.bean.Constraint;
import com.example.demo.bean.ContextDiagram;
import com.example.demo.bean.Interface;
import com.example.demo.bean.Machine;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.ProblemDomain;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/TextLSP")
@RestController
public class TextLSP {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<TextLSP> webSocketSet = new CopyOnWriteArraySet<TextLSP>();

	// 单例
	private static class SingletonHolder {
		private static TextLSP instance = new TextLSP();
	}

	public static TextLSP getInstance() {
		return SingletonHolder.instance;
	}

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		unregister(this.session);
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("TextLSP 有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(session.getId() + " TextLSP onMessage	" + message);
		JSONObject json = JSONObject.parseObject(message);
		String method = (String) json.get("method");
		switch (method) {
		case "TextDocument/didOpen":
			register(session, json);
			break;
		case "TextDocument/didSave":
			change(session, json);
			break;
		default:
			System.out.println("type=" + method + "==");
		}
	}

	@Deprecated
	void register_old(Session session, JSONObject json) {
		System.out.println("============register==============");
		JSONObject params = (JSONObject) json.get("params");
		JSONObject textDocument = (JSONObject) params.get("textDocument");
		String uri = (String) textDocument.get("uri");
		String pf = (String) textDocument.get("text");

		// 为每个编辑器创建一个observer对象
		LSPTextObserver observer = new LSPTextObserver(session, uri, pf);
		LSPObservers.getObserverSet().add(observer);

		// 若存在subject则注册
		boolean isFind = false;
		for (LSPSubject subject : LSPSubjects.getSubjectSet()) {
			if (uri.equals(subject.getUri()) && subject.getEditorType().contentEquals("text")) {
				subject.attach(observer);
				System.out.println(LSPSubjects.getSubjectSet());
				isFind = true;
			}
		}

		// 若不存在subject则新建subject并注册
		if (!isFind) {
			LSPSubject subject = new LSPTextSubject(uri, observer);
			LSPSubjects.getSubjectSet().add(subject);
		}

		System.out.println("==========================");
	}

	@Deprecated
	void register(Session session, JSONObject json) {
		System.out.println("============register==============");
		JSONObject params = (JSONObject) json.get("params");
		JSONObject textDocument = (JSONObject) params.get("textDocument");
		String uri = (String) textDocument.get("uri");
		String pf = (String) textDocument.get("text");

		// Project
		Project project = new Project();
		JSONObject pro = (JSONObject) textDocument.get("project");
		String title = (String) pro.get("title");
		project.setTitle(title);

		// contextDiagram
		ContextDiagram contextDiagram = new ContextDiagram();
		JSONObject jcontextDiagram = (JSONObject) pro.get("contextDiagram");
		title = (String) jcontextDiagram.get("title");
		String smachine = jcontextDiagram.getString("machine");
		Machine machine = JSONObject.parseObject(smachine, Machine.class);

		String spdList = jcontextDiagram.getString("problemDomainList");
		List<ProblemDomain> problemDomainList = (List<ProblemDomain>) JSONObject.parseArray(spdList,
				ProblemDomain.class);
		List<ProblemDomain> pdList = new ArrayList<ProblemDomain>();
		;
		for (ProblemDomain pd : problemDomainList) {
			pdList.add(pd);
		}
		String sintList = jcontextDiagram.getString("interfaceList");
		List<Interface> interfaceList = (List<Interface>) JSONObject.parseArray(sintList, Interface.class);

		contextDiagram.setTitle(title);
		contextDiagram.setMachine(machine);
		contextDiagram.setProblemDomainList(problemDomainList);
		contextDiagram.setInterfaceList(interfaceList);
		project.setContextDiagram(contextDiagram);

		// problemDiagram
		ProblemDiagram problemDiagram = new ProblemDiagram();
		JSONObject jproblemDiagram = (JSONObject) pro.get("problemDiagram");
		title = (String) jproblemDiagram.get("title");
		String sreqList = jproblemDiagram.getString("requirementList");
		List<Requirement> requirementList = (List<Requirement>) JSONObject.parseArray(sreqList, Requirement.class);
		String srefList = jproblemDiagram.getString("referenceList");
		List<Reference> referenceList = (List<Reference>) JSONObject.parseArray(srefList, Reference.class);
		String sconList = jproblemDiagram.getString("constraintList");
		List<Constraint> constraintList = (List<Constraint>) JSONObject.parseArray(sconList, Constraint.class);
		problemDiagram.setTitle(title);
		problemDiagram.setRequirementList(requirementList);
		problemDiagram.setReferenceList(referenceList);
		problemDiagram.setConstraintList(constraintList);
		problemDiagram.setContextDiagram(contextDiagram);
		project.setProblemDiagram(problemDiagram);

		// 为每个编辑器创建一个observer对象
		LSPTextObserver observer = new LSPTextObserver(session, uri, project, pf);
		LSPObservers.getObserverSet().add(observer);

		// 若存在subject则注册
		boolean isFind = false;
		for (LSPSubject subject : LSPSubjects.getSubjectSet()) {
			if (uri.equals(subject.getUri())) {
				subject.attach(observer);
				System.out.println(LSPSubjects.getSubjectSet());
				isFind = true;
			}
		}

		// 若不存在subject则新建subject并注册
		if (!isFind) {
			LSPSubject subject = new LSPSubject(uri, observer);
			LSPSubjects.getSubjectSet().add(subject);
		}

		System.out.println("==========================");
	}

	/**
	 * @param session
	 * @param title
	 * @param version
	 */
	void unregister(Session session, JSONObject json) {
		JSONObject params = (JSONObject) json.get("params");
		JSONObject diagram = (JSONObject) params.get("diagram");
		String uri = (String) diagram.get("uri");
		for (LSPSubject lsp : LSPSubjects.getSubjectSet()) {
			if (uri.equals(lsp.getUri())) {
				lsp.detach(session);
				if (lsp.getObserverSet().isEmpty()) {
					LSPSubjects.getSubjectSet().remove(lsp);
					System.out.println(lsp.getUri() + "无连接,删除该元素");
				}
			}
		}
	}

	void unregister(Session session) {
		for (LSPSubject lsp : LSPSubjects.getSubjectSet()) {
			lsp.detach(session);
			if (lsp.getObserverSet().isEmpty()) {
				LSPSubjects.getSubjectSet().remove(lsp);
				System.out.println(lsp.getUri() + "无连接,删除该元素");
			}
		}
		for (LSPObserver lsp : LSPObservers.getObserverSet()) {
			if (lsp.getSession() == session) {
				lsp.deleteFile();
				LSPObservers.getObserverSet().remove(lsp);
			}
		}
	}

	// old 修改subject
//	void change(String uri,String message){
//		for(LSPSubject lsp: LSPSubjects.getSubjectSet()) {
//			if(uri.equals(lsp.getUri())) {
//				lsp.setValue(message);
//			}
//		}
//	}

	// 修改observer
	void change(Session session, JSONObject json) {
		JSONObject params = (JSONObject) json.get("params");
		JSONObject textDocument = (JSONObject) params.get("textDocument");
		String uri = (String) textDocument.get("uri");
		String pf = (String) params.get("text");
		for (LSPObserver lsp : LSPObservers.getObserverSet()) {
			if (session.equals(lsp.getSession())) {
				lsp.change(pf);
			}
		}
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		TextLSP.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		TextLSP.onlineCount--;
	}
}