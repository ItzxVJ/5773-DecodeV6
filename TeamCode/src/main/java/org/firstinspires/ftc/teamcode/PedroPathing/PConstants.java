package org.firstinspires.ftc.teamcode.PedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PConstants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(10.977)
            .forwardZeroPowerAcceleration(-30.606163879739587)
            .lateralZeroPowerAcceleration(-55.773399997018196)
            .useSecondaryDrivePIDF(false)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryTranslationalPIDF(true)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1,0,0,0))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.02,0,0.01,0.01))
            .headingPIDFCoefficients(new PIDFCoefficients(1.5, 0, 0.01, 0))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(2.5, 0, 0.01, 0.0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.03, 0, 0.00001, 0.6, 0.0));

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftBack")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorEx.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorEx.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorEx.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorEx.Direction.FORWARD)
            .xVelocity(78.08685783326156)
            .yVelocity(61.35637130136564);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-4.5)
            .strafePodX(-1.25)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);


}