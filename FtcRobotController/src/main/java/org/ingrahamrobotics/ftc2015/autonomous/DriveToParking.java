package org.ingrahamrobotics.ftc2015.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ingrahamrobotics.ftc2015.drive.MotorCommands;

/**
 * Created by Ariel on 11/18/2015.
 */
public class DriveToParking extends LinearOpMode {

    private MotorCommands drive;

    @Override
    public void runOpMode() throws InterruptedException {
        //initalize(); (initalize stuff if necessary
        DcMotor right = hardwareMap.dcMotor.get("right_drive");
        DcMotor left = hardwareMap.dcMotor.get("left_drive");
        drive = new MotorCommands(left, right);
        waitForStart();

        //int x = 5500/42 * 12; this needs to be recalibrated for the bigger robot, this is encoder units/inches * 12 to get feet
        //drive.driveToDistance(2 * x, 1F); replace 2 with the measured distance in feet
    }
}
