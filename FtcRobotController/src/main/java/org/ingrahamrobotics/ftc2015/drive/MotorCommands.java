package org.ingrahamrobotics.ftc2015.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ingrahamrobotics.ftc2015.drive.DriveParameters;

/**
 * Created by robotics on 11/6/2015.
 */
public class MotorCommands {

    DcMotor leftMotor;
    DcMotor rightMotor;
    private boolean isFinished;

    public MotorCommands(DcMotor left, DcMotor right) {
        leftMotor = left;
        rightMotor = right;
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        isFinished = true;
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
    private void setMotorPowerH(float power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
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
    public boolean driveLoop(DriveParameters parameters, long startTime) {
        isFinished = false;
        //Get variables for use
        float power = parameters.getPower();
        int distance = parameters.getDistance();
        int goalPosition = (int) (power / Math.abs(power) * distance) + leftMotor.getCurrentPosition();
        setMotorPowerH(power);
        //Conditions to stop
        if(power == 0) {
            isFinished = true;
        } else if((System.currentTimeMillis() - startTime) / 1000.0 > 30) {
            isFinished = true;
            return false;
        } else if(distance != DriveParameters.DEF_DISTANCE) {
            if (power > 0 && leftMotor.getCurrentPosition() >= goalPosition) {
                isFinished = true;
            } else if (power < 0 && leftMotor.getCurrentPosition() <= goalPosition) {
                isFinished = true;
            }
        } else {
            isFinished = false;
        }
        return true;
    }

    public void stopDriveMotors() {
        isFinished = false;
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        isFinished = true;
    }

    public DriveParameters genDriveToDistance(float power, int distance) {
        return new DriveParameters(power, distance);
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
