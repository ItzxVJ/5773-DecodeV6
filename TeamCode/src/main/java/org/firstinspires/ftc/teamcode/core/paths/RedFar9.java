//package org.firstinspires.ftc.teamcode.core.paths;
//
//import com.acmerobotics.roadrunner.Action;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierCurve;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.PathChain;
//
////import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
////import org.firstinspires.ftc.teamcode.Subsystems.Gate;
////import org.firstinspires.ftc.teamcode.Subsystems.Hood;
////import org.firstinspires.ftc.teamcode.Subsystems.Lights;
////import org.firstinspires.ftc.teamcode.Subsystems.Pass;
////import org.firstinspires.ftc.teamcode.Subsystems.Turret;
//
//import java.util.List;
//
//public class RedFar9 {
//
//    public PathChain Path1;
//    public PathChain Path2;
//    public PathChain Path3;
//    public PathChain Path4;
//    public PathChain Path5;
//
//    public RedFar9(Follower follower, List<Action> runningActions, Pass p, Gate g, Hood h, Lights l, Turret t, Flywheel f) {
//        Path1 = follower
//                .pathBuilder()
//                .addPath(
//                        new BezierCurve(
//                                new Pose(56.000, 8.000),
//                                new Pose(42.868, 36.569),
//                                new Pose(20.821, 34.819)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
//                .addParametricCallback(0.99, () -> runningActions.add(p.intake()))
//                .build();
//
//        Path2 = follower
//                .pathBuilder()
//                .addPath(
//                        new BezierCurve(
//                                new Pose(20.821, 34.819),
//                                new Pose(45.317, 35.169),
//                                new Pose(56.000, 14.000)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
//                .build();
//
//        Path3 = follower
//                .pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(56.000, 14.000), new Pose(12.000, 8.000))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
//                .build();
//
//        Path4 = follower
//                .pathBuilder()
//                .addPath(
//                        new BezierCurve(
//                                new Pose(12.000, 8.000),
//                                new Pose(20.821, 13.998),
//                                new Pose(11.023, 14.522)
//                        )
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                .build();
//
//        Path5 = follower
//                .pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(11.023, 14.522), new Pose(56.000, 14.000))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
//                .build();
//    }
//}