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
import dev.nextftc.core.commands.delays.WaitUntil;
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
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

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
    private MotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    @Override
    public void onInit() {
        follower().setStartingPose(lastPose);
        frontLeftMotor = new MotorEx("leftFront").reversed();
        frontRightMotor = new MotorEx("rightFront");
        backLeftMotor = new MotorEx("leftBack").reversed();
        backRightMotor = new MotorEx("rightBack");
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
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );
        driverControlled.schedule();

        Gamepads.gamepad2().rightBumper()
                .whenTrue(NextPass.INSTANCE.intake)
                .whenBecomesFalse(NextPass.INSTANCE.rest);
        Gamepads.gamepad2().leftBumper()
                .whenTrue(NextPass.INSTANCE.reverse)
                .whenBecomesFalse(NextPass.INSTANCE.rest);
        Gamepads.gamepad2().rightTrigger()
                .atLeast(0.3)
                .whenTrue(new ParallelGroup(
                                NextFlywheel.INSTANCE.run(),
                                NextPass.INSTANCE.intake
                        )
                )
                .whenFalse(NextFlywheel.INSTANCE.rest())
                .whenBecomesFalse(new InstantCommand(() -> gatePos = gateBlock))
                .whenBecomesTrue(new SequentialGroup(
                                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                new InstantCommand(() -> gatePos = gateAllow)
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
