
import config.DefaultPorts;
import color.BasicColor;
import color.ColorDetector;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class Follower {
	
	private static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text+padding, 0, y);
	}
	
	/**
	 * Follows a white line on a reddish/maroon background
	 * Basically, follows the edge of a paper on my wooden floor!
	 */
	public static void main(String[] args) {
		ColorDetector cd = new ColorDetector(DefaultPorts.getColorSensor());
		println("Line follower!", 1);
		
		while (Button.ESCAPE.isUp()) {
			int dist = cd.getLineDistance(BasicColor.White, BasicColor.Red);
			println("Position: "+dist+"%", 6);
			
			int baseSpeed = 280, speed = 80; 
			Motor.A.setSpeed(baseSpeed+speed*dist/100);
			Motor.A.forward();
			Motor.D.setSpeed(baseSpeed+speed*(100-dist)/100);
			Motor.D.forward();
			
			Delay.msDelay(5);
		}
		
		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}

}
