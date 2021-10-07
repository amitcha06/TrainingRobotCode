package frc.robot.orbitmotors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder;

final class OrbitSpark extends CANSparkMax implements OrbitMotor {

    private final CANPIDController canPIDController = getPIDController();
    private final CANEncoder encoder;
    private final float motorPerPhysicalUnitsRatio;

    private final int id;

    public OrbitSpark(final MotorProps props) {
        super(props.port, MotorType.kBrushless);

        this.id = props.port;

        encoder = this.getEncoder();
        invertMotor(props.invertMotor);
        invertSensor(props.invertSensor);
        motorPerPhysicalUnitsRatio = props.motorPerPhysicalUnitsRatio;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setOutput(final MotorControlMode controlMode, final double value) {
        switch (controlMode) {
            case PERCENT_OUTPUT:
            default:
                canPIDController.setReference(value, ControlType.kDutyCycle);
                return;
            case POSITION:
                canPIDController.setReference(value * motorPerPhysicalUnitsRatio, ControlType.kPosition);
                return;
            case VELOCITY:
                canPIDController.setReference(value * motorPerPhysicalUnitsRatio * 60, ControlType.kVelocity);
                return;
            case MOTION_MAGIC:
                canPIDController.setReference(value * motorPerPhysicalUnitsRatio, ControlType.kSmartMotion);
                return;
            case CURRENT:
                canPIDController.setReference(value, ControlType.kCurrent);
                return;
        }
    }

    @Override
    public void invertMotor(final boolean invert) {
        setInverted(invert);
    }

    @Override
    public void invertSensor(final boolean invert) {
        if (invert) {
            throw new UnsupportedOperationException("##1690 Error## inverting brushless sensor");
        }
    }

    @Override
    public void config(final int slot, final double kP, final double kI, final double kD, final double kF,
            final int iZone, final double nominal, final double rampRate, final double peak) {

        canPIDController.setP(kP, slot);
        canPIDController.setI(kI, slot);
        canPIDController.setD(kD, slot);
        canPIDController.setFF(kF, slot);
        canPIDController.setIZone(iZone, slot);

        canPIDController.setOutputRange(-peak, peak, slot);

        setClosedLoopRampRate(rampRate);
    }

    @Override
    public void setIZone(final double iZone) {
        canPIDController.setIZone(iZone, 0);
    }

    @Override
    public void resetIAccum() {
        canPIDController.setIAccum(0);
    }

    @Override
    public int getRawPosition() {
        return (int) (encoder.getPosition());
    }

    @Override
    public int getRawVelocity() {
        return (int) (encoder.getVelocity());
    }

    @Override
    public float getPosition() {
        return (float) encoder.getPosition() / motorPerPhysicalUnitsRatio;
    }

    @Override
    public float getVelocity() {
        return (float) encoder.getVelocity() / motorPerPhysicalUnitsRatio / 60f;
    }

    @Override
    public double getThrottle() {
        return get();
    }

    @Override
    public double getCurrent() {
        return getOutputCurrent();
    }

    @Override
    public int closedLoopError() {
        System.err.println("##1690 Error## close-looping spark max");
        throw new UnsupportedOperationException("##1690 Error## close-looping spark max");
    }

    @Override
    public double getMotionMagicTargetPosition() {
        throw new UnsupportedOperationException("##1690 Error## motion-magicking spark max");
    }

    @Override
    public double getMotionMagicTargetVelocity() {
        throw new UnsupportedOperationException("##1690 Error## motion-magicking spark max");
    }

    @Override
    public void enableCurrentLimit() {
        throw new UnsupportedOperationException("##1690 Error## using spark max");

    }

    @Override
    public void setPeriod(final int dt_MilliSec) {
        setPeriodicFramePeriod(PeriodicFrame.kStatus0, dt_MilliSec);
        setPeriodicFramePeriod(PeriodicFrame.kStatus1, dt_MilliSec + 10);
        setPeriodicFramePeriod(PeriodicFrame.kStatus2, dt_MilliSec + 20);
    }

    @Override
    public void setSlave(final int masterPort) {
        throw new UnsupportedOperationException("##1690 Error## doesn't make sense to follow spark max");

    }

    @Override
    public void setSensorPosition(final int position) {
        encoder.setPosition(position);
    }

    @Override
    public void enableSoftLimit(final boolean enable) {
        enableSoftLimit(SoftLimitDirection.kForward, enable);
        enableSoftLimit(SoftLimitDirection.kReverse, enable);
    }

    @Override
    public void motionMagicArbitraryF(final float value, final float arbitraryFGain) {
        throw new UnsupportedOperationException("##1690 Error## motion-magicking spark max");
    }

    @Override
    public void positionArbitraryF(final float setPoint, final float arbitraryFGain) {
        throw new UnsupportedOperationException("##1690 Error## F-ing spark max");
    }

    @Override
    public void velocityArbitraryF(final float value, final float arbitraryFGain) {
        throw new UnsupportedOperationException("##1690 Error## F-ing spark max");
    }

    @Override
    public double getVoltage() {
        return getBusVoltage();
    }

    @Override
    public boolean hasResetOccurred() {
        throw new UnsupportedOperationException("##1690 Error## checking spark max reset");
    }
}
