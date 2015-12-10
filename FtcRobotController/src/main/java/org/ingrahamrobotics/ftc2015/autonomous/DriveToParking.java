package org.ingrahamrobotics.ftc2015.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ingrahamrobotics.ftc2015.drive.DriveParameters;
import org.ingrahamrobotics.ftc2015.drive.MotorCommands;

/**
 * Created by Ariel on 11/18/2015.
 */
public class DriveToParking extends LinearOpMode {

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
        DriveParameters frd8Ft = drive.genDriveToDistance(1.0f, 12 * 8 * MotorCommands.TICKS_PER_INCH);
        //maybe add some way to generate these on your own? Some sort of method.
        //Would require quite a bit of restructuring: do later
        long startTime = System.currentTimeMillis();
        int position = left.getCurrentPosition();
        do {
            drive.driveLoop(frd8Ft, startTime, position);
        } while(!drive.isFinished());
    }
}
