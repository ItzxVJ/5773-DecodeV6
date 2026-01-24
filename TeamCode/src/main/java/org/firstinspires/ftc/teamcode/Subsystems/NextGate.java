package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class NextGate implements Subsystem {
    public static final NextGate INSTANCE = new NextGate();
    private NextGate() { }
    private final ServoEx gate = new ServoEx("gate", -1);

    @Override
    public void initialize() {
        gate.setPosition(gatePos);
    }

    @Override
    public void periodic() {
        gate.setPosition(gatePos);
    }
}
