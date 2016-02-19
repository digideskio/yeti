package strategy;

import config.DefaultPorts;
import main.Bot;
import sensors.BasicColor;

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
		BasicColor courantColor = Bot.getSensorsCache().getColor();
		DefaultPorts.getRightMotor().forward();
		DefaultPorts.getLeftMotor().forward();
		if(courantColor == stopColor){
			DefaultPorts.getLeftMotor().setSpeed(0);	
			DefaultPorts.getRightMotor().setSpeed(0);
			return true;
		}else if(courantColor != c){
			DefaultPorts.getRightMotor().setSpeed(speed);
			DefaultPorts.getLeftMotor().setSpeed(0);	
		}else{
			DefaultPorts.getLeftMotor().setSpeed(speed);	
			DefaultPorts.getRightMotor().setSpeed(0);
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
