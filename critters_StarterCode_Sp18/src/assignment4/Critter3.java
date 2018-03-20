package assignment4;

public class Critter3 extends Critter {

	@Override
	public void doTimeStep() {
		if (getEnergy() > 100) {
			if (getRandomInt(100) < getEnergy()-100) {
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
	
	public String toString() {
		return "3";
	}
}
