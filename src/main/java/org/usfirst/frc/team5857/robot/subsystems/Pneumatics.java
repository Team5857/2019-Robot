package org.usfirst.frc.team5857.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	Compressor compressor;
	DoubleSolenoid solenoid1;
	public static boolean sol1State; //true = forward, false = backward
//	public static boolean sol2State; //same as above
	
	public Pneumatics()
	{
		compressor = new Compressor(0);
		solenoid1 = new DoubleSolenoid(0, 1);
		//solenoid2 = new DoubleSolenoid(2,3);
		
		solenoid1.set(DoubleSolenoid.Value.kReverse);
		//solenoid2.set(DoubleSolenoid.Value.kReverse);
		
	}
	
	public void toggleCompressor()
	{
		if(compressor.enabled())
			compressor.stop();
		else
			compressor.start();
	}
	
	
	protected void initDefaultCommand() {}
	
	public void Pneumatic1Toggle()
	{
		if(solenoid1.get() == DoubleSolenoid.Value.kReverse) {
			solenoid1.set(DoubleSolenoid.Value.kForward);
		}
		else {
			solenoid1.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
//	public void Pneumatic2Toggle()
//	{
//		if(solenoid2.get() == DoubleSolenoid.Value.kReverse)
//			solenoid2.set(DoubleSolenoid.Value.kForward);
//		else solenoid2.set(DoubleSolenoid.Value.kReverse);
//	}
	
	public void Pneumatic1FWD()
	{
		solenoid1.set(DoubleSolenoid.Value.kForward);
	}
	
//	public void Pneumatic2FWD()
//	{
//		solenoid2.set(DoubleSolenoid.Value.kForward);
//	}
	
	public void Pneumatic1REV()
	{
		solenoid1.set(DoubleSolenoid.Value.kReverse);
	}
	
//	public void Pneumatic2REV()
//	{
//		solenoid2.set(DoubleSolenoid.Value.kReverse);
//	}
	
}
