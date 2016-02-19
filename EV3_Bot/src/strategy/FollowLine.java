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
		// TODO Auto-generated method stub
		return null;
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
		if(courantColor != c)
			DefaultPorts.getRightMotor().setSpeed(180);
		else
			DefaultPorts.getLeftMotor().setSpeed(180);			
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
