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
			//Feeder
			if(feederActive) {
				feederActive = false;
			} else {
				feederActive = true;
			}
			if(feederActive){
				feeder.set(.3); //value in parentheses is percentage of power
			} else{
				feeder.set(0);
			}
		}
		outtake.set(driveStick.getRawAxis(2)); //left trigger
		outtakeSlow.set(driveStick.getRawAxis(3)); //right trigger
	}
	public double getOuttakeSpeed() {
		return outtake.get();
	}

	public double getOuttakeSlowSpeed(){
		return outtakeSlow.get();
	}

	public boolean getFeederSpeed() {
		return feeder.getInverted();
	}
	

	public void initDefaultCommand() {
		setDefaultCommand(new ToggleIntake());

	}
	
}
