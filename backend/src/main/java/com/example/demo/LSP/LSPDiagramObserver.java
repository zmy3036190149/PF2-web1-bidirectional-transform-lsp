package com.example.demo.LSP;

import java.util.LinkedList;
import java.util.List;

import javax.websocket.Session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.LSP.bean.LineInfo;
import com.example.demo.bean.Constraint;
import com.example.demo.bean.ContextDiagram;
import com.example.demo.bean.Interface;
import com.example.demo.bean.Machine;
import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.ProblemDomain;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.bean.RequirementPhenomenon;
import com.example.demo.bean.Shape;
import com.example.demo.service.ASTService;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

public class LSPDiagramObserver extends LSPObserver {

	private int i = 0;
	boolean errorFlag = false;

	// ==========================初始化====================
	@Deprecated
	public LSPDiagramObserver(Session session, String uri, Project project) {
		super(session, uri, "diagram");
		this.project = project;
		orgPath = rootAddress + "org.xml";// 原始版
		locPath = rootAddress + "loc.xml";// 客户端传来的最新版
		oldPath = rootAddress + "old.xml";// 合成的最新版
		mergePath = rootAddress + "merge.xml";// 合并版

		long time = System.currentTimeMillis();
		generateOrgAST(time);
		generateOldAST(time);

		System.out.println("new LSPDiagramObserver		");
	}

	@Deprecated
	public LSPDiagramObserver(Session session, String uri, String title, ContextDiagram cd) {
		super(session, uri, "diagram");
		project = new Project();
		project.setTitle(title);
		project.setContextDiagram(cd);
		ProblemDiagram pd = new ProblemDiagram();
		pd.setTitle("ProblemDiagram");
		project.setProblemDiagram(pd);
		// System.out.println("new LSPDiagramObserver ");
	}

	@Deprecated
	public void setProject(ProblemDiagram pd) {
		project.setProblemDiagram(pd);
		long time = System.currentTimeMillis();
		generateOrgAST(time);
	}

	@Deprecated
	public void setRequirementList(List<Requirement> requirementList) {
		project.getProblemDiagram().setRequirementList(requirementList);

	}

	@Deprecated
	public void setReferenceList(List<Reference> referenceList) {
		project.getProblemDiagram().setReferenceList(referenceList);
	}

	@Deprecated
	public void setConstraintList(List<Constraint> constraintList) {
		project.getProblemDiagram().setConstraintList(constraintList);
	}

	public LSPDiagramObserver(Session session, String uri, Project project, String pf) {
		super(session, uri, "diagram", project, pf);

		orgPath = rootAddress + "org.xml";// 原始版
		locPath = rootAddress + "loc.xml";// 客户端传来的最新版
		oldPath = rootAddress + "old.xml";// 合成的最新版
		mergePath = rootAddress + "merge.xml";// 合并版

		long time = System.currentTimeMillis();
		generateTextOrgAST(time);
		generateTextOldAST(time);
		generateDiagramOrgAST(time);
		generateDiagramOldAST(time);

		System.out.println("new LSPDiagramObserver		");
	}

	// save lastestProject to loc.xml
	public void saveLastestData() {
		saveLastestProject();
	}

	// 根据xml文件生成AST
	@Override
	public TreeContext generateAST(String filePath) {
		return ASTService.generateXmlAST(filePath);
	}

	public void setTree(ITree org, ITree old) {
		super.setTree(org, old);
		astService.generateXmlFile(old, oldPath);
		project = astService.getProject(oldPath);
	}

	@Override
	public void notifyClient() {
		// 通知客户端
		JSONObject message = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject diagram = new JSONObject();
		JSONArray contentChanges = new JSONArray();

		diagram.put("uri", uri);
		diagram.put("version", 0);

		JSONObject contentChange = new JSONObject();
		contentChange.put("shapeType", "project");
		contentChange.put("newProject", project);
		contentChanges.add(contentChange);

		params.put("diagram", diagram);
		params.put("contentChanges", contentChanges);

		message.put("jsonrpc", "2.0");
		message.put("id", 0);
		message.put("method", "Diagram/didChange");
		message.put("params", params);

		session.getAsyncRemote().sendText(JSON.toJSONString(message));
	}

