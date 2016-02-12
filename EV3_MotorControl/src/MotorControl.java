import gps.PositionTracker;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import motion.BasicMotion;
import color.ColorDetector;
import config.DefaultPorts;


public class MotorControl {
	private static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text+padding, 0, y);
	}
	
	public static void main(String[] args) {
		ColorDetector cd = new ColorDetector();
		println("Orientation test", 1);
		
		BasicMotion.openClaw();
		Delay.msDelay(500);
		BasicMotion.closeClaw();

		BasicMotion.rotate(90);
		Delay.msDelay(1000);
		BasicMotion.rotate(-180);
		Delay.msDelay(1000);
		BasicMotion.rotate(360);
		Delay.msDelay(1000);
		BasicMotion.rotate(-270);
		
		BasicMotion.moveBy(360);
		Delay.msDelay(1000);
		BasicMotion.moveBy(-720);
		Delay.msDelay(1000);
		BasicMotion.moveBy(360);
		
		while (Button.ESCAPE.isUp()) {
			println("Tacho L:"+Motor.A.getTachoCount(), 2);
			println("Tacho R:"+Motor.D.getTachoCount(), 3);
			println("Tacho C:"+Motor.C.getTachoCount(), 4);
			Delay.msDelay(50);
		}
		
		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}
}
