package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.ToggleIntake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	public static SpeedController intake1;
	public static SpeedController intake2;
	public static SpeedController intakeSpin;
	public static boolean intake1Active = false;
	public static boolean intake2Active = false;

	public Intake() {
		intake1 = new WPI_TalonSRX(2); // initialize left motors on port 12
		intake2 = new WPI_TalonSRX(3);
		intakeSpin = new WPI_TalonSRX(11);
	}
		
	public void toggleIntake(Joystick secondaryStick) {
		if(secondaryStick.getRawButtonPressed(3)) {
			if(intake1Active) {
				intake1Active = false;
			} else {
				intake1Active = true;
			}
			if(intake1Active){
				intake1.set(-0.6);
				intake2.set(0.6);
			} else{
				intake1.set(0);
				intake2.set(0);
			}
		}
		if(secondaryStick.getRawButtonPressed(2)) {
			if(intake1Active) {
				intake1Active = false;
			} else {
				intake1Active = true;
			}

			if(intake1Active){
				intake1.set(-0.6);
				intake2.set(0.6);
			} else{
				intake1.set(0);
				intake2.set(0);
			}
		}
	}

	public void toggleIntakeSpin(Joystick secondaryStick){

		intakeSpin.set(secondaryStick.getRawAxis(1));
		intakeSpin.set(-secondaryStick.getRawAxis(5));
		
	}
	public void autoDriveAtSpeed(double speed, double leftComp, double rightComp, double seconds) {
		
	}

	public boolean getIntakeSpeed1() {
		return intake1.getInverted();
	}

	public boolean getIntakeSpeed2() {
		return intake2.getInverted();
	}

	public double getIntakeSpinSpeed(){
		return intakeSpin.get();
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new ToggleIntake());
	}
	
}