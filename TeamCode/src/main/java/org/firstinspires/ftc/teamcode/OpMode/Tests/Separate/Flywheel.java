package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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



    FtcDashboard dashboard;

    @Override
    public void runOpMode() {

        dashboard = FtcDashboard.getInstance();

        controller = new FlywheelPIDFControl(hardwareMap);

        shootL = hardwareMap.get(DcMotorEx.class, "leftFly");
        shootR = hardwareMap.get(DcMotorEx.class, "rightFly");
        shootR.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            controller.setPIDF(skS, skV, skP, skI, skD);

            double currentRPM =
                    Math.abs((shootL.getVelocity() + shootR.getVelocity()) / 2);

            double batteryVoltage = getBatteryVoltage();
            double power = controller.update(targetRPM, currentRPM, batteryVoltage);
            double error = targetRPM - currentRPM;

            shootL.setPower(power);
            shootR.setPower(power);

            telemetry.addLine("Flywheel Status");
            telemetry.addData("Target Velocity", targetRPM);
            telemetry.addData("Interpolated RPM", interpolatedTargetRPM);
            telemetry.addData("Current RPM", currentRPM);
            telemetry.addData("Error", error);
            telemetry.addData("Battery Voltage", batteryVoltage);
            telemetry.addData("Motor Power", power);
            telemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target RPM", targetRPM);
            packet.put("Current RPM", currentRPM);
            packet.put("Error", error);
            packet.put("Battery Voltage", batteryVoltage);
            packet.put("Motor Power", power);

            dashboard.sendTelemetryPacket(packet);
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
