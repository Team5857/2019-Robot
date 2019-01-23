package org.usfirst.frc.team5857.robot.commands;

import org.usfirst.frc.team5857.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleArm extends Command {

	public ToggleArm() {
		super("ToggleArm");
		requires(Robot.arm);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.arm.toggleArm(Robot.oi.getSecondaryStick());
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}

}
