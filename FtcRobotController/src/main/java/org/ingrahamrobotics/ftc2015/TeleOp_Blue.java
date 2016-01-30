package org.ingrahamrobotics.ftc2015;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ingrahamrobotics.ftc2015.drive.LiftControl;

public class TeleOp_Blue extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    DcMotor liftMotor;
    DcMotor collectorSpinMotor;
    DigitalChannel switch_;
    boolean liftMoving;

    Servo zipLineLeft;
    Servo zipLineRight;
    Servo collectorTilt;
    Servo hook_servo;

    private boolean collectorIsTilting;
    enum CollectorTiltDirection {
        Left,
        Neutral,
        Right
    }
    private CollectorTiltDirection currentTiltDirection = CollectorTiltDirection.Neutral;
    private boolean wasPressed;
    private int inversionMult = 1;

    static final double SERVO_LEFT_DOWN = 1.0;
    static final double SERVO_LEFT_HALFWAY = 0.7;
    static final double SERVO_LEFT_UP = 0.05;
    static final double SERVO_RIGHT_DOWN = 0.0;
    static final double SERVO_RIGHT_HALFWAY = 0.3;
    static final double SERVO_RIGHT_UP = 0.95;
    static final double ARM_RIGHT = 1.0;
    static final double ARM_NEUTRAL = 0.675;
    static final double ARM_LEFT = 0.0;

    // Enable/Disable all encoder/switch based lift functions
    static final boolean AUTO_LIFT = false;

    private boolean switchMode = false;
    private boolean collectorOff = false;

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

    public void init() {
        // Drive Motors
        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        // Lift/Collector Motors
        liftMotor = hardwareMap.dcMotor.get("scoring_arm_motor");
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
        collectorSpinMotor = hardwareMap.dcMotor.get("collector_spin_motor");
        if (AUTO_LIFT) {
            switch_ = hardwareMap.digitalChannel.get("switch");
        }

        // Servos
        zipLineLeft = hardwareMap.servo.get("zip_line_left");
        zipLineLeft.setPosition(SERVO_LEFT_UP);
        zipLineRight = hardwareMap.servo.get("zip_line_right");
        zipLineRight.setPosition(SERVO_RIGHT_UP);

        collectorTilt = hardwareMap.servo.get("collector_tilt");
        collectorTilt.setPosition(ARM_NEUTRAL);

        hook_servo = hardwareMap.servo.get("hook_servo");
        hook_servo.setPosition(0);
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
        /*
        //To invert controls
        if(gamepad1.y) {
            wasPressed = true;
        }

        if(wasPressed && !gamepad1.y) {
            inversionMult *= -1;
            wasPressed = false;
        }
        */

        CollectTelemetry();
        // Tank Drive
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        right = Range.clip(right, -1f, 1f) * inversionMult;
        left = Range.clip(left, -1f, 1f) * inversionMult;
        motorRight.setPower(scale_motor_power(right));
        motorLeft.setPower(scale_motor_power(left));

        // Lift
        float lift = gamepad2.left_stick_y;
        lift = Range.clip(lift, -1f, 1f);
        liftMotor.setPower(scale_motor_power(lift));
        if(Math.abs(lift) > 0.1) {
            liftMoving = true;
        } else {
            liftMoving = false;
        }

        if (AUTO_LIFT) {
            if (gamepad1.y) {
                LiftControl liftControl = new LiftControl(liftMotor, switch_);
                liftControl.zeroEncoders();
            }
        }

        //we also want to be able to turn the collector motor off (it's noisy...)
        //uses a toggle
        if (!(gamepad1.a || gamepad2.a)) {
            if(!collectorOff) {
                // Collector Reverse
                if (gamepad2.dpad_down) {
                    collectorSpinMotor.setPower(-1);
                } else {
                    collectorSpinMotor.setPower(1);
                }
            }
        } else {
            switchMode = true;
        }

        if(switchMode && !(gamepad1.a || gamepad2.a)) {
            collectorOff = !collectorOff;
            switchMode = false;
        }

        if(collectorOff) {
            collectorSpinMotor.setPower(0);
        }

        //the hopper digs into the floor if it gets tilted while at minimum height
        //we need to disallow tilting the hopper until the lift arm is extended
        //75% or more, but we need a motor with an encoder to do this properly
        // Hopper Dump
        //calculate what's happening first, then execute them in the order needed
        if (gamepad2.b || gamepad2.right_stick_x > .1) { //see if this change is too unnatural, can have different buttons
            collectorIsTilting = true;
            currentTiltDirection = CollectorTiltDirection.Left;
        } else {
            collectorIsTilting = false;
            currentTiltDirection = CollectorTiltDirection.Neutral;
        }

        if(collectorIsTilting) {
            switch (currentTiltDirection) {
                case Left:
                    collectorTilt.setPosition(ARM_LEFT);
                    break;
                case Right:
                    collectorTilt.setPosition(ARM_RIGHT);
                    break;
                default:
                    collectorTilt.setPosition(ARM_NEUTRAL);
                    break;
            }
        } else {
            collectorTilt.setPosition(ARM_NEUTRAL);
        }

        //Changing wiper position
        if(gamepad2.left_bumper) {
            zipLineLeft.setPosition(SERVO_LEFT_DOWN);
        } else if(!liftMoving) {
            zipLineLeft.setPosition(SERVO_LEFT_UP);
        } else {
            zipLineRight.setPosition(SERVO_RIGHT_HALFWAY);
        }
        if(gamepad2.right_bumper) {
            zipLineRight.setPosition(SERVO_RIGHT_DOWN);
        } else if(!liftMoving) {
            zipLineRight.setPosition(SERVO_RIGHT_UP);
        } else {
            zipLineRight.setPosition(SERVO_RIGHT_HALFWAY);
        }

        //hook servo
        if (gamepad1.x){
            hook_servo.setPosition(1f);
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
        if (AUTO_LIFT) { telemetry.addData("Encoder Lift", liftMotor.getCurrentPosition()); }
    }
}
