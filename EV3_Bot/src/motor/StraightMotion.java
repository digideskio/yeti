package motor;

import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import main.Bot;
import config.DefaultPorts;

/**
 * Performs a straight (forwards or backwards) 
 * asynchronous motion while keeping the GPS updated
 */
public class StraightMotion {
	private static NXTRegulatedMotor lm = DefaultPorts.getLeftMotor(),
	 		 					     rm = DefaultPorts.getRightMotor();
	private int startTacho, lastUpdatedTacho;
	private boolean isMoving;
	private float speed;
	
	public StraightMotion() {
		this(lm.getMaxSpeed());
	}
	
	public StraightMotion(float speed) {
		startTacho = lastUpdatedTacho = 0;
		isMoving = false;
		this.speed = speed;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void start(boolean forward) {
		if (isMoving)
			throw new IllegalArgumentException("Already moving");
		isMoving = true;
		startTacho = lastUpdatedTacho = lm.getTachoCount();
		
		lm.synchronizeWith(new RegulatedMotor[]{rm});
		lm.startSynchronization();
		lm.setSpeed(speed);
		rm.setSpeed(speed);
		if (forward) {
			lm.forward();
			rm.forward();
		} else {
			lm.backward();
			rm.backward();
		}
		lm.endSynchronization();
	}
	
	public void stop() {
		if (!isMoving)
			return;
		
		updateGPS();
		
		lm.startSynchronization();
		lm.stop(true);
		rm.stop(true);
		lm.endSynchronization();
	}
	
	/**
	 * Call periodically while moving to update the GPS position
	 */
	public void updateGPS() {
		if (!isMoving)
			throw new IllegalArgumentException("Not moving");
		int newTacho = lm.getTachoCount();
		Bot.getGPS().movedBy(newTacho - lastUpdatedTacho);
		lastUpdatedTacho = newTacho;
	}
	
	/**
	 * Returns the current absolute distance traveled since start() was last called
	 */
	public int getCurrentDistance() {
		return Math.abs(lm.getTachoCount() - startTacho);
	}
}
