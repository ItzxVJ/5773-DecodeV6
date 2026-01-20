package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class NextLights implements Subsystem {
    public static final NextLights INSTANCE = new NextLights();
    private NextLights() { }
    private final RevBlinkinLedDriver lights = ActiveOpMode.hardwareMap().get(RevBlinkinLedDriver.class, "lights");

    public Command setRed() {
        return new InstantCommand(() -> lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED));
    }

    public Command setBlue() {
        return new InstantCommand(() -> lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE));
    }

    public Command setGreen() {
        return new InstantCommand(() -> lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN));
    }

    public Command setPurple() {
        return new InstantCommand(() -> lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET));
    }

    public Command setYellow() {
        return new InstantCommand(() -> lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW));
    }



}
