
import sensors.ColorDetector;
import config.DefaultPorts;
import sensors.ColorHSV;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class ColorTool {
	
	private static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text+padding, 0, y);
	}
	
	/**
	 * Shows the current detected color and its raw RGB components
	 * The raw RGB output from the sensor is undocumented,
	 * and it's practically meaningless without normalization.
	 */
	public static void main(String[] args) {
		ColorDetector cd = new ColorDetector();
		println("Color sensor", 1);
		println("Enter for RGB/HSV", 2);
		
		boolean hsvMode = false;
		
		while (Button.ESCAPE.isUp()) {
			if (Button.ENTER.isDown()) {
				hsvMode = !hsvMode;
				while (Button.ENTER.isDown())
					Delay.msDelay(10);
			}
			
			if (hsvMode) {
				ColorHSV hsv = cd.getHSV();
				println("Hue: "+hsv.getH(), 3);
				println("Sat: "+hsv.getS(), 4);
				println("Val: "+hsv.getV(), 5);
			} else {
				Color rawColor = cd.getCookedRGB();
				println("Red:   "+(int)(rawColor.getRed()), 3);
				println("Green: "+(int)(rawColor.getGreen()), 4);
				println("Blue:  "+(int)(rawColor.getBlue()), 5);
			}
			println("T/O: "+cd.getTunedColor()+'/'+cd.getStandardColorName(), 6);
			Delay.msDelay(100);
		}
		
		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}

}
