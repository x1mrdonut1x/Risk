package com.polandball.risk.main.model;

import com.polandball.risk.framework.model.AbstractCard;
import com.polandball.risk.framework.model.AbstractCountry;
import com.polandball.risk.framework.model.AbstractPlayer;
import com.polandball.risk.framework.ui.graphic.Circle;
import com.polandball.risk.framework.ui.graphic.ImageBox;
import com.polandball.risk.main.GraphicManager;
import com.polandball.risk.framework.ui.graphic.Text;

import javafx.scene.paint.Color;


public class Country extends AbstractCountry{

	int troops = 0;
	
	public Country(String name, ImageBox frame, Circle c){
		super(name, frame, c);
		
	}

	@Override
	public void addAdjacent(AbstractCountry... country) {
		
		for(AbstractCountry c : country){
			adjacent.put(c.getId(), c);
		}
	
	}

	@Override
	public void addTroops(int number) {
		troops += number;
	}

	@Override
	public void removeTroops(int number) {
		if (troops - number >= 0)
			troops -= number;
	}

	@Override
	public int getTroops() {
		return troops;
	}

	@Override
	public boolean isAdjacent(String country) {
		return adjacent.containsKey(country);
	}

	@Override
	public boolean isOccupied() {
		return getPlayer() != null;
	}
	
	@Override
	public boolean isOccupied(AbstractPlayer player) {
		return player.getId().equals(this.player.getId());
	}
	
	@Override
	public Player getPlayer() {
		return (Player) this.player;
	}

	@Override
	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}


	@Override
	public void draw(GraphicManager gm) {
		
		if(!isOccupied()){
			gm.setColor(Color.GRAY);
		}
		else{
			gm.setColor(getPlayer().getColor());
		}
			
		
		gm.setWidth(5);
		gm.draw(this.getCircle());
	}
	
	public void drawText(GraphicManager gm){
		Text troops = new Text();
		
		troops.setValue(Integer.toString(this.troops));
		troops.setX(this.getCircle().getX());
		troops.setY(this.getCircle().getY() );
		
		gm.draw(troops);
	}
	
	public boolean contains(double x, double y){
		return Math.pow((x - circle.getX()),2) + Math.pow((y - circle.getY()),2) < Math.pow(circle.getR(),2);
	}


}
