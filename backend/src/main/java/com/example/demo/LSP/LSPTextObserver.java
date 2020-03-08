package com.example.demo.LSP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.CompositeNode;
import org.eclipse.xtext.nodemodel.impl.CompositeNodeWithSemanticElement;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.nodemodel.impl.LeafNode;
import org.eclipse.xtext.nodemodel.impl.RootNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.springframework.beans.factory.annotation.Autowired;

import com.alexander.solovyov.TreeDifferences;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.LSP.bean.DiagramContentChangeEvent;
import com.example.demo.LSP.bean.LineInfo;
import com.example.demo.bean.Constraint;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.controller.WebSocket;
import com.example.demo.service.ASTService;
import com.example.demo.service.AddressService;
import com.example.demo.service.FileService;
//import com.example.demo.service.AddressService;
import com.example.demo.service.ProblemEditor;
import com.example.demo.service.TransXML;
import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.actions.model.Delete;
import com.github.gumtreediff.actions.model.Insert;
import com.github.gumtreediff.actions.model.Move;
import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.gen.antlr3.xml.XmlTreeGenerator;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.AbstractTree;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TimeTree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;
import com.github.gumtreediff.tree.TreeUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import pf.PfStandaloneSetup;

import com.github.gumtreediff.actions.model.*;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.AbstractTree;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class LSPTextObserver extends LSPObserver {

	@Autowired	//自动装配
	FileService fileService = new FileService();
	ASTService astService = new ASTService();
	private String pf = null;
	
    //==========================初始化====================
    public LSPTextObserver(Session session,String uri,String pf) {
		super(session,uri,"text");
		this.pf = pf;
	    orgPath =rootAddress + "org.pf";//原始版
	    locPath =rootAddress + "loc.pf";//客户端传来的最新版
	    oldPath =rootAddress + "old.pf";//合成的最新版
	    mergePath =rootAddress + "merge.pf";//合并版

		long time = System.currentTimeMillis();		
	    generateOrgAST(time);
	    generateOldAST(time);
	    
		System.out.println("new LSPTextObserver		");
	}
	
	//根据pf文件生成AST
	public TreeContext generateAST(String filePath) {
		return ASTService.generateAST(filePath);		
	}

	//save lastest pf to lastestProject/{uri}/loc.pf	
	public void saveLastestData() {
		File dir = new File(rootAddress);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		writeToFile(pf,locPath);
		
	}

	public static void writeToFile(String str,String path) {
		try {
			File writename = new File(path); 
			if(writename.exists())
				writename.delete();
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(str); 
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTree(ITree org,ITree old) {
		super.setTree(org, old);
		pf = astService.getPf(old);
	}
	
	//========================change=====================
	public void change(String pf){
		
		this.pf = pf;
		
		//从pf文件读取locAST,并根据当前时间和orgAST和oldAST节点时间，生成带有修改时间的locAST
		changeOldAST();
	    
		//生成新的 old.pf for test
		astService.generatePf(oldTree,oldPath);
		
		//发送给subject
		for(LSPSubject subject:LSPSubjects.getSubjectSet()) {
			if(subject.getUri().contentEquals(uri) ) {
				subject.setValue("text",orgTree, oldTree,session);
			}else {
				System.out.println(subject.getId() + " != " + uri);
			}
		}
		//set new orgTree
		orgTree = oldTree.deepCopy();
	}

	//================================merge======================

	@Override
	public void update(String type, ITree org, ITree old) {
		if(type.contentEquals("text")) {
			System.out.println( session.getId() + " update old ast since another editor change===================");
			update( org,  old);
			
		    //generate merge.pf
			astService.generatePf(oldTree,mergePath);
			
			//生成新的pf		
			pf = astService.getPf(oldTree);
			
			//发送给客户端
			notifyClient();	
			System.out.println(" update end ========================================");
		}
	}
	
	//send lastest pf when registed or merge
	public void notifyClient(){
		//通知客户端
		JSONObject message = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject textDocument = new JSONObject();
		
		textDocument.put("uri",uri);
		textDocument.put("version",0);
		
		params.put("textDocument",textDocument);
		params.put("text",pf);

        message.put("jsonrpc", "2.0");
        message.put("id",0); 
        message.put("method","TextDocument/didChange");
        message.put("params",params);
        session.getAsyncRemote().sendText(JSON.toJSONString(message)); 
	}

	/////////////////////////////////////////////////////////////////
	@Override
	public void setProject(ProblemDiagram problemDiagram) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setReferenceList(List<Reference> referenceList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setConstraintList(List<Constraint> constraintList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRequirementList(List<Requirement> requirementList) {
		// TODO Auto-generated method stub
		
	}
}
