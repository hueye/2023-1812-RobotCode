package frc.robot.subsystems.Drivetrain;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveModule extends SubsystemBase {
    
    CANSparkMax driveMotor;
    CANSparkMax turnMotor;
  
    SparkMaxPIDController drivePID;
    SparkMaxPIDController turnPID;
  
    RelativeEncoder driveEncoder;
    AbsoluteEncoder turnEncoder;
  
    double chassisAngularOffset;
  
    SwerveModuleState desiredState = new SwerveModuleState(0.0, new Rotation2d());

    public SwerveModule(int drivingCANID, int turningCANID, double chassisAngularOffset) {
        driveMotor = new CANSparkMax(drivingCANID, MotorType.kBrushless);
        turnMotor = new CANSparkMax(drivingCANID, MotorType.kBrushless);

        driveMotor.restoreFactoryDefaults();
        turnMotor.restoreFactoryDefaults();

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = turnMotor.getAbsoluteEncoder(Type.kDutyCycle);
        drivePID = driveMotor.getPIDController();
        drivePID.setFeedbackDevice(driveEncoder);
        turnPID = turnMotor.getPIDController();
        turnPID.setFeedbackDevice(turnEncoder);

        driveEncoder.setPositionConversionFactor(Constants.kDrivingEncoderPositionFactor);
        driveEncoder.setVelocityConversionFactor(Constants.kDrivingEncoderVelocityFactor);

        turnEncoder.setPositionConversionFactor(Constants.kTurningEncoderPositionFactor);
        turnEncoder.setVelocityConversionFactor(Constants.kTurningEncoderVelocityFactor);
        turnEncoder.setInverted(Constants.kTurningEncoderInverted);

        turnPID.setPositionPIDWrappingEnabled(true);
        turnPID.setPositionPIDWrappingMinInput(Constants.kTurningEncoderPositionPIDMinInput);
        turnPID.setPositionPIDWrappingMaxInput(Constants.kTurningEncoderPositionPIDMaxInput);

        drivePID.setP(Constants.kDrivingP);
        drivePID.setI(Constants.kDrivingI);
        drivePID.setD(Constants.kDrivingD);
        drivePID.setFF(Constants.kDrivingFF);
        drivePID.setOutputRange(Constants.kDrivingMinOutput,
            Constants.kDrivingMaxOutput);
   
        turnPID.setP(Constants.kTurningP);
        turnPID.setI(Constants.kTurningI);
        turnPID.setD(Constants.kTurningD);
        turnPID.setFF(Constants.kTurningFF);
        turnPID.setOutputRange(Constants.kTurningMinOutput,
            Constants.kTurningMaxOutput);

        driveMotor.setIdleMode(Constants.kDrivingMotorIdleMode);
        driveMotor.setIdleMode(Constants.kTurningMotorIdleMode);
        driveMotor.setSmartCurrentLimit(Constants.kDrivingMotorCurrentLimit);
        driveMotor.setSmartCurrentLimit(Constants.kTurningMotorCurrentLimit);
            
        driveMotor.burnFlash();
        turnMotor.burnFlash();
        
        desiredState.angle = new Rotation2d(turnEncoder.getPosition());
        driveEncoder.setPosition(0);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(driveEncoder.getVelocity(),
            new Rotation2d(turnEncoder.getPosition() - chassisAngularOffset));
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(driveEncoder.getPosition(),
            new Rotation2d(turnEncoder.getPosition() - chassisAngularOffset));
      }

      public void setDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState correctedDesiredState = new SwerveModuleState();
        correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
        correctedDesiredState.angle = desiredState.angle.plus(Rotation2d.fromRadians(chassisAngularOffset));
    
        SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(correctedDesiredState,
            new Rotation2d(turnEncoder.getPosition()));
    
        drivePID.setReference(optimizedDesiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);
        turnPID.setReference(optimizedDesiredState.angle.getRadians(), CANSparkMax.ControlType.kPosition);
    
      }
    
      /** Zeroes all the SwerveModule encoders. */
      public void resetEncoders() {
        driveEncoder.setPosition(0);
      }
}
