package org.ingrahamrobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by tanya on 10/9/15.
 */
public class TankTest extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    public void init() {
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {

        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = -gamepad2.left_stick_y;
        float right = -gamepad2.right_stick_y;

        telemetry.addData("G1LY", gamepad1.left_stick_y);
        telemetry.addData("G1RY", gamepad1.right_stick_y);
        telemetry.addData("G2LY", gamepad2.left_stick_x);
        telemetry.addData("G2RY", gamepad2.right_stick_y);
        telemetry.addData("G1X", gamepad1.x);
        telemetry.addData("G2X", gamepad2.x);
        telemetry.addData("EL", motorLeft.getCurrentPosition());
        telemetry.addData("ER", motorRight.getCurrentPosition());
        telemetry.addData("Right Motor Encoder", motorRight.getCurrentPosition());
        telemetry.addData("Left Motor Encoder", motorLeft.getCurrentPosition());

        // clip the right/left values so that the values never exceed +/- 1
        right = (float) Range.clip(right, -.5, .5);
        left = (float) Range.clip(left, -.5, .5);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        telemetry.addData("L Out", left);
        telemetry.addData("R Out", right);
    }

}
