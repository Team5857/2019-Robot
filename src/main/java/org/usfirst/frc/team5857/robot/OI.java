
package org.usfirst.frc.team5857.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team5857.robot.commands.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public Joystick driveStick;
	public Joystick secondaryStick;
	
	public OI() { 
		driveStick = new Joystick(0);				//Logitech Dual Action
		secondaryStick = new Joystick(1);
		
		//X-Box Controllers
		new JoystickButton(driveStick, 8).whenPressed(new ToggleCompressor());	//Start
		new JoystickButton(driveStick, 2).whenPressed(new Pneumatic1TOG());		//B
		new JoystickButton(driveStick, 1).whenPressed(new Pneumatic1REV());		//A
		new JoystickButton(secondaryStick, 2).whenPressed(new ToggleIntake());	//B
		new JoystickButton(secondaryStick, 3).whenPressed(new ToggleIntake());	//X
		new JoystickButton(secondaryStick, 6).whenPressed(new ResetEncoder());	//RB
		new JoystickButton(secondaryStick, 1).whenPressed(new RaiseArmUp());	//A

	}
	
	public Joystick getDriveStick() {
		return driveStick;
	}
	
	public Joystick getSecondaryStick() {
		return secondaryStick;
	}

}

