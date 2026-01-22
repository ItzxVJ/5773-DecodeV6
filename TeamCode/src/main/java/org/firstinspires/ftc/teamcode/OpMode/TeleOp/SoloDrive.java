package org.firstinspires.ftc.teamcode.OpMode.TeleOp;

import static org.firstinspires.ftc.teamcode.Core.Constants.gateAllow;
import static org.firstinspires.ftc.teamcode.Core.Constants.gateBlock;
import static org.firstinspires.ftc.teamcode.Core.Constants.gatePos;
import static org.firstinspires.ftc.teamcode.Core.Constants.hoodClosePos;
import static org.firstinspires.ftc.teamcode.Core.Constants.hoodPos;
import static org.firstinspires.ftc.teamcode.Core.Constants.redGoalPose;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.zTrash.NextInterp;
import org.firstinspires.ftc.teamcode.Subsystems.NextLights;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.bindings.BindingManager;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

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
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "SoloDrive")
public class SoloDrive extends NextFTCOpMode {
    public SoloDrive() {
        addComponents(
                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }
    private MotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    @Override
    public void onInit() {
        frontLeftMotor = new MotorEx("leftFront").reversed();
        frontRightMotor = new MotorEx("rightFront");
        backLeftMotor = new MotorEx("leftBack").reversed();
        backRightMotor = new MotorEx("rightBack");
        CommandManager.INSTANCE.scheduleCommand(new InstantCommand(() -> gatePos = gateBlock));
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

        Gamepads.gamepad1().rightBumper()
                .whenTrue(NextPass.INSTANCE.intake)
                .whenBecomesFalse(NextPass.INSTANCE.rest);
        Gamepads.gamepad1().leftBumper()
                .whenTrue(NextPass.INSTANCE.reverse)
                .whenBecomesFalse(NextPass.INSTANCE.rest);
        Gamepads.gamepad1().a()
                .whenBecomesTrue(
                        new SequentialGroup(
                                NextPass.INSTANCE.intake,
                                new InstantCommand(() -> hoodPos = hoodClosePos),
                                NextFlywheel.INSTANCE.runClose(),
                                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                new InstantCommand(() -> gatePos = gateAllow),
                                new Delay(0.25),
                                NextPass.INSTANCE.intake,
                                new Delay(3),
                                new InstantCommand(() -> gatePos = gateBlock).then(NextFlywheel.INSTANCE.rest())
                        )
                );
        Gamepads.gamepad1().b()
                .whenBecomesTrue(new InstantCommand(() -> gatePos = gateBlock).then(NextFlywheel.INSTANCE.rest()));
    }
    @Override
    public void onUpdate() {
        BindingManager.update();

    }

    @Override
    public void onStop() {
        BindingManager.reset();
    }
}
