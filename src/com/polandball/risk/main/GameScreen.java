package com.polandball.risk.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import com.polandball.risk.framework.ui.Context;
import com.polandball.risk.framework.ui.Screen;
import com.polandball.risk.main.model.Board;
import com.polandball.risk.main.model.Card;
import com.polandball.risk.main.model.Country;
import com.polandball.risk.main.model.Log;
import com.polandball.risk.main.model.Player;

public class GameScreen extends Screen{
	
	private Board board;
	private GraphicManager gm;
	
	private Canvas canvas;
	private double originalWidth = 1630;
	private double originalHeight = 1080;
	
	private Text tTurn;
	
	private Text tPlayer;
	private Text tTerritories;
	private Text tArmies;
	private Text tAvailable;
	private Text tCards;
	
	private TextArea taLog;
	private TextField tfConsole;
	
	private Button bRollDice;
	private Button bAttackTransfer;
	private Button bReinforce;
	private Button bEndTurn;
	private Button bExchange;
	
	private Country countrySender, countryReceiver;
	
	private boolean ACTION_ATTACK = false;
	
	public GameScreen(Context context) {
		super(context);

		board = (Board) context.getBoard();
		run();
		getStage().setMaximized(true);
	}

	@Override
	public Scene print() {
		
		BorderPane root = new BorderPane();
		
		//canvas init
		double realWidth = this.getWidth() * 0.8;
		double realHeight = this.getHeight(); //height of the row
		canvas = new Canvas(realWidth, realHeight);
		GraphicsContext gc = (GraphicsContext) canvas.getGraphicsContext2D();
		gm = new GraphicManager(gc, canvas.getWidth(), canvas.getHeight(), originalWidth, originalHeight);
		
		root.setTop(getTopMenu());
		root.setCenter(canvas);
	    root.setRight(getPanel());
		
		//Resize listeners
		Scene scene = new Scene(root);
		scene.widthProperty().addListener((oV, oldV, newV) -> resizeWidth(oV, oldV, newV));
		scene.heightProperty().addListener((oV, oldV, newV) -> resizeHeight(oV, oldV, newV));
		
		return scene;
	}
	
	/**
	 * FIXME canvas flows out of the window
	 * resize canvas to fit the container
	 * @param realWidth
	 * @param realHeight
	 */
	private void resizeCanvas(double realWidth, double realHeight){
		
		//scale canvas
		double scale = 1;
		double w = realWidth, h = realHeight;
		
		scale = w / originalWidth;
		h = originalHeight * scale;
		
		if(h > realHeight){
			h = realHeight;
			w = h / originalHeight * realWidth;
		}
		
		//update GraphicsManager dimensions
		gm.setRealDimension(w, h);
		printBoard();
	}
	
	private void resizeWidth(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth){
		resizeCanvas(newSceneWidth.doubleValue()  * 0.8, getHeight());
	}
	
	private void resizeHeight(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight){
		resizeCanvas(getWidth() * 0.8, newSceneHeight.doubleValue());
	}
	
	private void printBoard(){
		gm.getContext().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Board board = (Board) context.getBoard();
		board.draw(gm);
		
		ArrayList<Country> countries = (ArrayList<Country>) board.getCountries();
		for(Country c : countries){
			c.draw(gm);
			c.drawText(gm);
		}
	}
	
	private void printPlayersStats(){
		
		tTurn.setText("Turn: " + board.getTurn());
		
		Player player = board.getCurrentPlayer();
		
		tPlayer.setText("Player: " + player.getName());
		tTerritories.setText("Territories: " + player.countriesCount());
		tArmies.setText("Armies: " + player.getTroops());
		tAvailable.setText("Available: " + player.getAvailableTroops());
		tCards.setText("Cards: " + player.getCardsShorts());
		
	}
	
	private void printConsoleLogs(){
		
		Log log = (Log) context.getBoard().getLog();
		LinkedList<String> logs = log.getLogs();
		
		StringBuffer sbLog = new StringBuffer();
		for(String l : logs){
			sbLog.append(l).append("\r\n");
		}
		
		taLog.setText(sbLog.toString());
	}
	
	/**
	 * Invoke on country click; highlights adjacent countries
	 */
	private void colorAdjacent(){
		//use current clicked country
	}
	
	/**
	 * Invoke on country click; highlight border of currently clicked country
	 */
	private void colorCountry(){
		
	}
	
	/**
	 * Referesh view
	 */
	private void update(){
		printBoard();
		printPlayersStats();
		printConsoleLogs();
		colorAdjacent();
		colorCountry();
	}
	
