package com.polandball.risk.main;

import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import com.polandball.risk.framework.ui.GraphicManagerInterface;
import com.polandball.risk.framework.ui.graphic.Circle;
import com.polandball.risk.framework.ui.graphic.ImageBox;
import com.polandball.risk.framework.ui.graphic.Text;

public class GraphicManager implements GraphicManagerInterface{
	
	private GraphicsContext gc;
	private double rW, rH;
	private double oW, oH;
	
	public GraphicManager(GraphicsContext gc){
		this.gc = gc;
	}
	
	public GraphicManager(GraphicsContext gc, double realWidth, double realHeight, double originalWidth, double originalHeight){
		this(gc);
		this.rW = realWidth;
		this.rH = realHeight;
		this.oW = originalWidth;
		this.oH = originalHeight;
	}
	
	/**
	 * Set fill and stroke color
	 * @param c
	 */
	public void setColor(Color c){
		gc.setFill(c);
		gc.setStroke(c);
	}
	
	/**
	 * Set stroke line width
	 * @param w
	 */
	public void setWidth(double w){
		gc.setLineWidth(w);
	}
	
	@Override
	public void draw(ImageBox imageBox) {
		Image img = imageBox.getImage();
		gc.drawImage(img, this.scaleX(imageBox.getX()), this.scaleY(imageBox.getY()), this.scaleX(img.getWidth()), this.scaleY(img.getHeight()));
	}

	@Override
	public void draw(Rectangle rect) {
		gc.fillRect(this.scaleX(rect.getX()), this.scaleY(rect.getY()), this.scaleX(rect.getWidth()), this.scaleY(rect.getHeight()));
	}

	
	/**
	 * Draws circle
	 * @deprecated:
	 * @note use only horizontal scale for radius to keep circular shape
	 */
	@Override
	public void draw(Circle circle) {
		double r = circle.getR();
		gc.strokeOval(this.scaleX(circle.getX() - r), this.scaleY(circle.getY() - r),  this.scaleX(r*2), this.scaleY(r*2));
	}

	@Override
	public void draw(Text text) {
		//TODO center display
		gc.fillText(text.getValue(), this.scaleX(text.getX()), this.scaleY(text.getY()));
	}
	
	public GraphicsContext getContext(){
		return gc;
	}

	@Override
	public void setRealDimension(double w, double h) {
		this.rW = w;
		this.rH = h;
	}

	@Override
	public void setOriginalDimension(double w, double h) {
		this.oW = w;
		this.oH = h;
	}

	@Override
	public double scaleX(double x) {
		return x * rW / oW;
	}

	@Override
	public double scaleY(double y) {
		return y * rH / oH;
	}

	/**
	 * Returns value in original dimensions
	 */
	@Override
	public double descaleX(double x) {
		return x * oW / rW;
	}
	
	/**
	 * Returns value in original dimensions
	 */
	@Override
	public double descaleY(double y) {
		return y * oH / rH;
	}
	
	
	
}