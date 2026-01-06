package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class Hood implements Subsystem {
    public static final Hood INSTANCE = new Hood();
    private Hood() {}

    private final ServoEx hood = new ServoEx("hood");

    @Override
    public void periodic() {
        double[] result = LUT.lutGet(targetDistance);
        double desiredPos = result[0];

        if (desiredPos < hoodMinPos) {
            desiredPos = hoodMinPos;
        } else if (desiredPos > hoodMaxPos) {
            desiredPos = hoodMaxPos;
        }

        interpolatedHoodPos = desiredPos;
        hood.setPosition(desiredPos);
    }
}
