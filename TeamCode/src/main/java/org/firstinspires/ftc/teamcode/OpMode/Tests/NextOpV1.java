package org.firstinspires.ftc.teamcode.OpMode.Tests;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Core.Paths.RedClose12.*;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Gate;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Pass;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

public class NextOpV1 extends NextFTCOpMode {
    public NextOpV1() {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE, Gate.INSTANCE, Hood.INSTANCE, Pass.INSTANCE, Turret.INSTANCE),
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
//        CommandManager.INSTANCE.scheduleCommand(
//                new SequentialGroup(
//                    new FollowPath(firstShoot)
//                )
//        );
    }
    @Override
    public void onUpdate() {
        CommandManager.INSTANCE.scheduleCommand(Turret.INSTANCE.faceCommand(redGoalPose, follower().getPose()));
    }
    @Override
    public void onStop() {

    }
}
