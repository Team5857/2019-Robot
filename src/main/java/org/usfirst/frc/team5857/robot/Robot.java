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

public class Robot extends TimedRobot {
	private XboxController driveController = new XboxController(0);
	public static DriveTrain drivetrain;
	public static Arm arm;
	public static Intake intake;
	public static AHRS ahrs;
	//remove later
	//public static IntakeTilt intakeTilt;
	public PowerDistributionPanel pdp;
	public static Pneumatics pneumatic;
	public static Timer timer;
	public static double errorX;
	public static double areaTarget;
	public static DriveTrain driveTrain;
	public static OI oi;

	private boolean limelightHasTarget = false;
	private double limelightDrive = 0.0;
	private double limelightSteer = 0.0;
	//public AHRS ahrs;


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
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
		
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

		//Updates Limelight Values
		UpdateLimelightTracking();
		
		//Checks to see if X button is pressed
		if(auto){
			if(limelightHasTarget){
				drivetrain.driveWithSpeedSteer(limelightDrive, limelightSteer);
			}
			else{
				drivetrain.driveWithSpeedSteer(0, 0);
			}
		}
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

	public void UpdateLimelightTracking(){
		final double STEER_K = 0.04;                    // how hard to turn toward the target
        final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 5;        // Area of the target when the robot reaches the wall
        final double MAX_DRIVE = 0.5 ;                   // Simple speed limit so we don't drive too fast

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

		//post to smart dashboard periodically
		SmartDashboard.putNumber("LimelightX", tx);
		SmartDashboard.putNumber("LimelightArea", ta);
		SmartDashboard.putNumber("LimelightY", ty);
		SmartDashboard.putNumber("LimelightTargets", tv);
		SmartDashboard.putString("DB/String 4", "LimelightSteer " + String.format( "%.2f", limelightSteer));
		SmartDashboard.putString("DB/String 3", "LimelightDrive " + String.format( "%.2f", limelightDrive));
		Scheduler.getInstance().run();
	}

	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			Timer.delay(0.020);		/* wait for one motor update time period (50Hz)     */
			
			boolean zero_yaw_pressed = driveController.getYButton();
			if ( zero_yaw_pressed ) {
				ahrs.zeroYaw();
			}

			/* Display 6-axis Processed Angle Data                                      */
			SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
			SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
			SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
			SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
			SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
			
			/* Display tilt-corrected, Magnetometer-based heading (requires             */
			/* magnetometer calibration to be useful)                                   */
			
			SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
			
			/* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
			SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

			/* These functions are compatible w/the WPI Gyro Class, providing a simple  */
			/* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
			
			SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
			SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

			/* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
			
			SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
			SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
			SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
			SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

			/* Display estimates of velocity/displacement.  Note that these values are  */
			/* not expected to be accurate enough for estimating robot position on a    */
			/* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
			/* of these errors due to single (velocity) integration and especially      */
			/* double (displacement) integration.                                       */
			
			SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
			SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
			SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
			SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
			
			/* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
			/* NOTE:  These values are not normally necessary, but are made available   */
			/* for advanced users.  Before using this data, please consider whether     */
			/* the processed data (see above) will suit your needs.                     */
			
			SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
			SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
			SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
			SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
			SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
			SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
			SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
			SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
			SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
			SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());
			SmartDashboard.putNumber(   "IMU_Timestamp",        ahrs.getLastSensorTimestamp());
			
			/* Omnimount Yaw Axis Information                                           */
			/* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
			AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
			SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
			SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
			
			/* Sensor Board Information                                                 */
			SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());
			
			/* Quaternion Data                                                          */
			/* Quaternions are fascinating, and are the most compact representation of  */
			/* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
			/* from the Quaternions.  If interested in motion processing, knowledge of  */
			/* Quaternions is highly recommended.                                       */
			SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
			SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
			SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
			SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());
			
			/* Connectivity Debugging Support                                           */
			SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
			SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
		}
	}
	public void log() {
		//Prints Speed of Left Side in Dashboard (Tab: Basic)
		SmartDashboard.putString("DB/String 0", "Speed (L): " + String.format( "%.2f", (drivetrain.getLeftSpeed() * 100)) + "%");
		//Prints Speed of Right Side in Dashboard (Tab: Basic)
		SmartDashboard.putString("DB/String 5", "Speed (R): " + String.format( "%.2f", (drivetrain.getRightSpeed() * 100)) + "%");
		//Prints Encoder value for arm in Dashboard (Tab: Basic)	
		SmartDashboard.putString("DB/String 1", "Arm Encoder: " + String.format( "%.2f", arm.getEncoderValue()));
		Timer.delay(0.05);
	}
}
