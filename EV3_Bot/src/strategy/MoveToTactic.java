package strategy;

import gps.PositionTracker;
import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;

public class MoveToTactic implements Tactic {
	private AvoidFoe avoidfoe;
	private GoBack goback;
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
		distance = (int) Math.sqrt(xdiff * xdiff + ydiff * ydiff);

		sm = new StraightMotion();
	}

	@Override
	public String getDisplayName() {
		return "MoveTo" + targetx + ";" + targety;
	}

	@Override
	public boolean handleObstacle() {
		sm.stop();
		avoidfoe = new AvoidFoe();
		return true;
	}

	@Override
	public boolean handleContact() {
		sm.stop();
		goback = new GoBack();
		return true;
	}

	@Override
	public boolean perform() {
		if (avoidfoe != null) {
			boolean result = avoidfoe.perform();
			if (result)
				avoidfoe = null;
		}
		if (goback != null) {
			boolean res = goback.perform();
			if (res)
				goback = null;
		}

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
			if (Math.abs(rotDiff) >= 1.) {
				if (sm.isMoving())
					sm.stop();
				BasicMotion.rotate((int) rotDiff, true);
			}
			rotated = true;
		}

		int xdiff = gps.getRawX() - startx, ydiff = gps.getRawY() - starty;
		int curDist = (int) Math.sqrt(xdiff * xdiff + ydiff * ydiff);

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
}
