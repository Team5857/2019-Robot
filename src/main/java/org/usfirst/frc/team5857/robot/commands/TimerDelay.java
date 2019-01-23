package org.usfirst.frc.team5857.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TimerDelay extends Command {
	
	public double seconds;

	public TimerDelay(double sec)
	{
		super("TimerDelay");
		seconds = sec;
	}
	
	protected void initialize() {}
	
	protected void execute() {
		Timer.delay(seconds);
	}
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	protected void end() {}
	
	protected void interrupted() {
		end();
	}
}
