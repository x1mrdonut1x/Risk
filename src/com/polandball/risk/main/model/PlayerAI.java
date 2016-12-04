package com.polandball.risk.main.model;

import java.util.ArrayList;

import com.polandball.risk.framework.model.AbstractCountry;
import com.polandball.risk.framework.model.AbstractPlayer;

public class PlayerAI {
	
	
	/**
	 * AI choose country
	 * Chooses first not occupied country
	 * @param player
	 * @param arrayList
	 */
	public static void chooseCountry(AbstractPlayer player, ArrayList<? extends AbstractCountry> arrayList) {
		
		for(AbstractCountry country : arrayList){
			if(!country.isOccupied() && player.getAvailableTroops() > 0){
				player.addCountry(country);
				player.addTroops(country, 1);
				break;
			}
		}
	}
	
	/**
	 * AI allocate army
	 * Choose the least populated country to place troops
	 * @param player
	 */
	public static void allocateArmy(AbstractPlayer player) {
		
		AbstractCountry leastPopulated = null;
		int min = 9999;
		
		for(AbstractCountry country : player.getCountries().values()){
			player.addTroops(country, 1);
		}
		
	}

}
