package org.firstinspires.ftc.teamcode.OpMode.Helpers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelPIDFControl {

    private double kS, kV;
    private double kP, kI, kD;

    private static final double MAX_INTEGRAL = 500;
    private static final double D_ALPHA = 0.85;
    private static final double kA = 0.0002;

    private double integral = 0;
    private double lastError = 0;
    private double lastTime;
    private double lastTargetRPM = 0;
    private double filteredDerivative = 0;

    public FlywheelPIDFControl(HardwareMap hardwareMap) {
        lastTime = System.nanoTime() / 1e9;
    }

    public void setPIDF(double kS, double kV, double kP, double kI, double kD) {
        this.kS = kS;
        this.kV = kV;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double update(double targetRPM, double currentRPM, double batteryVoltage) {

        double currentTime = System.nanoTime() / 1e9;
        double dt = currentTime - lastTime;
        if (dt <= 0 || dt > 0.1) dt = 0.02;

        if (Math.abs(targetRPM - lastTargetRPM) > 50) {
            integral = 0;
        }

        double error = targetRPM - currentRPM;

        integral += error * dt;
        integral = Math.max(-MAX_INTEGRAL, Math.min(MAX_INTEGRAL, integral));

        double rawDerivative = (error - lastError) / dt;
        filteredDerivative = D_ALPHA * filteredDerivative + (1 - D_ALPHA) * rawDerivative;

        double pid =
                (kP * error) +
                        (kI * integral) +
                        (kD * filteredDerivative);

        double accel = (targetRPM - lastTargetRPM) / dt;

        double feedforward =
                (kS * Math.signum(targetRPM)) +
                        (kV * targetRPM) +
                        (kA * accel);

        double power = (pid + feedforward) / batteryVoltage;
        power = Math.max(-1.0, Math.min(1.0, power));

        lastError = error;
        lastTargetRPM = targetRPM;
        lastTime = currentTime;

        return power;
    }

    public double getFilteredDerivative() {
        return filteredDerivative;
    }
}