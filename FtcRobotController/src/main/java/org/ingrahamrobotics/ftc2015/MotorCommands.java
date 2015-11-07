package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by robotics on 11/6/2015.
 */
public class MotorCommands {
    DcMotor leftMotor = hardwareMap.dcMotor.get("Left");;
    DcMotor rightMotor = hardwareMap.dcMotor.get("Right");;
    public void setMotorSpeed(float speed) {
        leftMotor.setSpeed(speed);
        rightMotor.setSpeed(speed);
    }
    public void driveToDistance(int distance) {
        leftMotor.driveToDistance(distance);
        rightMotor.driveToDistance(distance);
    }
    public void setMotorTurn(double speed, boolean left) {
        if (left) {
            leftMotor.setSpeed(-speed);
            rightMotor.setSpeed(speed);
        } else {
            leftMotor.setSpeed(speed);
            rightMotor.setSpeed(-speed);
        }
    }
    public void driveToSonar(int x, int speed) {
        while (sonar.getValue()>x) {
            setMotorSpeed(speed);
        }
        setMotorSpeed(0);
    }
}
