/**
 *  Filename: Agent.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

import java.util.ArrayList;
import java.util.Random;

public class Agent {
    public static String goVacuum = "VACUUM";
    public static String goForward = "MOVE FORWARD";
    public static String goLeft = "TURN LEFT";
    public static String goRight = "TURN RIGHT";
    public static String turnOff = "TURN OFF";
    
    private Random rand;
    
    public Agent() {
        this.rand = new Random();
    }

    /**
     * Given information about the 8 spaces around the Agent, decide where to move next.
     *  
     * Neighbor tile labels are:
     * 0 1 2
     * 3 A 4
     * 5 6 7
     * 
     * 
     * @return An int representing the move the agent wishes to make.
     */
    public int getAction() {
        // TODO this is not done.
        return rand.nextInt(8);
    }
    
    public void move(Tile newLocation) {
        //this.currentLocation = newLocation;
        //this.visitedTiles.add(newLocation);
        
        // TODO I think this needs error checking? Unless we do the checking in the loop driving Q Learning.
    }
}