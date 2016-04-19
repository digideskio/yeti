
import sensors.BasicColor;
import sensors.ColorDetector;
import gps.PositionTracker;
import config.DefaultPorts;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


public class GPS {
	
	private static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text+padding, 0, y);
	}
	
	public static void main(String[] args) {
		ColorDetector cd = new ColorDetector(DefaultPorts.getColorSensor());
		PositionTracker gps = new PositionTracker(0,0);
		println("GPS Module", 1);
		
		gps.crossLine(BasicColor.Green, true);
		gps.followLine(BasicColor.Red, false);
		gps.crossLine(BasicColor.Blue, true);
		gps.followLine(BasicColor.Black, true);
		gps.crossLine(BasicColor.Black, false);
		
		while (Button.ESCAPE.isUp()) {
			println("Pos: "+gps.getPosDescription(), 6);
			
			Delay.msDelay(50);
		}
		
		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}
}
