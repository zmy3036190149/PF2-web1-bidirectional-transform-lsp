package com.example.demo.LSP;

import java.util.List;

import javax.websocket.Session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Constraint;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.service.ASTService;
import com.example.demo.service.FileOperation;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

public class LSPTextObserver extends LSPObserver {
	boolean isSyn = false;
	int times = 0;

	// ==========================初始化====================
	@Deprecated
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

	public LSPTextObserver(Session session, String uri, Project project, String pf) {
		super(session, uri, "diagram", project, pf);
		orgPath = rootAddress + "org.pf";// 原始版
		locPath = rootAddress + "loc.pf";// 客户端传来的最新版
		oldPath = rootAddress + "old.pf";// 合成的最新版
		mergePath = rootAddress + "merge.pf";// 合并版

		long time = System.currentTimeMillis();
		generateTextOrgAST(time);
		generateTextOldAST(time);
		generateDiagramOrgAST(time);
		generateDiagramOldAST(time);

		System.out.println("new LSPTextObserver		");
	}

	// 根据pf文件生成AST
	public TreeContext generateAST(String filePath) {
		return ASTService.generateNoErrorPfAST(filePath);
	}

	// save lastest pf to lastestProject/{uri}/loc.pf
	public void saveLastestData() {
		FileOperation.writeToFile(pf, rootAddress, locPath);
	}

	@Deprecated
	public void setTree(ITree org, ITree old) {
		super.setTree(org, old);
		pf = astService.getPf(old);
	}

	// ========================change=====================
	@Deprecated
	// 只更新 pf
	public void change_old(String pf) {

		this.pf = pf;

		// 从pf文件读取locAST, 并根据当前时间和orgAST和oldAST节点时间，生成带有修改时间的locAST
		changeOldAST();

		// 生成新的 old.pf for test
		ASTService.generatePfFile(oldTree, rootAddress, oldPath);

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

	boolean hasSpace(String old, String new1) {

		return true;
	}

	public void change(String pf) {

		// 判断是否同步
		this.pf = pf;
		// save pf to loc.pf, then gset locAST
		long time = System.currentTimeMillis();
		if (!generateTextLocAST(time))
			return;

		if (text_oldTree == null || text_orgTree == null) {
			generateTextOrgAST(time);
			generateTextOldAST(time);
		} else {
			// 根据orgAST,oldAST和locAST，生成新的oldAST
			text_oldTree = changeOldAST(text_orgTree, text_oldTree, text_locTree);
			text_oldContext.setRoot(text_oldTree);
		}

		// 生成新的 old.pf
		ASTService.generatePfFile(text_oldTree, rootAddress, text_oldPath);

		// old.pf -> project -> xmlFile -> xmlAST
		LSPTransform.transfer2Project(project, text_oldPath);// old.pf -> project
		saveLastestProject();// project -> xmlFile
		generateDiagramLocAST(time);// xmlFile -> xmlASt
		// change diagram_oldTree's time
		diagram_oldTree = changeOldAST(diagram_orgTree, diagram_oldTree, diagram_locTree);
		diagram_oldContext.setRoot(diagram_oldTree);

		// 发送给subject
		for (LSPSubject subject : LSPSubjects.getSubjectSet()) {
			if (subject.getUri().contentEquals(uri)) {
				subject.setValue(diagram_orgTree, diagram_oldTree, text_orgTree, text_oldTree, session);
			} else {
				System.out.println(subject.getId() + " != " + uri);
			}
		}
		// set new orgTree
		text_orgTree = text_oldTree.deepCopy();
		text_orgContext.setRoot(text_orgTree);
		diagram_orgTree = diagram_oldTree.deepCopy();
		diagram_orgContext.setRoot(diagram_orgTree);
	}

	// ================================merge======================

//	// old
//	@Override
//	public void update(String type, ITree org, ITree old) {
//		if (type.contentEquals("text")) {
//			updateText(org, old);
//			// 发送给客户端
//			notifyClient();
//		}
//	}

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
