package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import com.pedropathing.math.MathFunctions;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;

public class NextHood implements Subsystem {
    public static final NextHood INSTANCE = new NextHood();
    private NextHood() {}

    // private final ServoEx rightHood = new ServoEx("rightHood", -1);
    private final ServoEx leftHood = new ServoEx("leftHood", -1);

    @Override
    public void initialize() {
        leftHood.setPosition(hoodPos);
        //rightHood.setPosition(hoodPos);
    }

    @Override
    public void periodic() {
        leftHood.setPosition(hoodPos);
        //rightHood.setPosition(hoodPos);
    }

    public static double hoodAngle(double dist) {
        if (dist < 89.2) { // Close Angle Equation
            return MathFunctions.clamp(
                    2.57067e-7 * Math.pow(dist, 4)
                            - 0.0000631274 * Math.pow(dist, 3)
                            + 0.00559115 * Math.pow(dist, 2)
                            - 0.216311 * dist
                            + 3.53727,
                    0, 0.5
            );
        } else { // Far Angle Equation
            return 0;
        }
    }

    public Command updateAngle() {
        return new LambdaCommand()
                .setUpdate(() -> hoodPos = hoodAngle(gDist))
                .setIsDone(() -> false);
    }
}
