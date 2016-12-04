package com.polandball.risk.main.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.polandball.risk.framework.model.AbstractCard;
import com.polandball.risk.framework.model.AbstractCountry;
import com.polandball.risk.framework.model.AbstractPlayer;

public class Player extends AbstractPlayer {

	public Player(String name, Color c, boolean bot, int troops){
		super(name, c, bot, troops);
	}
	
	@Override
	public void addCountry(AbstractCountry country) {
		countries.put(country.getId(),country);
		country.setPlayer(this);
	}
	
	public void removeCountry(AbstractCountry country) {
		countries.remove(country.getId());
	}

	@Override
	public AbstractCountry getCountry(String name) {
		return countries.get(name);
	}

	@Override
	public HashMap<String, AbstractCountry> getCountries() {
		return countries;
	}
	
	public int countriesCount() {
		return this.countries.size();
	}

	@Override
	public void addTroops(AbstractCountry country, int number) {
		if (availableTroops > 0){
			country.addTroops(number);
			availableTroops -= number;
		}
	}

	@Override
	public void removeTroops(AbstractCountry country, int number) {
		//if (country.getTroops() >= 0)
			country.removeTroops(number);
	}

	@Override
	public int getTroops() {
		int troops = 0;
		
		for (AbstractCountry c : countries.values()){
			troops += c.getTroops();
		}
		
		return troops;
	}

	@Override
	public LinkedList<Integer> getDiceValues() {
		return this.dice;
	}

	@Override
	public void addDiceValue(int value) {
		this.dice.add(value);
	}

	@Override
	public int getDiceValue(int turn) {
		return this.dice.get(turn - 1);
	}

	@Override
	public int getLastDiceValue() {
		return this.dice.getLast();
	}

	@Override
	public void addCard(AbstractCard card) {
		this.cards.add(card);
	}

	@Override
	public Card drawCard(int type) {
		
		for(AbstractCard c : cards){
			if(c.getType() == type){
				cards.remove(c);
				return (Card)c;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean drawn() {
		return this.DRAWN;
	}
	
	public void setDrawn(boolean check) {
		this.DRAWN = check;
	}

	/**
	 * Returns how many cards of a type player has
	 * @param type
	 * @return
	 */
	@Override
	public int hasCard(int type) {
		int count = 0;
		for(AbstractCard c : cards){
			if(c.getType() == type){
				count++;
			}
		}
		
		return count;
	}

	@Override
	public String getCardsShorts() {
		
		StringBuffer sb = new StringBuffer();
		
		for(AbstractCard c : cards){
			sb.append(c.getShort());
		}
		
		return sb.toString();
	}

}
