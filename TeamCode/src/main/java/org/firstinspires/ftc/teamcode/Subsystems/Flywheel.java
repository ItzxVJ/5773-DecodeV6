package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() {}
    FlywheelPIDFControl controller;
    MotorEx shootL, shootR;
    public double kS, kV, kP, kI, kD;
    public double targetRPM;

    @Override
    public void initialize() {
        controller = new FlywheelPIDFControl(ActiveOpMode.hardwareMap());
        shootL = new MotorEx("shootL");
        shootR = new MotorEx("shootR").reversed();
    }

    @Override
    public void periodic() {
        controller.setPIDF(kS, kV, kP, kI, kD);

        double currentRPM = Math.abs((shootL.getVelocity() + shootR.getVelocity()) / 2);
        double batteryVoltage = getBatteryVoltage();
        double power = controller.update(targetRPM, currentRPM, batteryVoltage);

        shootL.setPower(power);
        shootR.setPower(power);
    }

    public double getBatteryVoltage() {
        double minVoltage = 14.0;
        for (VoltageSensor sensor : ActiveOpMode.hardwareMap().getAll(VoltageSensor.class)) {
            minVoltage = Math.min(minVoltage, sensor.getVoltage());
        }
        return minVoltage;
    }
}
