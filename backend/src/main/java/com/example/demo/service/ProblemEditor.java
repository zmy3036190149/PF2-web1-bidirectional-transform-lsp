package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.example.demo.bean.Constraint;
import com.example.demo.bean.ContextDiagram;
import com.example.demo.bean.Interface;
import com.example.demo.bean.Machine;
import com.example.demo.bean.PfLink;
import com.example.demo.bean.PfPhenomenon;
import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.ProblemDomain;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.bean.RequirementPhenomenon;
import com.example.demo.bean.SubProblemDiagram;

import pf.PfStandaloneSetup;
import com.example.demo.service.AddressService;
@Service
public class ProblemEditor{
	 private static String rootAddress = AddressService.rootAddress;
	
	 /*xtext ->xmi(已有接口)*/
	 public static File performSave(String filename) {
		File file = new File("pf/"+filename);
		// http://www.eclipse.org/forums/index.php?t=msg&goto=520616&
		PfStandaloneSetup.doSetup();
		XtextResourceSet resourceSet = new XtextResourceSet();
		
		// http://www.eclipse.org/forums/index.php?t=msg&goto=480679&
		
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL,	Boolean.TRUE);
		URI uri = URI.createURI(file.getPath()); // your input textual file
		
		
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(".pf", new XMIResourceFactoryImpl());
		
		Resource xtextResource = resourceSet.getResource(uri, true);
		
		File xmlFile = new File("pf/" + filename.replace(".pf", ".xml"));
//		System.out.println(xmlFile.getPath());
		URI xmiURI = URI.createURI(xmlFile.getPath());
		
		Resource xmiResource = new XMIResourceFactoryImpl().createResource(xmiURI);	//创建特定的资源

		xmiResource.getContents().add(xtextResource.getContents().get(0));
		
		try {
			xmiResource.save(null);
			System.out.println("saved "+xmiURI);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("failed to write " + uri);
		}
		
