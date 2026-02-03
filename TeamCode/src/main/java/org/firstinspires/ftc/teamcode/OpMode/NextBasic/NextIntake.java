//package org.firstinspires.ftc.teamcode.OpMode.NextBasic;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
//
//import dev.nextftc.bindings.BindingManager;
//
//import dev.nextftc.core.components.BindingsComponent;
//import dev.nextftc.core.components.SubsystemComponent;
//import dev.nextftc.ftc.Gamepads;
//import dev.nextftc.ftc.NextFTCOpMode;
//import dev.nextftc.ftc.components.BulkReadComponent;
//
//@TeleOp(name = "NextIntake")
//public class NextIntake extends NextFTCOpMode {
//    public NextIntake() {
//        addComponents(
//                new SubsystemComponent(NextPass.INSTANCE),
//                BulkReadComponent.INSTANCE,
//                BindingsComponent.INSTANCE
//        );
//    }
//    @Override
//    public void onStartButtonPressed() {
//        Gamepads.gamepad1().rightBumper()
//                .whenBecomesTrue(NextPass.INSTANCE.intake);
//        Gamepads.gamepad1().leftBumper()
//                .whenBecomesTrue(NextPass.INSTANCE.reverse);
//        Gamepads.gamepad1().y()
//                .whenBecomesTrue(NextPass.INSTANCE.rest);
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
