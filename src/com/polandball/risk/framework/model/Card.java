package com.polandball.risk.framework.model;

import com.polandball.risk.framework.ui.graphic.ImageBox;

public class Card extends AbstractCard{

	int value;
	String name;
	
	
	public Card(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	
	@Override
	public void setIcon(ImageBox icon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ImageBox getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
