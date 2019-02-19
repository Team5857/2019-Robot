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
	public static double armValue;

	public Arm() {
		leftarm = new WPI_TalonSRX(12); // initialize left motors on port 12
		rightarm = new WPI_TalonSRX(3);
	}
	
	/**
	 * Uses joysticks to raise arms
	 * @param secondaryStick
	 */
	public void toggleArm(Joystick secondaryStick) {
		//Uses Controller Joysticks to raiser/lower Arm
		leftarm.set(-secondaryStick.getRawAxis(1));				
		rightarm.set(secondaryStick.getRawAxis(1));
	}

	/**
	 * Raises arm completely up
	 * @param secondaryStick
	 */

	public void raiseArmLowHatch() {
		//low hatch
		while(armValue < 2665) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
	}
public void raiseArmMidHatch() {
		//mid hatch
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 17843) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
	}
	public void raiseArmTopHatch() {
		//top hatch
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 39597) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
	}
	public void raiseArmLowBall() {
		//low ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 10803) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
	}
	public void raiseArmMidBall() {
		//mid ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 27996) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
	}
	public void raiseArmTopBall() {
		//top ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 41586) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
	}
	public void raiseArmCargoBall() {
		//cargo ball
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) < 17801) {
			leftarm.set(0.5);
			rightarm.set(-0.5);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
	}

	// public void raiseArm(){
	// 	leftarm.set(.5);
	// 	rightarm.set(-.5);
	// }
	/**
	 * Puts the arm back in resting position
	 * @param secondaryStick
	 */
	public void resetArm() {
		//top hatch
		while(((BaseMotorController) rightarm).getSelectedSensorPosition(0) > 0) {
			leftarm.set(-0.1);
			rightarm.set(0.1);
			armValue = ((BaseMotorController) rightarm).getSelectedSensorPosition(0);
		}
		leftarm.set(0);
		rightarm.set(0);
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
