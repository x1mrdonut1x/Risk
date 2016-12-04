package com.polandball.risk.framework.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.polandball.risk.framework.ui.graphic.ImageBox;
import com.polandball.risk.main.model.Log;
import com.polandball.risk.main.model.Player;

public abstract class AbstractBoard extends Mappable{
	
	public static final int STATE_SET_ORDER = 0;
	public static final int STATE_CHOOSE_COUNTRY = 1;
	public static final int STATE_ALLOCATE_ARMIES = 2;
	public static final int STATE_CONQUER = 3;
	public static final int STATE_SUMMARY = 4;
	public static final int STATE_PAUSE = 5;
	public static final int STATE_ANIMATION = 6;
	
	public static final int ALLOWED_THROW_DICE = 0x00000001;
	public static final int ALLOWED_CHOOSE_COUNTRY = 0x00000010;
	public static final int ALLOWED_REINFORCE = 0x00000100;
	public static final int ALLOWED_ATTACK = 0x00001000;
	public static final int ALLOWED_TRANSFER = 0x00010000;
	public static final int ALLOWED_ALL = 0x11111111;
	
	///
	
	protected ArrayList<AbstractCountry> countries;
	protected ArrayList<AbstractPlayer> players;
	protected LinkedList<AbstractCard> cards;
	protected AbstractLog logs;
	
	protected int currentTurn = 1;
	protected int currentState = STATE_SET_ORDER;
	protected int allowedAction = ALLOWED_THROW_DICE;
	protected int currentPlayer = 0;
	protected ImageBox frame;
	
	public AbstractBoard(){
		countries = new ArrayList<AbstractCountry>();
		players = new ArrayList<AbstractPlayer>();
		cards = new LinkedList<AbstractCard>();
		
		initLog();
		initCountries();
		initMap();
		initCards();
	}
	
	public void setFrame(ImageBox frame){
		this.frame = frame;
	}
	
	public ImageBox getFrame(){
		return frame;
	}
	
	public void setState(int state){
		this.currentState = state;
	}
	
	public int getState(){
		return this.currentState;
	}
	
	public boolean checkState(int state){
		return this.currentState == state;
	}
	
	public void setAllowedAction(int allowed){
		this.allowedAction = allowed;
	}
	
	public boolean isAllowed(int allowed){
		return ((this.allowedAction & allowed) == allowed) || (this.allowedAction == this.ALLOWED_ALL);
	}
	/**
	 * Initialization
	 */
	public abstract void initLog();
	public abstract void initCountries();
	public abstract void initCards();
	public abstract void initMap();
	
	/**
	 * Log
	 */
	public abstract AbstractLog getLog();
	public abstract void log(String log);
	
	/**
	 * Player
	 */
	public abstract void addPlayer(AbstractPlayer player);
	public abstract AbstractPlayer getPlayer(int index);
	public abstract AbstractPlayer getCurrentPlayer();
	public abstract AbstractPlayer getNextPlayer();
	public abstract ArrayList<? extends AbstractPlayer> getPlayers();
	public abstract void setPlayersInOrder();
	public boolean isLastPlayer(){
		return currentPlayer == 0;
	}
	
	/**
	 * Country
	 */
	public abstract void addCountry(AbstractCountry country);
	public abstract ArrayList<? extends AbstractCountry> getCountries();
	
	/**
	 * Card
	 */
	public abstract void addCard(AbstractCard card);
	public abstract AbstractCard drawCard();
	public abstract LinkedList<? extends AbstractCard> getCards();
	public abstract void shuffleCards();
	public abstract void exchangeCards(String cards);
	
	/**
	 * Actions
	 */
	public abstract int throwDice();
	public abstract void endTurn();
	public abstract int getTurn();
	public abstract void populate();
	public abstract void reinforce(AbstractCountry country, int troops);
	public abstract void attack(AbstractCountry from, AbstractCountry to);

	
	public abstract boolean isGameEnded();


}