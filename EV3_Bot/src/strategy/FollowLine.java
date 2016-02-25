package strategy;

import config.DefaultPorts;
import main.Bot;
import sensors.BasicColor;
import sensors.ColorDetector;

public class FollowLine implements Tactic{

	private BasicColor c;
	private BasicColor stopColor;
	
	public FollowLine (BasicColor color, BasicColor stop){
		c = color;
		stopColor = stop;
	}
	
	@Override
	public String getDisplayName() {
		return "FollowLine";
	}

	@Override
	public boolean handleObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleContact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform() {
		int speed = 360;
		BasicColor curColor = Bot.getSensorsCache().getColor();
		DefaultPorts.getRightMotor().forward();
		DefaultPorts.getLeftMotor().forward();
		if(curColor == stopColor){
			//stop yeti
			DefaultPorts.getLeftMotor().setSpeed(0);	
			DefaultPorts.getRightMotor().setSpeed(0);
			return true;
		}else if(curColor != c){
			//turn right 
			//yeti tries to find the current color by spinning round
			DefaultPorts.getRightMotor().setSpeed(speed);
			DefaultPorts.getLeftMotor().setSpeed(0);	
		}else{
			//turn left to find the current color
			DefaultPorts.getLeftMotor().setSpeed(speed);	
			DefaultPorts.getRightMotor().setSpeed(0);
			while (curColor == c) {
				//go forward
				int dist = ColorDetector.getLineDistance(curColor);
				int baseSpeed = 280, speedo = 80; 
				DefaultPorts.getLeftMotor().setSpeed(baseSpeed+speedo*dist/100);
				DefaultPorts.getRightMotor().setSpeed(baseSpeed+speedo*(100-dist)/100);
			}
		}
		return false;
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
