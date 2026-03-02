//package org.firstinspires.ftc.teamcode.zTrash;
//
//import static org.firstinspires.ftc.teamcode.Core.Constants.*;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.VoltageSensor;
//
//import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;
//
//import dev.nextftc.ftc.ActiveOpMode;
//
//@Config
//@TeleOp(name = "Flywheel + Pass Test")
//public class ShootIntake extends LinearOpMode {
//
//    /* ---------- HARDWARE ---------- */
//    DcMotorEx shootL, shootR;
//    DcMotorEx pass;
//
//    FlywheelPIDFControl controller;
//    FtcDashboard dashboard;
//    Servo gate;
//
//    @Override
//    public void runOpMode() {
//
//        /* ---------- INIT ---------- */
//        dashboard = FtcDashboard.getInstance();
//
//        controller = new FlywheelPIDFControl(ActiveOpMode.hardwareMap());
//
//        shootL = hardwareMap.get(DcMotorEx.class, "leftFly");
//        shootR = hardwareMap.get(DcMotorEx.class, "rightFly");
//        gate = hardwareMap.get(Servo.class, "gate");
//        shootR.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        pass = hardwareMap.get(DcMotorEx.class, "pass");
//        pass.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        waitForStart();
//        targetRPM = 500;
//
//        /* ---------- LOOP ---------- */
//        while (opModeIsActive()) {
//
//            /* ================= FLYWHEEL ================= */
//
//            //controller.setPIDF(skS, skV, skP, skI, skD, );
//
//            double currentRPM =
//                    Math.abs((shootL.getVelocity() + shootR.getVelocity()) / 2);
//
//            double batteryVoltage = getBatteryVoltage();
//            double power = 0.0;
//            double error = 0.0;
//
//            // Flywheel only runs while LEFT BUMPER is held
//            if (gamepad1.left_bumper) {
//                power = controller.update(targetRPM, currentRPM, batteryVoltage);
//                error = targetRPM - currentRPM;
//            }
//
//            shootL.setPower(power);
//            shootR.setPower(power);
//
//            /* ================= PASS / INTAKE ================= */
//
//            if (gamepad1.a) {
//                pass.setPower(passIn);
//            } else if (gamepad1.b) {
//                pass.setPower(passOut);
//            } else if (gamepad1.x) {
//                pass.setPower(0.0);
//            }
//            if (gamepad1.dpad_left) {
//                gate.setPosition(gateAllow);
//            }
//            if (gamepad1.dpad_right) {
//                gate.setPosition(gateBlock);
//            }
//
//            /* ================= DRIVER STATION TELEMETRY ================= */
//
//            telemetry.addLine("Flywheel");
//            telemetry.addData("Enabled", gamepad1.left_bumper);
//            telemetry.addData("Target RPM", targetRPM);
//            telemetry.addData("Current RPM", currentRPM);
//            telemetry.addData("Error", error);
//            telemetry.addData("Motor Power", power);
//
//            telemetry.addLine("");
//            telemetry.addLine("Pass / Intake");
//            telemetry.addLine("A → +1 | B → -1 | X → 0");
//            telemetry.addData("Pass Power", pass.getPower());
//
//            telemetry.addData("Battery Voltage", batteryVoltage);
//            telemetry.update();
//
//            /* ================= FTC DASHBOARD TELEMETRY ================= */
//
//            TelemetryPacket packet = new TelemetryPacket();
//            packet.put("Flywheel Enabled", gamepad1.left_bumper);
//            packet.put("Target RPM", targetRPM);
//            packet.put("Current RPM", currentRPM);
//            packet.put("Error", error);
//            packet.put("Flywheel Power", power);
//            packet.put("Pass Power", pass.getPower());
//            packet.put("Battery Voltage", batteryVoltage);
//
//            dashboard.sendTelemetryPacket(packet);
//        }
//    }
//
//    /* ---------- UTIL ---------- */
//    public double getBatteryVoltage() {
//        double minVoltage = 14.0;
//        for (VoltageSensor sensor : hardwareMap.getAll(VoltageSensor.class)) {
//            minVoltage = Math.min(minVoltage, sensor.getVoltage());
//        }
//        return minVoltage;
//    }
//}
