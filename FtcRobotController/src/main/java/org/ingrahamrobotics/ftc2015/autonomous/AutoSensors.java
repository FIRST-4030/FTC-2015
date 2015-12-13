package org.ingrahamrobotics.ftc2015.autonomous;

import org.ingrahamrobotics.ftc2015.sensors.Compass;

/**
 * Created by Ariel on 12/12/2015.
 */
//Possible helper class, currently unused
public class AutoSensors {

    Compass compass;


    public AutoSensors() {
        compass = null;
    }

    public void addCompass(Compass c) {
        compass = c;
    }

    public boolean hasCompass() {
        return compass != null;
    }

    public void start() {
        if(compass != null) {
            compass.start();
        }
    }
}
