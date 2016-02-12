package gps;

public class Orientation {
	/// This is our Yaw in degrees relative to the world
	/// We consider 0 degrees to be the direction when
	/// looking at the red line from the yellow line
	private float worldYaw;
	
	/**
	 * Constructs an orientation from an angle relative to the world
	 */
	public Orientation(int degrees) {
		setAngle(degrees);
	}
	
	/**
	 * Constructs a starting orientation for a robot
	 * If isGreen, the robot starts from the green side of the terrain,
	 * if false it starts from the blue side (opposite orientation).
	 */
	public Orientation(boolean isGreen) {
		worldYaw = isGreen ? 90 : 270;
	}
	
	public float getAngle() {
		return worldYaw;
	}
	
	/**
	 * Returns the difference in degrees between two orientations
	 */
	public float diff(Orientation other) {
		return other.worldYaw - worldYaw;
	}
	
	public void setAngle(float degrees) {
		if (degrees < 0 || degrees >= 360)
			throw new IllegalArgumentException("Invalid angle");
		worldYaw = degrees;
	}
	
	public void rotate(float angle) {
		worldYaw = (worldYaw + angle) % 360;
		if (worldYaw < 0)
			worldYaw += 360;
	}
}
