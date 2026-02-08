package org.firstinspires.ftc.teamcode.OpMode.Helpers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelPIDFControl {

    private double kS, kV;
    private double kP, kI, kD;

    private static final double maxIntegral = 500;
    private static final double dAlpha = 0.85;

    private double integral = 0;
    private double lastError = 0;
    private double lastTime = 0;
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

        // Reset integral if target jumps
        if (Math.abs(targetRPM - lastTargetRPM) > 50) {
            integral = 0;
        }

        double error = targetRPM - currentRPM;

        // Integral with clamp
        integral += error * dt;
        integral = Math.max(-maxIntegral, Math.min(maxIntegral, integral));

        // Derivative (filtered)
        double rawDerivative = (error - lastError) / dt;
        filteredDerivative = dAlpha * filteredDerivative + (1 - dAlpha) * rawDerivative;

        // PID
        double pid =
                (kP * error) +
                        (kI * integral) +
                        (kD * filteredDerivative);

        // Acceleration feedforward
        double accel = (targetRPM - lastTargetRPM) / dt;

        // Added (required for stability)
        // accel feedforward
        double kA = 0.0002;
        double feedforward =
                (kS * Math.signum(targetRPM)) +
                        (kV * targetRPM) +
                        (kA * accel);

        double voltage = pid + feedforward;
        double power = voltage / batteryVoltage;

        power = Math.max(-1.0, Math.min(1.0, power));

        lastError = error;
        lastTargetRPM = targetRPM;
        lastTime = currentTime;

        return power;
    }

    // Optional but useful for readiness checks
    public double getFilteredDerivative() {
        return filteredDerivative;
    }
}
