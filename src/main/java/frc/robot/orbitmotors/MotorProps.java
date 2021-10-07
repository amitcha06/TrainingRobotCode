package frc.robot.orbitmotors;

public final class MotorProps {

    final int port;
    final boolean invertMotor;
    final boolean invertSensor;
    final float motorPerPhysicalUnitsRatio;

    public MotorProps(final int port, final boolean invertMotor, final boolean invertSensor,
            final float motorPerPhysicalUnitsRatio) {
        this.port = port;
        this.invertMotor = invertMotor;
        this.invertSensor = invertSensor;
        this.motorPerPhysicalUnitsRatio = motorPerPhysicalUnitsRatio;
    }
}
