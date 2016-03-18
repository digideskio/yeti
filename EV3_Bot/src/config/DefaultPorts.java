package config;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * @author tux3
 *
 * Defines the port configuration on our EV3.
 * This is the one source of truth for ports,
 * so it's easy to switch to a different configuration.
 */
public class DefaultPorts {
	public static Port getColorSensor() {
		return LocalEV3.get().getPort("S4");
	}
	public static Port getTouchSensor() {
		return LocalEV3.get().getPort("S1");
	}
	public static Port getUltrasonicSensor() {
		return LocalEV3.get().getPort("S2");
	}
	
	public static NXTRegulatedMotor getLeftMotor() {
		return Motor.A;
	}
	public static NXTRegulatedMotor getRightMotor() {
		return Motor.D;
	}
	public static NXTRegulatedMotor getClawMotor() {
		return Motor.C;
	}
}
