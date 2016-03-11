package strategy;

import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import gps.Orientation;
import gps.PositionTracker;

public class MoveToTactic implements Tactic {
	private boolean abort = false;
	PositionTracker gps;
	StraightMotion sm;
	int targetx, targety;
	int startx, starty, distance;
	boolean rotated; // If we overshoot a little, we don't want to turn around
	
	MoveToTactic(int targetx, int targety) {
		gps = Bot.getGPS();
		rotated = false;
		
		this.targetx = targetx;
		this.targety = targety;
		startx = gps.getRawX();
		starty = gps.getRawY();
		int xdiff = targetx - startx, ydiff = targety - starty;
		distance = (int) Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		
		if (distance <= 500)
			sm = new StraightMotion(360);
		else
			sm = new StraightMotion();
	}

	@Override
	public String getDisplayName() {
		return "MoveTo"+targetx+";"+targety;
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
		if (abort) {
			if (sm.isMoving())
				sm.stop();
			return true;
		}
		
		if (sm.isMoving())
			sm.updateGPS();
		
		if (!rotated) {
			int xdiff = targetx - gps.getRawX(), ydiff = targety - gps.getRawY();
			float rotDiff = gps.getOrientation().diff(xdiff, ydiff);
			if (Math.abs(rotDiff) >= 2.) {
				if (sm.isMoving())
					sm.stop();
				BasicMotion.rotate((int)rotDiff, true);
			}
			rotated = true;
		}
		
		int xdiff = gps.getRawX() - startx, ydiff = gps.getRawY() - starty;
		int curDist = (int) Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		
		if (curDist >= distance) {
			if (sm.isMoving())
				sm.stop();
			return true;
		}
		
		if (!sm.isMoving())
			sm.start(true);

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
