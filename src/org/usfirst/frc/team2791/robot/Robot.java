
package org.usfirst.frc.team2791.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team2791.robot.Robot.GamePeriod;
import org.usfirst.frc.team2791.robot.commands.FollowPath;
//import org.usfirst.frc.team2791.robot.commands.ExampleCommand;
//import org.usfirst.frc.team2791.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team2791.robot.subsystems.ShakerDrivetrain;
import org.usfirst.frc.team2791.robot.util.ShakerGyro;
import org.usfirst.frc.team2791.trajectory.AutoPaths;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static ShakerDrivetrain drivetrain;
	public static OI oi;
	public Thread driveTrainThread;
	public static GamePeriod gamePeriod;

	Command autonomousCommand;
	//SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("Starting to init my systems.");
		gamePeriod = GamePeriod.DISABLED;
		oi = new OI();
		drivetrain = new ShakerDrivetrain();
		
		//driveTrainThread = new Thread(drivetrain);
        //driveTrainThread.start();
		//chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putData(drivetrain);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		gamePeriod = GamePeriod.DISABLED;
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)	\
		autonomousCommand= new FollowPath(AutoPaths.get("StraightAheadPath"));
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		//if (autonomousCommand != null)
			//autonomousCommand.cancel();
		
		
		//(comment) This was added from 2017_Traj 
		// TO UNNAS: I got this to create a "pursuer", so we're off to a good start
		
		///end comment
		
		gamePeriod = GamePeriod.TELEOP;
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		oi.checkForAction();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	public enum GamePeriod {
        AUTONOMOUS, TELEOP, DISABLED
    }
}
