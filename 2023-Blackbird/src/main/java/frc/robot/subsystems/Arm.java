package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    
    public DoubleSolenoid leftArmSolenoid;

    public DoubleSolenoid rightArmSolenoid;

    public Arm() {
        leftArmSolenoid = new DoubleSolenoid(
            Constants.COMPRESSOR_ID,
            PneumaticsModuleType.REVPH,
            Constants.LEFT_ARM_EXTEND_CHANNEL,
            Constants.LEFT_ARM_RETRACT_CHANNEL
        );

        rightArmSolenoid = new DoubleSolenoid(
            Constants.COMPRESSOR_ID,
            PneumaticsModuleType.REVPH,
            Constants.RIGHT_ARM_EXTEND_CHANNEL,
            Constants.RIGHT_ARM_RETRACT_CHANNEL
        );
    }

    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {}

    public void toggleArm() {
        leftArmSolenoid.toggle();
        rightArmSolenoid.toggle();
    }
}
