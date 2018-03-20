package assignment4;

public class Critter2 extends Critter {
	
	private int dir;
	
	public Critter2() {
		
	}
	
	// Only walk horizontally or vertically (0, 2, 4, 6)
	public void doTimeStep() {
		dir = 2 * getRandomInt(4);
		walk(dir);
		int rand = getRandomInt(1000);
		if (rand > 200) {
			Critter2 child = new Critter2();
			reproduce(child, getRandomInt(8));
		}
		if (rand == 0) {
			Critter1 child = new Critter1();
			reproduce(child, getRandomInt(8));
		}
	}
	
	// If walking horizontally, fights, if going vertically, run.
	public boolean fight(String opponent) {
		if (dir == 0 || dir == 4) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "2";
	}
}
