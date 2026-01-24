package org.firstinspires.ftc.teamcode.Subsystems;

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

    private final MotorEx turret = new MotorEx("turret", -1)
            .floatMode();


    // DO NOT CHANGE (empirically verified)
    public static double rpt = 0.0036277061427;

    public static double lowLimit  = -807;
    public static double highLimit =  866;

    public static double kP = 0.01;
    public static double kD = 0.0;
    public static double kS = 0.05;

    private double targetTicks = 0;
    private double lastError = 0;
    private double lastTime = 0;

    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void initialize() {
        timer.reset();
        lastTime = timer.seconds();
    }

    @Override
    public void periodic() {

        double currentTicks = turret.getCurrentPosition();
        double error = targetTicks - currentTicks;

        double now = timer.seconds();
        double dt = now - lastTime;
        lastTime = now;
        if (dt <= 0) return;

        double p = kP * error;
        double d = kD * ((error - lastError) / dt);

        double power = p + d;

        if (Math.abs(error) > 1) {
            power += Math.signum(error) * kS;
        }

        if (currentTicks <= lowLimit && power < 0) power = 0;
        if (currentTicks >= highLimit && power > 0) power = 0;

        turret.setPower(clamp(power, -1.0, 1.0));
        lastError = error;
    }

    public void setYaw(double desiredYawRad) {
        double currentYaw = getYaw();
        double error = normalizeAngle(desiredYawRad - currentYaw);
        targetTicks = clamp(
                turret.getCurrentPosition() + (error / rpt),
                lowLimit,
                highLimit
        );
        lastError = 0;
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
            targetTicks = 0;
        });
    }

    private static double normalizeAngle(double a) {
        a %= (2 * Math.PI);
        if (a <= -Math.PI) a += 2 * Math.PI;
        if (a > Math.PI) a -= 2 * Math.PI;
        return a;
    }

    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}
