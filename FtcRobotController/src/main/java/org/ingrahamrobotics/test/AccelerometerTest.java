package org.ingrahamrobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.ingrahamrobotics.ftc2015.sensors.Accelorometer;

/**
 * Created by Robotics on 12/18/2015.
 */
public class AccelerometerTest extends OpMode {

    public Accelorometer accelorometer;

    @Override
    public void init() {
        accelorometer = new Accelorometer();

        accelorometer.start();
    }

    @Override
    public void loop() {
        float[] values = accelorometer.printValues();
        telemetry.addData("X", values[0]);
        telemetry.addData("Y", values[1]);
        telemetry.addData("Z", values[2]);
    }

    @Override
    public void stop() {
        accelorometer.stop();
    }
}
