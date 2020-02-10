package com.example.demo.bean;

public class PfPhenomenon {
	private String type;	
	private String name;
	private String description;
	public PfPhenomenon(String type, String name, String description) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
	}
	@Override
	public String toString() {
		String res = type + " " + name;
		if(!description.contentEquals(""))
			res += " \"" + description + "\"";		
		return  res;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
