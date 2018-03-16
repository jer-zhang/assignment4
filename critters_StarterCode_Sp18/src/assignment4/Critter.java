package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Set<Critter> critSet = new HashSet<Critter>();
  
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() {
    	return " ";
	}
	
	private int energy = 0;
  
	protected int getEnergy() {
    	return energy;
    }
	
	private int x_coord;
	private int y_coord;
	private boolean moved;
	
	protected final void walk(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(x_coord);
		coords.add(y_coord);
		if (moved == false) {
	      	move(direction, coords);
	      	x_coord = coords.get(0);
	      	y_coord = coords.get(1);
		}
        energy -= Params.walk_energy_cost;
      	moved = true;
	}
	
	protected final void run(int direction) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(x_coord);
		coords.add(y_coord);
		if (moved == false) {
			move(direction, coords);
		  	move(direction, coords);
	      	x_coord = coords.get(0);
	      	y_coord = coords.get(1);
		}
      	energy -= Params.run_energy_cost;
        moved = true;
	}
	
	/**
	 * Checks if a critter is in the target location
	 * @param direction Integer 0 to 7
	 * @param isRunning false if walking, true if running
	 * @return true if location is empty, false otherwise
	 */
	private boolean look(int direction, boolean isRunning) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(x_coord);
		coords.add(y_coord);
		move(direction, coords);
		if (isRunning) {
			move(direction, coords);
		}
		for (Critter c : critSet) {
			if ((c.x_coord == coords.get(0)) && (c.y_coord == coords.get(1))) {
				return false;
			}
		}
		return true;
	}
	
	private void move(int direction, ArrayList<Integer> coords) {
		int xVal = coords.get(0);
		int yVal = coords.get(1);
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
        coords.set(0, xVal);
        coords.set(1, yVal);
    }
  
	protected final void reproduce(Critter offspring, int direction) {
      	if (energy >= Params.min_reproduce_energy) {
          	offspring.energy = energy/2;
          	energy = (energy/2) + (energy%2);
    		ArrayList<Integer> coords = new ArrayList<Integer>();
    		coords.add(x_coord);
    		coords.add(y_coord);
          	offspring.move(direction, coords);
          	offspring.x_coord = coords.get(0);
          	offspring.y_coord = coords.get(1);
          	babies.add(offspring);
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
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class<?> c = Class.forName(critter_class_name);
			Critter crit = (Critter) c.newInstance();
			
			crit.x_coord = getRandomInt(Params.world_width);
			crit.y_coord = getRandomInt(Params.world_height);
			crit.energy = Params.start_energy;
			  
			critSet.add(crit);
	    } catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
	    } catch (Error e) {
			throw new InvalidCritterException(critter_class_name);
	    }
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
      	try {
      		if (!critSet.isEmpty()) {
	            for (Critter c : critSet){
	                if (Class.forName(critter_class_name).isInstance(c)){
	                  result.add(c);
	                }
	            }
      		}
        } catch (Exception e) {
        	throw new InvalidCritterException(critter_class_name);
        }
		return result;
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
		protected static Set<Critter> getCritSet() {
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
	
	public static void worldTimeStep() {
		for (Critter c : critSet) {
			c.doTimeStep();
			// TODO maybe remove here
		}
		
		for (Critter c1 : critSet) {
			for (Critter c2 : critSet) {
				if (c1 != c2) {
					if ((c1.x_coord == c2.x_coord) && (c1.y_coord == c2.y_coord)) {
						int c1Roll;
						int c2Roll;
						if ((c1.fight(c2.toString())) && (c1.energy > 0)) {
							c1Roll = getRandomInt(c1.energy);
						} else { 
							c1Roll = 0;
						}
						if ((c2.fight(c2.toString())) && (c2.energy > 0)) {
							c2Roll = getRandomInt(c2.energy);
						} else {
							c2Roll = 0;
						}
						if ((c1.x_coord == c2.x_coord) && (c1.y_coord == c2.y_coord) && (c1.energy > 0) && (c2.energy > 0)) {
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
		
		Set<Critter> tempSet = new HashSet<Critter>();
      	for (Critter c : critSet) {
			c.moved = false;
			c.energy -= Params.rest_energy_cost;
			if (c.energy <= 0) {
				tempSet.add(c);
			}
		}
      	for (Critter c : tempSet) {
      		critSet.remove(c);
      	}
      	for (Critter baby : babies) {
          	critSet.add(baby);
        }
      	
      	for (int i = 0; i < Params.refresh_algae_count; i++) {
          	Algae alga = new Algae();
          	alga.setX_coord(getRandomInt(Params.world_width));
          	alga.setY_coord(getRandomInt(Params.world_height));
          	alga.setEnergy(Params.start_energy);
          	critSet.add(alga);
        }
	}
	  
	public static void displayWorld() {
		ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
		for (int y = 0; y < Params.world_height; y++) {
			grid.add(new ArrayList<String>());
			for (int x = 0; x < Params.world_width; x++) {
				grid.get(y).add(" ");
			}
		}
		for (Critter c : critSet) {
			grid.get(c.y_coord).set(c.x_coord, c.toString());
        }
		printBorder(Params.world_width);
		for (ArrayList<String> row : grid) {
          	System.out.print("|");
			for (String crit : row) {
				System.out.print(crit);
            }
			System.out.print("|");
			System.out.println();
        }
		printBorder(Params.world_width);
    }
  
	private static void printBorder(int length) {
		System.out.print("+");
		for (int c = 0; c < length; c++) {
			System.out.print("-");
		}
		System.out.println("+");
    }
}
