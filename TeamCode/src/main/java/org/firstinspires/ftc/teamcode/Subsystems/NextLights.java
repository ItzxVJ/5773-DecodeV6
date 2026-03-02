package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Servo;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.ServoEx;

public class NextLights implements Subsystem {
    public static final NextLights INSTANCE = new NextLights();


    private NextLights() { }

    private final ServoEx lights = new ServoEx("lights", -1);
    public static double purple = 0.71, yellow = 0.38, red = 0.28, green = 0.5, orange = 0.333, blue = 0.65;

    public Command setRed() {
        return new InstantCommand(() -> lights.setPosition(red));
    }

    public Command setBlue() {
        return new InstantCommand(() -> lights.setPosition(blue));
    }

    public Command setGreen() {
        return new InstantCommand(() -> lights.setPosition(green));
    }

    public Command setPurple() {
        return new InstantCommand(() -> lights.setPosition(purple));
    }

    public Command setYellow() {
        return new InstantCommand(() -> lights.setPosition(yellow));
    }
}