	// ========================change=====================

	// 更新本地project并发送给客户端
	@Deprecated
	public void change_old(String message) {

		// 更新本地project
		dealWithMessage(message);

		// 将当前 project 存为loc.xml文件
		saveLastestData();

		// 生成 oldTree 并设置修改时间
		changeOldAST();

		// 生成 old.xml
		astService.generateXmlFile(oldTree, oldPath);

		// 发送给远端
		session.getAsyncRemote().sendText(message);

		// 发送给subject
		for (LSPSubject subject : LSPSubjects.getSubjectSet()) {
			if (subject.getUri().contentEquals(uri)) {
				subject.setValue("diagram", orgTree, oldTree, session);
			}
		}

		// set new orgTree
		orgTree = oldTree.deepCopy();
	}

	// 更新本地project及pf并发送给客户端
	public void change(String message) {

		// 发送给远端
		session.getAsyncRemote().sendText(message);

		// 更新本地project 并更新 text_oldTree
		dealWithMessage(message);

		// 将当前 project 存为loc.xml文件
		saveLastestProject();

		// generate diagram_locTree
		long time = System.currentTimeMillis();
		generateDiagramLocAST(time);

		// generate new diagram_oldTree
		diagram_oldTree = changeOldAST(diagram_orgTree, diagram_oldTree, diagram_locTree);
		diagram_oldContext.setRoot(diagram_oldTree);

		// generate old.xml for test
		astService.generateXmlFile(diagram_oldTree, diagram_oldPath);

		// 发送给subject
		for (LSPSubject subject : LSPSubjects.getSubjectSet()) {
			if (subject.getUri().contentEquals(uri)) {
				subject.setValue(diagram_orgTree, diagram_oldTree, text_orgTree, text_oldTree, session);
			}
		}

		// set new orgTree
		text_orgTree = text_oldTree.deepCopy();
		text_orgContext.setRoot(text_orgTree);
		diagram_orgTree = diagram_oldTree.deepCopy();
		diagram_orgContext.setRoot(diagram_orgTree);
	}

	public void dealWithMessage(String message) {
		JSONObject json = JSONObject.parseObject(message);
		JSONObject params = (JSONObject) json.get("params");
		JSONArray contentChanges = params.getJSONArray("contentChanges");
		for (Object contentChangeEvent : contentChanges) {
			JSONObject jcontentChange = (JSONObject) contentChangeEvent;
			String shapeType = jcontentChange.getString("shapeType");
			String changeType = jcontentChange.getString("changeType");
			switch (changeType) {
			case "add":
				add(shapeType, jcontentChange);
				break;
			case "delete":
				delete(shapeType, jcontentChange);
				break;
			case "change":
				change(shapeType, jcontentChange);
				break;
			}
		}
	}

	public void add(String shape, JSONObject jcontentChange) {
		String snewShape = jcontentChange.getString("newShape");
		String slineInfo = jcontentChange.getString("lineInfo");
		String snewPhenomenon = jcontentChange.getString("newPhenomenon");
		Shape newShape;
		switch (shape) {
		case "mac":
			newShape = JSON.parseObject(snewShape, Machine.class);
			if (project == null)
				System.out.println("project==null");
			else if (project.getContextDiagram() == null)
				System.out.println(" project.getContextDiagram()==null");
			else {
				project.getContextDiagram().setMachine((Machine) newShape);
				LSPTransform.add((Machine) newShape, text_oldTree);
			}
			break;
		case "pro":
			newShape = JSON.parseObject(snewShape, ProblemDomain.class);
			project.getContextDiagram().getProblemDomainList().add((ProblemDomain) newShape);
			LSPTransform.add((ProblemDomain) newShape, text_oldTree);
			break;
		case "req":
			newShape = JSON.parseObject(snewShape, Requirement.class);
			project.getProblemDiagram().getRequirementList().add((Requirement) newShape);
			LSPTransform.add((Requirement) newShape, text_oldTree);
			break;
		case "int":
			newShape = JSON.parseObject(snewShape, Interface.class);
			project.getContextDiagram().getInterfaceList().add((Interface) newShape);
			LSPTransform.add((Interface) newShape, text_oldTree);
			break;
		case "ref":
			newShape = JSON.parseObject(snewShape, Reference.class);
			project.getProblemDiagram().getReferenceList().add((Reference) newShape);
			LSPTransform.add((Reference) newShape, text_oldTree);
			break;
		case "con":
			newShape = JSON.parseObject(snewShape, Constraint.class);
			project.getProblemDiagram().getConstraintList().add((Constraint) newShape);
			LSPTransform.add((Constraint) newShape, text_oldTree);
			break;
		case "phe":
			LineInfo lineInfo = JSON.parseObject(slineInfo, LineInfo.class);
		}
	}

