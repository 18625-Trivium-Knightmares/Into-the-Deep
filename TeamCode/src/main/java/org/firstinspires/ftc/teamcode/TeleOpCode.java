package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "Robot Centric Drive")
//@Disabled
public class TeleOpCode extends LinearOpMode {

    DcMotor FR, FL, BR, BL, arm;
    public static long defaultdelay = 1000;
    public static double defaultpower = 1;
    public static double defaultnegativepower = -1;

    @Override
    public void runOpMode() {
        FR = hardwareMap.get(DcMotor.class, "rightFront");
        FL = hardwareMap.get(DcMotor.class, "leftFront");
        BR = hardwareMap.get(DcMotor.class, "rightBack");
        BL = hardwareMap.get(DcMotor.class, "leftBack");
        arm = hardwareMap.get(DcMotor.class, "arm");
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while(opModeIsActive()) {
            double gamepadpower = gamepad1.left_stick_y;
            double gamepadpowerright = gamepad1.right_stick_y;
            FR.setPower(gamepadpowerright);
            BR.setPower(gamepadpowerright);
            FL.setPower(gamepadpower);
            BL.setPower(gamepadpower);
            if (gamepad1.a) {
                arm.setPower(defaultpower);
            } else if (gamepad1.b) {
                arm.setPower(defaultnegativepower);
            } else {
                arm.setPower(0);
            }
        }
    }
}




