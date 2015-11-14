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
    }

    public void setMotorMode(DcMotorController.RunMode m) {
        leftMotor.setMode(m);
        rightMotor.setMode(m);
    }

    public void setMotorPower(float power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public void stopDriveMotors() {
        setMotorPower(0);
    }

    public void driveToDistance(int distance, float power) {
        /*
        if(!leftMotor.getMode().equals(DcMotorController.RunMode.RUN_TO_POSITION)) {
            //some error?
            return;
        }
        */
        setMotorPower(power);
        int leftGoal = leftMotor.getCurrentPosition() + distance;
        int rightGoal = rightMotor.getCurrentPosition() + distance;
        leftMotor.setTargetPosition(leftGoal);
        rightMotor.setTargetPosition(rightGoal);
    }

    public void setMotorTurn(float power, boolean left) {
        if (left) {
            leftMotor.setPower(-power);
            rightMotor.setPower(power);
        } else {
            leftMotor.setPower(power);
            rightMotor.setPower(-power);
        }
    }

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
