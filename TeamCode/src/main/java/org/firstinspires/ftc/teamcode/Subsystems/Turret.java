package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

@Config
public class Turret implements Subsystem {

    public static final Turret INSTANCE = new Turret();
    private Turret() {}

    private final MotorEx turret = new MotorEx("turret").floatMode();

    public static double rpt = 0.0029919;

    // Coarse PID (large error)
    public static double kP = 0.003;
    public static double kD = 0.000;
    public static double kF = 0.0;

    // Fine PID (small error)
    public static double sP = 0.005;
    public static double sD = 0.0001;

    public static double pidSwitch = 30; // ticks

    public static double target = 0;
    public static double error = 0;
    public static double power = 0;

    public static boolean manual = false;
    public static double manualPower = 0;

    private double lastError = 0;
    private double lastTime = 0;
    private double integral = 0;
    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void initialize() {
        timer.reset();
        lastTime = timer.seconds();
    }

    @Override
    public void periodic() {

        if (manual) {
            turret.setPower(manualPower);
            return;
        }

        double current = turret.getCurrentPosition();
        error = target - current;

        double currentTime = timer.seconds();
        double dt = currentTime - lastTime;
        lastTime = currentTime;

        if (dt <= 0) return;

        boolean coarse = Math.abs(error) > pidSwitch;

        double kp = coarse ? kP : sP;
        double kd = coarse ? kD : sD;

        double p = kp * error;
        double d = kd * ((error - lastError) / dt);

        double ff = coarse ? Math.signum(error) * kF : 0;

        power = p + d + ff;
        power = Math.max(-1.0, Math.min(1.0, power));

        turret.setPower(power);
        lastError = error;
    }

    public void setTurretTarget(double ticks) {
        target = ticks;
        integral = 0;
        lastError = 0;
    }

    public double getTurret() {
        return turret.getCurrentPosition();
    }

    public double getYaw() {
        return normalizeAngle(getTurret() * rpt);
    }

    public void setYaw(double radians) {
        radians = normalizeAngle(radians);
        setTurretTarget(radians / rpt);
    }

    public void addYaw(double radians) {
        setYaw(getYaw() + radians);
    }

    public void face(Pose targetPose, Pose robotPose) {
        double angleToTarget = Math.atan2(
                targetPose.getY() - robotPose.getY(),
                targetPose.getX() - robotPose.getX()
        );
        setYaw(normalizeAngle(angleToTarget - robotPose.getHeading()));
    }

    public void manual(double power) {
        manual = true;
        manualPower = power;
    }

    public void automatic() {
        manual = false;
    }

    public boolean isReady() {
        return Math.abs(error) < pidSwitch;
    }

    public static double normalizeAngle(double angleRadians) {
        double angle = angleRadians % (Math.PI * 2);
        if (angle <= -Math.PI) angle += Math.PI * 2;
        if (angle > Math.PI) angle -= Math.PI * 2;
        return angle;
    }

    public Command faceCommand(Pose targetPose, Pose robotPose) {
        return new LambdaCommand()
                .setUpdate(() -> face(targetPose, robotPose));
    }

    public Command resetTurret() {
        return new InstantCommand(() -> {
            turret.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turret.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            setTurretTarget(0);
        });
    }
}
