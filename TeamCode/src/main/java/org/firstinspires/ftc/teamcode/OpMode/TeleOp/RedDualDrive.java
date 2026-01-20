//package org.firstinspires.ftc.teamcode.OpMode.TeleOp;
//
//import static org.firstinspires.ftc.teamcode.Core.Constants.redGoalPose;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
//import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;
//import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
//import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
//import org.firstinspires.ftc.teamcode.zTrash.NextInterp;
//import org.firstinspires.ftc.teamcode.Subsystems.NextLights;
//import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
//import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;
//
//import dev.nextftc.bindings.BindingManager;
//
//import static dev.nextftc.extensions.pedro.PedroComponent.follower;
//
//import dev.nextftc.core.commands.Command;
//import dev.nextftc.core.commands.CommandManager;
//import dev.nextftc.core.commands.delays.WaitUntil;
//import dev.nextftc.core.commands.groups.ParallelGroup;
//import dev.nextftc.core.commands.groups.SequentialGroup;
//import dev.nextftc.core.components.BindingsComponent;
//import dev.nextftc.core.components.SubsystemComponent;
//import dev.nextftc.extensions.pedro.PedroComponent;
//import dev.nextftc.ftc.Gamepads;
//import dev.nextftc.ftc.NextFTCOpMode;
//import dev.nextftc.ftc.components.BulkReadComponent;
//import dev.nextftc.hardware.driving.MecanumDriverControlled;
//import dev.nextftc.hardware.impl.MotorEx;
//
//@TeleOp(name = "RedDualDrive")
//public class RedDualDrive extends NextFTCOpMode {
//    public RedDualDrive() {
//        addComponents(
//                new SubsystemComponent(NextFlywheel.INSTANCE, NextGate.INSTANCE, NextHood.INSTANCE, NextPass.INSTANCE, NextTurret.INSTANCE, NextLights.INSTANCE, NextInterp.INSTANCE),
//                new PedroComponent(PConstants::createFollower),
//                BulkReadComponent.INSTANCE,
//                BindingsComponent.INSTANCE
//        );
//    }
//
//    private final MotorEx frontLeftMotor = new MotorEx("frontLeft").reversed();
//    private final MotorEx frontRightMotor = new MotorEx("frontRight");
//    private final MotorEx backLeftMotor = new MotorEx("backLeft").reversed();
//    private final MotorEx backRightMotor = new MotorEx("backRight");
//
//
//    @Override
//    public void onInit() {
//        CommandManager.INSTANCE.scheduleCommand(
//                new ParallelGroup(
//                        NextTurret.INSTANCE.resetTurret(),
//                        NextGate.INSTANCE.block,
//                        NextLights.INSTANCE.setPurple()
//                )
//        );
//    }
//    @Override
//    public void onStartButtonPressed() {
//        Command driverControlled = new MecanumDriverControlled(
//                frontLeftMotor,
//                frontRightMotor,
//                backLeftMotor,
//                backRightMotor,
//                Gamepads.gamepad1().leftStickY().negate(),
//                Gamepads.gamepad1().leftStickX(),
//                Gamepads.gamepad1().rightStickX()
//        );
//        driverControlled.schedule();
//
//        NextTurret.INSTANCE.faceCommand(redGoalPose, follower().getPose());
//
//        Gamepads.gamepad1().rightBumper()
//                .whenBecomesTrue(NextPass.INSTANCE.intake);
//        Gamepads.gamepad1().rightTrigger()
//                .atLeast(0.3)
//                .whenBecomesTrue(
//                        new SequentialGroup(
//                                NextLights.INSTANCE.setYellow(),
//                                new WaitUntil(NextFlywheel.INSTANCE::isReady),
//                                NextLights.INSTANCE.setPurple(),
//                                NextGate.INSTANCE.allow,
//                                NextPass.INSTANCE.intake
//                        )
//                )
//                .whenBecomesFalse(
//                        NextGate.INSTANCE.block.then(NextFlywheel.INSTANCE.rest())
//                );
//    }
//    @Override
//    public void onUpdate() {
//        BindingManager.update();
//    }
//
//    @Override
//    public void onStop() {
//        BindingManager.reset();
//    }
//}
