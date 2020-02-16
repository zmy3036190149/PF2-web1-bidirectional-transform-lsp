package com.example.demo.bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.websocket.Session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.ProblemEditor;

public class TextSubject extends Subject{

	String text;
	String diagramMessage;
	ArrayList<String> textMessages = new ArrayList<String>();
	String textMessage = "";
	public TextSubject(String title, String version, String text, Observer observer) {
		super(title, version, "text", observer);
		System.out.println("new TextSubject,title=:	"+title);
		String filename = "E:/test-data/"+title+version+".pf";
		File testFile = new File(filename);
        if (!testFile.exists()) {
            try {
				testFile.createNewFile();
				FileWriter fw = new FileWriter(testFile);
				fw.write(text);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		this.text = text;
		registered( observer);	
		
		
		// TODO Auto-generated constructor stub
	}
	//将observer添加到observerSet，发送registered
	public void attach(Observer observer) {
		super.attach(observer);
		if(observer.editorType.contentEquals("diagram")) {
			System.out.println("text subject attach(diagram observer)	title: "+getTitle());
		}else {
			System.out.println("text subject attach(diagram observer)	title: "+getTitle());
			//发送registered
			registered( observer);
		}		
	}
	public void registered(Observer observer) {
		try {
			JSONObject message = new JSONObject();
			message.put("type","registered");
			message.put("text", text);
			message.put("from", "text");
			message.put("id", getId());
			observer.session.getBasicRemote().sendText(message.toJSONString());									
//			System.out.println("注册时发送最新版:"+message.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setValue(String message) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.parseObject(message); 
		String title = (String) json.get("title");	
		String version = (String) json.get("version");	
		String from = (String) json.get("from");	
		String new1 = (String) json.get("text");			
		if(from.contentEquals("diagram"))
			return;
		updateText(new1);
		String filename = "E:/test-data/"+title+version+".pf";
		File testFile = new File(filename);
        if (!testFile.exists()) {
            try {
				testFile.createNewFile();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        FileWriter fw;
		try {
			fw = new FileWriter(testFile);
			fw.write(new1);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		notifyObserver(message);
	}
	public void updateText(String newText) {
		System.out.println("updateText, id = " + id + "newText=" + newText);
		text = newText;
		id ++;
	}
	void notifyObserver(String message){
		//修改id
		JSONObject json = JSONObject.parseObject(message); 
		json.put("id", id++);
		message = json.toJSONString();
		for(Observer observer: getObserverSet()) {
				observer.update(message);					
		}
	}
	@Override
	public String transfer(String message) {
		// TODO Auto-generated method stub
		if(diagramMessage!=null && diagramMessage.contentEquals(message)) {
			return textMessage;
		}
		//project -> text	
		diagramMessage = message;
		JSONObject json = JSONObject.parseObject(message); 
		String type = (String) json.get("type");	
	    switch(type){
	      case "add": 
	        add(json);
	        break;
	      case "delete": 
	        delete(json);
	        break;
	      case "change":
	        change(json);
	        break;
	    }
	    json.put("text", text);
	    json.put("to", "text");
	    textMessage = json.toJSONString();
		return textMessage;
	}
	//===========================add===========================
	public void add(JSONObject json) {
		String shape = (String) json.get("shape");	
		JSONObject jshape = json.getJSONObject("new");
		String line = "";
		switch(shape) {
		case"mac":
			 line = getMachineLine(jshape);
			 break;
	     case "pro":
	    	 line = getProblemDomainLine(jshape);
	    	   break;
	      case "req":
	    	  line = getRequirementLine(jshape);
	    	   break;
	      case "int":
	    	  line = getInterfaceLine(jshape);
	    	  break;
	      case "ref":
	    	  line = getReferenceLine(jshape,"~~");
	    	  break;
	      case "con":
	    	  line = getConstraintLine(jshape,"~>");
	         break;
		}
		text += line+"\n";
		
	}
	public String getMachineLine(JSONObject jshape ) {
		String shortname = jshape.getString("shortname");
		String sType = " M";
		String name = jshape.getString("name");
		String line = shortname + sType + " \"" + name + "\"";
		return line;
	}
	public String getProblemDomainLine(JSONObject jshape ) {		
		String shortname = jshape.getString("shortname");
		String sType ="";
		switch(jshape.getString("type")) {
		 	case "Biddable": sType = " B";break;
		 	case "Causal": sType = " C";break;
		 	case "Lexical": sType = " X";break;		
		 }
		 String name = jshape.getString("name");
		 String line = shortname + sType + " \"" + name + "\"";
		return line;
	}
	public String getRequirementLine(JSONObject jshape) {
		String shortname = "";
  		String sType = "";
  		String name ="";
  		String line = "";
		 shortname = jshape.getString("shortname");
		 sType = " R";
		 name = jshape.getString("name");	
		 line = shortname + sType +  " \"" + name + "\"";  
		 return line;
	}
	public String getInterfaceLine(JSONObject jshape,String lineType) {
		String from = jshape.getString("from");
  		String to =jshape.getString("to");
  		String lineName = jshape.getString("name");
		JSONArray phe = jshape.getJSONArray("phenomenonList");
		String line = from + lineType + to + getpheLine(phe) + " \"" + lineName + "\"";
		return line;		 
	}
	public String getInterfaceLine(JSONObject jshape) {
		String from = "";
  		String lineType = "";
  		String to ="";
  		String lineName = "";
		JSONArray phe ;  	
		String line = "";
  		from = jshape.getString("from");
  	  lineType = " -- ";
  	  to = jshape.getString("to");
  	  lineName = jshape.getString("name");
  	  phe = jshape.getJSONArray("phenomenonList");
		  line = from + lineType + to + getpheLine(phe) + " \"" + lineName + "\"";
		  return line;
		 
	}
	public String getReferenceLine(JSONObject jshape, String lineType) {
		String from = "";
  		String to ="";
  		String lineName = "";
		JSONArray phe ;  	
		String line = "";
		from = jshape.getString("from");
  	  
  	  to = jshape.getString("to");
  	  lineName = jshape.getString("name");
  	  phe = jshape.getJSONArray("phenomenonList");
  	  line = from + lineType + to + getpheLine(phe) +" \"" + lineName + "\"";
		  return line;
		 
	}
	public String getConstraintLine(JSONObject jshape,String lineType) {
		String from = "";
  		String to = "";
  		String lineName = "";
		JSONArray phe ;  	
		String line = "";
		from = jshape.getString("from");
  	  to = jshape.getString("to");
  	  lineName = jshape.getString("name");
  	  phe = jshape.getJSONArray("phenomenonList");
  	  line = from + lineType + to + getpheLine(phe) + " \"" + lineName + "\"";
		  return line;
		 
	}
	public String getpheLine(JSONArray pheList) {
		String str=" {";
		for(int i =0; i<pheList.size();i++) {
			JSONObject phe = pheList.getJSONObject(i);
			String type = phe.getString("phenomenon_type");
			String name = phe.getString("phenomenon_name");
			str += type + " " + name + ", ";
		}
		str = str.substring(0,str.length()-2);
		if(str.length()>0) {
			str += "}";
		}
		return str;		
	}

	//======================delete=========================
	public void delete(JSONObject json) {
		String shape = (String) json.get("shape");	
		JSONObject jshape = json.getJSONObject("old");
		String shortname = "";
		String sType = "";
		String name ="";
		String from ="";
		String lineType ="";
		String to ="";
		String lineName = "";
		String line = "";
		switch(shape) {
		case"mac":
			 shortname = jshape.getString("shortname");
			 sType = " M";
			 name = jshape.getString("name");
			 break;
	     case "pro":
	    	 shortname = jshape.getString("shortname");
			 name = jshape.getString("name");
	    	   break;
	      case "req":
		    	 shortname = jshape.getString("name");
				 name = jshape.getString("name");
	    	   break;
	      case "int":
	    	  from = jshape.getString("from");
	    	  to = jshape.getString("to");
	    	  lineName = jshape.getString("name");
	    	  break;
	      case "ref":
	    	  from = jshape.getString("from");
	    	  lineType = "~~";
	    	  to = jshape.getString("to");
	    	  lineName = jshape.getString("name");
	    	  break;
	      case "con":
	    	  from = jshape.getString("from");
	    	  lineType = "~>";
	    	  to = jshape.getString("to");
	    	  lineName = jshape.getString("name");
	         break;
		}
		 String[] lines=text.split("\n");
		 String res = "";
		 for(int i = 0; i<lines.length; i++) {
			 //s1[1] 描述
			 line = lines[i];
			 String[] s1=line.split("\"");
			 String sname="";
			 if(s1.length>1) {				
				 sname = s1[1];
			 } 
			if(s1[0].indexOf(" M ")!=-1&&shape.contentEquals("mac")){
				 String[] s2 = s1[0].split(" M ");
				 if(sname.contentEquals(name)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf(" R ")!=-1 && shape.contentEquals("req")){
				 String[] s2 = s1[0].split(" R ");
				 if(sname.contentEquals(name)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf(" D ")!=-1 || s1[0].indexOf(" C ")!=-1 || s1[0].indexOf(" B ")!=-1 || s1[0].indexOf(" X ")!=-1){
				 String type = "";
				 if(s1[0].indexOf(" D ")!=-1) {
					 type ="D";
				 }else if(s1[0].indexOf(" C ")!=-1) {
					 type ="C";
				 }else if(s1[0].indexOf(" B ")!=-1) {
					 type ="B";
				 }else if(s1[0].indexOf(" X ")!=-1) {
					 type ="X";
				 }
				 String[] s2 = s1[0].split(" "+type+" ");
				 if(sname.contentEquals(name)) {
					 line = "";
				 }				 
			 }else if(s1[0].indexOf("--")!=-1 && shape.contentEquals("int")) {
				 //System.out.println(line);				 
				 String[] s2 = s1[0].split("--");
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();	
				 if(from.contentEquals(sfrom) && to.contentEquals(sto)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf("~~")!=-1 && shape.contentEquals("ref")) {
				 System.out.println("reference");
				 String[] s2 = s1[0].split("~~"); 
				 String sfrom = s2[0].trim(); 
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();
				 if(from.contentEquals(sfrom) && to.contentEquals(sto)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf("~>")!=-1 && shape.contentEquals("con")) {
				 System.out.println("constraint");
				 String[] s2 = s1[0].split("~>");
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();			
				 if(from.contentEquals(sfrom) && to.contentEquals(sto)) {
					 line = "";
				 }
			 }		
			 res += line+"\n";				 
		 }
//		 System.out.println(res);
		 text = res;
		 switch(shape) {
		 case "mac":
		 case "pro":
		 case "req":
			 deleteRelatedLine(shortname);
		 }

		
	}
	public void deleteRelatedLine(String shortname) {
		System.out.println("deleteRelatedLine		"+ shortname );
		 String[] lines=text.split("\n");
		 String res = "";
		 for(int i = 0; i<lines.length; i++) {
			 //s1[1] 描述
			 String line = lines[i];
			 String[] s1=line.split("\"");
			 String sname="";
			 if(s1.length>1) {				
				 sname = s1[1];
			 } 
			if(s1[0].indexOf("--")!=-1) { 
				 String[] s2 = s1[0].split("--");
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();	
				 if(shortname.contentEquals(sfrom) || shortname.contentEquals(sto)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf("->")!=-1) {		 
				 String[] s2 = s1[0].split("->");
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();	
				 if(shortname.contentEquals(sfrom) && shortname.contentEquals(sto)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf("~~")!=-1) {
				 String[] s2 = s1[0].split("~~"); 
				 String sfrom = s2[0].trim(); 
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();
				 if(shortname.contentEquals(sfrom) && shortname.contentEquals(sto)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf("~>")!=-1 ) {
				 String[] s2 = s1[0].split("~>");
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();			
				 if(shortname.contentEquals(sfrom) && shortname.contentEquals(sto)) {
					 line = "";
				 }
			 }else if(s1[0].indexOf("<~")!=-1 ) {
				 String[] s2 = s1[0].split("<~");
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();			
				 if(shortname.contentEquals(sfrom) && shortname.contentEquals(sto)) {
					 line = "";
				 }
			 }	
			 res += line+"\n";				 
		 }
//		 System.out.println(res);
		 text = res;

	}
	//===========================change===================
	public void change(JSONObject json) {
		//遍历pf文件，找到，对应行，针对节点和线分别处理
		String shape = (String) json.get("shape");	
		JSONObject old = json.getJSONObject("old");
		JSONObject new1 = json.getJSONObject("new");
		String shortname = "";
		String sType = "";
		String name ="";
		String from ="";
		String lineType ="";
		String to ="";
		JSONArray phe ;
		String lineName = "";
		String line = "";
		String newShortName = "";
		String newName = "";
		switch(shape) {
		case"mac":
			 shortname = old.getString("shortname");
			 sType = " M";
			 name = old.getString("name");
			 newShortName = new1.getString("shortname");
			 newName = new1.getString("name");
			 break;
	     case "pro":
	    	 shortname = old.getString("shortname");
			 switch(old.getString("type")) {
			 	case "Biddable": sType = "B";break;
			 	case "Causal": sType = "C";break;
			 	case "Lexical": sType = "X";break;		
			 }
			 name = old.getString("name");
	    	 newShortName = new1.getString("shortname");
			 newName = new1.getString("name");
	    	   break;
	      case "req":
		    	 shortname = old.getString("shortname");
		    	 sType = "R";
				 name = old.getString("name");  
		    	 newShortName = new1.getString("shortname");
				 newName = new1.getString("name");
	    	   break;
	      case "int":
	    	  from = old.getString("from");
	    	  to = old.getString("to");
	    	  lineName = old.getString("name");
	    	  break;
	      case "ref":
	    	  from = old.getString("from");
	    	  lineType = "~~";
	    	  to = old.getString("to");
	    	  lineName = old.getString("name");
	    	  break;
	      case "con":
	    	  from = old.getString("from");
	    	  lineType = "~>";
	    	  to = old.getString("to");
	    	  lineName = old.getString("name");
	         break;
		}


		 String[] lines=text.split("\n");
		 String res = "";
		 for(int i = 0; i<lines.length; i++) {
			 //s1[1] 描述
			 line = lines[i];
			 String[] s1=line.split("\"");
			 String sname="";
			 if(s1.length>1) {				
				 sname = s1[1];
			 } 
			if(s1[0].indexOf(" M ")!=-1&&shape.contentEquals("mac")){
				 String[] s2 = s1[0].split(" M ");
				 if(sname.contentEquals(name)) {
					 line = getMachineLine(new1);
				 }
			 }else if(s1[0].indexOf(" R ")!=-1 && shape.contentEquals("req")){
				 String[] s2 = s1[0].split(" R ");
				 if(sname.contentEquals(name)) {
					 line = getRequirementLine(new1);
				 }
			 }else if(s1[0].indexOf(" D ")!=-1 || s1[0].indexOf(" C ")!=-1 || s1[0].indexOf(" B ")!=-1 || s1[0].indexOf(" X ")!=-1){
				 String type = "";
				 if(s1[0].indexOf(" D ")!=-1) {
					 type ="D";
				 }else if(s1[0].indexOf(" C ")!=-1) {
					 type ="C";
				 }else if(s1[0].indexOf(" B ")!=-1) {
					 type ="B";
				 }else if(s1[0].indexOf(" X ")!=-1) {
					 type ="X";
				 }
				 String[] s2 = s1[0].split(" "+type+" ");
				 if(sname.contentEquals(name)) {
					 line = getProblemDomainLine(new1);
				 }
				 
			 }else if(s1[0].indexOf("--")!=-1 ||s1[0].indexOf("->")!=-1 ) {
				 String type = "";
				 if(s1[0].indexOf("--")!=-1)
					 type = "--";
				 else if(s1[0].indexOf("->")!=-1)
					 type = "->";
				 String[] s2 = s1[0].split(type);
				 String sfrom = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();	
				 if(shape.contentEquals("int") && from.contentEquals(sfrom) && to.contentEquals(sto)) {
					//System.out.println(line);		
					line = getInterfaceLine(new1);
				 }
				 if(shape.contentEquals("mac")|| shape.contentEquals("pro")) {
					//change related Link
					 if(sfrom.contentEquals(shortname) && !sfrom.contentEquals(newShortName)) {
						 line = newShortName + " " + type + " " + line.split(type)[1];
						 System.out.println("change related Link");
						 System.out.println(line);
					 }
					 if(sto.contentEquals(shortname) && !sto.contentEquals(newShortName)) {						 
						 String templine = line.split(type)[0].trim() + " " + type + " " + newShortName;
						 String str2 = line.split(type)[1].trim();
						 int index = str2.indexOf(" ");
						 line = templine + str2.substring(index, str2.length());						 
						 System.out.println("change related Link");
						 System.out.println(line);
					 }
				 }
			 }else if((s1[0].indexOf("~~")!=-1 || s1[0].indexOf("~>")!=-1 || s1[0].indexOf("<~")!=-1)) {
				 String type = "";
				 if(s1[0].indexOf("~~")!=-1)
					 type = "~~";
				 else if(s1[0].indexOf("~>")!=-1)
					 type = "~>";
				 else 
					 type = "<~";
				 String[] s2 = s1[0].split(type); 
				 String sfrom = s2[0].trim(); 
				 String[] s3 = s2[1].split("\\{");
				 String sto = s3[0].trim();
				 
				 if(shape.contentEquals("ref")) {
					 System.out.println("reference");
					 if(from.contentEquals(sfrom) && to.contentEquals(sto)) {
						 line = getReferenceLine(new1,type);
					 }
				 }else if(shape.contentEquals("con")) {
					 System.out.println("constraint");
					 if(from.contentEquals(sfrom) && to.contentEquals(sto)) {
						 line = getConstraintLine(new1,type);
					 }
				 }if(shape.contentEquals("pro")||shape.contentEquals("req")) {
					//change related Link
					 if(sfrom.contentEquals(shortname) && !sfrom.contentEquals(newShortName)) {
						 line = newShortName + " " + type + " " + line.split(type)[1];
						 System.out.println("change related Link");
						 System.out.println(line);
					 }
					 if(sto.contentEquals(shortname) && !sto.contentEquals(newShortName)) {
						 String templine = line.split(type)[0].trim() + " " + type + " " + newShortName;
						 String str2 = line.split(type)[1].trim();
						 int index = str2.indexOf(" ");
						 line = templine + str2.substring(index, str2.length());
						 System.out.println("change related Link");
						 System.out.println(line);
					 }
				 }
			 }	
			 res += line+"\n";				 
		 }
//		 System.out.println(res);
		 text = res;
	 }		
}