		return xmlFile;
	}
	
	 /* xmi -> project*/
	 public static Project transformXML(File xmlFile) {
		 Project project = new Project();
		 SAXReader saxReader = new SAXReader();
		 try {
			 Document document = saxReader.read(xmlFile);
			 Element pfElement = document.getRootElement();
			 List<?> nodeElementList = pfElement.elements("nodes");
			 List<?> linkElementList = pfElement.elements("links"); 
			 
			 Machine machine = new Machine();
			 List<ProblemDomain> problemDomainList = new ArrayList<ProblemDomain>();
			 List<Interface> interfaceList = new ArrayList<Interface>();
			 List<Requirement> requirementList = new ArrayList<Requirement>();
			 List<Reference> referenceList = new ArrayList<Reference>();
			 List<Constraint> constraintList = new ArrayList<Constraint>();
			 List<String> node = new ArrayList<String>();
			 
			 int reqnum = 0;
			 int domainnum = 0;
			 for(Object object : nodeElementList) {
				 Element nodeElement = (Element) object;
				 String name = nodeElement.attributeValue("name");
				 String description = nodeElement.attributeValue("description");
				 String type = nodeElement.attributeValue("type");
				 int height = 50;
				 int width = 100;

				 if(type == null) {
					 reqnum = reqnum + 1;
					 Requirement requirement = new Requirement();
					 requirement.setNo(reqnum);
					 requirement.setShortname(name);
					 if(description == null) {
						 requirement.setName(name);
						 node.add(name);
					 }else {
						 requirement.setName(description);
						 node.add(description);
					 }
					 requirement.setH(height);
					 requirement.setW(width);
					 requirement.setX( 900 );
					 requirement.setY( 50 + reqnum * 100 );
					 requirementList.add(requirement);
				 }else if(type.equals("R")) {
					 reqnum = reqnum + 1;
					 Requirement requirement = new Requirement();
					 requirement.setNo(reqnum);
					 requirement.setShortname(name);
					 if(description == null) {
						 requirement.setName(name);
						 node.add(name);
					 }else {
						 requirement.setName(description);
						 node.add(description);
					 }
					 requirement.setH(height);
					 requirement.setW(width);
					 requirement.setX( 900 );
					 requirement.setY( 50 + reqnum * 100 );
					 requirementList.add(requirement);
//					 node.add(description);
				 }else if(type.equals("M")) {
					 node.add(name);
					 if(description == null) {
						 machine.setName(name);
					 }else {
						 machine.setName(description);
					 }
					 machine.setShortname(name);
					 machine.setH(height);
					 machine.setW(width);
					 machine.setX( 200 );
					 machine.setY( 150 );
				 }else{
					 node.add(name);
					 domainnum = domainnum + 1;
					 ProblemDomain problemDomain = new ProblemDomain();
					 if(description == null) {
						 problemDomain.setName(name);
					 }else {
						 problemDomain.setName(description);
					 }
					 problemDomain.setShortname(name);
					 problemDomain.setNo(domainnum);
					 problemDomain.setH(height);
					 problemDomain.setW(width);
					 problemDomain.setX(500);
					 problemDomain.setY(domainnum * 100);
					 if(type.equals("B")) {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setType("Biddable");
					 }else if(type.equals("C")) {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setType("Causal");
					 }else if(type.equals("X")) {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setType("Lexical");
					 }else if(type.equals("D")){
						 problemDomain.setProperty("DesignDomain");
					 }else {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setProperty("");
					 }
					 problemDomainList.add(problemDomain);
				 }
			 }
			 
			 int interfaceNum = 0;
			 int referenceNum = 0;
			 int constraintNum = 0;
			 int pheNum = 0;
			 List<Phenomenon> pheList = new ArrayList<Phenomenon>();	//全部现象
			 for(Object object : linkElementList) {
				 Element nodeElement = (Element) object;
				 String type = nodeElement.attributeValue("type");
				 String name = nodeElement.attributeValue("description");
				 int fromNo = Integer.parseInt(nodeElement.attributeValue("from").split("\\.")[1]);
				 
				 int toNo = Integer.parseInt(nodeElement.attributeValue("to").split("\\.")[1]);
				 String from = node.get(fromNo);
				 String to = node.get(toNo);
				 
				 List<?> pheElementList = nodeElement.elements("phenomena");
				 List<Phenomenon> phenomenonList = new ArrayList<Phenomenon>();
				 
				 for(Object obj : pheElementList) {
					 Element pheElement = (Element) obj;
					 String pheName = pheElement.attributeValue("name");
					 String pheType = pheElement.attributeValue("type");
					 
					 boolean exit = false;
					 for(Phenomenon phe : pheList) {
						 if(phe.getPhenomenon_name()!=null && 
								 phe.getPhenomenon_name().equals(pheName) &&
								 phe.getPhenomenon_type().equals(pheType)) {
							 exit = true;
							 break;
						 }
					 } 
					 if(!exit) {
						 pheNum = pheNum + 1;
					 }
					 Phenomenon phenomenon = new Phenomenon();
					 phenomenon.setPhenomenon_name(pheName);
					 phenomenon.setPhenomenon_type(pheType);
					 phenomenon.setPhenomenon_no(pheNum);
					 phenomenon.setPhenomenon_from(from);
					 phenomenon.setPhenomenon_to(to);
					 phenomenonList.add(phenomenon);
					 pheList.add(phenomenon);
				 }
				 String description = name + ":";
				 String pheStr = "";
				 for(Phenomenon phenomenon: phenomenonList) {
					 if(pheStr.equals("")) {
						 pheStr = pheStr + phenomenon.getPhenomenon_name();
					 }else {
						 pheStr = pheStr + ", " + phenomenon.getPhenomenon_name();
					 }
				}
				if(!pheStr.equals("")) {
					pheStr = from + "!" + "{" +  pheStr + "}";
					}
				description = description + pheStr;
				if(type == null) {
					interfaceNum = interfaceNum + 1;
					 Interface inte = new Interface();
					 inte.setNo(interfaceNum);
					 inte.setName(name);
					 inte.setFrom(from);
					 inte.setTo(to);
					 inte.setPhenomenonList(phenomenonList);
					 inte.setDescription(description);
					 interfaceList.add(inte);
				}else if(type.equals("~~")) {
					referenceNum = referenceNum + 1;
					 Reference reference = new Reference();
					 reference.setNo(referenceNum);
					 reference.setName(name);
					 reference.setFrom(from);
					 reference.setTo(to);
					 List<RequirementPhenomenon> reqPheList = new ArrayList<RequirementPhenomenon>();
					 for(Phenomenon phe: phenomenonList) {
						 RequirementPhenomenon reqPhe = new RequirementPhenomenon();
						 reqPhe.setPhenomenon_no(phe.getPhenomenon_no());
						 reqPhe.setPhenomenon_name(phe.getPhenomenon_name());
						 reqPhe.setPhenomenon_type(phe.getPhenomenon_type());
						 reqPhe.setPhenomenon_from(phe.getPhenomenon_from());
						 reqPhe.setPhenomenon_to(phe.getPhenomenon_to());
						 reqPhe.setPhenomenon_constraint("false");
						 for(Requirement req: requirementList) {
							 if(req.getName().equals(from) || 
									 req.getName().equals(to)) {
								 reqPhe.setPhenomenon_requirement(req.getNo());
							 }
						 }
						 reqPheList.add(reqPhe);
					 }
					 reference.setPhenomenonList(reqPheList);
					 reference.setDescription(description);
					 referenceList.add(reference);
				 }else if(type.equals("<~") || type.equals("~>")) {
					 constraintNum = constraintNum + 1;
					 Constraint constraint = new Constraint();
					 constraint.setNo(constraintNum);
					 constraint.setName(name);
					 constraint.setFrom(from);
					 constraint.setTo(to);
					 List<RequirementPhenomenon> reqPheList = new ArrayList<RequirementPhenomenon>();
					 for(Phenomenon phe: phenomenonList) {
						 RequirementPhenomenon reqPhe = new RequirementPhenomenon();
						 reqPhe.setPhenomenon_no(phe.getPhenomenon_no());
						 reqPhe.setPhenomenon_name(phe.getPhenomenon_name());
						 reqPhe.setPhenomenon_type(phe.getPhenomenon_type());
						 reqPhe.setPhenomenon_from(phe.getPhenomenon_from());
						 reqPhe.setPhenomenon_to(phe.getPhenomenon_to());
						 reqPhe.setPhenomenon_constraint("true");
						 for(Requirement req: requirementList) {
//							 System.out.println(from);
//							 System.out.println(to);
							 if(req.getName().equals(from) || 
									 req.getName().equals(to)) {
								 reqPhe.setPhenomenon_requirement(req.getNo());
							 }
						 }
						 reqPheList.add(reqPhe);
					 }
					 constraint.setPhenomenonList(reqPheList);
					 constraint.setDescription(description);
					 constraintList.add(constraint);
				 }
				 else {
					 interfaceNum = interfaceNum + 1;
					 Interface inte = new Interface();
					 inte.setNo(interfaceNum);
					 inte.setName(name);
					 inte.setFrom(from);
					 inte.setTo(to);
					 inte.setPhenomenonList(phenomenonList);
					 inte.setDescription(description);
					 interfaceList.add(inte);
				 }
			 }
			 deleteOverlapInt(interfaceList);
			 deleteOverlapRef(referenceList);
			 deleteOverlapCon(constraintList);
			 changeRefPhe(interfaceList,referenceList);
			 changeConPhe(interfaceList,constraintList);
			 
			 String title = pfElement.attributeValue("name");
			 ContextDiagram contextDiagram = new ContextDiagram();
			 String contextDiagramName = "ContextDiagram";
			 contextDiagram.setTitle(contextDiagramName);
			 contextDiagram.setMachine(machine);
			 contextDiagram.setProblemDomainList(problemDomainList);
			 contextDiagram.setInterfaceList(interfaceList);
			 
			 ProblemDiagram problemDiagram = new ProblemDiagram();
			 String problemDiagramName = "ProblemDiagram";
			 problemDiagram.setTitle(problemDiagramName);
			 problemDiagram.setRequirementList(requirementList);
			 problemDiagram.setReferenceList(referenceList);
			 problemDiagram.setConstraintList(constraintList);
			 problemDiagram.setContextDiagram(contextDiagram);
			 
			 project.setTitle(title);
			 project.setContextDiagram(contextDiagram);
			 project.setProblemDiagram(problemDiagram);	

		 	} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return project; 
	 }

	 public static void changeRefPhe(List<Interface> interfaces,List<Reference>references){		 
		 for(Reference ref:references) {
			 for(RequirementPhenomenon reqPhe:ref.getPhenomenonList()) {
				 changeRefPhe(interfaces,reqPhe);
			 }			
		 }
	 }
	 public static void changeConPhe(List<Interface> interfaces,List<Constraint>cons){		 
		 for(Constraint ref:cons) {
			 for(RequirementPhenomenon reqPhe:ref.getPhenomenonList()) {
				 changeRefPhe(interfaces,reqPhe);
			 }			
		 }
	 }
	 public static void changeRefPhe(List<Interface> interfaces,RequirementPhenomenon refPhe){
		 for(Interface inter:interfaces) {
			 for(Phenomenon intPhe:inter.getPhenomenonList()) {
				 if(intPhe.getPhenomenon_name().contentEquals(refPhe.getPhenomenon_name())) {
					 refPhe.setPhenomenon_from(intPhe.getPhenomenon_from());
					 refPhe.setPhenomenon_to(intPhe.getPhenomenon_to());
				 }
			 }
		 }
	 }
			
	 public static Project XML2Project(File xmlFile,Project project) {		 
		 SAXReader saxReader = new SAXReader();
		 try {
			 Document document = saxReader.read(xmlFile);
			 Element pfElement = document.getRootElement();
			 List<?> nodeElementList = pfElement.elements("nodes");
			 List<?> linkElementList = pfElement.elements("links"); 
			 
//			 Machine machine = new Machine();
//			 List<ProblemDomain> problemDomainList = new ArrayList<ProblemDomain>();
//			 List<Requirement> requirementList = new ArrayList<Requirement>();

			 List<Interface> inteList = project.getContextDiagram().getInterfaceList();
			 List<Reference> refList = project.getProblemDiagram().getReferenceList();
			 List<Constraint> conList = project.getProblemDiagram().getConstraintList();

			 List<String> node = new ArrayList<String>();			 
			 int reqnum = project.getProblemDiagram().getRequirementList().size();
			 int domainnum = project.getContextDiagram().getProblemDomainList().size();
			 for(Object object : nodeElementList) {
				 Element nodeElement = (Element) object;
				 String name = nodeElement.attributeValue("name");
				 String description = nodeElement.attributeValue("description");
				 String type = nodeElement.attributeValue("type");
				 int height = 50;
				 int width = 100;

				 if(type == null) {
					 reqnum = reqnum + 1;
					 Requirement requirement = new Requirement();
					 requirement.setNo(reqnum);
					 if(description == null) {
						 requirement.setName(name);
						 node.add(name);
					 }else {
						 requirement.setName(description);
						 node.add(description);
					 }
					 requirement.setH(height);
					 requirement.setW(width);
					 requirement.setX( 900 );
					 requirement.setY( 50 + reqnum * 100 );
					 List<Requirement> reqList = project.getProblemDiagram().getRequirementList();
					 boolean flag = false;
					 for(int i =0;i<reqnum-1;i++) {						 
						 if(description.contentEquals(reqList.get(i).getName())
								 || name.contentEquals(reqList.get(i).getShortname())) {
							 reqList.get(i).setName(description);
							 reqList.get(i).setShortname(name);
							 flag = true;
							 break;
						 }
					 }
					 if(!flag) {
						 reqList.add(requirement);
					 }
				 }else if(type.equals("R")) {
					 reqnum = reqnum + 1;
					 Requirement requirement = new Requirement();
					 requirement.setNo(reqnum);
					 if(description == null) {
						 requirement.setName(name);
						 node.add(name);
					 }else {
						 requirement.setName(description);
						 node.add(description);
					 }
					 requirement.setH(height);
					 requirement.setW(width);
					 requirement.setX( 900 );
					 requirement.setY( 50 + reqnum * 100 );
					 
					 List<Requirement> reqList = project.getProblemDiagram().getRequirementList();
					 boolean flag = false;
					 for(int i =0;i<reqnum-1;i++) {						 
						 if(description.contentEquals(reqList.get(i).getName())
								 || name.contentEquals(reqList.get(i).getShortname())) {
							 reqList.get(i).setName(description);
							 reqList.get(i).setShortname(name);
							 flag = true;
							 break;
						 }
					 }
					 if(!flag) {
						 reqList.add(requirement);
					 }
					 
//					 node.add(description);
				 }else if(type.equals("M")) {
					 node.add(name);			 
					 
					 if(project.getContextDiagram().getMachine()!=null) {
						 Machine machine = project.getContextDiagram().getMachine();
						 if(description == null) {
							 machine.setName(name);
						 }else {
							 machine.setName(description);
						 }
						 machine.setShortname(name);
					 }else {
						 Machine machine =new Machine();
						 if(description == null) {
							 machine.setName(name);
						 }else {
							 machine.setName(description);
						 }
						 machine.setShortname(name);
						 machine.setH(height);
						 machine.setW(width);
						 machine.setX( 200 );
						 machine.setY( 150 );
						 project.getContextDiagram().setMachine(machine);
					 }
					 
				 }else{
					 node.add(name);
					 domainnum = domainnum + 1;
					 ProblemDomain problemDomain = new ProblemDomain();
					 if(description == null) {
						 problemDomain.setName(name);
					 }else {
						 problemDomain.setName(description);
					 }
					 problemDomain.setShortname(name);
					 problemDomain.setNo(domainnum);
					 problemDomain.setH(height);
					 problemDomain.setW(width);
					 problemDomain.setX(500);
					 problemDomain.setY(domainnum * 100);
					 if(type.equals("B")) {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setType("Biddable");
					 }else if(type.equals("C")) {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setType("Causal");
					 }else if(type.equals("X")) {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setType("Lexical");
					 }else if(type.equals("D")){
						 problemDomain.setProperty("DesignDomain");
					 }else {
						 problemDomain.setProperty("GivenDomain");
						 problemDomain.setProperty("");
					 }
					 
					 List<ProblemDomain> pdList = project.getContextDiagram().getProblemDomainList();
					 boolean flag = false;
					 for(int i =0;i<reqnum-1;i++) {						 
						 if(description.contentEquals(pdList.get(i).getName())
								 || name.contentEquals(pdList.get(i).getShortname())) {
							 pdList.get(i).setName(description);
							 pdList.get(i).setShortname(name);
							 pdList.get(i).setType(type);
							 flag = true;
							 break;
						 }
					 }
					 if(!flag) {
						 pdList.add(problemDomain);
					 }
				 }
			 }
			 int interfaceNum = 0;
			 int referenceNum = 0;
			 int constraintNum = 0;
			 int pheNum = 0;
			 List<Phenomenon> pheList = new ArrayList<Phenomenon>();	//全部现象
			 for(Object object : linkElementList) {
				 Element nodeElement = (Element) object;
				 String type = nodeElement.attributeValue("type");
				 String name = nodeElement.attributeValue("description");
				 int fromNo = Integer.parseInt(nodeElement.attributeValue("from").split("\\.")[1]);
				 int toNo = Integer.parseInt(nodeElement.attributeValue("to").split("\\.")[1]);
				 String from = node.get(fromNo);
				 String to = node.get(toNo);
				 
				 List<?> pheElementList = nodeElement.elements("phenomena");
				 List<Phenomenon> phenomenonList = new ArrayList<Phenomenon>();
				 
				 for(Object obj : pheElementList) {
					 Element pheElement = (Element) obj;
					 String pheName = pheElement.attributeValue("name");
					 String pheType = pheElement.attributeValue("type");
					 
					 boolean exit = false;
					 for(Phenomenon phe : pheList) {
						 if(phe.getPhenomenon_name().equals(pheName) &&
								 phe.getPhenomenon_type().equals(pheType)) {
							 exit = true;
							 break;
						 }
					 } 
					 if(!exit) {
						 pheNum = pheNum + 1;
					 }
					 Phenomenon phenomenon = new Phenomenon();
					 phenomenon.setPhenomenon_name(pheName);
					 phenomenon.setPhenomenon_type(pheType);
					 phenomenon.setPhenomenon_no(pheNum);
					 phenomenon.setPhenomenon_from(from);
					 phenomenon.setPhenomenon_to(to);
					 phenomenonList.add(phenomenon);
					 pheList.add(phenomenon);
				 }
				 String description = name + ":";
				 String pheStr = "";
				 for(Phenomenon phenomenon: phenomenonList) {
					 if(pheStr.equals("")) {
						 pheStr = pheStr + phenomenon.getPhenomenon_name();
					 }else {
						 pheStr = pheStr + ", " + phenomenon.getPhenomenon_name();
					 }
				}
				if(!pheStr.equals("")) {
					pheStr = from + "!" + "{" +  pheStr + "}";
					}
				description = description + pheStr;
				if(type == null) {
					 interfaceNum = interfaceNum + 1;
					 Interface inte = new Interface();
					 inte.setNo(interfaceNum);
					 inte.setName(name);
					 inte.setFrom(from);
					 inte.setTo(to);
					 inte.setPhenomenonList(phenomenonList);
					 inte.setDescription(description);
					 
					 boolean flag = false;
					 for(int i =0;i<interfaceNum-1;i++) {						 
						 if(from.contentEquals(inteList.get(i).getTo())
								 && to.contentEquals(inteList.get(i).getFrom())
							|| from.contentEquals(inteList.get(i).getTo())
								&& to.contentEquals(inteList.get(i).getFrom())) {
							 inteList.get(i).setPhenomenonList(phenomenonList);
							 inteList.get(i).setDescription(description);
							 flag = true;
							 break;
						 }
					 }
					 if(!flag) {
						 inteList.add(inte);
					 }
				}else if(type.equals("~~")) {
					 referenceNum = referenceNum + 1;
					 Reference reference = new Reference();
					 reference.setNo(referenceNum);
					 reference.setName(name);
					 reference.setFrom(from);
					 reference.setTo(to);
					 List<Requirement> reqList = project.getProblemDiagram().getRequirementList();
					 List<RequirementPhenomenon> reqPheList = new ArrayList<RequirementPhenomenon>();
					 for(Phenomenon phe: phenomenonList) {
						 RequirementPhenomenon reqPhe = new RequirementPhenomenon();
						 reqPhe.setPhenomenon_no(phe.getPhenomenon_no());
						 reqPhe.setPhenomenon_name(phe.getPhenomenon_name());
						 reqPhe.setPhenomenon_type(phe.getPhenomenon_type());
						 reqPhe.setPhenomenon_from(phe.getPhenomenon_from());
						 reqPhe.setPhenomenon_to(phe.getPhenomenon_to());
						 reqPhe.setPhenomenon_constraint("false");
						 //???
						 for(Requirement req: reqList) {
							 if(req.getName().equals(from) || 
									 req.getName().equals(to)) {
								 reqPhe.setPhenomenon_requirement(req.getNo());
							 }
						 }	
						 reqPheList.add(reqPhe);
					 }
					 reference.setPhenomenonList(reqPheList);
					 reference.setDescription(description);
					 
					 
					 
					 boolean flag = false;
					 for(int i =0;i<referenceNum-1;i++) {						 
						 if(from.contentEquals(refList.get(i).getTo())
								 && to.contentEquals(refList.get(i).getFrom())
							|| from.contentEquals(refList.get(i).getTo())
								&& to.contentEquals(refList.get(i).getFrom())) {
							 refList.get(i).setPhenomenonList(reqPheList);
							 refList.get(i).setDescription(description);
							 flag = true;
							 break;
						 }
					 }
					 if(!flag) {
						 refList.add(reference);
					 }
					 
				 }else if(type.equals("<~") || type.equals("~>")) {
					 constraintNum = constraintNum + 1;
					 Constraint constraint = new Constraint();
					 constraint.setNo(constraintNum);
					 constraint.setName(name);
					 constraint.setFrom(from);
					 constraint.setTo(to);
					 List<RequirementPhenomenon> reqPheList = new ArrayList<RequirementPhenomenon>();
					 List<Requirement> reqList = project.getProblemDiagram().getRequirementList();
					 for(Phenomenon phe: phenomenonList) {
						 RequirementPhenomenon reqPhe = new RequirementPhenomenon();
						 reqPhe.setPhenomenon_no(phe.getPhenomenon_no());
						 reqPhe.setPhenomenon_name(phe.getPhenomenon_name());
						 reqPhe.setPhenomenon_type(phe.getPhenomenon_type());
						 reqPhe.setPhenomenon_from(phe.getPhenomenon_from());
						 reqPhe.setPhenomenon_to(phe.getPhenomenon_to());
						 reqPhe.setPhenomenon_constraint("true");
						 for(Requirement req: reqList) {
//							 System.out.println(from);
//							 System.out.println(to);
							 if(req.getName().equals(from) || 
									 req.getName().equals(to)) {
								 reqPhe.setPhenomenon_requirement(req.getNo());
							 }
						 }
						 reqPheList.add(reqPhe);
					 }
					 constraint.setPhenomenonList(reqPheList);
					 constraint.setDescription(description);

					 
					 boolean flag = false;
					 for(int i =0;i<constraintNum-1;i++) {						 
						 if(from.contentEquals(conList.get(i).getTo())
								 && to.contentEquals(conList.get(i).getFrom())
							|| from.contentEquals(conList.get(i).getTo())
								&& to.contentEquals(conList.get(i).getFrom())) {
							 conList.get(i).setPhenomenonList(reqPheList);
							 conList.get(i).setDescription(description);
							 flag = true;
							 break;
						 }
					 }
					 if(!flag) {
						 conList.add(constraint);
					 }
				 }
//				 else {
//					 interfaceNum = interfaceNum + 1;
//					 Interface inte = new Interface();
//					 inte.setInterface_no(interfaceNum);
//					 inte.setInterface_name(name);
//					 inte.setInterface_from(from);
//					 inte.setInterface_to(to);
//					 inte.setPhenomenonList(phenomenonList);
//					 inte.setInterface_description(description);
//					 interfaceList.add(inte);
//				 }
			 }
			 deleteOverlapInt(inteList);
			 deleteOverlapRef(refList);
			 deleteOverlapCon(conList);
			 
			 String title = pfElement.attributeValue("name");
			 project.setTitle(title);

		 	} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return project;
	 }
	 /* project -> pf */
	 public static boolean project2pf(String projectAddress, Project project,String branch) {	
		try{
				GitUtil.gitCheckout(branch, rootAddress);  //切换分支
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
		}
		String filename = rootAddress + projectAddress + ".pf";
		File pfFile=  new File(filename);
		if(pfFile.exists()==false) {
			ProblemEditor.newpf( projectAddress,  project, branch);
		}else {
			try {
				String res = ProblemEditor.traversePf( filename, project);
				ProblemEditor.traverseProject(res,filename,project);
			}catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}				
		return true;
	 }	
	 /*新建pf*/
	 public static boolean newpf(String projectAddress, Project project,String branch) {	
		try{
				GitUtil.gitCheckout(branch, rootAddress);  //切换分支
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("problem: "+project.getTitle()+"\n\n");
		
		//Machine
		Machine machine = project.getContextDiagram().getMachine();
		buffer.append(machine.getShortname());
		buffer.append(" M");
		if(machine.getName()!="")
			buffer.append(" \""+machine.getName()+"\"");
		buffer.append("\n\n");
		
		//ProblemDomain
		List<ProblemDomain> pd = project.getContextDiagram().getProblemDomainList();
		int pdlen = project.getContextDiagram().getProblemDomainList().size();			
		for(int i =0 ;i<pdlen;i++) {
			buffer.append(pd.get(i).getShortname());
			if(pd.get(i).getProperty().contentEquals("DesignDomain")) {
				buffer.append(" D ");
			}else if(pd.get(i).getType()==null) {
				buffer.append(" ? ");
			}else if(pd.get(i).getType().contentEquals("Causal")) {
				buffer.append(" C ");
			}else if(pd.get(i).getType().contentEquals("Biddable")) {
				buffer.append(" B ");
			}else if(pd.get(i).getType().contentEquals("Lexical")) {
				buffer.append(" X ");
			}else {
				buffer.append(" "+ pd.get(i).getType());
			}
			if(pd.get(i).getName()!="")
				buffer.append("\""+pd.get(i).getName()+"\"");
			buffer.append("\n\n");
		}
		
		//requirement
		List<Requirement> req = project.getProblemDiagram().getRequirementList();
		int reqlen = project.getProblemDiagram().getRequirementList().size();			
		for(int i =0 ;i<reqlen;i++) {
			buffer.append(req.get(i).getShortname());			
			buffer.append(" R ");
			if(pd.get(i).getName()!="")
				buffer.append("\""+req.get(i).getName()+"\"");
			buffer.append("\n\n");
		}
		
		//interface
		List<Interface> intf = project.getContextDiagram().getInterfaceList();
		int intlen = intf.size();
		for(int i = 0;i<intlen;i++) {
			String int_from = intf.get(i).getFrom();
			String int_to = intf.get(i).getTo();
			
			List<Phenomenon> pheList = intf.get(i).getPhenomenonList();
			String pheList1 = " {";
			String pheList2 = " {";
							
			for(Phenomenon phe: pheList) {
				if(phe.getPhenomenon_from().contentEquals(int_from))
					pheList1 += " " + phe.getPhenomenon_type() + " " + phe.getPhenomenon_name() + ",";
				else 
					pheList2 += " " + phe.getPhenomenon_type() + " " + phe.getPhenomenon_name() + ",";
			}
			
			if(pheList1.length()>2) {
				pheList1= pheList1.substring(0,pheList1.length()-1);
				pheList1 += " } ";				
				buffer.append(int_from);
				buffer.append(" -- ");
				buffer.append(int_to);
				buffer.append(pheList1);
				buffer.append("\""+intf.get(i).getName()+"\"");
				buffer.append("\n\n");
			}	
			if(pheList2.length()>2) {		
				pheList2= pheList2.substring(0,pheList2.length()-1);
				pheList2 += " } ";				
				buffer.append(int_to);
				buffer.append(" -- ");
				buffer.append(int_from);
				buffer.append(pheList2);
				buffer.append("\""+intf.get(i).getName()+"\"");
				buffer.append("\n\n");
			}	
			if(pheList.size()==0) {
				buffer.append(int_from);
				buffer.append(" -- ");
				buffer.append(int_to);
				buffer.append(" \""+intf.get(i).getName()+"\"");
				buffer.append("\n\n");
			}
		}
		//reference
		List<Reference> ref = project.getProblemDiagram().getReferenceList();
		int reflen = ref.size();
		for(int i = 0;i<reflen;i++) {
			String ref_from = ProblemEditor.getShortname(project, ref.get(i).getFrom());			
			String ref_to = ProblemEditor.getShortname(project, ref.get(i).getTo());
			buffer.append(ref_from);
			buffer.append(" ~~ ");
			buffer.append(ref_to);
			List<RequirementPhenomenon> pheList = ref.get(i).getPhenomenonList();
			if(pheList.size()>0) {
				buffer.append(" { ");
				for(RequirementPhenomenon phe: pheList) {
					buffer.append(phe.getPhenomenon_type()+ " " + phe.getPhenomenon_name() + ",");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append(" }");
			}	
			buffer.append(" \"" + ref.get(i).getName()+"\"");			
			buffer.append("\n\n");
		}
		//constraint
		List<Constraint> con = project.getProblemDiagram().getConstraintList();
		int conlen = con.size();
		for(int i = 0;i<conlen;i++) {
			String con_from = ProblemEditor.getShortname(project, con.get(i).getFrom());			
			String con_to = ProblemEditor.getShortname(project, con.get(i).getTo());
			buffer.append(con_from);
			buffer.append(" ~> ");
			buffer.append(con_to);
			List<RequirementPhenomenon> pheList = con.get(i).getPhenomenonList();
			if(pheList.size()>0) {
				buffer.append(" { ");
				for(RequirementPhenomenon phe: pheList) {
					buffer.append(phe.getPhenomenon_type()+ " " + phe.getPhenomenon_name() + ",");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append(" }");
			}	
			buffer.append(" \"" + con.get(i).getName()+"\"");
			buffer.append("\n\n");
		}
		String filename = rootAddress + projectAddress + ".pf";
		File pfFile=  new File(filename);		
		try{
//			System.out.println("newPf-----------");
//			 System.out.println(buffer);
//			 System.out.println("---------------------");
			FileWriter fw = new FileWriter(filename);
			fw.write(buffer.toString());
			fw.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	 /*
	  遍历pf文件
	        节点更新方式：        
	            若简称相同，名称不同，则修改名称
	            若名称相同，简称不同，则修改名称
	            若简称、名称都不相同，则删除该节点
	        边更新方式        
	            若存在，则跳过
	            若不存在，则删除
	  */
	 public static String traversePf(String filename,Project project)  throws IOException{
		 File file=new File(filename);		 
		 FileInputStream fileInputStream = new FileInputStream(file);
		 InputStreamReader isr = new InputStreamReader(fileInputStream, "UTF-8");
		 BufferedReader br = new BufferedReader(isr);
	     String line = "";
	     String res = "";
		 
//	     File file2=new File(rootAddress+"temp.pf");
//	     FileOutputStream fileOutputStream = new FileOutputStream(file2);
		
		 int length = 0;
		 while((line=br.readLine())!=null) {
			 String name = "";
			 line += "\n";
			 //s1[1] 描述
			 String[] s1=line.split("\"");
			 if(line.contentEquals("\n")) {
				 //System.out.println("###空行###");
				 //System.out.println("---###"+line+"###---");
				 res += line;				 
				 continue;				 
			 }
			 else if(s1.length>1) {
				 //有描述
				 name = s1[1];
			 } 
			 if(s1[0].indexOf("problem:")==0){
				 //System.out.println("----"+line+"---");
				 res += line;	
				 continue;
			 }else if(s1[0].indexOf("~~")!=-1) {
//				 System.out.println("reference");
				 //s1[0]:	Machine ~~ World { event U1persNumber, event M1persData} 
				 String[] s2 = s1[0].split("~~"); 
				 //s2[0]:	Machine
				 String from = s2[0].trim(); 
				 //s3[0]:	World 
				 //s3[1]: 	event U1persNumber, event M1persData
				 String[] s3 = s2[1].split("\\{");
				 String to = s3[0].trim();
				 String[] s4 = new String[0];
				 if(s3.length>1) {
					 s4 = s3[1].split("\\}")[0].split(",");
				 }	 
				 if(s2.length>1) {					 
					 line = ProblemEditor.changeReference(from,to,"~~",s4,name,project);
				 }else 
					 line = "";					 
			 }else if(s1[0].indexOf("~>")!=-1) {
//				 System.out.println("constraint");
				 String[] s2 = s1[0].split("~>");
				 String from = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String to = s3[0].trim();
				 String[] s4 = new String[0];
				 if(s3.length>1) {
					 s4 = s3[1].split("\\}")[0].split(",");
				 }				 
				 if(s2.length>1) {					 
					 line = ProblemEditor.changeConstraint(from,to,"~>",s4,name,project);
				 }else 
					 line = "";				 
			 }else if(s1[0].indexOf("--")!=-1) {
				 //System.out.println(line);				 
				 String[] s2 = s1[0].split("--");
				 String from = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String to = s3[0].trim();
				 List<PfPhenomenon> pfPheList = new ArrayList<PfPhenomenon>();
				 if(s3.length>1) {
					 String s4 = s3[1].split("\\}")[0];
					 pfPheList = getPfPheList(s4);
					 //System.out.println("s4="+s4);
					 //System.out.println(pfPheList);
				 }
				 PfLink pfLink = new PfLink(from,"--",to,pfPheList,name);
				 line = ProblemEditor.changeInterface(pfLink,project);
				 				 
			 }else if(s1[0].indexOf("->")!=-1) {
				 //System.out.println(line);				 
				 String[] s2 = s1[0].split("->");
				 String from = s2[0].trim();
				 String[] s3 = s2[1].split("\\{");
				 String to = s3[0].trim();
				 List<PfPhenomenon> pfPheList = new ArrayList<PfPhenomenon>();
				 if(s3.length>1) {
					 String s4 = s3[1].split("\\}")[0];
					 pfPheList = getPfPheList(s4);
					 //System.out.println("s4="+s4);
					 //System.out.println(pfPheList);
				 }
				 PfLink pfLink = new PfLink(from,"->",to,pfPheList,name);
				 line = ProblemEditor.changeInterface(pfLink,project);				 				 
			 }else if(s1[0].indexOf(" M ")!=-1){
				 String[] s2 = s1[0].split(" M ");
				 String shortname = s2[0];				 
				 if(s2.length>=1) {					 
					 line = ProblemEditor.changeMachine(shortname,"M",name,project);					 
				 }else {
					 line = "";						 
				 }
					 				 
			 }else if(s1[0].indexOf(" R ")!=-1){
				 String[] s2 = s1[0].split(" R ");
				 String shortname = s2[0].trim();
				 line = ProblemEditor.changeRequirement(shortname,"R",name,project);
			 }else {
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
				 String shortname = s2[0].trim();
				 line = ProblemEditor.changeProblemDomain(shortname,type,name,project);
			 }		
			 //System.out.println("---"+line+"---");
			 //System.out.println(line);
			 res += line;				 
		 }
//		 System.out.println("--------result after traverse Pf-----------");
//		 System.out.println(res);
//		 System.out.println("--------result after traverse Pf end-------------");
//		 fileOutputStream.write(res.getBytes());		 
		 fileInputStream.close();
//		 fileOutputStream.close();
		 return res;
	 }
	 
	 private static List<PfPhenomenon> getPfPheList(String pfPhes) {
		// TODO Auto-generated method stub
		 List<PfPhenomenon> pfPheList =new ArrayList<PfPhenomenon>();
		 String[] str_pfPheList = pfPhes.split(",");
		 for(String str_pfPhe:str_pfPheList) {
			 //System.out.println("str_pfPhe=" + str_pfPhe);
			 String[] temp = str_pfPhe.split(" ");
			 PfPhenomenon pfPhe = new PfPhenomenon(temp[0],temp[1],"");
			 pfPheList.add(pfPhe);
		 }
		return pfPheList;
	}
	
	 /*
	  * 若pf中interface在project中出现*/
	 public static String changeInterface(PfLink pflink, Project project) {
		//get phenomenon		
		List<Interface> intList = project.getContextDiagram().getInterfaceList();
		List<PfPhenomenon> pfPheList = pflink.getPheList();		
		String intf_from = pflink.getFrom();
		String intf_to = pflink.getTo();
		for(Interface intf:intList) {
			if(pflink.getName().contentEquals(intf.getName())) {							
				//遍历pf中phe,修改并删除多余phe				
				Iterator<PfPhenomenon> it = pfPheList.iterator();
				while(it.hasNext()){
					PfPhenomenon phe = it.next();					
					String phetype = phe.getType();
					String phename = phe.getName();
					Boolean flag=false;
					for(Phenomenon intphe:intf.getPhenomenonList()) {
						if(phe.getName().contentEquals(intphe.getPhenomenon_name())
								&& intf_from.contentEquals(intphe.getPhenomenon_from())) {
							phe.setType(intphe.getPhenomenon_type());
							flag = true;
						}
					}
					//delete phe
					if(!flag)
						it.remove();	
				}
				//遍历project中的phe,增加phe
				for(Phenomenon intphe:intf.getPhenomenonList()) {
					Boolean flag = false;
					if(intphe.getPhenomenon_from().contentEquals(intf_from)) {
						for(PfPhenomenon phe:pfPheList) {
							if(phe.getName().contentEquals(intphe.getPhenomenon_name())) {
								flag=true;			
							}
						}
						//add phe	
						if(!flag) {								
							pfPheList.add(new PfPhenomenon(intphe.getPhenomenon_type(),intphe.getPhenomenon_name(),""));
						}
					}					
				}
				break;
			}	
			
		}		
		
		String line = "";
		line += intf_from;
		line += " -- ";
		line += intf_to;	
		if(!pfPheList.isEmpty()) {
			line += " {";
			Iterator<PfPhenomenon> it = pfPheList.iterator();
			while(it.hasNext()){
				line += " ";
				line += it.next().toString();
				line += ",";
			}
			line= line.substring(0,line.length() - 1);
			line += " }";
		}
		line +=" \"" + pflink.getName() + "\"";
		line +=" \n";
		return line;
	 }
	 public static String changeReference(String from,String to,String type,String[] pheList,String name,Project project) {
		String line = "";
		//reference
		List<Reference> ref = project.getProblemDiagram().getReferenceList();
		int reflen = ref.size();		
		List<String> arrayList = Arrays.asList(pheList);
		List<String> arrList = new ArrayList<String>(arrayList);
		for(int i = 0; i<reflen; i++) {
			if(name.contentEquals(ref.get(i).getName())) {
			String ref_from = ProblemEditor.getShortname(project, ref.get(i).getFrom());			
			String ref_to = ProblemEditor.getShortname(project, ref.get(i).getTo());			
			//遍历pf中phe
			Iterator<String> it = arrList.iterator();
			while(it.hasNext()){
				String phe = it.next();
				String[] s1= phe.split("\"");
				String des = "";
				if(s1.length>1) {
					des = s1[1];
				}
				String[] s2 = s1[0].split(" ");
				String phetype = s2[0].trim();
				String phename = s2[1].trim();
				Boolean flag=false;
				for(RequirementPhenomenon refphe:ref.get(i).getPhenomenonList()) {
					if(phename.contentEquals(refphe.getPhenomenon_name())) {
						phetype=refphe.getPhenomenon_type();
						flag = true;
					}
				}
				//delete phe
				if(!flag)
					it.remove();	
			}
			//遍历project中的phe
			for(RequirementPhenomenon refphe:ref.get(i).getPhenomenonList()) {
				Boolean flag = false;
				for(String phe:pheList) {
					String[] s1= phe.split("\"");
					String des = "";
					if(s1.length>1) {
						des = s1[1];
					}
					String[] s2 = s1[0].split(" ");
					String phetype = s2[0].trim();
					String phename = s2[1].trim();
					if(phename.contentEquals(refphe.getPhenomenon_name())) {
						flag=true;						
					}
				}
				//add phe	
				if(!flag) {
					String phe = refphe.getPhenomenon_type();
					phe += " ";
					phe += refphe.getPhenomenon_name();
					arrList.add(phe);
				}
			}
			line += ref_from;
			line += " ~~ ";
			line += ref_to;
			it = arrList.iterator();
			if(!arrayList.isEmpty()) {
				line += " {";
				while(it.hasNext()){
					line += " ";
					line += it.next();
					line += ",";
				}
				line= line.substring(0,line.length() - 1);
				line += "}";
			}
			line += " \"" + ref.get(i).getName()+"\"";
			line += "\n";
			return line;
			}
		}
		return line;
	 }
	 public static String changeConstraint(String from,String to,String type,String[] pheList,String name,Project project) {			
		//constraint
		String line = "";
		List<Constraint> con = project.getProblemDiagram().getConstraintList();
		int conlen = con.size();
		List<String> arrayList = Arrays.asList(pheList);
		List<String> arrList = new ArrayList<String>(arrayList);
		for(int i = 0;i<conlen;i++) {
			if(name.contentEquals(con.get(i).getName())) {
				String con_from = ProblemEditor.getShortname(project, con.get(i).getFrom());			
				String con_to = ProblemEditor.getShortname(project, con.get(i).getTo());
				
				//遍历pf中phe
				Iterator<String> it = arrList.iterator();
				while(it.hasNext()){
					String phe = it.next();
					String[] s1= phe.split("\"");
					String des = "";
					if(s1.length>1) {
						des = s1[1];
					}
					String[] s2 = s1[0].split(" ");
					String phetype = s2[0].trim();
					String phename = s2[1].trim();
					Boolean flag=false;
					for(RequirementPhenomenon conphe:con.get(i).getPhenomenonList()) {
						if(phename.contentEquals(conphe.getPhenomenon_name())) {
							phetype=conphe.getPhenomenon_type();
							flag = true;
						}
					}
					//delete phe
					if(!flag)
						it.remove();	
				}
				//遍历project中的phe
				for(RequirementPhenomenon conphe:con.get(i).getPhenomenonList()) {
					Boolean flag = false;
					for(String phe:pheList) {
						String[] s1= phe.split("\"");
						String des = "";
						if(s1.length>1) {
							des = s1[1];
						}
						String[] s2 = s1[0].split(" ");
						String phetype = s2[0].trim();
						String phename = s2[1].trim();
						if(phename.contentEquals(conphe.getPhenomenon_name())) {
							flag=true;						
						}
					}
					//add phe	
					if(!flag) {
						String phe = conphe.getPhenomenon_type();
						phe += " ";
						phe += conphe.getPhenomenon_name();
						arrList.add(phe);
					}
				}				
				line += con_from;
				line += " ~> ";
				line += con_to;
				it = arrList.iterator();
				if(!arrayList.isEmpty()) {
					line += " {";
					while(it.hasNext()){
						line += " ";
						line += it.next();
						line += ",";
					}
					line= line.substring(0,line.length() - 1);
					line += " }";
				}
				line += " \"" + con.get(i).getName()+"\"";
				line += "\n";
				return line;
			}
		}
		return line;
	 }
	 public static String getShortname(Project project,String name) {
		 
		 List<Requirement> req = project.getProblemDiagram().getRequirementList();
		 int reqlen = req.size();
		 for(int i = 0;i<reqlen;i++) {
			 if(req.get(i).getName().contentEquals(name)) {
				 return req.get(i).getShortname();
			 }		 
		 }
//		 System.out.println("getShortname---------"+name+"-----");
		 return name;
	 }
	 public static String changeMachine(String shortname,String type,String name,Project project) {
		 String line = "";
		 if(shortname.contentEquals(project.getContextDiagram().getMachine().getShortname())
				 || name.contentEquals(project.getContextDiagram().getMachine().getName())) {			 
			 line += project.getContextDiagram().getMachine().getShortname();
			 line += " M ";
			 line += "\"" + project.getContextDiagram().getMachine().getName() + "\"";
			 line += "\n";
			 return line;
		 }
		 return line;
	 }
	 public static String changeRequirement(String shortname,String type,String name,Project project) {
		 String line = "";
		//requirement
		List<Requirement> req = project.getProblemDiagram().getRequirementList();
		int reqlen = project.getProblemDiagram().getRequirementList().size();			
		for(int i =0 ;i<reqlen;i++) {
			if( name.contentEquals(req.get(i).getName())){					
				line += req.get(i).getShortname();			
				line += " R";
				if(req.get(i).getName()!="")
					line += " \""+req.get(i).getName()+"\"";
				line += "\n";
			}			
		}
		
		 return line;
	 }
	 public static String changeProblemDomain(String shortname,String type,String name,Project project) {
		 String line = "";
		//ProblemDomain
		List<ProblemDomain> pd = project.getContextDiagram().getProblemDomainList();
		int pdlen = project.getContextDiagram().getProblemDomainList().size();			
		for(int i =0 ;i<pdlen;i++) {
			if(name.contentEquals(pd.get(i).getName())
					|| shortname.contentEquals(pd.get(i).getShortname())) {
				line += pd.get(i).getShortname();
				if(pd.get(i).getProperty().contentEquals("DesignDomain")) {
					line += " D";
				}else if(pd.get(i).getType()==null) {
					line += " ?";
				}else if(pd.get(i).getType().contentEquals("Causal")) {
					line += " C";
				}else if(pd.get(i).getType().contentEquals("Biddable")) {
					line += " B";
				}else if(pd.get(i).getType().contentEquals("Lexical")) {
					line += " X";
				}
				if(pd.get(i).getName()!="")
					line += " \""+pd.get(i).getName()+"\"\n";
				return line;
			}							
		}	
		 return line;
	 }
	 
	 /*
	  遍历project
	        节点更新方式
	            若存在，则跳过
	            若不存在，则添加到最后
	        边更新方式        
	            若存在，则跳过
	            若不存在，则添加到最后    
	    
	  */
	 public static void traverseProject(String res,String filename,Project project)  throws IOException{
		 File file2=new File(filename);
		 FileOutputStream fileOutputStream=new FileOutputStream(file2);	
		 String line = "";
		 //Machine
		 Machine machine = project.getContextDiagram().getMachine();
		 line = ProblemEditor.getMachinePf(machine);
		 res = ProblemEditor.addLine(line, res);
		 //ProblemDomain
		List<ProblemDomain> pd = project.getContextDiagram().getProblemDomainList();
		int pdlen = project.getContextDiagram().getProblemDomainList().size();			
		for(int i =0 ;i<pdlen;i++) {
			line = ProblemEditor.getProblemDomainPf(pd.get(i));
			res = ProblemEditor.addLine(line, res);
		}
		//requirement
		List<Requirement> req = project.getProblemDiagram().getRequirementList();
		int reqlen = project.getProblemDiagram().getRequirementList().size();			
		for(int i =0 ;i<reqlen;i++) {
			line = ProblemEditor.getRequirementPf(req.get(i));
			res = ProblemEditor.addLine(line, res);
		}
		//interface
		List<Interface> intf = project.getContextDiagram().getInterfaceList();
		int intlen = intf.size();
		for(int i = 0;i<intlen;i++) {
			res=ProblemEditor.searchInterface(res,intf.get(i),true);
			//System.out.println("search another ");
			res=ProblemEditor.searchInterface(res,intf.get(i),false);
			//ProblemEditor.addLine(line, res);
		}
		//Reference
		List<Reference> ref = project.getProblemDiagram().getReferenceList();
		int reflen = ref.size();
		for(int i = 0;i<reflen;i++) {
			line = ProblemEditor.getReferencePf(ref.get(i),project);
			res = ProblemEditor.addLine(line, res);
		}
		//Constraint
		List<Constraint> con = project.getProblemDiagram().getConstraintList();
		int conlen = con.size();
		for(int i = 0;i<conlen;i++) {
			line = ProblemEditor.getConstraintPf(con.get(i),project);
//			System.out.println("&&&&&&&&&&&&&");
			res = ProblemEditor.addLine(line, res);
		}
//		 System.out.println("---------result of traverse Project-----------");
//		 System.out.println(res);
//		 System.out.println("----------------------------------------------");
		 fileOutputStream.write(res.getBytes());		
		 fileOutputStream.close();		 
	 }
	 public static PfLink getLink(String line) {
		 String name = "";
		 String from = "";
		 String to = "";
		 String type = "";
		 String[] phes;
		 String[] s2;
		 //s1[1] 描述
		 String[] s1=line.split("\"");
		 if(s1.length>1) {
			 name = s1[1];
		 }
		 if(s1[0].indexOf("~~")!=-1) {				 				 
			 type = "~~";				 				 
		 }else if(s1[0].indexOf("~>")!=-1) {				 			 
			 type = "~>";			 
		 }else if(s1[0].indexOf("<~")!=-1) {				 			 
			 type = "<~";			 
		 }else if(s1[0].indexOf("--")!=-1) {
			 type = "--";				 				 
		 }else if(s1[0].indexOf("->")!=-1) {
			 type = "->";				 				 				 			 				 
		 }else {
			 return null;
		 }	
		 s2 = s1[0].split(type);
		 from = s2[0].trim();
		 String[] s3 = s2[1].split("\\{");
		 to = s3[0].trim();
		 List<PfPhenomenon> pfPheList = new ArrayList<PfPhenomenon>();
		 if(s3.length>1) {
			 String s4 = s3[1].split("\\}")[0];
			 pfPheList = getPfPheList(s4);
		 }
		 PfLink pfLink = new PfLink(from,"->",to,pfPheList,name);
		 return pfLink;		 			 	
	 }
	 //在pf中找interface,找不到就在最后加一条,找到多条，删除重复的	 
	 public static String searchInterface(String res,Interface intf,Boolean forward)  throws IOException{		 
		 String link_name = intf.getName();
		 String link_from = intf.getFrom();
		 String link_to = intf.getTo();
		 String[] lines = res.split("\n");
		 res = "";
		 Boolean flag = false;
		 for (String line:lines) {
			 PfLink pfLink = getLink(line);
			 if(pfLink==null) {
				 res += line + "\n";
				 continue;
			 }
				 		 
			 if(link_name.contentEquals(pfLink.getName())&&forward
					 &&link_from.contentEquals(pfLink.getFrom())){
				 if(flag) {
//					 System.out.println(line+" 重复出现，删除");
					 line = "";
				 }else {
					 flag = true;
				 }
			}
//			 else if(link_from.contentEquals(pfLink.getFrom())&&forward
//					 &&link_to.contentEquals(pfLink.getTo())
//					 &&!link_name.contentEquals(pfLink.getName())) {
//				 System.out.println(line+" 名字已修改");
//				 flag = true;
//				 break;
//			}
			 else if(link_name.contentEquals(pfLink.getName())&&(!forward)
						 &&link_from.contentEquals(pfLink.getTo())
						 &&link_to.contentEquals(pfLink.getFrom())) {
				 if(flag) {
//					 System.out.println(line+" 重复出现，删除");
				 }else {					 
					 flag = true;
				 }					 
			}
//			else if(link_to.contentEquals(pfLink.getFrom())
//					&&link_from.contentEquals(pfLink.getTo())&&(!forward)
//					 &&!link_name.contentEquals(pfLink.getName())) {
//				 System.out.println(line+" 名字已修改");
//				 flag = true;
//				 break;
//			}		
			 res += line+ "\n";
		}
		 if(!flag) {
			 String line = getInterface(intf,forward);
			 if(!line.contentEquals("")) {
//				 System.out.println(line+" 不存在，加到最后");
				 res += line;
			 }
		 }
		 return res;
	 }
	 
	 public static String getInterface(Interface intf,Boolean forward){
		List<Phenomenon> pheList = intf.getPhenomenonList();
		String from;
		String to;
		if(forward) {
			from = intf.getFrom();
			to = intf.getTo();
		}else {
			from = intf.getTo();
			to = intf.getFrom();
		}
			
		String pheList1 = " {";
		Boolean flag = false;//另一个方向没有现象
		for(Phenomenon phe:pheList) {
			if(phe.getPhenomenon_from().contentEquals(from)
				&& phe.getPhenomenon_to().contentEquals(to)) {							
				pheList1 += phe.getPhenomenon_type() + " " + phe.getPhenomenon_name() + ",";
			}else if(phe.getPhenomenon_from().contentEquals(to)
					&& phe.getPhenomenon_to().contentEquals(from)) {
				flag = true;//另一个方向没有现象
			}
		}
		pheList1= pheList1.substring(0,pheList1.length()-1);
		if(pheList1.length()>1) {					
			pheList1 += " } ";				
		}
		//有现象，或正向且另一方向无现象
		if(forward&&flag||pheList1.length()>1) {
			String line = "";
			line += from;
			line += " -- ";
			line += to;
			line += pheList1;
			line += " \"" + intf.getName()+"\"";
			line += "\n";
			return line;
		}
		return "";
		
	 }
	 //若line在res中出现，则不做任何处理，否则加入到res后面
	 public static String addLine(String line,String res) {
		 //System.out.println(line);
		 Boolean flag = false;
		 String[] lines = res.split("\n");
		 res = "";
		 for(String row : lines) {
			 
			 String temp = row +"\n";
			 //System.out.println("temp="+temp);
			 if(temp.contentEquals(line)) {	
				 if(flag) {
//					 System.out.println(line + " 重复出现，删除");
				 }else {
					 res += temp;
					 flag = true;
				 }				 
			 }else {
				 res += temp;
			 }
		 }
		 if(!flag) {
//			 System.out.println(line + " 未出现，添加");
			 res += line + "\n";
		 }
			 
		 return res;
	 }
	 public static String getMachinePf(Machine machine) {
		 String res = "";
		 res += machine.getShortname();
		 res += " M ";
		 if(machine.getName()!="")
			 res += "\"" + machine.getName() + "\"";
		 res += "\n";
		 return res;		 
	 }
	 public static String getProblemDomainPf(ProblemDomain pd) {
		 String res = "";
		 res += pd.getShortname();
		 if(pd.getProperty().contentEquals("DesignDomain")) {
			res += " D ";
		 }else if(pd.getType()==null) {
				res += " ? ";
		 }else if(pd.getType().contentEquals("Causal")) {
			res += " C ";
		 }else if(pd.getType().contentEquals("Biddable")) {
			res += " B ";
		 }else if(pd.getType().contentEquals("Lexical")) {
			res += " X ";
		 }
		 if(pd.getName()!="")
			 res += "\"" + pd.getName()+ "\"";
		 res += "\n";
		 return res;
	 }
	 public static String getRequirementPf(Requirement req) {
		String line = "";
		//requirement
		line += req.getShortname();			
		line += " R";
		if(req.getName()!="")
			line += " \""+req.getName()+"\"";
		line += "\n";	
		return line;
	 }

//	 public static String changeInterfacePf(Interface intf,String res) {
//		List<Phenomenon> pheList = intf.getPhenomenonList();
//		//遍历pf中phe	
//		List<String> arrayList = Arrays.asList(pheList);
//		List<String> arrList = new ArrayList<String>(arrayList);
//		Iterator<String> it = arrList.iterator();
//		while(it.hasNext()){
//			String phe = it.next();
//			String[] s1= phe.split("\"");
//			String des = "";
//			if(s1.length>1) {
//				des = s1[1];
//			}
//			String[] s2 = s1[0].split(" ");
//			String phetype = s2[0].trim();
//			String phename = s2[1].trim();
//			Boolean flag=false;
//			for(Phenomenon intphe:intf.get(i).getPhenomenonList()) {
//				if(phename.contentEquals(intphe.getPhenomenon_name())
//						&& from.contentEquals(intphe.getPhenomenon_from())) {	
//					phetype = intphe.getPhenomenon_type();
//					flag = true;
//				}
//			}
//			//delete phe
//			if(!flag)
//				it.remove();	
//		}
//			//遍历project中的phe
//			for(Phenomenon intphe:intf.get(i).getPhenomenonList()) {
//				Boolean flag = false;
//				if(intphe.getPhenomenon_from().contentEquals(from)) {
//					for(String phe:pheList) {
//						String[] s1= phe.split("\"");
//						String des = "";
//						if(s1.length>1) {
//							des = s1[1];
//						}
//						String[] s2 = s1[0].split(" ");
//						String phetype = s2[0].trim();
//						String phename = s2[1].trim();
//						if(phename.contentEquals(intphe.getPhenomenon_name())) {
//							flag=true;			
//						}
//					}
//					//add phe	
//					if(!flag) {
//						String phe = intphe.getPhenomenon_type();
//						phe += " ";
//						phe += intphe.getPhenomenon_name();
//						arrList.add(phe);
//					}
//				}					
//			}
//		 
//			String line = "";
//			line += intf.getInterface_from();
//			line += " -- ";
//			line += intf.getInterface_to()+":";
//			line += " \""+intf.getInterface_name()+"\"";
//			line += "\n";
//			return line;
//		 }
//	 
	 public static String getReferencePf(Reference ref,Project project) {
			
		String from = ref.getFrom();
		String to = ref.getTo();
		List<Requirement> reqList= project.getProblemDiagram().getRequirementList();
		for(Requirement req:reqList) {
			if(from.contentEquals(req.getName()))
				from = req.getShortname();
			else if(to.contentEquals(req.getName()))
				to = req.getShortname();
		}
		List<RequirementPhenomenon> pheList = ref.getPhenomenonList();
		String pheList1 = " { ";
		for(Phenomenon phe:pheList) {
			pheList1 += phe.getPhenomenon_type() + " " + phe.getPhenomenon_name() + ",";
		}
		pheList1= pheList1.substring(0,pheList1.length()-1);
		if(pheList1.length()>2) {					
			pheList1 += " } ";	
		}else {
			pheList1 += "";
		}
		String line =  from;
		line += " ~~ ";
		line += to;
		line += pheList1;
		line += " \"" + ref.getName()+"\"";
		line += "\n\n";
		return line;
	 }
	 public static String getConstraintPf(Constraint con,Project project) {
		String from = con.getFrom();
		String to = con.getTo();
		List<Requirement> reqList= project.getProblemDiagram().getRequirementList();
		for(Requirement req:reqList) {
			if(from.contentEquals(req.getName()))
				from = req.getShortname();
			else if(to.contentEquals(req.getName()))
				to = req.getShortname();
		}
		List<RequirementPhenomenon> pheList = con.getPhenomenonList();
		String pheList1 = " { ";
		for(Phenomenon phe:pheList) {
			pheList1 += phe.getPhenomenon_type() + " " + phe.getPhenomenon_name() + ",";
		}
		pheList1= pheList1.substring(0,pheList1.length()-2);
//		System.out.println("======" + pheList1 +"======");
		if(pheList1.length()>2) {					
			pheList1 += " }";
//			System.out.println("======" + pheList1 +"======");
		}else {
			pheList1 = "";	
//			System.out.println("======" + pheList1 +"======");
		}
		String line =from;
		line += " ~> ";
		line += to;
		line += pheList1;
		line += " \"" + con.getName()+"\"";
		line += "\n";
//		System.out.println("======" + line +"======");
		return line;
	}
	 public static void deleteOverlapInt(List<Interface> interfaces) {
		 for(int i = 0; i < interfaces.size(); i ++ ) {
			 Interface intei = interfaces.get(i);
//			 System.out.println("-------------------------");
//			 System.out.println(intei.getInterface_name());
//			 System.out.println(intei.getInterface_from());
			 for(int j = interfaces.size() - 1; j > i ; j --) {
				 Interface intej = interfaces.get(j);
//				 System.out.println("to:" + intej.getInterface_to());
				 if(intei.getFrom().equals(intej.getTo()) && 
						 intei.getTo().equals(intej.getFrom())) {
					 String name = intei.getName() + "," + intej.getName();
					 intei.setName(name);
					 intei.getPhenomenonList().addAll(intej.getPhenomenonList());
					 String description = name + ":";
					 if(intej.getDescription().split(":").length == 1) {
						 if(intei.getDescription().split(":").length != 1) {
							 description = description + intei.getDescription().split(":")[1];
						 }
					 }else {
						 if(intei.getDescription().split(":").length == 1) {
							 description = description + intej.getDescription().split(":")[1];
						 }else {
							 description = description + intei.getDescription().split(":")[1] + "," + intej.getDescription().split(":")[1];
						 }
					 }
					 intei.setDescription(description);
					 interfaces.remove(intej);
				 }
			 }
		 }
	 }
	 
	 public static void deleteOverlapRef(List<Reference> references) {
		 for(int i = 0; i < references.size(); i ++ ) {
			 Reference referencei = references.get(i);
			 for(int j = references.size() - 1; j > i ; j --) {
				 Reference referencej = references.get(j);
				 if(referencei.getFrom().equals(referencej.getTo()) && 
						 referencei.getTo().equals(referencej.getFrom())) {
					 String name = referencei.getName() + "," + referencej.getName();
					 referencei.setName(name);
					 referencei.getPhenomenonList().addAll(referencej.getPhenomenonList());
					 String description = name + ":";
					 if(referencej.getDescription().split(":").length == 1) {
						 if(referencei.getDescription().split(":").length != 1) {
							 description = description + referencei.getDescription().split(":")[1];
						 }
					 }else {
						 if(referencei.getDescription().split(":").length == 1) {
							 description = description + referencej.getDescription().split(":")[1];
						 }else {
							 description = description + referencej.getDescription().split(":")[1] + "," + referencej.getDescription().split(":")[1];
						 }
					 }
					 referencei.setDescription(description);
					 references.remove(referencej);
				 }
			 }
		 }
	 }
	 
	 public static void deleteOverlapCon(List<Constraint> constraints) {
		 for(int i = 0; i < constraints.size(); i ++ ) {
			 Constraint constrainti = constraints.get(i);
			 for(int j = constraints.size() - 1; j > i ; j --) {
				 Constraint constraintj = constraints.get(j);
				 if(constrainti.getFrom().equals(constraintj.getTo()) && 
						 constrainti.getTo().equals(constraintj.getFrom())) {
					 String name = constrainti.getName() + "," + constraintj.getName();
					 constrainti.setName(name);
					 constrainti.getPhenomenonList().addAll(constraintj.getPhenomenonList());
					 String description = name + ":";
					 if(constraintj.getDescription().split(":").length == 1) {
						 if(constrainti.getDescription().split(":").length != 1) {
							 description = description + constrainti.getDescription().split(":")[1];
						 }
					 }else {
						 if(constrainti.getDescription().split(":").length == 1) {
							 description = description + constraintj.getDescription().split(":")[1];
						 }else {
							 description = description + constrainti.getDescription().split(":")[1] + "," + constraintj.getDescription().split(":")[1];
						 }
					 }
					 constrainti.setDescription(description);
					 constraints.remove(constraintj);
				 }
			 }
		 }
	 }
	 
}
