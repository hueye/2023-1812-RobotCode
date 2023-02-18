package frc.robot.commands.Drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriveSWERVE extends CommandBase {
    XboxController driveCtrl;
    public boolean fieldOriented = false;

    public DriveSWERVE(XboxController driveCtrl, boolean fieldOriented) {
        addRequirements(RobotContainer.drivetrain);

        this.driveCtrl = driveCtrl;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        RobotContainer.drivetrain.drive(
            MathUtil.applyDeadband(driveCtrl.getLeftX(), 0.05),
            MathUtil.applyDeadband(driveCtrl.getLeftY(), 0.05),
            MathUtil.applyDeadband(driveCtrl.getRightX(), 0.05), fieldOriented);}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
