
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
		
		/** Logitech Dual Action**/
		
		new JoystickButton(driveStick, 8).whenPressed(new ToggleCompressor());
		new JoystickButton(driveStick, 2).whenPressed(new Pneumatic1TOG());
		new JoystickButton(driveStick, 1).whenPressed(new Pneumatic1REV());
		new JoystickButton(secondaryStick, 6).whenPressed(new ToggleIntake());
		new JoystickButton(secondaryStick, 5).whenPressed(new ToggleIntake());
			
		/**Saitek ST290**/
	
		
	}
	
	public Joystick getDriveStick() {
		return driveStick;
	}
	
	public Joystick getSecondaryStick() {
		return secondaryStick;
	}

}

