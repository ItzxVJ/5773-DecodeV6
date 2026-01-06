package org.firstinspires.ftc.teamcode.OpMode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "Gate Test")
public class Gate extends LinearOpMode {

    public static double gatePos = 0;

    @Override
    public void runOpMode() {
        Servo gate = hardwareMap.get(Servo.class, "gate");

        waitForStart();

        while (opModeIsActive()) {
            gate.setPosition(gatePos);

            telemetry.addLine("Gate Servo Test");
            telemetry.addLine("Change 'gatePos' w/ Dashboard");
            telemetry.addData("Current Position", gate.getPosition());
            telemetry.update();
        }
    }
}
