// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.runs;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.StaticJoystick;
import frc.robot.subsystems.Blocks;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.SubSystemManager;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class BlocksRun extends TimedRobot {
  RobotState currentState;
  SubSystemManager subSystemManager;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    subSystemManager = new SubSystemManager(new Lift() , new Blocks());
    subSystemManager.init();
    currentState = RobotState.TRAVEL;
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if(StaticJoystick.joystick.getRawButton(0)) currentState = RobotState.MIDDLE;
    if(StaticJoystick.joystick.getRawButton(1)) currentState = RobotState.INTAKE;
    if(StaticJoystick.joystick.getRawButton(2)) currentState = RobotState.TRAVEL;
    if(StaticJoystick.joystick.getRawButton(3)) currentState = RobotState.TOP;
    subSystemManager.execute(currentState);
  }

}
