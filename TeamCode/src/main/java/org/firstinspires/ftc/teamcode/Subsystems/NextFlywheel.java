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

    public static double currentRPM = 0;
    public static boolean stop;

    private double cachedVoltage = 12.0;
    private double lastVoltageTime = 0;
    FlywheelLUT.ShotData shot;

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

        // Cache voltage (does not need to update every loop)
        double now = System.nanoTime() / 1e9;
        if (now - lastVoltageTime > 0.1) {
            cachedVoltage = getBatteryVoltage();
            lastVoltageTime = now;
        }

        double power = controller.update(commandedRPM, currentRPM, cachedVoltage);
        shot = lookup.getShotData(gDist);

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

    public static double flywheelSpeed(double dist) {
        return MathFunctions.clamp(
                -0.0000448751 * Math.pow(dist, 4)
                        + 0.0173673 * Math.pow(dist, 3)
                        - 2.40241 * Math.pow(dist, 2)
                        + 146.22618 * dist
                        - 2355.79014,
                700, 1700
        );
    }

    public Command calcRPM(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new InstantCommand(() -> {
            gDist = distanceTo(target, robotPoseSupplier.get());
            computedRPM = flywheelSpeed(gDist);
        });
    }

    public Command updateDistanceRPM(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new LambdaCommand()
                .setUpdate(() -> {
                    gDist = distanceTo(target, robotPoseSupplier.get());
                    computedRPM = shot.rpm;
                })
                .setIsDone(() -> false);
    }

    public Command run() {
        return new LambdaCommand()
                .setStart(() -> stop = false);
    }

    public Command testing() {
        return new InstantCommand(() -> {
            commandedRPM = wanted;
            stop = false;
        });
    }

    public Command instantRun() {
        return new InstantCommand(() -> {
            commandedRPM = computedRPM;
            stop = false;
        });
    }

    public Command rest() {
        return new InstantCommand(() -> {
            commandedRPM = restRPM;
            stop = false;
        });
    }

    public Command stop() {
        return new InstantCommand(() -> stop = true);
    }

    public Command farRev() {
        return new InstantCommand(() -> {
            commandedRPM = 1800;
            stop = false;
        });
    }

    public Command closeRev() {
        return new InstantCommand(() -> {
            commandedRPM = 1000;
            stop = false;
        });
    }

    public boolean isReady() {
        return Math.abs(currentRPM - commandedRPM) <= threshold
                && Math.abs(controller.getFilteredDerivative()) < 50;
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
