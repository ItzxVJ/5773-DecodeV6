package org.firstinspires.ftc.teamcode.zTrash;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.zTrash.RedClose18.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;

import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.NextFTCOpMode;

//@Autonomous(name = "RedClose18V2")
public class RedClose18V2 extends NextFTCOpMode {
    {
        addComponents(
                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE),
                new PedroComponent(PConstants::createFollower)
        );
    }

    @Override
    public void onInit() {
        follower().setStartingPose(start);
        CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        new InstantCommand(() -> gatePos = gateBlock),
                        new InstantCommand(() -> intakePower = passRest),
                        NextFlywheel.INSTANCE.stop(),
                        new SequentialGroup(
                                NextTurret.INSTANCE.resetTurret(),
                                NextTurret.INSTANCE.faceCommand(redGoalPose, () -> follower().getPose())
                        )
                )
        );
    }
    @Override
    public void onWaitForStart() {

    }
    @Override
    public void onStartButtonPressed() {
        CommandManager.INSTANCE.scheduleCommand(
                new SequentialGroup(
                        new InstantCommand(() -> intakePower = passIn),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> firstShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new ParallelGroup(
                            new FollowPath(firstShoot(follower())),
                            new SequentialGroup(
                                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                                new Delay(0.75),
                                new InstantCommand(() -> gatePos = gateAllow),
                                new Delay(shootWait),
                                new InstantCommand(() -> gatePos = gateBlock),
                                NextFlywheel.INSTANCE.rest()
                            )
                        ),
                        new FollowPath(firstIntake(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> secondShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(secondShoot(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(shootWait),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(secondIntake(follower())),
                        new Delay(0.2),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> thirdShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(thirdShoot(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(shootWait),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(thirdIntake(follower())),
                        new Delay(gateWait),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> fourthShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(fourthShoot(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(shootWait),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(fourthIntake(follower())),
                        new Delay(gateWait),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> fifthShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(fifthShoot(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(shootWait),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(fifthIntake(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> sixthShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(sixthShoot(follower())),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(shootWait),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.stop(),
                        new FollowPath(park(follower())),
                        new InstantCommand(() -> intakePower = 0)
                )
        );
    }
    @Override
    public void onUpdate() {
        follower().update();
        ActiveOpMode.telemetry().addData("Distance", gDist);
        ActiveOpMode.telemetry().addData("Hood Angle", hoodPos);
        ActiveOpMode.telemetry().addData("Commanded RPM", commandedRPM);
        ActiveOpMode.telemetry().update();
    }
    @Override
    public void onStop() {
        lastPose = follower().getPose();
    }
}
