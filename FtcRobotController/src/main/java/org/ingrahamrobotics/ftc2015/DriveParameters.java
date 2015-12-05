package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.util.Range;

/**
 * Created by robotics on 11/4/2015.
 */
public class DriveParameters {

    //these are the default values
    public static final int DEF_DISTANCE = 0;

    private float myPower;
    private int myDistance;

    //these are hypothetical
    private double gyroAngle;
    private int light;
    private int sonar;

    public DriveParameters(float power, int distance) {
        myPower = (float) Range.clip(power, -1, 1);
        if(distance < 0) {
            distance = DEF_DISTANCE;
        }
        myDistance = distance;
    }

    public float getPower() {
        return myPower;
    }

    public int getDistance() {
        return myDistance;
    }

    public void setPower(float power) {
        myPower = power;
    }

    public void setDistance(int distance) { myDistance = distance; }
}
