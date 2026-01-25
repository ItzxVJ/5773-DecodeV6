package org.firstinspires.ftc.teamcode.OpMode.Tests.NextTests;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.bindings.BindingManager;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "Empirical Test w/ Align")
public class SoloFlywheelTest extends NextFTCOpMode {
    public SoloFlywheelTest() {
        addComponents(
                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE),
                new PedroComponent(PConstants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    FtcDashboard dashboard;

    @Override
    public void onInit() {
        follower().setStartingPose(lastPose);
        dashboard = FtcDashboard.getInstance();
        CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        new InstantCommand(() -> gatePos = gateAllow),
                        NextFlywheel.INSTANCE.stop(),
                        new SequentialGroup(
                                NextTurret.INSTANCE.resetTurret(),
                                NextTurret.INSTANCE.faceCommand(redGoalPose, () -> follower().getPose())
                        ),
                        NextFlywheel.INSTANCE.updateDistanceRPM(redGoalPose, () -> follower().getPose())
                )
        );
    }
    @Override
    public void onStartButtonPressed() {

        Gamepads.gamepad1().rightBumper()
                .whenTrue(NextPass.INSTANCE.intake)
                .whenBecomesFalse(NextPass.INSTANCE.rest);
        Gamepads.gamepad1().leftBumper()
                .whenTrue(NextPass.INSTANCE.reverse)
                .whenBecomesFalse(NextPass.INSTANCE.rest);
        Gamepads.gamepad1().a()
                .whenBecomesTrue(NextFlywheel.INSTANCE.run());
        Gamepads.gamepad1().x()
                .whenBecomesTrue(NextFlywheel.INSTANCE.stop());
        Gamepads.gamepad1().dpadDown()
                .whenBecomesTrue(() -> gatePos = gateBlock);
        Gamepads.gamepad1().dpadUp()
                .whenBecomesTrue(() -> gatePos = gateAllow);
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
        follower().update();
        ActiveOpMode.telemetry().addData("Distance", gDist);
        ActiveOpMode.telemetry().addData("Hood Angle", hoodPos);
        ActiveOpMode.telemetry().addData("Commanded RPM", commandedRPM);
        ActiveOpMode.telemetry().addData("Current RPM", NextFlywheel.currentRPM);
        ActiveOpMode.telemetry().update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Target RPM", targetRPM);
        packet.put("Current RPM", NextFlywheel.currentRPM);
        packet.put("Error", targetRPM - NextFlywheel.currentRPM);

        dashboard.sendTelemetryPacket(packet);

    }

    @Override
    public void onStop() {
        BindingManager.reset();
    }
}
