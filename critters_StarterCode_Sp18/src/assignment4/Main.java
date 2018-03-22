package assignment4;
/* CRITTERS Main.java
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

import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.*;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */

/**
 * This class holds the controller of the Critter world
 * @author Jerry Zhang, Celine Lillie
 *
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        while (true) {
          	System.out.print("critters>");
        	String command = kb.nextLine();				// Take keyboard line and split into tokens
			String[] tokens = command.split("[ \t]+");
			try {
                if ((tokens[0].equals("quit")) && (tokens.length == 1)) {
                	// If the only token is quit, break out of while loop
                    break;
                } else if ((tokens[0].equals("show")) && (tokens.length == 1)) {
                	// If the only token is show, display world
                    Critter.displayWorld();
                } else if ((tokens[0].equals("step")) && (tokens.length <= 2)) {
                	// If there are 2 tokens, "step" and a number, loops worldTimeStep number of times
                    if (tokens.length == 2) {
						for (int i = 0; i < Integer.parseInt(tokens[1]); i++) {
							Critter.worldTimeStep();
                        }
                    } else if (tokens.length == 1) {
                    	// If the only token is step, step worldTimeStep once
						Critter.worldTimeStep();
                    }
                } else if ((tokens[0].equals("seed")) && (tokens.length == 2)) {
                	// If there are 2 tokens, "seed" and a number, generate seed
                  	Critter.setSeed(Integer.parseInt(tokens[1]));
                } else if ((tokens[0].equals("make")) && (tokens.length <= 3) && (tokens.length > 1)) {
                	// If there are 2-3 tokens, "make", name, and maybe number
                	int numCritters;			// Temp holder of number of critter to make
                	if (tokens.length == 2) {
                		numCritters = 1;
                	} else {
                		numCritters = Integer.parseInt(tokens[2]);
                	}
                	for (int i = 0; i < numCritters; i++) {
                		Critter.makeCritter(myPackage + "." + tokens[1]);	// Loop makeCritter, numCritters times
                	}
                } else if ((tokens[0].equals("stats")) && (tokens.length == 2)) {	// Display stats (# of critters present on board)
                	List<Critter> critterInstances= new ArrayList<Critter>();		// Array of instances
                	critterInstances = Critter.getInstances(myPackage + "." + tokens[1]);	// Get list of instances of critter desired
                	Class<?> c = Class.forName(myPackage + "." + tokens[1]);		// Create class of critter
                	Method m = c.getMethod("runStats", List.class);					// Get the method "runStats" in that class
                	m.invoke(null, critterInstances);								// Invoke method passing list of instance
                } else {
                	throw new Exception();									// Throw exception if any exception occurs (forName, parseInt)
                }
            } catch (Exception e) {
              	System.out.println("error processsing: " + command);		// Error line
            }
        }
        // System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }
}
