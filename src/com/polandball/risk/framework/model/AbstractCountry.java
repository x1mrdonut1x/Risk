package com.polandball.risk.framework.model;

import java.util.HashMap;

import com.polandball.risk.framework.ui.graphic.Circle;
import com.polandball.risk.framework.ui.graphic.ImageBox;

import javafx.scene.paint.Color;

public abstract class AbstractCountry extends Mappable {
		
	protected HashMap<String, AbstractCountry> adjacent;
	protected int troops;
	
	protected AbstractPlayer player;
	protected AbstractCard card;
	protected ImageBox frame;
	protected Circle circle;
	
	public AbstractCountry(String name){
		setName(name);
		adjacent = new HashMap<String, AbstractCountry>();
	}
	
	public AbstractCountry(String name, ImageBox ib, Circle c){
		this(name);
		
		frame = ib;
		c.setR(20);
		circle = c;
	}
	
	public ImageBox getFrame(){
		return this.frame;
	}
	
	public Circle getCircle(){
		return this.circle;
	}
	
	public abstract void addTroops(int number);
	public abstract void removeTroops(int number);
	public abstract int getTroops();
	
	public boolean isAdjacent(AbstractCountry country){
		return isAdjacent(country.getId());
	}
	public abstract boolean isAdjacent(String countryId);
	public abstract boolean isOccupied();
	public abstract boolean isOccupied(AbstractPlayer player);
	
	public abstract AbstractPlayer getPlayer();
	public abstract void setPlayer(AbstractPlayer player);
	public abstract void addAdjacent(AbstractCountry... country);
}