	public void addPhenomenon(LineInfo lineInfo, String snewPhenomenon) {
		String lineType = lineInfo.getType();
		switch (lineType) {
		case "int":
			Phenomenon newPhenomenon = JSON.parseObject(snewPhenomenon, Phenomenon.class);
			for (Interface intf : project.getContextDiagram().getInterfaceList()) {
				if (intf.getName().contentEquals(lineInfo.getName())) {
					intf.getPhenomenonList().add(newPhenomenon);

				}
			}
			break;
		case "ref":
			RequirementPhenomenon newRefPhenomenon = JSON.parseObject(snewPhenomenon, RequirementPhenomenon.class);
			for (Reference ref : project.getProblemDiagram().getReferenceList()) {
				if (ref.getName().contentEquals(lineInfo.getName())) {
					ref.getPhenomenonList().add(newRefPhenomenon);
				}
			}
			break;
		case "con":
			RequirementPhenomenon newConPhenomenon = JSON.parseObject(snewPhenomenon, RequirementPhenomenon.class);
			for (Constraint con : project.getProblemDiagram().getConstraintList()) {
				if (con.getName().contentEquals(lineInfo.getName())) {
					con.getPhenomenonList().add(newConPhenomenon);
				}
			}
			break;
		}
	}

	public void delete(String shape, JSONObject jcontentChange) {
		String soldShape = jcontentChange.getString("oldShape");
		String slineInfo = jcontentChange.getString("LineInfo");
		String soldPhenomenon = jcontentChange.getString("oldPhenomenon");
		Shape oldShape;
		switch (shape) {
		case "mac":
			oldShape = JSON.parseObject(soldShape, Machine.class);
			deleteRelatedLines(oldShape);
			project.getContextDiagram().setMachine(null);
			LSPTransform.delete((Machine) oldShape, text_oldTree);
			break;
		case "pro":
			oldShape = JSON.parseObject(soldShape, ProblemDomain.class);
			delete((ProblemDomain) oldShape, project.getContextDiagram().getProblemDomainList());
			LSPTransform.delete((ProblemDomain) oldShape, text_oldTree);
			break;
		case "req":
			oldShape = JSON.parseObject(soldShape, Requirement.class);
			delete((Requirement) oldShape, project.getProblemDiagram().getRequirementList());
			LSPTransform.delete((Requirement) oldShape, text_oldTree);
			break;
		case "int":
			oldShape = JSON.parseObject(soldShape, Interface.class);
			delete((Interface) oldShape, project.getContextDiagram().getInterfaceList());
			LSPTransform.delete((Interface) oldShape, text_oldTree);
			break;
		case "ref":
			oldShape = JSON.parseObject(soldShape, Reference.class);
			delete((Reference) oldShape, project.getProblemDiagram().getReferenceList());
			for (Requirement req : project.getProblemDiagram().getRequirementList()) {
				if (req.getName().contentEquals(((Reference) oldShape).getFrom())) {
					LSPTransform.delete(req.getShortname(), ((Reference) oldShape).getTo(), text_oldTree);
				} else if (req.getName().contentEquals(((Reference) oldShape).getTo())) {
					LSPTransform.delete(((Reference) oldShape).getFrom(), req.getShortname(), text_oldTree);
				}
			}

			break;
		case "con":
			oldShape = JSON.parseObject(soldShape, Constraint.class);
			delete((Constraint) oldShape, project.getProblemDiagram().getConstraintList());
			Constraint constraint = (Constraint) oldShape;
			for (Requirement req : project.getProblemDiagram().getRequirementList()) {
				if (req.getName().contentEquals(constraint.getFrom())) {
					LSPTransform.delete(req.getShortname(), constraint.getTo(), text_oldTree);
					break;
				} else if (req.getName().contentEquals(constraint.getTo())) {
					LSPTransform.delete(constraint.getFrom(), req.getShortname(), text_oldTree);
					break;
				}
			}
			break;
		case "phe":
			LineInfo lineInfo = JSON.parseObject(slineInfo, LineInfo.class);
			Phenomenon oldPhenomenon = JSON.parseObject(soldPhenomenon, Phenomenon.class);
		}
	}

