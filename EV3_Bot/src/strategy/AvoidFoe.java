package strategy;

import motor.BasicMotion;

/* This class can be the same as AvoidWall. Because, Yeti doesn't make a difference 
 * between both.
 */

public class AvoidFoe implements Tactic {

	public AvoidFoe() {
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
	 * If Yeti see a foe, he had to avoid it. Yeti goes to right (90°) after
	 * goes ahead (180*4 cm) and goes to left the robot returns to its line
	 */
	@Override
	public boolean perform() {
		BasicMotion.rotate(90); // +90 so it is going to right
		BasicMotion.moveBy(180 * 4);
		BasicMotion.rotate(-90);
		BasicMotion.moveBy(180 * 6);
		BasicMotion.rotate(-90);
		BasicMotion.moveBy(180 * 4);
		BasicMotion.rotate(90);
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
