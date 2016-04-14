package strategy;

import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import sensors.BasicColor;

public class FindVLine implements Tactic {
	private StraightMotion smotion;
	private BasicColor color;
	private boolean stopped, right;
	
	public FindVLine(BasicColor color) {
		this.color = color;
		stopped = false;
		smotion = new StraightMotion();
		int linex;
		switch (color)
		{
		case Red:
			linex = 1053;
			break;
		case Black:
			linex = 1053 + 1049;
			break;
		case Yellow:
			linex = 1053 + 1049 + 1041;
			break;
		default:
			throw new IllegalArgumentException("Not a valid hline color: "+color);
		}
		right = Bot.getGPS().getRawX() <= linex; 
	}
	
	@Override
	public String getDisplayName() {
		return "FindVL"+color;
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
		if (stopped) {
			smotion.stop();
			return true;
		}
		
		if (Bot.getSensorsCache().getColor() == color) {
			stopped = true;
			smotion.stop();
			try {
				Thread.sleep(500, 0);
			} catch (InterruptedException e) {
			}
			return true;
		}
		
		float angleDiff;
		if (right)
			angleDiff = Bot.getGPS().getOrientation().diff(1, 0);
		else
			angleDiff = Bot.getGPS().getOrientation().diff(-1, 0);
		if (Math.abs(angleDiff) >= 1.)
			BasicMotion.rotate((int)angleDiff, true);
		
		if (!smotion.isMoving())
			smotion.start(true);
		smotion.updateGPS();
		return false;
	}

	@Override
	public void abort() {
		stopped = true;
		smotion.stop();
	}
}
