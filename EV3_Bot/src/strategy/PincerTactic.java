package strategy;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import main.Bot;
import sensors.TouchSensor;

@SuppressWarnings("deprecation")
public class PincerTactic implements Tactic{

	private boolean stopped;
	public EV3LargeRegulatedMotor pincer;
	public DifferentialPilot pilot;
	
	PincerTactic() {
		stopped = false;
		pincer = new EV3LargeRegulatedMotor(MotorPort.B);
		pilot =  new DifferentialPilot(2.1f, 4.4f, Motor.A, Motor.C, true);  // parameters in inches
		
	}
	
	@Override
	public String getDisplayName() {
		return "Nothing";
	}
	
	@Override
	public boolean handleObstacle() {
		return true;
	}

	@Override
	public boolean handleContact() {
		return true;
	}

	@Override
	public boolean perform1() {
		openPincer();
		if(stopped)
			return false;

		if (Bot.getSensorsCache().isButtonPressed()) {
			closePincer();
			turn(180);
			movingForward(30);
		}
		return true;		
	}
	
	public void openPincer(){
		pincer.rotate(1200);
	}
	
	public void closePincer(){
		pincer.rotate(1200);
	}
	
	public void turn(double angle){
		pilot.rotate(angle);
	}
	
	public void movingForward(double distance){
		pilot.travel(distance);
	}

	public void turnLeft(double angle){
		pilot.rotateLeft();
	}
	
	public void turnRight(double angle){
		pilot.rotateRight();
	}
	
	@Override
	public void abort() {
		stopped = true;
	}

	@Override
	public void stop() {
		stopped = true;
	}

	@Override
	public boolean perform() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
