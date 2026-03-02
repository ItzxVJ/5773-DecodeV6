package org.firstinspires.ftc.teamcode.OpMode.TeleOp;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;


import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextCamera;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.Subsystems.NextLights;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.bindings.BindingManager;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "AA RedSoloDriveAfter")
public class RedSoloDriveAfter extends NextFTCOpMode {
    public RedSoloDriveAfter() {
        addComponents(
                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE, NextCamera.INSTANCE, NextLights.INSTANCE),
                new PedroComponent(PConstants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }
    @Override
    public void onInit() {
        follower().setStartingPose(lastPose); //-63.03
        CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        new InstantCommand(() -> gatePos = gateBlock),
                        new SequentialGroup(
                                NextTurret.INSTANCE.resetTheTurret(),
                                NextTurret.INSTANCE.faceWhileMovingCommand(
                                        redGoalPose,
                                        () -> follower().getPose(),
                                        () -> follower().getVelocity().getXComponent(),
                                        () -> follower().getVelocity().getYComponent()

                                )
                        ),
                        NextFlywheel.INSTANCE.stop(),
                        NextLights.INSTANCE.setPurple())
        );
    }
    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new PedroDriverControlled(
                () -> (double) -ActiveOpMode.gamepad1().left_stick_y * 0.9,
                () -> (double) -ActiveOpMode.gamepad1().left_stick_x * 1,
                () -> (double) -ActiveOpMode.gamepad1().right_stick_x * 0.5,
                true
        );

        driverControlled.schedule();

        Command doTheCalculations = new ParallelGroup(
                NextFlywheel.INSTANCE.calculations(
                        redGoalPose,
                        () -> follower().getPose(),
                        () -> follower().getVelocity().getXComponent(),
                        () -> follower().getVelocity().getYComponent()),
                NextFlywheel.INSTANCE.foreverRun()
        );

        doTheCalculations.schedule();

        Command shootCommand = new SequentialGroup(
//                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> {
                    if (gDist > 110) {
                        intakePower = passIn * 0.95;
                    } else {
                        intakePower = passIn;
                    }

                }),
                new InstantCommand(() -> gatePos = gateAllow)
        );

        Gamepads.gamepad1().rightTrigger()
                .atLeast(0.2)
                .whenBecomesTrue(shootCommand::schedule)
                .whenBecomesFalse(() -> {
                    shootCommand.cancel();
                    gatePos = gateBlock;
                    intakePower = passRest;
                });

        Gamepads.gamepad1().rightBumper()
                .whenTrue(new InstantCommand(() -> intakePower = passIn))
                .whenBecomesFalse(new InstantCommand(() -> intakePower = passRest));
        Gamepads.gamepad1().leftBumper()
                .whenTrue(new InstantCommand(() -> intakePower = passOut))
                .whenBecomesFalse(new InstantCommand(() -> intakePower = passRest));
        Gamepads.gamepad1().dpadRight()
                .whenBecomesTrue(NextTurret.INSTANCE.addYaw());
        Gamepads.gamepad1().dpadLeft()
                .whenBecomesTrue(NextTurret.INSTANCE.decreaseYaw());
        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(NextTurret.INSTANCE.resetYaw());
        Gamepads.gamepad1().y()
                .whenBecomesTrue(new InstantCommand(() -> follower().setPose(new Pose(-62,-62,0))));

    }

    @Override
    public void onUpdate() {
        BindingManager.update();
        follower().update();
        ActiveOpMode.telemetry().addData("Distance", gDist);
        ActiveOpMode.telemetry().addData("Hood Angle", hoodPos);
        ActiveOpMode.telemetry().addData("Commanded RPM", commandedRPM);
        ActiveOpMode.telemetry().addData("Turret Offset", yawOffset);
        ActiveOpMode.telemetry().addLine("good luck gang don't get cooked");
        ActiveOpMode.telemetry().update();

    }

    @Override
    public void onStop() {
        BindingManager.reset();
    }
}
