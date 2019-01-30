package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.ToggleArm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	public static SpeedController leftarm;
	public static SpeedController rightarm;

	public Arm() {
		leftarm = new WPI_TalonSRX(6); // initialize left motors on port 12
		rightarm = new WPI_TalonSRX(5);

	}
		
	public void toggleArm(Joystick secondaryStick) {
		if(secondaryStick.getRawButtonPressed(8)) {
		}
		leftarm.set(-secondaryStick.getRawAxis(1));					//left y-axis
		rightarm.set(secondaryStick.getRawAxis(1));
		
	}

	public void toggleArm(Joystick left, Joystick right)
	{
		Arm.leftarm.set(-left.getRawAxis(1));
		Arm.rightarm.set(right.getRawAxis(1));
	}

	public void autoDriveAtSpeed(double speed, double leftComp, double rightComp, double seconds) {
		
	}

	public double getLeftSpeed() {
		return leftarm.get();
	}
	
	public double getRightSpeed() {
		return rightarm.get();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ToggleArm());
	}
	
}