	/**
	 * TODO
	 * store currently clicked country
	 * add action buttons
	 * add actions
	 */
	private void onClick(MouseEvent e) {
		double x = gm.descaleX(e.getX());
		double y = gm.descaleY(e.getY());
		
		try{
			Country c = findCountry(x, y);


			//if country sender set and FLAG WAR set countryReceiver
			if(ACTION_ATTACK){
				countryReceiver = c;
				
				if(countryReceiver.getPlayer() == board.getCurrentPlayer() && countrySender.getPlayer() == board.getCurrentPlayer()){
					board.transfer(countrySender, countryReceiver, tfConsole.getText());
				}else{
					board.attack(countrySender, countryReceiver);
				}
				
				ACTION_ATTACK = false;
				
			}else if(board.checkState(Board.STATE_CHOOSE_COUNTRY)){
				countrySender = c;
				board.reinforce(countrySender, 1);
				
			}else{
				countrySender = c;
				board.getLog().addLog("You have selected " + c.getName());
			}
			
						
			
		}catch(NullPointerException err){
			//err.printStackTrace();
		}finally{
			update();
		}
	}
	
	
	/**
	 * Catch moving mouse
	 * @param e
	 */
	private void onHover(MouseEvent e){
		
		//TODO
		//check if country is adjacent to current
		//show possible moves as floating image
		
		
		
	}
	
	private void onEnter(KeyEvent ke){
		
		//check for Enter
		if(ke.getCode().equals(KeyCode.ENTER)){
			String command = tfConsole.getText();
			
			//TODO handle log commands
		}
		
	}
	
	private Country findCountry(double x, double y){
		ArrayList<Country> countries = (ArrayList<Country>) context.getBoard().getCountries();
		
		//Check if mouse inside a country contour

		for(Country c : countries){
			if(c.contains(x, y)){
				return c;
			}
		}
		
		return null;
	}
	
	@Override
	public void control() {
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onClick(e));
		canvas.addEventHandler(MouseEvent.MOUSE_MOVED, e -> onHover(e));
		
		bRollDice.setOnAction(e -> {
			if(board.isAllowed(Board.ALLOWED_THROW_DICE)){
				board.throwDice();
				board.endTurn();
				update();
			}
		});
		bAttackTransfer.setOnAction(e -> {
			
			if(countrySender != null && board.isAllowed(Board.ALLOWED_ATTACK)){
				ACTION_ATTACK = true;
				board.getLog().addLog("Select an adjacent country you want to attack/transfer to");
			}
			
			update();
		});
		bReinforce.setOnAction(e -> {
			if (board.checkState(Board.STATE_ALLOCATE_ARMIES)){
				board.reinforce(countrySender, 3);
			}else{
				board.reinforce(countrySender, 1);
			}
			update();
		});
		bEndTurn.setOnAction(e -> {
			if (!board.checkState(Board.STATE_SET_ORDER)){
			countryReceiver = countrySender = null;
			board.getLog().addLog("Player: " + board.getCurrentPlayer().getName() + " has ended his turn");
			board.endTurn();
			update();
			}
		});
		bExchange.setOnAction(e -> {
			String cards = tfConsole.getText();
			board.exchangeCards(cards);
			update();
		});
		tfConsole.setOnKeyPressed(e -> onEnter(e));

		board.getLog().addLog("Welcome to RISK!");
		board.getLog().addLog("Roll the dice to see who's going first!");
		update();
	}
	
	private Pane getPanel(){
		//Main Grid
		VBox vbPanel = new VBox();
	    
	    VBox vbButtons = new VBox();
	    
	    bAttackTransfer = new Button("Attack/Transfer");
	    bReinforce = new Button("Reinforce");
	    bRollDice = new Button("Roll Dice");
	    bExchange = new Button("Exchange");
	    bEndTurn = new Button("End Turn");
	    
	    vbButtons.getChildren().addAll(bAttackTransfer, bReinforce, bRollDice, bExchange, bEndTurn);
	    
	    //Players
	  	VBox vbPlayer = new VBox();
	  	
	  	tTurn = new Text();
	  	tPlayer = new Text();
	  	tTerritories = new Text();
	  	tArmies = new Text();
	  	tAvailable = new Text();
	  	tCards = new Text();
	  	
	  	vbPlayer.getChildren().addAll(tPlayer, tTerritories, tArmies, tAvailable, tCards);
	    
	  	VBox vbConsole = new VBox();
	  	taLog = new TextArea();
	  	taLog.setEditable(false);
	  	tfConsole = new TextField();
	  
		vbConsole.getChildren().addAll(taLog, tfConsole);
	    
		vbPanel.getChildren().addAll(new Text("Hello"), vbPlayer, vbButtons, vbConsole);
	    
	    return vbPanel;
	}
	
	private MenuBar getTopMenu(){
		//MenuBar
	    MenuBar menuBar = new MenuBar();
	    //EventHandler<ActionEvent> action = changeTabPlacement();
	    
	    //Game
	    Menu Game = new Menu("Game");
	    
	    MenuItem Stats = new MenuItem("Stats");
	    Game.getItems().add(Stats);

	    MenuItem Players = new MenuItem("Players");
	    Game.getItems().add(Players);
	    
	    //Settings
	    Menu Settings = new Menu("Settings");
	    
	    MenuItem Sound = new MenuItem("Sound");
	    Settings.getItems().add(Sound);

	    MenuItem Video = new MenuItem("Video");
	    Settings.getItems().add(Video);
	    
	    //Help
	    Menu Help = new Menu("Help");
	    
	    MenuItem Rules = new MenuItem("Rules");
	    Help.getItems().add(Rules);
	    
	    menuBar.getMenus().add(Game);
	    menuBar.getMenus().add(Settings);
	    menuBar.getMenus().add(Help);
	    
	    return menuBar;
	}
	
}