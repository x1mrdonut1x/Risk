import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Team Polandball

public class Polandball implements Bot {

	private BoardAPI board;
	private PlayerAPI player;

	private PlayerAI ai;

	Polandball(BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;
		player = inPlayer;

		ai = new PlayerAI();

		return;
	}

	public String getName() {

		String command = "";
		command = "Polandball";
		return (command);
	}

	public String getReinforcement() {
		String command = "";
		command = ai.reinforce();
		command = command.replaceAll("\\s", "");

		command += " 1";
		return (command);
	}

	public String getPlacement(int forPlayer) {
		String command = "";
		command = ai.reinforceNeutral(forPlayer);
		command = command.replaceAll("\\s", "");
		return (command);
	}

	public String getCardExchange() {
		String command = "";
		command = ai.cardExchange();
		return (command);
	}

	public String getBattle() {
		String command = "";
		command = ai.attack();
		return (command);
	}

	public String getDefence(int countryId) {
		String command = "";
		command = ai.defend(countryId);
		return (command);
	}

	public String getMoveIn(int attackCountryId) {
		String command = "";

		command = ai.moveIn(attackCountryId);
		return (command);
	}

	public String getFortify() {
		String command = "";
		command = ai.transfer();
		return (command);
	}

	class PlayerAI {

		public List<List<Integer>> countries;

		public PlayerAI() {

		}

		/**
		 * retrieves countries of player (grouped if connected)
		 * 
		 * @return
		 */
		private List<Integer> getMyCountries() {

			List<Integer> countries = new ArrayList<Integer>();

			for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
				if (board.getOccupier(i) == player.getId()) {
					countries.add(i);
				}
			}
			
			Collections.sort(countries, (a, b) -> {
				return Integer.compare(countPotentialAttackers(a), countPotentialAttackers(b));
			});
			
			return countries;
		}
		
		/**
		 * Retrieves countries of player
		 * @param player
		 * @return
		 */
		private List<Integer> getCountries(int player){
			List<Integer> countries = new ArrayList<Integer>();

			for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
				if (board.getOccupier(i) == player) {
					countries.add(i);
				}
			}
			