	public void delete(ProblemDomain shape, List<ProblemDomain> list) {
		deleteRelatedLines(shape);
		for (Shape item : list) {
			if (item.getName().contentEquals(shape.getName())) {
				list.remove(item);
				break;
			}
		}
	}

	public void delete(Requirement shape, List<Requirement> list) {
		deleteRelatedLines(shape);
		for (Shape item : list) {
			if (item.getName().contentEquals(shape.getName())) {
				list.remove(item);
				break;
			}
		}
	}

	public void delete(Reference shape, List<Reference> list) {
		for (Shape item : list) {
			if (item.getName().contentEquals(shape.getName())) {
				list.remove(item);
				break;
			}
		}
	}

	public void delete(Interface shape, List<Interface> list) {
		for (Shape item : list) {
			if (item.getName().contentEquals(shape.getName())) {
				list.remove(item);
				break;
			}
		}
	}

	public void delete(Constraint shape, List<Constraint> list) {
		for (Shape item : list) {
			if (item.getName().contentEquals(shape.getName())) {
				list.remove(item);
				break;
			}
		}
	}

	private void deleteRelatedLines(Shape delete) {
		List<Interface> interfaceList = this.project.getContextDiagram().getInterfaceList();
		List<Reference> referenceList = this.project.getProblemDiagram().getReferenceList();
		List<Constraint> constraintList = this.project.getProblemDiagram().getConstraintList();
		if (delete instanceof Machine) {
			Machine machine = (Machine) delete;
			List<Interface> deleteInterfaceList = new LinkedList<>();
			for (Interface interfacee : interfaceList) {
				if (interfacee.getTo().equals(machine.getShortname())
						|| interfacee.getFrom().equals(machine.getShortname())) {
					interfacee.getPhenomenonList().clear();
					deleteInterfaceList.add(interfacee);
					if (!LSPTransform.delete(interfacee, text_oldTree)) {
						errorFlag = true;
					}
				}
			}
			interfaceList.removeAll(deleteInterfaceList);
		}
		if (delete instanceof ProblemDomain) {
			ProblemDomain problemDomain = (ProblemDomain) delete;
			List<Interface> deleteInterfaceList = new LinkedList<>();
			for (Interface interfacee : interfaceList) {
				if (interfacee.getTo().equals(problemDomain.getShortname())
						|| interfacee.getFrom().equals(problemDomain.getShortname())) {
					interfacee.getPhenomenonList().clear();
					deleteInterfaceList.add(interfacee);
					if (!LSPTransform.delete(interfacee, text_oldTree)) {
						errorFlag = true;
					}
				}
			}
			interfaceList.removeAll(deleteInterfaceList);

			List<Reference> deleteReferebceList = new LinkedList<>();
			for (Reference reference : referenceList) {
				if (reference.getTo().equals(problemDomain.getShortname())
						|| reference.getFrom().equals(problemDomain.getShortname())) {
					reference.getPhenomenonList().clear();
					deleteReferebceList.add(reference);
					for (Requirement req : project.getProblemDiagram().getRequirementList()) {
						if (req.getName().contentEquals(reference.getFrom())
								|| req.getName().contentEquals(reference.getTo())) {
							LSPTransform.delete(problemDomain.getShortname(), req.getShortname(), text_oldTree);
						}
					}
				}
			}
			referenceList.removeAll(deleteReferebceList);

			List<Constraint> deleteConstraintList = new LinkedList<>();
			for (Constraint constraint : constraintList) {
				if (constraint.getTo().equals(problemDomain.getShortname())
						|| constraint.getFrom().equals(problemDomain.getShortname())) {
					constraint.getPhenomenonList().clear();
					deleteConstraintList.add(constraint);
					for (Requirement req : project.getProblemDiagram().getRequirementList()) {
						if (req.getName().contentEquals(constraint.getFrom())
								|| req.getName().contentEquals(constraint.getTo())) {
							LSPTransform.delete(req.getShortname(), problemDomain.getShortname(), text_oldTree);
						}
					}
				}
			}
			constraintList.removeAll(deleteConstraintList);
		}

		else if (delete instanceof Requirement) {
			Requirement requirement = (Requirement) delete;

			List<Reference> deleteReferebceList = new LinkedList<>();
			for (Reference reference : referenceList) {
				if (reference.getTo().equals(requirement.getName())) {
					reference.getPhenomenonList().clear();
					deleteReferebceList.add(reference);
					LSPTransform.delete(reference.getFrom(), requirement.getShortname(), text_oldTree);
				} else if (reference.getFrom().equals(requirement.getName())) {
					reference.getPhenomenonList().clear();
					deleteReferebceList.add(reference);
					LSPTransform.delete(requirement.getShortname(), reference.getTo(), text_oldTree);
				}
			}
			referenceList.removeAll(deleteReferebceList);

			List<Constraint> deleteConstraintList = new LinkedList<>();
			for (Constraint constraint : constraintList) {
				if (constraint.getTo().equals(requirement.getName())) {
					constraint.getPhenomenonList().clear();
					deleteConstraintList.add(constraint);
					LSPTransform.delete(constraint.getFrom(), requirement.getShortname(), text_oldTree);

				} else if (constraint.getFrom().equals(requirement.getName())) {
					constraint.getPhenomenonList().clear();
					deleteConstraintList.add(constraint);
					LSPTransform.delete(requirement.getShortname(), constraint.getTo(), text_oldTree);

				}
			}
			constraintList.removeAll(deleteConstraintList);
		}
	}

