package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import org.usfirst.frc.team5857.robot.commands.ToggleArm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem {
	public SpeedController leftarm;
	public SpeedController rightarm;
	public static SpeedController intakeSpin;
	public double armValue;

	public Arm() {
		leftarm = new WPI_TalonSRX(12); // initialize left motors on port 12
		rightarm = new WPI_TalonSRX(3);
	}
	
	/**
	 * Uses joysticks to raise arms
	 * @param secondaryStick
	 */
	public void toggleArm(Joystick secondaryStick) {
		leftarm.set(-secondaryStick.getRawAxis(1));
		rightarm.set(secondaryStick.getRawAxis(1));
	}

	/**
	 * Raises arm completely up
	 * @param secondaryStick
	 */

	public void moveArm(String direction){
		if(direction.equals("up")){
			leftarm.set(0.5);
			rightarm.set(-0.5);
		} else if(direction.equals("down")){
			leftarm.set(-0.5);
			rightarm.set(0.5);
		}
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
