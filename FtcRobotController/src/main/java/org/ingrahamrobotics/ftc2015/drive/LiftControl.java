package org.ingrahamrobotics.ftc2015.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DigitalChannel;

/**
 * Created by Robotics on 1/6/2016.
 */

//Helper functions for providing control of the lift
public class LiftControl {
    DcMotor liftMotor;
    DigitalChannel switch_;
    boolean encodersZeroed; //Are the encoders zeroed to the known height?
    //Need some variable for the switch here, dont know what class yet
    public LiftControl(DcMotor lift, DigitalChannel switch_p){//add parameter for switch
        liftMotor = lift;
        switch_ = switch_p;
        encodersZeroed = false;
    }
    public void zeroEncoders(){//Zero the encoders to the known position
        liftMotor.setPower(1);
        while(switch_.getState()==false){}
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        liftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        encodersZeroed = true;
    }
}
