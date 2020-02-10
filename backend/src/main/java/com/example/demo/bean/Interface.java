package com.example.demo.bean;

import java.util.List;

public class Interface extends Line{
	private int interface_no;	//交互编号
	private String interface_name;	//交互名称
	private String interface_description;	//交互内容
	private String interface_from;
	private String interface_to;
	private List<Phenomenon> phenomenonList;	//现象列表
	private int interface_x1;	//位置信息
	private int interface_y1;
	private int interface_x2;
	private int interface_y2;
	@Override
	public int getNo() {
		// TODO Auto-generated method stub
		return interface_no;
	}
	@Override
	public void setNo(int interface_no) {
		// TODO Auto-generated method stub
		this.interface_no = interface_no;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return interface_name;
	}
	@Override
	public void setName(String interface_name) {
		// TODO Auto-generated method stub
		this.interface_name = interface_name;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return interface_description;
	}
	@Override
	public void setDescription(String interface_description) {
		// TODO Auto-generated method stub
		this.interface_description = interface_description;
	}
	@Override
	public String getFrom() {
		// TODO Auto-generated method stub
		return interface_from;
	}
	@Override
	public void setFrom(String interface_from) {
		// TODO Auto-generated method stub
		this.interface_from = interface_from;
	}
	@Override
	public String getTo() {
		// TODO Auto-generated method stub
		return interface_to;
	}
	@Override
	public void setTo(String interface_to) {
		// TODO Auto-generated method stub
		this.interface_to = interface_to;
	}
	@Override
	public int getX1() {
		// TODO Auto-generated method stub
		return interface_x1;
	}
	@Override
	public void setX1(int interface_x1) {
		// TODO Auto-generated method stub
		this.interface_x1 = interface_x1;		
	}
	@Override
	public int getY1() {
		// TODO Auto-generated method stub
		return interface_y1;
	}
	@Override
	public void setY1(int interface_y1) {
		// TODO Auto-generated method stub
		this.interface_y1 = interface_y1;		
	}
	@Override
	public int getX2() {
		// TODO Auto-generated method stub
		return interface_x2;
	}
	@Override
	public void setX2(int interface_x2) {
		// TODO Auto-generated method stub
		this.interface_x2 = interface_x2;
	}
	@Override
	public int getY2() {
		// TODO Auto-generated method stub
		return interface_y2;
	}
	@Override
	public void setY2(int interface_y2) {
		// TODO Auto-generated method stub
		this.interface_y2 = interface_y2;
	}
	public int getInterface_no() {
		return interface_no;
	}
	public void setInterface_no(int interface_no) {
		this.interface_no = interface_no;
	}
	public String getInterface_name() {
		return interface_name;
	}
	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}
	public String getInterface_description() {
		return interface_description;
	}
	public void setInterface_description(String interface_description) {
		this.interface_description = interface_description;
	}
	public String getInterface_from() {
		return interface_from;
	}
	public void setInterface_from(String interface_from) {
		this.interface_from = interface_from;
	}
	public String getInterface_to() {
		return interface_to;
	}
	public void setInterface_to(String interface_to) {
		this.interface_to = interface_to;
	}
	public List<Phenomenon> getPhenomenonList() {
		return phenomenonList;
	}
	public void setPhenomenonList(List<Phenomenon> phenomenonList) {
		this.phenomenonList = phenomenonList;
	}
	public int getInterface_x1() {
		return interface_x1;
	}
	public void setInterface_x1(int interface_x1) {
		this.interface_x1 = interface_x1;
	}
	public int getInterface_y1() {
		return interface_y1;
	}
	public void setInterface_y1(int interface_y1) {
		this.interface_y1 = interface_y1;
	}
	public int getInterface_x2() {
		return interface_x2;
	}
	public void setInterface_x2(int interface_x2) {
		this.interface_x2 = interface_x2;
	}
	public int getInterface_y2() {
		return interface_y2;
	}
	public void setInterface_y2(int interface_y2) {
		this.interface_y2 = interface_y2;
	}
}
