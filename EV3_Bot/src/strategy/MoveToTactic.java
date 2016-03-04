package strategy;

import gps.Orientation;
import gps.PositionTracker;

public class MoveToTactic implements Tactic {
	private boolean abort = false;
	PositionTracker gps;
	int targetx, targety;
	
	MoveToTactic(int targetx, int targety) {
		this.targetx = targetx;
		this.targety = targety;
	}

	@Override
	public String getDisplayName() {
		return "MoveTo";
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
		if (abort)
			return true;
		
		Orientation rot = gps.getOrientation();
		int xdiff = targetx - gps.getRawX(),
			ydiff = targety - gps.getRawY();
		
		
		return false;
	}

	@Override
	public void abort() {
		abort = true;
	}

	@Override
	public void stop() {
		abort = true;
	}

}
