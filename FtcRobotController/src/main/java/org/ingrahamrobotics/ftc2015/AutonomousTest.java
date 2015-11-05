package org.ingrahamrobotics.ftc2015;

/**
 * Created by robotics on 11/4/2015.
 */
public class AutonomousTest {

    public void init() {

    }

    /**
     * The following pseudo code is written with the following assumptions:
     * int x = 5500/42 * 12; (the encoder distance equal to 1 foot)
     * int y = the ultrasound value needed to drive to
     * driveToDistance(int d, double sp) is a method that goes forward and backwards, driving wheel motors to d, an encoder value, at a speed of sp
     * turnToAngle(double a, boolean left) is a method that turns to a specific angle, left if true, right if false
     * driveToUltrasound(int d, double sp) is a method that drives to (at or less than) an ultrasound value d, at a speed of sp
     * public void autoRed(double longDrive) {
     *     driveToDistance(2 * x, 1.0);
     *     turnToAngle(45, true);
     *     driveToDistance(longDrive * x, 1.0);
     *     turnToAngle(45, true);
     *     driveToUltrasound(y, 0.5);
     *     dumpRoutine();
     *     driveToDistance(-3 * x, 1.0);
     *     turnToAngle(45, true);
     *     driveToDistance(4.4 * x, 1.0);
     *     //ascend ramp
     * }
     *
     * public void autoRedClose() {
     *     autoRed(6.2);
     * }
     *
     * public void autoRedFar() {
     *     autoRed(7.9);
     * }
     *
     *
     */
}
