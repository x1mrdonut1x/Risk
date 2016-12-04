package com.polandball.risk.framework.ui;

import com.polandball.risk.framework.model.AbstractBoard;

import javafx.stage.Stage;

public class Context {
	
	private Stage stage;
	private AbstractBoard board;
	
	public Context(){
		
	}
	
	public Context(Stage stage, AbstractBoard board){
		this.stage = stage;
		this.board = board;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public AbstractBoard getBoard() {
		return board;
	}
	
	public void setBoard(AbstractBoard board) {
		this.board = board;
	}
	
}
