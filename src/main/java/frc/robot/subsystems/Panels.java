package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.StaticJoystick;
import frc.robot.orbitmotors.MotorControlMode;
import frc.robot.orbitmotors.MotorProps;
import frc.robot.orbitmotors.OrbitMotor;
import frc.robot.orbitmotors.OrbitMotorFactory;

public class Panels implements SubSystem {
    private final DoubleSolenoid piston = new DoubleSolenoid(0, 0);
    private final DoubleSolenoid vacuumReleasePiston = new DoubleSolenoid(0, 0);
    private final OrbitMotor vacuum = OrbitMotorFactory.talonSRX(new MotorProps(0, false, false, 1));

    private boolean pistonForward = false;
    private  boolean lastR1 = false;
    @Override
    public void init() {
        piston.set(Value.kReverse);
    }

    @Override
    public void execute(final RobotState state) {
        boolean vaccumActive = false;
        boolean vaccumRelease = false;
        final float rJoystickYVal = (float) StaticJoystick.joystick.getRawAxis(4);
        final boolean r1Val = StaticJoystick.joystick.getRawButton(5);

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
                if (r1Val && !lastR1) {
                    pistonForward = !pistonForward;
                }
                if (rJoystickYVal > 0) {
                    vaccumActive = false;
                    vaccumRelease = false;
                }
                break;
        }

        piston.set(pistonForward ? Value.kForward : Value.kReverse);
        vacuum.setOutput(MotorControlMode.PERCENT_OUTPUT, vaccumActive ? 0 : 1);
        vacuumReleasePiston.set(vaccumRelease ? Value.kForward : Value.kReverse);
        lastR1 = r1Val;
    }
}
