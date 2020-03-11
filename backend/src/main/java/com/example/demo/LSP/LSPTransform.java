package com.example.demo.LSP;

import java.io.File;
import java.util.List;

import com.example.demo.bean.Constraint;
import com.example.demo.bean.Interface;
import com.example.demo.bean.Machine;
import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.ProblemDomain;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.service.ASTService;
import com.example.demo.service.FileOperation;
import com.example.demo.service.ProblemEditor;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

public class LSPTransform {

	public static void add(Machine machine, ITree tree) {
		String str = "\n" + machine.getShortname() + " M \"" + machine.getName() + "\"";
		changeAST(tree, Integer.MAX_VALUE, str);
	}

	public static void add(ProblemDomain problemDomain, ITree tree) {
		String type = "C";
		switch (problemDomain.getProperty()) {
		case "Causal":
			type = "C";
		case "Lexical":
			type = "X";
		case "Biddable":
			type = "B";
		}
		String str = "\n" + problemDomain.getShortname() + " " + type + " \"" + problemDomain.getName() + "\"\n";
		changeAST(tree, Integer.MAX_VALUE, str);
	}

	public static void add(Requirement requirement, ITree tree) {
		String str = "\n" + requirement.getShortname() + " R \"" + requirement.getName() + "\"\n";
		changeAST(tree, Integer.MAX_VALUE, str);
	}

	public static void add(Interface interfacee, ITree tree) {
		String str = "\n" + interfacee.getFrom() + " -- " + interfacee.getTo() + " \"" + interfacee.getName() + "\"\n";
		changeAST(tree, Integer.MAX_VALUE, str);
	}

	public static void add(Reference reference, ITree tree) {
		String str = "\n" + reference.getFrom() + " ~> " + reference.getTo() + " \"" + reference.getName() + "\"\n";
		changeAST(tree, Integer.MAX_VALUE, str);
	}

	public static void add(Constraint constraint, ITree tree) {
		String str = "\n" + constraint.getFrom() + " ~> " + constraint.getTo() + " \"" + constraint.getName() + "\"\n";
		changeAST(tree, Integer.MAX_VALUE, str);
	}

//	public static void addPhenomenon(ITree tree, LineInfo lineInfo, Phenomenon phenomenon) {
//		String lineType = lineInfo.getType();
//		String lineName = lineInfo.getName();
//		String lineFrom = lineInfo.getFrom();
//		String lineTo = lineInfo.getTo();
//		String pheType = phenomenon.getType();
//		String pheName = phenomenon.getName();
//		ITree lineTree = null;
//		ITree typeTree = null;
//		ArrayList<String> types = new ArrayList<String>();
//		if (lineType.contentEquals("int")) {
//			types.add("--");
//			types.add("->");
//		} else {
//			types.add("~~");
//			types.add("~>");
//		}
//		// find line AST
//		for (ITree t : tree.getTrees()) {
//			if (types.contains(t.getLabel())) {
//				typeTree = t.getParent();
//				lineTree = t.getParent().getParent();
//				int pos = typeTree.getPos();
//				int fromPos = pos >= 1 ? pos - 1 : 0;
//				ITree fromTree = lineTree.getChild(fromPos);
//				int len = lineTree.getChildren().size();
//				ITree toTree = lineTree.getChild(pos + 1);
//				if (toTree.getType() == 3) {
//					toTree = lineTree.getChild(pos + 2);
//				}
//				if (fromTree.getLabel().contentEquals(lineFrom) && toTree.getLabel().contentEquals(lineTo)
//						|| fromTree.getLabel().contentEquals(lineTo) && toTree.getLabel().contentEquals(lineFrom)) {
//					for (ITree lineChild : lineTree.getChildren()) {
//						if (lineChild.getLabel().contentEquals("}")) {
//							// not first phenomenon
//							String str = "," + pheType + " " + pheName;
//							int k = lineChild.getPos();
//							changeAST(lineTree, k, str);
//						}
//					}
//				}
//			}
//		}
//
//		// first phenomenon
//		// not first phenomenon
//	}

//	public static void addRequirementPhenomenon(ITree tree, LineInfo lineInfo, RequirementPhenomenon phenomenon) {
//		String lineName = lineInfo.getName();
//		String lineFrom = lineInfo.getFrom();
//		String lineTo = lineInfo.getTo();
//		String pheType = phenomenon.getType();
//		String pheName = phenomenon.getName();
//		ITree lineTree = null;
//		ITree typeTree = null;
//
//		// find line AST
//		for (ITree t : tree.getTrees()) {
//			if (t.getLabel().contains("~~") || t.getLabel().contains("~>")) {
//				typeTree = t.getParent();
//				lineTree = t.getParent().getParent();
//				int pos = typeTree.getPos();
//				ITree fromTree = lineTree.getChild(pos - 1);
//				ITree toTree = lineTree.getChild(pos + 1);
//				if (toTree.getType() == 3) {
//					toTree = lineTree.getChild(pos + 2);
//				}
//				if (fromTree.getLabel().contentEquals(lineFrom) && toTree.getLabel().contentEquals(lineTo)
//						|| fromTree.getLabel().contentEquals(lineTo) && toTree.getLabel().contentEquals(lineFrom)) {
//					for (ITree lineChild : lineTree.getChildren()) {
//						if (lineChild.getLabel().contentEquals("}")) {
//							// not first phenomenon
//							String str = "," + pheType + " " + pheName;
//							int k = lineChild.getPos();
//							changeAST(lineTree, k, str);
//							return;
//						}
//					}
//					String str = " {" + pheType + " " + pheName + "}";
//					int len = lineTree.getChildren().size();
//					ITree nameChild = lineTree.getChild(len - 1);
//					if (nameChild.getLabel().contentEquals("\"" + lineName + "\"")) {
//						changePheAST(lineTree, len - 2, str);
//					}
//
//				}
//			}
//		}
//
//	}

