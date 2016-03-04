package strategy;

import motor.BasicMotion;

public class GoBack implements Tactic {

	private boolean wall;

	public GoBack() {
		this.wall = true;
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
	 * The robot need to back off before chose another strategy.
	 * 
	 * Search a line to begin another strategy
	 */
	@Override
	public boolean perform() {
		BasicMotion.rotate(180);
		BasicMotion.moveBy(10);
		this.wall = false;
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
