package org.ingrahamrobotics.ftc2015.drive;

/**
 * Created by robotics on 1/20/2016.
 */
public class TimerWait {

    private static long startTime;

    public static void wait1Msec(int mSec) {
        startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < mSec) {
            continue;
        }
    }
}
