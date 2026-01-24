package org.firstinspires.ftc.teamcode.Core.Paths;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.follower.Follower;

public final class RedClose18 {

    /* ------------------ POSES ------------------ */

    public static Pose start = new Pose(0, 0, 0);

    public static Pose firstShootPos = new Pose(0, 0, 0);
    public static Pose intake1 = new Pose(0, 0, 0);

    public static Pose secondShootPos = new Pose(0, 0, 0);
    public static Pose intake2 = new Pose(0, 0, 0);

    public static Pose thirdShootPos = new Pose(0, 0, 0);
    public static Pose intake3 = new Pose(0, 0, 0);

    public static Pose fourthShootPos = new Pose(0, 0, 0);
    public static Pose parkPos = new Pose(0, 0, 0);

    /* ------------------ CONTROL POINTS ------------------ */

    public static Pose pre1Control = new Pose(0, 0, 0);
    public static Pose pre2Control = new Pose(0, 0, 0);
    public static Pose pre3Control = new Pose(0, 0, 0);

    /* ------------------ PATHS ------------------ */

    public static PathChain firstShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
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
                        new BezierCurve(
                                intake1,
                                pre2Control,
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
                        intake2.getHeading()
                )
                .build();
    }

    public static PathChain thirdShoot(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                intake2,
                                pre3Control,
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
                        new BezierCurve(
                                thirdShootPos,
                                pre3Control,
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
                                fourthShootPos
                        )
                )
                .setLinearHeadingInterpolation(
                        intake3.getHeading(),
                        fourthShootPos.getHeading()
                )
                .build();
    }

    public static PathChain park(Follower follower) {
        return follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                fourthShootPos,
                                parkPos
                        )
                )
                .setLinearHeadingInterpolation(
                        fourthShootPos.getHeading(),
                        parkPos.getHeading()
                )
                .build();
    }
}
