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
import java.util.concurrent.TimeUnit;

public class Agent {
    private static Random rand = new Random();
    private boolean isAlive;
    private boolean printSteps;
    private double alpha;
    private double gamma;
    private Environment env;
    private Tile currentLocation;
    private HashMap<Tile, HashMap<Tile, Double>> Q;
    private HashMap<Tile, HashMap<Tile, Double>> R;
    private HashMap<Tile, HashMap<Tile, Double>> mutableR;
    
    
    public Agent(Environment env, double alpha, double gamma, boolean printSteps) {
        this.isAlive = true;
        this.env = env;
        this.alpha = alpha;
        this.gamma = gamma;
        this.printSteps = printSteps;
        
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
                } else if (destination.hasFurniture()) {
                	R.get(current).put(destination, 0.0d);
                } else {
                    R.get(current).put(destination, 2.0d);
                }
            }
        }
    }

    
    private ArrayList<Tile> getPossibleMoves(Tile t) {
        ArrayList<Tile> options = new ArrayList<Tile>();
        int col = t.getCol();
        int row = t.getRow();
        
        if (row-1 >= 0 && row-1 < this.env.roomSize && col-1 >= 0 && col-1 < this.env.roomSize) { // -1 -1
            if (!this.env.getTile(row-1, col-1).hasFurniture()) {
                options.add(this.env.getTile(row-1, col-1));
            }
        }
        if (row-1 >= 0 && row-1 < this.env.roomSize) { // -1 0
            if (!this.env.getTile(row-1, col).hasFurniture()) {
                options.add(this.env.getTile(row-1, col));
            }
        }
        if (row-1 >= 0 && row-1 < this.env.roomSize && col+1 >= 0 && col+1 < this.env.roomSize) { // -1 1
            if (!this.env.getTile(row-1, col+1).hasFurniture()) {
                options.add(this.env.getTile(row-1, col+1));
            }
        }
        if (col-1 >= 0 && col-1 < this.env.roomSize) { // 0 -1
            if (!this.env.getTile(row, col-1).hasFurniture()) {
                options.add(this.env.getTile(row, col-1));
            }
        }
        if (col+1 >= 0 && col+1 < this.env.roomSize) { // 0 1
            if (!this.env.getTile(row, col+1).hasFurniture()) {
                options.add(this.env.getTile(row, col+1));
            }
        }
        if (row+1 >= 0 && row+1 < this.env.roomSize && col-1 >= 0 && col-1 < this.env.roomSize) { // 1 -1
            if (!this.env.getTile(row+1, col-1).hasFurniture()) {
                options.add(this.env.getTile(row+1, col-1));
            }
        }
        if (row+1 >= 0 && row+1 < this.env.roomSize) { // 1 0
            if (!this.env.getTile(row+1, col).hasFurniture()) {
                options.add(this.env.getTile(row+1, col));
            }
        }
        if (row+1 >= 0 && row+1 < this.env.roomSize && col+1 >= 0 && col+1 < this.env.roomSize) { // 1 1
            if (!this.env.getTile(row+1, col+1).hasFurniture()) {
                options.add(this.env.getTile(row+1, col+1));
            }
        }
        if (options.size() == 1) {
            System.out.println("Your problem is here.");
        }
        return options;
    }
    
    
    
    public void printQmatrix() {
        System.out.println();
        for (int i = 0; i < env.roomSize; i++) {
            for (int j = 0; j < env.roomSize; j++) {
                System.out.print(i + "," + j + "  ");
            }
        }
        
        for (int i = 0; i < env.roomSize; i++) {
            for (int j = 0; j < env.roomSize; j++) {
                System.out.println(i + "," + j + "  :");
                for (int ii = 0; ii < env.roomSize; ii++) {
                    for (int jj = 0; jj < env.roomSize; jj++) {
                        System.out.print(Q.get(env.getTile(i, j)).get(env.getTile(ii, jj)) + "  ");
                    }
                }
            }
        }
        System.out.println();
    }
    
    
    public void printRmatrix() {
        System.out.println();
        for (int i = 0; i < env.roomSize; i++) {
            for (int j = 0; j < env.roomSize; j++) {
                System.out.print(i + "," + j + "  ");
            }
        }
        //System.out.println();
        
        
        for (int i = 0; i < env.roomSize; i++) {
            for (int j = 0; j < env.roomSize; j++) {
                System.out.println(i + "," + j + "  ");
                for (int ii = 0; ii < env.roomSize; ii++) {
                    for (int jj = 0; jj < env.roomSize; jj++) {
                        System.out.print(R.get(env.getTile(i, j)).get(env.getTile(ii, jj)) + "  ");
                    }
                }
            }
        }
    }
    
    

    
    
    
    public double maxQ(Tile target) {
        Tile max = null;
        for (Tile t : getPossibleMoves(target)) {
            if (max == null) {
                max = t;
            }
            if (Q.get(target).get(t) > Q.get(target).get(max)) {
                max = t;
            }
        }
        return Q.get(target).get(max);
    }
    
 // TODO add ability to control which action selection method we use
    
    // one whole learning episode
    public void haveLearningEpisode() {
        env.refresh();
        this.mutableR = new HashMap<Tile, HashMap<Tile, Double>>(this.R);
        isAlive = true;
        
        Tile agentHome = null;
        do {
            agentHome = this.env.getTile(rand.nextInt(env.roomSize), rand.nextInt(env.roomSize));
        } while (agentHome.hasPony() && agentHome.hasTroll() && agentHome.hasFurniture() && agentHome.isGoal());
        agentHome.hasAgent = true;
        agentHome.isHome = true;
        this.currentLocation = agentHome;
        this.currentLocation.setHasVisited();
        
        while (isAlive) {
            //System.out.println("Agent position row:" + currentLocation.getRow() + " col:" + currentLocation.getCol());
            
//            if (printSteps) {
//                env.drawBoard();
//                //printQmatrix();
//            }
            learningActionSelection();
            
            if (currentLocation.hasTroll()) {
                //System.out.println("We got eaten!");
                isAlive = false;
            } else if (currentLocation.hasPony()) {
                //System.out.println("We ate a pony!");
                for (HashMap<Tile, Double> h : mutableR.values()) {
                    h.put(currentLocation, 0d);
                }
            } else if (currentLocation.isGoal()) {
                isAlive = false;
                //System.out.println("We made it to the exit!");
            }
            
            //System.out.println("Count is: " + count);
            
            try {
                //TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                // fuck it
            }
            this.env.setAgentTile(currentLocation);
        }    
    }
    
    public void haveGreedyEpisode() {
        
        env.refresh();
        isAlive = true;
        
        Tile agentHome = null;
        do {
            agentHome = this.env.getTile(rand.nextInt(env.roomSize), rand.nextInt(env.roomSize));
        } while (agentHome.hasPony() && agentHome.hasTroll() && agentHome.hasFurniture() && agentHome.isGoal());
        agentHome.hasAgent = true;
        agentHome.isHome = true;
        this.currentLocation = agentHome;
        this.currentLocation.setHasVisited();
        
        
        
        
        while (isAlive) {
            System.out.println("Agent position row:" + currentLocation.getRow() + " col:" + currentLocation.getCol());
            
            if (printSteps) {
                env.drawBoard();
                //printQmatrix();
            }
            
            greedyActionSelection();
            
            if (currentLocation.hasTroll()) {
            	System.out.print("We hit a troll.");
                isAlive = false;
            } else if (currentLocation.hasPony()) {
            	System.out.println("We got a pony! Yum.");
                
            } else if (currentLocation.isGoal()) {
                isAlive = false;
                System.out.println("We made it to the exit!");
            }
            this.env.setAgentTile(currentLocation);
        }
        
    }
    
    /**
     * The Agent decides where to move next.
     *  
     * Neighbor tile labels are:
     * 0 1 2
     * 3 A 4
     * 5 6 7
     */
    public void learningActionSelection() {
        if (env.getAgentTile() == null) {
            System.out.println("uh oh");
        }
        //System.out.println("Agent position before move row:" + currentLocation.getRow() + " col:" + currentLocation.getCol());
        
        ArrayList<Tile> possibleFirstActions = this.getPossibleMoves(currentLocation);
        Tile nextState = possibleFirstActions.get(rand.nextInt(possibleFirstActions.size()));
        
        
        // Q(state, action) = R(state, action) + gamma* MaxOf[Q(next state, all actions)]
        double rValue = mutableR.get(currentLocation).get(nextState);
        
        double gValue = this.gamma * maxQ(nextState);
        
        Q.get(currentLocation).put(nextState, rValue + gValue);
        // Q has now been updated
        
        this.currentLocation = nextState;
        //env.setAgentTile(this.currentLocation);
        this.currentLocation.setHasVisited();
        //System.out.println("Agent position after move row:" + currentLocation.getRow() + " col:" + currentLocation.getCol());
        // TODO is this method done? Hard to know since I haven't written any fucking tests!
        
    }
    
    public void greedyActionSelection() {
        //env.drawBoard();
        ArrayList<Tile> possibleFirstActions = this.getPossibleMoves(currentLocation);
        Tile nextState = null;
        for (Tile t : possibleFirstActions) {
            if (nextState == null) {
                nextState = t;
            }
            if (Q.get(currentLocation).get(t) > Q.get(currentLocation).get(nextState)) {
                nextState = t;
            }
        }
        this.currentLocation = nextState;
        // TODO is this method done? Hard to know since I haven't written any fucking tests!
        this.currentLocation.setHasVisited();
        //env.drawBoard();
    }
    
    public Tile getCurrentLocation() {
        return currentLocation;
    }
    
}