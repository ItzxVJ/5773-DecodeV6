package org.firstinspires.ftc.teamcode.OpMode.Helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlywheelLUT {
    
    public static class ShotData {
        public double distance; // meters
        public double rpm;      // flywheel RPM
        public double hood;     // hood angle in radians

        public ShotData(double distance, double rpm, double hood) {
            this.distance = distance;
            this.rpm = rpm;
            this.hood = hood;
        }
    }

    private final List<ShotData> table = new ArrayList<>();

    public FlywheelLUT() {
        table.add(new ShotData(0, 0, 0));
        table.add(new ShotData(0, 0, 0));
        table.add(new ShotData(0, 0, 0));
        table.add(new ShotData(0, 0, 0));
        table.add(new ShotData(0, 0, 0));
        table.add(new ShotData(0, 0, 0));
        table.add(new ShotData(0, 0, 0));

    }

    public ShotData getShotData(double distance) {
        if (distance <= table.get(0).distance) {
            return table.get(0); // below minimum, clamp to first point
        }
        if (distance >= table.get(table.size() - 1).distance) {
            return table.get(table.size() - 1); // above max, clamp to last point
        }

        // Find two nearest points
        ShotData lower = table.get(0);
        ShotData upper = table.get(0);
        for (int i = 0; i < table.size() - 1; i++) {
            if (distance >= table.get(i).distance && distance <= table.get(i + 1).distance) {
                lower = table.get(i);
                upper = table.get(i + 1);
                break;
            }
        }

        // Linear interpolation
        double t = (distance - lower.distance) / (upper.distance - lower.distance);
        double interpRPM = lower.rpm + t * (upper.rpm - lower.rpm);
        double interpHood = lower.hood + t * (upper.hood - lower.hood);

        return new ShotData(distance, interpRPM, interpHood);
    }

    public void addShotData(double distance, double rpm, double hood) {
        table.add(new ShotData(distance, rpm, hood));
        table.sort(Comparator.comparingDouble(a -> a.distance)); // keep table sorted
    }
}
