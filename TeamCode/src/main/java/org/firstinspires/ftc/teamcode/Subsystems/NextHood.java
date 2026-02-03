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
            return MathFunctions.clamp(
                    -1.24462e-8 * Math.pow(dist, 4)
                            + 0.00000483688 * Math.pow(dist, 3)
                            - 0.00066087 * Math.pow(dist, 2)
                            + 0.0330194 * dist
                            - 0.0954934,
                    0, 0.42
            );
    }

    public Command updateAngle() {
        return new LambdaCommand()
                .setUpdate(() -> hoodPos = hoodAngle(gDist))
                .setIsDone(() -> false);
    }
}
