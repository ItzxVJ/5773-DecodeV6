package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;
import org.firstinspires.ftc.teamcode.OpMode.Helpers.LookUpTable;

import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() {}
    FlywheelPIDFControl controller;
    MotorEx shootL, shootR;
    public static double threshold = 200;
    public static double currentRPM;
    public boolean run = false;

    @Override
    public void initialize() {
        controller = new FlywheelPIDFControl(ActiveOpMode.hardwareMap());
        shootL = new MotorEx("shootL");
        shootR = new MotorEx("shootR").reversed();
    }

    @Override
    public void periodic() {
        controller.setPIDF(skS, skV, skP, skI, skD);
        double[] result = LUT.lutGet(targetDistance);
        interpolatedTargetRPM = result[1];

        currentRPM = Math.abs((shootL.getVelocity() + shootR.getVelocity()) / 2);
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

    public boolean isReady() {
        return Math.abs(currentRPM - targetRPM) <= threshold;
    }

    public Command rest() {
        return new InstantCommand(() -> targetRPM = restRPM);
    }

    public Command run() {
        return new InstantCommand(() -> targetRPM = interpolatedTargetRPM);
    }

    public Command stop() {
        return new InstantCommand(() -> targetRPM = 0);
    }
}
