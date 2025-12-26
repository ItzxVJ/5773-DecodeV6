package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Gate implements Subsystem {
    public static final Gate INSTANCE = new Gate();
    private Gate() { }
    private final ServoEx gate = new ServoEx("gate");
    public Command allow = new SetPosition(gate, gateAllow).requires(this);
    public Command block = new SetPosition(gate, gateBlock).requires(this);
}
