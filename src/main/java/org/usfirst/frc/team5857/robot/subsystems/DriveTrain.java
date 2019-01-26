package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	public static SpeedController left1;
	public static SpeedController left2;
	public static SpeedController right1;
	public static SpeedController right2;
	public DriveTrain() {
		left1 = new WPI_TalonSRX(7);					//initialize left motors on port 12
		left2 = new WPI_TalonSRX(8);
		right1 = new WPI_TalonSRX(3);
		right2 = new WPI_TalonSRX(4);
	}
		
	public void tankDrive(Joystick driveStick) {
		left1.set(-driveStick.getRawAxis(1));					//left y-axis
		left2.set(-driveStick.getRawAxis(1));
		right1.set(driveStick.getRawAxis(5));
		right2.set(driveStick.getRawAxis(5));
	}

	public void tankDrive(Joystick left, Joystick right)
	{
		DriveTrain.left1.set(left.getRawAxis(1));
		DriveTrain.left2.set(left.getRawAxis(1));
		DriveTrain.right1.set(right.getRawAxis(5));
		DriveTrain.right2.set(right.getRawAxis(5));
	}

	public void autoDriveAtSpeed(double speed, double leftComp, double rightComp, double seconds) {
		left1.set(-leftComp*speed);
		left2.set(-leftComp*speed);
		right1.set(-rightComp*speed);
		right2.set((-rightComp*speed));
		
		Timer.delay(seconds);
		
		//stop
		left1.set(0);
		left2.set(0);
		right1.set(0);
		right2.set(0);
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
