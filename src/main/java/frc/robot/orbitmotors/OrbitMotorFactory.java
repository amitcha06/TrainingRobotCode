package frc.robot.orbitutil.orbitmotors;

public final class OrbitMotorFactory {

    public static OrbitMotor spark(final MotorProps props) {
        return new OrbitSpark(props);
    }

    public static OrbitMotor talonSRX(final MotorProps props) {
        return new OrbitTalonSRX(props);
    }

    public static OrbitMotor falcon(final MotorProps props) {
        return new OrbitFalcon(props);
    }
}
