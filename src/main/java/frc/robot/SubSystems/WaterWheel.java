package frc.robot.SubSystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Enums.RobotState;

public class WaterWheel {
    private final TalonFX topMotor = new TalonFX(0/* will change */);
    private final TalonFX bottomMotor = new TalonFX(0/* will change */);
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0/* will change */);
    public Value pistonState;
    private double topPower = 0;
    private double bottomPower = 0;

    public void init() {
        piston.set(Value.kForward);
    }

    public void excute(final RobotState state, final double RJoystickYVal, final boolean R1val) {
        switch (state) {
            case INTAKE:
                pistonState = Value.kReverse;
                topPower = 1;
                bottomPower = 1;
                break;
            case MIDDLE:
            case TOP:
                pistonState = Value.kForward;
                // Will change
                if (R1val) {
                    topPower = RJoystickYVal;
                    bottomPower = RJoystickYVal;
                } else {
                    topPower = RJoystickYVal;
                    bottomPower = RJoystickYVal;
                }
                break;
            case TRAVEL:
                pistonState = Value.kForward;
                topPower = 0;
                bottomPower = 0;
                break;
            default:
                break;

        }
        piston.set(pistonState);
        topMotor.set(ControlMode.PercentOutput, topPower);
        bottomMotor.set(ControlMode.PercentOutput, bottomPower);
    }
}
