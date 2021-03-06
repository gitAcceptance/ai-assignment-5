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
    public final int EPOCHS = 10000;
    
    public QLearning() {
        this.rand = new Random();
    }

    

    // Run the instance of QLearning.java
    public void run(String fileName, boolean printEachStep, boolean learningRate, double alpha, double gamma) {
        printEachStep = false;
        PrintWriter writer = null;

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
        Agent a = new Agent(env, alpha, gamma, printEachStep, learningRate);
        env.setAgent(a);
        
        // FIXME replace all this trash with Agent.haveLearningEpisode() method calls
        // NOTE: MAKE SURE YOU USE Environment.setAgentTile() IF YOU HAVE TO. AGENT WILL STILL FUNCTION BUT ENVIRONMENT MIGHT PRINT WEIRD SHIT 
        
        
        
        // learning loop?
        System.out.println("Starting to learn!");
        //a.printRmatrix();
        while (epochCount < EPOCHS) {
            
            a.haveLearningEpisode();
            
            
            
            // TODO Maybe done? keep track of the ponies we ate
            //System.out.println("ecpoch count is: " + epochCount);
            epochCount++;
        }
        System.out.println("I have finished learning!");
        
        // TODO get the visited areas printing
        System.out.println("I am now exploting my learning policy with a.haveGreedyEpisode()");
        //a.printQmatrix();
        //a.printRmatrix();
        int ponyCount = a.haveGreedyEpisode();
        
        
        // Display the ASCII state of the board
        System.out.println();
        System.out.println("Ponies eaten : " + ponyCount + "/" + env.getNUMBER_OF_PONIES());
        writer.println();
        env.drawBoard();
        writer.close();
    }

    public static void main(String[] args) {
     
        String fileName = args[0];
        boolean printEachStep = false;
        boolean learningRate = false;
        
        if(args.length == 2) {
        	if(args[1].equals("--print")) {
        		printEachStep = true;
        	}
        	if(args[1].equals("--variable")) {
        		learningRate = true;
        	}
        }
        
        if(args.length == 3) {
        	if(args[1].equals("--print")) {
        		printEachStep = true;
        	}
        	if(args[2].equals("--variable")) {
        		learningRate = true;
        	}
        }
        
       
        
        // Passes Alpha and Gamma as params
        // TODO !!!!!IMPORTANT!!!!! check with her input file 
        
        System.out.println("********** Variation 1 with alpha = 0.75, gamma = 0.5 **********");
        QLearning agent1 = new QLearning();
        agent1.run(fileName, printEachStep, false,  0.75, 0.5);
        
        System.out.println();
        System.out.println("*********************** Variation 2 ***********************");
        
        System.out.println("********** Run 1/6 with alpha = 0.1, gamma = 0.5 **********");
        QLearning agent2 = new QLearning();
        agent2.run(fileName, printEachStep, false,  0.1, 0.5);
        
        System.out.println("********** Run 2/6 with alpha = 0.5, gamma = 0.5 **********");
        QLearning agent3 = new QLearning();
        agent3.run(fileName, printEachStep, false, 0.5, 0.5);
        
        System.out.println("********** Run 3/6 with alpha = 0.9, gamma = 0.5 **********");
        QLearning agent4 = new QLearning();
        agent4.run(fileName, printEachStep, false, 0.9, 0.5);
        
        System.out.println("********** Run 4/6 with alpha = 0.6, gamma = 0.1 **********");
        QLearning agent5 = new QLearning();
        agent5.run(fileName, printEachStep, false, 0.6, 0.1);
        
        System.out.println("********** Run 5/6 with alpha = 0.6, gamma = 0.5 **********");
        QLearning agent6 = new QLearning();
        agent6.run(fileName, printEachStep, false, 0.6, 0.5);
        
        System.out.println("********** Run 6/6 with alpha = 0.6, gamma = 0.9 **********");
        QLearning agent7 = new QLearning();
        agent7.run(fileName, printEachStep, false, 0.6, 0.9);
        
        System.out.println();
        System.out.println("*********************** Variation 3 ***********************");
        
        System.out.println("********** Run 1/6 with alpha = 0.1, gamma = 0.5 **********");
        QLearning agent8 = new QLearning();
        agent8.run(fileName, printEachStep, true,  0.1, 0.5);
        
        System.out.println("********** Run 2/6 with alpha = 0.5, gamma = 0.5 **********");
        QLearning agent9 = new QLearning();
        agent9.run(fileName, printEachStep, true, 0.5, 0.5);
        
        System.out.println("********** Run 3/6 with alpha = 0.9, gamma = 0.5 **********");
        QLearning agent10 = new QLearning();
        agent10.run(fileName, printEachStep, true, 0.9, 0.5);
        
        System.out.println("********** Run 4/6 with alpha = 0.6, gamma = 0.1 **********");
        QLearning agent11 = new QLearning();
        agent11.run(fileName, printEachStep, true, 0.6, 0.1);
        
        System.out.println("********** Run 5/6 with alpha = 0.6, gamma = 0.5 **********");
        QLearning agent12 = new QLearning();
        agent12.run(fileName, printEachStep, true, 0.6, 0.5);
        
        System.out.println("********** Run 6/6 with alpha = 0.6, gamma = 0.9 **********");
        QLearning agent13 = new QLearning();
        agent13.run(fileName, printEachStep, true, 0.6, 0.9);
        
        
        
        
        
        
    }
}