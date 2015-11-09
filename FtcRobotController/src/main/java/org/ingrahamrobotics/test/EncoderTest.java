package org.ingrahamrobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Robotics on 11/4/2015.
 */
public class EncoderTest extends OpMode {
    DcMotor motorRight;
    DcMotor motorLeft;

    public void init(){
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
    }
    public void loop(){
        telemetry.addData("Right Motor Encoder", motorRight.getCurrentPosition());
        telemetry.addData("Left Motor Encoder", motorLeft.getCurrentPosition());


        motorLeft.setPower(-.3);
        motorRight.setPower(.3);
    }
}
