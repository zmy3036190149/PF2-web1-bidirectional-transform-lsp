package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import com.example.demo.service.Forward;
//import com.google.gson.Gson;
import com.example.demo.service.TailLogThread;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/LSP")
@RestController
public class LSP {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<LSP> LSPSet = new CopyOnWriteArraySet<LSP>();
	//每个项目的每种编辑器对应一个subject
	private static CopyOnWriteArraySet<Forward> forwardSet = new CopyOnWriteArraySet<Forward>();

	//单例
	private static class SingletonHolder{
  	     private static LSP instance = new LSP();
    }
   	public static  LSP getInstance(){
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
		LSPSet.add(this);     //加入set中
		forwardSet.add(new Forward(session));
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){		
		LSPSet.remove(this);  
		for(Forward lsp: forwardSet) {
			if(this.session==lsp.getSession()) {
				lsp.getP().destroy();
				lsp.getThread().stopThread();
				forwardSet.remove(lsp);
			}
		}
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		message = "Content-Length: "+ message.length() + "\r\n\r\n"+ message+"\r";
//		System.out.println(message);
		//转发
		for(Forward lsp: LSP.forwardSet) {
			if(session==lsp.getSession()) {
				lsp.forward(message);	
			}
		}
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for(Forward lsp: LSP.forwardSet) {
//			if(session==lsp.getSession()) {
//				message= "{\"jsonrpc\":\"2.0\",\"method\":\"initialized\",\"params\":{}}";
//				message = "Content-Length: "+ message.length() + "\r\n\r\n"+ message+"\r";		
//				lsp.forward(message);
//				
//			}
//		}

	}
}