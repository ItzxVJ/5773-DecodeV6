package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import static org.firstinspires.ftc.teamcode.Core.Constants.hoodPos;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "AB HoodZero")
public class Hood extends LinearOpMode {

    @Override
    public void runOpMode() {
        Servo rightHood = hardwareMap.get(Servo.class, "rightHood");
        Servo leftHood = hardwareMap.get(Servo.class, "leftHood");
        rightHood.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            rightHood.setPosition(hoodPos);
            leftHood.setPosition(hoodPos);

            telemetry.addLine("Hood Servo Test");
            telemetry.addLine("Change 'hoodPos' w/ Dashboard");
            telemetry.addData("Right Current Position", rightHood.getPosition());
            telemetry.addData("Left Current Position", leftHood.getPosition());
            telemetry.update();
        }
    }
}
