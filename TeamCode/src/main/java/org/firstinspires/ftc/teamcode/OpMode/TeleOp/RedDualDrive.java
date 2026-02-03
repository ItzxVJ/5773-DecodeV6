package org.firstinspires.ftc.teamcode.OpMode.TeleOp;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.bindings.BindingManager;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.Delay;
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

@TeleOp(name = "RedDualDrive")
public class RedDualDrive extends NextFTCOpMode {
    public RedDualDrive() {
        addComponents(
                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE),
                new PedroComponent(PConstants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }
    @Override
    public void onInit() {
        follower().setStartingPose(lastPose);
                CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        new InstantCommand(() -> gatePos = gateBlock),
                        new SequentialGroup(
                                NextTurret.INSTANCE.resetTurret(),
                                NextTurret.INSTANCE.faceCommand(redGoalPose, () -> follower().getPose())
                        ),
                        NextFlywheel.INSTANCE.updateDistanceRPM(redGoalPose, () -> follower().getPose()),
                        NextHood.INSTANCE.updateAngle()
                )
        );
    }
    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new PedroDriverControlled(
                () -> (double) -ActiveOpMode.gamepad1().left_stick_y / 1.5,
                () -> (double) -ActiveOpMode.gamepad1().left_stick_x / 1.5,
                () -> (double) -ActiveOpMode.gamepad1().right_stick_x / 3,
                true
        );

        driverControlled.schedule();

        Gamepads.gamepad2().rightBumper()
                .whenTrue(new InstantCommand(() -> intakePower = passIn))
                .whenBecomesFalse(new InstantCommand(() -> intakePower = passRest));
        Gamepads.gamepad2().leftBumper()
                .whenTrue(new InstantCommand(() -> intakePower = passOut))
                .whenBecomesFalse(new InstantCommand(() -> intakePower = passRest));
        Gamepads.gamepad2().x()
                .whenBecomesTrue(NextFlywheel.INSTANCE.rest());
        Gamepads.gamepad2().a()
                .whenBecomesTrue(NextFlywheel.INSTANCE.rev());
        Gamepads.gamepad2().b()
                .whenBecomesTrue(
                        new SequentialGroup(
                                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
                                NextFlywheel.INSTANCE.instantRun(),
                                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                new InstantCommand(() -> intakePower = passIn),
                                new InstantCommand(() -> gatePos = gateAllow),
                                new Delay(shootWait),
                                new InstantCommand(() -> gatePos = gateBlock),
                                NextFlywheel.INSTANCE.rest(),
                                new InstantCommand(() -> intakePower = passRest)
                        )
                );
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
        follower().update();
        ActiveOpMode.telemetry().addData("Distance", gDist);
        ActiveOpMode.telemetry().addData("Hood Angle", hoodPos);
        ActiveOpMode.telemetry().addData("Commanded RPM", commandedRPM);
        ActiveOpMode.telemetry().update();

    }

    @Override
    public void onStop() {
        BindingManager.reset();
    }
}
