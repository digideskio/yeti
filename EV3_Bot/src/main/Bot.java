package main;

import java.io.File;

import gps.DeparturePosition;
import gps.PaletPosition;
import gps.PositionTracker;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import motor.BasicMotion;
import sensors.ColorDetector;
import sensors.SensorsCache;
import sensors.SonarSensor;
import sensors.TouchSensor;
import strategy.Planner;

public class Bot {
	private static Planner planner;
	private static ColorDetector cd;
	private static PositionTracker gps;
	private static TouchSensor button;
	private static SonarSensor sonar;
	private static SensorsCache cache;
	private static int FPS = 0;
	private static File logFile;

	public static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text + padding, 0, y);
	}

	public static void log(String text) {
		if (logFile == null) {
			logFile = new File("Bot.log");
		}

	}

	/**
	 * Returns a cache of the sensor input read by this robot This should be
	 * used instead of reading directly from sensors
	 */
	public static SensorsCache getSensorsCache() {
		return cache;
	}

	/**
	 * Returns the global position tracker of the robot Use this instead of
	 * local solutions to keep state consistent
	 */
	public static PositionTracker getGPS() {
		return gps;
	}

	/**
	 * Returns the global tactic planner
	 */
	public static Planner getPlanner() {
		return planner;
	}

	public static void main(String[] args) {
		cd = new ColorDetector();
		button = new TouchSensor();
		sonar = new SonarSensor();
		cache = new SensorsCache(cd, button, sonar);

		println("Yeti Bot", 0);
		DeparturePosition.choosePosition();
		gps = new PositionTracker(DeparturePosition.getxDeparture(), DeparturePosition.getyDeparture());
		planner = new Planner();
		new PaletPosition();
		BasicMotion.openClaw(true);

		while (Button.ESCAPE.isUp()) {
			processEvents();
		}

		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}

	/**
	 * Main event loop of the bot Don't do anything blocking in here, unless you
	 * want to ignore all sensory input
	 */
	public static void processEvents() {
		// 1. Read data from sensors
		cache.update();

		// 2. Handle exceptional events
		float sonarDistance = cache.getSonarDistance();
		if (sonarDistance <= 10.f) {
			// With claws closed we have direct contact at just under 8cm,
			// so anywhere under 10cm is guaranteed close contact
			// With claws open we risk reaching 0cm with gives +inf,
			// so 10cm is already getting pretty dangerous
			//planner.handleContact();
		} else if (sonarDistance <= 30.f) {
			// At 30cm we're getting close to an object, but
			// still have some time to swerve around and we shouldn't
			// get false positives from non-critical obstacles
			//planner.handleObstacle();
		}

		// 3. Perform our tactics
		planner.performTactics();

		// 4. Print status
		println("Pos:" + gps.getRawPosString() + " " + gps.getPosDescription(), 0);
		println("Rot:" + gps.getOrientation().getAngle(), 1);
		println("Pressed:" + (cache.isButtonPressed() ? "Yes" : "No"), 2);
		println("Distance:" + String.format("%.1f", sonarDistance) + "cm", 3);
		println("Tac:" + planner.getCurrentTacticName(), 4);
		println("Color:" + cache.getColor(), 5);
		println("FPS:" + FPS+", last:"+PaletPosition.getGoToLine(), 6);
		FPS += 1;

		// 5. Don't sample inputs too often to slightly reduce chances of
		// outliers
		Delay.msDelay(5);
	}
}
