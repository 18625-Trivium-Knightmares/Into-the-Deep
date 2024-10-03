package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp (name = "SkunkedTeleOp", group = "TELEOP")
public class SkunkedTeleOp extends LinearOpMode {

    DcMotor FR, FL, BR, BL, arm, actuator, slide;


    @Override
    public void runOpMode() throws InterruptedException {
        FR = hardwareMap.get(DcMotor.class, "rightFront");
        FL = hardwareMap.get(DcMotor.class, "leftFront");
        BR = hardwareMap.get(DcMotor.class, "rightBack");
        BL = hardwareMap.get(DcMotor.class, "leftBack");
        arm = hardwareMap.get(DcMotor.class, "arm");
        actuator = hardwareMap.get(DcMotor.class, "actuator");
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        slide = hardwareMap.get(DcMotor.class, "slide");
        waitForStart();
        while (opModeIsActive()) {
            double gamepadpower = gamepad1.left_stick_y;
            double gamepadpowerright = gamepad1.right_stick_y;
            FR.setPower(gamepadpowerright);
            BR.setPower(gamepadpowerright);
            FL.setPower(gamepadpower);
            BL.setPower(gamepadpower);
            if (gamepad1.a) {
                arm.setPower(1);
                telemetry.addData("Arm:", "Up");
                telemetry.update();
            } else if (gamepad1.b) {
                arm.setPower(-1);
                telemetry.addData("Arm:", "Down");
                telemetry.update();
            } else {
                arm.setPower(0);
            }
            if (gamepad1.dpad_up) {
                slide.setPower(1);
                telemetry.addData("Slides:", "Up");
                telemetry.update();
            } else if (gamepad1.dpad_down) {
                slide.setPower(-1);
                telemetry.addData("Slides:", "Down");
                telemetry.update();
            } else {
                slide.setPower(0);
            }
            if (gamepad1.x) {
                actuator.setPower(1);
                telemetry.addData("Actuator:", "Up");
                telemetry.update();
            } else if (gamepad1.y) {
                actuator.setPower(-1);
                telemetry.addData("Actuator:", "Down");
                telemetry.update();
            } else {
                actuator.setPower(0);
            }
        }

    }
}