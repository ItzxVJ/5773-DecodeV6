package org.firstinspires.ftc.teamcode.Core;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;


@Config
public class Constants {
    public static double n = 3.1415926;
    public static double gateAllow = 0.62;
    public static double gateBlock = 0.425;
    public static double gatePos;

    public static double passIn = 1;
    public static double passOut = -1;
    public static double passRest = 0;
    public static double interpolatedHoodPos;
    public static Pose redGoalPose = new Pose(76,-56);
    public static Pose blueGoalPose = new Pose(0,0, Math.toRadians(0));
    public static Pose lastPose = new Pose(0,0, Math.toRadians(0));
    public static double closeRPM = 550;
    public static double interpolatedTargetRPM;
    public static double targetRPM;
    public static double restRPM = 200;
    public static double targetDistance;

    public static double hoodClosePos = 0.1;
    public static double hoodPos;
    public static double hoodFarPos = 0.2;
    public static double hoodMinPos = 0;
    public static double hoodMaxPos = 0.5;
    public static double skP = 0.13, skI, skD, skV = 0.004, skS = 3.3;
    public static double fskP = 0.005, fskI, fskD, fskV = 0.0046, fskS = 0.9;
    public static double cskP = 0.005, cskI, cskD, cskV = 0.0046, cskS = 0.9;
    public static double fSpeed;
    public static double gDist;
    public static double commandedRPM;
    public static double computedRPM;
    public static double shootWait = 1.75;
    public static double gateWait = 1.75;
}
