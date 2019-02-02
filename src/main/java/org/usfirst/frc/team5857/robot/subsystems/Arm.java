package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import org.usfirst.frc.team5857.robot.commands.ToggleArm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem {
	public static SpeedController leftarm;
	public static SpeedController rightarm;

	public Arm() {
		leftarm = new WPI_TalonSRX(12); // initialize left motors on port 12
		rightarm = new WPI_TalonSRX(3);
	}
		
	public void toggleArm(Joystick secondaryStick) {
		//Uses Controller Joysticks to raiser/lower Arm
		leftarm.set(-secondaryStick.getRawAxis(1));				
		rightarm.set(secondaryStick.getRawAxis(1));
		//Prints out encoder values
		SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
		if(secondaryStick.getRawButtonPressed(8)) {
			resetEncoder();
		}
		if(secondaryStick.getRawButtonPressed(1)) {
				while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 42990) {
					leftarm.set(0.5);
					rightarm.set(-0.5);
					if(secondaryStick.getRawButtonPressed(7)) {
						rightarm.set(0);
						leftarm.set(0);
					}
				}
			}	
		if(secondaryStick.getRawButtonPressed(4)) {
			while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) >0) {
				leftarm.set(-0.5);
				rightarm.set(0.5);
				if(secondaryStick.getRawButtonPressed(7)) {
					rightarm.set(0);
					leftarm.set(0);
				}
			}
		}
	}
	public void resetEncoder() {
		((BaseMotorController) rightarm).setSelectedSensorPosition(0, 0, 10);
		System.out.println("Encoder has been reset");
	}

	public boolean hasResetOccurred() {
		return ((BaseMotorController) rightarm).getSelectedSensorPosition(0) == 0;
	}
	public void toggleArm(Joystick left, Joystick right)
	{
		leftarm.set(-left.getRawAxis(1));
		rightarm.set(right.getRawAxis(1));
	}


	public void autosecondaryAtSpeed(double speed, double leftComp, double rightComp, double seconds) {
		
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
