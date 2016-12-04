package com.polandball.risk.framework.model;

import com.polandball.risk.main.GraphicManager;

public abstract class Mappable extends Model{
	
	protected double x, y;
	
	public void setXY(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void draw(GraphicManager gm);
	public abstract boolean contains(double x, double y);
}
