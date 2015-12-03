package org.ingrahamrobotics;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;

import org.ingrahamrobotics.test.AutonomousTest;
import org.ingrahamrobotics.ftc2015.TeleOp;
import org.ingrahamrobotics.test.ColorSensorTest;
import org.ingrahamrobotics.test.EncoderTest;
import org.ingrahamrobotics.test.PhoneSensorTest;

/**
 * Created by kotlarek on 2015-11-04.
 */
public class LocalOpModeRegister {

    public LocalOpModeRegister(OpModeManager manager) {
        manager.register("TeleOp", TeleOp.class);
        manager.register("Auto - Experimental", AutonomousTest.class);

        if (true){
            manager.register("PhoneSensorTest", PhoneSensorTest.class);
        }

        if (false) {
            manager.register("ColorSensorTest", ColorSensorTest.class);
            manager.register("EncoderTest", EncoderTest.class);
            manager.register("AutonomousTest", AutonomousTest.class);
        }
    }
}
