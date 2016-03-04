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

	PaletBackBase() {
		stopped = false;
		smotion = new StraightMotion();
	}

	@Override
	public String getDisplayName() {
		return "PaletBackBase";
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
		if (!smotion.isMoving())
			smotion.start(true);
		if (Bot.getSensorsCache().getColor() == BasicColor.White) {
			smotion.stop();
			BasicMotion.openClaw(false);
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
