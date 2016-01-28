package org.ingrahamrobotics.ftc2015.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ingrahamrobotics.ftc2015.drive.DriveParameters;
import org.ingrahamrobotics.ftc2015.drive.MotorCommands;

/**
 * Created by Ariel on 1/27/2016.
 */

public class DriveToParkingWStops extends LinearOpMode {

    private MotorCommands drive;

    @Override
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

        // Drive forward into parking zone
        drive = new MotorCommands(left, right);
        DriveParameters frd3Ft = drive.genDriveToDistance(1.0f, (int) (12 * 2.6 * MotorCommands.TICKS_PER_INCH));
        DriveParameters bwdHlfFt = drive.genDriveToDistance(-1.0f, (int) (12 * 0.5 * MotorCommands.TICKS_PER_INCH));
        //maybe add some way to generate these on your own? Some sort of method.
        //Would require quite a bit of restructuring: do later
        for(int i = 0; i < 3; i++) {
            long startTime = System.currentTimeMillis();
            int position = left.getCurrentPosition();
            do {
                drive.driveLoop(frd3Ft, startTime, position);
            } while (!drive.isFinished());
            startTime = System.currentTimeMillis();
            position = left.getCurrentPosition();
            do {
                drive.driveLoop(bwdHlfFt, startTime, position);
            } while (!drive.isFinished());
        }
        DriveParameters frdRst = drive.genDriveToDistance(1.0f, (int) (12 * 1.5 * MotorCommands.TICKS_PER_INCH));
        long startTime = System.currentTimeMillis();
        int position = left.getCurrentPosition();
        do {
            drive.driveLoop(frdRst, startTime, position);
        } while (!drive.isFinished());
    }
}
