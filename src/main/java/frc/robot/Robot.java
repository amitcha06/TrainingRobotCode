package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Blocks;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Panels;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.SubSystemManager;
import frc.robot.subsystems.WaterWheel;

public class Robot extends TimedRobot{
    int num = 0;
    boolean lastL1Val = false;
    boolean lastR1Val = false;
    RobotState currentState = RobotState.TRAVEL;
    SubSystemManager subSystemManager;
    void printState(int num){
        switch(num){
            case 0:
                System.out.println("Blocks State");
                break;
            case 1:
                System.out.println("WaterWheel State");
                break;
            case 2:
                System.out.println("Panels State");        
                break;
        }
    }

    @Override
    public void robotPeriodic() {
        final boolean l1Val = StaticJoystick.joystick.getRawButton(4);
        final boolean r1Val = StaticJoystick.joystick.getRawButton(5);
        if(l1Val && !lastL1Val){
            num--;
            printState(num);
        }
        if(r1Val && !lastR1Val){
            num++;
            printState(num);
        }
        if(num > 2) num = 0;
        if(num < 0) num = 2;
        lastL1Val = l1Val;
        lastR1Val = r1Val;
    }

    @Override
    public void teleopInit() {
        switch(num){
            case 0:
                subSystemManager = new SubSystemManager(new Lift(),new Blocks());
                break;
            case 1:
                subSystemManager = new SubSystemManager(new Lift(), new WaterWheel());
                break;
            case 2:
                subSystemManager = new SubSystemManager(new Lift(),new Panels());
                break;    
        }
    }
    @Override
    public void teleopPeriodic() {
        if(StaticJoystick.joystick.getRawButton(0)) currentState = RobotState.MIDDLE;
        if(StaticJoystick.joystick.getRawButton(1)) currentState = RobotState.INTAKE;
        if(StaticJoystick.joystick.getRawButton(2)) currentState = RobotState.TRAVEL;
        if(StaticJoystick.joystick.getRawButton(3)) currentState = RobotState.TOP;
        subSystemManager.execute(currentState);
    }
}
