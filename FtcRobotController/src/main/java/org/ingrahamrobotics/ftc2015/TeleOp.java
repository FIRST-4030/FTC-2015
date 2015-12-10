package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    DcMotor liftMotor;
    DcMotor collectorSpinMotor;

    Servo zipLineLeft;
    Servo zipLineRight;
    Servo collectorTilt;

    static final double SERVO_LEFT_DOWN = 1.0;
    static final double SERVO_LEFT_UP = 0.0;
    static final double SERVO_RIGHT_DOWN = 0.0;
    static final double SERVO_RIGHT_UP = 1.0;
    static final double ARM_RIGHT = 1.0;
    static final double ARM_NEUTRAL = 0.5;
    static final double ARM_LEFT = 0.0;

    /*
    g1 L joystick = left tread
    g1 R joystick = right tread
    g2 Y = lift up
    g2 A = lift down
    g2 B = lift midway
    g2 D pad = hopper
    g2 L bumper = L flag
    g2 R bumper = R flag
    */
/

    Servo collectorTilt;

    public void init() {
        // Drive Motors
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorRight.setDirection(DcMotor.Direction.REVERSE);

        // Lift/Collector Motors
        liftMotor = hardwareMap.dcMotor.get("scoring_arm_motor");
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
        collectorSpinMotor = hardwareMap.dcMotor.get("collector_spin_motor");

        // Servos
        zipLineLeft = hardwareMap.servo.get("zip_line_left");
        zipLineRight = hardwareMap.servo.get("zip_line_right");
        collectorTilt = hardwareMap.servo.get("collector_tilt");
        collectorTilt.setPosition(ARM_NEUTRAL);
    }

    @Override
    public void start(){
        super.start();
        collectorSpinMotor.setDirection(DcMotor.Direction.FORWARD);
        collectorSpinMotor.setPower(1f);
    }

    @Override
    public void stop(){
        super.stop();
        collectorSpinMotor.setPower(0f);
    }

    public void loop() {

        CollectTelemetry();
        // Tank Drive
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        right = Range.clip(right, -1f, 1f);
        left = Range.clip(left, -1f, 1f);
        motorRight.setPower(scale_motor_power(right));
        motorLeft.setPower(scale_motor_power(left));

        // Lift
        float lift = gamepad2.left_stick_y;
        lift = Range.clip(lift, -1f, 1f);
        liftMotor.setPower(scale_motor_power(lift));

        //we also want to be able to turn the collector motor off (it's noisy...)
        if (!(gamepad1.a || gamepad2.a)) {
            // Collector Reverse
            if (gamepad2.dpad_down) {
                collectorSpinMotor.setPower(-1);
            } else {
                collectorSpinMotor.setPower(1);
            }
        } else {
            collectorSpinMotor.setPower(0);
        }
        //the hopper digs into the floor if it gets tilted while at minimum height
        //we need to disallow tilting the hopper until the lift arm is extended
        //75% or more, but we need a motor with an encoder to do this properly
        // Hopper Dump
        if (gamepad2.x || gamepad2.right_stick_x < -.1) {
            collectorTilt.setPosition(ARM_LEFT);
        } else if (gamepad2.b || gamepad2.right_stick_x > .1) {
            collectorTilt.setPosition(ARM_RIGHT);
        } else {
            collectorTilt.setPosition(ARM_NEUTRAL);
        }

        // Flags
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
    }

    //--------------------------------------------------------------------------
    //
    // scale_motor_power
    //
    /**
     * Scale the joystick input using a nonlinear algorithm.
     */
    protected float scale_motor_power(float p_power) {
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
        if (l_index < 0) {
            l_index = -l_index;
        } else if (l_index > 16) {
            l_index = 16;
        }

        if (l_power < 0) {
            l_scale = -l_array[l_index];
        } else {
            l_scale = l_array[l_index];
        }

        return l_scale;

    } // scale_motor_power

    //TODO: Create base OpMode that implements telemetry?
    protected void CollectTelemetry(){
        telemetry.addData("G1LY", gamepad1.left_stick_y);
        telemetry.addData("G1RY", gamepad1.right_stick_y);
        telemetry.addData("G2LY", gamepad2.left_stick_x);
        telemetry.addData("G2RY", gamepad2.right_stick_y);
        telemetry.addData("G1X", gamepad1.x);
        telemetry.addData("G2X", gamepad2.x);
        telemetry.addData("EL", motorLeft.getCurrentPosition());
        telemetry.addData("ER", motorRight.getCurrentPosition());

    }
}
