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
        if (v.dirtUnder == 1) return goVacuum;

        if (v.dirtForward == 1) return goForward;

        if (v.dirtRight == 1) return goRight;

        if (v.dirtLeft == 1) return goLeft;

        if (v.dirtBehind == 1) return goRight;

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

        - if I'm on a dirt pile, vacuum it up
        - if there's a dirt pile in front of me drive forward
        - if I'm adjacent to a dirt pile turn towards it (if there are piles to left and right, randomly pick a direction)
        - if there's no dirt anywhere and I'm not adjacent to the goal, move forward
        - if the goal is in front of me, move forward         
        - if I'm adjacent to the goal turn towards the goal
        - if I'm on the goal and I am not adjacent to any dirt, turn off.

        */

        return goForward;
    }

}