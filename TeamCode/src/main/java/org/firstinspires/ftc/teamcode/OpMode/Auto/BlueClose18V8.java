package org.firstinspires.ftc.teamcode.OpMode.Auto;

import static org.firstinspires.ftc.teamcode.Core.Constants.*;
import static org.firstinspires.ftc.teamcode.Core.Paths.BlueClose18V5.*;
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

@Autonomous(name = "BlueClose18V8")
public class BlueClose18V8 extends NextFTCOpMode {

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
                        new SequentialGroup(
                                NextTurret.INSTANCE.resetTurret(),
                                NextTurret.INSTANCE.faceWhileMovingCommand(
                                        blueGoalPose,
                                        () -> follower().getPose(),
                                        () -> follower().getVelocity().getXComponent(),
                                        () -> follower().getVelocity().getYComponent()

                                )
                        ),
                        NextFlywheel.INSTANCE.updateDistanceRPM(blueGoalPose, () -> follower().getPose()),
                        NextFlywheel.INSTANCE.stop()
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
        return new ParallelGroup(
                doTheCalculations,
                new SequentialGroup(
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
                        park()
                )
        );
    }

    private Command shootAndIntake1() {
        return new SequentialGroup(
                new InstantCommand(() -> intakePower = passIn),
                NextTurret.INSTANCE.resetYaw(),
                new ParallelGroup(
                        new FollowPath(firstShoot(follower())),
                        new SequentialGroup(
                                new WaitUntil(() -> gDist > 60),
                                shootTheBalls

                        )
                ),
                new ParallelGroup(
                        new FollowPath(firstIntake(follower())),
                        new InstantCommand(() -> gatePos = gateBlock)
                )
        );
    }

    private Command shoot2() {
        return new SequentialGroup(
                new FollowPath(secondShoot(follower())),
                shootTheBalls
        );
    }

    private Command gateIntake1() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(gate1(follower()), true),
                        new SequentialGroup(
                                new Delay(shootWaitGateClose),
                                new InstantCommand(() -> gatePos = gateBlock)
                        )
                ),
                new Delay(gateWait)
        );
    }

    private Command shoot3() {
        return new SequentialGroup(
                new FollowPath(thirdShoot(follower())),
                shootTheBalls
        );
    }

    private Command gateIntake2() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(gate2(follower()), true),
                        new SequentialGroup(
                                new Delay(shootWaitGateClose),
                                new InstantCommand(() -> gatePos = gateBlock)
                        )
                ),
                new Delay(gateWait)
        );
    }

    private Command shoot4() {
        return new SequentialGroup(
                new FollowPath(fourthShoot(follower())),
                shootTheBalls
        );
    }

    private Command gateIntake3() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(gate3(follower()), true),
                        new SequentialGroup(
                                new Delay(shootWaitGateClose),
                                new InstantCommand(() -> gatePos = gateBlock)
                        )
                ),
                new Delay(gateWait)
        );
    }

    private Command shoot5() {
        return new SequentialGroup(
                new FollowPath(fifthShoot(follower())),
                shootTheBalls
        );
    }

    private Command intakeClose() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(closeIntake(follower())),
                        new SequentialGroup(
                                new Delay(shootWaitGateClose),
                                new InstantCommand(() -> gatePos = gateBlock)
                        )
                )
        );
    }

    private Command shoot6() {
        return new SequentialGroup(
                new FollowPath(sixthShoot(follower())),
                shootTheBalls

        );
    }

    private Command park() {
        return new SequentialGroup(
                NextFlywheel.INSTANCE.stop(),
                new FollowPath(lilPark(follower())),
                new InstantCommand(() -> intakePower = 0)

        );
    }

    Command doTheCalculations = new ParallelGroup(
            NextFlywheel.INSTANCE.calculations(
                    blueGoalPose,
                    () -> follower().getPose(),
                    () -> follower().getVelocity().getXComponent(),
                    () -> follower().getVelocity().getYComponent()),
            NextFlywheel.INSTANCE.foreverRun()
    );

    Command shootTheBalls = new SequentialGroup(
            new InstantCommand(() -> gatePos = gateAllow),
            new Delay(shootWait),
            new InstantCommand(() -> gatePos = gateBlock)
    );
}
