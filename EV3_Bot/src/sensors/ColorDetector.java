package sensors;
import java.io.Closeable;
import config.DefaultPorts;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;


/**
 * @author tux3
 * High level and low level APIs to detect the current color seen by the sensor.
 * It's tuned to detect only the terrain's colors accurately
 * Call close() to free up the port when no longer needed
 * 
 * Use getTunedColor() for good color detection in the test environment.
 * Use getLineDistance() to know close (in %) we are to a line we're following.
 */
public class ColorDetector implements Closeable {
	private EV3ColorSensor cs;
	private static SampleProvider rgb;
	private static float[] samples;
	
	public ColorDetector() {
		cs = new EV3ColorSensor(DefaultPorts.getColorSensor());
		rgb = cs.getRGBMode();
		samples = new float[rgb.sampleSize()];
	}
	
	private static String colorToString(int color) {
		if (color == Color.NONE)
			return "None";
		else if (color == Color.BLACK)
			return "Black";
		else if (color == Color.BLUE)
			return "Blue";
		else if (color == Color.GREEN)
			return "Green";
		else if (color == Color.YELLOW)
			return "Yellow";
		else if (color == Color.RED)
			return "Red";
		else if (color == Color.WHITE)
			return "White";
		else if (color == Color.BROWN)
			return "Brown";
		else
			throw new IllegalArgumentException("Invalid color "+color);
	}
	
	/**
	 * Returns the color code detected by the official EV3 software
	 * Use the tuned version for better result in the test environment.
	 */
	public int getStandardColorCode() {
		return cs.getColorID();
	}
	
	/**
	 * Returns the color names detected by the official EV3 software
	 * Use the tuned version for better result in the test environment.
	 */
	public String getStandardColorName() {
		int color = getStandardColorCode();
		return colorToString(color);
	}
	
	/**
	 * Returns the raw RGB values read from the sensor.
	 * This is largely meaningless due to ambient light variations,
	 * use HSV instead if you care about detecting the color.
	 */
	public Color getRawRGB() {
		rgb.fetchSample(samples, 0);
		return new Color((int)(samples[0]*255), 
						 (int)(samples[1]*255), 
						 (int)(samples[2]*255));
	}
	
	/**
	 * Returns some adjusted RGB values read from the sensor.
	 * More meaningful than the raw RGB, but you should still use HSV instead.
	 */
	public static Color getCookedRGB() {
		rgb.fetchSample(samples, 0);
		return new Color(Math.min((int)(samples[0]*956), 255), 
						 Math.min((int)(samples[1]*638), 255), 
						 Math.min((int)(samples[2]*383), 255));
	}
	
	/**
	 * Returns the HSV values computed from the cooked RGB data
	 * This gives a reasonably accurate estimation of the color,
	 * except the value is often very low and the saturation low-ish.
	 * H is between 0-360 and S/V are between 0-100 (to match the GIMP color picker!)
	 */
	public static ColorHSV getHSV() {
		float[] hsv = new float[3];
		Color color = getCookedRGB();
		java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
		ColorHSV result = new ColorHSV((int)(hsv[0] * 360),
									   (int)(hsv[1] * 100),
									   (int)(hsv[2] * 100));
		return result;
	}
	
	public BasicColor HSVtoColor(ColorHSV hsv) {
		int H = hsv.getH(), S = hsv.getS(), V = hsv.getV();
		hsv = null;
		
		/// TODO: Tune to the actual terrain
		/// Right now it works incredibly well on random objects,
		/// much better than the official one, but it needs tuning for the terrain
		if (V <= 1)
			return BasicColor.None;
		else if (S <= 25 && V >= 4 && V <= 30)
			return BasicColor.Gray;
		else if (S <= 70 && V <= 4)
			return BasicColor.Black;
		else if (S <= 50 && V >= 40)
			return BasicColor.White;
		else if (H <= 15 || H >= 300)
			return BasicColor.Red;
		else if (H >= 185)
			return BasicColor.Blue;
		else if (H >= 60)
			return BasicColor.Green;
		else if (H >= 15)
			return BasicColor.Yellow;
		else
			return BasicColor.None;
	}
	
	/**
	 * Accurate detection of colors
	 */
	public BasicColor getTunedColor() {
		ColorHSV hsv = getHSV();
		return HSVtoColor(hsv);
	}
	
	/**
	 * Returns a percentage indicating how close we are to a line of the given color
	 * Assumes that the background (outside of the line) is Gray,
	 * if the background is too colorful the function might think it's always on the line
	 */
	public static int getLineDistance(BasicColor color) {
		return getLineDistance(color, BasicColor.Gray);
	}
	
	public static int getLineDistance(BasicColor color, BasicColor background) {
		// Take the distance between the given color and gray and define it as 100 units,
		// then clamp the distance between the current color and the target to 100 units.
		// Only consider S and V because for low S/V, H becomes meaningless.
		
		int vcoeff = 1;
		ColorHSV target = color.toHSV(), bg = background.toHSV(), current = getHSV();;
		
		// For low V, S varies a *lot* so don't take it as much into account
		if (current.getV() <= 5)
			vcoeff = 2;
		if (current.getV() <= 2)
			vcoeff = 3;
		int curDist = Math.abs(target.getS() - current.getS())
				    + Math.abs(target.getV() - current.getV()) * vcoeff;
		int refDist = Math.abs(target.getS() - bg.getS())
					+ Math.abs(target.getV() - bg.getV()) * vcoeff;
		
		curDist = Math.max(Math.min(curDist*110/refDist - 10, 100), 0);
		return curDist;
	}
	
	public void close() {
		cs.close();
	}
}
