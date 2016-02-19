package strategy;

import config.DefaultPorts;
import main.Bot;
import sensors.BasicColor;

public class FollowLine implements Tactic{

	private BasicColor c;
	
	public FollowLine (BasicColor color){
		c = color;
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
		BasicColor courantColor = Bot.getSensorsCache().getColor();
		DefaultPorts.getRightMotor().forward();
		DefaultPorts.getLeftMotor().forward();
		if(courantColor != c){
			DefaultPorts.getRightMotor().setSpeed(180);
			DefaultPorts.getLeftMotor().setSpeed(0);	
		}
		else{
			DefaultPorts.getLeftMotor().setSpeed(180);	
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
