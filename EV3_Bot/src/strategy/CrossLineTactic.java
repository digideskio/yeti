package strategy;



import gps.PositionTracker;
import main.Bot;
import sensors.BasicColor;
import sensors.SensorsCache;

/**
 * when Yeti crosses a line, he updates his coordinates
 */
public class CrossLineTactic implements Tactic {
	private boolean stopped;
	BasicColor currentColor;
	SensorsCache cache;
	PositionTracker gps;
	AvoidFoe avoidfoe;
	GoBack goback;
	
	/**
	 * Moves up to a line of the given color in the given direction 
	 * then keeps going for crossDistance units
	 */
	CrossLineTactic() {
		stopped = false;
		cache = Bot.getSensorsCache();
		gps = Bot.getGPS();		
	}
	
	@Override
	public String getDisplayName() {
		return "Seek line";
	}
	
	@Override
	public boolean handleObstacle() {
		avoidfoe = new AvoidFoe();
		return true;
	}

	@Override
	public boolean handleContact() {
		goback = new GoBack();
		return true;
	}

	@Override
	public boolean perform() {
		if (stopped)
			return true;
		
		if (currentColor != BasicColor.Gray) {
			gps.crossLine(currentColor);
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
