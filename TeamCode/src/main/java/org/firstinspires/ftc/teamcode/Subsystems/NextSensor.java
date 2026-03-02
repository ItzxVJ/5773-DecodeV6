package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.Command;

public class NextSensor implements Subsystem {

    public static final NextSensor INSTANCE = new NextSensor();

    private NextSensor () {};
    private final DistanceSensor distanceSensor = ActiveOpMode.hardwareMap().get(DistanceSensor.class, "sensor");

    public double getDistance () {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

    public boolean seesObject() {
        return getDistance() < 5.0; // 5 cm threshold (adjust as needed)
    }

}