package org.firstinspires.ftc.teamcode.OpMode.Auto;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.Core.Paths.RedClose18V5.*;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.PedroPathing.PConstants;
import org.firstinspires.ftc.teamcode.Subsystems.NextFlywheel;

import org.firstinspires.ftc.teamcode.Subsystems.NextGate;
import org.firstinspires.ftc.teamcode.Subsystems.NextHood;
import org.firstinspires.ftc.teamcode.Subsystems.NextPass;
import org.firstinspires.ftc.teamcode.Subsystems.NextTurret;

import dev.nextftc.core.commands.Command;
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
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "RedClose18V6")
public class RedClose18V6 extends NextFTCOpMode {
    {
        addComponents(
                BulkReadComponent.INSTANCE,
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
        autonomousRoutine().schedule();
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

    private Command autonomousRoutine() {
        return new SequentialGroup(
                shootAndIntake1(),
                shoot2(),
                gateIntake1(),
                shoot3(),
                gateIntake2(),
                shoot4(),
                gateIntake3(),
                shoot5(),
                intakeClose(),
                shoot6(),
//                intakeFar(),
//                shoot7(),
                park()
        );
    }

    private Command shootAndIntake1() {
        return new SequentialGroup(
                new InstantCommand(() -> intakePower = passIn),
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> firstShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(firstShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock),
                NextTurret.INSTANCE.resetYaw(),
                new FollowPath(firstIntake(follower()))
        );
    }

    private Command shoot2() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> secondShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(secondShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock)

        );
    }

    private Command gateIntake1() {
        return new SequentialGroup(
                new FollowPath(gate1(follower())),
                new Delay(gateWait)
        );
    }

    private Command shoot3() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> thirdShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(thirdShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock)
        );
    }

    private Command gateIntake2() {
        return new SequentialGroup(
                new FollowPath(gate2(follower())),
                new Delay(gateWait)
        );
    }

    private Command shoot4() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> fourthShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(fourthShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock)
        );
    }

    private Command gateIntake3() {
        return new SequentialGroup(
                new FollowPath(gate3(follower())),
                new Delay(gateWait)
        );
    }

    private Command shoot5() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> fifthShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(fifthShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock)
        );
    }

    private Command intakeClose() {
        return new SequentialGroup(
                new FollowPath(closeIntake(follower()))
        );
    }

    private Command shoot6() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> sixthShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(sixthShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock)
        );
    }

    private Command intakeFar() {
        return new SequentialGroup(
                new FollowPath(farIntake(follower()))
        );
    }

    private Command shoot7() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.calcRPM(redGoalPose, () -> seventhShootPos),
                NextFlywheel.INSTANCE.instantRun(),
                new FollowPath(seventhShoot(follower())),
                new WaitUntil(NextFlywheel.INSTANCE::isReady),
                new InstantCommand(() -> gatePos = gateAllow),
                new Delay(shootWait),
                new InstantCommand(() -> gatePos = gateBlock),
                NextFlywheel.INSTANCE.rest()
        );
    }

    private Command park() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.stop(),
                new FollowPath(lilPark(follower())),
                new InstantCommand(() -> intakePower = 0)
        );
    }
}
