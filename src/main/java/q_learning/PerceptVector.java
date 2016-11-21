/**
 *  Filename: PerceptVector.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

public class PerceptVector {
    
    // define the vector values
    public int touchSensorPushed = 0;
    public int ponyUnder = 0;
    public int ponyForward = 0;
    public int ponyBehind = 0;
    public int ponyLeft = 0;
    public int ponyRight = 0;
    public int goalUnder = 0;
    public int goalForward = 0;
    public int goalBehind = 0;
    public int goalLeft = 0;
    public int goalRight = 0;

    PerceptVector(Environment env, Tile currentPos, String heading, boolean bumpedWall){
        if (bumpedWall) {
            touchSensorPushed = 1;
        }

        // find out if we have pony under
        if (currentPos.hasPony == true) ponyUnder = 1;

        // find out if we have pony around
        if (heading == "NORTH") {
            // checking for pony ahead
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasPony == true) {
                    ponyForward = 1;
                }
            }
            // checking for pony behind
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasPony == true) {
                    ponyBehind = 1;
                }
            }
            // checking for pony left
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasPony == true) {
                    ponyLeft = 1;
                }
            }
            // checking for pony right
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasPony == true) {
                    ponyRight = 1;
                }
            }
        } else if (heading == "SOUTH") {
            // checking for pony ahead
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasPony == true) {
                    ponyForward = 1;
                }
            }
            // checking for pony behind
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasPony == true) {
                    ponyBehind = 1;
                }
            }
            // checking for pony left
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasPony == true) {
                    ponyLeft = 1;
                }
            }
            // checking for pony right
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasPony == true) {
                    ponyRight = 1;
                }
            }
        } else if (heading == "EAST") {
            // checking for pony ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasPony == true) {
                    ponyForward = 1;
                }
            }
            // checking for pony behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasPony == true) {
                    ponyBehind = 1;
                }
            }
            // checking for pony left
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasPony == true) {
                    ponyLeft = 1;
                }
            }
            // checking for pony right
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasPony == true) {
                    ponyRight = 1;
                }
            }
        } else if (heading == "WEST") {
            // checking for pony ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).hasPony == true) {
                    ponyForward = 1;
                }
            }
            // checking for pony behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).hasPony == true) {
                    ponyBehind = 1;
                }
            }
            // checking for pony left
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).hasPony == true) {
                    ponyLeft = 1;
                }
            }
            // checking for pony right
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).hasPony == true) {
                    ponyRight = 1;
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
            // checking for pony ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).isGoal == true) {
                    goalForward = 1;
                }
            }
            // checking for pony behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).isGoal == true) {
                    goalBehind = 1;
                }
            }
            // checking for pony left
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).isGoal == true) {
                    goalLeft = 1;
                }
            }
            // checking for pony right
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).isGoal == true) {
                    goalRight = 1;
                }
            }
        } else if (heading == "WEST") {
            // checking for pony ahead
            if (env.getTile(currentPos.getRow(), currentPos.getCol()-1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()-1).isGoal == true) {
                    goalForward = 1;
                }
            }
            // checking for pony behind
            if (env.getTile(currentPos.getRow(), currentPos.getCol()+1) != null) {
                if (env.getTile(currentPos.getRow(), currentPos.getCol()+1).isGoal == true) {
                    goalBehind = 1;
                }
            }
            // checking for pony left
            if (env.getTile(currentPos.getRow()-1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()-1, currentPos.getCol()).isGoal == true) {
                    goalLeft = 1;
                }
            }
            // checking for pony right
            if (env.getTile(currentPos.getRow()+1, currentPos.getCol()) != null) {
                if (env.getTile(currentPos.getRow()+1, currentPos.getCol()).isGoal == true) {
                    goalRight = 1;
                }
            }
        }
    }

    // Convert the vector to a string value for printing
    public String toString() {
        return "<"+touchSensorPushed+", "+ponyUnder+", "+ponyForward+", "+ponyBehind+", "+ponyLeft+", "+ponyRight+", "+goalUnder+", "+goalForward+", "+goalBehind+", "+goalLeft+", "+goalRight+">";
    }

}