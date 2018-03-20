package assignment4;

public class Critter4 extends Critter{
	
	public Critter4() {
		
	}
	
	private int turn;
	
	public void doTimeStep() {
		turn += turn % 4;
		if (turn == 0) {
			run(getRandomInt(8));
		} else {
			walk(getRandomInt(8));
			if (getRandomInt(10) > 9) {
				Critter4 child = new Critter4();
				reproduce(child, getRandomInt(8));
			}
		}
		
	}
	
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
	
	public String toString() {
		return "4";
	}
}
