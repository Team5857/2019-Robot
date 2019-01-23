package org.usfirst.frc.team5857.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5857.robot.Robot;

public class Pneumatic1TOG extends Command {

	public Pneumatic1TOG()
	{
		super("Pneumatic1TOG");
		requires(Robot.pneumatic);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.pneumatic.Pneumatic1Toggle();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}

}
