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

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5857.robot.subsystems.Arm;
import org.usfirst.frc.team5857.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5857.robot.subsystems.Intake;
import org.usfirst.frc.team5857.robot.subsystems.Pneumatics;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team5857.robot.commands.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends TimedRobot {
	private XboxController driveController = new XboxController(0);
	private Joystick secondaryController = new Joystick(1);
	public static DriveTrain drivetrain;
	public static Arm arm;
	public static Intake intake;
	public static AHRS ahrs;
	public PowerDistributionPanel pdp;
	public static Pneumatics pneumatic;
	public static Timer timer;
	public static double errorX;
	public static double areaTarget;
	public static DriveTrain driveTrain;
	public static OI oi;

	//Limelight Values
	public boolean limelightHasTarget = false;
	public double limelightDrive = 0.0;
	public double limelightSteer = 0.0;

	//NavX Values
	public double angleOfRobot = 0; 

	//Arm
	public double armEncoder = 0;


	Command autonomousCommand, Auto_BeginLeft, Auto_BeginMid, Auto_BeginRight;
	SendableChooser chooser;
	
	public void robotInit() {
		//Camera 
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(640, 480);
			
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
			
			Mat source = new Mat();
			Mat output = new Mat();
			
			while(!Thread.interrupted()) {
				cvSink.grabFrame(source);
				Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
				outputStream.putFrame(output);
			}
		}).start();

		//Class Initializations
		drivetrain = new DriveTrain();
		arm = new Arm();
		intake = new Intake();
		pdp = new PowerDistributionPanel(0);	
		pneumatic = new Pneumatics();
		oi = new OI();

        try {
            ahrs = new AHRS(SPI.Port.kMXP);
            ahrs.enableLogging(true);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

		//set values
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
		
		pdp.clearStickyFaults();

		chooser = new SendableChooser();
		SmartDashboard.putData("Auto mode", chooser);

		CameraServer.getInstance().startAutomaticCapture();
		//disable compressor
		pneumatic.stopCompressor();
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
		boolean auto = driveController.getXButton();
		boolean lowHatch = secondaryController.getRawButtonPressed(11);
		boolean midHatch = secondaryController.getRawButtonPressed(9);
		boolean topHatch = secondaryController.getRawButtonPressed(7);
		boolean lowBall = secondaryController.getRawButtonPressed(12);
		boolean midBall = secondaryController.getRawButtonPressed(10);
		boolean topBall = secondaryController.getRawButtonPressed(8);
		boolean cargoBall = secondaryController.getRawButtonPressed(4);
		boolean resetArm = secondaryController.getRawButtonPressed(1);

		//Updates Limelight Values
		UpdateLimelightTracking();
		
		//Checks to see if X button is pressed
		if(auto){
			if(limelightHasTarget) drivetrain.driveWithSpeedSteer(limelightDrive, limelightSteer);
			else drivetrain.driveWithSpeedSteer(0, 0);
		}
		if(lowHatch) {if(armEncoder < 2665) {arm.moveArm("up");} else if(armEncoder > 2665){arm.moveArm("down");}}
		if(midHatch) {if(armEncoder < 17843) {arm.moveArm("up");} else if(armEncoder > 17843){arm.moveArm("down");}}
		if(topHatch) {if(armEncoder < 39597) {arm.moveArm("up");} else if(armEncoder > 39597){arm.moveArm("down");}}
		if(lowBall) {if(armEncoder < 10803) {arm.moveArm("up");} else if(armEncoder > 10803){arm.moveArm("down");}}
		if(midBall) {if(armEncoder < 27996) {arm.moveArm("up");} else if(armEncoder > 27996){arm.moveArm("down");}}
		if(topBall) {if(armEncoder < 41586) {arm.moveArm("up");} else if(armEncoder > 41586){arm.moveArm("down");}}
		if(cargoBall) {if(armEncoder < 17801) {arm.moveArm("up");} else if(armEncoder > 17801){arm.moveArm("down");}}
		if(resetArm) {if(armEncoder > 500) {arm.moveArm("down");}}

		log();
		operatorControl();
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	/**
	 * This function updates limelight values for autonomous driving
	 */
	public void UpdateLimelightTracking(){
		final double STEER_K = 0.03;                    // how hard to turn toward the target
        final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 23;        // Area of the target when the robot reaches the wall
        final double MAX_DRIVE = 0.7;                  // Simple speed limit so we don't drive too fast

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

		if(tv <1.0){
			limelightHasTarget = false;
			limelightDrive = 0;
			limelightSteer = 0;
			return;
		}

		limelightHasTarget = true;

		//Steering
		double steer_cmd = tx * STEER_K;
		limelightSteer = steer_cmd;

		//Drive Forward
		double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;

		if(drive_cmd > MAX_DRIVE){
			drive_cmd = MAX_DRIVE;
		}
		limelightDrive = drive_cmd;

		//Update Encoder
		armEncoder = arm.getEncoderValue();
		//post to smart dashboard periodically
		SmartDashboard.putNumber("LimelightX", tx);
		SmartDashboard.putNumber("LimelightArea", ta);
		SmartDashboard.putNumber("LimelightY", ty);
		SmartDashboard.putNumber("LimelightTargets", tv);
		SmartDashboard.putString("DB/String 4", "LimelightSteer " + String.format( "%.2f", limelightSteer));
		SmartDashboard.putString("DB/String 3", "LimelightDrive " + String.format( "%.2f", limelightDrive));
		Scheduler.getInstance().run();
	}

	/**
	 * Updates NavX Values For Driving Purposes
	 */
	public void operatorControl() {
		
		boolean zero_yaw_pressed = driveController.getYButton();
		if ( zero_yaw_pressed ) {
			ahrs.zeroYaw();
		}

		/* Checks if everything is connected                                        */
		SmartDashboard.putBoolean("Connected", ahrs.isConnected());
		SmartDashboard.putBoolean("Moving", ahrs.isMoving());
		SmartDashboard.putBoolean("Rotating", ahrs.isRotating());

		/*Prints Gyro Value 														*/
		SmartDashboard.putNumber("Angle", ahrs.getAngle());
		angleOfRobot = ahrs.getAngle();

		/* Display estimates of velocity/displacement.  Note that these values are  */
		/* not expected to be accurate enough for estimating robot position on a    */
		/* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
		/* of these errors due to single (velocity) integration and especially      */
		/* double (displacement) integration.                                       */
		SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
		SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
		SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
		SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
	}

	public void log() {
		//Prints Speed of Left Side in Dashboard (Tab: Basic)
		SmartDashboard.putString("DB/String 0", "Speed (L): " + String.format( "%.2f", (drivetrain.getLeftSpeed() * 100)) + "%");
		//Prints Speed of Right Side in Dashboard (Tab: Basic)
		SmartDashboard.putString("DB/String 5", "Speed (R): " + String.format( "%.2f", (drivetrain.getRightSpeed() * 100)) + "%");
		//Prints Encoder value for arm in Dashboard (Tab: Basic)	
		SmartDashboard.putString("DB/String 1", "Encoder of the Arm: " + String.format( "%.2f", arm.getEncoderValue()));
		//Prints compressor state
		SmartDashboard.putBoolean("Compressor On", pneumatic.isCompressorOn());
		//Prints solenoid state
		SmartDashboard.putBoolean("Solenoid1 State", pneumatic.sol1State());
		SmartDashboard.putBoolean("Solenoid2 State", pneumatic.sol2State());
		Timer.delay(0.05);
	}
}
