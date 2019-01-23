package org.usfirst.frc.team5857.robot.commands;

import org.usfirst.frc.team5857.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {

	public DriveWithJoystick() {
		super("DriveWithJoystick");
		requires(Robot.drivetrain);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.drivetrain.tankDrive(Robot.oi.getDriveStick());
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}

}
