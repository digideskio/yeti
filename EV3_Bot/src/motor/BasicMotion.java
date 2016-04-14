package motor;

import java.util.Arrays;

import config.DefaultPorts;
import lejos.hardware.motor.NXTRegulatedMotor;
import main.Bot;

/**
 * Provides static functions for basic robot motion
 * None of the provided functions reset the tachometers
 * TODO: Update the GPS with this information
 */
public class BasicMotion {
	private static NXTRegulatedMotor lm = DefaultPorts.getLeftMotor(),
							 		 rm = DefaultPorts.getRightMotor(),
							 		 claw = DefaultPorts.getClawMotor();
	
	/**
	 * Perform a rotation (in degrees) of the robot
	 */
	public static void rotate(int deg) {
		rotate(deg, true);
	}
	public static void rotate(int deg, boolean waitForCompletion) {
		double deg2tacho = 2.235;
		int speed = 280;
		lm.synchronizeWith(new NXTRegulatedMotor[]{rm});
		lm.startSynchronization();
		lm.setSpeed(speed);
		rm.setSpeed(speed);
		lm.rotate((int)(-deg*deg2tacho), true);
		rm.rotate((int)(deg*deg2tacho), true);
		lm.endSynchronization();
		if (waitForCompletion)
			rm.waitComplete();
		Bot.getGPS().rotatedBy(deg);
	}
	
	/**
	 * Move the robot (forward or backwards) by a given number of units and stops
	 */
	public static void moveBy(int units) {
		Bot.log("MoveBy"+units+" stack:\n"
				+Arrays.toString(Thread.currentThread().getStackTrace()));
		
		int maxSpeed = (int)lm.getMaxSpeed();
		int minSpeed = 45;
		int accelDist = 50, decelDist = 180;
		int startTacho = lm.getTachoCount();
		int diff = 0;
		Bot.getGPS().movedBy(units);
		lm.synchronizeWith(new NXTRegulatedMotor[]{rm});
		
		// Set slow start speed and accelerate later
		lm.startSynchronization();
		lm.setSpeed(minSpeed);
		rm.setSpeed(minSpeed);
		if (units > 0) {
			lm.forward();
			rm.forward();
		} else {
			lm.backward();
			rm.backward();
		}
		lm.endSynchronization();
		units = Math.abs(units);
		
		// Accelerate
		do  {
			diff = Math.abs(lm.getTachoCount()-startTacho);
			if (diff <= accelDist)
				accelerate(minSpeed, maxSpeed, diff, accelDist);
		} while (diff < units/2);
		
		// Decelerate
		do  {
			diff = Math.abs(lm.getTachoCount()-startTacho);
			if (units-diff <= decelDist)
				accelerate(minSpeed, maxSpeed, units-diff, decelDist);
		} while (diff < units);
		
		lm.startSynchronization();
		lm.stop(true);
		rm.stop(true);
		lm.endSynchronization();
	}

	/** Open the front claw */
	public static void openClaw() {
		openClaw(false);
	}
	public static void openClaw(boolean waitForCompletion) {
		claw.setSpeed(claw.getMaxSpeed());
		claw.rotate(1300, !waitForCompletion);
	}
	
	/** Close the front claw */
	public static void closeClaw() {
		closeClaw(false);
	}
	public static void closeClaw(boolean waitForCompletion) {
		claw.setSpeed(claw.getMaxSpeed());
		claw.rotate(-1300, !waitForCompletion);
	}
	
	/**
	 * Accelerates gently up to a maximum speed
	 * lm must be synchronized with rm.
	 */
	private static void accelerate(int minSpeed, int maxSpeed, int pos, int distance) {
		double posRatio = Math.min((double)pos/distance, 1.0);
		int speed = Math.max((int)(posRatio * maxSpeed), minSpeed); 
		lm.startSynchronization();
		lm.setSpeed(speed);
		rm.setSpeed(speed);
		lm.endSynchronization();
	}
}
