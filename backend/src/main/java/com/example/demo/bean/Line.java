package com.example.demo.bean;

import java.util.List;

public abstract class Line extends Shape{
	
	public abstract String getDescription();
	public abstract void setDescription(String interface_description);
	
	public abstract String getFrom() ;
	public abstract void setFrom(String interface_from) ;
	
	public abstract String getTo() ;
	public abstract void setTo(String interface_to) ;
	
	public abstract int getX1() ;
	public abstract void setX1(int interface_x1);
	
	public abstract int getY1();
	public abstract void setY1(int interface_y1);
	
	public abstract int getX2();
	public abstract void setX2(int interface_x2) ;
	
	public abstract int getY2();
	public abstract void setY2(int interface_y2);
}
