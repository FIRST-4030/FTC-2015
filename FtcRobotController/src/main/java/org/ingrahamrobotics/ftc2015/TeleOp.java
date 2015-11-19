package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Robotics on 11/14/2015.
 */
public class TeleOp extends OpMode {

    //NOTE: THIS CODE IS MOSTLY UNTESTED AND USES HARDWARE THAT IS NOT YET ON THE ROBOT
    //Follow the instructions in the comments on how to use the code
    //Hook mechanics are not yet added, because I didn't know them


    DcMotor motorLeft;
    DcMotor motorRight;
    //Even if they are not on the robot, having undefined fields will not matter in the code
    DcMotor lift;
    DcMotor collectorIntake;
    Servo collectorTilt;
    Servo zipLineLeft;
    Servo zipLineRight;

    //The servos accept floats between 0 and 1
    final float SERVO_DOWN = 0F;
    final float SERVO_UP = 1F;
    final int LIFT_UP = 100000;
    final int LIFT_DOWN = 0;
    //Test value for lift is full speed positive. can be changed if necessary
    final double LIFT_SPEED = 1.0;
    //Test values for collector are full speed opposite directions. can be changed
    final double COLLECTOR_IN = 1.0;
    final double COLLECTOR_OUT = 1.0;

    @Override
    public void init() {
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        //These parts should be commented out based on whether or not they're on the robot
        //The code won't work properly if the unadded parts are initialized
        //lift = hardwareMap.dcMotor.get("lift");
        //collectorIntake = hardwareMap.dcMotor.get("collector_intake");
        //collectorTilt = hardwareMap.servo.get("collector_tilt");
        zipLineLeft = hardwareMap.servo.get("zip_line_left");
        zipLineRight = hardwareMap.servo.get("zip_line_right");

        //other motors may need to be reversed
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        //lift.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        //lift.setTargetPosition(LIFT_DOWN);
        //lift.setPower(LIFT_SPEED);
    }

    @Override
    public void loop() {
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = gamepad2.left_stick_y;
        float right = gamepad2.right_stick_y;

        //For debugging values before adjustment - can be commented out
        telemetry.addData("G1LY", gamepad1.left_stick_y);
        telemetry.addData("G1RY", gamepad1.right_stick_y);
        //or added to if necessary

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        //For debugging values after adjustment - can be commented out
        telemetry.addData("L Out", left);
        telemetry.addData("R Out", right);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        //NOTE: CURRENTLY EVERYTHING IS MAPPED TO THE FIRST JOYSTICK FOR EASE OF TESTING
        //Can be remapped after discussion with drivers to the final positions

        //will move the zipline servos up and down
        /*in this case, SERVO_UP is used to describe when the flags are inside
        of the robot and SERVO_DOWN refers to when they're sticking out to hit the zipline
        */
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
        /*in this case SERVO_DOWN is used to describe when the collector is flat
        and
        */
        //may want to map this to buttons on controller 2
        /*
        if(gamepad1.left_bumper) {
            collectorTilt.setPosition(SERVO_DOWN);
        } else if(gamepad1.right_bumper) {
            collectorTilt.setPosition(SERVO_UP);
        }

        //sets the lifts to one preset high and lowers it
        //may want to map this to buttons on controller 2
        if(gamepad1.dpad_down) {
            lift.setTargetPosition(LIFT_DOWN);
        } else if(gamepad1.dpad_up) {
            lift.setTargetPosition(LIFT_UP);
        }

        //sets the intake spinners going in and out
        //may want to map this to a joystick on controller 2
        if(gamepad1.left_stick_button) {
            collectorIntake.setPower(COLLECTOR_OUT);
        } else if(gamepad1.right_stick_button) {
            collectorIntake.setPower(COLLECTOR_IN);
        }
        */
    }
}
