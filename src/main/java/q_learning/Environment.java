/**
 *  Filename: Environment.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

import java.util.*;

public class Environment {
    
    //class constants and variables
    ArrayList<Tile> map;    // arraylist of tiles
    String heading;         // orientation of agent
    boolean sensorPressed;  // determines if sensor is pressed
    int roomSize;           // constant for the room size

    // Create the initial envionment based on the input file
    public Environment(List<Integer> integers) {
        this.heading = "NORTH";
        roomSize = integers.get(0); // 5
        int PIECES_OF_FURNITURE = integers.get(1); // 2 
        int NUMBER_OF_PONIES = integers.get(2); // 2

        if(!(PIECES_OF_FURNITURE >= 1 && PIECES_OF_FURNITURE <= 3)) {
            System.err.println("Pieces of furniture is not between 1 and 3!");
        }

        if(!(NUMBER_OF_PONIES >= 1 && NUMBER_OF_PONIES <= 15)) {
            System.err.println("Number of ponies is not between 1 and 15!");
        }

        System.out.println("Line 1 Parsing: N value (room size) " + roomSize + ", Number of trolls (1-3): " + PIECES_OF_FURNITURE + ", Number of ponies (1-15): " + NUMBER_OF_PONIES);

        int goalCount = 0;
        int ponyCount = 0;
        int furnitureCount = 0;
        int trollCount = 0;

        this.map = new ArrayList<Tile>();

        // make a room full of empty floor tiles
        for (int row = 0; row < roomSize; row++) {
            for (int col = 0; col < roomSize; col++) {
                this.map.add(new Tile(row, col));
            }
        }

        // now we need to put stuff in that room
        for (int i = 3; i < integers.size(); i+=2) {
            if(1 != goalCount) {
                Tile t = this.getTile(integers.get(i+1), integers.get(i));
                System.out.println("Line 2 parsing: Escape locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                t.setGoal(true);
                goalCount++;
            }

            // This needs to turn into pony locations
            else if (NUMBER_OF_PONIES != ponyCount){
                Tile t = this.getTile(integers.get(i+1), integers.get(i));
                System.out.println("Line 3 parsing: Pony locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                t.setPony(true);
                ponyCount++;
            }

            // This needs to turn into obstructons
            else if (PIECES_OF_FURNITURE != furnitureCount) {
                Tile t = this.getTile(integers.get(i+1), integers.get(i));
                System.out.println("Line 4 parsing: Obstruction locaton : (" + integers.get(i+1) + "), (" + integers.get(i) + ")");
                t.setFurniture(true);
                furnitureCount++;
            }

            // ELSE IF(NUMBER OF TROLLS != trollCount)
                // Do the same stuff as above
            else {
                System.out.print("Nothing added to tile.");
            }
        }

        // now we set the home and place the robot in the room at the home location.
        this.getTile(0, 0).isHome = true;
        this.getTile(0, 0).hasAgent = true;
    }

    // get the current state of the board in an easily printable form
    public String[][] getBoard() {

        String[][] theBoard = new String[roomSize][roomSize];

        final String theAgent = "o";
        final String thePony = "P";
        final String theFurniture = "X";
        final String theHome = "H";
        final String theGoal = "E";

        for (Tile t : this.map) {

            if(t.hasAgent) {
                if (this.heading == "NORTH") {
                    theBoard[t.getRow()][t.getCol()] = "^";
                } else if (this.heading == "SOUTH") {
                    theBoard[t.getRow()][t.getCol()] = "v";
                } else if (this.heading == "EAST") {
                    theBoard[t.getRow()][t.getCol()] = ">";
                } else if (this.heading == "WEST") {
                    theBoard[t.getRow()][t.getCol()] = "<";
                } else {
                    theBoard[t.getRow()][t.getCol()] = "o";
                }
                
            }
            else if(t.isHome) {
                theBoard[t.getRow()][t.getCol()] = theHome;
            }
            else if (t.getFurniture()) {
                theBoard[t.getRow()][t.getCol()] = theFurniture;
            }
            else if (t.getPony()) {
                theBoard[t.getRow()][t.getCol()] = thePony;
            }
            else if (t.getGoal()) {
                theBoard[t.getRow()][t.getCol()] = theGoal;
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
            return true;
        }
    }

    // Move the agent forward on the board depending on the heading
    public void moveAgentForward() {
        this.sensorPressed = false;
        if (this.heading == "NORTH") {
            boolean result = this.moveAgent(this.getAgentTile().getRow()+1, this.getAgentTile().getCol());
            if (result == false) {
                this.sensorPressed = true;
            }
        } else if (this.heading == "SOUTH") {
            boolean result = this.moveAgent(this.getAgentTile().getRow()-1, this.getAgentTile().getCol());
            if (result == false) {
                this.sensorPressed = true;
            }
        } else if (this.heading == "EAST") {
            boolean result = this.moveAgent(this.getAgentTile().getRow(), this.getAgentTile().getCol()+1);
            if (result == false) {
                this.sensorPressed = true;
            }
        } else if (this.heading == "WEST") {
            boolean result = this.moveAgent(this.getAgentTile().getRow(), this.getAgentTile().getCol()-1);
            if (result == false) {
                this.sensorPressed = true;
            }
        }
    }

    // Change the orientation of the agent
    public void turnAgentRight() {
        if (heading == "NORTH") {
            heading = "EAST";
        } else if (heading == "SOUTH") {
            heading = "WEST";
        } else if (heading == "EAST") {
            heading = "SOUTH";
        } else if (heading == "WEST") {
            heading = "NORTH";
        }
    }

    // Change the orientation of the agent
    public void turnAgentLeft() {
        if (heading == "NORTH") {
            heading = "WEST";
        } else if (heading == "SOUTH") {
            heading = "EAST";
        } else if (heading == "EAST") {
            heading = "NORTH";
        } else if (heading == "WEST") {
            heading = "SOUTH";
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