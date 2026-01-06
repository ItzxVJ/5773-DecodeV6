package org.firstinspires.ftc.teamcode.OpMode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Turret Test")
@Config
public class Turret extends LinearOpMode {
    public static double rpt = 0.0029919;

    public static double kP = 0.003;
    public static double kD = 0.000;
    public static double sP = 0.005;
    public static double sD = 0.0001;

    public static double pidSwitch = 30;
    public static double targetYawRad = 0.0;

    private DcMotorEx turret;
    private final ElapsedTime timer = new ElapsedTime();

    private double lastError = 0;
    private double lastTime = 0;

    @Override
    public void runOpMode() {

        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

        timer.reset();
        lastTime = timer.seconds();

        while (opModeIsActive()) {

            double currentTicks = turret.getCurrentPosition();

            double normalizedYaw = normalizeAngle(targetYawRad);
            double targetTicks = normalizedYaw / rpt;

            double error = targetTicks - currentTicks;

            double currentTime = timer.seconds();
            double dt = currentTime - lastTime;
            lastTime = currentTime;
            if (dt <= 0) continue;

            boolean coarse = Math.abs(error) > pidSwitch;

            double kp = coarse ? kP : sP;
            double kd = coarse ? kD : sD;

            double p = kp * error;
            double d = kd * ((error - lastError) / dt);

            double power = p + d;
            power = Math.max(-1.0, Math.min(1.0, power));

            turret.setPower(power);
            lastError = error;

            telemetry.addData("Target Yaw (rad)", targetYawRad);
            telemetry.addData("Normalized Yaw (rad)", normalizedYaw);
            telemetry.addData("Target Ticks", targetTicks);
            telemetry.addData("Current Ticks", currentTicks);
            telemetry.addData("Error", error);
            telemetry.addData("Power", power);
            telemetry.update();
        }
    }

    private double normalizeAngle(double angleRadians) {
        double angle = angleRadians % (Math.PI * 2);
        if (angle <= -Math.PI) angle += Math.PI * 2;
        if (angle > Math.PI) angle -= Math.PI * 2;
        return angle;
    }
}
