package com.example.demo.bean;

public class Requirement extends Node {
	private int requirement_no;	//需求编号
	private String requirement_context;	//需求描述
	private String requirement_shortname;	//需求描述
	private int requirement_x;	//位置信息
	private int requirement_y;
	private int requirement_h;
	private int requirement_w;
	public int getNo() {
		return requirement_no;
	}
	public void setNo(int requirement_no) {
		this.requirement_no = requirement_no;
	}
	public String getName() {
		return requirement_context;
	}
	public void setName(String requirement_context) {
		this.requirement_context = requirement_context;
	}
	public int getX() {
		return requirement_x;
	}
	public void setX(int requirement_x) {
		this.requirement_x = requirement_x;
	}
	public int getY() {
		return requirement_y;
	}
	public void setY(int requirement_y) {
		this.requirement_y = requirement_y;
	}
	public String getShortName() {
		return requirement_context;
	}
	public void setShortName(String node_no) {
		
	}
	
	public int getRequirement_no() {
		return requirement_no;
	}
	public void setRequirement_no(int requirement_no) {
		this.requirement_no = requirement_no;
	}
	public String getRequirement_context() {
		return requirement_context;
	}
	public void setRequirement_context(String requirement_context) {
		this.requirement_context = requirement_context;
	}
	public String getRequirement_shortname() {
		return requirement_shortname;
	}
	public void setRequirement_shortname(String requirement_shortname) {
		this.requirement_shortname = requirement_shortname;
	}
	public int getRequirement_x() {
		return requirement_x;
	}
	public void setRequirement_x(int requirement_x) {
		this.requirement_x = requirement_x;
	}
	public int getRequirement_y() {
		return requirement_y;
	}
	public void setRequirement_y(int requirement_y) {
		this.requirement_y = requirement_y;
	}
	public int getRequirement_h() {
		return requirement_h;
	}
	public void setRequirement_h(int requirement_h) {
		this.requirement_h = requirement_h;
	}
	public int getRequirement_w() {
		return requirement_w;
	}
	public void setRequirement_w(int requirement_w) {
		this.requirement_w = requirement_w;
	}
}
