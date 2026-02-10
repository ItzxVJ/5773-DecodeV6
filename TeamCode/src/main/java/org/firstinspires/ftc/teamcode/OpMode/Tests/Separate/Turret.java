package org.firstinspires.ftc.teamcode.OpMode.Tests.Separate;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Turret Test")
@Config
public class Turret extends LinearOpMode {

    /* ---------- CONVERSION ---------- */

    // radians per encoder tick
    public static double rpt = 0.0036277061427;

    /* ---------- PID GAINS ---------- */

    public static double kP = 0.00;
    public static double kD = 0.000;

    public static double sP = 0;
    public static double sD = 0;

    // static friction compensation
    public static double kS = 0.1;

    // error threshold (ticks) for coarse vs fine PID
    public static double pidSwitchTicks = 5;

    /* ---------- SOFT LIMITS (ENCODER TICKS) ---------- */

    public static double lowThresholdTicks = -807;   // set later
    public static double highThresholdTicks = 866;   // set later

    /* ---------- TARGET ---------- */

    // target turret angle (radians)
    public static double targetYawRad = 0.0;

    /* ---------- HARDWARE ---------- */

    private DcMotorEx turret;

    /* ---------- TIMING ---------- */

    private final ElapsedTime timer = new ElapsedTime();
    private double lastTime = 0.0;
    private double lastError = 0.0;
    private boolean firstLoop = true;

    /* ---------- DASHBOARD ---------- */

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() {

        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        timer.reset();
        lastTime = timer.seconds();

        while (opModeIsActive()) {

            /* ---------- STATE ---------- */

            double currentTicks = turret.getCurrentPosition();

            double normalizedYaw = normalizeAngle(targetYawRad);
            double unclampedTargetTicks = normalizedYaw / rpt;

            // clamp target inside soft limits
            double targetTicks = Math.max(
                    lowThresholdTicks,
                    Math.min(highThresholdTicks, unclampedTargetTicks)
            );

            double error = targetTicks - currentTicks;

            /* ---------- TIME ---------- */

            double currentTime = timer.seconds();
            double dt = currentTime - lastTime;
            lastTime = currentTime;

            if (dt <= 0) continue;

            /* ---------- COARSE / FINE ---------- */

            boolean coarse = Math.abs(error) > pidSwitchTicks;

            double kp = coarse ? kP : sP;
            double kd = coarse ? kD : sD;

            /* ---------- PD ---------- */

            double p = kp * error;

            double d = 0.0;
            if (!firstLoop) {
                d = kd * ((error - lastError) / dt);
            } else {
                firstLoop = false;
            }

            double power = p + d;

            /* ---------- STATIC FRICTION ---------- */

            if (Math.abs(error) > 1) {
                power += Math.signum(error) * kS;
            }

            /* ---------- CLAMP POWER ---------- */

            power = Math.max(-1.0, Math.min(1.0, power));

            /* ---------- HARD SOFT-LIMIT ENFORCEMENT ---------- */

            if (currentTicks <= lowThresholdTicks && power < 0) {
                power = 0.0;
            }
            if (currentTicks >= highThresholdTicks && power > 0) {
                power = 0.0;
            }

            turret.setPower(power);
            lastError = error;

            /* ---------- DASHBOARD TELEMETRY ---------- */

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("targetYawRad", targetYawRad);
            packet.put("targetTicks", targetTicks);
            packet.put("currentTicks", currentTicks);
            packet.put("errorTicks", error);
            packet.put("power", power);
            packet.put("P", p);
            packet.put("D", d);
            packet.put("kp", kp);
            packet.put("kd", kd);
            packet.put("dt", dt);
            packet.put("lowLimit", lowThresholdTicks);
            packet.put("highLimit", highThresholdTicks);

            dashboard.sendTelemetryPacket(packet);

            /* ---------- DRIVER STATION TELEMETRY ---------- */

            telemetry.addData("Target (rad)", targetYawRad);
            telemetry.addData("Target Ticks", targetTicks);
            telemetry.addData("Current Ticks", currentTicks);
            telemetry.addData("Error", error);
            telemetry.addData("Power", power);
            telemetry.update();
        }
    }

    /* ---------- UTIL ---------- */

    // normalizes angle to (-π, π]
    private double normalizeAngle(double angleRadians) {
        double angle = angleRadians % (Math.PI * 2);
        if (angle <= -Math.PI) angle += Math.PI * 2;
        if (angle > Math.PI) angle -= Math.PI * 2;
        return angle;
    }
}
