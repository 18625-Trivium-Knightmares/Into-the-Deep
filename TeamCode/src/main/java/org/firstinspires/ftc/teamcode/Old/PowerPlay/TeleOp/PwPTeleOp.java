package org.firstinspires.ftc.teamcode.Old.PowerPlay.TeleOp;

import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.logger;

import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Old.PowerPlay.Robot.PwPRobot;

@TeleOp(name = "PwPTeleOp")
public class PwPTeleOp extends LinearOpMode {
    PwPRobot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new PwPRobot(this,true);
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
//        robot.cv.observeSleeve();
        Pose2d startPose = new Pose2d(41, 62.25, toRadians(90));
        robot.roadrun.setPoseEstimate(startPose);//        robot.cv.observeStick();
//        robot.cv.observeCone();
        waitForStart();
//        robot.cv.observeStick();
        resetRuntime();
        robot.roadrun.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.roadrun.update();
        while(!isStopRequested()){
            logger.loopcounter++;
            robot.teleOp();
            telemetry.update();
        }
        robot.stop();
    }
}