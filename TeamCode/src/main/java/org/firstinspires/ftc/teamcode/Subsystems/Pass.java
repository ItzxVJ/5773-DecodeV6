package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Pass implements Subsystem {
    public static final Pass INSTANCE = new Pass();
    private Pass() { }
    private final MotorEx pass = new MotorEx("pass");
    public Command intake = new SetPower(pass, passIn).requires(this);
    public Command reverse = new SetPower(pass, passOut).requires(this);
    public Command rest = new SetPower(pass, passRest).requires(this);
}
