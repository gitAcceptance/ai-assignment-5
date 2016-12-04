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
import java.util.HashMap;
import java.util.Random;

public class Agent {
    private static Random rand = new Random();
    
    private double gamma;
    private Environment env;
    private Tile currentLocation;
    private HashMap<Tile, HashMap<Tile, Double>> Q;
    private HashMap<Tile, HashMap<Tile, Double>> R;
    
    
    
    public Agent(Environment env, double gamma) {
        this.env = env;
        this.gamma = gamma;
        
        this.Q = new HashMap<Tile, HashMap<Tile, Double>>();
        this.R = new HashMap<Tile, HashMap<Tile, Double>>();
        
        // Initialize Q and R
        for (Tile current : this.env.map) {
            Q.put(current, new HashMap<Tile, Double>());
            R.put(current, new HashMap<Tile, Double>());
            for (Tile destination: this.env.map) {
                Q.get(current).put(destination, 0.0d);
                if (destination.isGoal()) {
                    R.get(current).put(destination, 15.0d);
                } else if (destination.hasPony()) {
                    R.get(current).put(destination, 10.0d);
                } else if (destination.hasTroll()) {
                    R.get(current).put(destination, -15.0d);
                } else {
                    R.get(current).put(destination, 0.0d);
                }
            }
        }
        
        
    }

    
    private ArrayList<Tile> getPossibleMoves(Tile t) {
        ArrayList<Tile> options = new ArrayList<Tile>();
        int col = t.getCol();
        int row = t.getRow();
        
        if (row-1 >= 0 && row-1 <= this.env.roomSize && col-1 >= 0 && col-1 <= this.env.roomSize && !this.env.getTile(row-1, col-1).hasFurniture()) { // -1 -1
            options.add(this.env.getTile(row-1, col-1));
            
        } else if (row-1 >= 0 && row-1 <= this.env.roomSize && !this.env.getTile(row-1, col).hasFurniture()) { // -1 0
            options.add(this.env.getTile(row-1, col));
            
        } else if (row-1 >= 0 && row-1 <= this.env.roomSize && col+1 >= 0 && col+1 <= this.env.roomSize && !this.env.getTile(row-1, col+1).hasFurniture()) { // -1 1
            options.add(this.env.getTile(row-1, col+1));
            
        } else if (col-1 >= 0 && col-1 <= this.env.roomSize && !this.env.getTile(row, col-1).hasFurniture()) { // 0 -1
            options.add(this.env.getTile(row, col-1));
            
        } else if (col+1 >= 0 && col+1 <= this.env.roomSize && !this.env.getTile(row, col+1).hasFurniture()) { // 0 1
            options.add(this.env.getTile(row, col+1));
            
        } else if (row-1 >= 0 && row-1 <= this.env.roomSize && col+1 >= 0 && col+1 <= this.env.roomSize && !this.env.getTile(row+1, col-1).hasFurniture()) { // 1 -1
            options.add(this.env.getTile(row+1, col-1));
            
        } else if (row+1 >= 0 && row+1 <= this.env.roomSize && !this.env.getTile(row+1, col).hasFurniture()) { // 1 0
            options.add(this.env.getTile(row+1, col));
            
        } else if (row+1 >= 0 && row+1 <= this.env.roomSize && col+1 >= 0 && col+1 <= this.env.roomSize && !this.env.getTile(row+1, col+1).hasFurniture()) { // 1 1
            options.add(this.env.getTile(row+1, col+1));
        }
        
        return options;
    }
    
    
    public double maxQ(Tile t) {
        // FIXME implement this
        // start by using Agent.getPossibleMoves()
        return -1d;
    }
    
 // TODO add ability to control which action selection method we use
    
    // one whole learning episode
    public void haveAnEpisode(Tile startingLocation) {
        this.currentLocation = startingLocation;
        
        while (!currentLocation.isGoal()) { // TODO change to while not at goal AND not eaten
            // TODO remove the 10 point reward from R whenever a pony is removed
            learningActionSelection();
        }
        
        
    }
    
    /**
     * The Agent decides where to move next.
     *  
     * Neighbor tile labels are:
     * 0 1 2
     * 3 A 4
     * 5 6 7
     * 
     * 
     * @return An int representing the move the agent wishes to make.
     */
    public void learningActionSelection() {
        ArrayList<Tile> possibleFirstActions = this.getPossibleMoves(currentLocation);
        Tile nextState = possibleFirstActions.get(rand.nextInt(env.roomSize));
        
        
        // Q(state, action) = R(state, action) + gamma* MaxOf[Q(next state, all actions)]
        double rValue = R.get(currentLocation).get(nextState);
        
        double gValue = this.gamma * maxQ(nextState);
        
        Q.get(currentLocation).put(nextState, rValue + gValue);
        // Q has now been updated
        
        this.currentLocation = nextState;
        // TODO is this method done? Hard to know since I haven't written any fucking tests!
        
    }
    
    public void greedyActionSelection() {
        
    }
    
    public Tile getCurrentLocation() {
        return currentLocation;
    }
    
}