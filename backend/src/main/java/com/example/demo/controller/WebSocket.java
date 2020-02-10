package com.example.demo.controller;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Project;
import com.example.demo.bean.DiagramObserver;
import com.example.demo.bean.Subject;
import com.example.demo.bean.DiagramSubject;
import com.example.demo.bean.Observer;
import com.example.demo.bean.TextObserver;
import com.example.demo.bean.TextSubject;
//import com.google.gson.Gson;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/webSocket")
@RestController
public class WebSocket {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

	//每个项目的每种编辑器对应一个subject
	private static CopyOnWriteArraySet<Subject> subjectSet = new CopyOnWriteArraySet<Subject>();

	//单例
	private static class SingletonHolder{
  	     private static WebSocket instance = new WebSocket();
    }
   	public static  WebSocket getInstance(){
   		return SingletonHolder.instance;
   	}
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		webSocketSet.add(this);     //加入set中
		addOnlineCount();           //在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){		
		unregister(this.session);
		webSocketSet.remove(this);  //从set中删除
		subOnlineCount();           //在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {		
		System.out.println("来自客户端的消息长度:	"+message.length());
		if(message.length()<10000)
			System.out.println("来自客户端的消息:	"+ message);
		JSONObject json = JSONObject.parseObject(message); 
		String from = (String) json.get("from");	
		String type = (String) json.get("type");
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
		int id = (int) json.get("id");					
		switch(type) {
		case "register": register(session,json);break;
		case "unregister":unregister(session,json);break;
		case "change":
		case "add":
		case "delete":		
			change(message,title,version,from);
			break;
		default: System.out.println("type="+type+"==");
		}

	}
	void register(Session session,JSONObject json ){
		System.out.println("============register==============");
		String from = (String) json.get("from");	
		String type = (String) json.get("type");
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
		String text="";
		Project project = null;
		Observer observer= null;
		//为每个编辑器创建一个observer对象
		switch(from) {
			case "text": 
				observer = new TextObserver(title,version,session);
				text = (String) json.get("text");
				break;
			case "diagram": 
				observer = new DiagramObserver(title,version,session);
				break;
		}
		//若存在subject则注册
		int flag = 0;
		boolean textFlag = false;
		boolean diagramFlag = false;
		System.out.println(from + " observer register");				
		for(Subject subject: WebSocket.subjectSet) {
			if(title.equals(subject.getTitle()) && version.contentEquals(subject.getVersion())) {
				subject.attach(observer);				
				flag++;
				if(subject.getEditorType()=="text")
					textFlag = true;
				else
					diagramFlag = true;
			}
		}
//		System.out.println("flag = "+flag);
		//若不存在subject则新建subject并注册
		if(flag<2) {
			Subject subject = null;
			switch(from) {
				case "text": 
					if(!textFlag) {
						subject = new TextSubject(title,version,text,observer);
						////添加图形编辑器中的observer
						if(flag>0) attachFromOtherSubject( title, version,subject);
						WebSocket.subjectSet.add(subject);
					}
					break;
				case "diagram": 
					if(!diagramFlag) {
						subject = new DiagramSubject(title,version,project,observer);
						//添加文本编辑器中的observer	
						if(flag>0) attachFromOtherSubject( title, version,subject);
						WebSocket.subjectSet.add(subject);						
					}
					break;						
			}			
		}

		System.out.println("==========================");
	}
	void attachFromOtherSubject(String title,String version,Subject subject){
		for(Subject sub:WebSocket.subjectSet) {
			if(sub.getTitle().contentEquals(title) && sub.getVersion().contentEquals(version)) {
				System.out.println("attachFromOtherSubject	title = "+title+", version = "+version+", sub.getEditorType()="+sub.getEditorType());
				for(Observer ob: sub.getObserverSet()) {
					if(ob.getSession()!= session) {
						subject.attach(ob);
					}
				}
			}
		}
	}
	/**
	 * @param session
	 * @param title
	 * @param version
	 */
	void unregister(Session session,JSONObject json){
		String from = (String) json.get("from");	
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
		for(Subject lsp: WebSocket.subjectSet) {
			if(title.equals(lsp.getTitle()) && version.contentEquals(lsp.getVersion())) {
				lsp.detach(session,from);
				if(lsp.getObserverSet().isEmpty()) {
					WebSocket.subjectSet.remove(lsp);
					System.out.println(lsp.getTitle()+"无连接,删除该元素");
				}
			}
		}
	}
	void unregister(Session session){
		for(Subject lsp: WebSocket.subjectSet) {			
				lsp.detach(session);	
				if(lsp.getObserverSet().isEmpty()) {
					WebSocket.subjectSet.remove(lsp);
					System.out.println(lsp.getTitle()+"无连接,删除该元素");
				}
		}
	}

	public static CopyOnWriteArraySet<Subject> getSubjectSet() {
		return subjectSet;
	}

	public static void setSubjectSet(CopyOnWriteArraySet<Subject> subjectSet) {
		WebSocket.subjectSet = subjectSet;
	}

	void change(String message,String title,String version,String from){
		for(Subject lsp: WebSocket.subjectSet) {
			if(title.equals(lsp.getTitle()) && version.contentEquals(lsp.getVersion()) && from.contentEquals(lsp.getEditorType())) {
				lsp.setValue(message);
			}
		}
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
		//this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocket.onlineCount--;
	}
}