package com.example.demo.LSP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

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

public class LSPFileService {
	// 保存最新版project，保存为一个文件
	public static boolean saveLastestProject(String rootAddress, String projectAddress, Project project) {
		Document document = DocumentHelper.createDocument();
		SAXReader saxReader = new SAXReader();
		File xmlFile = new File(rootAddress + "loc.xml");
		if (xmlFile.exists()) {
			xmlFile.delete();
		}
		Element projectElement = document.addElement("project");
		Element titleElement = projectElement.addElement("title");
		String title = project.getTitle();
		titleElement.setText(title);

		ContextDiagram tmp_CD = project.getContextDiagram();
		if (tmp_CD != null) {
			projectElement = addContextDiagramElement(projectElement, tmp_CD);
		}

		ProblemDiagram tmp_PD = project.getProblemDiagram();
		if (tmp_PD != null) {
			projectElement = addProblemDiagramElement(projectElement, tmp_PD);
		}

		StringWriter strWtr = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
//		format.setSuppressDeclaration(true);
		format.setIndent(true);
		format.setIndent("    ");
		format.setNewlines(true);
		// 写入文件
		XMLWriter xmlWriter = new XMLWriter(strWtr, format);
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (xmlFile.exists() == true) {
			xmlFile.delete();
		}
		try {
			xmlFile.createNewFile();
			XMLWriter out = new XMLWriter(new FileWriter(xmlFile), format);
			out.write(document);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private static Element addContextDiagramElement(Element document, ContextDiagram contextDiagram) {
		Element contextDiagramElement = document.addElement("ContextDiaagram");
		Element titleElement = contextDiagramElement.addElement("title");
		Element machineElement = contextDiagramElement.addElement("Machine");
		Element problemDomainListElement = contextDiagramElement.addElement("ProblemDomain");
		Element givenDomainListElement = problemDomainListElement.addElement("GivenDomain");
		Element designDomainListElement = problemDomainListElement.addElement("DesignDomain");
		Element interfaceListElement = contextDiagramElement.addElement("Interface");
		titleElement.setText("ContextDiagram");
		Machine machine = contextDiagram.getMachine();
		List<ProblemDomain> problemDomainList = contextDiagram.getProblemDomainList();
		List<Interface> interfaceList = contextDiagram.getInterfaceList();
		if (machine != null) {
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
			machineElement.addAttribute("machine_locality", re.toString());
		}
		if (problemDomainList.size() > 0) {

			// List<?> givenDomainElementList = givenDomainListElement.elements("Element");
			// List<?> designDomainElementList = designDomainListElement.elements("Element
			// ");
			for (int i = 0; i < problemDomainList.size(); i++) {
				ProblemDomain tmp_PD = problemDomainList.get(i);
				String property = tmp_PD.getProperty();
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
				if (property.equals("GivenDomain")) {

					Element givenDomainElement = givenDomainListElement.addElement("Element");
					givenDomainElement.addAttribute("problemdomain_no", no);
					givenDomainElement.addAttribute("problemdomain_name", name);
					givenDomainElement.addAttribute("problemdomain_shortname", shortname);
					givenDomainElement.addAttribute("problemdomain_type", type);
					givenDomainElement.addAttribute("problemdomain_locality", locality);
				} else {
					Element designDomainElement = designDomainListElement.addElement("Element");
					designDomainElement.addAttribute("problemdomain_no", no);
					designDomainElement.addAttribute("problemdomain_name", name);
					designDomainElement.addAttribute("problemdomain_shortname", shortname);
					designDomainElement.addAttribute("problemdomain_type", type);
					designDomainElement.addAttribute("problemdomain_locality", locality);
				}

			}
		}
		if (interfaceList.size() > 0) {
			for (int i = 0; i < interfaceList.size(); i++) {
				Interface tmp_i = interfaceList.get(i);
				List<Phenomenon> phenomenonElementList = tmp_i.getPhenomenonList();
				String no = String.valueOf(tmp_i.getNo());
				String name = tmp_i.getName();
				String description = tmp_i.getDescription();
				String shortname = machine.getShortname();
				String to = "";
				String from = "";
				if (tmp_i.getTo().equals(shortname)) {
					to = tmp_i.getFrom().replaceAll("\n", "&#x000A");
					from = tmp_i.getTo().replaceAll("\n", "&#x000A");
				} else {
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

				String locality = re.toString();

				Element interfaceElement = interfaceListElement.addElement("Element");
				interfaceElement.addAttribute("interface_description", description);
				interfaceElement.addAttribute("interface_no", no);
				interfaceElement.addAttribute("interface_locality", locality);
				interfaceElement.addAttribute("interface_from", from);
				interfaceElement.addAttribute("interface_to", to);
				interfaceElement.addAttribute("interface_name", name);

				for (int j = 0; j < phenomenonElementList.size(); j++) {

					Phenomenon tmp_p = phenomenonElementList.get(j);

					String phe_no = String.valueOf(tmp_p.getNo());
					String phe_name = tmp_p.getName();
					String phe_type = tmp_p.getType();
					String phe_from = tmp_p.getFrom();
					String phe_to = tmp_p.getTo();
					Element phenomenonElement = interfaceElement.addElement("Phenomenon");
					phenomenonElement.addAttribute("phenomenon_no", phe_no);
					phenomenonElement.addAttribute("phenomenon_name", phe_name);
					phenomenonElement.addAttribute("phenomenon_type", phe_type);
					phenomenonElement.addAttribute("phenomenon_from", phe_from);
					phenomenonElement.addAttribute("phenomenon_to", phe_to);

				}
			}
		}
		return document;

	}

	private static Element addProblemDiagramElement(Element document, ProblemDiagram problemDiagram) {
		Element problemDiagramElement = document.addElement("ProblemDiagram");
		Element contextDiagramElement = problemDiagramElement.addElement("ContextDiagram");
		Element titleElement = problemDiagramElement.addElement("title");
		Element requirementListElement = problemDiagramElement.addElement("Requirement");
		Element constraintListElement = problemDiagramElement.addElement("Constraint");
		Element referenceListElement = problemDiagramElement.addElement("Reference");
		contextDiagramElement.setText("ContextDiagram");
		titleElement.setText("ProblemDiagram");
		List<Requirement> requirementList = problemDiagram.getRequirementList();
		List<Constraint> constraintList = problemDiagram.getConstraintList();
		List<Reference> referenceList = problemDiagram.getReferenceList();

		for (int i = 0; i < requirementList.size(); i++) {
			Requirement tmp_req = requirementList.get(i);
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
			Element requirementElement = requirementListElement.addElement("Element");
			requirementElement.addAttribute("requirement_no", no);
			requirementElement.addAttribute("requirement_context", name);
			requirementElement.addAttribute("requirement_shortname", shortname);
			requirementElement.addAttribute("requirement_locality", locality);
		}

		if (constraintList.size() > 0) {
			for (int i = 0; i < constraintList.size(); i++) {
				Constraint tmp_c = constraintList.get(i);
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

				Element constraintElement = constraintListElement.addElement("Element");
				constraintElement.addAttribute("constraint_description", description);
				constraintElement.addAttribute("constraint_no", no);
				constraintElement.addAttribute("constraint_locality", locality);
				constraintElement.addAttribute("constraint_from", from);
				constraintElement.addAttribute("constraint_to", to);
				constraintElement.addAttribute("constraint_name", name);

				for (int j = 0; j < phenomenonElementList.size(); j++) {

					RequirementPhenomenon tmp_p = phenomenonElementList.get(j);

					String phe_no = String.valueOf(tmp_p.getNo());
					String phe_name = tmp_p.getName();
					String phe_type = tmp_p.getType();
					String phe_from = tmp_p.getFrom().replaceAll("\n", "&#x000A");
					String phe_to = tmp_p.getTo().replaceAll("\n", "&#x000A");
					String phe_constraint = tmp_p.getConstraint();
					String phe_requirement = String.valueOf(tmp_p.getRequirement());
					Element phenomenonElement = constraintElement.addElement("Phenomenon");
					phenomenonElement.addAttribute("phenomenon_no", phe_no);
					phenomenonElement.addAttribute("phenomenon_name", phe_name);
					phenomenonElement.addAttribute("phenomenon_type", phe_type);
					phenomenonElement.addAttribute("phenomenon_from", phe_from);
					phenomenonElement.addAttribute("phenomenon_to", phe_to);
					phenomenonElement.addAttribute("phenomenon_constraint", phe_constraint);
					phenomenonElement.addAttribute("phenomenon_requirement", phe_requirement);

				}
			}
		}

		if (referenceList.size() > 0) {
			for (int i = 0; i < referenceList.size(); i++) {
				Reference tmp_c = referenceList.get(i);
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

				Element ReferenceElement = referenceListElement.addElement("Element");
				ReferenceElement.addAttribute("reference_description", description);
				ReferenceElement.addAttribute("reference_no", no);
				ReferenceElement.addAttribute("reference_locality", locality);
				ReferenceElement.addAttribute("reference_from", from);
				ReferenceElement.addAttribute("reference_to", to);
				ReferenceElement.addAttribute("reference_name", name);

				for (int j = 0; j < phenomenonElementList.size(); j++) {

					RequirementPhenomenon tmp_p = phenomenonElementList.get(j);

					String phe_no = String.valueOf(tmp_p.getNo());
					String phe_name = tmp_p.getName();
					String phe_type = tmp_p.getType();
					String phe_from = tmp_p.getFrom().replaceAll("\n", "&#x000A");
					String phe_to = tmp_p.getTo().replaceAll("\n", "&#x000A");
					String phe_constraint = tmp_p.getConstraint();
					String phe_requirement = String.valueOf(tmp_p.getRequirement());
					Element phenomenonElement = ReferenceElement.addElement("Phenomenon");
					phenomenonElement.addAttribute("phenomenon_no", phe_no);
					phenomenonElement.addAttribute("phenomenon_name", phe_name);
					phenomenonElement.addAttribute("phenomenon_type", phe_type);
					phenomenonElement.addAttribute("phenomenon_from", phe_from);
					phenomenonElement.addAttribute("phenomenon_to", phe_to);
					phenomenonElement.addAttribute("phenomenon_constraint", phe_constraint);
					phenomenonElement.addAttribute("phenomenon_requirement", phe_requirement);

				}
			}
		}
		return document;
	}

}
