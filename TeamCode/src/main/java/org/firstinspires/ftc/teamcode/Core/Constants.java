package org.firstinspires.ftc.teamcode.Core;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.LookUpTable;

public class Constants {
    public static double n = 3.1415926;
    public static double gateAllow;
    public static double gateBlock;
    public static double passIn;
    public static double passOut;
    public static double passRest;
    public static double interpolatedHoodPos;
    public static Pose redGoalPose = new Pose(0,0, Math.toRadians(0));
    public static Pose blueGoalPose = new Pose(0,0, Math.toRadians(0));
    public static double interpolatedTargetRPM;
    public static double targetRPM;
    public static double restRPM;
    public static double targetDistance;
    public static LookUpTable LUT;
    public static double hoodMinPos;
    public static double hoodMaxPos;
    public static double skP, skI, skD, skV, skS;
}
