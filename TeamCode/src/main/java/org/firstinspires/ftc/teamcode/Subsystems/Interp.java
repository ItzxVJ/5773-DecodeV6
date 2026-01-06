package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.LookUpTable;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class Interp implements Subsystem {
    public static final Interp INSTANCE = new Interp();
    private Interp() { }

    @Override
    public void initialize() {
        LUT = new LookUpTable();

        double[][] values = {
                {1, 45, 1000}, //Angle, RPM, Distance
                {2, 90, 2000},
                {3, 135, 3000}
        };

        LUT.lutAdd(values);
    }
}
