package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Robotics on 11/14/2015.
 */
public class TeleOp extends OpMode {

    DcMotor motorLeft;
    DcMotor motorRight;
    Servo collectorTilt;
    Servo zipLineLeft;
    Servo zipLineRight;
    //these are untested ints. they need to be invidualized to each servo and tested.
    final int SERVO_DOWN = 20;
    final int SERVO_UP = 40;


    @Override
    public void init() {
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        //collectorTilt = hardwareMap.servo.get("collector_tilt");
        zipLineLeft = hardwareMap.servo.get("zip_line_left");
        zipLineRight = hardwareMap.servo.get("zip_line_right");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = gamepad2.left_stick_y;
        float right = gamepad2.right_stick_y;

        //For debugging values before adjustment - can be commented out
        telemetry.addData("G1LY", gamepad1.left_stick_y);
        telemetry.addData("G1RY", gamepad1.right_stick_y);

        // clip the right/left values so that the values never exceed +/- 1
        right = (float) Range.clip(right, -1, 1);
        left = (float) Range.clip(left, -1, 1);

        //For debugging values after adjustment - can be commented out
        telemetry.addData("L Out", left);
        telemetry.addData("R Out", right);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        //will move the zipline servos up and down
        if(gamepad1.x) {
            zipLineLeft.setPosition(SERVO_UP);
        } else if(gamepad1.a) {
            zipLineLeft.setPosition(SERVO_DOWN);
        }

        if(gamepad1.y) {
            zipLineRight.setPosition(SERVO_UP);
        } else if(gamepad1.b) {
            zipLineRight.setPosition(SERVO_DOWN);
        }

        //raises (tilts) and lowers the collector to score
        if(gamepad1.left_bumper) {
            collectorTilt.setPosition(SERVO_DOWN);
        } else if(gamepad1.right_bumper) {
            collectorTilt.setPosition(SERVO_UP);
        }
    }
}
