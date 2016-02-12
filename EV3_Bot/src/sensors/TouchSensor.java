package sensors;

import config.DefaultPorts;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * Marketing calls it an "exceptionally precise tool", it's really just a push button
 */
public class TouchSensor {
	private EV3TouchSensor sensor;
	private SensorMode mode;
	private float[] samples;
	
	public TouchSensor() {
		sensor = new EV3TouchSensor(DefaultPorts.getTouchSensor());
		mode = sensor.getTouchMode();
		samples = new float[mode.sampleSize()];
	}
	
	public boolean isPressed() {
		mode.fetchSample(samples, 0);
		return samples[0] == 1.f;
	}
}
