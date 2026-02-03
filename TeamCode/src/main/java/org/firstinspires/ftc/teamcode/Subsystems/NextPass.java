package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class NextPass implements Subsystem {
    public static final NextPass INSTANCE = new NextPass();
    private NextPass() { }
    private final MotorEx pass = new MotorEx("pass", -1);

    @Override
    public void initialize() {
        pass.setPower(0);
    }

    @Override
    public void periodic() {
        pass.setPower(intakePower);
    }
}
