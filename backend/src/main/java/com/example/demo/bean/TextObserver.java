package com.example.demo.bean;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.WebSocket;
public class TextObserver extends Observer {
//	String text;
	public TextObserver(String title,String version,Session session) {
		super(session,title,version,"text");
	}
	public TextObserver(Session session) {
		super(session,"text");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
		System.out.print("textobserver update		");
		JSONObject json = JSONObject.parseObject(message); 
		String from = (String) json.get("from");	
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
//		ArrayList<String> messages = new ArrayList<String>();
//		String message = "";
		if(from.contentEquals("diagram")){
			//转换project 为 pf
			System.out.println("转换project 为 pf");
//			json.put("to","text");
//			json.put("new", text);
//			message = json.toJSONString();
			for(Subject subject:WebSocket.getSubjectSet()) {
				if(subject.getTitle().contentEquals(title) && subject.getVersion().contentEquals(version) && subject.getEditorType().contentEquals(editorType)) {
					message = subject.transfer(message);
					break;
				}
			}
		}
		//向客户端发送消息
		try {			
			System.out.println(message);
			session.getBasicRemote().sendText(message);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
