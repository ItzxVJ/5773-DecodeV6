package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class NextHood implements Subsystem {
    public static final NextHood INSTANCE = new NextHood();
    private NextHood() {}

    private final ServoEx rightHood = new ServoEx("rightHood", -1);
    private final ServoEx leftHood = new ServoEx("leftHood", -1);

    @Override
    public void initialize() {
        leftHood.setPosition(hoodPos);
        //rightHood.setPosition(hoodPos);
    }

    @Override
    public void periodic() {
        leftHood.setPosition(hoodPos);
        //rightHood.setPosition(hoodPos);
    }
}
