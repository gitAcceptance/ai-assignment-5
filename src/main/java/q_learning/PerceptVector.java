/**
 *  Filename: PerceptVctor.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment: 1
 *  Due: 9/19/2016, 11:00 PM
 */

public class PerceptVector {
    
    // define the vector values
    public int touchSensorPushed = 0;
    public int dirtUnder = 0;
    public int dirtForward = 0;
    public int dirtBehind = 0;
    public int dirtLeft = 0;
    public int dirtRight = 0;
    public int goalUnder = 0;
    public int goalForward = 0;
    public int goalBehind = 0;
    public int goalLeft = 0;
    public int goalRight = 0;

    PerceptVector(Environment env, Tile currentPos, String heading, boolean bumpedWall){
        if (bumpedWall) {
            touchSensorPushed = 1;
        }

        // find out if we have dirt under
        if (currentPos.hasDirt == true) dirtUnder = 1;

        // find out if we have dirt around
        if (heading == "NORTH") {
            // checking for dirt ahead
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasDirt == true) {
                    dirtForward = 1;
                }
            }
            // checking for dirt behind
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasDirt == true) {
                    dirtBehind = 1;
                }
            }
            // checking for dirt left
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasDirt == true) {
                    dirtLeft = 1;
                }
            }
            // checking for dirt right
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasDirt == true) {
                    dirtRight = 1;
                }
            }
        } else if (heading == "SOUTH") {
            // checking for dirt ahead
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasDirt == true) {
                    dirtForward = 1;
                }
            }
            // checking for dirt behind
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasDirt == true) {
                    dirtBehind = 1;
                }
            }
            // checking for dirt left
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasDirt == true) {
                    dirtLeft = 1;
                }
            }
            // checking for dirt right
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasDirt == true) {
                    dirtRight = 1;
                }
            }
        } else if (heading == "EAST") {
            // checking for dirt ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasDirt == true) {
                    dirtForward = 1;
                }
            }
            // checking for dirt behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasDirt == true) {
                    dirtBehind = 1;
                }
            }
            // checking for dirt left
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasDirt == true) {
                    dirtLeft = 1;
                }
            }
            // checking for dirt right
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasDirt == true) {
                    dirtRight = 1;
                }
            }
        } else if (heading == "WEST") {
            // checking for dirt ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasDirt == true) {
                    dirtForward = 1;
                }
            }
            // checking for dirt behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasDirt == true) {
                    dirtBehind = 1;
                }
            }
            // checking for dirt left
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasDirt == true) {
                    dirtLeft = 1;
                }
            }
            // checking for dirt right
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasDirt == true) {
                    dirtRight = 1;
                }
            }
        }

        // dont forget about goal underneath
        if (currentPos.isGoal == true) goalUnder = 1;


        // now we check for goals
        if (heading == "NORTH") {
            // checking for goal ahead
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).isGoal == true) {
                    goalForward = 1;
                }
            }
            // checking for goal behind
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).isGoal == true) {
                    goalBehind = 1;
                }
            }
            // checking for goal left
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).isGoal == true) {
                    goalLeft = 1;
                }
            }
            // checking for goal right
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).isGoal == true) {
                    goalRight = 1;
                }
            }
        } else if (heading == "SOUTH") {
            // checking for goal ahead
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).isGoal == true) {
                    goalForward = 1;
                }
            }
            // checking for goal behind
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).isGoal == true) {
                    goalBehind = 1;
                }
            }
            // checking for goal left
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).isGoal == true) {
                    goalLeft = 1;
                }
            }
            // checking for goal right
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).isGoal == true) {
                    goalRight = 1;
                }
            }
        } else if (heading == "EAST") {
            // checking for dirt ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).isGoal == true) {
                    goalForward = 1;
                }
            }
            // checking for dirt behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).isGoal == true) {
                    goalBehind = 1;
                }
            }
            // checking for dirt left
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).isGoal == true) {
                    goalLeft = 1;
                }
            }
            // checking for dirt right
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).isGoal == true) {
                    goalRight = 1;
                }
            }
        } else if (heading == "WEST") {
            // checking for dirt ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).isGoal == true) {
                    goalForward = 1;
                }
            }
            // checking for dirt behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).isGoal == true) {
                    goalBehind = 1;
                }
            }
            // checking for dirt left
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).isGoal == true) {
                    goalLeft = 1;
                }
            }
            // checking for dirt right
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).isGoal == true) {
                    goalRight = 1;
                }
            }
        }
    }

    // Convert the vector to a string value for printing
    public String toString() {
        return "<"+touchSensorPushed+", "+dirtUnder+", "+dirtForward+", "+dirtBehind+", "+dirtLeft+", "+dirtRight+", "+goalUnder+", "+goalForward+", "+goalBehind+", "+goalLeft+", "+goalRight+">";
    }

}