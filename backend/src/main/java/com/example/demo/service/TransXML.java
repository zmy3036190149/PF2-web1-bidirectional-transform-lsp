package com.example.demo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.example.demo.bean.Cell;
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
import com.example.demo.service.AddressService;
public class TransXML {
	//从xml文件中读取Project
	private static String rootAddress = AddressService.rootAddress;
	public  Project getProject(String projectAddress) {
		Project project = new Project();
		SAXReader saxReader = new SAXReader();
		try {
			File xmlFile = new File(rootAddress + projectAddress + "\\Project.xml");
			Document document = saxReader.read(xmlFile);
			
			Element projectElement = document.getRootElement();
			Element titleElement = projectElement.element("title");
			Element fileListElement = projectElement.element("fileList");
			Element contextDiagramElement = fileListElement.element("ContextDiagram");
			Element problemDiagramElement = fileListElement.element("ProblemDiagram");
			
			String title = titleElement.getText();
			String contextDiagramName = contextDiagramElement.getText();
			String problemDiagramName = problemDiagramElement.getText();
			ContextDiagram contextDiagram = getContextDiagram(projectAddress,contextDiagramName);
			ProblemDiagram problemDiagram = getProblemDiagram(projectAddress,problemDiagramName);
								
			project.setTitle(title);
			project.setContextDiagram(contextDiagram);
			project.setProblemDiagram(problemDiagram);	
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}
	
	private ContextDiagram getContextDiagram(String projectAddress,String contextDiagramName) {
		// TODO Auto-generated method stub
		ContextDiagram contextDiagram = new ContextDiagram();
		SAXReader saxReader = new SAXReader();
		try {
			File contextDiagramFile = new File(rootAddress + projectAddress + "\\" + contextDiagramName + ".xml");
			if(!contextDiagramFile.exists()) {
				return null;
			}
			Document document = saxReader.read(contextDiagramFile);
			
			Element contextDiagramElement = document.getRootElement();
			Element titleElement = contextDiagramElement.element("title");
			Element machineElement = contextDiagramElement.element("Machine");
			Element problemDomainListElement = contextDiagramElement.element("ProblemDomain");
			Element interfaceListElement = contextDiagramElement.element("Interface");
					
			String title = titleElement.getText();
			Machine machine = getMachine(machineElement);
			List<ProblemDomain> problemDomainList= getProblemDomainList(problemDomainListElement);
			List<Interface> interfaceList = getInterfaceList(interfaceListElement);
			
			contextDiagram.setTitle(title);
			contextDiagram.setMachine(machine);
			contextDiagram.setProblemDomainList(problemDomainList);
			contextDiagram.setInterfaceList(interfaceList);
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contextDiagram;
	}

	private ProblemDiagram getProblemDiagram(String projectAddress,String problemDiagramName) {
		// TODO Auto-generated method stub
		ProblemDiagram problemDiagram = new ProblemDiagram();
		SAXReader saxReader = new SAXReader();
		try {
			File problemDiagramFile = new File(rootAddress + projectAddress + "\\" + problemDiagramName + ".xml");
			if(!problemDiagramFile.exists()) {
				return null;
			}
			Document document = saxReader.read(problemDiagramFile);
			
			Element problemDiagramElement = document.getRootElement();
			Element titleElement = problemDiagramElement.element("title");
			Element contextDiagramElement = problemDiagramElement.element("ContextDiagram");
			Element requirementListElement = problemDiagramElement.element("Requirement");
			Element constraintListElement = problemDiagramElement.element("Constraint");
			Element referenceListElement = problemDiagramElement.element("Reference");
			
			String title = titleElement.getText();
			String contextDiagramName = contextDiagramElement.getText();
			ContextDiagram contextDiagram = getContextDiagram(projectAddress,contextDiagramName);
			List<Requirement> requirementList = getRequirementList(requirementListElement);
			List<Constraint> constraintList = getConstraintList(constraintListElement);
			List<Reference> referenceList = getReferenceList(referenceListElement);
			
			problemDiagram.setTitle(title);
			problemDiagram.setContextDiagram(contextDiagram);
			problemDiagram.setRequirementList(requirementList);
			problemDiagram.setConstraintList(constraintList);
			problemDiagram.setReferenceList(referenceList);
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return problemDiagram;
	}
	
	private Machine getMachine(Element machineElement) {
		// TODO Auto-generated method stub
		Machine machine = new Machine();
		
		String name = machineElement.attributeValue("machine_name");
		String shortname = machineElement.attributeValue("machine_shortname");
		String machine_locality = machineElement.attributeValue("machine_locality");
		String[] locality= machine_locality.split(",");
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

	private List<ProblemDomain> getProblemDomainList(Element problemDomainListElement) {
		// TODO Auto-generated method stub
		List<ProblemDomain> problemDomainList = new ArrayList<ProblemDomain> ();
		
		Element givenDomainListElement = problemDomainListElement.element("GivenDomain");
		Element designDomainListElement = problemDomainListElement.element("DesignDomain");
		List<?> givenDomainElementList = givenDomainListElement.elements("Element");
		List<?> designDomainElementList = designDomainListElement.elements("Element ");
		
		for(Object object : givenDomainElementList) {
			ProblemDomain problemDomain = new ProblemDomain();
			Element givenDomainElement = (Element) object;
			
			int no = Integer.parseInt(givenDomainElement.attributeValue("problemdomain_no"));
			String name = givenDomainElement.attributeValue("problemdomain_name");
			String shortname = givenDomainElement.attributeValue("problemdomain_shortname");
			String type = givenDomainElement.attributeValue("problemdomain_type");
			String property = "GivenDomain";
			String problemdomain_locality = givenDomainElement.attributeValue("problemdomain_locality");
			String[] locality= problemdomain_locality.split(",");
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
		for(Object object : designDomainElementList) {
			Element designDomainElement = (Element) object;
			
			int no = Integer.parseInt(designDomainElement.attributeValue("problemdomain_no"));
			String name = designDomainElement.attributeValue("problemdomain_name");
			String shortname = designDomainElement.attributeValue("problemdomain_shortname");
			String type = designDomainElement.attributeValue("problemdomain_type");
			String property = "DesignDomain";
			String problemdomain_locality = designDomainElement.attributeValue("locality");
			String[] locality= problemdomain_locality.split(",");
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

	private List<Interface> getInterfaceList(Element interfaceListElement) {
		// TODO Auto-generated method stub
		List<Interface> interfaceList = new ArrayList<Interface>();
		List<?> interfaceElementList = interfaceListElement.elements("Element");
		
		for(Object object : interfaceElementList) {
			Element interfaceElement = (Element) object;
			List<?> phenomenonElementList = interfaceElement.elements("Phenomenon");
			
			int no = Integer.parseInt(interfaceElement.attributeValue("interface_no"));
			String name = interfaceElement.attributeValue("interface_name");
			String description = interfaceElement.attributeValue("interface_description");
			String from = interfaceElement.attributeValue("interface_from");
			String to = interfaceElement.attributeValue("interface_to");
			String interface_locality = interfaceElement.attributeValue("interface_locality");
			List<Phenomenon> phenomenonList = getPhenomenonList(phenomenonElementList);
			String[] locality= interface_locality.split(",");
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
	
	private List<Phenomenon> getPhenomenonList(List<?> phenomenonElementList) {
		// TODO Auto-generated method stub
		List<Phenomenon> phenomenonList = new ArrayList<Phenomenon> ();
		for(Object object : phenomenonElementList) {
			Element phenomenonElement = (Element)object;
			
			int phenomenon_no = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_no"));
			String phenomenon_name = phenomenonElement.attributeValue("phenomenon_name");
			String phenomentn_type = phenomenonElement.attributeValue("phenomenon_type");
			String phenomenon_from = phenomenonElement.attributeValue("phenomenon_from");
			String phenomenon_to = phenomenonElement.attributeValue("phenomenon_to");
			
			Phenomenon phenomenon = new Phenomenon();
			phenomenon.setPhenomenon_no(phenomenon_no);
			phenomenon.setPhenomenon_name(phenomenon_name);
			phenomenon.setPhenomenon_type(phenomentn_type);
			phenomenon.setPhenomenon_from(phenomenon_from);
			phenomenon.setPhenomenon_to(phenomenon_to);
			
			phenomenonList.add(phenomenon);
		}
		return phenomenonList;
	}

	private List<Requirement> getRequirementList(Element requirementListElement) {
		// TODO Auto-generated method stub
		List<?> requirementElementList = requirementListElement.elements("Element");
		List<Requirement> requirementList = new ArrayList<Requirement>();
		for(Object object : requirementElementList) {
			Element requirementElement = (Element)object;
			
			Requirement requirement = getRequirement(requirementElement);			
			
			requirementList.add(requirement);
		}
		return requirementList;
	}

	
	private Requirement getRequirement(Element requirementElement) {
		// TODO Auto-generated method stub
		Requirement requirement = new Requirement();
		
		int no = Integer.parseInt(requirementElement.attributeValue("requirement_no"));
		String name = requirementElement.attributeValue("requirement_context");
		String requirement_locality = requirementElement.attributeValue("requirement_locality");
		String[] locality= requirement_locality.split(",");
		int x = Integer.parseInt(locality[0]);
		int y = Integer.parseInt(locality[1]);
		int h = Integer.parseInt(locality[2]);
		int w = Integer.parseInt(locality[3]);
		
		requirement.setNo(no);
		requirement.setName(name);
		requirement.setX(x);
		requirement.setY(y);
		requirement.setH(h);
		requirement.setW(w);
		
		return requirement;
	}

	private List<Constraint> getConstraintList(Element constraintListElement) {
		// TODO Auto-generated method stub
		List<Constraint> constraintList = new ArrayList<Constraint>();
		List<?> constraintElementList = constraintListElement.elements("Element");
		for(Object object : constraintElementList) {
			Element constraintElement = (Element)object;
			List<?> phenomenonElementList = constraintElement.elements("Phenomenon");
			
			int no = Integer.parseInt(constraintElement.attributeValue("no"));
			String name = constraintElement.attributeValue("constraint_name");
			String description = constraintElement.attributeValue("constraint_description");
			String from = constraintElement.attributeValue("constraint_from");
			String to = constraintElement.attributeValue("constraint_to");
			List<RequirementPhenomenon> phenomenonList = getRequirementPhenomenonList(phenomenonElementList);
			String constraint_locality = constraintElement.attributeValue("constraint_locality");
			String[] locality= constraint_locality.split(",");
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

	private List<RequirementPhenomenon> getRequirementPhenomenonList(List<?> phenomenonElementList) {
		// TODO Auto-generated method stub
		List<RequirementPhenomenon> phenomenonList = new ArrayList<RequirementPhenomenon> ();
		for(Object object : phenomenonElementList) {
			Element phenomenonElement = (Element)object;
			
			int phenomenon_no = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_no"));
			String phenomenon_name = phenomenonElement.attributeValue("phenomenon_name");
			String phenomentn_type = phenomenonElement.attributeValue("phenomentn_type");
			String phenomenon_from = phenomenonElement.attributeValue("phenomenon_from");
			String phenomenon_to = phenomenonElement.attributeValue("phenomenon_to");
			String phenomenon_constraint = phenomenonElement.attributeValue("phenomenon_constraint");
			int phenomenon_requirement = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_requirement"));
			
			RequirementPhenomenon phenomenon = new RequirementPhenomenon();
			phenomenon.setPhenomenon_no(phenomenon_no);
			phenomenon.setPhenomenon_name(phenomenon_name);
			phenomenon.setPhenomenon_type(phenomentn_type);
			phenomenon.setPhenomenon_constraint(phenomenon_constraint);
			phenomenon.setPhenomenon_requirement(phenomenon_requirement);
			phenomenon.setPhenomenon_from(phenomenon_from);
			phenomenon.setPhenomenon_to(phenomenon_to);
			
			phenomenonList.add(phenomenon);
		}
		return phenomenonList;
	}

	private List<Reference> getReferenceList(Element referenceListElement) {
		// TODO Auto-generated method stub
		List<Reference> referenceList = new ArrayList<Reference>();
		List<?> referenceElementList = referenceListElement.elements("Element");
		for(Object object : referenceElementList) {
			Element referenceElement = (Element)object;
			List<?> phenomenonElementList = referenceElement.elements("Phenomenon");
			
			int no = Integer.parseInt(referenceElement.attributeValue("reference_no"));
			String name = referenceElement.attributeValue("reference_name");
			String description = referenceElement.attributeValue("reference_description");
			String from = referenceElement.attributeValue("reference_from");
			String to = referenceElement.attributeValue("reference_to");
			List<RequirementPhenomenon> phenomenonList = getRequirementPhenomenonList(phenomenonElementList);
			String reference_locality = referenceElement.attributeValue("reference_locality");
			String[] locality= reference_locality.split(",");
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

	public static void saveProject(Project project, String projectAddress) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");

		Machine machine = project.getContextDiagram().getMachine();
		List<ProblemDomain> problemDomains = project.getContextDiagram().getProblemDomainList();
		List<Requirement> requirements = project.getProblemDiagram().getRequirementList(); 
		List<Interface> interfaces = project.getContextDiagram().getInterfaceList();
		List<Reference> references = project.getProblemDiagram().getReferenceList();
		List<Constraint> constraints = project.getProblemDiagram().getConstraintList();
		List<Cell> cellList = new ArrayList<Cell>();
		
		Element mxGraphModelElement = document.addElement("mxGraphModel");
		Element rootElement = mxGraphModelElement.addElement("root");
		Element mxCellEle0 = rootElement.addElement("mxCell");
		mxCellEle0.addAttribute("id", String.valueOf(0));
		Element mxCellEle1 = rootElement.addElement("mxCell");
		mxCellEle1.addAttribute("id", String.valueOf(1));
		mxCellEle1.addAttribute("parent", String.valueOf(0));
		int i = 2;
		if(machine != null) {
			Cell cell = new Cell();
			cell.setId(i);
			cell.setValue(machine.getShortname());
			cell.setType("machine");
			cellList.add(cell);
			
			Element mxCellEle = rootElement.addElement("mxCell");
			mxCellEle.addAttribute("id", String.valueOf(i++));
			mxCellEle.addAttribute("parent", String.valueOf(1));
			mxCellEle.addAttribute("abbr", machine.getShortname());
			mxCellEle.addAttribute("vertex", String.valueOf(1));
			mxCellEle.addAttribute("style", "shape=machine");
			mxCellEle.addAttribute("value", machine.getName());
			Element mxGeometryEle = mxCellEle.addElement("mxGeometry");
			mxGeometryEle.addAttribute("as", "geometry");
			mxGeometryEle.addAttribute("height", String.valueOf(machine.getH()));
			mxGeometryEle.addAttribute("width", String.valueOf(machine.getW()));
			mxGeometryEle.addAttribute("y", String.valueOf(machine.getY()));
			mxGeometryEle.addAttribute("x", String.valueOf(machine.getX()));
			
		}
		
		if(problemDomains != null) {
			for(ProblemDomain problemDomain: problemDomains) {
				Cell cell = new Cell();
				cell.setId(i);
				cell.setValue(problemDomain.getShortname());
				cell.setType("domain");
				cellList.add(cell);
				
				Element mxCellEle = rootElement.addElement("mxCell");
				mxCellEle.addAttribute("id", String.valueOf(i++));
				mxCellEle.addAttribute("parent", String.valueOf(1));
				mxCellEle.addAttribute("abbr", problemDomain.getShortname());
				mxCellEle.addAttribute("vertex", String.valueOf(1));
				mxCellEle.addAttribute("style", "shape=domain");
				mxCellEle.addAttribute("value", problemDomain.getName());
				Element mxGeometryEle = mxCellEle.addElement("mxGeometry");
				mxGeometryEle.addAttribute("as", "geometry");
				mxGeometryEle.addAttribute("height", String.valueOf(problemDomain.getW()));
				mxGeometryEle.addAttribute("width", String.valueOf(problemDomain.getH()));
//				mxGeometryEle.addAttribute("height", String.valueOf(problemDomain.getProblemdomain_h()));
//				mxGeometryEle.addAttribute("width", String.valueOf(problemDomain.getProblemdomain_w()));
				mxGeometryEle.addAttribute("y", String.valueOf(problemDomain.getY()));
				mxGeometryEle.addAttribute("x", String.valueOf(problemDomain.getX()));
			}
		}
		
		if(requirements != null) {
			for(Requirement requirement: requirements) {
				Cell cell = new Cell();
				cell.setId(i);
				cell.setValue(requirement.getName());
				cell.setType("requirement");
				cellList.add(cell);
				
				Element mxCellEle = rootElement.addElement("mxCell");
				mxCellEle.addAttribute("id", String.valueOf(i++));
				mxCellEle.addAttribute("parent", String.valueOf(1));
				mxCellEle.addAttribute("vertex", String.valueOf(1));
				mxCellEle.addAttribute("style", "shape=requirement");
				mxCellEle.addAttribute("value", requirement.getName());
				Element mxGeometryEle = mxCellEle.addElement("mxGeometry");
				mxGeometryEle.addAttribute("as", "geometry");
				mxGeometryEle.addAttribute("height", String.valueOf(requirement.getW() * 2));
				mxGeometryEle.addAttribute("width", String.valueOf(requirement.getH() * 2));
				mxGeometryEle.addAttribute("y", String.valueOf(requirement.getY()));
				mxGeometryEle.addAttribute("x", String.valueOf(requirement.getX()));
			}
		}
		
		if(interfaces != null) {
			for(Interface inte: interfaces) {
				String from = inte.getFrom();
				String to = inte.getTo();
				List<Phenomenon> phenomenons = inte.getPhenomenonList();
				
				List<String> desList = new ArrayList<String>();
				boolean set = false;
				for(Phenomenon phenomenon: phenomenons) {
					set = false;
					for(String des: desList) {
						int j = desList.indexOf(des);
						if(phenomenon.getPhenomenon_from().equals(des.split("!")[0])) {
							des = des + "," + phenomenon.getPhenomenon_name();
							desList.set(j, des);
							set = true;
							break;
						}
					}
					if(set == false) {
						String des = phenomenon.getPhenomenon_from() + "!{" + phenomenon.getPhenomenon_name();
						desList.add(des);
					}
					
				}
				
				String property = "";
				String value = "";
				for(int j = 0; j < desList.size(); j ++ ) {
					property = property + inte.getName() + ( j + 1 ) + ":" + desList.get(j) + "};";
					value = value + inte.getName() + ( j + 1 ) + ",";
				}
				
				Element mxCellEle = rootElement.addElement("mxCell");
				mxCellEle.addAttribute("id", String.valueOf(i++));
				mxCellEle.addAttribute("parent", String.valueOf(1));
				mxCellEle.addAttribute("value", value);
				mxCellEle.addAttribute("property", property);
				mxCellEle.addAttribute("edge", String.valueOf(1));
				
				int target = 0;
				int source = 0;
				
				for(Cell cell: cellList) {
					if(cell.getValue().equals(from)) {
						mxCellEle.addAttribute("source", String.valueOf(cell.getId()));
						if(cell.getType() == "domain") {
							source = cell.getId();
						}
					}else if(cell.getValue().equals(to)) {
						mxCellEle.addAttribute("target", String.valueOf(cell.getId()));
						if(cell.getType() == "domain") {
							target = cell.getId();
						}
					}
				}
				
				Element mxGeometryEle = mxCellEle.addElement("mxGeometry");
				mxGeometryEle.addAttribute("as", "geometry");
				mxGeometryEle.addAttribute("relative", String.valueOf(1));
				if(source != 0) {
					Element mxPointEle = mxGeometryEle.addElement("mxPoint");
					mxPointEle.addAttribute("as", "targetPoint");
					mxPointEle.addAttribute("y", String.valueOf(inte.getY1()));
					mxPointEle.addAttribute("x", String.valueOf(inte.getX1()));
				}
				if(target != 0) {
					Element mxPointEle = mxGeometryEle.addElement("mxPoint");
					mxPointEle.addAttribute("as", "sourcePoint");
					mxPointEle.addAttribute("y", String.valueOf(inte.getY2()));
					mxPointEle.addAttribute("x", String.valueOf(inte.getX2()));
				}
			}
		}
		
		if(references != null) {
			for(Reference reference: references) {
				String from = reference.getFrom();
				String to = reference.getTo();
				List<RequirementPhenomenon> phenomenons = reference.getPhenomenonList();
				
				List<String> desList = new ArrayList<String>();
				boolean set = false;
				for(Phenomenon phenomenon: phenomenons) {
					set = false;
					for(String des: desList) {
						int j = desList.indexOf(des);
						if(phenomenon.getPhenomenon_from().equals(des.split("!")[0])) {
							des = des + "," + phenomenon.getPhenomenon_name();
							desList.set(j, des);
							set = true;
							break;
						}
					}
					if(set == false) {
						String des = phenomenon.getPhenomenon_from() + "!{" + phenomenon.getPhenomenon_name();
						desList.add(des);
					}
					
				}
				
				String property = "";
				String value = "";
				for(int j = 0; j < desList.size(); j ++ ) {
					property = property + reference.getName() + ( j + 1 ) + ":" + desList.get(j) + "};";
					value = value + reference.getName() + ( j + 1 ) + ",";
				}
				
				Element mxCellEle = rootElement.addElement("mxCell");
				mxCellEle.addAttribute("id", String.valueOf(i++));
				mxCellEle.addAttribute("parent", String.valueOf(1));
				mxCellEle.addAttribute("style", "dashed");
				mxCellEle.addAttribute("value", value);
				mxCellEle.addAttribute("property", property);
				mxCellEle.addAttribute("edge", String.valueOf(1));
				
				int target = 0;
				int source = 0;
				
				for(Cell cell: cellList) {
					if(cell.getValue().equals(from)) {
						mxCellEle.addAttribute("source", String.valueOf(cell.getId()));
						if(cell.getType() == "domain") {
							source = cell.getId();
						}
					}else if(cell.getValue().equals(to)) {
						mxCellEle.addAttribute("target", String.valueOf(cell.getId()));
						if(cell.getType() == "domain") {
							target = cell.getId();
						}
					}
				}
				
				Element mxGeometryEle = mxCellEle.addElement("mxGeometry");
				mxGeometryEle.addAttribute("as", "geometry");
				mxGeometryEle.addAttribute("relative", String.valueOf(1));
				if(source != 0) {
					Element mxPointEle = mxGeometryEle.addElement("mxPoint");
					mxPointEle.addAttribute("as", "targetPoint");
					mxPointEle.addAttribute("y", String.valueOf(reference.getY1()));
					mxPointEle.addAttribute("x", String.valueOf(reference.getX1()));
				}
				if(target != 0) {
					Element mxPointEle = mxGeometryEle.addElement("mxPoint");
					mxPointEle.addAttribute("as", "sourcePoint");
					mxPointEle.addAttribute("y", String.valueOf(reference.getY2()));
					mxPointEle.addAttribute("x", String.valueOf(reference.getX2()));
				}
			}
		}
		
		if(constraints != null) {
			for(Constraint constraint: constraints) {
				String from = constraint.getFrom();
				String to = constraint.getTo();
				List<RequirementPhenomenon> phenomenons = constraint.getPhenomenonList();
				
				List<String> desList = new ArrayList<String>();
				boolean set = false;
				for(Phenomenon phenomenon: phenomenons) {
					set = false;
					for(String des: desList) {
						int j = desList.indexOf(des);
						if(phenomenon.getPhenomenon_from().equals(des.split("!")[0])) {
							des = des + "," + phenomenon.getPhenomenon_name();
							desList.set(j, des);
							set = true;
							break;
						}
					}
					if(set == false) {
						String des = phenomenon.getPhenomenon_from() + "!{" + phenomenon.getPhenomenon_name();
						desList.add(des);
					}
					
				}
				
				String property = "";
				String value = "";
				for(int j = 0; j < desList.size(); j ++ ) {
					property = property + constraint.getName() + ( j + 1 ) + ":" + desList.get(j) + "};";
					value = value + constraint.getName() + ( j + 1 ) + ",";
				}
				
				Element mxCellEle = rootElement.addElement("mxCell");
				mxCellEle.addAttribute("id", String.valueOf(i++));
				mxCellEle.addAttribute("parent", String.valueOf(1));
				mxCellEle.addAttribute("style", "arrow");
				mxCellEle.addAttribute("value", value);
				mxCellEle.addAttribute("property", property);
				mxCellEle.addAttribute("edge", String.valueOf(1));
				
				int target = 0;
				int source = 0;
				
//				for(Cell cell: cellList) {
//					if(cell.getValue().equals(from) && cell.getType() == "domain") {
//						source = cell.getId();
//						mxCellEle.addAttribute("source", String.valueOf(source));
//					}else if(cell.getValue().equals(to) && cell.getType() == "domain") {
//						target = cell.getId();
//						mxCellEle.addAttribute("target", String.valueOf(target));
//					}
//				}
				for(Cell cell: cellList) {
					if(cell.getValue().equals(from)) {
						mxCellEle.addAttribute("source", String.valueOf(cell.getId()));
						if(cell.getType() == "domain") {
							source = cell.getId();
						}
					}else if(cell.getValue().equals(to)) {
						mxCellEle.addAttribute("target", String.valueOf(cell.getId()));
						if(cell.getType() == "domain") {
							target = cell.getId();
						}
					}
				}
				
				Element mxGeometryEle = mxCellEle.addElement("mxGeometry");
				mxGeometryEle.addAttribute("as", "geometry");
				mxGeometryEle.addAttribute("relative", String.valueOf(1));
				if(source != 0) {
					Element mxPointEle = mxGeometryEle.addElement("mxPoint");
					mxPointEle.addAttribute("as", "targetPoint");
					mxPointEle.addAttribute("y", String.valueOf(constraint.getY1()));
					mxPointEle.addAttribute("x", String.valueOf(constraint.getX1()));
				}
				if(target != 0) {
					Element mxPointEle = mxGeometryEle.addElement("mxPoint");
					mxPointEle.addAttribute("as", "sourcePoint");
					mxPointEle.addAttribute("y", String.valueOf(constraint.getY2()));
					mxPointEle.addAttribute("x", String.valueOf(constraint.getX2()));
				}
			}
		}

		File xmlFile = new File( AddressService.downloadRootAddress+"GXNU.xml");
		if(xmlFile.exists()==true) {
			xmlFile.delete();
		}
		try {
			xmlFile.createNewFile();
			//创建一个输出流对象
			Writer out = new FileWriter(xmlFile);
			//创建一个dom4j创建xml的对象
			XMLWriter writer = new XMLWriter(out, format);
			//调用write方法将doc文档写到指定路径
			writer.write(document);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 	
}

