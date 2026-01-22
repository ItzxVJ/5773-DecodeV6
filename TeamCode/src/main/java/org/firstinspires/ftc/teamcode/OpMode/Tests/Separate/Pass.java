package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Pass Test")
public class Pass extends LinearOpMode {

    private DcMotorEx pass;
    private Servo gate;

    @Override
    public void runOpMode() {

        pass = hardwareMap.get(DcMotorEx.class, "pass");
        gate = hardwareMap.get(Servo.class, "gate");
        pass.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                pass.setPower(passIn);
            } else if (gamepad1.b) {
                pass.setPower(passOut);
            } else if (gamepad1.x) {
                pass.setPower(0.0);
            }
            if (gamepad1.dpad_left) {
                gate.setPosition(gateAllow);
            }
            if (gamepad1.dpad_right) {
                gate.setPosition(gateBlock);
            }

            telemetry.addLine("Controls:");
            telemetry.addLine("A  → Intake");
            telemetry.addLine("B  → Outtake");
            telemetry.addLine("X  → Rest");
            telemetry.addLine("D-Left  → GateAllow");
            telemetry.addLine("D-Right  → GateBlock");
            telemetry.addData("Current Power", pass.getPower());
            telemetry.update();
        }
    }
}
