package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class Hood implements Subsystem {
    public static final Hood INSTANCE = new Hood();
    private Hood() { }
    private final ServoEx hood = new ServoEx("hood");

    @Override
    public void periodic() {
        hood.setPosition(hoodPos);
    }
}
