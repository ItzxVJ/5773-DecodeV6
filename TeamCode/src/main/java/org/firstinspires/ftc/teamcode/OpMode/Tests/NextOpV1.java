package org.firstinspires.ftc.teamcode.OpMode.Tests;

import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

public class NextOpV1 extends NextFTCOpMode {
    public NextOpV1() {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE)
        );
    }
}
