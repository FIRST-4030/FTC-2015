package org.ingrahamrobotics.ftc2015;

/**
 * Created by robotics on 11/4/2015.
 */
public class DriveParameters {

    public static final int DEF_DISTANCE = 0;

    private float myPower;
    private int myDistance;

    //these are hypothetical
    private double gyroAngle;
    private int light;
    private int sonar;

    public DriveParameters(float power, int distance) {
        myPower = power;
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

    public void setDistance(int distance) {
        myDistance = distance;
    }
}
