package org.usfirst.frc.team5857.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5857.robot.Robot;

public class Pneumatic1FWD extends Command {

	public Pneumatic1FWD()
	{
		super("Pneumatic1FWD");
		requires(Robot.pneumatic);
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Robot.pneumatic.Pneumatic1FWD();
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}

}
