package com.example.demo.LSP.bean;

import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.Shape;

public class DiagramContentChangeEvent {
    String shapeType;
    String changeType;

    //if shapeType in "mac/pro/req/int/ref/con"
    Shape oldShape;
    Shape newShape;

    //if shapeType == "phe"
    LineInfo lineInfo;
    Phenomenon oldPhenomenon;
    Phenomenon newPhenomenon;
	public String getShapeType() {
		return shapeType;
	}
	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public Shape getOldShape() {
		return oldShape;
	}
	public void setOldShape(Shape oldShape) {
		this.oldShape = oldShape;
	}
	public Shape getNewShape() {
		return newShape;
	}
	public void setNewShape(Shape newShape) {
		this.newShape = newShape;
	}
	public LineInfo getLineInfo() {
		return lineInfo;
	}
	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}
	public Phenomenon getOldPhenomenon() {
		return oldPhenomenon;
	}
	public void setOldPhenomenon(Phenomenon oldPhenomenon) {
		this.oldPhenomenon = oldPhenomenon;
	}
	public Phenomenon getNewPhenomenon() {
		return newPhenomenon;
	}
	public void setNewPhenomenon(Phenomenon newPhenomenon) {
		this.newPhenomenon = newPhenomenon;
	}
    
}
