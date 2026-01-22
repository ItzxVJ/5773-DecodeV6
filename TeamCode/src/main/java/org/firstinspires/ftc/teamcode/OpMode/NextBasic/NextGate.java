//package org.firstinspires.ftc.teamcode.OpMode.NextBasic;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import dev.nextftc.bindings.BindingManager;
//
//import dev.nextftc.core.components.BindingsComponent;
//import dev.nextftc.core.components.SubsystemComponent;
//import dev.nextftc.ftc.Gamepads;
//import dev.nextftc.ftc.NextFTCOpMode;
//import dev.nextftc.ftc.components.BulkReadComponent;
//
//@TeleOp(name = "NextGate")
//public class NextGate extends NextFTCOpMode {
//    public NextGate() {
//        addComponents(
//                new SubsystemComponent(org.firstinspires.ftc.teamcode.Subsystems.NextGate.INSTANCE),
//                BulkReadComponent.INSTANCE,
//                BindingsComponent.INSTANCE
//        );
//    }
//    @Override
//    public void onStartButtonPressed() {
//        Gamepads.gamepad1().rightBumper()
//                .whenBecomesTrue(org.firstinspires.ftc.teamcode.Subsystems.NextGate.INSTANCE.allow);
//        Gamepads.gamepad1().leftBumper()
//                .whenBecomesTrue(org.firstinspires.ftc.teamcode.Subsystems.NextGate.INSTANCE.block);
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
