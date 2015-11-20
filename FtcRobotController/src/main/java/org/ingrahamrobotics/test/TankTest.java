package org.ingrahamrobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by tanya on 10/9/15.
 */
public class TankTest extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    DcMotor scoringArmMotor;
    DcMotor collectorSpinMotor;

    Servo zipLineLeft;
    Servo zipLineRight;

    final double SERVO_LEFT_UP = 0.8;
    final double SERVO_RIGHT_UP = 0.25;
    final double SERVO_LEFT_DOWN = 0;
    final double SERVO_RIGHT_DOWN = 1.0;

    Servo collectorTilt;

    public void init() {
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorRight.setDirection(DcMotor.Direction.REVERSE);

        scoringArmMotor = hardwareMap.dcMotor.get("scoring_arm_motor");
        collectorSpinMotor = hardwareMap.dcMotor.get("collector_spin_motor");
        zipLineLeft = hardwareMap.servo.get("zip_line_left");
        zipLineRight = hardwareMap.servo.get("zip_line_right");
        collectorTilt = hardwareMap.servo.get("collector_tilt");
    }

    @Override
    public void start(){
        super.start();
        //start the collector spinning here
        collectorSpinMotor.setDirection(DcMotor.Direction.FORWARD);
        collectorSpinMotor.setPower(1f);
    }

    @Override
    public void stop(){
        super.stop();
        //stop the collector spinning here
        collectorSpinMotor.setPower(0f);
    }

    public void loop() {

        // note that if y equal -1 then joystick is pushed all of the way forward.
        //for testing purposes, everything runs through one gamepad, gamepad2
        float left = -gamepad2.left_stick_y;
        float right = -gamepad2.right_stick_y;

        //telemetry.addData("G1LY", gamepad1.left_stick_y);
        //telemetry.addData("G1RY", gamepad1.right_stick_y);
        telemetry.addData("G2LY", gamepad2.left_stick_x);
        telemetry.addData("G2RY", gamepad2.right_stick_y);
        //telemetry.addData("G1X", gamepad1.x);
        telemetry.addData("G2X", gamepad2.x);
        //telemetry.addData("G1BL", gamepad1.left_bumper);
        //telemetry.addData("G1BR", gamepad1.right_bumper);
        telemetry.addData("G2BL", gamepad2.left_bumper);
        telemetry.addData("G2BR", gamepad2.right_bumper);
        telemetry.addData("EL", motorLeft.getCurrentPosition());
        telemetry.addData("ER", motorRight.getCurrentPosition());
        telemetry.addData("Right Motor Encoder", motorRight.getCurrentPosition());
        telemetry.addData("Left Motor Encoder", motorLeft.getCurrentPosition());

        // clip the right/left values so that the values never exceed +/- 1
        right = (float) Range.clip(right, -1, 1);
        left = (float) Range.clip(left, -1, 1);

        // write the values to the motors
        //try to scale it with algorithm swiped from the PushBotHardware OpCode
        motorRight.setPower(scale_motor_power(right));
        motorLeft.setPower(scale_motor_power(left));

        telemetry.addData("L Out", left);
        telemetry.addData("R Out", right);

        //will move the zipline servos up and down
        if(gamepad2.left_bumper) {
            zipLineLeft.setPosition(SERVO_LEFT_DOWN);
        } else {
            zipLineLeft.setPosition(SERVO_LEFT_UP);
        }

        if(gamepad2.right_bumper) {
            zipLineRight.setPosition(SERVO_RIGHT_DOWN);
        } else {
            zipLineRight.setPosition(SERVO_RIGHT_UP);
        }

        //raises (tilts) and lowers the collector to score

    }

    //--------------------------------------------------------------------------
    //
    // scale_motor_power
    //
    /**
     * Scale the joystick input using a nonlinear algorithm.
     */
    protected float scale_motor_power(float p_power)
    {
        //
        // Assume no scaling.
        //
        float l_scale;

        //
        // Ensure the values are legal.
        //
        float l_power = Range.clip (p_power, -1, 1);

        float[] l_array =
                { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    } // scale_motor_power

}
