package strategy;

import gps.PaletPosition;
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
	private static Boolean firstCapture;
	private String name;
	
	PaletBackBase() {
		if (firstCapture == null)
			firstCapture = false;
		stopped = false;
		smotion = new StraightMotion();
		if(Bot.getGPS().isGreen())
			goCenter = new MoveToTactic(Bot.getGPS().getRawX()+(1050/2), Bot.getGPS().getRawY()-(1280/2));
		else
			goCenter = new MoveToTactic(Bot.getGPS().getRawX()+(1050/2), Bot.getGPS().getRawY()+(1280/2));
	
	}

	@Override
	public String getDisplayName() {
		if (name != null)
			return null;
		return "PaletBackBase";
	}

	@Override
	public boolean handleObstacle() {
		smotion.stop();
		avoidfoe = new AvoidFoe();
		name = avoidfoe.getDisplayName();
		return true;
	}

	@Override
	public boolean handleContact() {
		smotion.stop();
		goback = new GoBack();
		name = goback.getDisplayName();
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
		
		if(!firstCapture && goCenter != null){
			if (goCenter.perform()) {
				goCenter = null;
				firstCapture = true;
				Bot.getGPS().rotatedBy(5);
			}
			return false;
		}
		
		float angleDiff = Bot.getGPS().getOrientation().diffTowardsTarget();
		if (Math.abs(angleDiff) >= 1.) {
			BasicMotion.rotate((int)angleDiff, true);
		}
			
		if (!smotion.isMoving())
			smotion.start(true);
		smotion.updateGPS();
		
		if (Bot.getSensorsCache().getColor() == BasicColor.White) {
			smotion.stop();
			BasicMotion.openClaw(true);
			BasicMotion.moveBy(-180 * 2);
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
}
