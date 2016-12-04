package com.polandball.risk.framework.ui.graphic;
public class Circle extends Drawable{
	
	private double r;
	private double x, y;
	
	public Circle(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Circle(double x, double y, double r){
		this(x, y);
		this.r = r;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	
}