package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Jerry Zhang
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * Celine Lillie
 * Cml3665
 * 15460
 * Slip days used: 0
 * Spring 2018
 */


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

/**
 * This class a base class for all the critters in our world
 * @author Jerry Zhang, Celine Lillie
 *
 */
public abstract class Critter {
	private static String myPackage;											// String of our package name
	private static List<Critter> babies = new java.util.ArrayList<Critter>();	// Baby Critter list
	private static Set<Critter> critSet = new HashSet<Critter>();				// Critter set
  
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];		// Gets package name
	}
	
	private static java.util.Random rand = new java.util.Random();
	
	/**
	 * This method generates a random integer between 0 and max
	 * @param max Max value for random
	 * @return Random integer
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	/**
	 * This method sets random number generator to a seed value
	 * @param new_seed A number to set seed
	 */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	/**
	 * This method returns a string of the specific critter
	 */
	public String toString() {
    	return " ";
	}
	
	private int energy = 0;		// Energy of critter
  
	/**
	 * Energy getter function
	 * @return energy value
	 */
	protected int getEnergy() {
    	return energy;
    }
	
	private int x_coord;		// X coord of critter
	private int y_coord;		// Y coord of critter
	private boolean moved;		// Boolean on whether the critter has moved in the current time step
	
	/**
	 * This method moves the critter one space
	 * @param direction A value from 0 to 7 indicating direction on grid
	 */
	protected final void walk(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();	// An array of coords to temporarily hold current coord of critter
		coords.add(x_coord);
		coords.add(y_coord);
		if (moved == false) {				// If the critter has not moved, move the critter. Updated points stored in array
	      	move(direction, coords);
	      	x_coord = coords.get(0);
	      	y_coord = coords.get(1);
		}
        energy -= Params.walk_energy_cost;	// Drain some walk energy
      	moved = true;
	}
	
	/**
	 * This method moves the critter two spaces
	 * @param direction A value from 0 to 7 indicating direction on grid
	 */
	protected final void run(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();	// An array of coords to temporarily hold current coord of critter
		coords.add(x_coord);
		coords.add(y_coord);
		if (moved == false) {				// If the critter has not moved, move the critter twice. Updated points stored in array
			move(direction, coords);
		  	move(direction, coords);
	      	x_coord = coords.get(0);
	      	y_coord = coords.get(1);
		}
      	energy -= Params.run_energy_cost; // Drain some run energy
        moved = true;
	}
	
	/**
	 * Checks if a critter is in the target location
	 * @param direction Integer 0 to 7
	 * @param isRunning false if walking, true if running
	 * @return true if location is empty, false otherwise
	 */
	protected boolean look(int direction, boolean isRunning) {
		ArrayList<Integer> coords = new ArrayList<Integer>();	// An array of coords to temporarily hold current coord of critter
		coords.add(x_coord);
		coords.add(y_coord);
		move(direction, coords);		// "Walk" which updates coords in array
		if (isRunning) {
			move(direction, coords);	// "Run" which updates coordds in array
		}
		for (Critter c : critSet) {		// If there is a critter in the set with same updated coord, return false
			if ((c.x_coord == coords.get(0)) && (c.y_coord == coords.get(1))) {
				return false;
			}
		}
		return true;					// Return true if no critter found in direction
	}
	
	/**
	 * This method updates the coordinates in a passed array with updated x, y values
	 * @param direction Int value from 0 to 7 indicating direction on grid
	 * @param coords An ArrayList holding previous terms and updates the x, y values
	 */
	private void move(int direction, ArrayList<Integer> coords) {
		int xVal = coords.get(0);
		int yVal = coords.get(1);
		// Determine movement of critter based on direction. Moving CCW on grid with 0 being horizontally right
        switch (direction) {
			case 0:
				xVal = (xVal + 1) % Params.world_width;
            	break;
			case 1:
                xVal = (xVal + 1) % Params.world_width;
                yVal = (yVal - 1 + Params.world_height) % Params.world_height;
                break;
            case 2:
                yVal = (yVal - 1 + Params.world_height) % Params.world_height;
                break;
            case 3:
            	xVal = (xVal - 1 + Params.world_width) % Params.world_width;
                yVal = (yVal - 1 + Params.world_height) % Params.world_height;
                break;
            case 4:
            	xVal = (xVal - 1 + Params.world_width) % Params.world_width;
                break;
            case 5:
            	xVal = (xVal - 1 + Params.world_width) % Params.world_width;
                yVal = (yVal + 1) % Params.world_height;
                break;
            case 6:
                yVal = (yVal + 1) % Params.world_height;
                break;       
            case 7:
            	xVal = (xVal + 1) % Params.world_width;
                yVal = (yVal + 1) % Params.world_height;
            	break;        
        }
        coords.set(0, xVal);	// Updates x, y coords in arrayList
        coords.set(1, yVal);
    }
	
	/**
	 * This method reproduces a critter and places it in an adjacent cell
	 * @param offspring The critter child
	 * @param direction Int value from 0 to 7 indicating direction on grid
	 */
	protected final void reproduce(Critter offspring, int direction) {
      	if (energy >= Params.min_reproduce_energy) {		// If ther is enough energy to reproduce
          	offspring.energy = energy/2;					// Offspring receives half energy of parent
          	energy = (energy/2) + (energy%2);
    		ArrayList<Integer> coords = new ArrayList<Integer>();	// Find new coords for the offspring
    		coords.add(x_coord);
    		coords.add(y_coord);
          	offspring.move(direction, coords);
          	offspring.x_coord = coords.get(0);
          	offspring.y_coord = coords.get(1);
          	babies.add(offspring);							// Add offspring to baby list
        }
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name Name of passed parameter, user input from main
	 * @throws InvalidCritterException	If critter_class_name does not correspond to a class
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class<?> c = Class.forName(critter_class_name);			// Creates class of string passed if class is present
			Critter crit = (Critter) c.newInstance();				// Creates new instance of class created above
			
			crit.x_coord = getRandomInt(Params.world_width);		// Set coords to random and energy to start_energy
			crit.y_coord = getRandomInt(Params.world_height);
			crit.energy = Params.start_energy;
			  
			critSet.add(crit);										// Add critter to set
			
	    } catch (Exception e) {										// If an error or exception, throw a new instance of InvalidCritterException
			throw new InvalidCritterException(critter_class_name);
	    } catch (Error e) {
			throw new InvalidCritterException(critter_class_name);
	    }
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return result List of Critters
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();			// List of critter instances
      	try {
      		if (!critSet.isEmpty()) {										// Iterates through set adding matching critters to result list
	            for (Critter c : critSet){
	                if (Class.forName(critter_class_name).isInstance(c)){
	                  result.add(c);
	                }
	            }
      		}
        } catch (Exception e) {
        	throw new InvalidCritterException(critter_class_name);			// If invalid name, throw exception
        }
		return result;			// Return list of instnaces
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static Set<Critter> getPopulation() {
			return critSet;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		critSet.clear();
		babies.clear();
	}
	
	/**
	 * This method calls doTimeStep on all critters in set, fights, adds babies, adds algae
	 */
	public static void worldTimeStep() {
		// Calls doTimeStep for all critters
		for (Critter c : critSet) {
			c.doTimeStep();
			// TODO maybe remove here
		}
		
		// Fight sequence for all critters in set
		for (Critter c1 : critSet) {
			for (Critter c2 : critSet) {
				if (c1 != c2) {
					// If critter is not itself and has the same coords
					if ((c1.x_coord == c2.x_coord) && (c1.y_coord == c2.y_coord)) {
						int c1Roll;					// Holds value of critter1 random "dice" roll
						int c2Roll;					// Holds value of critter2 random "dice" roll
						if ((c1.fight(c2.toString())) && (c1.energy > 0)) {
							c1Roll = getRandomInt(c1.energy);		// Roll if critter1 wants to fight
						} else { 
							c1Roll = 0;				// If critter1 attempts to run, roll 0
						}
						if ((c2.fight(c2.toString())) && (c2.energy > 0)) {
							c2Roll = getRandomInt(c2.energy);		// Roll if critter2 wants to fight
						} else {
							c2Roll = 0;				// If critter2 attempts to run, roll 0
						}
						// If coords are the same and each critter has enrgy
						if ((c1.x_coord == c2.x_coord) && (c1.y_coord == c2.y_coord) && (c1.energy > 0) && (c2.energy > 0)) {
							// Compare rolls, higher rolls gets half opponent energy added, set loser energy to 0 (indicate death)
							if (c1Roll >= c2Roll) {
                              	c1.energy += c2.energy/2;
                              	c2.energy = 0;
                            } else {
                              	c2.energy += c1.energy/2;
                              	c1.energy = 0;
                            }
						}
                    }
                }
            }
        }      
		
		Set<Critter> tempSet = new HashSet<Critter>();	// Temp set of old critters for removal
      	for (Critter c : critSet) {				
			c.moved = false;							// Reset moved flag to false
			c.energy -= Params.rest_energy_cost;		// Remove rest energy
			if (c.energy <= 0) {						// If energy is lower than 0, add to tempSet
				tempSet.add(c);
			}
		}
      	for (Critter c : tempSet) {						// Remove tempSet from critSet
      		critSet.remove(c);
      	}
      	tempSet.clear();								// Clear tempSet to use again
      	for (Critter baby : babies) {
      		baby.moved = false;							// Set moved flag to false
          	critSet.add(baby);							// Add babies to critSet
        }
      	babies.clear();									// Clear babies to use again
      	for (int i = 0; i < Params.refresh_algae_count; i++) {	// Add algae to map
          	Algae alga = new Algae();
          	alga.setX_coord(getRandomInt(Params.world_width));	// Random x, y coords, set energy
          	alga.setY_coord(getRandomInt(Params.world_height));
          	alga.setEnergy(Params.start_energy);
          	critSet.add(alga);							// Add to critList
        }
	}
	  
	/**
	 * This method displays the current world of the critters
	 */
	public static void displayWorld() {
		ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();	// 2D array of Critters
		for (int y = 0; y < Params.world_height; y++) {				// Initialize grid
			grid.add(new ArrayList<String>());
			for (int x = 0; x < Params.world_width; x++) {
				grid.get(y).add(" ");
			}
		}
		for (Critter c : critSet) {									// Add critters to grid
			grid.get(c.y_coord).set(c.x_coord, c.toString());
        }
		printBorder(Params.world_width);			// Add border
		for (ArrayList<String> row : grid) {
          	System.out.print("|");
			for (String crit : row) {				// Print each row and column in grid
				System.out.print(crit);
            }
			System.out.print("|");
			System.out.println();
        }
		printBorder(Params.world_width);			// Add border
    }
  
	/**
	 * This method prints the border of the grid
	 * @param length Grid width
	 */
	private static void printBorder(int length) {
		System.out.print("+");
		for (int c = 0; c < length; c++) {
			System.out.print("-");
		}
		System.out.println("+");
    }
}
