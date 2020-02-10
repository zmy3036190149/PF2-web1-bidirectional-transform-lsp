package com.example.demo.bean;

public class Machine extends Node{
	private String machine_name;	//机器名称
	private String machine_shortName;	//名称缩写
	private int machine_x;	//位置信息
	private int machine_y;
	private int machine_h;
	private int machine_w;
	public Machine() {
		super();
	}
	public Machine(String machine_name,String machine_shortName,int machine_x, int machine_y, int machine_w,int machine_h){
		this.machine_name = machine_name;
		this.machine_shortName = machine_shortName;
		this.machine_x = machine_x;
		this.machine_y = machine_y;
		this.machine_w = machine_w;
		this.machine_h = machine_h;
	}
	public String getName() {
		return machine_name;
	}
	public void setName(String machine_name) {
		this.machine_name = machine_name;
	}
	public String getShortName() {
		return machine_shortName;
	}
	public void setShortName(String machine_shortName) {
		this.machine_shortName = machine_shortName;
	}
	public int getX() {
		return machine_x;
	}
	public void setX(int machine_x) {
		this.machine_x = machine_x;
	}
	public int getY() {
		return machine_y;
	}
	public void setY(int machine_y) {
		this.machine_y = machine_y;
	}
	@Override
	public int getNo() {
		return 0;
	}
	@Override
	public void setNo(int node_no) {	
	}
	public String getMachine_name() {
		return machine_name;
	}
	public void setMachine_name(String machine_name) {
		this.machine_name = machine_name;
	}
	public String getMachine_shortName() {
		return machine_shortName;
	}
	public void setMachine_shortName(String machine_shortName) {
		this.machine_shortName = machine_shortName;
	}
	public int getMachine_x() {
		return machine_x;
	}
	public void setMachine_x(int machine_x) {
		this.machine_x = machine_x;
	}
	public int getMachine_y() {
		return machine_y;
	}
	public void setMachine_y(int machine_y) {
		this.machine_y = machine_y;
	}
	public int getMachine_h() {
		return machine_h;
	}
	public void setMachine_h(int machine_h) {
		this.machine_h = machine_h;
	}
	public int getMachine_w() {
		return machine_w;
	}
	public void setMachine_w(int machine_w) {
		this.machine_w = machine_w;
	}
}
