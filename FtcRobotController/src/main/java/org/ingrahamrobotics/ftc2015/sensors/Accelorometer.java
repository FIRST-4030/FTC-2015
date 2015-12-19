package org.ingrahamrobotics.ftc2015.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

/**
 * Created by Robotics on 12/18/2015.
 */
public class Accelorometer implements SensorEventListener {

    private SensorManager sensorManager;

    private boolean ready = false;
    private float zeroHeading = 0;

    private float x = 0;
    private float y = 0;
    private float z = 0;

    public Accelorometer () {
        sensorManager = (SensorManager) FtcRobotControllerActivity.FTC_ACTIVITY.getSystemService(Context.SENSOR_SERVICE);
    }

    public void start() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop() {
        sensorManager.unregisterListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
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

    public float[] printValues() {
        float[] values = new float[3];
        values[0] = x;
        values[1] = y;
        values[2] = z;
        return values;
    }
}
