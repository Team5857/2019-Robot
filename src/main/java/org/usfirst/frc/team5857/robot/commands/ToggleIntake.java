package org.usfirst.frc.team5857.robot.commands;

import org.usfirst.frc.team5857.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleIntake extends Command {

	public ToggleIntake() {
		super("ToggleIntake");
		requires(Robot.intake);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.intake.toggleIntake(Robot.oi.getSecondaryStick());
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}

}
