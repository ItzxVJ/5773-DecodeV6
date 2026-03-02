package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.function.Supplier;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelLUT;
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
    public static boolean stop;

    private double cachedVoltage = 12.0;
    private double lastVoltageTime = 0;
    private double lastCommandedRPM = 0;

    FlywheelLUT.ShotData shot;

    @Override
    public void initialize() {
        controller = new FlywheelPIDFControl(ActiveOpMode.hardwareMap());
        shootL = new MotorEx("leftFly");
        shootR = new MotorEx("rightFly").reversed();

        // PIDF setup only once
        controller.setPIDF(fskS, fskV, fskP, fskI, fskD);
    }

    @Override
    public void periodic() {
        currentRPM = Math.abs(shootR.getVelocity());

        double now = System.nanoTime() / 1e9;
        if (now - lastVoltageTime > 0.1) {
            cachedVoltage = getBatteryVoltage();
            lastVoltageTime = now;
        }

        shot = lookup.getShotData(gDist);

        double targetRPM = shot.rpm;

        double maxDeltaRPM = 120;
        commandedRPM = MathFunctions.clamp(
                targetRPM,
                lastCommandedRPM - maxDeltaRPM,
                lastCommandedRPM + maxDeltaRPM
        );
        lastCommandedRPM = commandedRPM;

        double power = controller.update(commandedRPM, currentRPM, cachedVoltage);

        if (stop) {
            shootL.setPower(0);
            shootR.setPower(0);
        } else {
            shootL.setPower(power);
            shootR.setPower(power);
        }
    }

    public double distanceTo(Pose target, Pose robot) {
        return Math.hypot(
                target.getX() - robot.getX(),
                target.getY() - robot.getY()
        );
    }
    public Command calculations(
            Pose target,
            Supplier<Pose> robotPose,
            Supplier<Double> vx,
            Supplier<Double> vy) {
        return new LambdaCommand()
                .setUpdate(() -> {
                    Pose robot = robotPose.get();

                    double tFlight = estimateFlightTime(gDist);

                    double futureX = robot.getX() + vx.get() * tFlight;
                    double futureY = robot.getY() + vy.get() * tFlight;

                    gDist = Math.hypot(
                            target.getX() - futureX,
                            target.getY() - futureY
                    );

                    shot = lookup.getShotData(gDist);
                })
                .setIsDone(() -> false);
    }

    public Command updateDistanceRPM(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new LambdaCommand()
                .setUpdate(() -> gDist = distanceTo(target, robotPoseSupplier.get()))
                .setIsDone(() -> false);
    }

    public Command foreverRun() {
        return new LambdaCommand()
                .setUpdate(() -> stop = false)
                .setIsDone(() -> false);
    }

    public Command rest() {
        return new InstantCommand(() -> stop = false);
    }

    public Command stop() {
        return new InstantCommand(() -> stop = true);
    }

    public boolean isReady() {
        return Math.abs(currentRPM - commandedRPM) <= threshold
                && Math.abs(controller.getFilteredDerivative()) < 50;
    }

    private double getBatteryVoltage() {
        double minVoltage = 14.0;
        for (VoltageSensor sensor : ActiveOpMode.hardwareMap().getAll(VoltageSensor.class)) {
            minVoltage = Math.min(minVoltage, sensor.getVoltage());
        }

        return minVoltage;
    }

    public Command testing() {
        return new InstantCommand(() -> {
            commandedRPM = wanted;
            stop = false;
        });
    }
}