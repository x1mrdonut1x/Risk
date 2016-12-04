package com.polandball.risk.framework.ui.graphic;

public class Text extends Drawable{
	
	private String value;
	private double x, y;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}
	
}
