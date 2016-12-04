package com.polandball.risk.framework.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class Screen{
	
	protected String title;
	protected Context context;
	protected Stage stage;
	
	public Screen(Context context){
		this.context = context;
		getStage().setTitle(getTitle());
	}
	
	public Screen(Context context, String title){
		this(context);
		getStage().setTitle(getTitle());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void run(){
		Stage stage = getStage();
		
		stage.setScene(print());
		stage.sizeToScene();
		stage.show();
		
		control();
	}
	
	public Stage getStage(){
		return context.getStage();
	}
	
	public Context getContext(){
		return context;
	}
	
	public Rectangle2D getSize(){
		return javafx.stage.Screen.getPrimary().getVisualBounds();
	}
	
	public double getWidth(){
		return getSize().getWidth();
	}
	
	public double getHeight(){
		return getSize().getHeight();
	}
	
	public abstract Scene print();
	public abstract void control();
}