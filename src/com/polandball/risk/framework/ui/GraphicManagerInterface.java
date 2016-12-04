package com.polandball.risk.framework.ui;

import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;

import com.polandball.risk.framework.ui.graphic.Circle;
import com.polandball.risk.framework.ui.graphic.ImageBox;
import com.polandball.risk.framework.ui.graphic.Text;

public interface GraphicManagerInterface{
		
	public void draw(ImageBox image);
	public void draw(Rectangle rect);
	public void draw(Circle circle);
	public void draw(Text text);
	public GraphicsContext getContext();
	
	public void setRealDimension(double w, double h);
	public void setOriginalDimension(double w, double h);
	public double scaleX(double x);
	public double scaleY(double y);
	public double descaleX(double x);
	public double descaleY(double y);
}
