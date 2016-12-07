/**
 *  Filename: Environment.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

import java.io.*;
import java.util.*;

public class Environment {
    Random rand;
    ArrayList<Tile> map;    // arraylist of tiles
    PrintWriter boardOut;
    
    String heading;         // orientation of agent
    boolean sensorPressed;  // determines if sensor is pressed
    
    int roomSize;           // constant for the room size
    private int NUMBER_OF_TROLLS;
    private int NUMBER_OF_PONIES;

    final String theAgent = "B";
    final String thePony = "P";
    final String theFurniture = "X";
    final String theGoal = "E";
    final String theTroll = "T";
    final String travelled = "$";
    final String home = "H";
    
    private Agent steve;

    // Create the initial envionment based on the input file
    public Environment(String fileName, PrintWriter pw) {

        this.rand = new Random();
        this.heading = "NORTH";
        this.boardOut = pw;
           
        try {

            InputStream resourceStream = QLearning.class.getResourceAsStream("/" + fileName);
            Scanner fileScanner = new Scanner(resourceStream);
            String line;

            // Scan the first line
            line = fileScanner.nextLine();
            Scanner scan1 = new Scanner(line);
            while(scan1.hasNextInt()) {
                // System.out.println("Goal is " + scan1.nextInt());
                // System.out.println("Number of trolls is " + scan1.nextInt());
                // System.out.println("Number of ponies is " + scan1.nextInt());
                roomSize = scan1.nextInt(); // 5
                NUMBER_OF_TROLLS = scan1.nextInt();
                NUMBER_OF_PONIES = scan1.nextInt();
            }

            this.map = new ArrayList<Tile>();

            // make a room full of empty floor tiles
            for (int row = 0; row < roomSize; row++) {
                for (int col = 0; col < roomSize; col++) {
                    this.map.add(new Tile(row, col));
                }
            }

            // Scan the second line
            line = fileScanner.nextLine();
            Scanner scan2 = new Scanner(line);
            while(scan2.hasNextInt()) {
                // System.out.println("Goal (" + scan2.nextInt() + "), (" + scan2.nextInt() +")");
                Tile t = this.getTile(scan2.nextInt(), scan2.nextInt());
                t.setGoal(true);
            }

            // Scan the third line
            line = fileScanner.nextLine();
            Scanner scan3 = new Scanner(line);
            while(scan3.hasNextInt()) {
                // System.out.println("Pony coordinates");
                // System.out.println("(" + scan3.nextInt()  + "), (" + scan3.nextInt() + ")");
                Tile t = this.getTile(scan3.nextInt(), scan3.nextInt());
                t.setPony(true);

            }

            // Scan the fourth line
            line = fileScanner.nextLine();
            Scanner scan4 = new Scanner(line);
            while(scan4.hasNextInt()) {
                // System.out.println("Obstruction coordinates");
                // System.out.println("(" + scan4.nextInt()  + "), (" + scan4.nextInt() + ")");
            
                    int checkFirstNum = scan4.nextInt();
                    int checkSecondNum = scan4.nextInt();


                    if(!(checkFirstNum == -1 && checkSecondNum == -1)) {
                        Tile t = this.getTile(scan4.nextInt(), scan4.nextInt());
                        t.setFurniture(true);
                    }
            }

            // Scan the fifth line
            line = fileScanner.nextLine();
            Scanner scan5 = new Scanner(line);
            while(scan5.hasNextInt()) {
                // System.out.println("Troll coordinates");
                // System.out.println("(" + scan5.nextInt()  + "), (" + scan5.nextInt() + ")");
                Tile t = this.getTile(scan5.nextInt(), scan5.nextInt());
                t.setTroll(true);
            }

            fileScanner.close();

            // now we randomly set the home and place the robot in the room at the home location.
            Tile agentHome = null;
            do {
                agentHome = this.getTile(rand.nextInt(roomSize), rand.nextInt(roomSize));
            } while (!(agentHome.hasPony() == false && agentHome.hasTroll() == false && agentHome.hasFurniture() == false));
            agentHome.isHome = true;
            agentHome.setHasAgent(true);
            agentHome.setHasVisited();
        }

        catch(Exception e) {
            System.out.print(fileName + " is not found");
            e.printStackTrace();
        }
        
        if (this.getAgentTile() == null) {
            System.out.println("FUCK");
        }
        
        
    }

    // get the current state of the board in an easily printable form
    public String[][] getBoard() {

        String[][] theBoard = new String[roomSize][roomSize];

        for (Tile t : this.map) {

            if(t.hasAgent()) {
                theBoard[t.getRow()][t.getCol()] = theAgent;
            }
            else if (t.isHome == true) {
                theBoard[t.getRow()][t.getCol()] = home;
            }
            else if (t.hasFurniture()) {
                theBoard[t.getRow()][t.getCol()] = theFurniture;
            }
            else if (t.hasVisited()) {
                theBoard[t.getRow()][t.getCol()] = travelled;
            }
            else if (t.hasPony()) {
                theBoard[t.getRow()][t.getCol()] = thePony;
            }
            else if (t.isGoal()) {
                theBoard[t.getRow()][t.getCol()] = theGoal;
            }
            else if (t.hasTroll()) {
                theBoard[t.getRow()][t.getCol()] = theTroll;
            } 
            else {
                theBoard[t.getRow()][t.getCol()] = " ";
            }
        }

        return theBoard;
    }

    public String getAgentSymbol() {
        return this.theAgent;
    }

    public String getPonySymbol() {
        return this.thePony;
    }

    public String getFurnitureSymbol() {
        return this.theFurniture;
    }

    public String getGoalSymbol() {
        return this.theGoal;
    }

    public String getTrollSymbol() {
        return this.theTroll;
    }

    public String getTravelledSymbol() {
        return this.travelled;
    }

    // Returns a reference to the tile on the map matching the given params.
    public Tile getTile(int row, int col) {
        for (Tile t : this.map) {
            if (t.getRow() == row && t.getCol() == col) return t;
        }
        return null;
    }

    // Returns a reference to the tile the agent is on.
    public Tile getAgentTile() {
        for (Tile t : this.map) {
            if (t.hasAgent() == true) {
                return t;
            }
        }
        return null;
    }

    public void setAgentTile(Tile newLocation) {
        this.getAgentTile().setHasAgent(false);
        //this.getTile(this.steve.getCurrentLocation().getRow(), this.steve.getCurrentLocation().getRow()).setHasAgent(true);
        this.getTile(newLocation.getRow(), newLocation.getCol()).setHasAgent(true);
    }
    
    
    // Move agent to specified location.
    // returns false if the attempted move isn't allowed, meaning the bump sensor should be triggered
    public boolean moveAgent(int row, int col) {
        if (this.getTile(row, col) == null) {                       // out of bounds
            return false;
        } else if (this.getTile(row, col).hasFurniture() == true) {   // furniture in the way
            return false;
        } else {
            this.getAgentTile().setHasAgent(false);
            this.getTile(row, col).setHasAgent(true);
            
            this.getTile(row, col).setHasVisited();
            
            return true;
        }
    }
    
    
    // Remove pony from the board
    public void removePony(Tile tile) {
        tile.setPony(false);
    }

    // Get the agents heading
    public String getHeading() {
        return heading;
    }

	public int getNUMBER_OF_TROLLS() {
		return NUMBER_OF_TROLLS;
	}

	public int getNUMBER_OF_PONIES() {
		return NUMBER_OF_PONIES;
	}
    
    public void drawBoard() {
        for (int row = getBoard().length - 1; row >= 0; row--) {
            
            // Print the top wall
            if(row == getBoard().length - 1) {
                this.drawTopWall();
            }

            // Print the left wall
            System.out.print("|");
            boardOut.print("|");

            for (int column = 0; column < getBoard().length; column++) {

                // Display the content of cell board
                if(column == getBoard().length - 1) {
                    // TODO make the rows display properly (4,0) becomes (0,0)
                    System.out.print(getBoard()[row][column]);
                    boardOut.print(getBoard()[row][column]);
                }
                else {
                    System.out.print(getBoard()[row][column] + " ");
                    boardOut.print(getBoard()[row][column] + " ");
                }
            }

            // Print the right wall
            System.out.print("|");
            boardOut.print("|");

            // Go to the next line to print either the bottom or middle wall
            System.out.println();
            boardOut.println();

            // Print the bottom wall
            if(row == 0) {
                this.drawBottomWall();
            }

            // Print the middle wall
            else {
                this.drawMiddleWall();
            }

            // Go to the next line to print pony, furniture, goal, home, or agent
            System.out.println();
            boardOut.println();
        }
    }
    
    
    // Draw the top wall
    public void drawTopWall() {
        for (int wall = 0; wall < this.getBoard().length; wall++) {
            System.out.print("+-");
            boardOut.print("+-");
        }
        System.out.print("+");
        boardOut.print("+");
        System.out.println();
        boardOut.println();
    }

    // Draw the bottom wall
    public void drawBottomWall() {
        for (int wall = 0; wall < this.getBoard().length; wall++) {
            System.out.print("+-");
            boardOut.print("+-");
        }
        System.out.print("+");
        boardOut.print("+");
    }

    // Draw the middle wall
    public void drawMiddleWall() {
        for (int column = 0; column < this.getBoard().length + 1; column++) {
            System.out.print("+ ");
            boardOut.print("+ ");
        }
    }
    
    public void refresh() {
        for (Tile t : map) {
            t.resetVisited();
            t.isHome = false;
            t.setHasAgent(false);
        }
    }
    
    public void setAgent(Agent a) {
        this.steve = a;
    }
    
    
}