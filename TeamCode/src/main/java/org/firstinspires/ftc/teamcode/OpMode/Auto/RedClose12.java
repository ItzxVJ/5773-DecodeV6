package org.firstinspires.ftc.teamcode.OpMode.Auto;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.Core.Paths.RedClose12.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Interp;
import org.firstinspires.ftc.teamcode.Subsystems.Lights;
import org.firstinspires.ftc.teamcode.Subsystems.Gate;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Pass;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

public class RedClose12 extends NextFTCOpMode {
    {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE, Gate.INSTANCE, Hood.INSTANCE, Pass.INSTANCE, Turret.INSTANCE, Lights.INSTANCE, Interp.INSTANCE),
                new PedroComponent(PConstants::createFollower)
        );
    }

    @Override
    public void onInit() {
        CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        Turret.INSTANCE.resetTurret(),
                        Gate.INSTANCE.block,
                        Lights.INSTANCE.setPurple()
                )
        );
    }
    @Override
    public void onWaitForStart() {

    }
    @Override
    public void onStartButtonPressed() {
        CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        Turret.INSTANCE.faceCommand(redGoalPose, follower().getPose()),
                        new SequentialGroup(
                            Flywheel.INSTANCE.run(),
                            new FollowPath(firstShoot),
                            Lights.INSTANCE.setYellow(),
                            new WaitUntil(Flywheel.INSTANCE::isReady),
                            Lights.INSTANCE.setPurple(),
                            Gate.INSTANCE.allow,
                            Pass.INSTANCE.intake,
                            new Delay(5),
                            Gate.INSTANCE.block,
                            Flywheel.INSTANCE.rest(),
                            new FollowPath(firstIntake),
                            new Delay(5),
                            new FollowPath(secondShoot),
                            Lights.INSTANCE.setYellow(),
                            new WaitUntil(Flywheel.INSTANCE::isReady),
                            Lights.INSTANCE.setPurple(),
                            Gate.INSTANCE.allow,
                            Pass.INSTANCE.intake,
                            new Delay(5),
                            Gate.INSTANCE.block,
                            Flywheel.INSTANCE.rest(),
                            new FollowPath(secondIntake),
                            new Delay(5),
                            new FollowPath(thirdShoot),
                            Lights.INSTANCE.setYellow(),
                            new WaitUntil(Flywheel.INSTANCE::isReady),
                            Lights.INSTANCE.setPurple(),
                            Gate.INSTANCE.allow,
                            Pass.INSTANCE.intake,
                            new Delay(5),
                            Gate.INSTANCE.block,
                            Flywheel.INSTANCE.rest(),
                            new FollowPath(thirdIntake),
                            new Delay(5),
                            new FollowPath(fourthShoot),
                            Lights.INSTANCE.setYellow(),
                            new WaitUntil(Flywheel.INSTANCE::isReady),
                            Lights.INSTANCE.setPurple(),
                            Gate.INSTANCE.allow,
                            Pass.INSTANCE.intake,
                            new Delay(5),
                            Gate.INSTANCE.block,
                            Flywheel.INSTANCE.stop(),
                            new FollowPath(park),
                            Lights.INSTANCE.setBlue()
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
