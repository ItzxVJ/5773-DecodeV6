//package org.firstinspires.ftc.teamcode.OpMode.Auto;
//
//import static org.firstinspires.ftc.teamcode.Core.Constants.*;
//import static org.firstinspires.ftc.teamcode.Core.Paths.RedClose12.*;
//import static dev.nextftc.extensions.pedro.PedroComponent.follower;
//
//import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
//import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
//import org.firstinspires.ftc.teamcode.zTrash.NextInterp;
//import org.firstinspires.ftc.teamcode.Subsystems.NextLights;
//import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
//import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
//import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
//import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;
//
//import dev.nextftc.core.commands.CommandManager;
//import dev.nextftc.core.commands.delays.Delay;
//import dev.nextftc.core.commands.delays.WaitUntil;
//import dev.nextftc.core.commands.groups.ParallelGroup;
//import dev.nextftc.core.commands.groups.SequentialGroup;
//import dev.nextftc.core.components.SubsystemComponent;
//import dev.nextftc.extensions.pedro.FollowPath;
//import dev.nextftc.extensions.pedro.PedroComponent;
//import dev.nextftc.ftc.NextFTCOpMode;
//
//public class RedClose12 extends NextFTCOpMode {
//    {
//        addComponents(
//                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE, NextLights.INSTANCE, NextInterp.INSTANCE),
//                new PedroComponent(PConstants::createFollower)
//        );
//    }
//
//    @Override
//    public void onInit() {
//        CommandManager.INSTANCE.scheduleCommand(
//                new ParallelGroup(
//                        NextTurret.INSTANCE.resetTurret(),
//                        NextGate.INSTANCE.block,
//                        NextLights.INSTANCE.setPurple()
//                )
//        );
//    }
//    @Override
//    public void onWaitForStart() {
//
//    }
//    @Override
//    public void onStartButtonPressed() {
//        CommandManager.INSTANCE.scheduleCommand(
//                new ParallelGroup(
//                        NextTurret.INSTANCE.faceCommand(redGoalPose, follower().getPose()),
//                        new SequentialGroup(
//                            NextFlywheel.INSTANCE.run(),
//                            new FollowPath(firstShoot),
//                            NextLights.INSTANCE.setYellow(),
//                            new WaitUntil(NextFlywheel.INSTANCE::isReady),
//                            NextLights.INSTANCE.setPurple(),
//                            NextGate.INSTANCE.allow,
//                            NextPass.INSTANCE.intake,
//                            new Delay(5),
//                            NextGate.INSTANCE.block,
//                            NextFlywheel.INSTANCE.rest(),
//                            new FollowPath(firstIntake),
//                            new Delay(5),
//                            new FollowPath(secondShoot),
//                            NextLights.INSTANCE.setYellow(),
//                            new WaitUntil(NextFlywheel.INSTANCE::isReady),
//                            NextLights.INSTANCE.setPurple(),
//                            NextGate.INSTANCE.allow,
//                            NextPass.INSTANCE.intake,
//                            new Delay(5),
//                            NextGate.INSTANCE.block,
//                            NextFlywheel.INSTANCE.rest(),
//                            new FollowPath(secondIntake),
//                            new Delay(5),
//                            new FollowPath(thirdShoot),
//                            NextLights.INSTANCE.setYellow(),
//                            new WaitUntil(NextFlywheel.INSTANCE::isReady),
//                            NextLights.INSTANCE.setPurple(),
//                            NextGate.INSTANCE.allow,
//                            NextPass.INSTANCE.intake,
//                            new Delay(5),
//                            NextGate.INSTANCE.block,
//                            NextFlywheel.INSTANCE.rest(),
//                            new FollowPath(thirdIntake),
//                            new Delay(5),
//                            new FollowPath(fourthShoot),
//                            NextLights.INSTANCE.setYellow(),
//                            new WaitUntil(NextFlywheel.INSTANCE::isReady),
//                            NextLights.INSTANCE.setPurple(),
//                            NextGate.INSTANCE.allow,
//                            NextPass.INSTANCE.intake,
//                            new Delay(5),
//                            NextGate.INSTANCE.block,
//                            NextFlywheel.INSTANCE.stop(),
//                            new FollowPath(park),
//                            NextLights.INSTANCE.setBlue()
//                    )
//                )
//        );
//    }
//    @Override
//    public void onUpdate() {
//        CommandManager.INSTANCE.scheduleCommand(NextTurret.INSTANCE.faceCommand(redGoalPose, follower().getPose()));
//    }
//    @Override
//    public void onStop() {
//
//    }
//}
