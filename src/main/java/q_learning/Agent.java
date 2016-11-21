/**
 *  Filename: Agent.java
 *  Authors: Jesse Peplinski and Andrew Valancius
 *  Course: CIS 421 ­ Artificial Intelligence
 *  Assignment 5: Q-Learning
 *  First phase Due: Monday, 11/21/2016, 11:00 PM
 *  Second phase due: Wednesday, 12/7/2016, 11:00 PM
 */

package q_learning;

import java.util.Random;

public class Agent {
    public static String goVacuum = "VACUUM";
    public static String goForward = "MOVE FORWARD";
    public static String goLeft = "TURN LEFT";
    public static String goRight = "TURN RIGHT";
    public static String turnOff = "TURN OFF";

    /*
    getAction()
    params: PerceptVector
    returns: a string representing the action the agent wishes to make
    */
    public static String getAction(PerceptVector v) {
        if (v.ponyUnder == 1) return goVacuum;

        if (v.ponyForward == 1) return goForward;

        if (v.ponyRight == 1) return goRight;

        if (v.ponyLeft == 1) return goLeft;

        if (v.ponyBehind == 1) return goRight;

        if (v.goalUnder == 1) return turnOff;

        if (v.goalForward == 1) return goForward;

        if (v.goalRight == 1) return goRight;

        if (v.goalLeft == 1) return goLeft;

        if (v.goalBehind == 1) return goRight;

        Random r = new Random();
        int roll = r.nextInt(100);
        if (roll < 80 && v.touchSensorPushed == 0) return goForward;
        if (roll >= 80 && roll <93) return goRight;
        if (roll >= 93 ) return goLeft;

        /*
        if touch sensor is pressed, consider randomly turning

        - if I'm on a pony pile, vacuum it up
        - if there's a pony pile in front of me drive forward
        - if I'm adjacent to a pony pile turn towards it (if there are piles to left and right, randomly pick a direction)
        - if there's no pony anywhere and I'm not adjacent to the goal, move forward
        - if the goal is in front of me, move forward         
        - if I'm adjacent to the goal turn towards the goal
        - if I'm on the goal and I am not adjacent to any pony, turn off.

        */

        return goForward;
    }

}