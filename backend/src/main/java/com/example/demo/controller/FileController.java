package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.MyOntClass;
import com.example.demo.bean.Project;
import com.example.demo.service.FileService;
import com.example.demo.service.ProblemEditor;
import com.example.demo.service.ProjectService;

@CrossOrigin
@RestController
//@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired	//自动装配
	FileService fileService;
	@Autowired	//自动装配
	ProjectService projectService;
	
	@RequestMapping("/upload/{projectAddress}")
	public boolean upload(@RequestParam("xmlFile") MultipartFile uploadFile, @PathVariable("projectAddress") String projectAddress)
//	public void upload(@RequestParam("file") File uploadFile,@PathVariable("projectAddress") String projectAddress)
			throws IOException{
		String branch = projectAddress;
		if (uploadFile == null) {
            System.out.println("上传失败，无法找到文件！");
        }
        // BMP、JPG、JPEG、PNG、GIF
        String fileName = uploadFile.getOriginalFilename();
//        if (fileName.endsWith(".pf")) {
//        	this.uploadpf(uploadFile, projectAddress);
//        	return true;
//        }
        if (!fileName.endsWith(".xml")) {
        	System.out.println("上传失败，请选择xml文件！");
        }
        //逻辑处理
        
        fileService.addFile(uploadFile, projectAddress, branch);
        System.out.println(fileName + "上传成功");
        return true;
	}
	
	@RequestMapping(value="/setProject/{projectAddress}",method = RequestMethod.POST)
	@ResponseBody
	public boolean setProject(@PathVariable("projectAddress") String projectAddress ) {
		String branch = projectAddress;
		boolean result = fileService.setProject(projectAddress, branch); 
		return result;
	}
	
	@RequestMapping(value="/getProject/{projectAddress}/{version}",method = RequestMethod.GET)
	@ResponseBody
	public Project getProject(@PathVariable("projectAddress") String projectAddress, @PathVariable("version") String version) {
		System.out.println(projectAddress + version + "*********************");
		String branch = projectAddress;
		Project project = fileService.getProject(projectAddress, version, branch);
		System.out.println("getProject"+project.getTitle());
		/*
		 * if((project.getScenarioGraphList() == null) ||
		 * project.getScenarioGraphList().size() <
		 * project.getProblemDiagram().getRequirementList().size()) {
		 * if(project.getScenarioGraphList() == null) { List<ScenarioGraph>
		 * scenarioGraphList = new ArrayList<ScenarioGraph>();
		 * scenarioGraphList.addAll(projectService.getNewSGList(project));
		 * project.setScenarioGraphList(scenarioGraphList); }else {
		 * project.getScenarioGraphList().addAll(projectService.getNewSGList(project));
		 * } }
		 */
		return project;
	}
	
	//获取项目文件夹下pf文件的内容，若不存在pf文件返回空串
	@RequestMapping(value="/getPf/{projectAddress}/{version}",method = RequestMethod.GET)
	@ResponseBody
	public String getPf(@PathVariable("projectAddress") String projectAddress, @PathVariable("version") String version) {
		System.out.println(projectAddress + version + "*********************");
		String branch = projectAddress;
		String pf = fileService.getPf(projectAddress, version, branch);
		System.out.println("getPf"+projectAddress);		
		return pf;
	}
	
	//获取项目文件夹下pf文件的内容，若不存在pf文件，则将xml文件转为pf文件，再返给客户端
	@RequestMapping(value="/getNotNullPf/{projectAddress}/{version}",method = RequestMethod.GET)
	@ResponseBody
	public String getNotNullPf(@PathVariable("projectAddress") String projectAddress, @PathVariable("version") String version) {
		System.out.println(projectAddress + version + "*********************");
		String branch = projectAddress;
		String pf = fileService.getNotNullPf(projectAddress, version, branch);
		System.out.println("getNotNullPf"+projectAddress);		
		return pf;
	}
	
	@RequestMapping(value="/saveProject/{projectAddress}",method = RequestMethod.POST)
	@ResponseBody
	public boolean saveProjecr(@PathVariable("projectAddress") String projectAddress, @RequestBody Project project) {
		String branch = projectAddress;
		boolean result = false;
		result = fileService.saveProject(projectAddress, project, branch);
		return result;
	}
	
	@RequestMapping(value="/savePf/{projectAddress}",method = RequestMethod.POST)
	@ResponseBody
	public boolean savePf(@PathVariable("projectAddress") String projectAddress, @RequestBody String pf) {
		boolean result = false;
		result = fileService.savePf(projectAddress,pf);
		return result;
	}
	
