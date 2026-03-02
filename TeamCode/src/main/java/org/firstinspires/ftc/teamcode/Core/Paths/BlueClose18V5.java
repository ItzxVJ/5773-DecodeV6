package org.firstinspires.ftc.teamcode.Core.Paths;
import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;

public class BlueClose18V5 {

    public static Pose start = new Pose(55.27, 48.60, 2.42);
    public static Pose firstShootPos = new Pose(14.10, 22.05, 2.36);

    public static Pose preIntake1 = new Pose(-12.52, 34.93, 1.57);
    public static Pose intake1 = new Pose(-17, 52.96, 1.60);

    public static Pose secondShootPos = new Pose(0.28, 13.93, 1.53);

    public static Pose intake2Control = new Pose(-13.33, 37, 1.58);
    public static Pose intake2 = new Pose(-12.16, 63, 1.042);
    public static Pose releaseGate = new Pose(-12.16, 63, 1.042);



    public static Pose thirdShootPos = secondShootPos;

    public static Pose intake3Control = intake2Control;
    public static Pose intake3 = intake2;

    public static Pose fourthShootPos = thirdShootPos;

    public static Pose intake4Control = intake3Control;
    public static Pose intake4 = intake3;

    public static Pose fifthShootControl = new Pose(-10.97, 38.99, 1.56);
    public static Pose fifthShootPos = new Pose(13.7, 27.4, 1.63);

    public static Pose intake6 = new Pose(10.89, 45.93, 1.63);

    public static Pose sixthShootPos = fifthShootPos;

    public static Pose park = new Pose(11.60, 32.12, 1.63);

    /* ---------------- FIRST SHOOT & INTAKE ---------------- */

    public static PathChain firstShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        start,
                        firstShootPos
                ))
                .setLinearHeadingInterpolation(
                        start.getHeading(),
                        intake1.getHeading(),
                        0.15
                )
                .setTValueConstraint(0.4)
                .setBrakingStrength(brakingStrength)
                .build();
    }

    public static PathChain firstIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        firstShootPos,
                        preIntake1,
                        intake1
                ))
                .setLinearHeadingInterpolation(
                        start.getHeading(),
                        intake1.getHeading(),
                        0.9
                )
                .setNoDeceleration()
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
                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(tConstraint)
                .build();
    }

    /* ---------------- SECOND INTAKE ---------------- */

    public static PathChain gate1(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        secondShootPos,
                        intake2Control,
                        intake2
                ))
                .setHeadingInterpolation(
                        HeadingInterpolator.piecewise(
                                new HeadingInterpolator.PiecewiseNode(
                                        0,0.5, HeadingInterpolator.constant(secondShootPos.getHeading())
                                ),
                                new HeadingInterpolator.PiecewiseNode(
                                        0.5, 1, HeadingInterpolator.linear(secondShootPos.getHeading(), intake2.getHeading())
                                )
                        )
                )
                .build();
    }

    /* ---------------- THIRD SHOOT ---------------- */

    public static PathChain thirdShoot(Follower f) {
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
                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(tConstraint)
                .build();
    }

    /* ---------------- THIRD INTAKE ---------------- */

    public static PathChain gate2(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        thirdShootPos,
                        intake3Control,
                        intake3
                ))
                .setHeadingInterpolation(
                        HeadingInterpolator.piecewise(
                                new HeadingInterpolator.PiecewiseNode(
                                        0,0.5, HeadingInterpolator.constant(thirdShootPos.getHeading())
                                ),
                                new HeadingInterpolator.PiecewiseNode(
                                        0.5, 1, HeadingInterpolator.linear(thirdShootPos.getHeading(), intake3.getHeading())
                                )
                        )
                )
                .build();
    }

    /* ---------------- FOURTH SHOOT ---------------- */

    public static PathChain fourthShoot(Follower f) {
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
                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(tConstraint)
                .build();
    }

    /* ---------------- FOURTH INTAKE ---------------- */

    public static PathChain gate3(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        fourthShootPos,
                        intake4Control,
                        intake4
                ))
                .setHeadingInterpolation(
                        HeadingInterpolator.piecewise(
                                new HeadingInterpolator.PiecewiseNode(
                                        0,0.5, HeadingInterpolator.constant(fourthShootPos.getHeading())
                                ),
                                new HeadingInterpolator.PiecewiseNode(
                                        0.5, 1, HeadingInterpolator.linear(fourthShootPos.getHeading(), intake4.getHeading())
                                )
                        )
                )
                .build();
    }

    /* ---------------- FIFTH SHOOT ---------------- */

    public static PathChain fifthShoot(Follower f) {
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
                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(tConstraint)
                .build();
    }

    /* ---------------- FIFTH INTAKE ---------------- */

    public static PathChain closeIntake(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        fifthShootPos,
                        intake6
                ))
                .setLinearHeadingInterpolation(
                        fifthShootPos.getHeading(),
                        intake6.getHeading()
                )
                .setTValueConstraint(tConstraint)
                .setNoDeceleration()
                .build();
    }

    /* ---------------- SIXTH SHOOT ---------------- */

    public static PathChain sixthShoot(Follower f) {
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

                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(0.5)
                .build();
    }

    /* ---------------- PARK ---------------- */

    public static PathChain lilPark(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        sixthShootPos,
                        park
                ))
                .setLinearHeadingInterpolation(
                        sixthShootPos.getHeading(),
                        park.getHeading()
                )
                .setBrakingStrength(brakingStrength)
                .build();
    }
}
