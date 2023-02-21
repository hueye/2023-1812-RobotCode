package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class ArmACTIVE extends CommandBase {
    
    public ArmACTIVE() {
        addRequirements(RobotContainer.armSystem);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        Arm.Extended();
    }

    @Override
    public void end(boolean interrupted) {
        Arm.Retracted();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
