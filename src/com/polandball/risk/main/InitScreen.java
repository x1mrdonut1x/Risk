/*
 * 
 * Screen to input players names and assign territories
 * 
 */
package com.polandball.risk.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import com.polandball.risk.framework.ui.Context;
import com.polandball.risk.framework.ui.Screen;
import com.polandball.risk.main.model.Board;
import com.polandball.risk.main.model.Country;
import com.polandball.risk.main.model.Player;

public class InitScreen extends Screen {

	private TextField tfName;
	private Label lName;
	private int i = 0;

	public InitScreen(Context stage) {
		super(stage);

		run();
	}

	@Override
	public Scene print() {

		VBox box = new VBox();
		tfName = new TextField();
		lName = new Label("Enter name for player #" + (i + 1) + ": ");
		Button addPlayer = new Button("Add Player");

		addPlayer.setOnAction(e -> onclick(e));

		box.getChildren().addAll(lName, tfName, addPlayer);
		return new Scene(box, 300, 500);
	}
	
	private void onclick(ActionEvent e) {

		if (tfName.getText().trim().isEmpty()) {
			lName.setText("Field cannot be empty!");
		} else {
			
			lName.setText("Enter name for player #" + (i + 2) + ": ");
			context.getBoard().addPlayer(new Player(tfName.getText(), Player.colors[i],false, 9));
			tfName.setText("");

			if (i == 1) {
				// Create bots
				context.getBoard().addPlayer(new Player("AI1", Player.colors[i + 1], true, 6));
				context.getBoard().addPlayer(new Player("AI2", Player.colors[i + 2], true, 6));
				context.getBoard().addPlayer(new Player("AI3", Player.colors[i + 3], true, 6));
				context.getBoard().addPlayer(new Player("AI4", Player.colors[i + 4], true, 6));

				assignCountries();
				new GameScreen(context);
			}
			
			i++;
		}
	}

	// Assigning countries for the initialization stage
	public void assignCountries() {
		/*
		Board board = (Board) context.getBoard();
		List<Country> countries = (List<Country>) board.getCountries();
		
		Collections.shuffle(countries);
		
		ArrayList<Player> players = (ArrayList<Player>) board.getPlayers();
		int current_country = 0;
		final int ai_country_number = 6;
		final int country_number = 9;
		for(Player player : players){
			if(player.isBot()){
				player.addCountries(countries.subList(current_country, current_country + ai_country_number));
				current_country += ai_country_number;
			}else{
				player.addCountries(countries.subList(current_country, current_country + country_number));
				current_country += country_number;
			}
		}*/
	}
	
	@Override
	public void control() {
		// TODO Auto-generated method stub

	}

}