package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "LiftTest")
public class Lift extends LinearOpMode {

    @Override
    public void runOpMode() {

        CRServo leftLift = hardwareMap.get(CRServo.class, "leftLift");
        CRServo rightLift = hardwareMap.get(CRServo.class, "rightLift");

        // Reverse one side if needed so both move the same physical direction
        leftLift.setDirection(CRServo.Direction.REVERSE);
        rightLift.setDirection(CRServo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {

            // FTC convention: pushing joystick up gives negative values
            double leftPower  = -gamepad1.left_stick_y;
            double rightPower = -gamepad1.right_stick_y;

            leftLift.setPower(leftPower);
            rightLift.setPower(rightPower);

            telemetry.addData("Left Lift Power", leftPower);
            telemetry.addData("Right Lift Power", rightPower);
            telemetry.update();
        }
    }
}