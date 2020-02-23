package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
 
import javax.websocket.Session;

public class TailLogThread extends Thread {

	private BufferedReader reader;
	private Session session;
	
	public TailLogThread(InputStream in, Session session) {
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.session = session;
		
	}
	
	@Override
	public void run() {
		String line;
		try {
			String s = "";
			int i = 0;
			int len=0;
			line = reader.readLine();
			if(line != null && line.contains("Content-Length:")) {
				String slen = line.substring(line.indexOf("Content-Length: ")+16);
				System.out.println(slen);
				len = Integer.parseInt(slen);
				reader.readLine();
			}
			
			while(true) {
				//读取内容
				char cbuf[] = new char[len];
				reader.read(cbuf, 0, len);				
				s = new String(cbuf);
				if(!s.contentEquals("")) {
					try {
						session.getBasicRemote().sendText(s);						
					}catch (IOException e) {
						System.out.println("error run");
						e.printStackTrace();
						return;
					}
//					System.out.print("====Send to client====");
//					System.out.print(s);
//					System.out.println("====have sent to client=====");
				}
				//Content-Length
				line = reader.readLine();
				if(line!=null && line.contains("Content-Length:")) {
					String slen = line.substring(line.indexOf("Content-Length: ")+16);
//					System.out.println(slen);
					len = Integer.parseInt(slen);
					reader.readLine();
				}								
			}
		} catch (IOException e) {
			System.out.println("error run");
			e.printStackTrace();
		}
	}
}
