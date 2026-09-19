package org.firstinspires.ftc.teamcode.brainstemPilotAuto;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import org.brainstemfirst.pilot.ftc.PilotOpMode;
import org.brainstemfirst.pilot.ftc.PilotRegistry;
import org.brainstemfirst.pilot.ftc.bezier.follower.BezierDrivePath;
import org.brainstemfirst.pilot.ftc.model.FieldConstants;
import org.firstinspires.ftc.teamcode.BrainSTEMRobot;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * TEAM-OWNED — Brainstem Pilot UI creates this file once and will not overwrite it.
 * Wire your robot, drive, and {@code PilotRegistry.addCommand} calls here.
 * Generated OpModes in {@code opmodeAutos/} extend this class.
 */
public abstract class PilotAutoBase extends PilotOpMode {
    protected BrainSTEMRobot robot;

    protected PilotAutoBase(String autoId) {
        super(autoId);
        BezierFollower.apply();
    }

    @Override
    protected void setupRobot(FieldConstants.Alliance alliance, Pose2d startPose) {
        robot = new BrainSTEMRobot(hardwareMap, telemetry, startPose);
        robot.drive.localizer.setPose(startPose);
    }

    @Override
    protected Supplier<Pose2d> pose() {
        return robot.drive::getPose;
    }

    @Override
    protected Supplier<PoseVelocity2d> lastVelRobot() {
        return robot.drive::lastVelRobot;
    }

    @Override
    protected Consumer<PoseVelocity2d> setDrivePowers() {
        return robot.drive::setDrivePowers;
    }

    @Override
    protected DoubleSupplier maxAngVel() {
        return robot.drive::maxAngVel;
    }

    @Override
    protected void registerCommands() {
        // Names must match the editor: PilotRegistry.addCommand("Subsystem", "Command", () -> action);
        
        PilotRegistry.addCommand("Subsystem", "Command", () ->
            packet -> {
            //ADD FUNCTION TO RUN HERE;
            //RETURN A BOOLEAN HERE;
            return false;
        });
    }

    @Override
    protected boolean updateRobot(TelemetryPacket packet) {
        robot.update();
        BezierFollower.apply();

        // Add telemetry for the BezierDrivePath status.
        BezierDrivePath.Status s = BezierDrivePath.status();
        telemetry.addData("target X", s.targetPoint.x);
        telemetry.addData("target Y", s.targetPoint.y);
        telemetry.addData("heading rad", s.targetHeadingRad);
        telemetry.addData("remaining", s.remainingLength);
        telemetry.update();
        return true;
    }
}