package com.example.demo.LSP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Constraint;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.service.ASTService;
import com.example.demo.service.FileService;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

public class LSPTextObserver extends LSPObserver {

	@Autowired // 自动装配
	FileService fileService = new FileService();
	ASTService astService = new ASTService();
	private String pf = null;

	// ==========================初始化====================
	public LSPTextObserver(Session session, String uri, String pf) {
		super(session, uri, "text");
		this.pf = pf;
		orgPath = rootAddress + "org.pf";// 原始版
		locPath = rootAddress + "loc.pf";// 客户端传来的最新版
		oldPath = rootAddress + "old.pf";// 合成的最新版
		mergePath = rootAddress + "merge.pf";// 合并版

		long time = System.currentTimeMillis();
		generateOrgAST(time);
		generateOldAST(time);

		System.out.println("new LSPTextObserver		");
	}

	// 根据pf文件生成AST
	public TreeContext generateAST(String filePath) {
		return ASTService.generateAST(filePath);
	}

	// save lastest pf to lastestProject/{uri}/loc.pf
	public void saveLastestData() {
		File dir = new File(rootAddress);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		writeToFile(pf, locPath);

	}

	public static void writeToFile(String str, String path) {
		try {
			File writename = new File(path);
			if (writename.exists())
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

	public void setTree(ITree org, ITree old) {
		super.setTree(org, old);
		pf = astService.getPf(old);
	}

	// ========================change=====================
	public void change(String pf) {

		this.pf = pf;

		// 从pf文件读取locAST,并根据当前时间和orgAST和oldAST节点时间，生成带有修改时间的locAST
		changeOldAST();

		// 生成新的 old.pf for test
		astService.generatePf(oldTree, oldPath);

		// 发送给subject
		for (LSPSubject subject : LSPSubjects.getSubjectSet()) {
			if (subject.getUri().contentEquals(uri)) {
				subject.setValue("text", orgTree, oldTree, session);
			} else {
				System.out.println(subject.getId() + " != " + uri);
			}
		}
		// set new orgTree
		orgTree = oldTree.deepCopy();
	}

	// ================================merge======================

	@Override
	public void update(String type, ITree org, ITree old) {
		if (type.contentEquals("text")) {
			System.out.println(session.getId() + " update old ast since another editor change===================");
			update(org, old);

			// generate merge.pf
			astService.generatePf(oldTree, mergePath);

			// 生成新的pf
			pf = astService.getPf(oldTree);

			// 发送给客户端
			notifyClient();
			System.out.println(" update end ========================================");
		}
	}

	// send lastest pf when registed or merge
	public void notifyClient() {
		// 通知客户端
		JSONObject message = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject textDocument = new JSONObject();

		textDocument.put("uri", uri);
		textDocument.put("version", 0);

		params.put("textDocument", textDocument);
		params.put("text", pf);

		message.put("jsonrpc", "2.0");
		message.put("id", 0);
		message.put("method", "TextDocument/didChange");
		message.put("params", params);
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
