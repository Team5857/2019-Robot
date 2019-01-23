package org.usfirst.frc.team5857.robot.commands;

import org.usfirst.frc.team5857.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleCompressor extends Command {
	
	public ToggleCompressor()
	{
		super("ToggleCompressor");
		requires(Robot.pneumatic);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.pneumatic.toggleCompressor();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}
		
	

}
