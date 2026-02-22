package org.firstinspires.ftc.teamcode.zTrash;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.zTrash.RedClose18V3.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;

import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.NextFTCOpMode;

//@Autonomous(name = "RedClose18V4")
public class RedClose18V4 extends NextFTCOpMode {
    {
        addComponents(
                new SubsystemComponent(
                        NextFlywheel.INSTANCE,
                        NextGate.INSTANCE,
                        NextHood.INSTANCE,
                        NextPass.INSTANCE,
                        NextTurret.INSTANCE
                ),
                new PedroComponent(PConstants::createFollower)
        );
    }

    @Override
    public void onInit() {
        follower().setStartingPose(start);
        CommandManager.INSTANCE.scheduleCommand(
                new ParallelGroup(
                        new InstantCommand(() -> gatePos = gateBlock),
                        new InstantCommand(() -> intakePower = passRest),
                        NextFlywheel.INSTANCE.stop(),
                        new SequentialGroup(
                                NextTurret.INSTANCE.resetTurret(),
                                NextTurret.INSTANCE.faceCommand(redGoalPose, () -> follower().getPose())
                        )
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        CommandManager.INSTANCE.scheduleCommand(
                new SequentialGroup(

                        /* ---------- FIRST SHOOT ---------- */
                        new InstantCommand(() -> intakePower = passIn),
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> firstShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new ParallelGroup(
                            new FollowPath(firstShootAndIntake(follower()), true, 0.9),
                            new SequentialGroup(
                                    new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                    new InstantCommand(() -> gatePos = gateAllow),
                                    new Delay(shootWait),
                                    new InstantCommand(() -> gatePos = gateBlock),
                                    NextFlywheel.INSTANCE.rest()
                            )
                        ),

                        /* ---------- SECOND SHOOT ---------- */
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> secondShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(secondShoot(follower(),
                                new SequentialGroup(
                                    new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                    new InstantCommand(() -> gatePos = gateAllow),
                                    new Delay(shootWait),
                                    new InstantCommand(() -> gatePos = gateBlock),
                                    NextFlywheel.INSTANCE.rest()
                                )
                        )),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new Delay(shootWait),

                        new FollowPath(secondIntake(follower()), true, 0.9),
                        new Delay(gateWait),

                        /* ---------- THIRD SHOOT ---------- */
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> thirdShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(thirdShoot(follower(),
                                new SequentialGroup(
                                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                        new InstantCommand(() -> gatePos = gateAllow),
                                        new Delay(shootWait),
                                        new InstantCommand(() -> gatePos = gateBlock),
                                        NextFlywheel.INSTANCE.rest()
                                )
                        )),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new Delay(shootWait),

                        new FollowPath(thirdIntake(follower()), true, 0.9),
                        new Delay(gateWait),

                        /* ---------- FOURTH SHOOT ---------- */
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> fourthShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(fourthShoot(follower(),
                                new SequentialGroup(
                                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                        new InstantCommand(() -> gatePos = gateAllow),
                                        new Delay(shootWait),
                                        new InstantCommand(() -> gatePos = gateBlock),
                                        NextFlywheel.INSTANCE.rest()
                                )
                        ), true, 0.95),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new Delay(shootWait),

                        new FollowPath(fourthIntake(follower()), true, 0.9),
                        new Delay(gateWait),

                        /* ---------- FIFTH SHOOT ---------- */
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> fifthShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(fifthShoot(follower(),
                                new SequentialGroup(
                                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                        new InstantCommand(() -> gatePos = gateAllow),
                                        new Delay(shootWait),
                                        new InstantCommand(() -> gatePos = gateBlock),
                                        NextFlywheel.INSTANCE.rest()
                                )
                        )),
                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new Delay(shootWait),

                        new FollowPath(fifthIntake(follower()), true, 0.9),

                        /* ---------- SIXTH SHOOT ---------- */
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> sixthShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(sixthShoot(follower(),
                                new SequentialGroup(
                                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                        new InstantCommand(() -> gatePos = gateAllow),
                                        new Delay(shootWait),
                                        new InstantCommand(() -> gatePos = gateBlock),
                                        NextFlywheel.INSTANCE.rest()
                                )
                        )),

                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new Delay(shootWait),

                        new FollowPath(sixthIntake(follower()), true, 0.9),

                        /* ---------- SEVENTH SHOOT ---------- */
                        NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> seventhShootPos),
                        NextFlywheel.INSTANCE.instantRun(),
                        new FollowPath(seventhShoot(follower(),
                                new SequentialGroup(
                                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                                        new InstantCommand(() -> gatePos = gateAllow),
                                        new Delay(shootWait),
                                        new InstantCommand(() -> gatePos = gateBlock),
                                        NextFlywheel.INSTANCE.rest()
                                )
                        )),

                        new WaitUntil(NextFlywheel.INSTANCE::isReady),
                        new Delay(shootWait),

                        /* ---------- PARK ---------- */
                        NextFlywheel.INSTANCE.stop(),
                        new FollowPath(lilPark(follower())),
                        new InstantCommand(() -> intakePower = 0)
                )
        );
    }

    @Override
    public void onUpdate() {
        follower().update();
        ActiveOpMode.telemetry().addData("Distance", gDist);
        ActiveOpMode.telemetry().addData("Hood Angle", hoodPos);
        ActiveOpMode.telemetry().addData("Commanded RPM", commandedRPM);
        ActiveOpMode.telemetry().update();
    }

    @Override
    public void onStop() {
        lastPose = follower().getPose();
    }
}
