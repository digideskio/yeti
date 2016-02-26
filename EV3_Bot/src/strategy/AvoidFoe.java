package strategy;

import config.DefaultPorts;
import main.Bot;
import motor.BasicMotion;

public class AvoidFoe implements Tactic{
	
	private boolean foe;
	public AvoidFoe() {
		foe = true;
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

	/**
	 * If Yeti see a foe, he had to avoid it. 
	 * Perform -> Call only when handleObstacle is true in another strategy
	 * Yeti goes to right (90°) after goes ahead (10 cm) and goes to left
	 */
	@Override
	public boolean perform() {
		BasicMotion.rotate(90); // +90 so it is going to right
		BasicMotion.moveBy(180*4);
		BasicMotion.rotate(-90); 
		BasicMotion.moveBy(180*6);
		BasicMotion.rotate(90); // +90 so it is going to right
		BasicMotion.moveBy(180*4);
		BasicMotion.rotate(-90); 
		if(Bot.getSensorsCache().getSonarDistance() == 0)
			foe = false;
		return true;
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
