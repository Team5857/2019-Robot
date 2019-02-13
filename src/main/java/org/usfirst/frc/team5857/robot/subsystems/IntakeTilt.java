// package org.usfirst.frc.team5857.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// //import org.usfirst.frc.team5857.robot.commands.ToggleIntakeSpin;
// import org.usfirst.frc.team5857.robot.commands.*;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.SpeedController;
// import edu.wpi.first.wpilibj.command.Subsystem;

// public class IntakeTilt extends Subsystem {
// 	public static SpeedController intakeSpin;

// 	public IntakeTilt() {
// 		intakeSpin = new WPI_TalonSRX(2);
// 	}
	
// 	public void toggleIntakeSpin(Joystick secondaryStick){
// 		intakeSpin.set(secondaryStick.getRawAxis(5));
// 	}

// 	public double getIntakeSpinSpeed(){
// 		return intakeSpin.get();
// 	}
	
// 	public void initDefaultCommand() {
// 		setDefaultCommand(new ToggleIntakeSpin());
// 	}
	
// }
