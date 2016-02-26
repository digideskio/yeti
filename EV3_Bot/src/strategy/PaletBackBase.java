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
		// TODO Auto-generated method stubWHITE
		return false;
	}
	
	@Override
	public boolean perform() {
		smotion.start(true);
		if(Bot.getSensorsCache().getColor()==BasicColor.White){
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
