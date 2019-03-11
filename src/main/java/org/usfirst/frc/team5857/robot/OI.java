
package org.usfirst.frc.team5857.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team5857.robot.commands.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	//Joysticks
	public Joystick driveStick, secondaryStick;
	
	public OI() { 
		driveStick = new Joystick(0);		//New XBOX Controller
		secondaryStick = new Joystick(1);	//Old XBOX Controller

		//X-Box Controllers
		new JoystickButton(driveStick, 8).whenPressed(new ToggleCompressor());		//Start
		new JoystickButton(driveStick, 2).whenPressed(new Pneumatic1TOG());			//B
		new JoystickButton(driveStick, 1).whenPressed(new Pneumatic2TOG());			//A
		new JoystickButton(driveStick, 5).whenPressed(new ToggleIntake());			//Left Bumper	
		new JoystickButton(driveStick, 6).whenPressed(new ToggleIntake());			//Right Bumper
		// new JoystickButton(secondaryStick, 7).whenPressed(new switchCameraModes());		//Back Button 
	}

	public Joystick getDriveStick() {
		return driveStick;
	}
	
	public Joystick getSecondaryStick() {
		return secondaryStick;
	}

}

