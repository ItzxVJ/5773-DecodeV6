package org.firstinspires.ftc.teamcode.Core.Paths;
import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;

public class RedClose18V5 {

    public static Pose start = new Pose(64.7, -38.5, -2.39);
    public static Pose firstShootPos = new Pose(30, -10, -1);

    public static Pose preIntake1 = new Pose(-3,-22.9,-1.58);
    public static Pose intake1 = new Pose(-7,-46.77,-1.58);

    public static Pose secondShootControl = new Pose(5.7,-14.4,-1.55);
    public static Pose secondShootPos = new Pose(19.5,-8.5,-1.75);

    public static Pose intake2Control = new Pose(2,-15.5,-1.57);
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

    public static Pose park = new Pose(18.3,-16.6,-2.63);

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
                        0.8
                )
                .setBrakingStrength(brakingStrength)
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
                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(tConstraint)
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
                .setTValueConstraint(tConstraint)
                .build();
    }

    /* ---------------- SIXTH INTAKE ---------------- */

    public static PathChain farIntake(Follower f) {
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
                .setBrakingStrength(brakingStrength)
                .build();
    }

    /* ---------------- SEVENTH SHOOT ---------------- */

    public static PathChain seventhShoot(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        intake7,
                        seventhShootPos
                ))
                .setLinearHeadingInterpolation(
                        intake7.getHeading(),
                        seventhShootPos.getHeading(), 0.01
                )
                .setBrakingStrength(brakingStrength)
                .setTValueConstraint(tConstraint)
                .build();
    }

    /* ---------------- PARK ---------------- */

    public static PathChain lilPark(Follower f) {
        return f.pathBuilder()
                .addPath(new BezierLine(
                        seventhShootPos,
                        park
                ))
                .setLinearHeadingInterpolation(
                        seventhShootPos.getHeading(),
                        park.getHeading()
                )
                .setBrakingStrength(brakingStrength)
                .build();
    }
}
