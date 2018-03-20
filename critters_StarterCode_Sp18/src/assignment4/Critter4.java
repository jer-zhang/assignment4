package assignment4;

public class Critter4 extends Critter{
	
	public Critter4() {
		
	}
	
	private int turn;
	
	public void doTimeStep() {
		turn += turn % 4;
		if (turn == 0) {
			walk(getRandomInt(8));
		} else {
			run(getRandomInt(8));
		}
		
	}
	
	public boolean fight(String opponent) {
		if (opponent == "1") {
			return false;
		} else {
			return true;
		}
	}
	
	public String toString() {
		return "4";
	}
}
