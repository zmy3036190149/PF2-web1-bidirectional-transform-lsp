package com.example.demo.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.websocket.Session;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.WebSocket;
//import com.example.demo.service.AddressService;
import com.example.demo.service.ProblemEditor;

public class DiagramObserver extends Observer {

	public DiagramObserver(Session session) {
		super(session,"diagram");
		// TODO Auto-generated constructor stub
	}
	public DiagramObserver(String title,String version,Session session) {
		super(session,title,version,"diagram");
		// TODO Auto-generated constructor stub
	}
	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
		System.out.print("diagramobserver update		");
		JSONObject json = JSONObject.parseObject(message); 
		String from = (String) json.get("from");	
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
		ArrayList<String> messages = new ArrayList<String>();
		
		
		if(from.contentEquals("text")){	
			boolean error = (boolean) json.get("error");
			if(error)
				return;
			// pf -> project
			for(Subject subject:WebSocket.getSubjectSet()) {
				if(subject.getTitle().contentEquals(title) && subject.getVersion().contentEquals(version) && subject.getEditorType().contentEquals("diagram")) {
					message = subject.transfer(message);
					break;
				}
			}
			System.out.println("============     from = text		============");
		}else if(from.contentEquals("diagram")){			
			json.put("to","diagram");
			message = json.toJSONString();
			messages.add(message);
			System.out.println("============     from = diagram		============");
		}
		try {		
			System.out.println(message);				
			session.getBasicRemote().sendText(message);				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
