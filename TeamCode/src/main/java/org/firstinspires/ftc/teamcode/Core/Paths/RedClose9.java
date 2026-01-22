package org.firstinspires.ftc.teamcode.Core.Paths;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.follower.Follower;

public final class BasicRedClose12 {
    public static Pose start = new Pose(63.6, -39.3, -2.4);
    public static Pose firstShootPos = new Pose(30.6, -14.3, -2.1);
    public static Pose pre1Intake = new Pose(20.3, -23.5, -1.6);
    public static Pose intake1 = new Pose(19.3, -43.1, -1.6);
    public static Pose secondShootPos = new Pose(30.6, -14.3, -2.1);
    public static Pose pre2Intake = new Pose(-3.8, -23.2, -1.6);
    public static Pose intake2 = new Pose(-4.5, -41.6, -1.6);
    public static Pose thirdShootPos = new Pose(30.6, -14.3, -2.1);
    public static Pose pre3Intake = new Pose(-27.6, -24, -1.6);
    public static Pose intake3 = new Pose(-28.4, -41.6, -1.6);
    public static Pose fourthShootPos = new Pose(30.6, -14.3, -2.1);
    public static Pose parkPos = new Pose(17.5, -19.5, -0.7);

    public static PathChain firstShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, firstShootPos))
                .setLinearHeadingInterpolation(
                        start.getHeading(),
                        firstShootPos.getHeading()
                )
                .build();
    }

    public static PathChain firstPreIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(firstShootPos, pre1Intake))
                .setLinearHeadingInterpolation(
                        firstShootPos.getHeading(),
                        pre1Intake.getHeading()
                )
                .build();
    }

    public static PathChain firstIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(pre1Intake, intake1))
                .setLinearHeadingInterpolation(
                        pre1Intake.getHeading(),
                        intake1.getHeading()
                )
                .build();
    }

    public static PathChain secondShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(intake1, secondShootPos))
                .setLinearHeadingInterpolation(
                        intake1.getHeading(),
                        secondShootPos.getHeading()
                )
                .build();
    }

    public static PathChain secondPreIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(secondShootPos, pre2Intake))
                .setLinearHeadingInterpolation(
                        secondShootPos.getHeading(),
                        pre2Intake.getHeading()
                )
                .build();
    }

    public static PathChain secondIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(pre2Intake, intake2))
                .setLinearHeadingInterpolation(
                        pre2Intake.getHeading(),
                        intake2.getHeading()
                )
                .build();
    }

    public static PathChain thirdShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(intake2, thirdShootPos))
                .setLinearHeadingInterpolation(
                        intake2.getHeading(),
                        thirdShootPos.getHeading()
                )
                .build();
    }

    public static PathChain thirdPreIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(thirdShootPos, pre3Intake))
                .setLinearHeadingInterpolation(
                        thirdShootPos.getHeading(),
                        pre3Intake.getHeading()
                )
                .build();
    }

    public static PathChain thirdIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(pre3Intake, intake3))
                .setLinearHeadingInterpolation(
                        pre3Intake.getHeading(),
                        intake3.getHeading()
                )
                .build();
    }

    public static PathChain fourthShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(intake3, fourthShootPos))
                .setLinearHeadingInterpolation(
                        intake3.getHeading(),
                        fourthShootPos.getHeading()
                )
                .build();
    }

    public static PathChain park(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(fourthShootPos, parkPos))
                .setLinearHeadingInterpolation(
                        fourthShootPos.getHeading(),
                        parkPos.getHeading()
                )
                .build();
    }
}