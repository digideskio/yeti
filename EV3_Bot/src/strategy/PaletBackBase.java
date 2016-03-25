package strategy;

import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import sensors.BasicColor;

public class PaletBackBase implements Tactic {

	private boolean stopped;
	private StraightMotion smotion;
	private AvoidFoe avoidfoe;
	private GoBack goback;
	private MoveToTactic goCenter;
	
	PaletBackBase() {
		stopped = false;
		smotion = new StraightMotion();
		goCenter = new MoveToTactic(Bot.getGPS().getRawX()+(1050/2), Bot.getGPS().getRawY()+(1280/2));
	}

	@Override
	public String getDisplayName() {
		return "PaletBackBase";
	}

	@Override
	public boolean handleObstacle() {
		smotion.stop();
		avoidfoe = new AvoidFoe();
		return true;
	}

	@Override
	public boolean handleContact() {
		smotion.stop();
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
		
		if(goCenter != null){
			if (goCenter.perform())
				goCenter = null;
			return false;
		}
		
		float angleDiff = Bot.getGPS().getOrientation().diffTowardsTarget();
		if (angleDiff >= 2.)
			BasicMotion.rotate((int)angleDiff, true);
		
		if (!smotion.isMoving())
			smotion.start(true);
		
		if (Bot.getSensorsCache().getColor() == BasicColor.White) {
			smotion.stop();
			BasicMotion.openClaw(true);
			BasicMotion.moveBy(-180 * 3);
			BasicMotion.rotate(180);
			return true;
		}
		if (stopped) {
			return true;
		}
		return false;
	
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
