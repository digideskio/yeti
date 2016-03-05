package strategy;

import main.Bot;
import motor.BasicMotion;
import gps.PaletPosition;

public class PincerTactic implements Tactic{

	private boolean stopped,Pinceropened;
	
	PincerTactic() {
		stopped = false;
		Pinceropened=false;
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
	public boolean perform() {
		if(!Pinceropened){
			BasicMotion.openClaw();
			Pinceropened=true;
		}
		
		if(stopped)
			return true;

		if (Bot.getSensorsCache().isButtonPressed()) {
			PaletPosition.position();
			BasicMotion.closeClaw(false);
			return true;
		}
		return false;		
	}
	
	@Override
	public void abort() {
		stopped = true;
	}


	@Override
	public void stop() {
		stopped = true;
	}
	
}
