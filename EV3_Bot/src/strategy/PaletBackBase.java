package strategy;

import sensors.BasicColor;
import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;

public class PaletBackBase implements Tactic{
	
	private boolean stopped;
	private StraightMotion smotion;
	
	PaletBackBase(){
		stopped=false;
		smotion = new StraightMotion();
	}
	@Override
	public String getDisplayName() {
		return "PaletBackBase";
	}

	@Override
	public boolean handleObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleContact() {
		// TODO Auto-generated method stubWHITE
		return false;
	}
	
	@Override
	public boolean perform() {
		if (!smotion.isMoving())
			smotion.start(true);
		if(Bot.getSensorsCache().getColor()==BasicColor.White){
			smotion.stop();
			BasicMotion.openClaw(false);
			return true;
		}
		if(stopped){
			return true;
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
