package org.ingrahamrobotics.test;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Carter on 12/3/2015.
 */
public class PhoneSensorListener implements SensorEventListener {

    private SensorEvent _mostRecentEvent = null;

    @Override
    public void onSensorChanged(SensorEvent event) {
        _mostRecentEvent = event;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //nada, for now
    }

    public SensorEvent mostRecentEvent(){
        return _mostRecentEvent;
    }

}
