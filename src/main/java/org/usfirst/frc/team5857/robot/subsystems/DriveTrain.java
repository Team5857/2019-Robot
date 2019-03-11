package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team5857.robot.commands.*;

public class DriveTrain extends Subsystem {
	public SpeedController left1, left2, right1, right2;
	public SpeedControllerGroup leftMotors;
	public SpeedControllerGroup rightMotors;
	public DifferentialDrive allMotors;

	//PID
	int P, I, D = 1;
	int integral, previous_error, setpoint = 0;
	
	public DriveTrain() {
		left1 = new WPI_TalonSRX(13);					//initialize left motors on port 12
		left2 = new WPI_TalonSRX(14);
		right1 = new WPI_TalonSRX(0);
		right2 = new WPI_TalonSRX(1);
		leftMotors = new SpeedControllerGroup(left1, left2);
		rightMotors = new SpeedControllerGroup(right1, right2);
		allMotors = new DifferentialDrive(leftMotors, rightMotors);
	}
	public void driveLeftMotors(Joystick driveStick){
		
	}	
	public void tankDrive(Joystick driveStick) {
		// Specializes the two joysticks for either driving or turning
		// allMotors.arcadeDrive(driveStick.getRawAxis(3), driveStick.getRawAxis(0));
		// allMotors.arcadeDrive(-driveStick.getRawAxis(2), driveStick.getRawAxis(0));
		
		left1.set(-driveStick.getRawAxis(1));
		left2.set(-driveStick.getRawAxis(1));
		right1.set(driveStick.getRawAxis(5));
		right2.set(driveStick.getRawAxis(5));
	}


	public void driveWithSpeedSteer(double speed, double steer) {
		allMotors.arcadeDrive(speed, steer);
	}
	
	public double getLeftSpeed() {
		return left1.get();
	}

	public double getRightSpeed() {
		return right1.get();
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}	
}
