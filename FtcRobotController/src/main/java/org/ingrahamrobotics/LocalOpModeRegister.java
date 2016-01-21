package org.ingrahamrobotics;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;

import org.ingrahamrobotics.ftc2015.TeleOp_Blue;
import org.ingrahamrobotics.ftc2015.autonomous.DriveToParking;
import org.ingrahamrobotics.ftc2015.autonomous.RampFarBlue;
import org.ingrahamrobotics.ftc2015.autonomous.RampFarRed;
import org.ingrahamrobotics.test.AccelerometerTest;
import org.ingrahamrobotics.test.AutonomousTest;
import org.ingrahamrobotics.ftc2015.TeleOp;
import org.ingrahamrobotics.test.ColorSensorTest;
import org.ingrahamrobotics.test.CompassTest;
import org.ingrahamrobotics.test.EncoderTest;
import org.ingrahamrobotics.test.PhoneSensorTest;

/**
 * Created by kotlarek on 2015-11-04.
 */
public class LocalOpModeRegister {

    public LocalOpModeRegister(OpModeManager manager) {
        manager.register("TeleOp Red", TeleOp.class);
        manager.register("TeleOp Blue", TeleOp_Blue.class);
        manager.register("AutoPark", DriveToParking.class);
        manager.register("AutoRampCloseR", RampFarRed.class);
        manager.register("AutoRampCloseB", RampFarBlue.class);

        if (true) {
            manager.register("Compass Test", CompassTest.class);
            manager.register("Accelorometer Test", AccelerometerTest.class);
            manager.register("PhoneSensorTest", PhoneSensorTest.class);
        }

        if (false) {
            manager.register("ColorSensorTest", ColorSensorTest.class);
            manager.register("EncoderTest", EncoderTest.class);
            manager.register("AutonomousTest", AutonomousTest.class);
        }
    }
}
