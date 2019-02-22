package org.usfirst.frc.team5857.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	Compressor compressor;
	DoubleSolenoid solenoid1;
	Solenoid solenoid2;
	public static boolean sol1State; //true = forward, false = backward
	public static boolean sol2State;
	public static boolean compressorOn;
	
	public Pneumatics()
	{
		compressor = new Compressor(0);
		solenoid1 = new DoubleSolenoid(0, 1);
		solenoid2 = new Solenoid(4);
		compressorOn = false;
		
		solenoid1.set(DoubleSolenoid.Value.kReverse);
		sol1State = false;
		solenoid2.set(true);
		sol2State = true;

	}
	
	public void stopCompressor(){
		compressor.stop();
		compressorOn = false;
	}

	public void toggleCompressor()
	{
		if(compressor.enabled()) {
			compressor.stop();
			compressorOn = false;
		}
		else {
			compressor.start();
			compressorOn = true;
		}
	}
	
	public void Pneumatic1Toggle() {
		if(solenoid1.get() == DoubleSolenoid.Value.kReverse) {
			solenoid1.set(DoubleSolenoid.Value.kForward);
			sol1State = true;
		}
		else {
			solenoid1.set(DoubleSolenoid.Value.kReverse);
			sol1State = false;
		}
	}

	public void Pneumatic2Toggle() {
		if(solenoid2.get()) {
			solenoid2.set(false);
			sol2State = false;
		}
		else {
			solenoid2.set(true);
			sol2State = true;
		}
	}
	

	
	public boolean isCompressorOn(){
		return compressorOn;
	}

	public boolean sol1State(){
		return sol1State;
	}

	public boolean sol2State(){
		return sol2State;
	}
	protected void initDefaultCommand() {}
}
