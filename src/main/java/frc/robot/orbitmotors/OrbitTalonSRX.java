package frc.robot.orbitutil.orbitmotors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

final class OrbitTalonSRX extends WPI_TalonSRX implements OrbitMotor {

    private final float motorPerPhysicalUnitsRatio;
    private final int id;

    public OrbitTalonSRX(final MotorProps props) {
        super(props.port);

        this.id = props.port;

        invertMotor(props.invertMotor);
        invertSensor(props.invertSensor);
        motorPerPhysicalUnitsRatio = props.motorPerPhysicalUnitsRatio;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void setOutput(final MotorControlMode controlMode, final double value) {
        switch (controlMode) {
        case PERCENT_OUTPUT:
        default:
            set(ControlMode.PercentOutput, value);
            return;
        case POSITION:
            set(ControlMode.Position, value * motorPerPhysicalUnitsRatio);
            return;
        case VELOCITY:
            set(ControlMode.Velocity, value * motorPerPhysicalUnitsRatio / 10);
            return;
        case MOTION_MAGIC:
            set(ControlMode.MotionMagic, value * motorPerPhysicalUnitsRatio);
            return;
        case CURRENT:
            set(ControlMode.Current, value);
            return;
        }
    }

    @Override
    public void invertMotor(final boolean invert) {
        setInverted(invert);
    }

    @Override
    public void invertSensor(final boolean invert) {
        setSensorPhase(invert);
    }

    @Override
    public void config(final int slot, final double kP, final double kI, final double kD, final double kF,
            final int iZone, final double nominal, final double rampRate, final double peak) {

        config_kP(slot, kP);
        config_kI(slot, kI);
        config_kD(slot, kD);
        config_kF(slot, kF);
        config_IntegralZone(slot, iZone);

        configNominalOutputForward(nominal);
        configNominalOutputReverse(-nominal);

        configClosedloopRamp(rampRate);

        configPeakOutputForward(peak);
        configPeakOutputReverse(-peak);
    }

    @Override
    public void setIZone(final double iZone) {
        config_IntegralZone(0, iZone);
    }

    @Override
    public void resetIAccum() {
        setIntegralAccumulator(0);
    }

    @Override
    public int getRawPosition() {
        return (int) getSelectedSensorPosition();
    }

    @Override
    public int getRawVelocity() {
        return (int) getSelectedSensorVelocity();
    }

    @Override
    public float getPosition() {
        return ((float) getRawPosition()) / motorPerPhysicalUnitsRatio;
    }

    @Override
    public float getVelocity() {
        return ((float) getRawVelocity()) / motorPerPhysicalUnitsRatio * 10;
    }

    @Override
    public double getThrottle() {
        return get();
    }

    @Override
    public double getCurrent() {
        return getSupplyCurrent();
    }

    @Override
    public int closedLoopError() {
        return (int) super.getClosedLoopError();
    }

    @Override
    public double getMotionMagicTargetPosition() {
        return super.getActiveTrajectoryPosition() / motorPerPhysicalUnitsRatio;
    }

    @Override
    public double getMotionMagicTargetVelocity() {
        return super.getActiveTrajectoryVelocity() / motorPerPhysicalUnitsRatio * 10;
    }

    @Override
    public void enableCurrentLimit() {
        super.enableCurrentLimit(true);
    }

    @Override
    public void setPeriod(final int dt_MilliSec) {
        // setControlFramePeriod(5, 0);
        setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 1000 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
        setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1100 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, 1200 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 310 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 1300 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 1400 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 200 + dt_MilliSec);
        setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 1500 + dt_MilliSec);
        enableVoltageCompensation(true);
    }

    @Override
    public void setSlave(final int masterPort) {
        set(TalonSRXControlMode.Follower, masterPort);
    }

    @Override
    public void setSensorPosition(final int position) {
        setSelectedSensorPosition(position);
    }

    @Override
    public void enableSoftLimit(final boolean enable) {
        overrideSoftLimitsEnable(enable);
    }

    @Override
    public void motionMagicArbitraryF(final float setPoint, final float arbitraryFGain) {
        set(ControlMode.MotionMagic, setPoint * motorPerPhysicalUnitsRatio, DemandType.ArbitraryFeedForward,
                arbitraryFGain);
    }

    @Override
    public void positionArbitraryF(final float setPoint, final float arbitraryFGain) {
        set(ControlMode.Position, setPoint * motorPerPhysicalUnitsRatio, DemandType.ArbitraryFeedForward,
                arbitraryFGain);
    }

    @Override
    public void velocityArbitraryF(final float setPoint, final float arbitraryFGain) {
        set(ControlMode.Velocity, setPoint * motorPerPhysicalUnitsRatio / 10, DemandType.ArbitraryFeedForward,
                arbitraryFGain);
    }

    @Override
    public double getVoltage() {
        return getBusVoltage();
    }

    @Override
    public boolean hasResetOccurred() {
        return super.hasResetOccurred();
    }
}
