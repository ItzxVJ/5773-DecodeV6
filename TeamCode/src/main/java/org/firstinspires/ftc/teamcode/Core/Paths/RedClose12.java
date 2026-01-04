package org.firstinspires.ftc.teamcode.Core.Paths;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class RedClose12 {
    public static Pose start = new Pose(0,0, Math.toRadians(0));
    public static Pose firstShootPos = new Pose(0,0, Math.toRadians(0));
    public static Pose intake1Control = new Pose(0,0, Math.toRadians(0));
    public static Pose intake1 = new Pose(0,0, Math.toRadians(0));
    public static Pose secondShootPosControl = new Pose(0,0, Math.toRadians(0));
    public static Pose secondShootPos = new Pose(0,0, Math.toRadians(0));
    public static Pose intake2Control = new Pose(0,0, Math.toRadians(0));
    public static Pose intake2 = new Pose(0,0, Math.toRadians(0));
    public static Pose thirdShootPosControl = new Pose(0,0, Math.toRadians(0));
    public static Pose thirdShootPos = new Pose(0,0, Math.toRadians(0));
    public static Pose intake3Control = new Pose(0,0, Math.toRadians(0));
    public static Pose intake3 = new Pose(0,0, Math.toRadians(0));
    public static Pose fourthShootPosControl = new Pose(0,0, Math.toRadians(0));
    public static Pose fourthShootPos = new Pose(0,0, Math.toRadians(0));
    public static Pose parkPos = new Pose(0,0, Math.toRadians(0));

    public static PathChain firstShoot = follower().pathBuilder()
            .addPath(new BezierLine(start, firstShootPos))
            .setLinearHeadingInterpolation(start.getHeading(), firstShootPos.getHeading())
            .build();
    public static PathChain firstIntake = follower().pathBuilder()
            .addPath(new BezierCurve(firstShootPos, intake1Control, intake1))
            .setLinearHeadingInterpolation(firstShootPos.getHeading(), intake1.getHeading())
            .build();
    public static PathChain secondShoot = follower().pathBuilder()
            .addPath(new BezierCurve(intake1, secondShootPosControl, secondShootPos))
            .setLinearHeadingInterpolation(intake1.getHeading(), secondShootPos.getHeading())
            .build();
    public static PathChain secondIntake = follower().pathBuilder()
            .addPath(new BezierCurve(secondShootPos, intake2Control, intake2))
            .setLinearHeadingInterpolation(secondShootPos.getHeading(), intake2.getHeading())
            .build();
    public static PathChain thirdShoot = follower().pathBuilder()
            .addPath(new BezierCurve(intake2, thirdShootPosControl, thirdShootPos))
            .setLinearHeadingInterpolation(intake2.getHeading(), thirdShootPos.getHeading())
            .build();
    public static PathChain thirdIntake = follower().pathBuilder()
            .addPath(new BezierCurve(thirdShootPos, intake3Control, intake3))
            .setLinearHeadingInterpolation(thirdShootPos.getHeading(), intake3.getHeading())
            .build();
    public static PathChain fourthShoot = follower().pathBuilder()
            .addPath(new BezierCurve(intake3, fourthShootPosControl, fourthShootPos))
            .build();
    public static PathChain park = follower().pathBuilder()
            .addPath(new BezierLine(fourthShootPos, parkPos))
            .setLinearHeadingInterpolation(fourthShootPos.getHeading(), parkPos.getHeading())
            .build();
}