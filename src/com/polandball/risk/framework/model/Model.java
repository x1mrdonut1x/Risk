package com.polandball.risk.framework.model;

public class Model {
	
	protected String id;
	protected String name;
	
	public String getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
		this.id = hashCode() + name;
	}
	
	@Override
	public String toString(){
		return this.getName();
	}
}