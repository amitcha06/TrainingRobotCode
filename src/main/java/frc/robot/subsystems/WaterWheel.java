package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class WaterWheel {
    private final OrbitMotor topMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));
    private final OrbitMotor bottomMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0/* will change */);

    public void init() {
        piston.set(Value.kForward);
    }

    public void execute(final RobotState state, final float RJoystickYVal, final boolean R1val) {
        Value pistonState;
        float topPower;
        float bottomPower;

        switch (state) {
            case INTAKE:
                pistonState = Value.kReverse;
                topPower = 1;
                bottomPower = 1;
                break;

            case MIDDLE:
            case TOP:
                pistonState = Value.kForward;

                // TODO: change motor direction
                if (R1val) {
                    topPower = RJoystickYVal;
                    bottomPower = RJoystickYVal;
                } else {
                    topPower = RJoystickYVal;
                    bottomPower = RJoystickYVal;
                }
                break;

            case TRAVEL:
            default:
                pistonState = Value.kForward;
                topPower = 0;
                bottomPower = 0;
                break;
        }

        piston.set(pistonState);
        topMotor.setOutput(MotorControlMode.PERCENT_OUTPUT, topPower);
        bottomMotor.setOutput(MotorControlMode.PERCENT_OUTPUT, bottomPower);
    }
}
