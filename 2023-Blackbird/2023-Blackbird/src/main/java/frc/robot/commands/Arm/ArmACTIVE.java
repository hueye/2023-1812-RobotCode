package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ArmACTIVE extends CommandBase {
    
    public ArmACTIVE() {
        addRequirements(RobotContainer.armSystem);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        RobotContainer.armSystem.toggleArm();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
