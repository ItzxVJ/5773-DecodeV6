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
@TeleOp(name = "Flywheel PIDFVS Test", group = "Testing")
public class Flywheel extends LinearOpMode {

    public static double targetRPM = 0;

    @Override
    public void runOpMode() {

        /* ===== Dashboard ===== */
        FtcDashboard dashboard = FtcDashboard.getInstance();

        /* ===== Controller ===== */
        FlywheelPIDFControl controller = new FlywheelPIDFControl(hardwareMap);

        /* ===== Motors ===== */
        DcMotorEx shootL = hardwareMap.get(DcMotorEx.class, "leftFly");
        DcMotorEx shootR = hardwareMap.get(DcMotorEx.class, "rightFly");

        shootR.setDirection(DcMotorSimple.Direction.REVERSE);

        shootL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shootR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            /* ===== Push global PIDF into controller ===== */
            controller.setPIDF(skS, skV, skP, skI, skD);

            /* ===== Measure RPM (match subsystem) ===== */
            double currentRPM = Math.abs(shootR.getVelocity());

            /* ===== Battery voltage ===== */
            double batteryVoltage = getBatteryVoltage();

            /* ===== Compute power ===== */
            double power = controller.update(
                    targetRPM,
                    currentRPM,
                    batteryVoltage
            );

            /* ===== Apply power ===== */
            shootL.setPower(power);
            shootR.setPower(power);

            /* ===== Error ===== */
            double error = targetRPM - currentRPM;

            /* ===== Driver Station Telemetry ===== */
            telemetry.addLine("Flywheel PIDF Test");
            telemetry.addData("Target RPM", targetRPM);
            telemetry.addData("Current RPM", currentRPM);
            telemetry.addData("Error", error);
            telemetry.addData("Motor Power", power);
            telemetry.addData("Battery Voltage", batteryVoltage);
            telemetry.update();

            /* ===== Dashboard Telemetry ===== */
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target RPM", targetRPM);
            packet.put("Current RPM", currentRPM);
            packet.put("Error", error);
            packet.put("Motor Power", power);
            packet.put("Battery Voltage", batteryVoltage);

            dashboard.sendTelemetryPacket(packet);
        }

        /* ===== Stop motors on exit ===== */
        shootL.setPower(0);
        shootR.setPower(0);
    }

    private double getBatteryVoltage() {
        double minVoltage = 14.0;
        for (VoltageSensor sensor : hardwareMap.getAll(VoltageSensor.class)) {
            minVoltage = Math.min(minVoltage, sensor.getVoltage());
        }
        return minVoltage;
    }
}