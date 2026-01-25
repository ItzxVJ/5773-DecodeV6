package org.firstinspires.ftc.teamcode.Core.Paths;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.follower.Follower;

public final class RedClose18 {

    /* ------------------ POSES ------------------ */

    public static Pose start = new Pose(63.6, -39.3, -2.4);
    public static Pose firstShootPos = new Pose(27.7, -19.9, -2.1);
    public static Pose intake1 = new Pose(20, -44, -1.7);

    public static Pose secondShootPos = new Pose(19.4, -13.4, -2.6);
    public static Pose intake2 = new Pose(-8, -44, -1.6);

    public static Pose thirdShootPos = new Pose(20.3, -13.2, -2);
    public static Pose intake3 = new Pose(-3.6, -49.6, -1.2);

    public static Pose fourthShootPos = thirdShootPos;
    public static Pose intake4 = intake3;

    public static Pose fifthShootPos = new Pose(16.1, -5.8, -2.7);
    public static Pose intake5 = new Pose(-26.8, -39, -1.6);
    public static Pose intake5Turn = new Pose(-26.8, -39, -2.5);
    public static Pose sixthShootPos = new Pose(18.8, -7.6, -2.5);
    public static Pose parkPos = new Pose(-12.3, -12.2, -2.5);


    /* ------------------ CONTROL POINTS ------------------ */

    public static Pose pre1Control = new Pose(20.6, -28.4, -1.6);
    public static Pose pre2Control = new Pose(-6.5, -19.9, -2.1);
    public static Pose pre5Control = new Pose(-34, -22.7, -2.3);
    public static Pose pre3ShootControl = new Pose(6.2, -25.5,-2.3);
    public static Pose pre4ShootControl = new Pose(2.5, -30.5, -2.1);
    public static Pose pre5ShootControl = pre4ShootControl;

    /* ------------------ PATHS ------------------ */

    public static PathChain firstShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                start,
                                firstShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        start.getHeading(),
                        firstShootPos.getHeading()
                )
                .setNoDeceleration()
                .build();
    }

    public static PathChain firstIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                firstShootPos,
                                pre1Control,
                                intake1
                        )
                )
                .setLinearHeadingInterpolation(
                        firstShootPos.getHeading(),
                        intake1.getHeading()
                )
                .build();
    }

    public static PathChain secondShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                intake1,
                                secondShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        intake1.getHeading(),
                        secondShootPos.getHeading()
                )
                .build();
    }

    public static PathChain secondIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                secondShootPos,
                                pre2Control,
                                intake2
                        )
                )
                .setLinearHeadingInterpolation(
                        secondShootPos.getHeading(),
                        intake2.getHeading(), 0.5
                )
                .build();
    }

    public static PathChain thirdShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                intake2,
                                pre3ShootControl,
                                thirdShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        intake2.getHeading(),
                        thirdShootPos.getHeading()
                )
                .build();
    }

    public static PathChain thirdIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                thirdShootPos,
                                intake3
                        )
                )
                .setLinearHeadingInterpolation(
                        thirdShootPos.getHeading(),
                        intake3.getHeading()
                )
                .build();
    }

    public static PathChain fourthShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                intake3,
                                pre4ShootControl,
                                fourthShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        intake3.getHeading(),
                        fourthShootPos.getHeading()
                )
                .build();
    }

    public static PathChain fourthIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                fourthShootPos,
                                intake4
                        )
                )
                .setLinearHeadingInterpolation(
                        fourthShootPos.getHeading(),
                        intake4.getHeading()
                )
                .build();
    }

    public static PathChain fifthShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                intake4,
                                pre5ShootControl,
                                fifthShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        intake4.getHeading(),
                        fifthShootPos.getHeading()
                )
                .build();
    }

    public static PathChain fifthIntake(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                fifthShootPos,
                                pre5Control,
                                intake5
                        )
                )
                .setLinearHeadingInterpolation(
                        fifthShootPos.getHeading(),
                        intake5.getHeading(), 0.65
                )
                .build();
    }

    public static PathChain sixthShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                intake5,
                                sixthShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        intake5.getHeading(),
                        sixthShootPos.getHeading()
                )
                .build();
    }

    public static PathChain park(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                sixthShootPos,
                                parkPos
                        )
                )
                .setLinearHeadingInterpolation(
                        sixthShootPos.getHeading(),
                        parkPos.getHeading()
                )
                .build();
    }
}
