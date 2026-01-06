package org.firstinspires.ftc.teamcode.OpMode.Tests;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

@Config
@TeleOp(name = "Flywheel Test")
public class Flywheel extends LinearOpMode {

    DcMotorEx shootL, shootR;
    FlywheelPIDFControl controller;

    public static double targetVel;

    @Override
    public void runOpMode() {

        controller = new FlywheelPIDFControl(hardwareMap);

        shootL = hardwareMap.get(DcMotorEx.class, "shootL");
        shootR = hardwareMap.get(DcMotorEx.class, "shootR");
        shootR.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            controller.setPIDF(skS, skV, skP, skI, skD);

            double[] result = LUT.lutGet(targetDistance);
            interpolatedTargetRPM = result[1];

            double currentRPM =
                    Math.abs((shootL.getVelocity() + shootR.getVelocity()) / 2);

            double batteryVoltage = getBatteryVoltage();
            double power = controller.update(targetVel, currentRPM, batteryVoltage);

            shootL.setPower(power);
            shootR.setPower(power);

            telemetry.addLine("Flywheel Status");
            telemetry.addData("Target Velocity", targetVel);
            telemetry.addData("Interpolated RPM", interpolatedTargetRPM);
            telemetry.addData("Current RPM", currentRPM);
            telemetry.addData("Error", targetVel - currentRPM);
            telemetry.addData("Battery Voltage", batteryVoltage);
            telemetry.addData("Motor Power", power);
            telemetry.update();
        }
    }

    public double getBatteryVoltage() {
        double minVoltage = 14.0;
        for (VoltageSensor sensor : hardwareMap.getAll(VoltageSensor.class)) {
            minVoltage = Math.min(minVoltage, sensor.getVoltage());
        }
        return minVoltage;
    }
}
