package com.example.demo.bean;

import java.util.List;

public class Project {
	private String title;	//项目名
	private ContextDiagram contextDiagram;	//上下文图
	private ProblemDiagram problemDiagram;	//问题图
	private List<SubProblemDiagram> subProblemDiagramList;	//子问题图
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ContextDiagram getContextDiagram() {
		return contextDiagram;
	}
	public void setContextDiagram(ContextDiagram contextDiagram) {
		this.contextDiagram = contextDiagram;
	}
	public ProblemDiagram getProblemDiagram() {
		return problemDiagram;
	}
	public void setProblemDiagram(ProblemDiagram problemDiagram) {
		this.problemDiagram = problemDiagram;
	}
	public List<SubProblemDiagram> getSubProblemDiagramList() {
		return subProblemDiagramList;
	}
	public void setSubProblemDiagramList(List<SubProblemDiagram> subProblemDiagramList) {
		this.subProblemDiagramList = subProblemDiagramList;
	}
}
