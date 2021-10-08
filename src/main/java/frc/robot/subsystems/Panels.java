package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Panels {
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0);
    private final DoubleSolenoid vacuumReleasePiston = new DoubleSolenoid(0, 0);
    private final OrbitMotor vacuum = OrbitMotorFactory.talonSRX(new MotorProps(0, false, false, 1));

    private boolean pistonForward = false;

    public void init() {
        piston.set(Value.kReverse);
    }

    public void execute(final RobotState state, final boolean R1risingEdge, final double RJoystickYVal) {
        boolean vaccumActive = false;
        boolean vaccumRelease = false;

        switch (state) {
            case INTAKE:
                vaccumActive = true;
                vaccumRelease = true;
                pistonForward = true;
                break;
            case TRAVEL:
            default:
                vaccumActive = true;
                vaccumRelease = true;
                pistonForward = false;
                break;

            case MIDDLE:
            case TOP:
                if (R1risingEdge) {
                    pistonForward = !pistonForward;
                }
                if (RJoystickYVal > 0) {
                    vaccumActive = false;
                    vaccumRelease = false;
                }
                break;
        }

        piston.set(pistonForward ? Value.kForward : Value.kReverse);
        vacuum.setOutput(MotorControlMode.PERCENT_OUTPUT, vaccumActive ? 0 : 1);
        vacuumReleasePiston.set(vaccumRelease ? Value.kForward : Value.kReverse);
    }
}
