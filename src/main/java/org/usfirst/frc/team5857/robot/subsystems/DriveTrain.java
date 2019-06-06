package org.usfirst.frc.team5857.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team5857.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	public SpeedController left1, left2, right1, right2;

	public DriveTrain() {
		left1 = new WPI_TalonSRX(2);					//initialize left motors on port 12
		left2 = new WPI_TalonSRX(3);
		right1 = new WPI_TalonSRX(12);
		right2 = new WPI_TalonSRX(14);
	}

	public void tankDrive(Joystick driveStick) {
		left1.set(-driveStick.getRawAxis(1));
		left2.set(-driveStick.getRawAxis(1));
		right1.set(driveStick.getRawAxis(5));
		right2.set(driveStick.getRawAxis(5));
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
