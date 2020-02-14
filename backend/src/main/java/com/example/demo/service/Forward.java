package com.example.demo.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import javax.websocket.Session;

import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
public class Forward {
	Session session;//client
	Process p;
	BufferedWriter bw;
	BufferedReader br;
	public Forward(Session session) {
		super();
		this.session = session;
		Runtime run = Runtime.getRuntime();
		String cmd = "java -jar E:/pf-language-server.jar";
//		String cmd = "ipconfig -all";
		try {
			p = run.exec(cmd);
	        // write to child process
	        bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
	        InputStream inputStream = p.getInputStream();
			TailLogThread thread = new TailLogThread(inputStream, session);
			thread.start();
			System.out.println("thread.Id = "+thread.getId());
//	        BufferedInputStream in = new BufferedInputStream(p.getInputStream());
//	        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void forward(String message) {
		try {
//			System.out.println(" forward "+ message);
			bw.write(message);
			bw.flush();

//			System.out.println(" finish forward ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
}
