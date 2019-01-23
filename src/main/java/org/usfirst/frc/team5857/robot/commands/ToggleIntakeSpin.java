package org.usfirst.frc.team5857.robot.commands;

import org.usfirst.frc.team5857.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleIntakeSpin extends Command {

	public ToggleIntakeSpin() {
		super("ToggleIntakeSpin");
		requires(Robot.intake);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.intake.toggleIntakeSpin(Robot.oi.getSecondaryStick());
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}

}
