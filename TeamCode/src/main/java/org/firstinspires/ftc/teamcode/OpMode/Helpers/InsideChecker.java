package org.firstinspires.ftc.teamcode.OpMode.Helpers;

import com.pedropathing.geometry.Pose;

import java.util.function.Supplier;
public final class InsideChecker {

    private InsideChecker() {}

    // Robot size in inches
    private static final double ROBOT_HALF = 17.0 / 2.0;

    // Triangle vertices (immutable)
    private static final double AX = 7.39, AY = 8.97;
    private static final double BX = 79.39, BY = -63.03;
    private static final double CX = 79.39, CY = 83.53;

    // Precompute edge coefficients for half-plane checks
    // Edge AB
    private static final double ABX = BX - AX;
    private static final double ABY = BY - AY;
    // Edge BC
    private static final double BCX = CX - BX;
    private static final double BCY = CY - BY;
    // Edge CA
    private static final double CAX = AX - CX;
    private static final double CAY = AY - CY;

    /* =========================
       === PUBLIC API ===
       ========================= */

    /** Use a Pose supplier (command-safe) */
    public static boolean canShoot(Supplier<Pose> poseSupplier) {
        Pose pose = poseSupplier.get();
        if (pose == null) return false;

        return canShoot(pose.getX(), pose.getY());
    }

    /** Center coordinates version */
    public static boolean canShoot(double centerX, double centerY) {
        // Corners of robot (square)
        double left = centerX - ROBOT_HALF;
        double right = centerX + ROBOT_HALF;
        double top = centerY + ROBOT_HALF;
        double bottom = centerY - ROBOT_HALF;

        // Check all four corners
        return pointInTriangle(left, top) ||
                pointInTriangle(left, bottom) ||
                pointInTriangle(right, top) ||
                pointInTriangle(right, bottom);
    }

    /* =========================
       === HALF-PLANE CHECK ===
       ========================= */

    /** Fast check using precomputed edge vectors */
    private static boolean pointInTriangle(double px, double py) {
        boolean hasNeg, hasPos;

        // Edge AB
        double d1 = (px - BX) * (AY - BY) - (AX - BX) * (py - BY);
        // Edge BC
        double d2 = (px - CX) * (BY - CY) - (BX - CX) * (py - CY);
        // Edge CA
        double d3 = (px - AX) * (CY - AY) - (CX - AX) * (py - AY);

        hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }
}