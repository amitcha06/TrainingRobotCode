package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Blocks {
    // TODO: check ports and properties
    private final OrbitMotor tiltMotor = OrbitMotorFactory.talonSRX(new MotorProps(0, false, false, 1));
    private final OrbitMotor intakeWheels = OrbitMotorFactory.spark(new MotorProps(0, false, false, 1));
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0);

    private final double tiltMotorHigh = 0;
    private final double tiltMotorLow = 0;

    private boolean pistonForward = false;
    private boolean tiltMotorTop = false;

    public void init() {
        piston.set(Value.kForward);
    }

    public void execute(final RobotState state, final double RJoystickYVal, final boolean R1ValRisingEdge,
            final boolean L1RisingEdge) {
        double intakePower;

        switch (state) {
            case INTAKE:
                pistonForward = false;
                intakePower = 1;
                tiltMotorTop = false;
                break;

            case MIDDLE:
                if (R1ValRisingEdge) {
                    pistonForward = !pistonForward;
                }
                intakePower = RJoystickYVal;
                tiltMotorTop = false;
                break;

            case TOP:
                if (R1ValRisingEdge) {
                    pistonForward = !pistonForward;
                }
                intakePower = RJoystickYVal;
                if (L1RisingEdge) {
                    tiltMotorTop = !tiltMotorTop;
                }
                break;

            case TRAVEL:
            default:
                pistonForward = false;
                intakePower = 0;
                tiltMotorTop = false;
                break;
        }

        piston.set(pistonForward ? Value.kForward : Value.kReverse);
        intakeWheels.setOutput(MotorControlMode.PERCENT_OUTPUT, intakePower);
        tiltMotor.setOutput(MotorControlMode.POSITION, tiltMotorTop ? tiltMotorHigh : tiltMotorLow);
    }
}
