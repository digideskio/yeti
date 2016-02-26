package sensors;

import config.DefaultPorts;

/**
 * Caches the data from sensors in a centralized location,
 * so that it can be queried quickly and with consistent results
 */
public class SensorsCache {
	private ColorDetector cd;
	private ColorHSV color;
	
	private TouchSensor button;
	private boolean isPressed;
	
	private SonarSensor sonar;
	private float distance;
	
	public SensorsCache(ColorDetector cd, TouchSensor button, SonarSensor sonar) {
		this.cd = cd;
		this.button = button;
		this.sonar = sonar;
	}
	
	/**
	 * Updates the cache with fresh readings from the sensors
	 */
	public void update() {
		color = cd.getHSV();
		isPressed = button.isPressed();
		distance = sonar.getDistance();
	}
	
	public ColorHSV getColorHSV() {
		return color;
	}
	
	public BasicColor getColor() {
		return cd.HSVtoColor(color);
	}
	
	public int getLineDistance(BasicColor curColor) {
		return cd.getLineDistance(curColor);
	}
	
	public boolean isButtonPressed() {
		return isPressed;
	}
	
	public float getSonarDistance() {
		return distance;
	}
}
