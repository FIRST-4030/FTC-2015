package org.ingrahamrobotics.ftc2015.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ingrahamrobotics.ftc2015.autonomous.AutoSensors;
import org.ingrahamrobotics.ftc2015.autonomous.DriveToParking;
import org.ingrahamrobotics.ftc2015.drive.DriveParameters;
import org.ingrahamrobotics.ftc2015.sensors.Compass;

/**
 * Created by robotics on 11/6/2015.
 */
public class MotorCommands {

    DcMotor leftMotor;
    DcMotor rightMotor;
    Compass compass;
    private boolean isFinished;
    private boolean compassReady;
    public static final int TICKS_PER_INCH = 250;

    public MotorCommands(DcMotor left, DcMotor right) {
        leftMotor = left;
        rightMotor = right;
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        isFinished = true;
        compass = new Compass();
        compass.start();
    }

    /*
    public void setMotorMode(DcMotorController.RunMode m) {
        leftMotor.setMode(m);
        rightMotor.setMode(m);
    }
*/

    public void setMotorPower(float power) {
        isFinished = false;
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        isFinished = true;
    }

    //Helper method for the drive loop
    private void setMotorPowerH(float power, double turn, boolean right) {
        float lPower = power;
        float rPower = power;
        //Sets motors to go opposite directions when turning
        if(turn != 0) {
            if(right) {
                rPower = -rPower;
            } else {
                lPower = -lPower;
            }
        }
        leftMotor.setPower(lPower);
        rightMotor.setPower(rPower);
    }

    /*
    //Returns false if there's an error with inputs
    public boolean drive(DriveParameters parameters) {
        isFinished = false;
        //Get the parameters to check
        float power = parameters.getPower();
        int distance = parameters.getDistance();
        //Safety checks
        if(power < -1 || power > 1 || distance < 0) {
            isFinished = true;
            return false;
        }
        long startTime = System.currentTimeMillis();
        while(!isFinished) {
            driveLoop(parameters, startTime);
        }
        return true;
    }
    */

    //Returns false if there's an error
    //MAY NEED TO ADD A HEADING PARAMETER//
    public boolean driveLoop(DriveParameters parameters, long startTime, int position) {
        //Makes sure that the compass is ready before driving, as it will be needed
        if(!compassReady) {
            if (compass.ready()) {
                compass.reset();
                compassReady = true;
            }
            else {
                //return false;
            }
        }
        isFinished = false;
        //Get variables for use
        float power = parameters.getPower();
        int distance = parameters.getDistance();
        int goalPosition = (int) (power / Math.abs(power) * distance) + position;
        double turnAngle = parameters.getTurnAngle();
        boolean isToRight = parameters.isToRight();
        setMotorPowerH(power, turnAngle, isToRight);
        //Conditions to stop
        if(power == 0) {
            isFinished = true;
        } else if((System.currentTimeMillis() - startTime) / 1000.0 > 30) {
            finish();
            return false;
        } else if(distance != DriveParameters.DEF_DISTANCE) {
            if (power > 0 && leftMotor.getCurrentPosition() >= goalPosition) {
                finish();
            } else if (power < 0 && leftMotor.getCurrentPosition() <= goalPosition) {
                finish();
            }
        } else if(turnAngle != DriveParameters.DEF_ANGLE) {
            int turn = Math.abs(compass.relativeHeading());
            if(turn <= turnAngle + 5 && turn >= turnAngle - 5) {
                finish();
            }
        } else {
            isFinished = false;
        }
        return true;
    }


    private void finish() {
        stopDriveMotors();
    }

    public void stopDriveMotors() {
        isFinished = false;
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        isFinished = true;
    }

    public void resetCompass() {
        compass.reset();
    }

    public DriveParameters genDriveToDistance(float power, int distance) {
        return new DriveParameters(power, distance, DriveParameters.DEF_ANGLE, false);
    }

    public DriveParameters genTurnToAngle(float power, double angle, boolean isRight) {
        return new DriveParameters(Math.abs(power), DriveParameters.DEF_DISTANCE, angle, isRight);
    }

    public boolean isFinished() {
        return isFinished;
    }

/*
    public void setMotorTurn(float power, boolean left) {
        if (left) {
            leftMotor.setPower(-power);
            rightMotor.setPower(power);
        } else {
            leftMotor.setPower(power);
            rightMotor.setPower(-power);
        }
    }
    */

    /*
    public void driveToSonar(int d, float power) {
        while (sonar.getValue() > d) {
            setMotorPower(power);
        }
        stopDriveMotors();
    }
    */

    public void turnToAngle() {

    }
}
