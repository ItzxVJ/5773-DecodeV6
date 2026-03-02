package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.Supplier;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

@Config
public class NextTurret implements Subsystem {

    public static final NextTurret INSTANCE = new NextTurret();
    private NextTurret() {}

    private final MotorEx turret = new MotorEx("turret", -1);

    public static double rpt = 0.00364031593695225;

    public static double lowLimit  = -750;
    public static double highLimit =  750;

    public static double kP = 0.003;
    public static double kD = 0.0002;
    public static double kS = 0.125;
    public static double minPower = 0.05;

    public static double finekP = 0.004;
    public static double finekD = 0.0001;

    public static double fineZoneTicks = 40;
    public static double toleranceTicks = 2;

    public static double targetAlpha = 0.25;
    public static double targetTicks = 0.0;
    public static double filteredTargetTicks = 0.0;

    private double lastError = 0.0;
    private double lastTime = 0.0;
    public static double currentTicks;

    public static double TURRET_X_OFFSET = -2.15; // inches
    public static double TURRET_Y_OFFSET = 0.0;

    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void initialize() {
        timer.reset();
        lastTime = timer.seconds();

        turret.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turret.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void periodic() {

        double now = timer.seconds();
        double dt = now - lastTime;
        lastTime = now;

        dt = Math.max(0.001, Math.min(dt, 0.05));

        targetTicks = clamp(targetTicks, lowLimit, highLimit);

        currentTicks = turret.getCurrentPosition();
        double rawError = targetTicks - currentTicks;

        if (Math.abs(rawError) < 30) {
            filteredTargetTicks = targetTicks;
        } else {
            filteredTargetTicks =
                    (1.0 - targetAlpha) * filteredTargetTicks +
                            targetAlpha * targetTicks;
        }

        double error = filteredTargetTicks - currentTicks;

        double derivative = (error - lastError) / dt;
        lastError = error;

        double coarseOutput = (kP * error) + (kD * derivative);

        double fineOutput = (finekP * error) + (finekD * derivative);

        double blend;
        if (Math.abs(error) >= fineZoneTicks) {
            blend = 1.0;
        } else {
            blend = Math.abs(error) / fineZoneTicks;
        }

        double power =
                (blend * coarseOutput) +
                        ((1.0 - blend) * fineOutput);

        if (Math.abs(error) > toleranceTicks) {
            power += Math.signum(error) * kS;
        }

        if (Math.abs(power) < minPower) {
            power = Math.signum(power) * minPower;
        }

        boolean pushingLower = currentTicks <= lowLimit + 3 && power < 0;
        boolean pushingUpper = currentTicks >= highLimit - 3 && power > 0;

        if (pushingLower || pushingUpper) {
            power = 0;
        }

        turret.setPower(clamp(power, -1.0, 1.0));
    }

    public void setYaw(double desiredYawRad) {
        double adjustedYaw = desiredYawRad + yawOffset;
        targetTicks = clamp(adjustedYaw / rpt, lowLimit, highLimit);
    }

    public void face(Pose target, Pose robot) {
        Pose turretPose = getTurretPose(robot);

        double angleToTarget = Math.atan2(
                target.getY() - turretPose.getY(),
                target.getX() - turretPose.getX()
        );

        setYaw(angleToTarget - robot.getHeading());
    }

    public Command faceCommand(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new LambdaCommand()
                .setUpdate(() -> face(target, robotPoseSupplier.get()))
                .setIsDone(() -> false);
    }

    public Command resetTurret() {
        return new InstantCommand(() -> {
            turret.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turret.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            targetTicks = 0.0;
            filteredTargetTicks = 0.0;
            lastError = 0.0;
        });
    }

    public Command resetTheTurret() {
        return new InstantCommand(() -> {
            turret.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            targetTicks = currentTicks;
            filteredTargetTicks = 0.0;
            lastError = 0.0;
        });
    }

    public Command addYaw() {
        return new InstantCommand(() -> yawOffset += yawStepRad);
    }

    public Command decreaseYaw() {
        return new InstantCommand(() -> yawOffset -= yawStepRad);
    }

    public Command resetYaw() {
        return new InstantCommand(() -> yawOffset = 0.0);
    }
    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    public void faceWhileMoving(
            Pose goal,
            Pose robot,
            double vx,
            double vy
    ) {

        double tFlight = estimateFlightTime(gDist);

        double futureX = robot.getX() + vx * tFlight;
        double futureY = robot.getY() + vy * tFlight;

        Pose futureRobot = new Pose(futureX, futureY, robot.getHeading());

        Pose turretFuture = getTurretPose(futureRobot);

        double angle = Math.atan2(
                goal.getY() - turretFuture.getY(),
                goal.getX() - turretFuture.getX()
        );

        setYaw(angle - robot.getHeading());
    }
    public Command faceWhileMovingCommand(
            Pose goal,
            Supplier<Pose> robotPose,
            Supplier<Double> vx,
            Supplier<Double> vy
    ) {
        return new LambdaCommand()
                .setUpdate(() -> faceWhileMoving(
                        goal,
                        robotPose.get(),
                        vx.get(),
                        vy.get()
                ))
                .setIsDone(() -> false);
    }

    private Pose getTurretPose(Pose robot) {
        double cos = Math.cos(robot.getHeading());
        double sin = Math.sin(robot.getHeading());

        double tx = robot.getX()
                + TURRET_X_OFFSET * cos
                - TURRET_Y_OFFSET * sin;

        double ty = robot.getY()
                + TURRET_X_OFFSET * sin
                + TURRET_Y_OFFSET * cos;

        return new Pose(tx, ty, robot.getHeading());
    }
}