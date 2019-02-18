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
	
	/**
	 * Raises arm completely up
	 * @param secondaryStick
	 */
	public void raiseArmLowHatch(Joystick secondaryStick) {
		//low hatch
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 2665) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}
	public void raiseArmMidHatch(Joystick secondaryStick) {
		//mid hatch
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 17843) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}
	public void raiseArmTopHatch(Joystick secondaryStick) {
		//top hatch
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 39597) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}
	public void raiseArmLowBall(Joystick secondaryStick) {
		//low ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 10803) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}
	public void raiseArmMidBall(Joystick secondaryStick) {
		//mid ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 27996) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}
	public void raiseArmTopBall(Joystick secondaryStick) {
		//top ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 41586) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}
	public void raiseArmCargoBall(Joystick secondaryStick) {
		//cargo ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 17801) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			// SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}

	/**
	 * Puts the arm back in resting position
	 * @param secondaryStick
	 */
	public void resetArm(Joystick secondaryStick) {
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) > 0) {
			leftarm.set(-0.1);
			rightarm.set(0.1);
			SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
			if(secondaryStick.getRawButtonPressed(7)) {
				rightarm.set(0);
				leftarm.set(0);
			}
		}
	}

	/**
	 * Uses joysticks to raise arms
	 * @param secondaryStick
	 */
	public void toggleArm(Joystick secondaryStick) {
		//Uses Controller Joysticks to raiser/lower Arm
		leftarm.set(-secondaryStick.getRawAxis(1));				
		rightarm.set(secondaryStick.getRawAxis(1));
		//Prints out encoder values
		//SmartDashboard.putNumber("EncoderValue", ((BaseMotorController) rightarm).getSelectedSensorPosition(0));
	}

	/**
	 * Sets encoder value to 0
	 */
	public void resetEncoder() {
		((BaseMotorController) rightarm).setSelectedSensorPosition(0, 0, 10);
		System.out.println("Encoder has been reset");
	}

	/**
	 * Checks if encoder value is at 0
	 */
	public boolean hasResetOccurred() {
		return ((BaseMotorController) rightarm).getSelectedSensorPosition(0) == 0;
	}

	// /**
	//  * Moves arm based on joystick
	//  * @param left
	//  * @param right
	//  */
	// public void toggleArm(Joystick left, Joystick right)
	// {
	// 	leftarm.set(-left.getRawAxis(1));
	// 	rightarm.set(right.getRawAxis(1));
	// }

	/**
	 * Used in log
	 * @return encoder value
	 */
	public double getEncoderValue() {
		return ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
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
