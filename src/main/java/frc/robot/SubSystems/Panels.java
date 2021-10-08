package frc.robot.SubSystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Enums.RobotState;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Panels {
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0);
    private final DoubleSolenoid vacuumRelease = new DoubleSolenoid(0, 0);
    private final OrbitMotor vacuum = OrbitMotorFactory.talonSRX(new MotorProps(0, false, false, 1));


    public void init() {
        piston.set(Value.kReverse);
    }

    public void execute(final RobotState state, final boolean R1risingEdge, final double RJoystickYVal) {
        Value pistonState;
        Value vacuumReleaseState; //check default value of vacuumRelease when robot is done
        double vacuumPower;

        switch (state) {
            case INTAKE:
                pistonState = Value.kForward;
                vacuumPower = 1;
                vacuumReleaseState = Value.kForward;
                break;
            case MIDDLE:
            case TOP:
                if (R1risingEdge) {
                    if (pistonState == Value.kForward) {
                        pistonState = Value.kReverse;
                    } else if (pistonState == Value.kReverse) {
                        pistonState = Value.kForward;
                    }
                }
                if (RJoystickYVal > 0) {
                    vacuumPower = 0;
                    vacuumReleaseState = Value.kReverse;
                }
                break;
            case TRAVEL:
                default:
                pistonState = Value.kReverse;
                vacuumPower = 1;
                vacuumReleaseState = Value.kForward;
                break;
        }
        piston.set(pistonState);
        vacuum.setOutput(MotorControlMode.PERCENT_OUTPUT, vacuumPower);
        vacuumRelease.set(vacuumReleaseState);
    }
}
