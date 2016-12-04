package com.polandball.risk.framework.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;

public abstract class AbstractPlayer extends Model {
	
	protected HashMap<String, AbstractCountry> countries;
	protected Color color;
	protected int availableTroops;
	protected LinkedList<Integer> dice;
	protected ArrayList<AbstractCard> cards;
	
	public int BONUS_TROOPS = 0;
	protected boolean IS_BOT;
	
	protected boolean DRAWN = false;
	
	public static final Color[] colors = {Color.BLUE, Color.GREEN, Color.FLORALWHITE, Color.WHITESMOKE, Color.ANTIQUEWHITE, Color.NAVAJOWHITE, Color.GHOSTWHITE};
	
	public AbstractPlayer(String name, Color c, int troops){
		this.name = name;
		this.IS_BOT = false;
		this.color = c;
		this.availableTroops = troops;
		
		countries = new HashMap<String, AbstractCountry>();
		dice = new LinkedList<Integer>();
		cards = new ArrayList<AbstractCard>();
	}
	
	public boolean hasTroops(){
		return availableTroops != 0;
	}
	
	public int getAvailableTroops(){
		return availableTroops;
	}
	
	public void setAvailableTroops(int number){
		availableTroops = number;;
	}
	
	public void addAvailableTroops(int number){
		availableTroops += number;;
	}
	
	public AbstractPlayer(String name, Color c, boolean bot, int troops){
		this(name, c, troops);
		this.IS_BOT = bot;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public boolean isBot(){
		return IS_BOT;
	}
	
	public abstract void addCountry(AbstractCountry country);
	public abstract void removeCountry(AbstractCountry country);
	public abstract AbstractCountry getCountry(String name);
	public abstract HashMap<String, AbstractCountry> getCountries();
	public void addCountries(List<? extends AbstractCountry> countries){
		
		for(AbstractCountry c : countries){
			this.addCountry(c);
		}
	}
	
	public abstract void addTroops(AbstractCountry country, int number);
	public abstract void removeTroops(AbstractCountry country, int number);
	public abstract int getTroops(); //count from all countries
	
	public abstract LinkedList<Integer> getDiceValues();
	public abstract int getDiceValue(int turn);
	public abstract int getLastDiceValue();
	public abstract void addDiceValue(int value);
	
	public abstract AbstractCard drawCard(int type);
	public abstract void addCard(AbstractCard card);
	public abstract int hasCard(int type);
	public abstract String getCardsShorts();
	public abstract boolean drawn();
	
	public void  reset(){
		DRAWN = false;
	}
}
