package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.function.Supplier;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
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
                0.000010076 * Math.pow(dist, 4)
                        - 0.00402775 * Math.pow(dist, 3)
                        + 0.566131 * Math.pow(dist, 2)
                        - 27.51827 * dist
                        + 1226.37926,
                700, 1700
        );

    }

    public Command calcRPM(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new InstantCommand(() -> {
            gDist  = distanceTo(target, robotPoseSupplier.get());
            computedRPM = flywheelSpeed(gDist);
        });
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
                .setStart(() -> {stop = false;});
//                .setUpdate(() -> commandedRPM = computedRPM)
//                .setIsDone(() -> (chill = true) || (stop = true) || (rev = true));
    }

    public Command instantRun() {
        return new InstantCommand(() -> {commandedRPM = computedRPM; stop = false;});
    }

    public Command rest() {
        return new InstantCommand(() -> {commandedRPM = restRPM; stop = false;});
    }

    public Command stop() {
        return new InstantCommand(() -> stop = true);
    }

    public Command rev() {
        return new InstantCommand(() -> {commandedRPM = 1000; stop = false;});
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
