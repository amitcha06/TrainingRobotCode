package frc.robot.SubSystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Enums.RobotState;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Blocks {
    private final OrbitMotor tiltMotor = OrbitMotorFactory.talonSRX(new MotorProps(0, false, false, 1));
    private final OrbitMotor intakeWheels = OrbitMotorFactory.spark(new MotorProps(0, false, false, 1));
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0/* will change */);
    public Value pistonState;
    private double intakePower;
    private double tiltMotorPos;
    private final double tiltMotorHigh = 0;
    private final double tiltMotorLow = 0;

    public void init() {
        piston.set(Value.kForward);
    }

    public void excute(final RobotState state, final double RJoystickYVal, final boolean R1ValRisingEdge,
            final boolean L1RisingEdge) {
        switch (state) {
            case INTAKE:
                pistonState = Value.kReverse;
                intakePower = 1;
                tiltMotorPos = tiltMotorLow;
                break;
            case MIDDLE:
                if (R1ValRisingEdge) {
                    if (pistonState == Value.kForward) {
                        pistonState = Value.kReverse;
                    } else if (pistonState == Value.kReverse) {
                        pistonState = Value.kForward;
                    }
                }
                intakePower = RJoystickYVal;
                tiltMotorPos = tiltMotorLow;
            case TOP:
                if (R1ValRisingEdge) {
                    if (pistonState == Value.kForward) {
                        pistonState = Value.kReverse;
                    } else if (pistonState == Value.kReverse) {
                        pistonState = Value.kForward;
                    }
                }
                intakePower = RJoystickYVal;
                if (L1RisingEdge) {
                    if (tiltMotorPos == tiltMotorHigh) {
                        tiltMotorPos = tiltMotorLow;
                    } else {
                        tiltMotorPos = tiltMotorHigh;
                    }
                }
                break;
            case TRAVEL:
                pistonState = Value.kForward;
                intakePower = 0;
                tiltMotorPos = 0;
                break;
            default:
                break;
        }
        piston.set(pistonState);
        intakeWheels.setOutput(MotorControlMode.PERCENT_OUTPUT, intakePower);
        tiltMotor.setOutput(MotorControlMode.POSITION, tiltMotorPos);
    }
}
