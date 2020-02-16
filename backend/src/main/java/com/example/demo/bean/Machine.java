package com.example.demo.bean;

public class Machine extends Node{
	private String name;	//机器名称
	private String shortName;	//名称缩写
	private int x;	//位置信息
	private int y;
	private int h;
	private int w;
	public Machine() {
		super();
	}
	public Machine(String name,String shortName,int x, int y, int w,int h){
		this.name = name;
		this.shortName = shortName;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public int getNo() {
		return 0;
	}
	@Override
	public void setNo(int node_no) {	
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
}
