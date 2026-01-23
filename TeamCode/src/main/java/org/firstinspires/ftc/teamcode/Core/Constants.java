package org.firstinspires.ftc.teamcode.Core;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.LookUpTable;

@Config
public class Constants {
    public static double n = 3.1415926;
    public static double gateAllow = 0.6;
    public static double gateBlock = 0.4;
    public static double gatePos;

    public static double passIn = 1;
    public static double passOut = -1;
    public static double passRest = 0;
    public static double interpolatedHoodPos;
    public static Pose redGoalPose = new Pose(69,-54);
    public static Pose blueGoalPose = new Pose(0,0, Math.toRadians(0));
    public static double closeRPM = 550;
    public static double interpolatedTargetRPM;
    public static double targetRPM;
    public static double restRPM = 200;
    public static double targetDistance;
    public static LookUpTable LUT;
    public static double hoodClosePos = 0.1;
    public static double hoodPos;
    public static double hoodFarPos = 0.2;
    public static double hoodMinPos = 0;
    public static double hoodMaxPos = 0.5;
    public static double skP = 0.01, skI, skD, skV = 0.01067, skS = 0.3;
}
