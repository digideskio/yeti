package main;
import config.DefaultPorts;
import gps.PositionTracker;
import sensors.ColorDetector;
import sensors.SensorsCache;
import sensors.SonarSensor;
import sensors.TouchSensor;
import strategy.Planner;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


public class Bot {
	private static Planner planner;
	private static ColorDetector cd;
	private static PositionTracker gps;
	private static TouchSensor button;
	private static SonarSensor sonar;
	private static SensorsCache cache;
	
	private static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text+padding, 0, y);
	}
	
	/**
	 * Returns a cache of the sensor input read by this robot
	 * This should be used instead of reading directly from sensors
	 */
	public static SensorsCache getSensorsCache() {
		return cache;
	}
	
	/**
	 * Returns the global position tracker of the robot
	 * Use this instead of local solutions to keep state consistent
	 */
	public static PositionTracker getGPS() {
		return gps;
	}
	
	public static void main(String[] args) {
		planner = new Planner();
		cd = new ColorDetector();
		gps = new PositionTracker(0,0);
		button = new TouchSensor();
		sonar = new SonarSensor();
		cache = new SensorsCache(cd, button, sonar);
		
		println("Yeti Bot", 0);
		
		while (Button.ESCAPE.isUp()) {
			processEvents();
		}
		
		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}
	
	/**
	 * Main event loop of the bot
	 * Don't do anything blocking in here,
	 * unless you want to ignore all sensory input
	 */
	public static void processEvents() {
		// 1. Read data from sensors
		cache.update();
		
		// 2. Handle exceptional events
		float sonarDistance = cache.getSonarDistance();
		if (sonarDistance <= 30.f) {
			// At 30cm we're getting close to an object, but
			// still have some time to swerve around and we shouldn't
			// get false positives from non-critical obstacles
			planner.handleObstacle();
		} else if (sonarDistance <= 10.f) {
			// With claws closed we have direct contact at just under 8cm,
			// so anywhere under 10cm is guaranteed close contact
			// With claws open we risk reaching 0cm with gives +inf, 
			// so 10cm is already getting pretty dangerous
			planner.handleContact();
		}
		
		// 3. Perform our tactics
		planner.performTactics();
		
		// 4. Print status
		println("Pos: "+gps.getPosDescription(), 1);
		println("Pressed: "+(cache.isButtonPressed()?"Yes":"No"), 2);
		println("Distance: "+String.format("%.1f",sonarDistance)+"cm", 3);
		println("Tactic: "+planner.getCurrentTacticName(), 4);
		println("Color: "+cache.getColor(), 5);
		
		// 5. Don't sample inputs too often to slightly reduce chances of outliers
		Delay.msDelay(10);
	}
}
