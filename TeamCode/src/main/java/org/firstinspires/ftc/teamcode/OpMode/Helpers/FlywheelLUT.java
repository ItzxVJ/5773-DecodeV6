package org.firstinspires.ftc.teamcode.OpMode.Helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlywheelLUT {
    public static double distanceOffset = 7;

    public static class ShotData {
        public double distance; // inches
        public double rpm;      // flywheel RPM
        public double hood;     // hood position

        public ShotData(double distance, double rpm, double hood) {
            this.distance = distance;
            this.rpm = rpm;
            this.hood = hood;
        }
    }

    private final List<ShotData> table = new ArrayList<>();

    public FlywheelLUT() {
        table.add(new ShotData(52 - distanceOffset, 960, 0.42));
        table.add(new ShotData(61 - distanceOffset, 1020, 0.38));
        table.add(new ShotData(73 - distanceOffset, 1070, 0.28));
        table.add(new ShotData(86 - distanceOffset, 1130, 0.2));
        table.add(new ShotData(100 - distanceOffset, 1210, 0.16));
        table.add(new ShotData(115 - distanceOffset, 1280, 0.14));
        table.add(new ShotData(133 - distanceOffset, 1350, 0.12));
        table.add(new ShotData(152 - distanceOffset, 1460, 0.08));
        table.add(new ShotData(166 - distanceOffset, 1530, 0.08));

//        table.add(new ShotData(49,910,0.42));
//        table.add(new ShotData(61,980,0.36));
//        table.add(new ShotData(77,1030,0.32));
//        table.add(new ShotData(91,1150,0.24));
//        table.add(new ShotData(116,1280,0.16));
//        table.add(new ShotData(135,1520,0.12));
//        table.add(new ShotData(154,1640,0.12));
    }

    public ShotData getShotData(double distance) {

        // Clamp distance
        if (distance <= table.get(0).distance) {
            ShotData d = table.get(0);
            return new ShotData(distance, d.rpm, d.hood);
        }
        if (distance >= table.get(table.size() - 1).distance) {
            ShotData d = table.get(table.size() - 1);
            return new ShotData(distance, d.rpm, d.hood);
        }

        // Find distance bracket
        ShotData lower = table.get(0);
        ShotData upper = table.get(0);

        for (int i = 0; i < table.size() - 1; i++) {
            if (distance >= table.get(i).distance && distance <= table.get(i + 1).distance) {
                lower = table.get(i);
                upper = table.get(i + 1);
                break;
            }
        }

        // Distance → RPM interpolation
        double t = (distance - lower.distance) / (upper.distance - lower.distance);
        double interpRPM = lower.rpm + t * (upper.rpm - lower.rpm);

        // RPM → Hood interpolation
        double interpHood = getHoodFromRPM(interpRPM);

        return new ShotData(distance, interpRPM, interpHood);
    }

    public double getHoodFromRPM(double rpm) {

        if (rpm <= table.get(0).rpm) {
            return table.get(0).hood;
        }
        if (rpm >= table.get(table.size() - 1).rpm) {
            return table.get(table.size() - 1).hood;
        }

        ShotData lower = table.get(0);
        ShotData upper = table.get(0);

        for (int i = 0; i < table.size() - 1; i++) {
            if (rpm >= table.get(i).rpm && rpm <= table.get(i + 1).rpm) {
                lower = table.get(i);
                upper = table.get(i + 1);
                break;
            }
        }

        double t = (rpm - lower.rpm) / (upper.rpm - lower.rpm);
        return lower.hood + t * (upper.hood - lower.hood);
    }

    public void addShotData(double distance, double rpm, double hood) {
        table.add(new ShotData(distance, rpm, hood));
        table.sort(Comparator.comparingDouble(a -> a.distance));
    }
}