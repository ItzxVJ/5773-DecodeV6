package org.firstinspires.ftc.teamcode.OpMode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "Hood Test")
public class Hood extends LinearOpMode {

    public static double hoodPos = 0;

    @Override
    public void runOpMode() {
        Servo hood = hardwareMap.get(Servo.class, "hood");

        waitForStart();

        while (opModeIsActive()) {
            hood.setPosition(hoodPos);

            telemetry.addLine("Hood Servo Test");
            telemetry.addLine("Change 'hoodPos' w/ Dashboard");
            telemetry.addData("Current Position", hood.getPosition());
            telemetry.update();
        }
    }
}
