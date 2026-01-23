package org.firstinspires.ftc.teamcode.OpMode.Auto;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.Core.Paths.BasicRedClose12.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
import org.firstinspires.ftc.teamcode.zTrash.NextInterp;

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
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "RedClose12V3")
public class RedClose12V3 extends NextFTCOpMode {
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
                        NextFlywheel.INSTANCE.stop(),
                        new InstantCommand(() -> hoodPos = hoodClosePos),
                        new SequentialGroup(
                            NextTurret.INSTANCE.resetTurret(),
                            NextTurret.INSTANCE.faceCommand(redGoalPose, () -> follower().getPose()
                        )

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
                        NextPass.INSTANCE.intake,
                        NextFlywheel.INSTANCE.runClose(),
                        new FollowPath(firstShoot(follower())),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(1.5),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(firstPreIntake(follower())),
                        new FollowPath(firstIntake(follower())),
                        NextFlywheel.INSTANCE.runClose(),
                        new FollowPath(secondShoot(follower())),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(1.5),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(secondPreIntake(follower())),
                        new FollowPath(secondIntake(follower())),
                        NextFlywheel.INSTANCE.runClose(),
                        new FollowPath(thirdShoot(follower())),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(1.5),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(thirdPreIntake(follower())),
                        new FollowPath(thirdIntake(follower())),
                        NextFlywheel.INSTANCE.runClose(),
                        new FollowPath(fourthShoot(follower())),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new InstantCommand(() -> gatePos = gateAllow),
                        new Delay(1.5),
                        new InstantCommand(() -> gatePos = gateBlock),
                        NextFlywheel.INSTANCE.rest(),
                        new FollowPath(park(follower()))
                    )

        );
    }
    @Override
    public void onUpdate() {
        follower().update();
    }
    @Override
    public void onStop() {

    }
}
