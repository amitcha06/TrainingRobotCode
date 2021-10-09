package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.StaticJoystick;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class WaterWheel implements SubSystem {
    private final OrbitMotor topMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));
    private final OrbitMotor bottomMotor = OrbitMotorFactory.falcon(new MotorProps(1, false, false, 1));
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0/* will change */);
    @Override
    public void init() {
        piston.set(Value.kForward);
    }
    @Override
    public void execute(final RobotState state) {
        Value pistonState;
        float topPower;
        float bottomPower;
        final float rJoystickYVal = (float) StaticJoystick.joystick.getRawAxis(4);
        final boolean r1Val = StaticJoystick.joystick.getRawButton(5);
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
                if (r1Val) {
                    topPower = rJoystickYVal;
                    bottomPower = rJoystickYVal;
                } else {
                    topPower = rJoystickYVal;
                    bottomPower = rJoystickYVal;
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
