package assignment4;
/* CRITTERS Critter4.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jerry Zhang
 * jz9954
 * 15465
 * Celine Lillie
 * Cml3665
 * 15460
 * Slip days used: 0
 * Spring 2018
 */

public class Critter4 extends Critter{
	
	private int turn;
	
	/**
	 * Critter4 constructor initializes the turn variable.
	 */
	public Critter4() {
		turn = 0;
	}
	
	/**
	 * Critter4 runs once every 4 turns, walks for all other turns. If it walks, there
	 * is a 10% chance that it will reproduce and make a new Critter4.
	 */
	public void doTimeStep() {
		turn++;
		turn = turn % 4;
		if (turn == 0) {
			run(getRandomInt(8));
		} else {
			walk(getRandomInt(8));
			if (getRandomInt(10) == 9) {
				Critter4 child = new Critter4();
				reproduce(child, getRandomInt(8));
			}
		}
	}
	
	/**
	 * Critter4 will not fight Critter1's and will try to run. It will fight everything else.
	 */
	public boolean fight(String opponent) {
		if (opponent == "1") {
			int rand = getRandomInt(8);
			if (look(rand, false)) {
				run(rand);
			}
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * "4" represents Critter4 on the map.
	 */
	public String toString() {
		return "4";
	}
}
