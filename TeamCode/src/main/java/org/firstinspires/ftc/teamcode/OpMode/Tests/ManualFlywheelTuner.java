package org.firstinspires.ftc.teamcode.OpMode.Tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

@Config
@TeleOp(name = "ManualFlywheelTuner")
public class ManualFlywheelTuner extends LinearOpMode {

    public DcMotorEx shootL, shootR;
    public double targetRPM;
    public double kS, kV, kP, kI, kD;

    FlywheelPIDFControl controller;

    @Override
    public void runOpMode() {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        shootL = hardwareMap.get(DcMotorEx.class, "shootL");
        shootR = hardwareMap.get(DcMotorEx.class, "shootR");
        shootR.setDirection(DcMotorSimple.Direction.REVERSE);

        controller = new FlywheelPIDFControl(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            controller.setPIDF(kS, kV, kP, 0, kD);

            double currentRPM = Math.abs((shootL.getVelocity() + shootR.getVelocity()) / 2);
            double batteryVoltage = getBatteryVoltage();
            double power = controller.update(targetRPM, currentRPM, batteryVoltage);

            shootL.setPower(power);
            shootR.setPower(power);

            telemetry.addData("Target RPM", targetRPM);
            telemetry.addData("Current RPM", currentRPM);
            telemetry.addData("Power", power);
            telemetry.addData("kP", kP);
            telemetry.addData("kI", kI);
            telemetry.addData("kD", kD);

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target RPM", targetRPM);
            packet.put("Current RPM", currentRPM);
            packet.put("Power", power);
            packet.put("Error", targetRPM - currentRPM);
            dashboard.sendTelemetryPacket(packet);

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
