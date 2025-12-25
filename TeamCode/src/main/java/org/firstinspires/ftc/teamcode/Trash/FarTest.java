//package org.firstinspires.ftc.teamcode.OpMode.Tests;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.bylazar.configurables.annotations.Configurable;
//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierCurve;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.PathChain;
//import com.pedropathing.util.Timer;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
//import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
//import org.firstinspires.ftc.teamcode.Subsystems.Gate;
//import org.firstinspires.ftc.teamcode.Subsystems.Hood;
//import org.firstinspires.ftc.teamcode.Subsystems.Lights;
//import org.firstinspires.ftc.teamcode.Subsystems.Pass;
//import org.firstinspires.ftc.teamcode.Subsystems.Turret;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Autonomous(name = "FarTest", group = "Autonomous")
//@Configurable // Panels
//public class FarTest extends OpMode {
//
//    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
//    private Follower follower; // Pedro Pathing follower instance
//    private Timer pathTimer, actionTimer, opmodeTimer;
//    private int pathState; // Current autonomous path state (state machine)
//    private PathChain Path1, Path2, Path3, Path4, Path5;
//    List<Action> runningActions = new ArrayList<>();
//    FtcDashboard dash = FtcDashboard.getInstance();
//    Hood h;
//    Pass p;
//    Gate g;
//    Lights l;
//    Turret t;
//    Flywheel f;
//
//    @Override
//    public void start() {
//
//    }
//
//    @Override
//    public void init() {
//        pathTimer = new Timer();
//        opmodeTimer = new Timer();
//        opmodeTimer.resetTimer();
//        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
//
//        buildPaths();
//
//        h = new Hood(hardwareMap);
//        p = new Pass(hardwareMap);
//        //g = new Gate(hardwareMap);
//        l = new Lights(hardwareMap);
//        t = new Turret();
//        //f = new Flywheel();
//
//        follower = PConstants.createFollower(hardwareMap);
//        follower.setStartingPose(new Pose(56, 8, Math.toRadians(90)));
//
//
//        panelsTelemetry.debug("Status", "Initialized");
//        panelsTelemetry.update(telemetry);
//    }
//
//    @Override
//    public void loop() {
//        follower.update(); // Update Pedro Pathing
//        autonomousPathUpdate(); // Update autonomous state machine
//
//        TelemetryPacket packet = new TelemetryPacket();
//
//        List<Action> newActions = new ArrayList<>();
//        for (Action action : runningActions) {
//            action.preview(packet.fieldOverlay());
//            if (action.run(packet)) {
//                newActions.add(action);
//            }
//        }
//        runningActions = newActions;
//        dash.sendTelemetryPacket(packet);
//
//        // Log values to Panels and Driver Station
//        panelsTelemetry.debug("Path State", pathState);
//        panelsTelemetry.debug("X", follower.getPose().getX());
//        panelsTelemetry.debug("Y", follower.getPose().getY());
//        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
//        panelsTelemetry.update(telemetry);
//    }
//
//    public int autonomousPathUpdate() {
//        switch (pathState) {
//            case 0:
//                follower.followPath(Path1);
//                setPathState(1);
//                break;
//            case 1:
//                if (!follower.isBusy()) {
//                    runningActions.add(new SequentialAction());
//                    follower.followPath(Path2);
//                    setPathState(2);
//                }
//
//                break;
//            case 2:
//                follower.followPath(Path2);
//                setPathState(3);
//                break;
//
//        }
//        return pathState;
//    }
//
//    public void buildPaths() {
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
//
//    public void setPathState(int pState) {
//        pathState = pState;
//        pathTimer.resetTimer();
//    }
//}