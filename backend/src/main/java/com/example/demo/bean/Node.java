package com.example.demo.bean;

public abstract class Node extends Shape {
	
	
	public abstract String getShortName();
	public abstract void setShortName(String node_no);
	
	public abstract int getX();
	public abstract void setX(int node_x);
	
	public abstract int getY();
	public abstract void setY(int node_y);
}
