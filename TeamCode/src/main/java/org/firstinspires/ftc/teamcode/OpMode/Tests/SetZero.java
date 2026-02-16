package org.firstinspires.ftc.teamcode.OpMode.Tests;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.OpMode.Helpers.FlywheelPIDFControl;

@Config
@TeleOp(name = "SetOriginCoordinates")
public class SetZero extends LinearOpMode {

    @Override
    public void runOpMode() {

        waitForStart();

        lastPose = new Pose(0,0,0);
    }
}
