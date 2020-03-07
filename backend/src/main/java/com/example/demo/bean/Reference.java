package com.example.demo.bean;

import java.util.List;

public class Reference extends ProblemDiagramLine {
	private int no;	//引用编号
	private String name;	//引用名称
	private String description;	//引用详情
	private String from;	
	private String to;
	private int x1;	//位置信息
	private int y1;
	private int x2;
	private int y2;
	private List<RequirementPhenomenon> phenomenonList;	//现象列表
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	public List<RequirementPhenomenon> getPhenomenonList() {
		return phenomenonList;
	}
	public void setPhenomenonList(List<RequirementPhenomenon> phenomenonList) {
		this.phenomenonList = phenomenonList;
	}
	public void refreshPhenomenonList(String oldShortName,String newShortName){
		for(RequirementPhenomenon phenomenon:phenomenonList){
			if(phenomenon.getFrom().equals(oldShortName)) phenomenon.setFrom(newShortName);
    		else if(phenomenon.getTo().equals(oldShortName)) phenomenon.setTo(newShortName);
		}
	}
}
