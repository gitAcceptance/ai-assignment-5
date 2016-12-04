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
    
    public QLearning() {
        this.rand = new Random();
    }

    // Draw ASCII board state 
    public void drawBoardState(String[][] theBoard, PrintWriter pw) {

        for (int row = theBoard.length - 1; row >= 0; row--) {
            
            // Print the top wall
            if(row == theBoard.length - 1) {
                drawTopWall(theBoard, pw);
            }

            // Print the left wall
            System.out.print("|");
            pw.print("|");

            for (int column = 0; column < theBoard.length; column++) {

                // Display the content of cell board
                if(column == theBoard.length - 1) {
                    // TODO make the rows display properly (4,0) becomes (0,0)
                    System.out.print(theBoard[row][column]);
                    pw.print(theBoard[row][column]);
                }
                else {
                    System.out.print(theBoard[row][column] + " ");
                    pw.print(theBoard[row][column] + " ");
                }
            }

            // Print the right wall
            System.out.print("|");
            pw.print("|");

            // Go to the next line to print either the bottom or middle wall
            System.out.println();
            pw.println();

            // Print the bottom wall
            if(row == 0) {
                drawBottomWall(theBoard, pw);
            }

            // Print the middle wall
            else {
                drawMiddleWall(theBoard, pw);
            }

            // Go to the next line to print pony, furniture, goal, home, or agent
            System.out.println();
            pw.println();
        }
    }

    // Draw the top wall
    public static void drawTopWall(String[][] theBoard, PrintWriter pw) {
        for (int wall = 0; wall < theBoard.length; wall++) {
            System.out.print("+-");
            pw.print("+-");
        }
        System.out.print("+");
        pw.print("+");
        System.out.println();
        pw.println();
    }

    // Draw the bottom wall
    public static void drawBottomWall(String[][] theBoard, PrintWriter pw) {
        for (int wall = 0; wall < theBoard.length; wall++) {
            System.out.print("+-");
            pw.print("+-");
        }
        System.out.print("+");
        pw.print("+");
    }

    // Draw the middle wall
    public static void drawMiddleWall(String[][] theBoard, PrintWriter pw) {
        for (int column = 0; column < theBoard.length + 1; column++) {
            System.out.print("+ ");
            pw.print("+ ");
        }
    }

    // Run the instance of QLearning.java
    public void run(String fileName) {

        String moveStr;
        boolean agentOn = true;
        PrintWriter writer = null;

        int time = 0;
        int overallScore = 0;

        Environment env = new Environment(fileName);

        System.out.println("Agent symbol: " + env.getAgentSymbol());
        System.out.println("Pony symbol: " + env.getPonySymbol());
        System.out.println("Obstruction symbol: " + env.getFurnitureSymbol());
        System.out.println("Goal symbol: " + env.getGoalSymbol());
        System.out.println("Troll symbol: " + env.getTrollSymbol());
        System.out.println("Travelled symbol: " + env.getTravelledSymbol());

        try {
            writer = new PrintWriter("prog1_log.txt", "UTF-8");       
        }
        catch (Exception e) {
            System.out.print("prog1_log.txt not created.");
            e.printStackTrace();
            System.exit(1);
        }

        // Display the ASCII state of the board
        drawBoardState(env.getBoard(), writer);
        
        // While (The agent has not turned off)
        while(agentOn) {

            // Get random choice from agent
            Agent a = new Agent(env, 0.5); // TODO pass the constructor what gamma is actually set to.
            int choice = rand.nextInt(8);
            
            // FIXME replace all this trash with Agent.haveAnEpisode() method calls
            // NOTE: MAKE SURE YOU USE Environment.setAgentTile() IF YOU HAVE TO. AGENT WILL STILL FUNCTION BUT ENVIRONMENT MIGHT PRINT WEIRD SHIT 
            
            /*  
             * Neighbor tile labels are:
             * 0 1 2
             * 3 A 4
             * 5 6 7
             */
            if (choice == 0) {
                env.moveAgent(env.getAgentTile().getRow() - 1, env.getAgentTile().getCol() - 1);
            } else if (choice == 1) {
                env.moveAgent(env.getAgentTile().getRow() - 1, env.getAgentTile().getCol());
            } else if (choice == 2) {
                env.moveAgent(env.getAgentTile().getRow() - 1, env.getAgentTile().getCol() + 1);
            } else if (choice == 3) {
                env.moveAgent(env.getAgentTile().getRow(), env.getAgentTile().getCol() - 1);
            } else if (choice == 4) {
                env.moveAgent(env.getAgentTile().getRow(), env.getAgentTile().getCol() + 1);
            } else if (choice == 5) {
                env.moveAgent(env.getAgentTile().getRow() + 1, env.getAgentTile().getCol() - 1);
            } else if (choice == 6) {
                env.moveAgent(env.getAgentTile().getRow() + 1, env.getAgentTile().getCol());
            } else if (choice == 7) {
                env.moveAgent(env.getAgentTile().getRow() + 1, env.getAgentTile().getCol() + 1);
            }
        
            // Display the ASCII state of the board
            //drawBoardState(env.getBoard());

            // Add one from the score
            overallScore += 1;
            

            // Add one to the time
            time++;

            int lengthOfInt = String.valueOf(time).length();
            int lengthOfScore = String.valueOf(overallScore).length();
            //int lengthOfMove = moveStr.length();
            
            if (env.getAgentTile().hasTroll()) {
            	overallScore -= 15;
            	System.out.println("The burglar has been eaten. Quitting from program.");
            	break;
            	// TODO end the trial after he gets killed by the troll
            }
            
            if (env.getAgentTile().hasPony()) {
            	overallScore += 10;
            	env.getAgentTile().setPony(false);
            }
            
            if (env.getAgentTile().isGoal()) {
                agentOn = false;
                overallScore += 15;
            }
        }
        
        // TODO finish calculating score
        
        // Display the ASCII state of the board
        System.out.println();
        System.out.println("Score: " + overallScore);
        writer.println();
        writer.println("Score: " + overallScore);
        drawBoardState(env.getBoard(), writer);
        writer.close();
    }

    public static void main(String[] args) {
        QLearning agent = new QLearning();
        String fileName = args[0];
        agent.run(fileName);
    }
}