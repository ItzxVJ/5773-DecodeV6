package org.firstinspires.ftc.teamcode.OpMode.Tests;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;
import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Gate;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Pass;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous
public class NextOpV1 extends NextFTCOpMode {
    public NextOpV1() {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE, Turret.INSTANCE, Hood.INSTANCE, Gate.INSTANCE, Pass.INSTANCE),
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
        CommandManager.INSTANCE.scheduleCommand(Turret.INSTANCE.faceCommand(targetRed, follower().getPose()));
    }
    @Override
    public void onUpdate() {

    }
    @Override
    public void onStop() {

    }


}
