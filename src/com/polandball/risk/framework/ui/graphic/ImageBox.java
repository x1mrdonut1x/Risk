package com.polandball.risk.framework.ui.graphic;

import javafx.scene.image.Image;

public class ImageBox {
	
	private Image image;
	private double x, y;
	
	public ImageBox(String filename, int x, int y) {
		image = new Image(getPath(filename));
		this.x = x;
		this.y = y;
	}
	
	private String getPath(String name){
		return "file:res" + name;
	}

	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getEndX(){
		return x + image.getWidth();
	}
	
	public double getEndY(){
		return y + image.getHeight();
	}
	
	//Checks if x, y are in circle's radius
	public boolean contains(double x, double y){
		
		boolean result = false;
		if(x > this.x && x < this.getEndX() && y > this.y && y < this.getEndY()) result = true;
		
		return result;
	}
	
}