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

    /* ---------------- Hardware ---------------- */

    private final MotorEx turret = new MotorEx("turret", -1)
            .floatMode();

    /* ---------------- Constants ---------------- */

    // radians per tick
    public static double rpt = 0.00376689766;

    // encoder limits
    public static double lowLimit  = -683;
    public static double highLimit =  683;

    // control gains
    public static double kP = 0.0035;
    public static double kS = 0.1;

    /* ---------------- State ---------------- */

    private double targetTicks = 0.0;
    private double lastTime = 0.0;

    private final ElapsedTime timer = new ElapsedTime();

    /* ---------------- Lifecycle ---------------- */

    @Override
    public void initialize() {
        timer.reset();
        lastTime = timer.seconds();
    }

    @Override
    public void periodic() {
        // Clamp target instead of wrapping
        targetTicks = clamp(targetTicks, lowLimit, highLimit);

        double currentTicks = turret.getCurrentPosition();
        double error = targetTicks - currentTicks;

        double now = timer.seconds();
        double dt = now - lastTime;
        lastTime = now;
        if (dt <= 0) return;

        double power = kP * error;

        if (Math.abs(error) > 1) {
            power += Math.signum(error) * kS;
        }

        if (!turretIdle) {
            turret.setPower(clamp(power, -1.0, 1.0));
        } else {
            turret.setPower(0);
        }
    }

    /* ---------------- Yaw Control ---------------- */

    public void setYaw(double desiredYawRad) {
        double adjustedDesiredYaw = desiredYawRad + yawOffset;

        // Convert desired yaw directly to encoder ticks
        double desiredTicks = adjustedDesiredYaw / rpt;

        // Clamp instead of wrap
        targetTicks = clamp(desiredTicks, lowLimit, highLimit);
    }

    public double getYaw() {
        return turret.getCurrentPosition() * rpt;
    }

    public void face(Pose target, Pose robot) {
        double angleToTarget = Math.atan2(
                target.getY() - robot.getY(),
                target.getX() - robot.getX()
        );

        setYaw(angleToTarget - robot.getHeading());
    }

    /* ---------------- Commands ---------------- */

    public Command faceCommand(Pose target, Supplier<Pose> robotPoseSupplier) {
        return new LambdaCommand()
                .setUpdate(() -> face(target, robotPoseSupplier.get()))
                .setIsDone(() -> false);
    }

    public Command resetTurret() {
        return new InstantCommand(() -> {
            turret.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turret.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            turret.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
            targetTicks = 0.0;
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

    /* ---------------- Helpers ---------------- */

    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}
