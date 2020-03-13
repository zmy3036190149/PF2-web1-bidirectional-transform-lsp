package com.example.demo.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
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
import com.github.gumtreediff.gen.antlr3.xml.XmlTreeGenerator;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TimeTree;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

import pf.PfStandaloneSetup;

public class ASTService {
	// reset id of AST
	public static int resetId(ITree tree, int id) {
		tree.setId(id++);
		for (ITree child : tree.getChildren()) {
			id = resetId(child, id);
		}
		return id;
	}

	// add time for locTime
	public static ITree addTime(Tree tree, long time) {
		return TimeTree.deepCopy(tree, time);
	}

	// ================== project =============================
	// 根据AST生成 xml文件
	public static void generateXmlFile(ITree tree, String path) {
		ITree root = getProjectParent(tree);
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?";
		str += parseAST(root);
//		System.out.println(str);
		try {
			File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件
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

	public static ITree getProjectParent(ITree tree) {
		for (ITree child : tree.getChildren()) {
			if (child.getLabel().contentEquals("project"))
				return tree;
		}
		for (ITree child : tree.getChildren()) {
			ITree res = getProjectParent(child);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	// 遍历xml AST
	public static String parseAST(ITree tree) {
		String str = "";
		// 输出
//		System.out.print(tree.toShortString()+","+tree.getId()+"\t\t");	
//		if(tree.getParent()!=null)
//			System.out.println(tree.getParent().toShortString()+","+tree.getParent().getId());
//		else
//			System.out.println("root");
		try {
			if (tree.getType() == 10) {
				for (ITree brother : tree.getParent().getChildren()) {
					if (brother.getType() == 10) {
						if (brother == tree)
							str += ">";
						break;
					}
				}
				str += "<";
			} else if (tree.getType() == 11) {
				str += tree.getLabel();
			} else if (tree.getType() == 6) {// 属性
				str += " " + tree.getLabel();
			} else if (tree.getType() == 8) {// 属性值
				str += "=" + tree.getLabel();
			} else if (tree.getType() == 15) {// String <title >insulin </title>
				str += ">" + tree.getLabel();
			}
			// 遍历子节点
			for (ITree child : tree.getChildren()) {
				str += parseAST(child);
			}
			// 输出 />
			// <Machine machine_name="controller" machine_shortname="controller"
			// machine_locality="100,250,50,100"/>

			if (tree.getType() == 10) {
				boolean isFind = false;
				for (ITree child : tree.getChildren()) {
					if (child.getType() == 10 || child.getType() == 15)
						isFind = true;
				}
				if (isFind) {
					/*
					 * 1 <Element 2 interface_description="b:LC!{OffPulse,Onpulse}" >3 4 <Phenomenon
					 * phenomenon_no="1" /> 5 <Phenomenon phenomenon_no="2" /> </Element>
					 */
					// <title>ContextDiagram</title>
//					System.out.println("find child.getType()==10 || child.getType()==15");
					str += "</" + tree.getChild(0).getLabel() + ">\n";
				} else {
					// <DesignDomain/>
					// <Machine machine_name="Light Controller" machine_shortname="LC"
					// machine_locality="280,180,0,0"/>
					str += "/>\n";
				}
				// <title>insulin </title>

			}
//		System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	public static TreeContext generateXmlAST(String filePath) {
		XmlTreeGenerator xmlTreeGenerator = new XmlTreeGenerator();
		try {
			return xmlTreeGenerator.generateFromFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取最新的project
	public static Project getProject(String projectAddress) {
		Project project = new Project();
		SAXReader saxReader = new SAXReader();
		try {
			File xmlFile = new File(projectAddress);
			Document document = saxReader.read(xmlFile);
			Element projectElement = document.getRootElement();
			Element titleElement = projectElement.element("title");
			Element contextDiagramElement = projectElement.element("ContextDiaagram");
			Element problemDiagramElement = projectElement.element("ProblemDiagram");
			String title = titleElement.getText();

			Element cdtitleElement = contextDiagramElement.element("title");
			Element machineElement = contextDiagramElement.element("Machine");
			Element problemDomainListElement = contextDiagramElement.element("ProblemDomain");
			Element interfaceListElement = contextDiagramElement.element("Interface");

			String cdtitle = cdtitleElement.getText();
			Machine machine = getMachine(machineElement);
			List<ProblemDomain> problemDomainList = getProblemDomainList(problemDomainListElement);
			List<Interface> interfaceList = getInterfaceList(interfaceListElement);

			ContextDiagram contextDiagram = new ContextDiagram();
			contextDiagram.setTitle(title);
			contextDiagram.setMachine(machine);
			contextDiagram.setProblemDomainList(problemDomainList);
			contextDiagram.setInterfaceList(interfaceList);

			Element pdtitleElement = problemDiagramElement.element("title");
			Element requirementListElement = problemDiagramElement.element("Requirement");
			Element constraintListElement = problemDiagramElement.element("Constraint");
			Element referenceListElement = problemDiagramElement.element("Reference");

			String pdtitle = pdtitleElement.getText();
			List<Requirement> requirementList = getRequirementList(requirementListElement);
			List<Constraint> constraintList = getConstraintList(constraintListElement);
			List<Reference> referenceList = getReferenceList(referenceListElement);

			ProblemDiagram problemDiagram = new ProblemDiagram();
			problemDiagram.setTitle(pdtitle);
			problemDiagram.setContextDiagram(contextDiagram);
			problemDiagram.setRequirementList(requirementList);
			problemDiagram.setConstraintList(constraintList);
			problemDiagram.setReferenceList(referenceList);

			project.setTitle(title);
			project.setContextDiagram(contextDiagram);
			project.setProblemDiagram(problemDiagram);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}

	private static Machine getMachine(Element machineElement) {
		// TODO Auto-generated method stub
		Machine machine = new Machine();

		String name = machineElement.attributeValue("machine_name");
		if (name == null)
			return null;
		name = name.replaceAll("&#x000A", "\n");
		String shortname = machineElement.attributeValue("machine_shortname");
		String machine_locality = machineElement.attributeValue("machine_locality");
		String[] locality = machine_locality.split(",");
		int x = Integer.parseInt(locality[0]);
		int y = Integer.parseInt(locality[1]);
		int h = Integer.parseInt(locality[2]);
		int w = Integer.parseInt(locality[3]);

		machine.setName(name);
		machine.setShortname(shortname);
		machine.setH(h);
		machine.setW(w);
		machine.setX(x);
		machine.setY(y);

		return machine;
	}

	private static List<ProblemDomain> getProblemDomainList(Element problemDomainListElement) {
		// TODO Auto-generated method stub
		List<ProblemDomain> problemDomainList = new ArrayList<ProblemDomain>();

		Element givenDomainListElement = problemDomainListElement.element("GivenDomain");
		Element designDomainListElement = problemDomainListElement.element("DesignDomain");
		List<?> givenDomainElementList = givenDomainListElement.elements("Element");
		List<?> designDomainElementList = designDomainListElement.elements("Element");

		for (Object object : givenDomainElementList) {
			ProblemDomain problemDomain = new ProblemDomain();
			Element givenDomainElement = (Element) object;

			int no = Integer.parseInt(givenDomainElement.attributeValue("problemdomain_no"));
			String name = givenDomainElement.attributeValue("problemdomain_name");
			name = name.replaceAll("&#x000A", "\n");
			String shortname = givenDomainElement.attributeValue("problemdomain_shortname");
			String type = givenDomainElement.attributeValue("problemdomain_type");
			String property = "GivenDomain";
			String problemdomain_locality = givenDomainElement.attributeValue("problemdomain_locality");
			String[] locality = problemdomain_locality.split(",");
			int x = Integer.parseInt(locality[0]);
			int y = Integer.parseInt(locality[1]);
			int h = Integer.parseInt(locality[2]);
			int w = Integer.parseInt(locality[3]);

			problemDomain.setNo(no);
			problemDomain.setName(name);
			problemDomain.setShortname(shortname);
			problemDomain.setType(type);
			problemDomain.setProperty(property);
			problemDomain.setX(x);
			problemDomain.setY(y);
			problemDomain.setH(h);
			problemDomain.setW(w);

			problemDomainList.add(problemDomain);
		}
		for (Object object : designDomainElementList) {
			Element designDomainElement = (Element) object;

			int no = Integer.parseInt(designDomainElement.attributeValue("problemdomain_no"));
			String name = designDomainElement.attributeValue("problemdomain_name");
			String shortname = designDomainElement.attributeValue("problemdomain_shortname");
			String type = designDomainElement.attributeValue("problemdomain_type");
			String property = "DesignDomain";
			String problemdomain_locality = designDomainElement.attributeValue("problemdomain_locality");
			String[] locality = problemdomain_locality.split(",");
			int x = Integer.parseInt(locality[0]);
			int y = Integer.parseInt(locality[1]);
			int h = Integer.parseInt(locality[2]);
			int w = Integer.parseInt(locality[3]);

			ProblemDomain problemDomain = new ProblemDomain();
			problemDomain.setNo(no);
			problemDomain.setName(name);
			problemDomain.setShortname(shortname);
			problemDomain.setType(type);
			problemDomain.setProperty(property);
			problemDomain.setX(x);
			problemDomain.setY(y);
			problemDomain.setH(h);
			problemDomain.setW(w);

			problemDomainList.add(problemDomain);
		}

		return problemDomainList;
	}

	private static List<Interface> getInterfaceList(Element interfaceListElement) {
		// TODO Auto-generated method stub
		List<Interface> interfaceList = new ArrayList<Interface>();
		List<?> interfaceElementList = interfaceListElement.elements("Element");

		for (Object object : interfaceElementList) {
			Element interfaceElement = (Element) object;
			List<?> phenomenonElementList = interfaceElement.elements("Phenomenon");

			int no = Integer.parseInt(interfaceElement.attributeValue("interface_no"));
			String name = interfaceElement.attributeValue("interface_name");
			String description = interfaceElement.attributeValue("interface_description");
			String from = interfaceElement.attributeValue("interface_from").replaceAll("&#x000A", "\n");
			;
			String to = interfaceElement.attributeValue("interface_to").replaceAll("&#x000A", "\n");
			;
			String interface_locality = interfaceElement.attributeValue("interface_locality");
			List<Phenomenon> phenomenonList = getPhenomenonList(phenomenonElementList);
			String[] locality = interface_locality.split(",");
			int x1 = Integer.parseInt(locality[0]);
			int x2 = Integer.parseInt(locality[1]);
			int y1 = Integer.parseInt(locality[2]);
			int y2 = Integer.parseInt(locality[3]);

			Interface inte = new Interface();
			inte.setNo(no);
			inte.setName(name);
			inte.setDescription(description);
			inte.setFrom(from);
			inte.setTo(to);
			inte.setPhenomenonList(phenomenonList);
			inte.setX1(x1);
			inte.setY1(y1);
			inte.setX2(x2);
			inte.setY2(y2);

			interfaceList.add(inte);
		}

		return interfaceList;
	}

	private static List<Phenomenon> getPhenomenonList(List<?> phenomenonElementList) {
		// TODO Auto-generated method stub
		List<Phenomenon> phenomenonList = new ArrayList<Phenomenon>();
		for (Object object : phenomenonElementList) {
			Element phenomenonElement = (Element) object;

			int phe_no = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_no"));
			String phe_name = phenomenonElement.attributeValue("phenomenon_name");
			String phe_type = phenomenonElement.attributeValue("phenomenon_type");
			String phe_from = phenomenonElement.attributeValue("phenomenon_from").replaceAll("&#x000A", "\n");
			;
			String phe_to = phenomenonElement.attributeValue("phenomenon_to").replaceAll("&#x000A", "\n");
			;

			Phenomenon phenomenon = new Phenomenon();
			phenomenon.setNo(phe_no);
			phenomenon.setName(phe_name);
			phenomenon.setType(phe_type);
			phenomenon.setFrom(phe_from);
			phenomenon.setTo(phe_to);

			phenomenonList.add(phenomenon);
		}
		return phenomenonList;
	}

	private static List<Requirement> getRequirementList(Element requirementListElement) {
		// TODO Auto-generated method stub
		List<?> requirementElementList = requirementListElement.elements("Element");
		List<Requirement> requirementList = new ArrayList<Requirement>();
		for (Object object : requirementElementList) {
			Element requirementElement = (Element) object;

			Requirement requirement = getRequirement(requirementElement);

			requirementList.add(requirement);
		}
		return requirementList;
	}

	private static Requirement getRequirement(Element requirementElement) {
		// TODO Auto-generated method stub
		Requirement requirement = new Requirement();

		int no = Integer.parseInt(requirementElement.attributeValue("requirement_no"));
		String name = requirementElement.attributeValue("requirement_context").replaceAll("&#x000A", "\n");
		String shortname = requirementElement.attributeValue("requirement_shortname");
		if (shortname == null) {
			shortname = name.replaceAll(" ", "");
		}
		String requirement_locality = requirementElement.attributeValue("requirement_locality");
		String[] locality = requirement_locality.split(",");
		int x = Integer.parseInt(locality[0]);
		int y = Integer.parseInt(locality[1]);
		int h = Integer.parseInt(locality[2]);
		int w = Integer.parseInt(locality[3]);

		requirement.setNo(no);
		requirement.setName(name);
		requirement.setShortname(shortname);
		requirement.setX(x);
		requirement.setY(y);
		requirement.setH(h);
		requirement.setW(w);

		return requirement;
	}

	private static List<Constraint> getConstraintList(Element constraintListElement) {
		// TODO Auto-generated method stub
		List<Constraint> constraintList = new ArrayList<Constraint>();
		List<?> constraintElementList = constraintListElement.elements("Element");
		for (Object object : constraintElementList) {
			Element constraintElement = (Element) object;
			List<?> phenomenonElementList = constraintElement.elements("Phenomenon");

			int no = Integer.parseInt(constraintElement.attributeValue("constraint_no"));
			String name = constraintElement.attributeValue("constraint_name");
			String description = constraintElement.attributeValue("constraint_description");
			String from = constraintElement.attributeValue("constraint_from").replaceAll("&#x000A", "\n");
			;
			String to = constraintElement.attributeValue("constraint_to").replaceAll("&#x000A", "\n");
			;
			List<RequirementPhenomenon> phenomenonList = getRequirementPhenomenonList(phenomenonElementList);
			String constraint_locality = constraintElement.attributeValue("constraint_locality");
			String[] locality = constraint_locality.split(",");
			int x1 = Integer.parseInt(locality[0]);
			int x2 = Integer.parseInt(locality[1]);
			int y1 = Integer.parseInt(locality[2]);
			int y2 = Integer.parseInt(locality[3]);

			Constraint constraint = new Constraint();
			constraint.setNo(no);
			constraint.setName(name);
			constraint.setDescription(description);
			constraint.setFrom(from);
			constraint.setTo(to);
			constraint.setPhenomenonList(phenomenonList);
			constraint.setX1(x1);
			constraint.setX2(x2);
			constraint.setY1(y1);
			constraint.setY2(y2);

			constraintList.add(constraint);
		}
		return constraintList;
	}

	private static List<RequirementPhenomenon> getRequirementPhenomenonList(List<?> phenomenonElementList) {
		// TODO Auto-generated method stub
		List<RequirementPhenomenon> phenomenonList = new ArrayList<RequirementPhenomenon>();
		for (Object object : phenomenonElementList) {
			Element phenomenonElement = (Element) object;

			int phe_no = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_no"));
			String phe_name = phenomenonElement.attributeValue("phenomenon_name");
			String phe_type = phenomenonElement.attributeValue("phenomenon_type");
			String phe_from = phenomenonElement.attributeValue("phenomenon_from").replaceAll("&#x000A", "\n");
			;
			String phe_to = phenomenonElement.attributeValue("phenomenon_to").replaceAll("&#x000A", "\n");
			;
			String phe_constraint = phenomenonElement.attributeValue("phenomenon_constraint");
			int phe_requirement = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_requirement"));

			RequirementPhenomenon phenomenon = new RequirementPhenomenon();
			phenomenon.setNo(phe_no);
			phenomenon.setName(phe_name);
			phenomenon.setType(phe_type);
			phenomenon.setConstraint(phe_constraint);
			phenomenon.setRequirement(phe_requirement);
			phenomenon.setFrom(phe_from);
			phenomenon.setTo(phe_to);

			phenomenonList.add(phenomenon);
		}
		return phenomenonList;
	}

	private static List<Reference> getReferenceList(Element referenceListElement) {
		// TODO Auto-generated method stub
		List<Reference> referenceList = new ArrayList<Reference>();
		List<?> referenceElementList = referenceListElement.elements("Element");
		for (Object object : referenceElementList) {
			Element referenceElement = (Element) object;
			List<?> phenomenonElementList = referenceElement.elements("Phenomenon");

			int no = Integer.parseInt(referenceElement.attributeValue("reference_no"));
			String name = referenceElement.attributeValue("reference_name");
			String description = referenceElement.attributeValue("reference_description");
			String from = referenceElement.attributeValue("reference_from").replaceAll("&#x000A", "\n");
			;
			String to = referenceElement.attributeValue("reference_to").replaceAll("&#x000A", "\n");
			;
			List<RequirementPhenomenon> phenomenonList = getRequirementPhenomenonList(phenomenonElementList);
			String reference_locality = referenceElement.attributeValue("reference_locality");
			String[] locality = reference_locality.split(",");
			int x1 = Integer.parseInt(locality[0]);
			int x2 = Integer.parseInt(locality[1]);
			int y1 = Integer.parseInt(locality[2]);
			int y2 = Integer.parseInt(locality[3]);

			Reference reference = new Reference();
			reference.setNo(no);
			reference.setName(name);
			reference.setDescription(description);
			reference.setFrom(from);
			reference.setTo(to);
			reference.setPhenomenonList(phenomenonList);
			reference.setX1(x1);
			reference.setX2(x2);
			reference.setY1(y1);
			reference.setY2(y2);

			referenceList.add(reference);
		}
		return referenceList;
	}

	// ===================== pf =======================
	private static Deque<ITree> trees = new ArrayDeque<>();
	static int Root_Node = 0;
	static int Composite_Node_With_Semantic_Element = 1;
	static int Composite_Node = 2;
	static int Hidden_Leaf_Node = 3;
	static int Leaf_Node = 4;
	static int id = 0;

	// 根据AST生成pf文件
	public static void generatePfFile(ITree tree, String rootAddress, String path) {

//		System.out.println("generatePf");	
		String str = getPf(tree);
		FileOperation.writeToFile(str, rootAddress, path);

	}

	// 遍历 pf AST 获取pf文本
	public static String getPf(ITree tree) {
		String str = "";
		// 输出
//		System.out.print(tree.toShortString()+","+tree.getId()+"\t\t");	
//		if(tree.getParent()!=null)
//			System.out.println(tree.getParent().toShortString()+","+tree.getParent().getId());
//		else
//			System.out.println("root");
		if (tree.getType() == Hidden_Leaf_Node || tree.getType() == Leaf_Node)
			str += tree.getLabel();

		// 遍历子节点
		for (ITree child : tree.getChildren()) {
			str += getPf(child);
		}
		return str;
	}

	// 读取文件获取pf
	public String getPf(String projectAddress) {
		String res = "";
		try {
			InputStream is = new FileInputStream(projectAddress);
			int iAvail = is.available();
			byte[] bytes = new byte[iAvail];
			is.read(bytes);
			res = new String(bytes);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 根据pf文件生成AST
	public static TreeContext generateNoErrorPfAST(String filePath) {
		File file = new File(filePath);
		PfStandaloneSetup.doSetup();
		XtextResourceSet resourceSet = new XtextResourceSet();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		URI uri = URI.createURI(file.getPath());
		XtextResource xtextResource = (XtextResource) resourceSet.getResource(uri, true);
		IParseResult parseResult = xtextResource.getParseResult();
		EList<Diagnostic> error = xtextResource.getErrors();
		if (error.isEmpty()) {
			ICompositeNode rootNode = parseResult.getRootNode();
			TreeContext context = new TreeContext();
			id = 0;
			buildTree(context, rootNode);
			return context;
		}
		return null;
	}

	// 根据pf文件生成AST
	public static TreeContext generatePfAST(String filePath) {
		File file = new File(filePath);
		PfStandaloneSetup.doSetup();
		XtextResourceSet resourceSet = new XtextResourceSet();
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		URI uri = URI.createURI(file.getPath());
		XtextResource xtextResource = (XtextResource) resourceSet.getResource(uri, true);
		IParseResult parseResult = xtextResource.getParseResult();

		ICompositeNode rootNode = parseResult.getRootNode();
		TreeContext context = new TreeContext();
		id = 0;
		buildTree(context, rootNode);
		return context;

	}

	// 将Xtext格式AST转换为GumTree格式语法树
	public static void buildTree(TreeContext context, INode node) {
		String tokenName = "";
		int type = 0;
		String label = node.getText();
		if (node instanceof HiddenLeafNode) {// LeafNode的子类
			// TerminalRuleImpl 空格 换行
//    		System.out.println("============HiddenLeafNode============");
//    		System.out.println("$"+node.getText()+"#");
//    		label = node.getText(); 
			tokenName = "3HiddenLeafNode";
			type = Hidden_Leaf_Node;
		} else if (node instanceof LeafNode) {
			// KeywordImpl problem:
			// RuleCallImpl LightControler LC "Light Controller"
			// EnumLiteralDeclarationImpl M
//    		System.out.println("============LeafNode============");
//    		System.out.println("$"+node.getText()+"#");
//    		label = node.getText(); 
			tokenName = "4LeafNode";
			type = Leaf_Node;
		} else if (node instanceof RootNode) {// ParserRuleImpl
//    		System.out.println("============0 rootnode============");
			label = ITree.NO_LABEL;
			tokenName = "0RootNode";
			type = Root_Node;
//    		for(INode child : ((ICompositeNode) node).getChildren()) {    		
//    			buildTree(context,child);
//    		}
		} else if (node instanceof CompositeNodeWithSemanticElement) {
			// RuleCallImpl
//    		System.out.println("==============1 CompositeNodeWithSemanticElement ====="); 
			label = ITree.NO_LABEL;
			tokenName = "1CompositeNodeWithSemanticElement";
			type = Composite_Node_With_Semantic_Element;
//    		for(INode child : ((ICompositeNode) node).getChildren()) {              		
//    			buildTree(context,child);
//    		}       	
		} else if (node instanceof CompositeNode) {
			// RuleCallImpl
//        	System.out.println("============2 CompositeNode =====");
			label = ITree.NO_LABEL;
			tokenName = "2CompositeNode";
			type = Composite_Node;
//    		for(INode child : ((ICompositeNode) node).getChildren()) {         		
//    			buildTree(context,child);
//    		} 	
		}
		ITree t = context.createTree(type, label, tokenName);

		int start = node.getTotalOffset();
		int length = node.getTotalLength();
		t.setPos(start);
		t.setLength(length);
		t.setId(id);
		id++;

		if (trees.isEmpty())
			context.setRoot(t);
		else
			t.setParentAndUpdateChildren(trees.peek());
//        System.out.println(t.toShortString()+","+t.getId());
		if (node instanceof ICompositeNode) {
			trees.push(t);
			for (INode child : ((ICompositeNode) node).getChildren()) {
				buildTree(context, child);
			}
			trees.pop();
		}
	}

}
