package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.Project;
import com.example.demo.bean.Error;
import com.example.demo.bean.RequirementPhenomenon;
import com.example.demo.service.ProjectService;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {
	@Autowired	//自动装配
	ProjectService projectService;
	
	@RequestMapping(value="/getPhenomenon",method = RequestMethod.POST)
	@ResponseBody
	public List<Phenomenon> getPhenomenon(@RequestBody Project project) {
		List<Phenomenon> phenomenonList = projectService.getPhenomenon(project);
		return phenomenonList;
	}
	
	@RequestMapping(value="/getReference",method = RequestMethod.POST)
	@ResponseBody
	public List<RequirementPhenomenon> getRequirementPhenomenon(@RequestBody Project project) {
		List<RequirementPhenomenon> phenomenonList = projectService.getRequirementPhenomenon(project);
		return phenomenonList;
	}
	
	@RequestMapping(value="/checkPD",method = RequestMethod.POST)
	@ResponseBody
	public boolean checkPD(@RequestBody Project project) {
		boolean res = projectService.checkPD(project.getProblemDiagram());
		return res;
	}
	
	
	@RequestMapping(value="/checkCorrectContext",method = RequestMethod.POST)
	@ResponseBody
	public List<Error> checkCorrectContext(@RequestBody Project project) {
		List<Error> errorList = projectService.checkCorrectContext(project);
		return errorList;
	}
	
	@RequestMapping(value="/checkCorrectProblem",method = RequestMethod.POST)
	@ResponseBody
	public List<Error> checkCorrectProblem(@RequestBody Project project) {
		List<Error> errorList = projectService.checkCorrectProblem(project);
		return errorList;
	}
	
}
