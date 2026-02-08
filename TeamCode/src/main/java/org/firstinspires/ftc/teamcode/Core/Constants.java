package org.firstinspires.ftc.teamcode.Core;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelLUT;


@Config
public class Constants {
    public static double gateAllow = 0.62;
    public static double gateBlock = 0.425;
    public static double gatePos;

    public static double passIn = -1;
    public static double passOut = 1;
    public static double passRest = 0;
    public static Pose redGoalPose = new Pose(76,-56);
    public static Pose blueGoalPose = new Pose(0,0, Math.toRadians(0));
    public static Pose lastPose = new Pose(0,0, Math.toRadians(0));
    public static double interpolatedTargetRPM;
    public static double targetRPM;
    public static double restRPM = 200;

    public static double hoodPos;
    public static double skP = 0.13, skI, skD, skV = 0.004, skS = 3.3;
    public static double gDist;
    public static double commandedRPM;
    public static double computedRPM;
    public static double shootWait = 1;
    public static double gateWait = 0.8;
    public static double intakePower;
    public static double wanted = 0;
    public static double tConstraint = 0.7;
    public static double brakingStrength = 1.6;
    public static double yawOffset = 0;
    public static double yawStepRad = 0.1;
    public static boolean turretIdle = true;
    public static FlywheelLUT lookup = new FlywheelLUT();
}
