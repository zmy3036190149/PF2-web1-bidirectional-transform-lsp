package com.example.demo.bean;

public class Phenomenon {
	private int phenomenon_no;	//现象编号
	private String phenomenon_name;	//现象名称
	private String phenomenon_type;	//现象类型
	private String phenomenon_from;	//发送方
	private String phenomenon_to;	//接收方
	
	public int getPhenomenon_no() {
		return phenomenon_no;
	}
	public void setPhenomenon_no(int phenomenon_no) {
		this.phenomenon_no = phenomenon_no;
	}
	public String getPhenomenon_name() {
		return phenomenon_name;
	}
	public void setPhenomenon_name(String phenomenon_name) {
		this.phenomenon_name = phenomenon_name;
	}
	public String getPhenomenon_type() {
		return phenomenon_type;
	}
	public void setPhenomenon_type(String phenomenon_type) {
		this.phenomenon_type = phenomenon_type;
	}
	public String getPhenomenon_from() {
		return phenomenon_from;
	}
	public void setPhenomenon_from(String phenomenon_from) {
		this.phenomenon_from = phenomenon_from;
	}
	public String getPhenomenon_to() {
		return phenomenon_to;
	}
	public void setPhenomenon_to(String phenomenon_to) {
		this.phenomenon_to = phenomenon_to;
	}
	
}
