package assignment4;

public class Critter1 extends Critter {
	
	public Critter1() {
	}
	
	@Override
	public void doTimeStep() {
		while (getEnergy() > Params.min_reproduce_energy) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		if (!opponent.equals("1")) {
			return true;
		} else {
			int dir = getRandomInt(8);
			if (look(dir, false)) {
				walk(dir);
			}
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "1";
	}
}
