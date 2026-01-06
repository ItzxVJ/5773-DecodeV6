package org.firstinspires.ftc.teamcode.OpMode.Helpers;
import com.arcrobotics.ftclib.util.InterpLUT;

public class LookUpTable {

    private final InterpLUT angleLUT;
    private final InterpLUT rpmLUT;

    public LookUpTable() {
        angleLUT = new InterpLUT();
        rpmLUT = new InterpLUT();
    }

    public void lutAdd(double[][] values) {
        for (double[] pair : values) {
            if (pair.length == 3) {
                angleLUT.add(pair[0], pair[1]);
                rpmLUT.add(pair[0], pair[2]);
            }
        }
        rpmLUT.createLUT();
        angleLUT.createLUT();
    }

    public double[] lutGet(double target) {
        return new double[] {angleLUT.get(target), rpmLUT.get(target)};
    }



}