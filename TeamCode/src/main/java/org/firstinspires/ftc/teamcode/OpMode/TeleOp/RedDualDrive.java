package org.firstinspires.ftc.teamcode.OpMode.TeleOp;

import static org.firstinspires.ftc.teamcode.Core.Constants.redGoalPose;

import com.bylazar.gamepad.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.Subsystems.Gate;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Interp;
import org.firstinspires.ftc.teamcode.Subsystems.Lights;
import org.firstinspires.ftc.teamcode.Subsystems.Pass;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

import dev.nextftc.bindings.BindingManager;
import static dev.nextftc.bindings.Bindings.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "RedDualDrive")
public class RedDualDrive extends NextFTCOpMode {
    public RedDualDrive() {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE, Gate.INSTANCE, Hood.INSTANCE, Pass.INSTANCE, Turret.INSTANCE, Lights.INSTANCE, Interp.INSTANCE),
                new PedroComponent(PConstants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final MotorEx frontLeftMotor = new MotorEx("frontLeft").reversed();
    private final MotorEx frontRightMotor = new MotorEx("frontRight");
    private final MotorEx backLeftMotor = new MotorEx("backLeft").reversed();
    private final MotorEx backRightMotor = new MotorEx("backRight");


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

        Turret.INSTANCE.faceCommand(redGoalPose, follower().getPose());

        Gamepads.gamepad1().rightBumper()
                .whenBecomesTrue(Pass.INSTANCE.intake);
        Gamepads.gamepad1().rightTrigger()
                .atLeast(0.3)
                .whenBecomesTrue(
                        new SequentialGroup(
                                Lights.INSTANCE.setYellow(),
                                new WaitUntil(Flywheel.INSTANCE::isReady),
                                Lights.INSTANCE.setPurple(),
                                Gate.INSTANCE.allow,
                                Pass.INSTANCE.intake
                        )
                )
                .whenBecomesFalse(
                        Gate.INSTANCE.block.then(Flywheel.INSTANCE.rest())
                );
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
