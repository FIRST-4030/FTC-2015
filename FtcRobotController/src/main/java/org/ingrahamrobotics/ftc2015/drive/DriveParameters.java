package org.ingrahamrobotics.ftc2015.drive;

import com.qualcomm.robotcore.util.Range;

/**
 * Created by robotics on 11/4/2015.
 */
public class DriveParameters {

    //these are the default values
    public static final int DEF_DISTANCE = 0;
    public static final int DEF_ANGLE = 0;

    private float myPower;
    private int myDistance;
    private double myTurnAngle;
    private boolean isRight;

    //these are hypothetical
    private int light;
    private int sonar;

    public DriveParameters(float power, int distance, double turn, boolean right) {
        myPower = (float) Range.clip(power, -1, 1);
        if(distance < 0) {
            distance = DEF_DISTANCE;
        }
        myDistance = distance;
        if(turn < 0) {
            turn = 0;
        } else if(turn > 360) {
            turn = turn - ((turn % 360) * 360);
        }
        myTurnAngle = turn;
        isRight = right;
    }

    public float getPower() {
        return myPower;
    }

    public int getDistance() {
        return myDistance;
    }

    public double getTurnAngle() {
        return myTurnAngle;
    }

    public boolean isToRight() {
        return isRight;
    }

    public void setPower(float power) {
        myPower = power;
    }

    public void setDistance(int distance) { myDistance = distance; }

    public void setTurnAngle(double turn) {
        myTurnAngle = turn;
    }
}
