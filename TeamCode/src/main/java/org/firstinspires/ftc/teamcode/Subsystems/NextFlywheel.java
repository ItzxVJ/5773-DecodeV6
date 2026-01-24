package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.function.Supplier;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

@Config
public class NextFlywheel implements Subsystem {

    public static final NextFlywheel INSTANCE = new NextFlywheel();
    private NextFlywheel() {}

    private FlywheelPIDFControl controller;
    private MotorEx shootL, shootR;

    public static double threshold = 30;

    public static double currentRPM = 0;
    public static boolean chill;
    public static boolean stop;

    @Override
    public void initialize() {
        controller = new FlywheelPIDFControl(ActiveOpMode.hardwareMap());
        shootL = new MotorEx("leftFly");
        shootR = new MotorEx("rightFly").reversed();
    }

    @Override
    public void periodic() {

        controller.setPIDF(skS, skV, skP, skI, skD);

        currentRPM = Math.abs(shootR.getVelocity());
        double voltage = getBatteryVoltage();

        double power = controller.update(commandedRPM, currentRPM, voltage);

        shootL.setPower(power);
        shootR.setPower(power);
    }

    public double distanceTo(Pose target, Pose robot) {
        return Math.hypot(
                target.getX() - robot.getX(),
                target.getY() - robot.getY()
        );
    }

    public static double flywheelSpeed(double dist) {
        if (dist >= 61.1) { // Far RPM Equation
            return MathFunctions.clamp(
                    0.000256182 * Math.pow(dist, 3)
                            + 0.103637 * Math.pow(dist, 2)
                            + 18.01228 * dist
                            + 227.82463,
                    700, 1700
            );
        } else { // Close RPM Equation
            return MathFunctions.clamp(
                    -0.108666 * Math.pow(dist, 2)
                            + 16.99431 * dist
                            + 466.29551,
                    700, 1700
            );
        }
    }


    public Command updateDistanceRPM(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new LambdaCommand()
                .setUpdate(() -> {
                    gDist = distanceTo(target, robotPoseSupplier.get());
                    computedRPM = flywheelSpeed(gDist);
                })
                .setIsDone(() -> false);
    }

    public Command run() {
        return new LambdaCommand()
                .setStart(() -> {chill = false; stop = false;})
                .setUpdate(() -> commandedRPM = computedRPM)
                .setIsDone(() -> (chill = true) || (stop = true));
    }

    public Command rest() {
        return new InstantCommand(() -> {commandedRPM = restRPM; chill = true;});
    }

    public Command stop() {
        return new InstantCommand(() -> {commandedRPM = 0; stop = true;});
    }

    public boolean isReady() {
        return currentRPM >= commandedRPM - threshold &&
                currentRPM <= commandedRPM + threshold;
    }


    private double getBatteryVoltage() {
        double minVoltage = 14.0;
        for (VoltageSensor sensor :
                ActiveOpMode.hardwareMap().getAll(VoltageSensor.class)) {
            minVoltage = Math.min(minVoltage, sensor.getVoltage());
        }
        return minVoltage;
    }
}
