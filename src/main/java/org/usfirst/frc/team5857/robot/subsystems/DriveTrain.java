package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team5857.robot.*;

public class DriveTrain extends Subsystem {
	public static SpeedController left1;
	public static SpeedController left2;
	public static SpeedController right1;
	public static SpeedController right2;
	public static SpeedControllerGroup leftMotors;
	public static SpeedControllerGroup rightMotors;
	public static DifferentialDrive allMotors;

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
				
	public void tankDrive(Joystick driveStick) {
		//Specializes the two joysticks for either driving or turning
		// allMotors.arcadeDrive(driveStick.getRawAxis(3), driveStick.getRawAxis(0));
		// allMotors.arcadeDrive(-driveStick.getRawAxis(2), driveStick.getRawAxis(0));


		
		//Hugh Mungus Brain Mode
		// allMotors.tankDrive(-driveStick.getRawAxis(1), -driveStick.getRawAxis(5));

		//1900's
		// leftMotors.set(-driveStick.getRawAxis(1));
		// rightMotors.set(driveStick.getRawAxis(5));
		
		//Cave Man Style
		left1.set(-driveStick.getRawAxis(1));
		left2.set(-driveStick.getRawAxis(1));
		right1.set(driveStick.getRawAxis(5));
		right2.set(driveStick.getRawAxis(5));
	}

	public void autoDriveAtSpeed(double speed, double leftComp, double rightComp, double seconds) {
		allMotors.tankDrive(-leftComp*speed, -rightComp*speed);
		// leftMotors.set(-leftComp*speed);
		// rightMotors.set(-rightComp*speed);	
		Timer.delay(seconds);
		
		//stop
		allMotors.arcadeDrive(0, 0);
	}

	public void driveWithSpeedSteer(double speed, double steer) {
		allMotors.arcadeDrive(speed, steer);
	}

	public void stopRobot() {
		allMotors.arcadeDrive(0, 0);
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
