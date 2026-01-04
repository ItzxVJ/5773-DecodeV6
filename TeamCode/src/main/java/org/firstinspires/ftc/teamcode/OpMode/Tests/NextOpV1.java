package org.firstinspires.ftc.teamcode.OpMode.Tests;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.Core.Paths.RedClose12.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.zTrash.zFlywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Gate;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Pass;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

public class NextOpV1 extends NextFTCOpMode {
    public NextOpV1() {
        addComponents(
                new SubsystemComponent(zFlywheel.INSTANCE, Gate.INSTANCE, Hood.INSTANCE, Pass.INSTANCE, Turret.INSTANCE),
                new PedroComponent(PConstants::createFollower)
        );
    }

    @Override
    public void onInit() {
        CommandManager.INSTANCE.scheduleCommand(Turret.INSTANCE.resetTurret());
    }
    @Override
    public void onWaitForStart() {

    }
    @Override
    public void onStartButtonPressed() {
        CommandManager.INSTANCE.scheduleCommand(
                new SequentialGroup(
                    new ParallelGroup(
                            new FollowPath(firstShoot),
                            new InstantCommand(() -> targetRPM = 900)
                    ),
                    new ParallelGroup(
                            new FollowPath(firstIntake),
                            Pass.INSTANCE.intake
                    ),
                    new ParallelGroup(
                            new FollowPath(secondShoot),
                            new InstantCommand(() -> targetRPM = 100)
                    ),
                    new ParallelGroup(
                            new FollowPath(secondIntake),
                            new InstantCommand(() -> targetRPM = 900)
                    ),
                    new ParallelGroup(
                            new FollowPath(thirdShoot),
                            new InstantCommand(() -> targetRPM = 100)
                    ),
                    new ParallelGroup(
                            new FollowPath(thirdIntake),
                            new InstantCommand(() -> targetRPM = 900)
                    ),
                    new ParallelGroup(
                            new FollowPath(fourthShoot),
                            new InstantCommand(() -> targetRPM = 100)
                    )
                )
        );
    }
    @Override
    public void onUpdate() {
        CommandManager.INSTANCE.scheduleCommand(Turret.INSTANCE.faceCommand(redGoalPose, follower().getPose()));
    }
    @Override
    public void onStop() {

    }
}
