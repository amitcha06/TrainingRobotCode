package frc.robot.SubSystems;

import frc.robot.Enums.RobotState;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Lift {
    private final OrbitMotor liftMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));
    private double wantedPos;

    // will change
    private final double highPos = 0;
    private final double midPos = 0;
    private final double bottomPos = 0;

    public void excute(final RobotState state) {
        switch (state) {
            case INTAKE:
                wantedPos = bottomPos;
            case MIDDLE:
                wantedPos = midPos;
                break;
            case TOP:
                wantedPos = highPos;
                break;
            case TRAVEL:
                wantedPos = bottomPos;
                break;
            default:
                break;

        }
        // talon.set(ControlMode.MotionMagic, wantedPos);
        liftMotor.setOutput(MotorControlMode.POSITION, wantedPos);
    }
}
