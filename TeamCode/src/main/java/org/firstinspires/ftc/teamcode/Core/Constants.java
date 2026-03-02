package org.firstinspires.ftc.teamcode.Core;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelLUT;


@Config
public class Constants {
    public static double gateAllow = 0.61;
    public static double gateBlock = 0.39;
    public static double gatePos;


    public static double xOffset = -8.86;
    public static double yOffset = -11;
    public static double hOffset = 0.04;
    public static double passIn = 1;
    public static double passOut = -1;
    public static double passRest = 0;
    public static Pose redGoalPose = new Pose(72,-72);
  //  public static Pose center = new Pose(7.39, 8.97);
    public static Pose blueGoalPose = new Pose(72,72);
    public static Pose lastPose = new Pose(0,0, 0);
    public static double interpolatedTargetRPM;
    public static double targetRPM;
    public static double restRPM = 200;

    public static double hoodPos;
    public static double skS, skV, skP, skI, skD, skA;
    public static double cskP = 0.2, cskI, cskD, cskV = 0.0038, cskS = 1.55, cskA;
    public static double fskP = 0.04, fskI, fskD = 0.0005, fskV = 0.0043, fskS = 0.8;
    public static double gDist;
    public static double commandedRPM;
    public static double computedRPM;
    public static double shootWait = 0.85;
    public static double shootWaitGateClose = 0.65;
    public static double gateWait = 1;
    public static double intakePower;
    public static double wanted = 0;
    public static double tConstraint = 0.65;
    public static double brakingStrength = 1.6;
    public static double yawOffset = 0;
    public static double yawStepRad = 0.04;
    public static FlywheelLUT lookup = new FlywheelLUT();
    public static double wheelDiameterIn = 4.5;
    public static double efficiency = 0.6d;
    public static double currentRPM;

    public static double TOF_A = 0.55;
    public static double TOF_B = 0.00347;    // sec per inch


    public static double estimateFlightTime(double distanceInches) {
        return TOF_A + TOF_B * distanceInches;
    }

    public static boolean tracking = false;






}
