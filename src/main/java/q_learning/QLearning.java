/**
 *  Filename: QLearning.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */


import java.io.*;
import java.util.*;

public class QLearning {

    // Read the input file in, add each integer to an arraylist, and return the arraylist
    public ArrayList<Integer> readInputFile(String filename) {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        try {

            InputStream resourceStream = QLearning.class.getResourceAsStream("/" + filename);

            Scanner fileScanner = new Scanner(resourceStream);


            // This used to be the scanner. Let's find out is the above code works.
            // Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextInt()) {
               integers.add(fileScanner.nextInt());
            }
        }
        catch(Exception e) {
            System.out.print(filename + " is not found");
            e.printStackTrace();
        }
        return integers;
    }

    // Draw ASCII board state 
    public void drawBoardState(String[][] theBoard) {

        for (int row = theBoard.length - 1; row >= 0; row--) {
            
            // Print the top wall
            if(row == theBoard.length - 1) {
                drawTopWall(theBoard);
            }

            // Print the left wall
            System.out.print("|");

            for (int column = 0; column < theBoard.length; column++) {

                // Display the content of cell board
                if(column == theBoard.length - 1) {
                    // TODO make the rows display properly (4,0) becomes (0,0)
                    System.out.print(theBoard[row][column]);
                }
                else {
                    System.out.print(theBoard[row][column] + " ");
                }
            }

            // Print the right wall
            System.out.print("|");

            // Go to the next line to print either the bottom or middle wall
            System.out.println();

            // Print the bottom wall
            if(row == 0) {
                drawBottomWall(theBoard);
            }

            // Print the middle wall
            else {
                drawMiddleWall(theBoard);
            }

            // Go to the next line to print dirt, furniture, goal, home, or agent
            System.out.println();
        }
    }

    // Draw the top wall
    public static void drawTopWall(String[][] theBoard) {
        for (int wall = 0; wall < theBoard.length; wall++) {
            System.out.print("+-");
        }
        System.out.print("+");
        System.out.println();
    }

    // Draw the bottom wall
    public static void drawBottomWall(String[][] theBoard) {
        for (int wall = 0; wall < theBoard.length; wall++) {
            System.out.print("+-");
        }
        System.out.print("+");
    }

    // Draw the middle wall
    public static void drawMiddleWall(String[][] theBoard) {
        for (int column = 0; column < theBoard.length + 1; column++) {
            System.out.print("+ ");
        }
    }

    // Run the instance of ReflexAgent.java
    public void run() {

        List<Integer> integers = readInputFile("input.txt");
        PerceptVector p;
        String moveStr;
        boolean agentOn = true;
        PrintWriter writer = null;

        int time = 0;
        int overallScore = 0;

        Environment env = new Environment(integers);

        try {
            writer = new PrintWriter("prog1_log.txt", "UTF-8");
            writer.println("Time    <B Du Df Db Dr Dl Gu Gf Gb Gr Gl>       Action     Score");
            writer.println("-----------------------------------------       ------     -----");          
        }
        catch (Exception e) {
            System.out.print("prog1_log.txt not created.");
        }

        // While (The agent has not turned off)
        while(agentOn) {

            // Create a new percept vector
            p = env.getPerceptVector(); 

            // Get action from the reflex agent
            moveStr = Agent.getAction(p);

            if (moveStr == "TURN OFF") agentOn = false;
            if (moveStr == "MOVE FORWARD") {
                env.moveAgentForward();
            } else if (moveStr == "TURN RIGHT") {
                env.turnAgentRight();
            } else if (moveStr == "TURN LEFT") {
                env.turnAgentLeft();
            } else if (moveStr == "VACUUM") {
                env.removeDirt(env.getAgentTile());
                overallScore += 100;
            }

            // Display the ASCII state of the board
            drawBoardState(env.getBoard());

            // Subtract one from the score
            overallScore -= 1;

            // Add one to the time
            time++;

            // Print out underneath console
            System.out.println("Last action: " + moveStr);
            System.out.println("Score: " + overallScore);
            System.out.println("Vector: " + p.toString());

            int lengthOfInt = String.valueOf(time).length();
            int lengthOfScore = String.valueOf(overallScore).length();
            int lengthOfMove = moveStr.length();

            if(lengthOfInt == 1) {
                if(lengthOfMove > 5) {
                    writer.printf("%s    %36s    %10s    %7s\n", time, p.toString(), moveStr, overallScore);
                }
                else {
                    writer.printf("%s    %36s    %10s    %5s\n", time, p.toString(), moveStr, overallScore);
                }
            }
            else if(lengthOfInt == 2) {
                writer.printf("%s    %35s    %-10s    %5s\n", time, p.toString(), moveStr, overallScore);
            }
            else if(lengthOfInt == 3) {
                writer.printf("%s    %34s    %-10s    %5s\n", time, p.toString(), moveStr, overallScore);
            }
            else if(lengthOfInt == 4) {
                writer.printf("%s    %33s    %-10s    %5s\n", time, p.toString(), moveStr, overallScore);
            }
        }
        writer.close();
    }

    public static void main(String[] args) {
        QLearning agent = new QLearning();
        // String fileName = args[0];
        agent.run();

    }
}