package frc.robot.subsystems;

import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Lift implements SubSystem{
    private final OrbitMotor liftMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));

    // will change
    private final float highPos = 0;
    private final float midPos = 0;
    private final float bottomPos = 0;

    @Override
    public void init() {
        liftMotor.setOutput(MotorControlMode.POSITION, bottomPos);
    }
    
    @Override
    public void execute(final RobotState state) {
        float wantedPos;
        switch (state) {
            case INTAKE:
            case TRAVEL:
            default:
                wantedPos = bottomPos;
                break;
            case MIDDLE:
                wantedPos = midPos;
                break;
            case TOP:
                wantedPos = highPos;
                break;
        }
        liftMotor.setOutput(MotorControlMode.POSITION, wantedPos);
    }
}
