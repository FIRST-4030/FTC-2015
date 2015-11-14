package org.ingrahamrobotics.ftc2015;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.ingrahamrobotics.ftc2015.MotorCommands;

import java.util.List;

/**
 * Created by robotics on 11/4/2015.
 */
public class AutonomousTest extends LinearOpMode{ //implements SensorEventListener{

    private MotorCommands drive;

    boolean run = false;

    //SensorManager manager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
    //Sensor rotation = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    //float x, y, z, w;

    DcMotor right = hardwareMap.dcMotor.get("right_drive");
    DcMotor left = hardwareMap.dcMotor.get("left_drive");


    /*public void onSensorChanged(SensorEvent event) {
        // we received a sensor event. it is a good practice to check
        // that we received the proper event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // convert the rotation-vector to a 4x4 matrix. the matrix
            // is interpreted by Open GL as the inverse of the
            // rotation-vector, which is what we want.

            x = event.values[0];
            y = event.values[0];
            z = event.values[0];
            w = event.values[0];
        }
    }*/

    @Override
    //public void onAccuracyChanged(Sensor sensor, int accuracy) {

    //}

    public void runOpMode() {
        /*manager.registerListener((SensorEventListener) this, rotation, SensorManager.SENSOR_DELAY_UI);

        List<Sensor> rotationVector = manager.getSensorList(Sensor.TYPE_ROTATION_VECTOR);

        List<Sensor> all = manager.getSensorList(Sensor.TYPE_ALL);
        telemetry.addData("rotationVector 0 (x)",x);
        telemetry.addData("rotationVector 0 (x)",y);
        telemetry.addData("rotationVector 0 (x)",z);
        telemetry.addData("rotationVector 0 (x)",w);
        telemetry.addData("Have gyro?", gyro);
        //int i = 0;
        for(Sensor s: all) {
            telemetry.addData("Sensor" + i, s.getName());
            i++;
        }*/
        //drive = new MotorCommands(left, right);
        //drive.setMotorMode(DcMotorController.RunMode.RUN_TO_POSITION);
        //int x = 5500/42 * 12;
        //drive.driveToDistance(2 * x, 1F);
        //turn
        turn(45);
    }

    public void autoRed() {

    }

    //Turn method using encoders as a backup
    public void turn(float degrees){
        float conversion = (12000/50); //Encoder value / degrees
        int encoderValue = (int) (degrees*conversion);

        left.setPower(.5);
        right.setPower(-.5);

        int leftGoal;
        int rightGoal;

        if(degrees>0){
            leftGoal = left.getCurrentPosition()+encoderValue;
            rightGoal = left.getCurrentPosition()-encoderValue;
        }else{
            leftGoal = left.getCurrentPosition()-encoderValue;
            rightGoal = left.getCurrentPosition()+encoderValue;
        }

        left.setTargetPosition(leftGoal);
        right.setTargetPosition(rightGoal);
    }
    /**
     * The following pseudo code is written with the following assumptions:
     * int x = 5500/42 * 12; (the encoder distance equal to 1 foot)
     * int y = the ultrasound value needed to drive to
     * driveToDistance(int d, double sp) is a method that goes forward and backwards, driving wheel motors to d, an encoder value, at a speed of sp
     * turnToAngle(double a, boolean left) is a method that turns to a specific angle, left if true, right if false
     * driveToUltrasound(int d, double sp) is a method that drives to (at or less than) an ultrasound value d, at a speed of sp
     * public void autoRed(double longDrive) {
     *     driveToDistance(2 * x, 1.0);
     *     turnToAngle(45, true);
     *     driveToDistance(longDrive * x, 1.0);
     *     turnToAngle(45, true);
     *     driveToUltrasound(y, 0.5);
     *     dumpRoutine();
     *     driveToDistance(-3 * x, 1.0);
     *     turnToAngle(45, true);
     *     driveToDistance(4.4 * x, 1.0);
     *     //ascend ramp
     * }
     *
     * public void autoRedClose() {
     *     autoRed(6.2);
     * }
     *
     * public void autoRedFar() {
     *     autoRed(7.9);
     * }
     *
     *
     */
}