	public static void changeAST(ITree tree, int pos, String str) {
		String filePath = "PF/temp.pf";
		str = "problem: #1#\n" + str + " M111 M \"machine\"";
		FileOperation.writeToFile(str, filePath);
		TreeContext tc = ASTService.generatePfAST(filePath);
		ITree t = tc.getRoot();
		for (ITree child : t.getChildren()) {
			if (child.getType() == 1) {
				t = child;
				break;
			}
		}
		long time = System.currentTimeMillis();
		t = ASTService.addTime((Tree) t, time);
		int len = tree.getChildren().size();
		if (pos < len) {
			tree.insertChild(t, pos);
		} else {
			tree.addChild(t);
		}
		ASTService.resetId(tree, 0);
	}
//
//	// 修改 tree：新增现象,first
//	public static void changePheAST(ITree tree, int pos, String str) {
//		String filePath = "PF/temp.pf";
//		FileOperation.writeToFile(str, filePath);
//		TreeContext tc = ASTService.generatePfAST(filePath);
//		ITree t = tc.getRoot();
//
//		long time = System.currentTimeMillis();
//		t = ASTService.addTime((Tree) t, time);
//		int len = tree.getChildren().size();
//		if (pos < len) {
//			for (ITree child : t.getChildren()) {
//				tree.insertChild(child, pos++);
//			}
//		} else {
//			for (ITree child : t.getChildren()) {
//				tree.addChild(child);
//			}
//		}
//		ASTService.resetId(tree, 0);
//	}

