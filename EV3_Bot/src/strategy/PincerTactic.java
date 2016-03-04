package strategy;

import main.Bot;
import motor.BasicMotion;

public class PincerTactic implements Tactic {

	private boolean stopped, Pinceropened;
	private AvoidFoe avoidfoe;
	private GoBack goback;

	PincerTactic() {
		stopped = false;
		Pinceropened = false;
	}

	@Override
	public String getDisplayName() {
		return "Nothing";
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
		if (!Pinceropened) {
			BasicMotion.openClaw();
			Pinceropened = true;
		}

		if (stopped)
			return true;

		if (Bot.getSensorsCache().isButtonPressed()) {
			BasicMotion.closeClaw(false);
			return true;
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
