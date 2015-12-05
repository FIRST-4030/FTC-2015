package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by robotics on 11/6/2015.
 */
public class MotorCommands {

    DcMotor leftMotor;
    DcMotor rightMotor;

    public MotorCommands(DcMotor left, DcMotor right) {
        leftMotor = left;
        rightMotor = right;
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    /*
    public void setMotorMode(DcMotorController.RunMode m) {
        leftMotor.setMode(m);
        rightMotor.setMode(m);
    }
*/

    public void setMotorPower(float power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public boolean drive(DriveParameters parameters) {
        //Get the parameters to check
        float power = parameters.getPower();
        int distance = parameters.getDistance();
        //Safety checks
        if(power < -1 || power > 1 || distance < 0) {
            return false;
        }
        long startTime = System.currentTimeMillis();

        //True if we got to the end of the method
        return true;
    }

    //Returns true when the loop is done
    public boolean driveLoop(DriveParameters parameters, long startTime) {
        //Get variables for use
        float power = parameters.getPower();
        int distance = parameters.getDistance();
        int goalPosition = (int) (power / Math.abs(power) * distance) + leftMotor.getCurrentPosition();
        //Conditions to stop
        if(power == 0) {
            return true;
        }
        setMotorPower(power);
        if((System.currentTimeMillis() - startTime) / 1000.0 > 30) {
            return true;
        }
        if(distance != DriveParameters.DEF_DISTANCE) {
            if (power > 0 && leftMotor.getCurrentPosition() >= goalPosition) {
                return true;
            } else if (power < 0 && leftMotor.getCurrentPosition() <= goalPosition) {
                return true;
            }
        }
        //insert other stuff here
        return false;
    }

    public void stopDriveMotors() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    public void driveToDistance(float power, int distance) {
        drive(new DriveParameters(power, distance));
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
