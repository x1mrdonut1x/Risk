package com.polandball.risk.framework.model;

import com.polandball.risk.framework.ui.graphic.ImageBox;

public abstract class AbstractCard extends Model{
	
	protected ImageBox icon;
	protected int type;
	protected int value;
	
	public static final int TYPE_WILD = 0;
	public static final int TYPE_INFANTRY = 1;
	public static final int TYPE_CAVALRY = 2;
	public static final int TYPE_ARTILERY = 3;
	
	public void setIcon(ImageBox icon){
		this.icon = icon;
	}
	
	public ImageBox getIcon(){
		return this.icon;
	}
	
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
	
	public String getShort(){
		
		switch(type){
		case TYPE_WILD:
			return "w";
		case TYPE_INFANTRY:
			return "i";
		case TYPE_CAVALRY:
			return "c";
		case TYPE_ARTILERY:
			return "a";
		default:
			return "error: wrong card type";
		}
		
	}
	
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}
}
