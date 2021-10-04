package frc.robot.SubSystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Enums.RobotState;

public class Lift {
    private final TalonFX talon = new TalonFX(0/* will change */);
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
        talon.set(ControlMode.MotionMagic, wantedPos);
    }
}
