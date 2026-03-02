//package org.firstinspires.ftc.teamcode.zTrash;
//
//import static org.firstinspires.ftc.teamcode.Core.Constants.*;
//
//import dev.nextftc.control.ControlSystem;
//import dev.nextftc.control.KineticState;
//import dev.nextftc.core.commands.Command;
//import dev.nextftc.core.commands.utility.InstantCommand;
//import dev.nextftc.core.subsystems.Subsystem;
//import dev.nextftc.hardware.impl.MotorEx;
//
//public class zFlywheel implements Subsystem {
//    public static final zFlywheel INSTANCE = new zFlywheel();
//    private zFlywheel() {}
//    MotorEx shootL, shootR;
//    ControlSystem controller;
//    KineticState generalVel = new KineticState(0, interpolatedTargetRPM);
//    public static double kP = 0, kI = 0, kD = 0, kF;
//    public static double threshold = 100;
//
//    @Override
//    public void initialize() {
//        shootL = new MotorEx("shootL");
//        shootR = new MotorEx("shootR").reversed();
//
//        controller = ControlSystem.builder()
//                .velPid(kP, kI, kD)
//                .build();
//
//        controller.setGoal(generalVel);
//    }
//
//    @Override
//    public void periodic() {
//        controller.setGoal(generalVel);
//        shootL.setPower(controller.calculate(new KineticState(shootL.getCurrentPosition(), shootL.getVelocity())));
//        shootR.setPower(controller.calculate(new KineticState(shootL.getCurrentPosition(), shootL.getVelocity())));
//    }
//
//    public boolean isReady() {
//        return Math.abs(shootL.getVelocity() - interpolatedTargetRPM) <= threshold;
//    }
//
//    public Command rest() {
//        return new InstantCommand(() -> generalVel = new KineticState(0, restRPM));
//    }
//
//    public Command run() {
//        return new InstantCommand(() -> generalVel = new KineticState(0, targetRPM));
//    }
//
//    public Command stop() {
//        return new InstantCommand(() -> generalVel = new KineticState(0, 0));
//    }
//
//}
