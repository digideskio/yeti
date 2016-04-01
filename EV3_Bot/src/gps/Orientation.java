package gps;

public class Orientation {
	/// This is our Yaw in degrees relative to the world
	/// We consider 0 degrees to be the direction when
	/// looking at the red line from the yellow line
	private float worldYaw;
	private boolean isGreen;
	
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
		this.isGreen = isGreen;
		worldYaw = isGreen ? 270 : 90;
	}
	
	public float getAngle() {
		return worldYaw;
	}
	
	public float diffTowardsTarget() {
		if (isGreen)
			return diff(0, -1);
		else
			return diff(0, 1);
	}
	
	/**
	 * Returns the difference in degrees between two orientations
	 */
	public float diff(Orientation other) {
		return other.worldYaw - worldYaw;
	}
	
	/**
	 * Returns the difference in degrees between the current orientation
	 * and the given vector, so that rotating by it will make the robot
	 * face towards that vector.
	 */
	public float diff(int x, int y) {
		if (x==0 && y==0)
			return 0;
		
		// First get the angle of the vector with 0
		float vecAngle = (float)((Math.atan2(y, x) * 360./(2*Math.PI) + 360.) % 360.);
		return (vecAngle - worldYaw) % 360.f;
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