	public void change(String shape, JSONObject jcontentChange) {
		String soldShape = jcontentChange.getString("oldShape");
		String snewShape = jcontentChange.getString("newShape");
		String slineInfo = jcontentChange.getString("LineInfo");
		String soldPhenomenon = jcontentChange.getString("oldPhenomenon");
		String snewPhenomenon = jcontentChange.getString("newPhenomenon");
		Shape oldShape;
		Shape newShape;
		Phenomenon oldPhenomenon;
		Phenomenon newPhenomenon;
		switch (shape) {
		case "mac":
			newShape = JSON.parseObject(snewShape, Machine.class);
			oldShape = JSON.parseObject(soldShape, Machine.class);
			change((Machine) oldShape, (Machine) newShape);
			LSPTransform.change((Machine) oldShape, (Machine) newShape, text_oldTree);
			break;
		case "pro":
			oldShape = JSON.parseObject(soldShape, ProblemDomain.class);
			newShape = JSON.parseObject(snewShape, ProblemDomain.class);
			change((ProblemDomain) oldShape, (ProblemDomain) newShape);
			LSPTransform.change((ProblemDomain) oldShape, (ProblemDomain) newShape, text_oldTree);
			break;
		case "req":
			oldShape = JSON.parseObject(soldShape, Requirement.class);
			newShape = JSON.parseObject(snewShape, Requirement.class);
			change((Requirement) oldShape, (Requirement) newShape);
			LSPTransform.change((Requirement) oldShape, (Requirement) newShape, text_oldTree);
			break;
		case "int":
			oldShape = JSON.parseObject(soldShape, Interface.class);
			newShape = JSON.parseObject(snewShape, Interface.class);
			change((Interface) oldShape, (Interface) newShape);
			LSPTransform.change((Interface) oldShape, (Interface) newShape, text_oldTree);
			break;
		case "ref":
			oldShape = JSON.parseObject(soldShape, Reference.class);
			newShape = JSON.parseObject(snewShape, Reference.class);
			change((Reference) oldShape, (Reference) newShape);
			LSPTransform.change((Reference) oldShape, (Reference) newShape, text_oldTree);
			break;
		case "con":
			oldShape = JSON.parseObject(soldShape, Constraint.class);
			newShape = JSON.parseObject(snewShape, Constraint.class);
			change((Constraint) oldShape, (Constraint) newShape);
			LSPTransform.change((Constraint) oldShape, (Constraint) newShape, text_oldTree);
			break;
		}
	}

