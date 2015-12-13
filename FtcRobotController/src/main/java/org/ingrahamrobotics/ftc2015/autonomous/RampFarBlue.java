package org.ingrahamrobotics.ftc2015.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ingrahamrobotics.ftc2015.drive.DriveParameters;
import org.ingrahamrobotics.ftc2015.drive.MotorCommands;

/**
 * Created by robotics on 12/9/2015.
 */
public class RampFarBlue extends LinearOpMode {

    private MotorCommands drive;

    public void runOpMode() {
        // Init
        DcMotor right = hardwareMap.dcMotor.get("right_drive");
        DcMotor left = hardwareMap.dcMotor.get("left_drive");

        // Wait for Start
        try {
            waitForStart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        drive = new MotorCommands(left, right);
        //Back up
        DriveParameters bkd7Ft = drive.genDriveToDistance(-1.0f, 12 * 7 * MotorCommands.TICKS_PER_INCH);
        long startTime = System.currentTimeMillis();
        int position = left.getCurrentPosition();
        do {
            drive.driveLoop(bkd7Ft, startTime, position);
        } while(!drive.isFinished());
        //Turn
        DriveParameters trn50L = drive.genTurnToAngle(1.0f, 50, false);
        startTime = System.currentTimeMillis();
        position = left.getCurrentPosition();
        drive.resetCompass();
        do {
            drive.driveLoop(trn50L, startTime, position);
        } while(!drive.isFinished());
        //Go up ramp
        DriveParameters frd5Ft = drive.genDriveToDistance(1.0f, 12 * 5 * MotorCommands.TICKS_PER_INCH);
        startTime = System.currentTimeMillis();
        position = left.getCurrentPosition();
        do {
            drive.driveLoop(frd5Ft, startTime, position);
        } while(!drive.isFinished());
    }

}
