package org.firstinspires.ftc.teamcode.OpMode.Helpers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class FlywheelPIDFControl {

    private double kS, kV;
    private double kP, kI, kD;

    private double integral = 0;
    private double lastError = 0;
    private double lastTime = 0;

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
        if (dt <= 0) dt = 0.02;

        double error = targetRPM - currentRPM;

        integral += error * dt;
        double derivative = (error - lastError) / dt;

        double pid = (kP * error) + (kI * integral) + (kD * derivative);
        double feedforward = (kS * Math.signum(targetRPM)) + (kV * targetRPM);

        double voltage = pid + feedforward;
        double power = voltage / batteryVoltage;

        power = Math.max(-1.0, Math.min(1.0, power));

        lastError = error;
        lastTime = currentTime;

        return power;
    }
}
