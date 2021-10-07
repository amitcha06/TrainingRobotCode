package frc.robot.orbitmotors;

public interface OrbitMotor {

    void setOutput(final MotorControlMode controlMode, final double value);

    void invertMotor(final boolean invert);

    void invertSensor(final boolean invert);

    void config(final int slot, final double kP, final double kI, final double kD, final double kF, final int iZone,
            final double nominal, final double rampRate, final double peak);

    void setIZone(final double iZone);

    void resetIAccum();

    int getRawPosition();

    int getRawVelocity();

    float getPosition();

    float getVelocity();

    double getThrottle();

    double getCurrent();

    int closedLoopError();

    double getMotionMagicTargetPosition();

    double getMotionMagicTargetVelocity();

    double getVoltage();

    void enableCurrentLimit();

    void setPeriod(final int dt_MilliSec);

    void setSlave(final int masterPort);

    void setSensorPosition(final int position);

    void enableSoftLimit(final boolean enable);

    void motionMagicArbitraryF(final float setPoint, final float arbitraryFGain);

    void positionArbitraryF(final float setPoint, final float arbitraryFGain);

    void velocityArbitraryF(final float setPoint, final float arbitraryFGain);

    boolean hasResetOccurred();

    int getID();
}
