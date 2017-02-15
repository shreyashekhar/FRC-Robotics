// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team5905.subsystems;

import org.usfirst.frc.team5905.RobotMap;
import org.usfirst.frc.team5905.commands.*;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Shooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController speedController = RobotMap.shooterSpeedController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static final double MAX_SPEED = 0.8;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public static final double SPEED_INCREMENT = 0.05;
    public static final String SHOOTER_SPEED_LABEL = "ShooterSpeed";
    
    private double currentSpeed;

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new ShooterStop());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void stopShooter(){
    	speedController.set(0.0);
    	currentSpeed = 0;
    	SmartDashboard.putString(SHOOTER_SPEED_LABEL, "" + currentSpeed);
    }
    
    public void increaseSpeed(){
    	if (currentSpeed + SPEED_INCREMENT <= MAX_SPEED){
    		double newSpeed = currentSpeed + SPEED_INCREMENT;
    		speedController.set(newSpeed);
    		currentSpeed = newSpeed;
    		SmartDashboard.putString(SHOOTER_SPEED_LABEL, "" + currentSpeed);
    	}    	
    }
    
    public void decreaseSpeed(){
    	if (currentSpeed - SPEED_INCREMENT >= 0.0){
    		double newSpeed = currentSpeed - SPEED_INCREMENT;
    		speedController.set(newSpeed);
    		currentSpeed = newSpeed;
    		SmartDashboard.putString(SHOOTER_SPEED_LABEL, "" + currentSpeed);
    	} 
    }
    
    
}

