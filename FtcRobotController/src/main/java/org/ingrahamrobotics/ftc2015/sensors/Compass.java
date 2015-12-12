package org.ingrahamrobotics.ftc2015.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

/**
 * Created by profplump on 2015-12-04.
 */
public class Compass implements SensorEventListener {

    private SensorManager sensorManager;

    private boolean ready = false;
    private float zeroHeading = 0;

    private float x = 0;
    private float y = 0;
    private float z = 0;

    public Compass () {
        sensorManager = (SensorManager) FtcRobotControllerActivity.FTC_ACTIVITY.getSystemService(Context.SENSOR_SERVICE);
    }

    public void start() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop() {
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                ready = false;
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                // Fallthrough
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                ready = true;
                break;
        }
    }

    public boolean ready() {
        return ready;
    }

    public void reset() {
        zeroHeading = heading();
    }

    public int zeroHeading() {
        return (int)zeroHeading;
    }

    public int heading() {
        return (int)x;
    }

    public int relativeHeading() {
        return (int)(x - zeroHeading) % 360;
    }
}
