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
    
    //class constants and variables
    Random rand;
    ArrayList<Tile> map;    // arraylist of tiles
    
    String heading;         // orientation of agent
    boolean sensorPressed;  // determines if sensor is pressed
    int roomSize;           // constant for the room size

    int NUMBER_OF_TROLLS;
    int NUMBER_OF_PONIES;

    // Create the initial envionment based on the input file
    // TODO - JESSE: Remove integer when function below works
    public Environment(List<Integer> integers, String fileName) {

        // TODO - JESSE: Create scanner and split each string on new line, or create multiple scanners on each line
        boolean changeToFile = true;

        this.rand = new Random();
        this.heading = "NORTH";

        if(changeToFile == true) {
           
            try {

                System.out.println("File name is "  + fileName);
                InputStream resourceStream = QLearning.class.getResourceAsStream("/" + fileName);

                Scanner fileScanner = new Scanner(resourceStream);

                String line;

                // Scan the first line
                line = fileScanner.nextLine();
                Scanner scan1 = new Scanner(line);
                if(scan1.hasNextInt()) {
                    // System.out.println("Goal is " + scan1.nextInt());
                    // System.out.println("Number of trolls is " + scan1.nextInt());
                    // System.out.println("Number of ponies is " + scan1.nextInt());
                    roomSize = scan1.nextInt(); // 5
                    NUMBER_OF_TROLLS = scan1.nextInt();
                    NUMBER_OF_PONIES = scan1.nextInt();
                }


                System.out.println("ROOM SIZE IS " + roomSize);

                this.map = new ArrayList<Tile>();

                // make a room full of empty floor tiles
                for (int row = 0; row < roomSize; row++) {
                    for (int col = 0; col < roomSize; col++) {
                        this.map.add(new Tile(row, col));
                    }
                }

                System.out.println("First line scanned");

                // Scan the second line
                line = fileScanner.nextLine();
                Scanner scan2 = new Scanner(line);
                if(scan2.hasNextInt()) {
                    // System.out.println("Goal (" + scan2.nextInt() + "), (" + scan2.nextInt() +")");
                    Tile t = this.getTile(scan2.nextInt(), scan2.nextInt());
                    t.setGoal(true);
                }

                // Scan the third line
                line = fileScanner.nextLine();
                Scanner scan3 = new Scanner(line);
                if(scan3.hasNextInt()) {
                    // System.out.println("Pony coordinates");
                    // System.out.println("(" + scan3.nextInt()  + "), (" + scan3.nextInt() + ")");
                    Tile t = this.getTile(scan3.nextInt(), scan3.nextInt());
                    t.setPony(true);

                }

                // Scan the fourth line
                line = fileScanner.nextLine();
                Scanner scan4 = new Scanner(line);
                if(scan4.hasNextInt()) {
                    System.out.println("Obstruction coordinates");
                    if(scan4.nextInt() == -1) {
                        System.out.println("No obstructions");
                    }
                    else {
                        // System.out.println("(" + scan4.nextInt()  + "), (" + scan4.nextInt() + ")");
                        Tile t = this.getTile(scan4.nextInt(), scan4.nextInt());
                        t.setFurniture(true);
                    }
                }

                // Scan the fifth line
                line = fileScanner.nextLine();
                Scanner scan5 = new Scanner(line);
                if(scan5.hasNextInt()) {
                    // System.out.println("Troll coordinates");
                    // System.out.println("(" + scan5.nextInt()  + "), (" + scan5.nextInt() + ")");
                    Tile t = this.getTile(scan5.nextInt(), scan5.nextInt());
                    t.setTroll(true);
                }

                fileScanner.close();

                // now we set the home and place the robot in the room at the home location.
                Tile agentHome = null;
                do {
                    agentHome = this.getTile(rand.nextInt(roomSize), rand.nextInt(roomSize));
                } while (!(agentHome.hasPony() == false && agentHome.hasTroll() == false && agentHome.hasFurniture() == false));

                agentHome.hasAgent = true;
                agentHome.setHasVisited();

                }

            catch(Exception e) {
                System.out.print(fileName + " is not found");
                e.printStackTrace();
            }
        }


        else  {
            this.rand = new Random();
            this.heading = "NORTH";
            roomSize = integers.get(0); // 5
            int PIECES_OF_FURNITURE = integers.get(1); // 2 
            int NUMBER_OF_PONIES = integers.get(2); // 2
            int NUMBER_OF_TROLLS = integers.get(3);

            // TODO - Do anything with error checking?
            // TODO - Change to number of trolls
            if(!(PIECES_OF_FURNITURE >= 1 && PIECES_OF_FURNITURE <= 3)) {
                System.err.println("Pieces of furniture is not between 1 and 3!");
            }

            if(!(NUMBER_OF_PONIES >= 1 && NUMBER_OF_PONIES <= 15)) {
                System.err.println("Number of ponies is not between 1 and 15!");
            }

            System.out.println("Line 1 Parsing: N value (room size) " + roomSize + ", Number of trolls (1-3): " + PIECES_OF_FURNITURE + ", Number of ponies (1-15): " + NUMBER_OF_PONIES);

            int goalCount = 0;
            int ponyCount = 0;
            int furnitureCount = 0; // TODO this will go away
            int trollCount = 0;

            this.map = new ArrayList<Tile>();

            // make a room full of empty floor tiles
            for (int row = 0; row < roomSize; row++) {
                for (int col = 0; col < roomSize; col++) {
                    this.map.add(new Tile(row, col));
                }
            }

            // now we need to put stuff in that room
            for (int i = 4; i < integers.size(); i+=2) {
                if(1 != goalCount) {
                    Tile t = this.getTile(integers.get(i+1), integers.get(i));
                    System.out.println("Line 2 parsing: Escape locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                    t.setGoal(true);
                    goalCount++;
                }

                // Place ponies on the tiles
                else if (NUMBER_OF_PONIES != ponyCount){
                    Tile t = this.getTile(integers.get(i+1), integers.get(i));
                    System.out.println("Line 3 parsing: Pony locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                    t.setPony(true);
                    ponyCount++;
                }

                // This needs to turn into obstructons, 
                // TODO: Parse this differently than original, we wont have the pieces of furniture variable delcared
                else if (PIECES_OF_FURNITURE != furnitureCount) {
                    if(integers.get(i+1) == -1 && integers.get(i) == -1) {
                        System.out.println("No obstructions!");
                    }
                    else {
                        Tile t = this.getTile(integers.get(i+1), integers.get(i));
                        System.out.println("Line 4 parsing: Obstruction locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                        t.setFurniture(true);
                        furnitureCount++;
                    }
                }

                else if(NUMBER_OF_TROLLS != trollCount) {
                    Tile t = this.getTile(integers.get(i+1), integers.get(i));
                    System.out.println("Line 5 parsing: Troll locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                    t.setTroll(true);
                    trollCount++;
                }

                else {
                    System.out.print("Nothing added to tile.");
                }
            }

            // now we set the home and place the robot in the room at the home location.
            Tile agentHome = null;
            do {
                agentHome = this.getTile(rand.nextInt(roomSize), rand.nextInt(roomSize));
            } while (!(agentHome.hasPony() == false && agentHome.hasTroll() == false && agentHome.hasFurniture() == false));
            agentHome.hasAgent = true;
            agentHome.setHasVisited();
        }
    }

    // get the current state of the board in an easily printable form
    public String[][] getBoard() {

        String[][] theBoard = new String[roomSize][roomSize];

        final String theAgent = "o";
        final String thePony = "P";
        final String theFurniture = "X";
        final String theGoal = "E";
        final String theTroll = "T";
        final String travelled = "$";

        for (Tile t : this.map) {

            if(t.hasAgent) {
                theBoard[t.getRow()][t.getCol()] = "o";
            }
            else if (t.hasFurniture()) {
                theBoard[t.getRow()][t.getCol()] = theFurniture;
            }
            else if (t.hasPony()) {
                theBoard[t.getRow()][t.getCol()] = thePony;
            }
            else if (t.isAtGoal()) {
                theBoard[t.getRow()][t.getCol()] = theGoal;
            }
            else if (t.hasTroll()) {
                theBoard[t.getRow()][t.getCol()] = theTroll;
            } else if (t.hasVisited()) {
                theBoard[t.getRow()][t.getCol()] = travelled;
            }
            else {
                theBoard[t.getRow()][t.getCol()] = " ";
            }
        }

        return theBoard;
    }


    // This is how we generate the Percept Vector at each timestep.
    public PerceptVector getPerceptVector() {
        return new PerceptVector(this, this.getAgentTile(), heading, sensorPressed);
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
            if (t.hasAgent == true) {
                return t;
            }
        }
        return null;
    }

    // Move agent to specified location.
    // returns false if the attempted move isn't allowed, meaning the bump sensor should be triggered
    public boolean moveAgent(int row, int col) {
        if (this.getTile(row, col) == null) {                       // out of bounds
            return false;
        } else if (this.getTile(row, col).hasFurniture == true) {   // furniture in the way
            return false;
        } else {
            this.getAgentTile().hasAgent = false;
            this.getTile(row, col).hasAgent = true;
            
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
}