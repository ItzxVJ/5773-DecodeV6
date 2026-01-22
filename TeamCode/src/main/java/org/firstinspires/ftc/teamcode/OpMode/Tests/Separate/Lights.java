package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Lights Test")
public class Lights extends LinearOpMode {

    private RevBlinkinLedDriver lights;

    @Override
    public void runOpMode() {

        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            } else if (gamepad1.b) {
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            } else if (gamepad1.x) {
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            } else if (gamepad1.y) {
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }

            telemetry.addLine("Controls:");
            telemetry.addLine("A  → Red");
            telemetry.addLine("B  → Blue");
            telemetry.addLine("X  → Green");
            telemetry.addLine("Y  → Purple");
            telemetry.update();
        }
    }
}
