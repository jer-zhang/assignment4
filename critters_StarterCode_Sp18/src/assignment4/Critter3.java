package assignment4;
/* CRITTERS Critter3.java
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

public class Critter3 extends Critter {
	
	/**
	 * Critter3 will reproduce if its energy is greater than 100. Critter3 will either produce a Critter3
	 * or a Critter1. Then it has a 20% chance of moving on its time step.
	 */
	@Override
	public void doTimeStep() {
		if (getEnergy() > 100) {
			int random = getRandomInt(100);
			if (random < getEnergy()%100) {
				Critter3 child = new Critter3();
				reproduce(child, getRandomInt(8));
			} else {
				Critter1 child = new Critter1();
				reproduce(child, getRandomInt(8));
			}
		}
		
		if (getRandomInt(100) < 20) {
			if (getRandomInt(100) < 20) {
				run(getRandomInt(8));
			} else {
				walk(getRandomInt(8));
			}
		}
	}
	
	/**
	 * Critter3 only has a 20% chance of fighting Critter1's. It will fight more if its energy is high.
	 * Otherwise it will just surrender.
	 */
	@Override
	public boolean fight(String opponent) {
		if (opponent.equals("1")) {
			if (getRandomInt(100) < 20) {
				return true;
			} else {
				return false;
			}
		} else if (getRandomInt(100) <= getEnergy()) {
			return true;
		}
		return false;
	}
	
	/**
	 * "3" represents Critter3 on the map.
	 */
	public String toString() {
		return "3";
	}
}
