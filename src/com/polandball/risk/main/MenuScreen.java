package com.polandball.risk.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.polandball.risk.framework.ui.Context;
import com.polandball.risk.framework.ui.Screen;

public class MenuScreen extends Screen{

	
	public MenuScreen(Context stage) {
		super(stage);
		
		run();
	}

	@Override
	public Scene print() {
		
		BorderPane root = new BorderPane();
		VBox menu = new VBox();
		
		Button bPlay = new Button("Play");
		bPlay.setOnAction(e -> aePlay(e));
		
		Button bSettings = new Button("Settings");
		
		Button bRules = new Button("Rules");
		
		Button bCredits = new Button("Credits");
		
		menu.getChildren().addAll(bPlay, bSettings, bRules, bCredits);
		root.setCenter(menu);
		
		return new Scene(root, 300, 500);
	}
	
	private void aePlay(ActionEvent event) {
    	new InitScreen(context);
    }
	
	@Override
	public void control() {
		// TODO Auto-generated method stub
		
	}

}
