package com.example.demo.bean;

import java.util.List;

public class Reference extends ProblemDiagramLine {
	private int reference_no;	//引用编号
	private String reference_name;	//引用名称
	private String reference_description;	//引用详情
	private String reference_from;	
	private String reference_to;
	private int reference_x1;	//位置信息
	private int reference_y1;
	private int reference_x2;
	private int reference_y2;
	private List<RequirementPhenomenon> phenomenonList;	//现象列表

	public int getNo() {
		return reference_no;
	}
	public void setNo(int reference_no) {
		this.reference_no = reference_no;
	}
	public String getName() {
		return reference_name;
	}
	public void setName(String reference_name) {
		this.reference_name = reference_name;
	}
	public String getDescription() {
		return reference_description;
	}
	public void setDescription(String reference_description) {
		this.reference_description = reference_description;
	}
	public String getFrom() {
		return reference_from;
	}
	public void setFrom(String reference_from) {
		this.reference_from = reference_from;
	}
	public String getTo() {
		return reference_to;
	}
	public void setTo(String reference_to) {
		this.reference_to = reference_to;
	}
	public int getX1() {
		return reference_x1;
	}
	public void setX1(int reference_x1) {
		this.reference_x1 = reference_x1;
	}
	public int getY1() {
		return reference_y1;
	}
	public void setY1(int reference_y1) {
		this.reference_y1 = reference_y1;
	}
	public int getX2() {
		return reference_x2;
	}
	public void setX2(int reference_x2) {
		this.reference_x2 = reference_x2;
	}
	public int getY2() {
		return reference_y2;
	}
	public void setY2(int reference_y2) {
		this.reference_y2 = reference_y2;
	}
	public int getReference_no() {
		return reference_no;
	}
	public void setReference_no(int reference_no) {
		this.reference_no = reference_no;
	}
	public String getReference_name() {
		return reference_name;
	}
	public void setReference_name(String reference_name) {
		this.reference_name = reference_name;
	}
	public String getReference_description() {
		return reference_description;
	}
	public void setReference_description(String reference_description) {
		this.reference_description = reference_description;
	}
	public String getReference_from() {
		return reference_from;
	}
	public void setReference_from(String reference_from) {
		this.reference_from = reference_from;
	}
	public String getReference_to() {
		return reference_to;
	}
	public void setReference_to(String reference_to) {
		this.reference_to = reference_to;
	}
	public int getReference_x1() {
		return reference_x1;
	}
	public void setReference_x1(int reference_x1) {
		this.reference_x1 = reference_x1;
	}
	public int getReference_y1() {
		return reference_y1;
	}
	public void setReference_y1(int reference_y1) {
		this.reference_y1 = reference_y1;
	}
	public int getReference_x2() {
		return reference_x2;
	}
	public void setReference_x2(int reference_x2) {
		this.reference_x2 = reference_x2;
	}
	public int getReference_y2() {
		return reference_y2;
	}
	public void setReference_y2(int reference_y2) {
		this.reference_y2 = reference_y2;
	}
	public List<RequirementPhenomenon> getPhenomenonList() {
		return phenomenonList;
	}
	public void setPhenomenonList(List<RequirementPhenomenon> phenomenonList) {
		this.phenomenonList = phenomenonList;
	}
}
