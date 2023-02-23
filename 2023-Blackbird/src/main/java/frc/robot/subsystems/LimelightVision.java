package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightVision extends SubsystemBase {

    //Unique variables:
    //tv = whether the limelight has valid targets
    //tx = horizontal offset from crosshair to target
    //ty = vertical offset from crosshair to target
    //ta = target area

    public static NetworkTable limeTable;
    public double tv, tx, ty, ta;
    public static double limeAngle; //add value
    public static double limeHeight; //add value
    public static double limeDesiredHeight; //add value

    //LEDMode values for the limelight:
    //1 = force off
    //2 = force blink
    //3 = force on

public LimelightVision() {
    limeTable.getEntry("ledMode").setNumber(3);
}

public void disableLime() {
    limeTable.getEntry("ledMode").setNumber(1);
}

public double distFromTarget() {
    double verticalDist = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
     if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1) {     
            double targetAngle = verticalDist + limeAngle;
            double targetAngleRadians = targetAngle * (Math.PI / 180.0);
            double targetDist = (limeDesiredHeight - limeHeight)/Math.tan(targetAngleRadians);
            return targetDist;
        }
        else {
            return 0;
        }
}

public static double AngleFromTarget() {
    double horizontalDist = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    return horizontalDist;
}

public static double AngleFromPiece() {
    double horizontalDist = NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("TargetX").getDouble(0.0);
        return horizontalDist;
}

}
