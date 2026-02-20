package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelLUT;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class NextHood implements Subsystem {
    public static final NextHood INSTANCE = new NextHood();
    private NextHood() {}
    private final ServoEx leftHood = new ServoEx("rightHood", -1);
    FlywheelLUT.ShotData shot;

    @Override
    public void initialize() {
        shot = lookup.getShotData(gDist);
        leftHood.setPosition(shot.hood);
    }

    @Override
    public void periodic() {
        shot = lookup.getShotData(gDist);
        leftHood.setPosition(shot.hood);
    }


}

