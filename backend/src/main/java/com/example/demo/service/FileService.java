package com.example.demo.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.Constraint;
import com.example.demo.bean.ContextDiagram;
import com.example.demo.bean.EnvEntity;
import com.example.demo.bean.Interface;
import com.example.demo.bean.Line;
import com.example.demo.bean.Machine;
import com.example.demo.bean.MyOntClass;
import com.example.demo.bean.Node;
import com.example.demo.bean.OntologyShow;
import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.ProblemDomain;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.bean.RequirementPhenomenon;
import com.example.demo.bean.SubProblemDiagram;
import com.example.demo.bean.versionInfo;
import com.example.demo.service.AddressService;
@Service
public class FileService {
	private File projectFolder;	//项目目录
	/* private File versionFolder; */
	private String rootAddress = AddressService.rootAddress;

	private String pfrootAddress = AddressService.pfRootAddress;
	public void addFile(MultipartFile file, String projectAddress, String branch) {
		try {
			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
			GitUtil.currentBranch(rootAddress);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		saveUploadFile(file, rootAddress);
		try {
			GitUtil.RecordUploadProjAt("uploadfile", rootAddress, ".");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void addpfFile(MultipartFile file) {
//		try {
//			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
//			GitUtil.currentBranch(rootAddress);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		saveUploadFile(file);
//		try {
//			GitUtil.RecordUploadProjAt("uploadfile", rootAddress, ".");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
	}
	
	//创建文件夹
	public boolean setProject(String projectAddress, String branch) {
		// TODO Auto-generated method stub
		boolean res = false;
		File file = new File(rootAddress);
		if(!file.exists()) {
			try {
				Repository repository = GitUtil.createRepository(rootAddress);
				GitUtil.RecordUploadProjAt("upload", rootAddress, rootAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if(!GitUtil.branchNameExist(branch, rootAddress)) {	//创建分支
				GitUtil.createBranch(branch, rootAddress);

			}
			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
			GitUtil.currentBranch(rootAddress);
			res = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		return res;
	}
	
	//保存上传文件
	public void saveUploadFile(MultipartFile mf,String projectAddress){
		String filePath = projectAddress + mf.getOriginalFilename();
		System.out.println(filePath);
		File file = new File(filePath);
		try {
			mf.transferTo(file);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//保存上传文件
	public void saveUploadFile(MultipartFile mf){
		String filePath = "E:/GitHub/PF/PF1-web/backend/pf/" + mf.getOriginalFilename();
		System.out.println(filePath);
		File file = new File(filePath);
		try {
			mf.transferTo(file);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//保存pf文件到项目文件夹
	public boolean savePf(String projectAddress, String pf) {
		try {
			GitUtil.gitCheckout(projectAddress, rootAddress);  //切换分支
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setProject(projectAddress, projectAddress);
		//保存pf文件
		String filename = rootAddress + projectAddress+".pf";
        File file =new File(filename);  
        try {
        	 if(!file.exists()){
        		 file.createNewFile();
        	 }
             FileWriter fileWritter = new FileWriter(filename); 
             System.out.println(file.getName());
             System.out.println(pf);
             fileWritter.write(pf);
             fileWritter.flush();
             fileWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			GitUtil.RecordUploadProjAt("savepf", rootAddress, ".");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	//从xml文件中读取Project
	public Project getProject(String projectAddress, String version, String branch) {
		Project project = new Project();
		SAXReader saxReader = new SAXReader();
		List<versionInfo> versions = searchVersionInfo(projectAddress, branch);
		try {
			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
			GitUtil.rollback(branch, rootAddress, projectAddress, version, versions);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			File xmlFile = new File(rootAddress + "Project.xml");
			Document document = saxReader.read(xmlFile);
			
			Element projectElement = document.getRootElement();
			Element titleElement = projectElement.element("title");
			Element fileListElement = projectElement.element("fileList");
			Element contextDiagramElement = fileListElement.element("ContextDiagram");
			Element problemDiagramElement = fileListElement.element("ProblemDiagram");
			System.out.println("ProblemDiagram");
			/*
			 * Element senarioGraphListElement =
			 * fileListElement.element("SenarioGraphList"); Element
			 * subProblemDiagramListElement =
			 * fileListElement.element("SubProblemDiagramList");
			 */
			
			String title = titleElement.getText();
			System.out.println(title);
			String contextDiagramName = contextDiagramElement.getText();
			String problemDiagramName = problemDiagramElement.getText();
			System.out.println(problemDiagramName);
			ContextDiagram contextDiagram = getContextDiagram(projectAddress, contextDiagramName);
			ProblemDiagram problemDiagram = getProblemDiagram(projectAddress, problemDiagramName);
											
			project.setTitle(title);
			project.setContextDiagram(contextDiagram);
			project.setProblemDiagram(problemDiagram);	
			
			/*
			 * if(senarioGraphListElement != null) { List<?> senarioGraphElementList =
			 * senarioGraphListElement.elements("SenarioGraph"); List<ScenarioGraph>
			 * scenarioGraphList = getScenarioGraphList(projectAddress, version,
			 * senarioGraphElementList); project.setScenarioGraphList(scenarioGraphList); }
			 * if(subProblemDiagramListElement != null) { List<?>
			 * subProblemDiagramElementList =
			 * subProblemDiagramListElement.elements("SubProblemDiagram");
			 * List<SubProblemDiagram> subProblemDiagramList =
			 * getSubProblemDiagramList(projectAddress, version,
			 * subProblemDiagramElementList);
			 * project.setSubProblemDiagramList(subProblemDiagramList); }
			 */
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}
	//从项目文件夹下获取pf文本
	public String getNotNullPf(String projectAddress, String version, String branch) {
		//get pf text if pf file exist
		String res=getPf(projectAddress,version,branch);
		if(!res.contentEquals("")) return res;
		//get project
		Project project = getProject(projectAddress, version, branch);
		//project 2 pf file
		boolean result = ProblemEditor.project2pf(projectAddress,project, branch);
		if(result) {
			res=getPf(projectAddress,version,branch);
		}
		return res;			
	}
	
	//从项目文件夹下获取pf文本
	public String getPf(String projectAddress, String version, String branch) {
		List<versionInfo> versions = searchVersionInfo(projectAddress, branch);
		try {
			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
			GitUtil.rollback(branch, rootAddress, projectAddress, version, versions);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		String res="";
		try{
			File file = new File(rootAddress + projectAddress + ".pf");
            InputStream is = new FileInputStream(file);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            res = new String(bytes);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }		
		return res;
	}
	
	private ContextDiagram getContextDiagram(String projectAddress, String contextDiagramName) {
		// TODO Auto-generated method stub
		ContextDiagram contextDiagram = new ContextDiagram();

		System.out.println(rootAddress + contextDiagramName + ".xml");
		SAXReader saxReader = new SAXReader();
		try {
			File contextDiagramFile = new File(rootAddress + contextDiagramName + ".xml");
			if(!contextDiagramFile.exists()) {
				System.out.println("文件不存在");
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

	private ProblemDiagram getProblemDiagram(String projectAddress, String problemDiagramName) {
		// TODO Auto-generated method stub
		ProblemDiagram problemDiagram = new ProblemDiagram();
		SAXReader saxReader = new SAXReader();
		try {
			File problemDiagramFile = new File(rootAddress + problemDiagramName + ".xml");
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
			ContextDiagram contextDiagram = getContextDiagram(projectAddress,  contextDiagramName);
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


//	private List<SubProblemDiagram> getSubProblemDiagramList(String projectAddress,  List<?> subProblemDiagramElementList) {
//		// TODO Auto-generated method stub
//		List<SubProblemDiagram> subProblemDiagramList = new ArrayList<SubProblemDiagram> ();
//		for(Object spde : subProblemDiagramElementList) {
//			Element spdEle = (Element) spde;
//			String subProblemDiagramName = spdEle.getText();
//			SAXReader saxReader = new SAXReader();
//			try {
//				File subProblemDiagramFile = new File(rootAddress + subProblemDiagramName + ".xml");
//				if(!subProblemDiagramFile.exists()) {
//					return null;
//				}
//				Document document = saxReader.read(subProblemDiagramFile);
//				
//				Element subProblemDiagramElement = document.getRootElement();
//				Element titleElement = subProblemDiagramElement.element("title");
//				Element machineElement = subProblemDiagramElement.element("Machine");
//				Element problemDomainListElement = subProblemDiagramElement.element("ProblemDomain");
//				Element requirementElement = subProblemDiagramElement.element("Requirement");
//				Element interfaceListElement = subProblemDiagramElement.element("Interface");
//				Element constraintListElement = subProblemDiagramElement.element("Constraint");
//				Element referenceListElement = subProblemDiagramElement.element("Reference");
//
//				String title = titleElement.getText();
//				Machine machine = getMachine(machineElement);
//				List<ProblemDomain> problemDomainList= getProblemDomainList(problemDomainListElement);
//				Requirement requirement = getRequirement(requirementElement);
//				List<Interface> interfaceList = getInterfaceList(interfaceListElement);
//				List<Constraint> constraintList = getConstraintList(constraintListElement);
//				List<Reference> referenceList = getReferenceList(referenceListElement);
//				
//				SubProblemDiagram subProblemDiagram = new SubProblemDiagram();
//				subProblemDiagram.setTitle(title);
//				subProblemDiagram.setMachine(machine);
//				subProblemDiagram.setProblemDomainList(problemDomainList);
//				subProblemDiagram.setRequirement(requirement);
//				subProblemDiagram.setInterfaceList(interfaceList);
//				subProblemDiagram.setConstraintList(constraintList);
//				subProblemDiagram.setReferenceList(referenceList);
//				
//				subProblemDiagramList.add(subProblemDiagram);
//			}catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return subProblemDiagramList;
//	}

	private Machine getMachine(Element machineElement) {
		// TODO Auto-generated method stub
		Machine machine = new Machine();
		
		String name = machineElement.attributeValue("machine_name");
		name = name.replaceAll("&#x000A", "\n");
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
		List<?> designDomainElementList = designDomainListElement.elements("Element");
		
		for(Object object : givenDomainElementList) {
			ProblemDomain problemDomain = new ProblemDomain();
			Element givenDomainElement = (Element) object;
			
			int no = Integer.parseInt(givenDomainElement.attributeValue("problemdomain_no"));
			String name = givenDomainElement.attributeValue("problemdomain_name");
			name = name.replaceAll("&#x000A", "\n");
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
			String problemdomain_locality = designDomainElement.attributeValue("problemdomain_locality");
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
			String from = interfaceElement.attributeValue("interface_from").replaceAll("&#x000A", "\n");;
			String to = interfaceElement.attributeValue("interface_to").replaceAll("&#x000A", "\n");;
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
			String phenomenon_type = phenomenonElement.attributeValue("phenomenon_type");
			String phenomenon_from = phenomenonElement.attributeValue("phenomenon_from").replaceAll("&#x000A", "\n");;
			String phenomenon_to = phenomenonElement.attributeValue("phenomenon_to").replaceAll("&#x000A", "\n");;
			
			Phenomenon phenomenon = new Phenomenon();
			phenomenon.setPhenomenon_no(phenomenon_no);
			phenomenon.setPhenomenon_name(phenomenon_name);
			phenomenon.setPhenomenon_type(phenomenon_type);
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
		String name = requirementElement.attributeValue("requirement_context").replaceAll("&#x000A", "\n");
		String shortname = requirementElement.attributeValue("requirement_shortname");
		if(shortname==null) {
			shortname = name.replaceAll(" ","");
		}
		String requirement_locality = requirementElement.attributeValue("requirement_locality");
		String[] locality= requirement_locality.split(",");
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

	private List<Constraint> getConstraintList(Element constraintListElement) {
		// TODO Auto-generated method stub
		List<Constraint> constraintList = new ArrayList<Constraint>();
		List<?> constraintElementList = constraintListElement.elements("Element");
		for(Object object : constraintElementList) {
			Element constraintElement = (Element)object;
			List<?> phenomenonElementList = constraintElement.elements("Phenomenon");
			
			int no = Integer.parseInt(constraintElement.attributeValue("constraint_no"));
			String name = constraintElement.attributeValue("constraint_name");
			String description = constraintElement.attributeValue("constraint_description");
			String from = constraintElement.attributeValue("constraint_from").replaceAll("&#x000A", "\n");;
			String to = constraintElement.attributeValue("constraint_to").replaceAll("&#x000A", "\n");;
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
			String phenomenon_type = phenomenonElement.attributeValue("phenomenon_type");
			String phenomenon_from = phenomenonElement.attributeValue("phenomenon_from").replaceAll("&#x000A", "\n");;
			String phenomenon_to = phenomenonElement.attributeValue("phenomenon_to").replaceAll("&#x000A", "\n");;
			String phenomenon_constraint = phenomenonElement.attributeValue("phenomenon_constraint");
			int phenomenon_requirement = Integer.parseInt(phenomenonElement.attributeValue("phenomenon_requirement"));
			
			RequirementPhenomenon phenomenon = new RequirementPhenomenon();
			phenomenon.setPhenomenon_no(phenomenon_no);
			phenomenon.setPhenomenon_name(phenomenon_name);
			phenomenon.setPhenomenon_type(phenomenon_type);
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
			String from = referenceElement.attributeValue("reference_from").replaceAll("&#x000A", "\n");;
			String to = referenceElement.attributeValue("reference_to").replaceAll("&#x000A", "\n");;
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
	

	
	
	



	





	public boolean saveProject(String projectAddress, Project project, String branch) {
		try {
			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		Document document = DocumentHelper.createDocument();
		setProject(projectAddress, branch);
		File temp_PD=  new File(rootAddress + "ProblemDiagram.xml");
		SAXReader saxReader = new SAXReader();
		File xmlFile = new File(rootAddress + "Project.xml");	
		
		
		//if(xmlFile.exists()) {
			//Project temp_project=getProject(projectAddress);
			//temp_PD=temp_project.getProblemDiagram();
			//File xmlPDFile = new File(root + projectAddress + "/ProblemDomain.xml");
		//}	

		if(!temp_PD.exists()) {
			Element projectElement = document.addElement("project");
			Element titleElement = projectElement.addElement("title");
			Element fileListElement = projectElement.addElement("fileList");
			Element contextDiagramElement = fileListElement.addElement("ContextDiagram");
			Element problemDiagramElement = fileListElement.addElement("ProblemDiagram");
			String title = project.getTitle();
			
			titleElement.setText(title);
			ContextDiagram tmp_CD=project.getContextDiagram();
			ProblemDiagram tmp_PD=project.getProblemDiagram();
			if(tmp_CD!=null) {
				contextDiagramElement.setText("ContextDiagram");
				ContextDiagram tmp_save=saveContextDiagram(projectAddress,tmp_CD);
			}
			if(tmp_PD!=null) {
				problemDiagramElement.setText("ProblemDiagram");
				ProblemDiagram problemDiagram = saveProblemDiagram(projectAddress,tmp_PD);
			}
			StringWriter strWtr = new StringWriter();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter xmlWriter = new XMLWriter(strWtr, format);
				try {
					xmlWriter.write(document);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(xmlFile.exists()==true) {
				xmlFile.delete();
			}
			try {
				xmlFile.createNewFile();
				XMLWriter out = new XMLWriter(new FileWriter(xmlFile));
				// XMLWriter out = new XMLWriter(new FileWriter(file), format);
				out.write(document);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			ContextDiagram tmp_CD=project.getContextDiagram();
			ProblemDiagram tmp_PD=project.getProblemDiagram();
			if(tmp_CD!=null) {
				ContextDiagram tmp_save=saveContextDiagram(projectAddress,tmp_CD);
			}
			if(tmp_PD!=null) {
				ProblemDiagram problemDiagram = saveProblemDiagram(projectAddress,tmp_PD);
			}
		}
		try {
			GitUtil.RecordUploadProjAt("save", rootAddress, ".");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return true;
	}
	
	public boolean format(String projectAddress, Project project,String branch) {
		try {
			GitUtil.gitCheckout(branch, rootAddress);  //切换分支
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.setProject(projectAddress, branch);
		TransXML.saveProject(project, projectAddress);
		return true;
	}
	
	private void copyFile(File sourceFile, File targetFile) throws IOException{
	// TODO Auto-generated method stub
		// 新建文件输入流并对它进行缓冲
		if(sourceFile.isDirectory()) {
			return;
		}
		String fileName = sourceFile.getName();
		if (!fileName.endsWith(".xml")) {
        	return;
        }
        FileInputStream input = new FileInputStream(sourceFile);  
        BufferedInputStream inBuff=new BufferedInputStream(input);  
  
        // 新建文件输出流并对它进行缓冲   
        FileOutputStream output = new FileOutputStream(targetFile);  
        BufferedOutputStream outBuff=new BufferedOutputStream(output);  
          
        // 缓冲数组   
        byte[] b = new byte[1024 * 5];  
        int len;  
        while ((len =inBuff.read(b)) != -1) {  
            outBuff.write(b, 0, len);  
        }  
        // 刷新此缓冲的输出流   
        outBuff.flush();  
          
        //关闭流   
        inBuff.close();  
        outBuff.close();  
        output.close();  
        input.close();  
    } 
	
	private ContextDiagram saveContextDiagram(String projectAddress,ContextDiagram contextDiagram) {
		// TODO Auto-generated method stub
		SAXReader saxReader = new SAXReader();
		Document document = DocumentHelper.createDocument();
		File xmlFile = new File(rootAddress + "/ContextDiagram.xml");
		Element contextDiagramElement=document.addElement("ContextDiaagram");
		Element titleElement = contextDiagramElement.addElement("title");
		Element machineElement = contextDiagramElement.addElement("Machine");
		Element problemDomainListElement = contextDiagramElement.addElement("ProblemDomain");
		Element givenDomainListElement = problemDomainListElement.addElement("GivenDomain");
		Element designDomainListElement = problemDomainListElement.addElement("DesignDomain");
		Element interfaceListElement = contextDiagramElement.addElement("Interface");
		titleElement.setText("ContextDiagram");
		Machine machine = contextDiagram.getMachine();
		List<ProblemDomain> problemDomainList= contextDiagram.getProblemDomainList();
		List<Interface> interfaceList = contextDiagram.getInterfaceList();
		if(machine!=null) {
			machineElement.addAttribute("machine_name", machine.getName().replaceAll("\n", "&#x000A"));
			machineElement.addAttribute("machine_shortname", machine.getShortname());
			StringBuffer re = new StringBuffer();
			re.append(machine.getX());
			re.append(",");
			re.append(machine.getY());
			re.append(",");
			re.append(machine.getH());
			re.append(",");
			re.append(machine.getW());
			machineElement.addAttribute("machine_locality",re.toString());
		}
		if(problemDomainList.size()>0) {

			//List<?> givenDomainElementList = givenDomainListElement.elements("Element");
			//List<?> designDomainElementList = designDomainListElement.elements("Element ");
			for(int i=0;i<problemDomainList.size();i++) {
				ProblemDomain tmp_PD=problemDomainList.get(i);
				String property=tmp_PD.getProperty();
				String no = String.valueOf(tmp_PD.getNo());
				String name = tmp_PD.getName().replaceAll("\n", "&#x000A");
				String shortname = tmp_PD.getShortname();
				String type = tmp_PD.getType();
				StringBuffer re = new StringBuffer();
				re.append(tmp_PD.getX());
				re.append(",");
				re.append(tmp_PD.getY());
				re.append(",");
				re.append(tmp_PD.getH());
				re.append(",");
				re.append(tmp_PD.getW());
				String locality = re.toString();
				if(property.equals("GivenDomain")) {

					Element givenDomainElement = givenDomainListElement.addElement("Element");
					givenDomainElement.addAttribute("problemdomain_no", no);
					givenDomainElement.addAttribute("problemdomain_name", name);
					givenDomainElement.addAttribute("problemdomain_shortname", shortname);
					givenDomainElement.addAttribute("problemdomain_type", type);
					givenDomainElement.addAttribute("problemdomain_locality",locality);
				}
				else {
					Element designDomainElement = designDomainListElement.addElement("Element");
					designDomainElement.addAttribute("problemdomain_no", no);
					designDomainElement.addAttribute("problemdomain_name", name);
					designDomainElement.addAttribute("problemdomain_shortname", shortname);
					designDomainElement.addAttribute("problemdomain_type", type);
					designDomainElement.addAttribute("problemdomain_locality",locality);
				}

			}
		}
		if(interfaceList.size()>0) {
			for(int i=0;i<interfaceList.size();i++) {
				Interface tmp_i=interfaceList.get(i);
				List<Phenomenon> phenomenonElementList = tmp_i.getPhenomenonList();
				String no = String.valueOf(tmp_i.getNo());
				String name = tmp_i.getName();
				String description = tmp_i.getDescription();
				String shortname=machine.getShortname();
				String to="";
				String from="";
				if(tmp_i.getTo().equals(shortname)) {
					to = tmp_i.getFrom().replaceAll("\n", "&#x000A");
					from = tmp_i.getTo().replaceAll("\n", "&#x000A");
				}
				else {
				from = tmp_i.getFrom().replaceAll("\n", "&#x000A");
				to = tmp_i.getTo().replaceAll("\n", "&#x000A");
				}
				StringBuffer re = new StringBuffer();
				re.append(tmp_i.getX1());
				re.append(",");
				re.append(tmp_i.getX2());
				re.append(",");
				re.append(tmp_i.getY1());
				re.append(",");
				re.append(tmp_i.getY1());
				
				String interface_locality = re.toString();
				
				Element interfaceElement=interfaceListElement.addElement("Element");
				interfaceElement.addAttribute("interface_description", description);
				interfaceElement.addAttribute("interface_no", no);
				interfaceElement.addAttribute("interface_locality",locality);
				interfaceElement.addAttribute("interface_from", from);
				interfaceElement.addAttribute("interface_to", to);
				interfaceElement.addAttribute("interface_name", name);
				
				for(int j=0;j<phenomenonElementList.size();j++) {

					Phenomenon tmp_p=phenomenonElementList.get(j);
					
					String phenomenon_no = String.valueOf(tmp_p.getPhenomenon_no());
					String phenomenon_name = tmp_p.getPhenomenon_name();
					String phenomenon_type = tmp_p.getPhenomenon_type();
					String phenomenon_from = tmp_p.getPhenomenon_from();
					String phenomenon_to = tmp_p.getPhenomenon_to();
					Element phenomenonElement=interfaceElement.addElement("Phenomenon");
					phenomenonElement.addAttribute("phenomenon_no", phenomenon_no);
					phenomenonElement.addAttribute("phenomenon_name", phenomenon_name);
					phenomenonElement.addAttribute("phenomenon_type", phenomenon_type);
					phenomenonElement.addAttribute("phenomenon_from", phenomenon_from);
					phenomenonElement.addAttribute("phenomenon_to", phenomenon_to);
					
				}
			}
		}
		StringWriter strWtr = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(strWtr, format);
			try {
				xmlWriter.write(document);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(xmlFile.exists()==true) {
			xmlFile.delete();
		}
		try {
			xmlFile.createNewFile();
			XMLWriter out = new XMLWriter(new FileWriter(xmlFile));
			// XMLWriter out = new XMLWriter(new FileWriter(file), format);
			out.write(document);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contextDiagram;
	}
	
	private ProblemDiagram saveProblemDiagram(String projectAddress,ProblemDiagram problemDiagram) {
		
		SAXReader saxReader = new SAXReader();
		Document document = DocumentHelper.createDocument();
		File xmlFile = new File(rootAddress + "/ProblemDiagram.xml");
		Element problemDiagramElement=document.addElement("ProblemDiagram");
		Element contextDiagramElement=problemDiagramElement.addElement("ContextDiagram");
		Element titleElement = problemDiagramElement.addElement("title");
		Element requirementListElement = problemDiagramElement.addElement("Requirement");
		Element constraintListElement = problemDiagramElement.addElement("Constraint");
		Element referenceListElement = problemDiagramElement.addElement("Reference");
		contextDiagramElement.setText("ContextDiagram");
		titleElement.setText("ProblemDiagram");
		List<Requirement> requirementList=problemDiagram.getRequirementList();	
		List<Constraint> constraintList=problemDiagram.getConstraintList();	
		List<Reference> referenceList=problemDiagram.getReferenceList();	
		
		for(int i=0;i<requirementList.size();i++) {
			Requirement tmp_req=requirementList.get(i);
			String no = String.valueOf(tmp_req.getNo());
			String name = tmp_req.getName().replaceAll("\n", "&#x000A");
			String shortname = tmp_req.getShortname();
			StringBuffer re = new StringBuffer();
			re.append(tmp_req.getX());
			re.append(",");
			re.append(tmp_req.getY());
			re.append(",");
			re.append(tmp_req.getH());
			re.append(",");
			re.append(tmp_req.getW());
			String locality = re.toString();
			Element requirementElement=requirementListElement.addElement("Element");
			requirementElement.addAttribute("requirement_no", no);
			requirementElement.addAttribute("requirement_context", name);
			requirementElement.addAttribute("requirement_shortname", shortname);
			requirementElement.addAttribute("requirement_locality", locality);
		}
		
		if(constraintList.size()>0) {
			for(int i=0;i<constraintList.size();i++) {
				Constraint tmp_c=constraintList.get(i);
				List<RequirementPhenomenon> phenomenonElementList = tmp_c.getPhenomenonList();
				String no = String.valueOf(tmp_c.getNo());
				String name = tmp_c.getName();
				String description = tmp_c.getDescription();
				String from = tmp_c.getFrom().replaceAll("\n", "&#x000A");
				String to = tmp_c.getTo().replaceAll("\n", "&#x000A");
				StringBuffer re = new StringBuffer();
				re.append(tmp_c.getX1());
				re.append(",");
				re.append(tmp_c.getX2());
				re.append(",");
				re.append(tmp_c.getY1());
				re.append(",");
				re.append(tmp_c.getY1());
				
				String locality = re.toString();
				
				Element constraintElement=constraintListElement.addElement("Element");
				constraintElement.addAttribute("constraint_description", description);
				constraintElement.addAttribute("constraint_no", no);
				constraintElement.addAttribute("constraint_locality",locality);
				constraintElement.addAttribute("constraint_from", from);
				constraintElement.addAttribute("constraint_to", to);
				constraintElement.addAttribute("constraint_name", name);
				
				for(int j=0;j<phenomenonElementList.size();j++) {

					RequirementPhenomenon tmp_p=phenomenonElementList.get(j);
					
					String phenomenon_no = String.valueOf(tmp_p.getPhenomenon_no());
					String phenomenon_name = tmp_p.getPhenomenon_name();
					String phenomenon_type = tmp_p.getPhenomenon_type();
					String phenomenon_from = tmp_p.getPhenomenon_from().replaceAll("\n", "&#x000A");
					String phenomenon_to = tmp_p.getPhenomenon_to().replaceAll("\n", "&#x000A");
					String phenomenon_constraint = tmp_p.getPhenomenon_constraint();
					String phenomenon_requirement = String.valueOf(tmp_p.getPhenomenon_requirement());
					Element phenomenonElement=constraintElement.addElement("Phenomenon");
					phenomenonElement.addAttribute("phenomenon_no", phenomenon_no);
					phenomenonElement.addAttribute("phenomenon_name", phenomenon_name);
					phenomenonElement.addAttribute("phenomenon_type", phenomenon_type);
					phenomenonElement.addAttribute("phenomenon_from", phenomenon_from);
					phenomenonElement.addAttribute("phenomenon_to", phenomenon_to);
					phenomenonElement.addAttribute("phenomenon_constraint", phenomenon_constraint);
					phenomenonElement.addAttribute("phenomenon_requirement", phenomenon_requirement);
					
				}
			}
		}
		
		if(referenceList.size()>0) {
			for(int i=0;i<referenceList.size();i++) {
				Reference tmp_c=referenceList.get(i);
				List<RequirementPhenomenon> phenomenonElementList = tmp_c.getPhenomenonList();
				String no = String.valueOf(tmp_c.getNo());
				String name = tmp_c.getName();
				String description = tmp_c.getDescription();
				String from = tmp_c.getFrom().replaceAll("\n", "&#x000A");
				String to = tmp_c.getTo().replaceAll("\n", "&#x000A");
				StringBuffer re = new StringBuffer();
				re.append(tmp_c.getX1());
				re.append(",");
				re.append(tmp_c.getX2());
				re.append(",");
				re.append(tmp_c.getY1());
				re.append(",");
				re.append(tmp_c.getY1());
				
				String locality = re.toString();
				
				Element ReferenceElement=referenceListElement.addElement("Element");
				ReferenceElement.addAttribute("reference_description", description);
				ReferenceElement.addAttribute("reference_no", no);
				ReferenceElement.addAttribute("reference_locality",locality);
				ReferenceElement.addAttribute("reference_from", from);
				ReferenceElement.addAttribute("reference_to", to);
				ReferenceElement.addAttribute("reference_name", name);
				
				for(int j=0;j<phenomenonElementList.size();j++) {

					RequirementPhenomenon tmp_p=phenomenonElementList.get(j);
					
					String phenomenon_no = String.valueOf(tmp_p.getPhenomenon_no());
					String phenomenon_name = tmp_p.getPhenomenon_name();
					String phenomenon_type = tmp_p.getPhenomenon_type();
					String phenomenon_from = tmp_p.getPhenomenon_from().replaceAll("\n", "&#x000A");
					String phenomenon_to = tmp_p.getPhenomenon_to().replaceAll("\n", "&#x000A");
					String phenomenon_constraint = tmp_p.getPhenomenon_constraint();
					String phenomenon_requirement = String.valueOf(tmp_p.getPhenomenon_requirement());
					Element phenomenonElement=ReferenceElement.addElement("Phenomenon");
					phenomenonElement.addAttribute("phenomenon_no", phenomenon_no);
					phenomenonElement.addAttribute("phenomenon_name", phenomenon_name);
					phenomenonElement.addAttribute("phenomenon_type", phenomenon_type);
					phenomenonElement.addAttribute("phenomenon_from", phenomenon_from);
					phenomenonElement.addAttribute("phenomenon_to", phenomenon_to);
					phenomenonElement.addAttribute("phenomenon_constraint", phenomenon_constraint);
					phenomenonElement.addAttribute("phenomenon_requirement", phenomenon_requirement);
					
				}
			}
		}
		
		StringWriter strWtr = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(strWtr, format);
			try {
				xmlWriter.write(document);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(xmlFile.exists()==true) {
			xmlFile.delete();
		}
		try {
			xmlFile.createNewFile();
			XMLWriter out = new XMLWriter(new FileWriter(xmlFile));
			// XMLWriter out = new XMLWriter(new FileWriter(file), format);
			out.write(document);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return problemDiagram;
	}






	public void download(String projectAddress, String fileName, HttpServletResponse resp, String branch) {
//		String proAdd = rootAddress + projectAddress + "/";
		try {
			GitUtil.gitCheckout(branch, rootAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result = fileToZip(rootAddress, AddressService.downloadRootAddress, fileName);
		System.out.println(result);
		if(!result) {
			return;
		}
		try {
			GitUtil.RecordUploadProjAt("download", rootAddress, ".");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = fileName + ".zip";
        DataInputStream in = null;
        OutputStream out = null;
        try{
            resp.reset();// 清空输出流
            
//            fileName = URLEncoder.encode(fileName,"UTF-8"); 
            fileName = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
            resp.setCharacterEncoding("UTF-8");  
            resp.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头
            resp.setContentType("application/msexcel");// 定义输出类型
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Cache-Control","no-cache"); 
            //输入流：本地文件路径
            in = new DataInputStream(
                    new FileInputStream(new File( AddressService.downloadRootAddress + fileName)));  
            //输出流
            out = resp.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }
        } catch(Exception e){
            e.printStackTrace();
            resp.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(), "UTF-8");  
                String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
                writer.write(data); 
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void format_download(String projectAddress, String fileName, HttpServletResponse resp, String branch) {
//		String proAdd = rootAddress + projectAddress + "/";
		try {
			GitUtil.gitCheckout(branch, rootAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = "GXNU" + ".xml";
        DataInputStream in = null;
        OutputStream out = null;
        try{
            resp.reset();// 清空输出流
            
//            fileName = URLEncoder.encode(fileName,"UTF-8"); 
            fileName = new String(fileName.getBytes("utf-8"),"ISO-8859-1");
            resp.setCharacterEncoding("UTF-8");  
            resp.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头
            resp.setContentType("application/msexcel");// 定义输出类型
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Cache-Control","no-cache"); 
            //输入流：本地文件路径
            in = new DataInputStream(
                    new FileInputStream(new File( AddressService.downloadRootAddress + fileName)));  
            //输出流
            out = resp.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }
        } catch(Exception e){
            e.printStackTrace();
            resp.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(), "UTF-8");  
                String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
                writer.write(data); 
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	 /** 
	  * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  
                	zipFile.delete();  
                }
                File[] sourceFiles = sourceFile.listFiles();  
                if(null == sourceFiles || sourceFiles.length<1){  
                    System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                }else{  
                    fos = new FileOutputStream(zipFile);  
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                    byte[] bufs = new byte[1024*10];  
                    for(int i=0;i<sourceFiles.length;i++){  
                    	if(sourceFiles[i].isDirectory()) {
                    		continue;
                    	}
                    	//创建ZIP实体，并添加进压缩包  
                        ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                        zos.putNextEntry(zipEntry);  
                        //读取待压缩的文件并写进压缩包里  
                        fis = new FileInputStream(sourceFiles[i]);  
                        bis = new BufferedInputStream(fis, 1024*10);  
                        int read = 0;  
                        while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                            zos.write(bufs,0,read);  
                        }  
                    }  
                    flag = true;  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    }

	public List<String> searchProject(String branch) {
		// TODO Auto-generated method stub
		Map<String,String> dicLits = new HashMap<String,String>();
		try {
			dicLits = GitUtil.gitAllBranch(rootAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> projectNames = new ArrayList<String>();
		//key
		for(String key : dicLits.keySet()){
			if(key.equals("master")) {
				continue;
			}
			projectNames.add(key);
		}
		return projectNames;
	}

	public List<versionInfo> searchVersionInfo(String project, String branch){
		List<versionInfo> versions = new ArrayList<versionInfo>();
		try {
			GitUtil.gitCheckout(branch, rootAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String command = "git reflog " + branch;
		File check = new File(rootAddress);
		
		List<String> vs = new ArrayList<String>();
		String commitVersion = null;
		try {
			Process p1 = Runtime.getRuntime().exec(command,null,check);
			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String s;
	        
	        while ((s = br.readLine()) != null) {
	        	if(s.indexOf("commit") != -1) {
	        		commitVersion = s.split(" ")[0];
	        		vs.add(commitVersion);
	        	}
	        	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String v : vs) {
			String versionCommand = "git show " + v;
			try {
				Process p2 = Runtime.getRuntime().exec(versionCommand,null,check);
				BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
				String str = null;
				String time = null;
				String versionId = null;
				
				while ((str = br.readLine()) != null) {
					if(str.startsWith("commit")) {
		        		versionId = str.split(" ")[1].substring(0, 7);	        		
		        	}
		        	if(str.startsWith("Date:")) {
		        		str = str.substring(8);
		        		time = str.substring(0, str.length() - 6);
		        		str = br.readLine();
		        		String value = br.readLine().split("0")[0];
		        		if(value.indexOf("upload") != -1) {
		        			if(value.indexOf("uploadproject") != -1) {
		        				continue;
		        			}else if(value.indexOf("uploadfile") != -1) {
			        			if(versions.size() > 0) {
			        				if(versions.get(versions.size() - 1).getCommand().indexOf("uploadfile") != -1) {
				        				continue;
				        			}
			        			}
		        			}else {
		        				continue;
		        			}
		        		}
		        		if(value.indexOf("download") != -1) {
		        			continue;
		        		}
		        		versionInfo version = new versionInfo();
		        		version.setVersionId(versionId);
		        		version.setTime(time);
		        		version.setCommand(value);
		        		versions.add(version);
		        	}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		return versions;
	}
	
	 public List<String> searchVersion(String project, String branch) { 
		 // TODO Auto-generated method stub 
		 List<String> projectVersions = new ArrayList<String>();
		 List<versionInfo> versions = searchVersionInfo(project, branch);
		 if(versions != null) {
				for(versionInfo version: versions) {
					projectVersions.add(version.getTime());
				}
		 }
		 return projectVersions; 
	 }
	 
	 public String[] pOntShowGetNodes(String address,String nodeName, String branch) {
//		   String[] re = null;
		   try {
				GitUtil.gitCheckout(branch, rootAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   String owlAdd = rootAddress + address;	   
		   OntologyShow os = new OntologyShow(owlAdd);
		   ArrayList<String> al =  os.deel(nodeName);
		   int length = al.size();
		   String[] re = new String[length];
		   for(int i = 0; i < length; i++) {
			   re[i] = al.get(i);
		   }
		   return re;
	   }
	  public ArrayList<MyOntClass> GetProblemDomains(String address, String branch) {
		  try {
				GitUtil.gitCheckout(branch, rootAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  String owlAdd = rootAddress + address +".owl";
		  System.out.println(owlAdd);
		  EnvEntity ee = new EnvEntity(owlAdd);
		  return ee.getProblemDomains();
	  }
	  public String[] eOntShowGetNodes(String address,String nodeName, String branch) {
		  try {
				GitUtil.gitCheckout(branch, rootAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		  String owlAdd = rootAddress + address;
		  EnvEntity ee = new EnvEntity(owlAdd);
		  ArrayList<String> al = ee.deel(nodeName);
		  int length = al.size();
		  String[] re = new String[length];
		  for(int i = 0; i < length; i++) {
			  re[i] = al.get(i);
		  }
		  return re;
	  }
} 
