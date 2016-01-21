package org.ingrahamrobotics.ftc2015.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DigitalChannel;

/**
 * Created by robotics on 1/20/2016.
 */
public class LiftPID extends OpMode {

    DcMotor liftMotor;
    DigitalChannel switchy;

    private final double LIFT_POWER = 1.0;
    private final int LIFT_DEAD_ZONE = 50;
    private final int LIFT_FULL_ERR = LIFT_DEAD_ZONE * 2;
    private final int LIFT_HEIGHT_ROBOT = 5200; //NEEDS TO BE CHANGED

    //To calibrate the lift height high
    private int calibration = 0;
    private int offset = 0;
    private final int LIFT_HEIGHT_CHANGE = 150;

    private int LIFT_RESET_TIMEOUT = 4;
    private int LIFT_RESET_OFFSET = 25;
    private double LIFT_RESET_SPEED = 0.2;

    private boolean liftReady = false;
    private boolean liftAtTarget = false;
    private int lastLiftErr = 0;

    public LiftPID(DcMotor motor, DigitalChannel swag_swit) {
        liftMotor = motor;
        switchy = swag_swit;
    }

    public void init() {
        stopLift();
        liftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void driveLift(double power) {
        liftMotor.setPower(power);
        liftMotor.setTargetPosition(0);
    }

    public void stopLift() {
        driveLift(0);
    }

    public void incrLiftHeightHigh() {
        calibration += LIFT_HEIGHT_CHANGE + lastLiftErr;
    }

    public void decrLiftHeightHigh() {
        calibration -= LIFT_HEIGHT_CHANGE - lastLiftErr;
    }

    public boolean readLiftTouch() {
        return switchy.getState();
    }

    public void resetLiftEncoder() {
        stopLift();
        TimerWait.wait1Msec(100);
        offset = liftMotor.getCurrentPosition();
    }

    public int readLiftEncoder() {
        return liftMotor.getCurrentPosition();
    }

    public boolean isLiftAboveRobot() {
        return (readLiftEncoder() > LIFT_HEIGHT_ROBOT);
    }

    public boolean isLiftReady() {
        return liftReady;
    }

    public void loop() {/*
        //For fail-safe code, may be inverted if needed
        int liftSpeed = LIFT_SPEED;

        // Track when we disable teleop drive
        bool driveStopped = false;

        // Run forever
        while (true) {

            #ifdef LIFT_DEBUG
            nxtDisplayBigTextLine(1, "%d %d", (int)liftCmd, readLiftEncoder());
            #endif

            // When reset is commanded ignore everything else until we are ready
            if (liftCmd == RESET && liftSpeed != 0) {

                // Try to get the lift down. Give if it doesn't happen promptly.
                ClearTimer(T1);
                while (!readLiftTouch()) {
                    #ifdef LIFT_DEBUG
                    nxtDisplayBigTextLine(1, "Reset %d", readLiftTouch());
                    #endif
                    driveLift(-liftSpeed);
                    if(time1[T1] > 1000 * LIFT_RESET_TIMEOUT) {
                        liftSpeed = 0;
                    }
                }
                resetLiftEncoder();

                // If the lift is down, nudge it up to find the switch release point
                while (readLiftTouch()) {
                    driveLift(liftSpeed * LIFT_RESET_SPEED);
                }
                resetLiftEncoder(LIFT_RESET_OFFSET);

                // Announce our completion status (or keep the RESET status if we failed)
                if (liftSpeed != 0) {
                    liftCmd = COLLECT;
                    liftReady = true;
                    #ifdef LIFT_DEBUG
                    nxtDisplayBigTextLine(1, "Ready");
                    #endif
                } else {
                    liftCmd = RESET;
                    liftReady = false;
                    #ifdef LIFT_DEBUG
                    nxtDisplayBigTextLine(1, "Reset Failed");
                    wait1Msec(1000 * LIFT_RESET_TIMEOUT);
                    #endif
                }

                // Jump back to the top of the loop once the lift is ready
                continue;
            }

            // Determine the height we'd like the lift to be at
            int liftCmdHeight = 0;
            switch (liftCmd) {
                case COLLECT:
                    liftCmdHeight = LIFT_HEIGHT_COLLECT;
                    break;

                case DRIVE:
                    liftCmdHeight = LIFT_HEIGHT_DRIVE;
                    break;

                case LOW:
                    liftCmdHeight = LIFT_HEIGHT_LOW;
                    break;

                case MED:
                    liftCmdHeight = LIFT_HEIGHT_MED;
                    break;

                case HIGH:
                    liftCmdHeight = LIFT_HEIGHT_HIGH + calibration;
                    break;

                case CENTER:
                    liftCmdHeight = LIFT_HEIGHT_CENTER;
                    break;

                case RESET:
                    break;

                // If we get here something bad happened
                // Stop the lift and exit the task
                default:
                    liftFatalErr();
                    // liftFatalErr should never return, but for clarity:
                    break;
            }

            // Calculate the difference between the current lift height and the desired lift height
            int liftErr = liftCmdHeight - readLiftEncoder();
            int liftErrAbs = abs(liftErr);

            // In theory we can do this and let the motor controller deal with the details
            #ifdef LIFT_REMOTE_PID
            if (liftErrAbs >= LIFT_DEAD_ZONE) {
                driveLiftTarget(liftSpeed, liftCmdHeight);
            }

            // Acknowledge remote PID completion
            if (liftTargetState() == runStateIdle) {
                stopLift();
            }

            // But in case that does not work
            #else

            // Calculate a lift speed based on the magnitude of the lift height error
            int driveSpeed = 0;
            if (liftErrAbs < LIFT_DEAD_ZONE) {
                // No motion when we're in the dead zone
                driveSpeed = 0;
            } else if (liftErrAbs >= LIFT_FULL_ERR) {
                // Full speed if we're more than LIFT_FULL_ERR away from the target
                driveSpeed = liftSpeed;
            } else {
                // More slowly as we approach the target
                // This is the "P" of PID. We may also need an "I".
                driveSpeed = ((LIFT_FULL_ERR - liftErrAbs) * driveSpeed) / LIFT_FULL_ERR;
            }

            // Invert the lift speed if the lift should move down
            if (liftErr < 0) {
                driveSpeed *= -1;
            }

            // Drive the lift and loop
            driveLift(driveSpeed);

            #endif

            // Note when we're on-target for outside observers
            if (liftErrAbs < LIFT_DEAD_ZONE) {
                liftAtTarget = true;
                lastLiftErr = liftErr;
            } else {
                liftAtTarget = false;
            }

            //Start/Stop DriveMec based on if it's safe with the lift
            if(isLiftAboveRobot()) {
                driveStopped = true;
                if(liftPeriod == TELEOP) {
                    StopTask(DriveMec);
                    stopDriveMotors();
                }
            } else if (driveStopped) {
                driveStopped = false;
                #ifdef FTC_DRIVETASK
                if(liftPeriod == TELEOP) {
                    StartTask(DriveMec);
                }
                #endif
            }

            // Surrender some time to other tasks
            EndTimeSlice();
        }
    */}
}
