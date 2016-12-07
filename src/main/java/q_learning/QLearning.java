/**
 *  Filename: QLearning.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

import java.io.*;
import java.util.*;

public class QLearning {
    
    Random rand;
    public final int EPOCHS = 250;
    
    public QLearning() {
        this.rand = new Random();
    }

    

    // Run the instance of QLearning.java
    public void run(String fileName, boolean printEachStep, double alpha, double gamma) {

        printEachStep = true;
        String moveStr;
        boolean agentOn = true;
        PrintWriter writer = null;

        int time = 0;
        int poniesEaten = 0;
        int epochCount = 0;
        

        try {
            writer = new PrintWriter("prog1_log.txt", "UTF-8");       
        }
        catch (Exception e) {
            System.out.print("prog1_log.txt not created.");
            e.printStackTrace();
            System.exit(1);
        }

        Environment env = new Environment(fileName, writer);
        
        System.out.println("Agent symbol: " + env.getAgentSymbol());
        System.out.println("Pony symbol: " + env.getPonySymbol());
        System.out.println("Obstruction symbol: " + env.getFurnitureSymbol());
        System.out.println("Goal symbol: " + env.getGoalSymbol());
        System.out.println("Troll symbol: " + env.getTrollSymbol());
        System.out.println("Travelled symbol: " + env.getTravelledSymbol());
        
        // Display the ASCII state of the board
        //env.drawBoard();
        
        // While (The agent has not turned off)
        
    	     
        // Get random choice from agent
        Agent a = new Agent(env, alpha, gamma, printEachStep);
        env.setAgent(a);
        
        // FIXME replace all this trash with Agent.haveLearningEpisode() method calls
        // NOTE: MAKE SURE YOU USE Environment.setAgentTile() IF YOU HAVE TO. AGENT WILL STILL FUNCTION BUT ENVIRONMENT MIGHT PRINT WEIRD SHIT 
        
        
        
        // learning loop?
        System.out.println("Starting to learn!");
        while (epochCount < EPOCHS) {
            
            a.haveLearningEpisode(env.getAgentTile());
            
            
            
            // TODO Maybe done? keep track of the ponies we ate
            //System.out.println("ecpoch count is: " + epochCount);
            epochCount++;
        }
        System.out.println("I have finished learning!");
        
        // TODO get the visited areas printing
        System.out.println("I am now exploting my learning policy with a.haveGreedyEpisode()");
        a.haveGreedyEpisode();
        
        // Display the ASCII state of the board
        System.out.println();
        System.out.println("Ponies eaten : " + poniesEaten + "/" + env.getNUMBER_OF_PONIES());
        writer.println();
        env.drawBoard();
        writer.close();
    }

    public static void main(String[] args) {
     
        String fileName = args[0];
        boolean printEachStep = false;
        
        if(args.length == 2) {
        	if(args[1].equals("--print")) {
        		printEachStep = true;
        	}
        }
        
        // Passes Alpha and Gamma as params
        
        System.out.println("********** Run 1/6 with alpha = 0.1, gamma = 0.5 **********");
        QLearning agent = new QLearning();
        agent.run(fileName, printEachStep, 0.1, 0.5);
        
        
        
        // TODO uncomment these when done 
//        System.out.println("********** Run 2/6 with alpha = 0.5, gamma = 0.5 **********");
//        QLearning agent1 = new QLearning();
//        agent1.run(fileName, printEachStep, 0.5, 0.5);
//        
//        System.out.println("********** Run 3/6 with alpha = 0.9, gamma = 0.5 **********");
//        QLearning agent2 = new QLearning();
//        agent2.run(fileName, printEachStep, 0.9, 0.5);
//        
//        System.out.println("********** Run 4/6 with alpha = 0.6, gamma = 0.1 **********");
//        QLearning agent3 = new QLearning();
//        agent3.run(fileName, printEachStep, 0.6, 0.1);
//        
//        System.out.println("********** Run 5/6 with alpha = 0.6, gamma = 0.5 **********");
//        QLearning agent4 = new QLearning();
//        agent4.run(fileName, printEachStep, 0.6, 0.5);
//        
//        System.out.println("********** Run 6/6 with alpha = 0.6, gamma = 0.9 **********");
//        QLearning agent5 = new QLearning();
//        agent5.run(fileName, printEachStep, 0.6, 0.9);
        
        
    }
}