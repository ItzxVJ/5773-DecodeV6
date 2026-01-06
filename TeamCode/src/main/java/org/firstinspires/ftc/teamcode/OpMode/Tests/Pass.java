package org.firstinspires.ftc.teamcode.OpModes.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Pass Test")
public class Pass extends LinearOpMode {

    private DcMotorEx pass;

    @Override
    public void runOpMode() {

        pass = hardwareMap.get(DcMotorEx.class, "pass");
        pass.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                pass.setPower(1.0);
            } else if (gamepad1.b) {
                pass.setPower(-1.0);
            } else if (gamepad1.x) {
                pass.setPower(0.0);
            }

            telemetry.addLine("Controls:");
            telemetry.addLine("A  → Power = +1");
            telemetry.addLine("B  → Power = -1");
            telemetry.addLine("X  → Power =  0");
            telemetry.addData("Current Power", pass.getPower());
            telemetry.update();
        }
    }
}
