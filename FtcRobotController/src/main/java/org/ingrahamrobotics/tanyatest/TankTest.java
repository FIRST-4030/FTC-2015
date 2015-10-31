package org.ingrahamrobotics.tanyatest;

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
        motorRight = hardwareMap.dcMotor.get("Right");
        motorLeft = hardwareMap.dcMotor.get("Left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
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

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        telemetry.addData("L Out", left);
        telemetry.addData("R Out", right);
    }

}
