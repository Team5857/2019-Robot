
package org.usfirst.frc.team5857.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team5857.robot.commands.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public Joystick driveStick;
	public Joystick secondaryStick;
	
	public OI() { 
		driveStick = new Joystick(0);		//New XBOX Controller
		secondaryStick = new Joystick(1);	//Old XBOX Controller

		//X-Box Controllers
		new JoystickButton(driveStick, 8).whenPressed(new ToggleCompressor());	//Start
		new JoystickButton(driveStick, 2).whenPressed(new Pneumatic1TOG());		//B
		new JoystickButton(driveStick, 1).whenPressed(new Pneumatic1REV());		//A
		new JoystickButton(secondaryStick, 2).whenPressed(new ToggleIntake());	//B
		new JoystickButton(secondaryStick, 3).whenPressed(new ToggleIntake());	//X
		new JoystickButton(secondaryStick, 8).whenPressed(new ResetEncoder());	//Start
		new JoystickButton(secondaryStick, 1).whenPressed(new RaiseArmUp());	//A
		new JoystickButton(secondaryStick, 4).whenPressed(new ResetArm());		//Y
		new JoystickButton(driveStick, 4).whenPressed(new StopRobot());			//Y
	}

	public Joystick getDriveStick() {
		return driveStick;
	}
	
	public Joystick getSecondaryStick() {
		return secondaryStick;
	}

}

