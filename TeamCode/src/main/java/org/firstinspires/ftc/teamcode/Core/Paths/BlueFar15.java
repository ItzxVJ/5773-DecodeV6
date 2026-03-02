package org.firstinspires.ftc.teamcode.Core.Paths;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueFar15 {

    /* ---------------- START ---------------- */

    public static Pose start = new Pose(-62.28, 14.97, 1.59);

    /* ---------------- SHOOT / INTAKE POSES ---------------- */

    public static Pose firstShootPos = new Pose(-51.34, 15.91, 1.59);

    public static Pose intake1Control = new Pose(-39.19, 29.88, 1.23);
    public static Pose intake1 = new Pose(-37.29, 47.92, 1.59);


    public static Pose secondShootPos = firstShootPos;
    public static Pose rawIntake2 = new Pose(-60.49, 57.81, 2.03);


    public static Pose thirdShootPos = firstShootPos;

    public static Pose rawIntake3Control = new Pose(-33.09, 40.84, 1.65);
    public static Pose rawIntake3 = new Pose(-62.4, 60.70, 3.10);

    public static Pose fourthShootPos = firstShootPos;

    public static Pose rawIntake4Control = rawIntake3Control;
    public static Pose rawIntake4 = rawIntake3;

    public static Pose fifthShootPos = firstShootPos;

    public static Pose park = new Pose(-45, 15.91, 1.59);

    /* ---------------- FIRST SHOOT ---------------- */

    public static PathChain firstShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        start,
                        firstShootPos
                ))
                .setLinearHeadingInterpolation(
                        start.getHeading(),
                        firstShootPos.getHeading()
                )
                .build();
    }

    /* ---------------- FIRST INTAKE ---------------- */

    public static PathChain firstIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        firstShootPos,
                        intake1Control,
                        intake1
                ))
                .setLinearHeadingInterpolation(
                        firstShootPos.getHeading(),
                        intake1.getHeading()
                )
                .build();
    }

    /* ---------------- SECOND SHOOT ---------------- */

    public static PathChain secondShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        intake1,
                        secondShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake1.getHeading(),
                        secondShootPos.getHeading(),
                        0.1
                )
                .build();
    }

    /* ---------------- RAW INTAKE 2 ---------------- */

    public static PathChain secondRawIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        secondShootPos,
                        rawIntake2
                ))
                .setLinearHeadingInterpolation(
                        secondShootPos.getHeading(),
                        rawIntake2.getHeading()
                )
                .build();
    }

    /* ---------------- THIRD SHOOT ---------------- */

    public static PathChain thirdShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        rawIntake2,
                        thirdShootPos
                ))
                .setLinearHeadingInterpolation(
                        rawIntake2.getHeading(),
                        thirdShootPos.getHeading(),
                        0.1
                )
                .setBrakingStrength(brakingStrength)
                .build();
    }

    /* ---------------- RAW INTAKE 3 ---------------- */

    public static PathChain thirdRawIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        thirdShootPos,
                        rawIntake3Control,
                        rawIntake3
                ))
                .setLinearHeadingInterpolation(
                        thirdShootPos.getHeading(),
                        rawIntake3.getHeading()
                )
                .setNoDeceleration()
                .build();
    }

    /* ---------------- FOURTH SHOOT ---------------- */

    public static PathChain fourthShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        rawIntake3,
                        fourthShootPos
                ))
                .setLinearHeadingInterpolation(
                        rawIntake3.getHeading(),
                        fourthShootPos.getHeading(),
                        0.1
                )
                .setBrakingStrength(brakingStrength)
                .build();
    }

    /* ---------------- RAW INTAKE 4 ---------------- */

    public static PathChain fourthRawIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        fourthShootPos,
                        rawIntake4Control,
                        rawIntake4
                ))
                .setLinearHeadingInterpolation(
                        fourthShootPos.getHeading(),
                        rawIntake4.getHeading()
                )
                .setNoDeceleration()
                .build();
    }

    /* ---------------- FIFTH SHOOT ---------------- */

    public static PathChain fifthShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        rawIntake4,
                        fifthShootPos
                ))
                .setLinearHeadingInterpolation(
                        rawIntake4.getHeading(),
                        fifthShootPos.getHeading(),
                        0.1
                )
                .setBrakingStrength(brakingStrength)
                .build();
    }

    /* ---------------- PARK ---------------- */

    public static PathChain lilPark(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        fifthShootPos,
                        park
                ))
                .setLinearHeadingInterpolation(
                        fifthShootPos.getHeading(),
                        park.getHeading()
                )
                .setBrakingStrength(brakingStrength)
                .build();
    }
}