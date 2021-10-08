package frc.robot.subsystems;

import frc.robot.Enums.RobotState;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Lift {
    private final OrbitMotor liftMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));

    // will change
    private final double highPos = 0;
    private final double midPos = 0;
    private final double bottomPos = 0;

    public void init() {
        liftMotor.setOutput(MotorControlMode.POSITION, bottomPos);
    }

    public void execute(final RobotState state) {
        double wantedPos;
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
