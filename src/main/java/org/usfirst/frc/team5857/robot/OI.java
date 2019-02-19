
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
		new JoystickButton(driveStick, 8).whenPressed(new ToggleCompressor());		//Start
		new JoystickButton(driveStick, 2).whenPressed(new Pneumatic1TOG());			//B
		new JoystickButton(driveStick, 1).whenPressed(new Pneumatic1REV());			//A
		new JoystickButton(driveStick, 5).whenPressed(new ToggleIntake());			//Left Bumper	
		new JoystickButton(driveStick, 6).whenPressed(new ToggleIntake());			//Right Bumper
		new JoystickButton(driveStick, 4).whenPressed(new StopRobot());				//Y
		new JoystickButton(secondaryStick, 5).whenPressed(new ResetEncoder());		//Joystick Button 5
		new JoystickButton(secondaryStick, 11).whenPressed(new RaiseArmLowHatch());	//Side Button 11
		new JoystickButton(secondaryStick, 9).whenPressed(new RaiseArmMidHatch());	//Side Button 9
		new JoystickButton(secondaryStick, 7).whenPressed(new RaiseArmTopHatch());	//Side Button 7
		new JoystickButton(secondaryStick, 12).whenPressed(new RaiseArmLowBall());	//Side Button 12
		new JoystickButton(secondaryStick, 10).whenPressed(new RaiseArmMidBall());	//Side Button 10	
		new JoystickButton(secondaryStick, 8).whenPressed(new RaiseArmTopBall());	//Side Button 8
		new JoystickButton(secondaryStick, 4).whenPressed(new RaiseArmCargoBall());	//Joystick Button 3
		new JoystickButton(secondaryStick, 1).whenPressed(new ResetArm());			//Trigger
	}

	public Joystick getDriveStick() {
		return driveStick;
	}
	
	public Joystick getSecondaryStick() {
		return secondaryStick;
	}

}

