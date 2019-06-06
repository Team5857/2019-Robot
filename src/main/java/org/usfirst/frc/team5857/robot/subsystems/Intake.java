package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.ToggleIntake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	public SpeedController feeder, outtake, outtakeSlow;
	public boolean feederActive, outtakeActive;
//test
	public Intake() {
	feeder = new WPI_TalonSRX(15); 
    outtake = new WPI_TalonSRX(0);
    outtakeSlow = new WPI_TalonSRX(7);
    feederActive = false;
    outtakeActive = false;
	}

	public void toggleIntake(Joystick driveStick) {
		if(driveStick.getRawButtonPressed(5)) { //Left Bumper
			//Intake 
			if(feederActive) {
				feederActive = false;
			} else {
				feederActive = true;
			}
			if(feederActive){
				feeder.set(.3);
			} else{
				feeder.set(0);
			}
		}
		if(driveStick.getRawButtonPressed(6)) { //Right Bumper
			//Outtake
			if(outtakeActive) {
				outtakeActive = false;
			} else {
				outtakeActive = true;
			}
			if(outtakeActive){
				outtake.set(.3);
			} else{
				outtake.set(0);
			}
		}
	}
	public boolean getOuttakeSpeed() {
		return outtake.getInverted();
	}

	public boolean getFeederSpeed() {
		return feeder.getInverted();
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new ToggleIntake());

	}
	
}