//	//将project转为pf文件，保存在项目文件夹下，成功返回true
//	@RequestMapping(value="/ProjectToPf/{projectAddress}",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public boolean project2pf(@PathVariable("projectAddress") String projectAddress, @RequestBody Project project) {
//		System.out.println(projectAddress);
//		String branch = projectAddress;
//		boolean result = false;		
//		result = ProblemEditor.project2pf(projectAddress,project, branch);
//		return result;
//	}
	
	@RequestMapping(value="/format/{projectAddress}",method = RequestMethod.POST)
	@ResponseBody
	public boolean formar(@PathVariable("projectAddress") String projectAddress, @RequestBody Project project) {
		String branch = projectAddress;
		boolean result = false;
		result = fileService.format(projectAddress, project,branch);
		return result;
	}
	
	@RequestMapping( value = "/download/{projectAddress}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void download( @PathVariable("projectAddress") String projectAddress,  HttpServletResponse resp) {
		String branch = projectAddress;
		String fileName = projectAddress.split("/")[0];
		fileService.download(projectAddress, fileName, resp, branch);
	}
	
	@RequestMapping( value = "/formatdownload/{projectAddress}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void format_download( @PathVariable("projectAddress") String projectAddress,  HttpServletResponse resp) {
		String branch = projectAddress;
		String fileName = projectAddress.split("/")[0];
		fileService.format_download(projectAddress, fileName, resp, branch);
	}
	
	//获取所有project列表
	@RequestMapping(value="/searchProject",method = RequestMethod.GET)
	@ResponseBody
	public List<String> searchProject() {
		String branch = "test";
		System.out.println("searchProject");
		List<String> projects = fileService.searchProject(branch);
		System.out.println(projects.size());
		return projects;
	}
		
	 @RequestMapping(value="/searchVersion/{project}",method = RequestMethod.GET)
	 @ResponseBody 
	 public List<String> searchVersion(@PathVariable("project") String project) 
	 { 
		 System.out.println(project);
		 String branch = project;
		 System.out.println("searchVersion");
		 List<String> versions = fileService.searchVersion(project, branch);
		 System.out.println(versions.size());
		 return versions;
	 }
	 
	@RequestMapping("/getProblemDomains/{owlAdd}/{owlName}")
	public ArrayList<MyOntClass> getProblemDomains(@PathVariable("owlAdd") String owlAdd,@PathVariable("owlName") String owlName) {
		ArrayList<MyOntClass> re = null;
		String branch = owlAdd;
		re = fileService.GetProblemDomains(owlName,branch);
	    return re;
	}
		
		@RequestMapping("/getNodes/{owlAdd}/{owlName}/{nodeName}/{type}")
		public String[] getNodes(@PathVariable("owlAdd") String owlAdd,@PathVariable("owlName") String owlName,@PathVariable("nodeName") String nodeName,
				@PathVariable("type") String type) {
		    String[] re = null;
		    String branch = owlAdd;
		    if(type.equals("powl"))
		    re = fileService.pOntShowGetNodes(owlName,nodeName, branch);    
		    else if(type.equals("eowl"))
		    re = fileService.eOntShowGetNodes(owlName,nodeName, branch);
		    return re;
		}
		
		@RequestMapping("/uploadpf/{projectAddress}")
		public void uploadpf(@RequestParam("xmlFile") MultipartFile uploadFile, @PathVariable("projectAddress") String projectAddress)
//		public void upload(@RequestParam("file") File uploadFile,@PathVariable("projectAddress") String projectAddress)
				throws IOException{
			String branch = projectAddress;
			System.out.println(projectAddress + "*****************");
			if (uploadFile == null) {
	            System.out.println("上传失败，无法找到文件！");
	        }
	        // BMP、JPG、JPEG、PNG、GIF
	        String fileName = uploadFile.getOriginalFilename();
	        if (!fileName.endsWith(".pf")) {
	        	System.out.println("上传失败，请选择pf文件！");
	        }
	        //逻辑处理
	        
	        fileService.addpfFile(uploadFile);
//	        this.getXMI(fileName,projectAddress);
	        System.out.println(fileName + "上传成功");
		}
		//根据pf文件名获取转换后的project
		@RequestMapping(value="/xtextToXmi",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		@ResponseBody
		public Project getXMI(@RequestBody String projectAddress) {			
			System.out.println("xtextToXmi projectAddress="+projectAddress);
			File xmlFile = ProblemEditor.performSave(projectAddress+".pf");
			Project project = ProblemEditor.transformXML(xmlFile);
			String branch = projectAddress;
			fileService.saveProject(projectAddress, project, branch);
			return project;
		}

}
