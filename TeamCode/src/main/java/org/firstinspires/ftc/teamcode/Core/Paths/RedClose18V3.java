package org.firstinspires.ftc.teamcode.Core.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class RedClose18V3 {
    public static double parametric = 0.94;

    public static Pose start = new Pose(64.7, -38.5, -2.39);
    public static Pose firstShootPos = new Pose(43, -24);

    public static Pose preIntake1 = new Pose(-3,-22.9,-1.58);
    public static Pose intake1 = new Pose(-3,-46.77,-1.58);

    public static Pose secondShootControl = new Pose(5.7,-14.4,-1.55);
    public static Pose secondShootPos = new Pose(19.5,-8.5,-1.75);

    public static Pose intake2Control = new Pose(-0.7,-15.5,-1.57);
    public static Pose intake2 = new Pose(-4.7,-52,-1.02);

    public static Pose thirdShootPos = new Pose(12.6,-3.9,-1.74);

    public static Pose intake3Control = intake2Control;
    public static Pose intake3 = intake2;

    public static Pose fourthShootPos = thirdShootPos;

    public static Pose intake4Control = intake3Control;
    public static Pose intake4 = intake3;

    public static Pose fifthShootControl = new Pose(-0.3,-20.5,-1.6);
    public static Pose fifthShootPos = new Pose(20.9,-17.9,-1.56);

    public static Pose intake6 = new Pose(20.5,-44.9,-1.57);

     public static Pose sixthShootPos = fifthShootPos;

    public static Pose intake7Control = new Pose(-29,-7,-1.61);
    public static Pose intake7 = new Pose(-32,-48.3,-1.61);

    public static Pose seventhShootPos = new Pose(22.4,-14.3,-2.63);

    public static Pose park = new Pose(18.3,-16.6,-2.64);

    /* ---------------- FIRST SHOOT & INTAKE ---------------- */

    public static PathChain firstShootAndIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        start,
                        firstShootPos,
                        preIntake1,
                        intake1
                ))
                .setLinearHeadingInterpolation(
                        start.getHeading(),
                        intake1.getHeading(),
                        0.15
                )
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- SECOND SHOOT ---------------- */

    public static PathChain secondShoot(Follower f, Runnable action) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        intake1,
                        secondShootControl,
                        secondShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake1.getHeading(),
                        secondShootPos.getHeading(),
                        0.3
                )
                .addParametricCallback(parametric, action)
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- SECOND INTAKE ---------------- */

    public static PathChain secondIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        secondShootPos,
                        intake2Control,
                        intake2
                ))
                .setLinearHeadingInterpolation(
                        secondShootPos.getHeading(),
                        intake2.getHeading(),
                        0.3
                )
                .build();
    }

    /* ---------------- THIRD SHOOT ---------------- */

    public static PathChain thirdShoot(Follower f, Runnable action) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        intake2,
                        thirdShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake2.getHeading(),
                        thirdShootPos.getHeading(),
                        0.3
                )
                .addParametricCallback(parametric, action)
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- THIRD INTAKE ---------------- */

    public static PathChain thirdIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        thirdShootPos,
                        intake3Control,
                        intake3
                ))
                .setLinearHeadingInterpolation(
                        thirdShootPos.getHeading(),
                        intake3.getHeading(),
                        0.3
                )
                .build();
    }

    /* ---------------- FOURTH SHOOT ---------------- */

    public static PathChain fourthShoot(Follower f, Runnable action) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        intake3,
                        fourthShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake3.getHeading(),
                        fourthShootPos.getHeading(),
                        0.3
                )
                .addParametricCallback(parametric, action)
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- FOURTH INTAKE ---------------- */

    public static PathChain fourthIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        fourthShootPos,
                        intake4Control,
                        intake4
                ))
                .setLinearHeadingInterpolation(
                        fourthShootPos.getHeading(),
                        intake4.getHeading(),
                        0.3
                )
                .build();
    }

    /* ---------------- FIFTH SHOOT ---------------- */

    public static PathChain fifthShoot(Follower f, Runnable action) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        intake4,
                        fifthShootControl,
                        fifthShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake4.getHeading(),
                        fifthShootPos.getHeading(),
                        0.3
                )
                .addParametricCallback(parametric, action)
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- FIFTH INTAKE ---------------- */

    public static PathChain fifthIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        fifthShootPos,
                        intake6
                ))
                .setLinearHeadingInterpolation(
                        fifthShootPos.getHeading(),
                        intake6.getHeading()
                )
                .setBrakingStrength(1)
                .build();
    }

    /* ---------------- SIXTH SHOOT ---------------- */

    public static PathChain sixthShoot(Follower f, Runnable action) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        intake6,
                        sixthShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake6.getHeading(),
                        sixthShootPos.getHeading(),
                        0.3
                )
                .addParametricCallback(parametric, action)
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- SIXTH INTAKE ---------------- */

    public static PathChain sixthIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        sixthShootPos,
                        intake7Control,
                        intake7
                ))
                .setLinearHeadingInterpolation(
                        sixthShootPos.getHeading(),
                        intake7.getHeading(),
                        0.3
                )
                .setBrakingStrength(2)
                .build();
    }

    /* ---------------- SEVENTH SHOOT ---------------- */

    public static PathChain seventhShoot(Follower f, Runnable action) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        intake7,
                        seventhShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake7.getHeading(),
                        seventhShootPos.getHeading(), 0.01
                )
                .setBrakingStrength(2)
                .addParametricCallback(parametric, action)
                .build();
    }

    /* ---------------- PARK ---------------- */

    public static PathChain park(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        seventhShootPos,
                        park
                ))
                .setLinearHeadingInterpolation(
                        seventhShootPos.getHeading(),
                        park.getHeading()
                )
                .setBrakingStrength(2)
                .build();
    }
}
