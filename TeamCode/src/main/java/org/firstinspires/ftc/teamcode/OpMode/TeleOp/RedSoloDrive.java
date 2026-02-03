//package org.firstinspires.ftc.teamcode.OpMode.TeleOp;
//
//import static org.firstinspires.ftc.teamcode.Core.Constants.*;
//import static dev.nextftc.extensions.pedro.PedroComponent.follower;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
//import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
//import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
//import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
//import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
//import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;
//
//import dev.nextftc.bindings.BindingManager;
//import dev.nextftc.core.commands.Command;
//import dev.nextftc.core.commands.CommandManager;
//import dev.nextftc.core.commands.delays.Delay;
//import dev.nextftc.core.commands.delays.WaitUntil;
//import dev.nextftc.core.commands.groups.ParallelGroup;
//import dev.nextftc.core.commands.groups.SequentialGroup;
//import dev.nextftc.core.commands.utility.InstantCommand;
//import dev.nextftc.core.components.BindingsComponent;
//import dev.nextftc.core.components.SubsystemComponent;
//import dev.nextftc.extensions.pedro.PedroComponent;
//import dev.nextftc.extensions.pedro.PedroDriverControlled;
//import dev.nextftc.ftc.ActiveOpMode;
//import dev.nextftc.ftc.Gamepads;
//import dev.nextftc.ftc.NextFTCOpMode;
//import dev.nextftc.ftc.components.BulkReadComponent;
//
//@TeleOp(name = "RedSoloDrive")
//public class RedSoloDrive extends NextFTCOpMode {
//    public RedSoloDrive() {
//        addComponents(
//                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE),
//                new PedroComponent(PConstants::createFollower),
//                BulkReadComponent.INSTANCE,
//                BindingsComponent.INSTANCE
//        );
//    }
//
//    @Override
//    public void onInit() {
//        follower().setStartingPose(lastPose);
//        CommandManager.INSTANCE.scheduleCommand(
//                new ParallelGroup(
//                        new InstantCommand(() -> gatePos = gateBlock),
//                        new SequentialGroup(
//                                NextTurret.INSTANCE.resetTurret(),
//                                NextTurret.INSTANCE.faceCommand(redGoalPose, () -> follower().getPose())
//                        ),
//                        NextFlywheel.INSTANCE.updateDistanceRPM(redGoalPose, () -> follower().getPose()),
//                        NextHood.INSTANCE.updateAngle(),
//                        NextFlywheel.INSTANCE.stop()
//                )
//        );
//    }
//
//    @Override
//    public void onStartButtonPressed() {
//        Command driverControlled = new PedroDriverControlled(
//                Gamepads.gamepad1().leftStickY().negate(),
//                Gamepads.gamepad1().leftStickX().negate(),
//                Gamepads.gamepad1().rightStickX().negate(),
//                true
//        );
//        driverControlled.schedule();
//
//        Gamepads.gamepad1().rightBumper()
//                .whenTrue(NextPass.INSTANCE.intake)
//                .whenBecomesFalse(NextPass.INSTANCE.rest);
//
//        Gamepads.gamepad1().leftBumper()
//                .whenTrue(NextPass.INSTANCE.reverse)
//                .whenBecomesFalse(NextPass.INSTANCE.rest);
//
//        Gamepads.gamepad1().x()
//                .whenBecomesTrue(NextFlywheel.INSTANCE.rest());
//
//        Gamepads.gamepad1().a()
//                .whenBecomesTrue(NextFlywheel.INSTANCE.rev());
//
//        Gamepads.gamepad1().b()
//                .whenBecomesTrue(
//                        new SequentialGroup(
//                                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> follower().getPose()),
//                                NextFlywheel.INSTANCE.instantRun(),
//                                new WaitUntil(NextFlywheel.INSTANCE::isReady),
//                                NextPass.INSTANCE.intake,
//                                new InstantCommand(() -> gatePos = gateAllow),
//                                new Delay(shootWait),
//                                new InstantCommand(() -> gatePos = gateBlock),
//                                NextFlywheel.INSTANCE.rest(),
//                                NextPass.INSTANCE.rest
//                        )
//                );
//    }
//
//    @Override
//    public void onUpdate() {
//        BindingManager.update();
//        follower().update();
//        ActiveOpMode.telemetry().addData("Distance", gDist);
//        ActiveOpMode.telemetry().addData("Hood Angle", hoodPos);
//        ActiveOpMode.telemetry().addData("Commanded RPM", commandedRPM);
//        ActiveOpMode.telemetry().update();
//    }
//
//    @Override
//    public void onStop() {
//        BindingManager.reset();
//    }
//}
