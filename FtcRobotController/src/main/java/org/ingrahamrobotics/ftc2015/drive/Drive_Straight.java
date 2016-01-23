package org.ingrahamrobotics.ftc2015.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by robotics on 1/22/2016.
 */
public class Drive_Straight extends OpMode {

    private final int DEADZONE = 0;
    private final int MAX_ERROR = 0;

    private int initHeading;
    private int headingErr;
    private int headingErrAbs;


    @Override
    public void init() {
        initHeading = 0;
    }

    @Override
    public void loop() {
        //Calculate error in movement
        int heading = 0; //getHeading();
        headingErr = initHeading - heading;
        headingErrAbs = Math.abs(headingErr);

        if(headingErrAbs < DEADZONE) {
            //no change
        } else if(headingErrAbs >= MAX_ERROR) {
            //set one motor to 0 and the other full power
        } else {

        }
    }
}
