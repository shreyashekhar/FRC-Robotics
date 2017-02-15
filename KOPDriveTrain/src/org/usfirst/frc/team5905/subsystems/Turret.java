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
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turret extends PIDSubsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController speedController = RobotMap.turretSpeedController;
    private final Encoder encoder = RobotMap.turretEncoder;
    
    //These are all constants that need calibration
    public static final double TURRET_ROTATION_RANGE_IN_DEGREES = 120.0;
    public static final long ENCODER_PULSES_PER_ROTATION = 400;  // Assuming 100 PPR, but 400 in 4x mode
    public static final double NUMBER_OF_MOTOR_ROTATIONS_TO_ACHIEVE_ONE_360_TURRET_ROTATION = 40;
    public static final int MAX_NUMBER_OF_SECONDS_TO_GET_TURRET_TO_LEFT_END = 5;
    public static final double SPEED_TO_RESET_TURRET = 0.8;
    //Position of the turret in degrees from max left extent
    //When turret is moving, this is the set point, not the actual position. The
    //actual position will get to the set point eventually
    private double position;
    
    private static final double MOVEMENT_INCREMENT_IN_DEGREES = 3.0;
    
    public static final String TURRET_POSITION_LABEL = "Turret Degrees from Left End";
    

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Initialize your subsystem here
    public Turret() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("Turret", 1.0, 0.0, 0.0);
        setAbsoluteTolerance(0.5);
        getPIDController().setContinuous(false);
        getPIDController().setInputRange(0.0, TURRET_ROTATION_RANGE_IN_DEGREES);
        getPIDController().setOutputRange(-1, 1);
        LiveWindow.addActuator("Turret", "PIDSubsystem Controller", getPIDController());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void resetTurret(){
    	//Disable the PID
    	getPIDController().disable();
    	//Start the motor
    	speedController.set(SPEED_TO_RESET_TURRET);
    	try {
    		//Wait for the turret to reach left end, after which you know for sure where it is
    		//no matter where it started from
    		Thread.sleep(MAX_NUMBER_OF_SECONDS_TO_GET_TURRET_TO_LEFT_END*1000);
    	} catch (InterruptedException e){	
    	}
    	//Stop the motor
    	speedController.set(0.0);   	
    	encoder.reset();
    	position = 0.0;
    	SmartDashboard.putString(TURRET_POSITION_LABEL, "" + position);
    	getPIDController().enable();
    	moveDegrees(TURRET_ROTATION_RANGE_IN_DEGREES/2.0);
    	try {
    		//Wait for the turret to rotate back to center position
    		Thread.sleep(MAX_NUMBER_OF_SECONDS_TO_GET_TURRET_TO_LEFT_END*1000);
    	} catch (InterruptedException e){	
    	}
    }
    
    public void moveLeft(){
    	moveDegrees(MOVEMENT_INCREMENT_IN_DEGREES*(-1.0));
    }
       
    public void moveRight(){
    	moveDegrees(MOVEMENT_INCREMENT_IN_DEGREES);
    }
       
    public void moveDegrees(double degrees){
    	if (position + degrees >= TURRET_ROTATION_RANGE_IN_DEGREES){
    		position = TURRET_ROTATION_RANGE_IN_DEGREES;
    	} else if (position + degrees <= 0){
    		position = 0;
    	} else {
    		position = position + degrees;
    	}
    	SmartDashboard.putString(TURRET_POSITION_LABEL, "" + position);
    	getPIDController().setSetpoint(position);
    }
    
    

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new TurretCenter());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
        //return encoder.pidGet();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
    	return getActualPosition();
    	
    }
    
    private double getActualPosition(){
    	int encoderValue = encoder.get();
    	double numberOfRotations = (encoderValue*1.0d)/(ENCODER_PULSES_PER_ROTATION*1.0d);
    	double degrees = (360.0*numberOfRotations)/(NUMBER_OF_MOTOR_ROTATIONS_TO_ACHIEVE_ONE_360_TURRET_ROTATION);
    	return degrees;
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        speedController.pidWrite(output);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
    }
}
