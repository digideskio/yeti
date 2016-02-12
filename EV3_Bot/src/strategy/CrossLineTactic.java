package strategy;

import gps.Orientation;
import gps.PositionTracker;
import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import sensors.BasicColor;
import sensors.SensorsCache;

/**
 * Runs up to a line of a given color and stops a given distance after it
 */
public class CrossLineTactic implements Tactic {
	private boolean stopped;
	int crossDistance;
	/// Distance at which we reached the line. -1 if we haven't reached it yet.
	int reachedDistance;
	BasicColor target;
	Orientation targetDirection;
	SensorsCache cache;
	PositionTracker gps;
	StraightMotion motion;
	
	/**
	 * Moves up to a line of the given color in the given direction 
	 * then keeps going for crossDistance units
	 */
	CrossLineTactic(BasicColor line, Orientation direction, int crossDistance) {
		stopped = false;
		this.crossDistance = crossDistance;
		this.reachedDistance = -1;
		target = line;
		targetDirection = direction;
		cache = Bot.getSensorsCache();
		gps = Bot.getGPS();
		motion = new StraightMotion();
	}
	
	@Override
	public String getDisplayName() {
		return "Seek line";
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
		if (stopped)
			return true;
		
		if (reachedDistance == -1 && cache.getColor() == target)
			reachedDistance = motion.getCurrentDistance();
		
		if (reachedDistance != -1
				&& motion.getCurrentDistance() - reachedDistance >= crossDistance) {
			motion.stop();
			stopped = true;
			return true;
		}
		
		if (!motion.isMoving()) {
			// Do a synchronous rotation to face the target line
			int directionError = (int)gps.getOrientation().diff(targetDirection); 
			if (Math.abs(directionError) >= 3)
				BasicMotion.rotate(directionError);
			
			motion.start(true);
		} else {
			motion.updateGPS();
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
