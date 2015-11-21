package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    //DcMotor liftMotor;
    //DcMotor collectorSpinMotor;

    Servo zipLineLeft;
    Servo zipLineRight;

    static final double SERVO_LEFT_UP = 0.8;
    static final double SERVO_RIGHT_UP = 0.25;
    static final double SERVO_LEFT_DOWN = 0;
    static final double SERVO_RIGHT_DOWN = 1.0;
    static final double ARM_RIGHT = .65;
    static final double ARM_NEUTRAL = .4;
    static final double ARM_LEFT = .15;

    /*
    g1 L joystick = left tread *
    g1 R joystick = right tread *
    g2 L joystick = lift *
    g2 R joystick = dump
    g2 X and B = dump
    g2 L bumper = L flag *
    g2 R bumper = R flag *
    g2 D pad = collector reverse *
     */

    Servo collectorTilt;

    public void init() {
        // Drive Motors
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        //motorRight.setDirection(DcMotor.Direction.REVERSE);

        // Lift/Collector Motors
        //liftMotor = hardwareMap.dcMotor.get("scoring_arm_motor");
        //liftMotor.setDirection(DcMotor.Direction.REVERSE);
        //collectorSpinMotor = hardwareMap.dcMotor.get("collector_spin_motor");

        // Servos
        zipLineLeft = hardwareMap.servo.get("zip_line_left");
        zipLineRight = hardwareMap.servo.get("zip_line_right");
        //collectorTilt = hardwareMap.servo.get("collector_tilt");
    }

    @Override
    public void start(){
        super.start();
        //collectorSpinMotor.setDirection(DcMotor.Direction.FORWARD);
        //collectorSpinMotor.setPower(1f);
    }

    @Override
    public void stop(){
        super.stop();
        //collectorSpinMotor.setPower(0f);
    }

    public void loop() {

        // Tank Drive
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        right = (float) Range.clip(right, -1, 1);
        left = (float) Range.clip(left, -1, 1);
        motorRight.setPower(scale_motor_power(right));
        motorLeft.setPower(scale_motor_power(left));

        // Lift
        //float lift = gamepad2.left_stick_y;
        //lift = (float) Range.clip(lift, -1, 1);
        //liftMotor.setPower(scale_motor_power(lift));

        // Collector Reverse
        //if(gamepad2.dpad_down) {
        //    collectorSpinMotor.setPower(-1);
        //} else {
        //    collectorSpinMotor.setPower(1);
        //}

        // Hopper Dump
        //if(gamepad2.x || gamepad2.right_stick_x<-.1) {
        //    collectorTilt.setPosition(ARM_LEFT);
        //} else if (gamepad2.b || gamepad2.right_stick_x>.1) {
        //    collectorTilt.setPosition(ARM_RIGHT);
        //} else {
        //    collectorTilt.setPosition(ARM_NEUTRAL);
        //}

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
}
