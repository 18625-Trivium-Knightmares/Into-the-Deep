package org.firstinspires.ftc.teamcode.Old.PowerPlay.Autonomous;

import static org.firstinspires.ftc.teamcode.Old.PowerPlay.Components.Lift.LiftConstants.LIFT_MED_JUNCTION;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.logger;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.time;
import static java.lang.Math.toRadians;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Old.PowerPlay.Robot.PwPRobot;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;

public class  BlueLeftMid {
    private boolean boosted, tooHot=false;
    PwPRobot robot=null;
    LinearOpMode op;
    double dummyP = 0, dropX=28.5
            , dropY=19.84, dropA = toRadians(320),lastTime = 0.0;
    ;
    TrajectorySequence preloadtrajectory=null, pickupTrajectory=null, park1trajectory=null,
            park2trajectory=null, park3trajectory=null, clearLTrajectory=null, clearRTrajectory=null,
            closeLTrajectory=null, closeRTrajectory=null, reDropTrajectory=null;
    ArrayList<TrajectorySequence> dropTrajectory=null, pick=null;
    ArrayList<Boolean> clObL=null, clObR=null,pInView=null;
    double startTime =0;

    public BlueLeftMid(boolean boost, LinearOpMode p_op, PwPRobot p_robot) {
        boosted = boost;
        op = p_op;
        robot = p_robot;
        op.telemetry = new MultipleTelemetry(op.telemetry, FtcDashboard.getInstance().getTelemetry());
        robot.roadrun.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void init() {
        Pose2d startPose = new Pose2d(33.9, 62.25, Math.toRadians(90));
        robot.setPoseEstimate(startPose);
        robot.cv.observeSleeve();
        op.resetRuntime();
        robot.cp1shot();
        preloadtrajectory = robot.roadrun.trajectorySequenceBuilder(new Pose2d(33.9,62.25, Math.toRadians(90)))
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(34, 48, toRadians(90)))
                .splineToSplineHeading(new Pose2d(32, 14, toRadians(315)), toRadians(270))
                .lineToLinearHeading(new Pose2d(dropX, dropY-1.5, toRadians(310)))
                .addTemporalMarker(robot::done)
                .build();

//        pickupTrajectory = robot.roadrun.trajectorySequenceBuilder(new Pose2d(dropX,dropY,Math.toRadians(315)))
//                .setReversed(true)
//                .splineTo(new Vector2d(dropX,dropY), toRadians(150))
//                .addTemporalMarker(robot::done)
//                .build();

        dropTrajectory = new ArrayList<>();
        pick = new ArrayList<>();
        clObL = new ArrayList<>();
        clObR = new ArrayList<>();
        pInView = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            clObL.add(false);
            clObR.add(false);
            pInView.add(false);
        }

        for (int i = 0; i < 5; i++) {
            dropTrajectory.add(robot.roadrun.trajectorySequenceBuilder(new Pose2d(62,12.0                                                                                                                                                                                                                                                                                           +0.4*i,Math.toRadians(0)))
                            .addTemporalMarker(()->robot.setPoling(true))
                    .setReversed(true)
                    .splineTo(new Vector2d(dropX,dropY+0.3*i), toRadians(143))
                    .addTemporalMarker(robot::done)
                    .build());
        }
        for (int i = 0; i < 5; i++) {
            pick.add(robot.roadrun.trajectorySequenceBuilder(dropTrajectory.get(i).end())
                    .addTemporalMarker(()->robot.setConing(true))
                    .setReversed(false)
                    .splineTo(new Vector2d(63.5,11.5+0.2*i), 0)
                    .addTemporalMarker(robot::done)
                    .build());
        }
        reDropTrajectory = robot.roadrun.trajectorySequenceBuilder(new Pose2d(dropX,dropY,Math.toRadians(-37)))
                .setReversed(false)
                .lineToLinearHeading(new     Pose2d(dropX+5.3,dropY-4.2+1.4,Math.toRadians(-37)))
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(dropX-1.0,dropY,Math.toRadians(-37)))
                .addTemporalMarker(robot::done)
                .build();

        park1trajectory = robot.roadrun.trajectorySequenceBuilder(dropTrajectory.get(4).end())
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(57, 14, Math.toRadians(90)), Math.toRadians(0))
                .build();
        park2trajectory = robot.roadrun.trajectorySequenceBuilder(dropTrajectory.get(4).end())
//                .lineToLinearHeading(new Pose2d(38, 10,dropA))
                .splineToLinearHeading(new Pose2d(36, 18,toRadians(270)), toRadians(270))
                .build();
        park3trajectory = robot.roadrun.trajectorySequenceBuilder(dropTrajectory.get(4).end())
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(dropX+4,dropY-9, toRadians(-5)),toRadians(dropA))
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(11,12, toRadians( 0)))
                .build();
        while (!op.isStarted() && !op.isStopRequested()) {
            dummyP = robot.checkRobotReadiness();
        }
        op.resetRuntime();
        robot.updateTime();
        robot.cv.observeStick();
        robot.setFirstLoop(true);
        lastTime = 1;
