package strategy;

import config.DefaultPorts;
import lejos.hardware.motor.NXTRegulatedMotor;
import main.Bot;
import sensors.BasicColor;

public class FollowLine implements Tactic {
	private AvoidFoe avoidfoe;
	private GoBack goback;
	private BasicColor c;
	private BasicColor stopColor;
	private NXTRegulatedMotor m1, m2;
	private boolean abort;

	public FollowLine(BasicColor color, BasicColor stop, boolean followFromLeft) {
		c = color;
		stopColor = stop;
		if (followFromLeft) {
			m1 = DefaultPorts.getLeftMotor();
			m2 = DefaultPorts.getRightMotor();
		} else {
			m1 = DefaultPorts.getRightMotor();
			m2 = DefaultPorts.getLeftMotor();
		}
	}

	@Override
	public String getDisplayName() {
		return "FL"+c+stopColor;
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
		if (abort) {
			m1.stop();
			m2.stop();
			return true;
		}
		BasicColor curColor = Bot.getSensorsCache().getColor();
		m1.forward();
		m2.forward();
		if (curColor == stopColor) {
			// stop yeti
			m1.setSpeed(0);
			m2.setSpeed(0);
			return true;
		} else {
			// turn left to find the searched color
			int dist = Bot.getSensorsCache().getLineDistance(c);
			int baseSpeed = 240, speedo = 160;
			m1.setSpeed(baseSpeed + speedo * dist / 100);
			m2.setSpeed(baseSpeed + speedo * (100 - dist) / 100);
		}

		return false;
	}

	@Override
	public void abort() {
		abort = true;
		m1.stop();
		m2.stop();
	}

	@Override
	public void stop() {
		abort();
	}

}