	public static void delete(Machine machine, ITree tree) {
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("M")) {
				ITree parent = t.getParent();
				while (parent.getType() != 1) {
					parent = parent.getParent();
				}
				for (ITree child : parent.getTrees()) {
					if (child.getLabel().contentEquals(machine.getShortname())) {
						parent.getParent().getChildren().remove(parent);
						return;
					}
				}
			}
		}
		// 无类型
	}

	public static void delete(ProblemDomain problemDomain, ITree tree) {
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("C") || t.getLabel().contentEquals("B") || t.getLabel().contentEquals("X")
					|| t.getLabel().contentEquals("D")) {
				ITree parent = t.getParent();
				while (parent.getType() != 1) {
					parent = parent.getParent();
				}
				for (ITree child : parent.getTrees()) {
					if (child.getLabel().contentEquals(problemDomain.getShortname())) {
						parent.getParent().getChildren().remove(parent);
						return;
					}
				}
			}
		}
	}

	public static void delete(Requirement requirement, ITree tree) {
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("R")) {
				ITree parent = t.getParent();
				while (parent.getType() != 1) {
					parent = parent.getParent();
				}
				for (ITree child : parent.getTrees()) {
					if (child.getLabel().contentEquals(requirement.getShortname())) {
						parent.getParent().getChildren().remove(parent);
					}
				}
			}
		}
	}

	public static void delete(Interface interfacee, ITree tree) {
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("--") || t.getLabel().contentEquals("->")) {
				ITree parent = t.getParent();
				while (parent.getType() != 1) {
					parent = parent.getParent();
				}
				int flag = 0;
				for (ITree child : parent.getTrees()) {
					if (child.getLabel().contentEquals(interfacee.getFrom())) {
						flag++;
					} else if (child.getLabel().contentEquals(interfacee.getTo())) {
						flag++;
					}
				}
				if (flag == 2) {
					parent.getParent().getChildren().remove(parent);
				}
			}
		}
	}

	public static void delete(Reference reference, ITree tree) {
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("~~") || t.getLabel().contentEquals("~>")) {
				ITree parent = t.getParent();
				while (parent.getType() != 1) {
					parent = parent.getParent();
				}
				int flag = 0;
				for (ITree child : parent.getTrees()) {
					if (child.getLabel().contentEquals(reference.getFrom())) {
						flag++;
					} else if (child.getLabel().contentEquals(reference.getTo())) {
						flag++;
					}
				}
				if (flag == 2)
					parent.getParent().getChildren().remove(parent);
			}
		}
	}

	public static void delete(Constraint constraint, ITree tree) {
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("~~") || t.getLabel().contentEquals("~>")) {
				ITree parent = t.getParent();
				while (parent.getType() != 1) {
					parent = parent.getParent();
				}
				int flag = 0;
				for (ITree child : parent.getTrees()) {
					if (child.getLabel().contentEquals(constraint.getFrom())) {
						flag++;
					} else if (child.getLabel().contentEquals(constraint.getTo())) {
						flag++;
					}
				}
				if (flag == 2)
					parent.getParent().getChildren().remove(parent);
			}
		}
	}

	public static void change(Machine old, Machine new1, ITree tree) {
		if (old.getShortname().contentEquals(new1.getShortname()) && old.getName().contentEquals(new1.getName()))
			return;
		int pos = 0;
		ITree parentTree = null;
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("M")) {
				ITree lineTree = t.getParent();
				while (lineTree.getType() != 1) {
					lineTree = lineTree.getParent();
				}
				for (ITree child : lineTree.getTrees()) {
					if (child.getLabel().contentEquals(old.getShortname())) {
						parentTree = lineTree.getParent();
						pos = parentTree.getChildren().indexOf(lineTree);

						// delete old AST
						parentTree.getChildren().remove(lineTree);

						// Insert new AST
						String str = getPfLine(new1);
						changeAST(parentTree, pos, str);
						ASTService.resetId(tree, 0);
						return;

					}
				}
			}
		}

	}

	public static void change(ProblemDomain old, ProblemDomain new1, ITree tree) {
		if (old.getShortname().contentEquals(new1.getShortname()) && old.getName().contentEquals(new1.getName())
				&& old.getType().contentEquals(new1.getType()) && old.getProperty().contentEquals(new1.getProperty()))
			return;
		int pos = 0;
		ITree parentTree = null;

		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("C") || t.getLabel().contentEquals("B") || t.getLabel().contentEquals("X")
					|| t.getLabel().contentEquals("D")) {
				ITree lineTree = t.getParent();
				while (lineTree.getType() != 1) {
					lineTree = lineTree.getParent();
				}
				for (ITree child : lineTree.getTrees()) {
					if (child.getLabel().contentEquals(old.getShortname())) {
						parentTree = lineTree.getParent();
						pos = parentTree.getChildren().indexOf(lineTree);

						// delete old AST
						parentTree.getChildren().remove(lineTree);

						// Insert new AST
						String str = getPfLine(new1);
						changeAST(parentTree, pos, str);
						ASTService.resetId(tree, 0);

						return;
					}
				}
			}
		}

	}

	public static void change(Requirement old, Requirement new1, ITree tree) {
		if (old.getShortname().contentEquals(new1.getShortname()) && old.getName().contentEquals(new1.getName()))
			return;
		int pos = 0;
		ITree parentTree = null;
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("R")) {
				ITree lineTree = t.getParent();
				while (lineTree.getType() != 1) {
					lineTree = lineTree.getParent();
				}
				for (ITree child : lineTree.getTrees()) {
					if (child.getLabel().contentEquals(old.getShortname())) {

						parentTree = lineTree.getParent();
						pos = parentTree.getChildren().indexOf(lineTree);

						// delete old AST
						parentTree.getChildren().remove(lineTree);

						// Insert new AST
						String str = getPfLine(new1);
						changeAST(parentTree, pos, str);
						ASTService.resetId(tree, 0);
						return;
					}
				}
			}
		}
	}

	public static void change(Reference old, Reference new1, ITree tree) {
		int pos = 0;
		ITree parentTree = null;
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("~~") || t.getLabel().contentEquals("~>")) {
				String lineType = t.getLabel();
				ITree lineTree = t.getParent();
				while (lineTree.getType() != 1) {
					lineTree = lineTree.getParent();
				}
				int flag = 0;
				for (ITree child : lineTree.getTrees()) {
					if (child.getLabel().contentEquals(old.getFrom())) {
						flag++;
					} else if (child.getLabel().contentEquals(old.getTo())) {
						flag++;
					}
				}
				if (flag == 2) {

					parentTree = lineTree.getParent();
					pos = parentTree.getChildren().indexOf(lineTree);
					parentTree.getChildren().remove(lineTree);
					String str = getPfLine(new1, lineType);
					changeAST(parentTree, pos, str);
					ASTService.resetId(tree, 0);
					return;
				}
			}
		}

	}

	public static void change(Interface old, Interface new1, ITree tree) {
		int pos = 0;
		ITree parentTree = null;
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("--") || t.getLabel().contentEquals("->")) {
				String lineType = t.getLabel();
				ITree lineTree = t.getParent();
				while (lineTree.getType() != 1) {
					lineTree = lineTree.getParent();
				}
				int flag = 0;
				for (ITree child : lineTree.getTrees()) {
					if (child.getLabel().contentEquals(old.getFrom())) {
						flag++;
					} else if (child.getLabel().contentEquals(old.getTo())) {
						flag++;
					}
				}
				if (flag == 2) {

					parentTree = lineTree.getParent();
					pos = parentTree.getChildren().indexOf(lineTree);
					parentTree.getChildren().remove(lineTree);
					String str = getPfLine(new1, lineType);
					changeAST(parentTree, pos, str);
					ASTService.resetId(tree, 0);
					return;
				}
			}
		}
	}

	public static void change(Constraint old, Constraint new1, ITree tree) {
		int pos = 0;
		ITree parentTree = null;
		for (ITree t : tree.getTrees()) {
			if (t.getLabel().contentEquals("~~") || t.getLabel().contentEquals("~>")) {
				String lineType = t.getLabel();
				ITree lineTree = t.getParent();
				while (lineTree.getType() != 1) {
					lineTree = lineTree.getParent();
				}
				int flag = 0;
				for (ITree child : lineTree.getTrees()) {
					if (child.getLabel().contentEquals(old.getFrom())) {
						flag++;
					} else if (child.getLabel().contentEquals(old.getTo())) {
						flag++;
					}
				}
				if (flag == 2) {
					parentTree = lineTree.getParent();
					pos = parentTree.getChildren().indexOf(lineTree);
					parentTree.getChildren().remove(lineTree);
					String str = getPfLine(new1, lineType);
					changeAST(parentTree, pos, str);
					ASTService.resetId(tree, 0);
					return;
				}
			}
		}

	}

	public static String getPfLine(Machine new1) {
		String shortName = new1.getShortname();
		String sType = " M ";
		String name = new1.getName();
		String line = "\n" + shortName + sType + "\"" + name + "\"";
		return line;
	}

	public static String getPfLine(ProblemDomain new1) {
		String shortName = new1.getShortname();
		String sType = "";
		String name = new1.getName();
		switch (new1.getType()) {
		case "Biddable":
			sType = " B";
			break;
		case "Causal":
			sType = " C";
			break;
		case "Lexical":
			sType = " X";
			break;
		}
		String line = "\n" + shortName + sType + " \"" + name + "\"";
		return line;
	}

	public static String getPfLine(Requirement new1) {
		String shortName = new1.getShortname();
		String sType = " R";
		String name = new1.getName();
		String line = "\n" + shortName + sType + " \"" + name + "\"";
		return line;
	}

	public static String getPfLine(Interface new1, String lineType) {
		String from = new1.getFrom();
		String to = new1.getTo();
		String lineName = new1.getName();
		List phe = new1.getPhenomenonList();
		String line = "\n" + from + " " + lineType + " " + to + getpheLine(phe) + " \"" + lineName + "\"";
		return line;
	}

	public static String getPfLine(Reference new1, String lineType) {
		String from = new1.getFrom();
		String to = new1.getTo();
		String lineName = new1.getName();
		List phe = new1.getPhenomenonList();
		String line = "\n" + from + " " + lineType + " " + to + getpheLine(phe) + " \"" + lineName + "\"";
		return line;

	}

	public static String getPfLine(Constraint new1, String lineType) {
		String from = new1.getFrom();
		String to = new1.getTo();
		String lineName = new1.getName();
		List phe = new1.getPhenomenonList();
		String line = "\n" + from + " " + lineType + " " + to + getpheLine(phe) + " \"" + lineName + "\"";
		return line;

	}

	public static String getpheLine(List<Phenomenon> pheList) {
		String str = " {";
		for (int i = 0; i < pheList.size(); i++) {
			Phenomenon phe = pheList.get(i);
			String type = phe.getType();
			String name = phe.getName();
			str += type + " " + name + ", ";
		}
		str = str.substring(0, str.length() - 2);
		if (str.length() > 0) {
			str += "}";
		}
		return str;
	}

	// ======================== pf -> project ============================

	public static void transfer2Project(Project oldProject, String fileName) {
		// 转为project
		File xmlFile = ProblemEditor.performSave1(fileName);
		Project newProject = ProblemEditor.transformXML(xmlFile);
		changeMachineWithNewProject(oldProject, newProject);
		changeProblemDomainWithNewProject(oldProject, newProject);
		changeRequirementWithNewProject(oldProject, newProject);
		changeInterfaceWithNewProject(oldProject, newProject);
		changeConstraintWithNewProject(oldProject, newProject);
		changeReferenceWithNewProject(oldProject, newProject);
	}

	public static void changeMachineWithNewProject(Project oldProject, Project newProject) {
		String name = newProject.getContextDiagram().getMachine().getName();
		String shortname = newProject.getContextDiagram().getMachine().getShortname();
		if (name == null && shortname == null) {
			oldProject.getContextDiagram().setMachine(null);
			oldProject.getProblemDiagram().getContextDiagram().setMachine(null);
			return;
		}
		name = name == null ? "" : name;
		shortname = shortname == null ? "" : shortname;
		if (oldProject.getContextDiagram().getMachine() == null) {
			oldProject.getContextDiagram().setMachine(new Machine(newProject.getContextDiagram().getMachine()));
		} else {
			oldProject.getContextDiagram().getMachine().setName(name);
			oldProject.getContextDiagram().getMachine().setShortname(shortname);
			oldProject.getProblemDiagram().getContextDiagram().getMachine().setName(name);
			oldProject.getProblemDiagram().getContextDiagram().getMachine().setShortname(shortname);
		}
	}

	public static void changeProblemDomainWithNewProject(Project oldProject, Project newProject) {
		List<ProblemDomain> oldList = oldProject.getContextDiagram().getProblemDomainList();
		List<ProblemDomain> newList = newProject.getContextDiagram().getProblemDomainList();
		// change & add
		for (ProblemDomain newItem : newList) {
			boolean isFind = false;
			for (ProblemDomain item : oldList) {
				if (newItem.getName() == item.getName()) {
					if (newItem.getShortname() != item.getShortname()) {
						item.setShortname(newItem.getShortname());
						item.setProperty(newItem.getProperty());
					}
					isFind = true;
				} else if (newItem.getShortname() == item.getShortname()) {
					if (newItem.getName() != item.getName()) {
						item.setName(newItem.getName());
						item.setProperty(newItem.getProperty());
					}
					isFind = true;
				}
			}
			if (!isFind) {
				oldList.add(newItem);
			}
		}
		// delete
		int i = 0;
		int len = oldList.size();
		for (i = len - 1; i >= 0; i--) {
			ProblemDomain item = oldList.get(i);
			boolean isFind = false;
			for (ProblemDomain newItem : newList) {
				if (newItem.getName() == item.getName()) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				oldList.remove(i);
			}
		}
	}

	public static void changeRequirementWithNewProject(Project oldProject, Project newProject) {
		List<Requirement> oldList = oldProject.getProblemDiagram().getRequirementList();
		List<Requirement> newList = newProject.getProblemDiagram().getRequirementList();
		// change & add
		for (Requirement newItem : newList) {
			boolean isFind = false;
			for (Requirement item : oldList) {
				if (newItem.getName() == item.getName()) {
					if (newItem.getShortname() != item.getShortname()) {
						item.setShortname(newItem.getShortname());
					}
					isFind = true;
				} else if (newItem.getShortname() == item.getShortname()) {
					if (newItem.getName() != item.getName()) {
						item.setName(newItem.getName());
					}
					isFind = true;
				}
			}
			if (!isFind) {
				oldList.add(newItem);
			}
		}
		// delete
		int i = 0;
		int len = oldList.size();
		for (i = len - 1; i >= 0; i--) {
			Requirement item = oldList.get(i);
			boolean isFind = false;
			for (Requirement newItem : newList) {
				if (newItem.getName() == item.getName()) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				oldList.remove(i);
			}
		}

	}

	public static void changeInterfaceWithNewProject(Project oldProject, Project newProject) {
		List<Interface> oldList = oldProject.getContextDiagram().getInterfaceList();
		List<Interface> newList = newProject.getContextDiagram().getInterfaceList();
		for (Interface newItem : newList) {
			boolean isFind = false;
			for (Interface item : oldList) {
				if (newItem.getFrom() == item.getFrom() && newItem.getTo() == item.getTo()) {
					if (newItem.getName() != null)
						item.setName(newItem.getName());
					// deal with phe
//					changePhenomenon(item.getPhenomenonList(),newItem.getPhenomenonList());
					isFind = true;
				}
			}
			if (!isFind) {
				oldList.add(newItem);
			}
		}
		// delete
		for (int i = oldList.size() - 1; i >= 0; i--) {
			Interface item = oldList.get(i);
			boolean isFind = false;
			for (Interface newItem : newList) {
				if (newItem.getFrom() == item.getFrom() && newItem.getTo() == item.getTo()) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				oldList.remove(i);
			}
		}
	}

	public static void changeConstraintWithNewProject(Project oldProject, Project newProject) {
		List<Constraint> oldList = oldProject.getProblemDiagram().getConstraintList();
		List<Constraint> newList = newProject.getProblemDiagram().getConstraintList();
		for (Constraint newItem : newList) {
			boolean isFind = false;
			for (Constraint item : oldList) {
				if (newItem.getFrom() == item.getFrom() && newItem.getTo() == item.getTo()) {
					if (newItem.getName() != null)
						item.setName(newItem.getName());
					// deal with phe
//					changePhenomenon(item.getPhenomenonList(),newItem.getPhenomenonList());
					isFind = true;
				}
			}
			if (!isFind) {
				oldList.add(newItem);
			}
		}
		// delete
		for (int i = oldList.size() - 1; i >= 0; i--) {
			Constraint item = oldList.get(i);
			boolean isFind = false;
			for (Constraint newItem : newList) {
				if (newItem.getFrom() == item.getFrom() && newItem.getTo() == item.getTo()) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				oldList.remove(i);
			}
		}

	}

	public static void changeReferenceWithNewProject(Project oldProject, Project newProject) {
		List<Reference> oldList = oldProject.getProblemDiagram().getReferenceList();
		List<Reference> newList = newProject.getProblemDiagram().getReferenceList();
		for (Reference newItem : newList) {
			boolean isFind = false;
			for (Reference item : oldList) {
				if (newItem.getFrom() == item.getFrom() && newItem.getTo() == item.getTo()) {
					if (newItem.getName() != null)
						item.setName(newItem.getName());
					// deal with phe
//					changePhenomenon(item.getPhenomenonList(),newItem.getPhenomenonList());
					isFind = true;
				}
			}
			if (!isFind) {
				oldList.add(newItem);
			}
		}
		// delete
		for (int i = oldList.size() - 1; i >= 0; i--) {
			Reference item = oldList.get(i);
			boolean isFind = false;
			for (Reference newItem : newList) {
				if (newItem.getFrom() == item.getFrom() && newItem.getTo() == item.getTo()) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				oldList.remove(i);
			}
		}
	}
}
