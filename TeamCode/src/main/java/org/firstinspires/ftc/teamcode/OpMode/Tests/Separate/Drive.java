package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp(name = "Drive Test")
public class Drive extends LinearOpMode {

    @Override
    public void runOpMode() {

        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "leftBack");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "rightBack");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y / 2;
            double x = -gamepad1.left_stick_x / 2;
            double rx = gamepad1.right_stick_x / 2;

            double frontLeftPower  = y + x + rx;
            double backLeftPower   = y - x + rx;
            double frontRightPower = y - x - rx;
            double backRightPower  = y + x - rx;

            double max = Math.max(
                    Math.abs(frontLeftPower),
                    Math.max(Math.abs(backLeftPower),
                            Math.max(Math.abs(frontRightPower), Math.abs(backRightPower)))
            );
            if (max > 1.0) {
                frontLeftPower  /= max;
                backLeftPower   /= max;
                frontRightPower /= max;
                backRightPower  /= max;
            }

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            telemetry.addLine("Drive Test!");
            telemetry.addData("Drive", x);
            telemetry.addData("Strafe", y);
            telemetry.addData("Rotate", rx);
            telemetry.update();
        }
    }
}
