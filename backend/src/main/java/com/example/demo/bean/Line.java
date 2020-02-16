package com.example.demo.bean;

import java.util.List;

public abstract class Line extends Shape{
	
	public abstract String getDescription();
	public abstract void setDescription(String description);
	
	public abstract String getFrom() ;
	public abstract void setFrom(String from) ;
	
	public abstract String getTo() ;
	public abstract void setTo(String to) ;
	
	public abstract int getX1() ;
	public abstract void setX1(int x1);
	
	public abstract int getY1();
	public abstract void setY1(int y1);
	
	public abstract int getX2();
	public abstract void setX2(int x2) ;
	
	public abstract int getY2();
	public abstract void setY2(int y2);
}
