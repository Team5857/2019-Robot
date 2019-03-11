package org.usfirst.frc.team5857.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	Compressor compressor;
	DoubleSolenoid solenoid1, solenoid2;
	public boolean sol1State, sol2State; //true = forward, false = backward
	public boolean compressorOn;
	
	public Pneumatics()
	{
		compressor = new Compressor(0);
		solenoid1 = new DoubleSolenoid(0, 1);
		solenoid2 = new DoubleSolenoid(2, 3); 
		compressorOn = false;
		
		solenoid1.set(DoubleSolenoid.Value.kReverse);
		sol1State = false;
		solenoid2.set(DoubleSolenoid.Value.kReverse);
		sol2State = false; 

	}
	
	public void stopCompressor(){
		compressor.stop();
		compressorOn = false;
	}

	public void startCompressor(){
		compressor.start();
		compressorOn = true;
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
		if(solenoid2.get() == DoubleSolenoid.Value.kReverse) {
			solenoid2.set(DoubleSolenoid.Value.kForward);
			sol2State = true;
		}
		else {
			solenoid2.set(DoubleSolenoid.Value.kReverse);
			sol2State = false;
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
