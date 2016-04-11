package strategy;


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
	AvoidFoe avoidfoe;
	GoBack goback;
	
	/**
	 * Moves up to a line of the given color in the given direction 
	 * then keeps going for crossDistance units
	 */
	CrossLineTactic() {
		stopped = false;
		cache = Bot.getSensorsCache();
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
		
		currentColor = cache.getColor();
		
		if (stopped)
			return true;
		
		if (currentColor != BasicColor.Gray) {
			Bot.getGPS().crossLine(currentColor);
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
