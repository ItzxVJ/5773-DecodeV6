package org.firstinspires.ftc.teamcode.zTrash;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.LookUpTable;

@Config
@TeleOp(name = "LUT Test")
public class Interp extends OpMode {

    LookUpTable LUT;
    public static double targetDistance;

    @Override
    public void init() {
        LUT = new LookUpTable();

        double[][] testing = {
                {1, 45, 1000},
                {2, 90, 2000},
                {3, 135, 3000}
        };

        LUT.lutAdd(testing);
    }

    @Override
    public void loop() {
        double[] result = LUT.lutGet(targetDistance);

        telemetry.addData("Target Distance", targetDistance);
        telemetry.addData("Angle", result[0]);
        telemetry.addData("RPM", result[1]);
        telemetry.update();
    }
}