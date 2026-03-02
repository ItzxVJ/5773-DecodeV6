package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;

public class NextCamera implements Subsystem {

    public static final NextCamera INSTANCE = new NextCamera();
    private NextCamera() {}

    private VisionPortal portal;
    private AprilTagProcessor aprilTag;

    private static final double IMAGE_WIDTH = 640.0;

    public Command startt(HardwareMap hardwareMap) {
        return new InstantCommand(() -> {
            aprilTag = new AprilTagProcessor.Builder()
                    .setDrawTagOutline(true)
                    .build();

            portal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTag)
                    .build();
        });


    }

    public boolean hasValidTag() {
        return !aprilTag.getDetections().isEmpty();
    }

    public double getHorizontalErrorPixels() {

        List<AprilTagDetection> detections = aprilTag.getDetections();
        if (detections.isEmpty()) return 0;

        AprilTagDetection tag = detections.get(0);

        double tagCenterX = tag.center.x;
        double imageCenterX = IMAGE_WIDTH / 2.0;

        return tagCenterX - imageCenterX;
    }

    public void stop() {
        if (portal != null) portal.close();
    }
}