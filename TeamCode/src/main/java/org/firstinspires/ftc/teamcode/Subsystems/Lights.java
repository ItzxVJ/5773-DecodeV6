package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.ServoEx;

public class Lights implements Subsystem {
    public static final Lights INSTANCE = new Lights();
    private Lights() { }
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