//        robot.cv.observeCone();
    }

    public void preload() {
        robot.followTrajectorySequenceAsync(preloadtrajectory);
        if (boosted) {
//            robot.delay(1.0);
//            robot.updateTrajectoryWithCam();
        }
        robot.delay(0.3);
        robot.raiseLiftArmToOuttake(true);
        robot.delay(0.5);
        robot.liftToPosition(LIFT_MED_JUNCTION);
        robot.wideClaw(false);
    }

    public boolean pick(int i) {

            robot.followTrajectorySequenceAsync(pick.get(i));

        if (boosted) {
            boolean[] vals = robot.checkIsOb(clObL.get(i), clObR.get(i),pick.get(0).end());
            clObL.set(i, vals[0]);
            clObR.set(i, vals[1]);
//            robot.delay(0.5);
//            robot.updateTrajectoryWithCone();
            robot.delay(0.4);
            clearObstacleL(i);
//            clearObstacleR(i);
        }
        robot.delay(0.1);
        robot.cycleLiftArmToCycle(true);
        robot.delay(0.1);
        robot.wideClaw();
        robot.delay(0.5);
        if(i==0){
            robot.iterateConestackUp();
        }
        else if (i != 4) {
            robot.iterateConestackDown();
        } else {
            robot.liftToPosition(0);
            robot.lowerLiftArmToIntake();
        }
        robot.closeClaw(false);
        if(boosted) {
            robot.delay(0.8);
        }
        if (boosted&& robot.queuer.queue(true, robot.queuer.isStarted()&&robot.clawSwitch.isSwitched())) {
            logger.log("/RobotLogs/GeneralRobot", "checking");
            return robot.clawSwitch.isSwitched();
        }
        return true;
    }

    public void drop(int i) {
        robot.followTrajectorySequenceAsync(dropTrajectory.get(i));
        if (boosted) {
//            robot.delay(0.1);
//            robot.updateTrajectoryWithCam();
        }

//        robot.delay(0.023 + 0.005 * (3 - i));
        robot.liftToPosition((int)LIFT_MED_JUNCTION.getValue()+20);
        robot.delay(0.15);
        robot.raiseLiftArmToOuttake(true);
        if (boosted) {
            robot.delay(0.00);
            boolean val = robot.poleInView(pInView.get(i+1));
            pInView.set(i+1, val);
            reDrop(i+1);
        }
        robot.delay(0.0);
        robot.wideClaw(false);
    }
    public void reDrop(int i){
        robot.followTrajectorySequenceAsync(reDropTrajectory,pInView.get(i));
    }

    public void clearObstacleL(int i) {
//        robot.setToNow(clObL.get(i));
        robot.delay(0.3);
        robot.clearObstacle(new Pose2d(56, 20, Math.toRadians(90)),clObL.get(i)||clObR.get(i));
//            robot.setStackHeight(i, clObL.get(i)||clObR.get(i));

//        if (robot.roadrun.getPoseEstimate().vec().distTo(pickupTrajectory.end().vec()) < 10) {
//            robot.changeTrajectorySequence(closeLTrajectory, clObL.get(i));
//        } else {
//            robot.changeTrajectorySequence(clearLTrajectory, clObL.get(i));
//        }
//        if (i == 0) {
//            robot.followTrajectorySequenceAsync(pickupTrajectory,clObL.get(i));
//        } else {
//            i--;
//            robot.followTrajectorySequenceAsync(pick.get(i),clObL.get(i));
//        }
    }

//    public void clearObstacleR(int i) {
//        robot.clearObstacle(new Pose2d(55, 19.5, Math.toRadians(90)),clObR.get(i));
//        robot.setStackHeight(i,clObR.get(i));
////        robot.done();
////        robot.setToNow(clObR.get(i));
////        if (robot.roadrun.getPoseEstimate().vec().distTo(pickupTrajectory.end().vec()) < 10) {
////            robot.changeTrajectorySequence(closeRTrajectory, clObR.get(i));
////        } else {
////            robot.changeTrajectorySequence(clearRTrajectory, clObR.get(i));
////        }        if (i == 0) {
////            robot.followTrajectorySequenceAsync(pickupTrajectory,clObR.get(i));
////        } else {
////            i--;
////            robot.followTrajectorySequenceAsync(pick.get(i),clObR.get(i));
////        }
//    }

    public boolean rePick() {
        return robot.clawSwitch.isSwitched();
    }

    public void reDrop() {

    }

    public void park() {
        if (dummyP == 1) {
            robot.followTrajectorySequenceAsync(park1trajectory,1);
        } else if (dummyP == 3) {
            robot.followTrajectorySequenceAsync(park3trajectory,1);
        } else {
            robot.followTrajectorySequenceAsync(park2trajectory,1);
        }
        robot.delay(2.0);
        robot.wideClaw(true);
        robot.delay(0.7);
        robot.liftToPosition(0,true);
        robot.delay(0.3);
        robot.lowerLiftArmToIntake(true);
    }

    public void update() {
        double loopTime = time - lastTime;
        if(!robot.queuer.isFirstLoop()&&loopTime>0.1){
            robot.setDistBroke(true);
        }
        lastTime = time;
        robot.setFirstLoop(false);
        robot.liftToTargetAuto();
        robot.roadrun.update();
        robot.updateClawStates();
        robot.updateLiftArmStates();
        robot.updateCV();
    }

    public void theWholeProgram() {
        init();
        abort:
        while ((time < 26.5 && (!robot.queuer.isFullfilled() || robot.queuer.isFirstLoop()))&&!op.isStopRequested()) {
            preload();
            for (int i = 0; i < 5; i++) {
                if (!pick(i)) {
                    break abort;
                }
                drop(i);
            }
            update();
        }
        robot.done();
        robot.queuer.reset();
        robot.done();
        while ((time < 29.8 && (!robot.queuer.isFullfilled() || robot.queuer.isFirstLoop()))&&!op.isStopRequested()) {
            park();
            update();
        }
        robot.stop();
    }
}