			return countries;
		}
		
		/**
		 * Counts number of attackers on our country
		 * 
		 * @param country
		 * @return
		 */
		private int countPotentialAttackers(int country) {

			int counter = 0;

			for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
				if ((board.getOccupier(i) != player.getId()) && (board.isAdjacent(country, i))) {
					counter++;
				}
			}

			return counter;
		}
		
		/**
		 * Counts how many troops in total can attack country
		 * @param country
		 * @return
		 */
		private int countStrengthOfAttackers(int country){
			
			int counter = 0;

			for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
				if ((board.getOccupier(i) == getEnemyId()) && (board.isAdjacent(country, i))) {
					counter += board.getNumUnits(i);
				}
			}

			return counter;
			
		}

		/**
		 * Counts how many of our countries are attacking given country
		 * 
		 * @param country
		 * @return
		 */
		private int countAttackRoutes(int country) {

			int counter = 0;

			for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
				if ((board.getOccupier(i) == player.getId()) && (board.isAdjacent(country, i))) {
					counter++;
				}
			}

			return counter;
		}

		/**
		 * Checks if country is worth attacking
		 * 
		 * @return
		 */
		private boolean isWinnable(int a, int d) {
			
			int attackers = board.getNumUnits(a) - 1;
			int defenders = board.getNumUnits(d);
			
			if(attackers < 2) return false;
			
			final double D_D1A1 = 0.417; // lose defender when 1 defender and 1
											// attacker
			final double D_D1A2 = 0.579;
			final double D_D1A3 = 0.66;
			final double A_D1A1 = 0.583;
			final double A_D1A2 = 0.421;
			final double A_D1A3 = 0.34;

			final double D_D2A1 = 0.255; // lose defender when 2 defender and 1
											// attacker
			final double D_D2A2 = 0.228;
			final double D_D2A3 = 0.372;
			final double A_D2A1 = 0.745;
			final double A_D2A2 = 0.448;
			final double A_D2A3 = 0.292;

			while (attackers > 1 && defenders >= 1) {

				if (attackers >= 3) {
					if (defenders >= 2) {
						attackers -= A_D2A3;
						defenders -= D_D2A3;
					} else {
						attackers -= A_D1A3;
						defenders -= D_D1A3;
					}

				}else if(attackers >= 2){
					
					if (defenders >= 2) {
						attackers -= A_D2A2;
						defenders -= D_D2A2;
					} else {
						attackers -= A_D1A2;
						defenders -= D_D1A2;
					}
					
				}else if(attackers >= 1){
					
					if (defenders >= 2) {
						attackers -= A_D2A1;
						defenders -= D_D2A1;
					} else {
						attackers -= A_D1A1;
						defenders -= D_D1A1;
					}
					
				}

			}

			return attackers > defenders;
		}
		
		/**
		 * Get enemy's id
		 */
		private int getEnemyId(){
			return (1 + player.getId()) % 2;
		}
		
		/**
		 * Get a list of adjacent countries that doesnt belong to player
		 * 
		 * @param country
		 * @return
		 */
		private List<Integer> getAdjacentAttackers(int country) {

			List<Integer> adjacents = new ArrayList<Integer>();

			for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
				if ((board.getOccupier(i) != player.getId()) && (board.isAdjacent(country, i))) {
					adjacents.add(i);
				}
			}

			return adjacents;
		}
		
		/**
		 * Retrieves trimmed name of country
		 */
		private String getCountryCode(int country){
			return GameData.COUNTRY_NAMES[country].replaceAll("\\s", "");
		}
		
		/**
		 * Finds which country should be attack target
		 * Checks adjacent countries
		 * checks probability of winning
		 * checks number of attacking troops
		 * 
		 * @return
		 */
		private int getAttackTarget(int country) {
			
			int minEnemyForce = 99;
			int minAttackers = 6;
			int minConnections = 6;

			int target = -1;

			for (int adjacent : getAdjacentAttackers(country)) {
				
				if(isWinnable(country, adjacent)){
					
					//check number of troops that can attack country
					int enemyForce = countStrengthOfAttackers(adjacent);
					// check number of connections with other foreign countries
					int attackers = countPotentialAttackers(adjacent); 
					// check number of connections with us
					int connections = countAttackRoutes(adjacent);
					if(enemyForce < minEnemyForce){
						minEnemyForce = enemyForce;
						minAttackers = attackers;
						minConnections = connections;
						target = adjacent;
					}else if(enemyForce == minEnemyForce){
						if ((attackers == minAttackers && connections < minConnections) || (attackers < minAttackers)) {
							minEnemyForce = enemyForce;
							minAttackers = attackers;
							minConnections = connections;
							target = adjacent;
						}
					}
					
				}
				
			}

			return target;

		}

		/**
		 * Finds country in group that is threatened (most connections/troops to potential attackers) the most
		 * 
		 * @return
		 */
		private int getThreatened(List<Integer> countries) {
			
			int country = -1;
			int maxEnemyTroops = 0;
			for(int c : countries){
				
				if(board.getNumUnits(c) < countStrengthOfAttackers(c)){
					int enemyTroops = countStrengthOfAttackers(c);
					if(enemyTroops > maxEnemyTroops){
						maxEnemyTroops = enemyTroops;
						country = c;
					}
				}
				
			}
			
			if(country >= 0) return country;
			
			for(int c : countries){
				
				if(board.getNumUnits(c) < countStrengthOfAttackers(c)){
					int enemyTroops = countPotentialAttackers(c);
					if(enemyTroops > maxEnemyTroops){
						maxEnemyTroops = enemyTroops;
						country = c;
					}
				}
				
			}
			
			if(country >= 0) return country;
			
			return countries.get((int) (Math.random() * countries.size()));
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/**
		 * STAGE 1 Reinforce the country threatened from the main group
		 */
		public String reinforce() {
						
			List<Integer> countries = getMyCountries();
			return getCountryCode(getThreatened(countries));
		}


		/**
		 * Reinforce neutral
		 * 
		 * @return
		 */
		public String reinforceNeutral(int forPlayer) {
			
			List<Integer> countries = getCountries(forPlayer);
			return getCountryCode(getThreatened(countries));
		}
		
		/**
		 * STAGE 2 Attack countries that will give you new territory (if
		 * possible on the same continent) but will keep you border as limited
		 * as possible
		 */
		public String attack() {
			
			int target = -1;
			int country = -1;
			
			List<Integer> countries = getMyCountries();
			for(int c : countries){
				
				target = getAttackTarget(c);
				if(target >= 0){
					country = c;
					break;
				}
				
			}
			
			if(target < 0){
				return "skip";
			}else{
				return getCountryCode(country) + " " + getCountryCode(target) + " " +  String.valueOf(board.getNumUnits(country) > 3 ? 3 : 2);
			}

			// find target to attack
			//getAttackTarget();

			// attack only if winnable
			//attack as long as winnable
		}

		/**
		 * Decide how many troops to defend
		 * 
		 * @param countryId
		 * @return
		 */
		public String defend(int countryId) {

			// set maximum number of troops to defend
			//use 2 defenders if you can
						
			return String.valueOf(board.getNumUnits(countryId) > 1 ? 2 : 1);
		}

		/**
		 * STAGE 3 Transfer troops from countries not threatened to borders
		 */
		public String transfer() {
			
			int from = -1, to = -1;
			
			int maxTroops = 0;
			
			//find safe
			for(int country : getMyCountries()){
				
				if(countPotentialAttackers(country) == 0 && board.getNumUnits(country) > 1){
					
					int troops = board.getNumUnits(country);
					if(troops > maxTroops){
						maxTroops = troops;
						from = country;
					}
					
				}
				
			}
			
			if(from < 0){
				return "skip";
			}
			
			int maxEnemyForce = 0;
			for(int country : getMyCountries()){
				if(board.isConnected(from, country)){
					
					int enemyForce = countPotentialAttackers(country);
					if(enemyForce > maxEnemyForce){
						maxEnemyForce = enemyForce;
						to = country;
					}
					
				}
				
			}
			

			// move troops from safe countries to border
			//count all troops and count ratio of threats
			//find if you can transfer from safe to threatened
			
			return getCountryCode(from) + " " + getCountryCode(to) + " " + String.valueOf(board.getNumUnits(from) - 1);
		}


		/**
		 * Move units after attack
		 * 
		 * @param attackCountryId
		 * @return
		 */
		public String moveIn(int attackCountryId) {
	
			return String.valueOf(board.getNumUnits(attackCountryId) - 1);
		}

		/**
		 * Exchange cards
		 * 
		 * @return
		 */
		public String cardExchange() {
			
			ArrayList<Card> cards = player.getCards();
			if(cards.size() < 5) return "skip";
			
			String[] ins = {"i", "c", "a", "w"};
			
			// Exchange as many cards as you can
			for(int[] set : Deck.SETS){
				
				if(player.isCardsAvailable(set)){
					
					return ins[set[0]] + " " + ins[set[1]] + " " + ins[set[2]];
					
				}
				
			}

			return "skip";
		}
	}

}
