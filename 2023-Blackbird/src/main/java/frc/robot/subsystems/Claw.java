package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
    
    public DoubleSolenoid miniClawSolenoid;

    public DoubleSolenoid bigClawSolenoid;

    public Claw(){

        miniClawSolenoid = new DoubleSolenoid(
            Constants.COMPRESSOR_ID,
            PneumaticsModuleType.REVPH,
            Constants.CLAW_OUT_CHANNEL,
            Constants.CLAW_IN_CHANNEL);

        bigClawSolenoid = new DoubleSolenoid(
            Constants.COMPRESSOR_ID,
            PneumaticsModuleType.REVPH,
            Constants.CLAW_EXTEND_SOLENOID_CHANNEL,
            Constants.CLAW_RETRACT_SOLENOID_CHANNEL);
    }
    
    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {}

    public void toggleClawHorizontal() {
        miniClawSolenoid.toggle();
    }

    public void toggleClawVertical() {
        bigClawSolenoid.toggle();
    }

}

