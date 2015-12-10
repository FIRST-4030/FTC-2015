package org.ingrahamrobotics.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tanya on 10/9/15.
 * Testbed OpMode that has been messed with repeatedly. :-)
 */
public class PhoneSensorTest extends OpMode {

    private SensorManager sensorManager;
    private LocationManager locationManager;
    private Sensor gyro;
    private Sensor accelerometer;
    //Servo leftFlipperServo;
    //Servo rightFlipperServo;
    DcMotor motorRight;
    DcMotor motorLeft;

    private SensorEvent gyroEvent = null;
    private SensorEvent accelerometerEvent = null;
    private PhoneSensorListener gyroListener = new PhoneSensorListener();
    private PhoneSensorListener accelerometerListener = new PhoneSensorListener();
    private LocationListener locationListener = new LocationListener() {
        public Location lastLocation = null;

        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //no idea
        }

        @Override
        public void onProviderEnabled(String provider) {
            //no idea
        }

        @Override
        public void onProviderDisabled(String provider) {
            //no idea
        }
    };

    public void init() {
        sensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        locationManager = (LocationManager) hardwareMap.appContext.getSystemService(Context.LOCATION_SERVICE);

        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gyroListener, gyro, SensorManager.SENSOR_DELAY_NORMAL);
        
        //locationManager.addGpsStatusListener((GpsStatus.Listener) locationListener);

        motorRight = hardwareMap.dcMotor.get("right_drive");
        motorLeft = hardwareMap.dcMotor.get("left_drive");
        motorRight.setDirection(DcMotor.Direction.REVERSE);

        //leftFlipperServo = hardwareMap.servo.get("zip_line_left");
        //rightFlipperServo = hardwareMap.servo.get("zip_line_right");

    }

    public void loop() {

        telemetry.addData("sensors", Arrays.toString(sensorManager.getSensorList(Sensor.TYPE_ALL).toArray()));
        
        gyroEvent = gyroListener.mostRecentEvent();
        accelerometerEvent = accelerometerListener.mostRecentEvent();

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
        //telemetry.addData("LF1P", leftFlipperServo.getPosition());
        //telemetry.addData("LF1D", leftFlipperServo.getDirection());
        //telemetry.addData("RF1P", leftFlipperServo.getPosition());
        //telemetry.addData("RF1D", leftFlipperServo.getDirection());

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        //TODO: the flipper code needs to be tweaked to reflect that a.) we now have 2 flippers, and b.) they point in opposite directions
        // also the servo position
        //float flipper_position = gamepad2.right_trigger;
        //Servo.Direction flipper_direction = flipperServo.getDirection();
        //flipper_position = Range.clip(flipper_position, 0, 1);

        //flipper_direction = gamepad2.left_bumper ? Servo.Direction.FORWARD : Servo.Direction.REVERSE;

        //leftFlipperServo.setPosition(flipper_position);
        //leftFlipperServo.setDirection(flipper_direction);
        //leftFlipperServo.setPosition(flipper_position);
        //leftFlipperServo.setDirection(flipper_direction);

        telemetry.addData("L Out", left);
        telemetry.addData("R Out", right);
        
        //get the sensors
        if (gyroEvent != null){
            telemetry.addData("Gyro X", gyroEvent.values[0]);
            telemetry.addData("Gyro Y", gyroEvent.values[1]);
            telemetry.addData("Gyro Z", gyroEvent.values[2]);
        }
        else{
            telemetry.addData("Gyro","Gyro Event is null.");
        }
        
        if (accelerometerEvent != null){
            telemetry.addData("Accelerometer X", accelerometerEvent.values[0]);
            telemetry.addData("Accelerometer Y", accelerometerEvent.values[1]);
            telemetry.addData("Accelerometer Z", accelerometerEvent.values[2]);
        }
        else{
            telemetry.addData("Accelerometer","Accelerometer Event is null.");
        }
    }

}
