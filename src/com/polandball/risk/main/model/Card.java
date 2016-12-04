package com.polandball.risk.main.model;

import com.polandball.risk.framework.model.AbstractCard;
import com.polandball.risk.framework.ui.graphic.ImageBox;

public class Card extends AbstractCard{
	
	public Card(int type, String name){
		this.setName(name);
		this.setType(type);
	}
	
	public Card(ImageBox icon, int type, String name, int value){
		this(type, name);
		this.setIcon(icon);
		this.setValue(value);
		
	}
}