	public void change(Machine old, Machine new1) {
		project.getContextDiagram().setMachine((Machine) new1);
		project.getProblemDiagram().getContextDiagram().setMachine((Machine) new1);
		if (!new1.getShortname().contentEquals(old.getShortname())) {
			changeRelatedLink(old.getShortname(), new1.getShortname());
		}
	}

	public void change(ProblemDomain old, ProblemDomain new1) {
		int i = 0;
		for (ProblemDomain item : project.getContextDiagram().getProblemDomainList()) {
			if (item.getNo() == old.getNo()) {
				project.getContextDiagram().getProblemDomainList().set(i, new1);
				if (!new1.getShortname().contentEquals(old.getShortname())) {
					changeRelatedLink(old.getShortname(), new1.getShortname());
				}
				break;
			}
			i++;
		}
	}

	public void change(Requirement old, Requirement new1) {
		int i = 0;
		for (Requirement item : project.getProblemDiagram().getRequirementList()) {
			if (item.getNo() == old.getNo()) {
				project.getProblemDiagram().getRequirementList().set(i, new1);
				if (!new1.getShortname().contentEquals(old.getShortname())) {
					changeRelatedLink(old.getShortname(), new1.getShortname());
				}
				break;
			}
			i++;
		}
	}

	public void change(Reference old, Reference new1) {
		int i = 0;
		for (Reference item : project.getProblemDiagram().getReferenceList()) {
			if (item.getNo() == old.getNo()) {
				project.getProblemDiagram().getReferenceList().set(i, new1);
				break;
			}
			i++;
		}
	}

	public void change(Interface old, Interface new1) {
		int i = 0;
		for (Interface item : project.getContextDiagram().getInterfaceList()) {
			if (item.getNo() == old.getNo()) {
				project.getContextDiagram().getInterfaceList().set(i, new1);
				break;
			}
			i++;
		}
	}

	public void change(Constraint old, Constraint new1) {
		int i = 0;
		for (Constraint item : project.getProblemDiagram().getConstraintList()) {
			if (item.getNo() == old.getNo()) {
				project.getProblemDiagram().getConstraintList().set(i, new1);
				break;
			}
			i++;
		}
	}

	public void changeRelatedLink(String oldShortName, String newShortName) {
		List<Interface> interfaceList = this.project.getContextDiagram().getInterfaceList();
		List<Reference> referenceList = this.project.getProblemDiagram().getReferenceList();
		List<Constraint> constraintList = this.project.getProblemDiagram().getConstraintList();
		for (Interface interfacee : interfaceList) {
			if (interfacee.getFrom().equals(oldShortName))
				interfacee.setFrom(newShortName);
			else if (interfacee.getTo().equals(oldShortName))
				interfacee.setTo(newShortName);
			interfacee.refreshPhenomenonList(oldShortName, newShortName);
		}
		for (Reference reference : referenceList) {
			if (reference.getFrom().equals(oldShortName))
				reference.setFrom(newShortName);
			else if (reference.getTo().equals(oldShortName))
				reference.setTo(newShortName);
			reference.refreshPhenomenonList(oldShortName, newShortName);
		}
		for (Constraint constraint : constraintList) {
			if (constraint.getFrom().equals(oldShortName))
				constraint.setFrom(newShortName);
			else if (constraint.getTo().equals(oldShortName))
				constraint.setTo(newShortName);
			constraint.refreshPhenomenonList(oldShortName, newShortName);
		}

	}

	// ========================merge=======================
	// old
//	@Override
//	public void update(String type, ITree org, ITree old) {
//		if (type.contentEquals("diagram")) {
//			updateDiagram(org, old);
//			// notify client the change
//			notifyClient();
//		}
//	}

}
