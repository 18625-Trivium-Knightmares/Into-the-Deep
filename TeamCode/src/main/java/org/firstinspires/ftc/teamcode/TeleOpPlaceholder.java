// this is placeholder for teleop ignore this if you want to
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "Robot Centric Drive Placeholder", group = "skunked")
@Disabled
public class TeleOpPlaceholder extends LinearOpMode {

    DcMotor FR, FL, BR, BL;
    public static long defaultdelay = 1000;
    public static double defaultpower = 1;
    public static double defaultnegativepower = -1;

    @Override
    public void runOpMode() {
        // skunked teleop code
        FR = hardwareMap.get(DcMotor.class, "rightFront");
        FL = hardwareMap.get(DcMotor.class, "leftFront");
        BR = hardwareMap.get(DcMotor.class, "rightBack");
        BL = hardwareMap.get(DcMotor.class, "leftBack");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        FR.setPower(defaultpower);
        FL.setPower(defaultpower);
        BR.setPower(defaultpower);
        BL.setPower(defaultpower);

        sleep(defaultdelay);

        FR.setPower(defaultnegativepower);
        FL.setPower(defaultnegativepower);
        BR.setPower(defaultpower);
        BL.setPower(defaultpower);

        sleep(defaultdelay);

        FR.setPower(defaultnegativepower);
        FL.setPower(defaultnegativepower);
        BR.setPower(defaultnegativepower);
        BL.setPower(defaultnegativepower);

        sleep(defaultdelay);

        FR.setPower(defaultpower);
        FL.setPower(defaultpower);
        BR.setPower(defaultnegativepower);
        BL.setPower(defaultnegativepower);

        sleep(defaultdelay);
    }
}




