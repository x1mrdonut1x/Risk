package com.polandball.risk.main;

import com.polandball.risk.framework.ui.Context;
import com.polandball.risk.main.model.Board;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setMaximized(true);
		Context context = new Context(primaryStage, new Board());
		new MenuScreen(context);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
