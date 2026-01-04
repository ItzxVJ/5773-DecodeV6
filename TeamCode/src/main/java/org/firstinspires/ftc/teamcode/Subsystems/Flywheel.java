package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.targetRPM;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() {}
    MotorEx shootL, shootR;
    ControlSystem controller;
    public double kP, kI, kD;

    @Override
    public void initialize() {
        shootL = new MotorEx("shootL");
        shootR = new MotorEx("shootR").reversed();

        controller = ControlSystem.builder()
                .velPid(kP, kI, kD)
                .build();

        controller.goal = new KineticState(0);
    }

    @Override
    public void periodic() {
        shootL.setPower(controller.calculate(
                shootL.getCurrentPosition(),
                shootL.getVelocity()
        ));
        shootR.setPower(controller.calculate(
                shootR.getCurrentPosition(),
                shootR.getVelocity()
        ));
    }

    public double getBatteryVoltage() {
        double minVoltage = 14.0;
        for (VoltageSensor sensor : ActiveOpMode.hardwareMap().getAll(VoltageSensor.class)) {
            minVoltage = Math.min(minVoltage, sensor.getVoltage());
        }
        return minVoltage;
    }
}
