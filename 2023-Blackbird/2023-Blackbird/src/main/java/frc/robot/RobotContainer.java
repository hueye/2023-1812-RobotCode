// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Claw.ClawHORIZONTAL;
import frc.robot.commands.Claw.ClawVERTICAL;
import frc.robot.commands.Compressor.CompressACTIVE;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Compress;
import frc.robot.subsystems.Drivetrain.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    public static Claw clawSystem = new Claw();

    public static Arm armSystem = new Arm();

    public static Drivetrain drivetrain = new Drivetrain();

    public static Compress compressorSystem = new Compress();

    public static XboxController operatorController;

    public static XboxController driverController;

    boolean fieldOriented = false;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    operatorController = new XboxController(Constants.CTRL_FOR_DRIVER_ID);
    
    driverController = new XboxController(Constants.CTRL_FOR_OPERATOR_ID);

    //default commands
    compressorSystem.setDefaultCommand(new CompressACTIVE());
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  public void configureBindings() {
      new JoystickButton(operatorController, XboxController.Button.kA.value).whileTrue(new ClawHORIZONTAL());
      
      new JoystickButton(operatorController, XboxController.Button.kB.value).whileTrue(new ClawVERTICAL());
  

    new JoystickButton(driverController, XboxController.Button.kRightBumper.value)
        .whileTrue(new RunCommand(() -> drivetrain.setX(),
            drivetrain));

    new JoystickButton(driverController, XboxController.Button.kLeftBumper.value)
        .onTrue(new InstantCommand(() -> fieldOriented = !fieldOriented));

    new JoystickButton(driverController, XboxController.Button.kStart.value)
        .onTrue(new InstantCommand(() -> drivetrain.zeroHeading(),
            drivetrain));

  }
}