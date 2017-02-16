package org.usfirst.frc.team2791.trajectory;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * PID + Feedforward controller for following a Trajectory.
 * contains calculate method, which uses feedfoward as the feeback loop
 *
 * @author Jared341
 * @author unbun
 */
public class TrajectoryFollower {

  private double kp_;
  private double ki_;  // Not currently used, but might be in the future.
  private double kd_;
  private double kv_;
  private double ka_;
  private double last_error_;
  private double current_heading = 0;
  private int current_segment;
  private Trajectory profile_;
  public String name;

  public TrajectoryFollower(String name) {
    this.name = name;
  }

  public void configure(double kp, double ki, double kd, double kv, double ka) {
    kp_ = kp;
    ki_ = ki;
    kd_ = kd;
    kv_ = kv;
    ka_ = ka;
  }

  public void reset() {
    last_error_ = 0.0;
    current_segment = 0;
  }

  public void setTrajectory(Trajectory profile) {
    profile_ = profile;
  }

  public double calculate(double distance_so_far) {
   
    if (current_segment < profile_.getNumSegments()) {
      Trajectory.Segment segment = profile_.getSegment(current_segment);
      double error = segment.pos - distance_so_far;
      double output = kp_ * error + kd_ * ((error - last_error_)
              / segment.dt - segment.vel) + (kv_ * segment.vel
              + ka_ * segment.acc);

      last_error_ = error;
      current_heading = segment.heading;
      current_segment++;
      debug(segment, distance_so_far, error, kp, ki, kd);
      return output;
      
      debug()
    } else {
      return 0;
    }
  }

  public double getHeading() {
    return current_heading;
  }

  public boolean isFinishedTrajectory() {
    return current_segment >= profile_.getNumSegments();
  }
  
  public int getCurrentSegment() {
    return current_segment;
  }
  
  public int getNumSegments() {
    return profile_.getNumSegments();
  }
  
  public void debug(){
      
      SmartDashboard.putNumber(name + "Pos Sensed", distance_so_far);
      SmartDashboard.putNumber(name + "Pos Goal", segment.pos);
      SmartDashboard.putNumber(name + "Pos Error", segment.pos-distance_so_far);
      
      
      SmartDashboard.putNumber(name + "Vel Sensed", Robot.drivetrain.getAverageVelocity());
      SmartDashboard.putNumber(name + "Vel Goal", segment.vel);
      SmartDashboard.putNumber(name + "Vel Error", Robot.drivetrain.getAverageVelocity()-segment.vel);
    
    
      SmartDashboard.putNumber(name + "Acc Sensed", Robot.drivetrain.getAverage());
      SmartDashboard.putNumber(name + "Acc Goal", segment.acc);
      SmartDashboard.putNumber(name + "Acc Error", Robot.drivetrain.getAverageAcceleration()-segment.acc);
  }
  }
}
