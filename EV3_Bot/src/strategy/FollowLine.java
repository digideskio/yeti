package strategy;

import config.DefaultPorts;
import main.Bot;
import sensors.BasicColor;
import sensors.ColorDetector;

public class FollowLine implements Tactic{
	private AvoidFoe avoidfoe;
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
		avoidfoe = new AvoidFoe();
		return true;
	}

	@Override
	public boolean handleContact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform() {
		if(avoidfoe != null) {
			boolean result = avoidfoe.perform();
			if (result)
				avoidfoe = null;
		}
		BasicColor curColor = Bot.getSensorsCache().getColor();
		DefaultPorts.getRightMotor().forward();
		DefaultPorts.getLeftMotor().forward();
		if(curColor == stopColor){
			//stop yeti
			DefaultPorts.getLeftMotor().setSpeed(0);	
			DefaultPorts.getRightMotor().setSpeed(0);
			return true;	
		}else{
			//turn left to find the searched color
			int dist = Bot.getSensorsCache().getLineDistance(c);
			int baseSpeed = 280, speedo = 180; 
			DefaultPorts.getLeftMotor().setSpeed(baseSpeed+speedo*dist/100);
			DefaultPorts.getRightMotor().setSpeed(baseSpeed+speedo*(100-dist)/100);
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
