//==============================================
// Robot AMOS - Experimental Code
// Last Modified 03/18/2017
// Author: WVR Programming Dept. 
// Notes:
//	-Autonomous: Based on FOR loop timing system
//	-Outdated subsystems (Pulley, Intake, etc.)
//	-Status: Incomplete	-Build? True
//==============================================

package org.usfirst.frc.team5857.robot;

import org.usfirst.frc.team5857.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5857.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static DriveTrain drivetrain;
	public static Intake intake;
	public static PowerDistributionPanel pdp;

	public static OI oi;
	public static Timer timer;

	Command autonomousCommand;
	SendableChooser chooser;
	
	public void robotInit() {
		//Class Initializations
		drivetrain = new DriveTrain();
		intake = new Intake();
		pdp = new PowerDistributionPanel(0);	
		oi = new OI();


		pdp.clearStickyFaults();

		chooser = new SendableChooser();
		SmartDashboard.putData("Auto mode", chooser);
		}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit(){
		
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to 
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) autonomousCommand.cancel();

		Scheduler.getInstance().removeAll();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		


		log();
		// operatorControl();
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void log() {
		//Prints Speed of Left Side in Dashboard (Tab: Basic)
		SmartDashboard.putString("DB/String 0", "Speed (L): " + String.format( "%.2f", (drivetrain.getLeftSpeed() * 100)) + "%");
		//Prints Speed of Right Side in Dashboard (Tab: Basic)
		SmartDashboard.putString("DB/String 5", "Speed (R): " + String.format( "%.2f", (drivetrain.getRightSpeed() * 100)) + "%");
		Timer.delay(0.05);
	}
}
