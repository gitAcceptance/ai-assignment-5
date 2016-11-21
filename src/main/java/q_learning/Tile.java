/**
 *  Filename: Tile.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

public class Tile {

    // class constants and variables
    public int row;
    public int col;
    public boolean hasFurniture = false;
    public boolean hasPony = false;
    public boolean isGoal = false;
    public boolean isHome = false;
    public boolean hasAgent = false;
    public boolean hasTroll = false;
    public boolean hasVisited = false;

    // Parameter: row - the row number of the grid
    //            column - the column number of the grid              
    // Precondition: row / col positive
    // Returns: N/A
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Parameter: N/A             
    // Precondition: N/A
    // Returns: the row number of the grid
    public int getRow() {
        return row;
    }

    // Parameter: N/A             
    // Precondition: N/A
    // Returns: the column number of the grid
    public int getCol() {
        return col;
    }

    // Parameter: tileHasFurniture - t/f for determining if tile has furniture on it         
    // Precondition: N/A
    // Returns: N/A
    public void setFurniture(boolean tileHasFurniture) {
        hasFurniture = tileHasFurniture;
    }

    // Parameter: N/A             
    // Precondition: N/A
    // Returns: the boolean value of the furniture
    public boolean getFurniture() {
        return hasFurniture;
    }

    // Parameter: tileHasPony - t/f for determining if tile has pony on it         
    // Precondition: N/A
    // Returns: N/A
    public void setPony(boolean tileHasPony) {
        hasPony = tileHasPony;
    }

    // Parameter: N/A             
    // Precondition: N/A
    // Returns: the boolean value of the pony
    public boolean getPony() {
        return hasPony;
    }
    
    public boolean hasVisited() {
        return hasVisited;
    }

    public void setHasVisited() {
        this.hasVisited = true;
    }
    
    

    public void setTroll(boolean tileHasTroll) {
        hasTroll = tileHasTroll;
    }

    public boolean getTroll() {
        return hasTroll;
    }

    // Parameter: tileHasFurniture - t/f for determining if tile is the goal      
    // Precondition: N/A
    // Returns: N/A
    public void setGoal(boolean tileIsGoal) {
        isGoal = tileIsGoal;
    }

    // Parameter: N/A             
    // Precondition: N/A
    // Returns: the boolean value of the goal
    public boolean getGoal() {
        return isGoal;
    }
}