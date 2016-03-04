package sensors;

import config.DefaultPorts;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class SonarSensor {
	private EV3UltrasonicSensor sensor;
	private SampleProvider mode;
	private float[] samples;
	
	public SonarSensor() {
		sensor = new EV3UltrasonicSensor(DefaultPorts.getUltrasonicSensor());
		mode = sensor.getDistanceMode();
		samples = new float[mode.sampleSize()];
	}
	
	/**
	 * Returns the distance in centimeters detected by the sensor
	 */
	public float getDistance() {
		mode.fetchSample(samples, 0);
		return samples[0] * 100;
	}
}
