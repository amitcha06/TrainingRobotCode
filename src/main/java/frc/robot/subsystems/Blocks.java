package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.StaticJoystick;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Blocks implements SubSystem{
    // TODO: check ports and properties
    private final OrbitMotor tiltMotor = OrbitMotorFactory.talonSRX(new MotorProps(0, false, false, 1));
    private final OrbitMotor intakeWheels = OrbitMotorFactory.spark(new MotorProps(0, false, false, 1));
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0);

    private final float tiltMotorHigh = 0;
    private final float tiltMotorLow = 0;

    private boolean pistonForward = false;
    private boolean tiltMotorTop = false;
    private boolean lastR1  =  false;
    private boolean lastL1 = false;

    @Override
    public void init() {
        piston.set(Value.kForward);
    }

    @Override
    public void execute(final RobotState state) {
        float intakePower;
        final boolean r1Val = StaticJoystick.joystick.getRawButton(5);
        final boolean l1Val = StaticJoystick.joystick.getRawButton(4);
        final float rJoystickYVal = (float) StaticJoystick.joystick.getRawAxis(4);
        switch (state) {
            case INTAKE:
                pistonForward = false;
                intakePower = 1;
                tiltMotorTop = false;
                break;

            case MIDDLE:
                if (r1Val && !lastR1) {
                    pistonForward = !pistonForward;
                }
                intakePower = rJoystickYVal;
                tiltMotorTop = false;
                break;

            case TOP:
                if (r1Val && !lastR1) {
                    pistonForward = !pistonForward;
                }
                intakePower = rJoystickYVal;
                if (l1Val && !lastL1) {
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
        lastR1 = r1Val;
        lastL1 = l1Val;
    }
}
