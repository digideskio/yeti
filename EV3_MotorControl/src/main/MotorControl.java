package main;
import gps.PositionTracker;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import motor.StraightMotion;
import color.ColorDetector;


public class MotorControl {
	private static PositionTracker gps;
	
	private static void println(String text, int y) {
		String padding = "                    ";
		LCD.drawString(text+padding, 0, y);
	}
	
	/**
	 * Returns the global position tracker of the robot
	 * Use this instead of local solutions to keep state consistent
	 */
	public static PositionTracker getGPS() {
		return gps;
	}
	
	public static void main(String[] args) {
		ColorDetector cd = new ColorDetector();
		gps = new PositionTracker(0, 0);
		println("Orientation test", 1);
		
		StraightMotion sm = new StraightMotion();
		sm.start(true);
		
		while (Button.ESCAPE.isUp()) {
			if (Button.ENTER.isDown()) {
				if (sm.isMoving())
					sm.stop();
				else
					sm.start(true);
			}
			
			println("Tacho L:"+Motor.A.getTachoCount(), 2);
			println("Tacho R:"+Motor.D.getTachoCount(), 3);
			println("Tacho C:"+Motor.C.getTachoCount(), 4);
			println("Pos:"+gps.getRawPosString(), 5);
			println("Rot:"+gps.getOrientation().getAngle()+"°", 6);
			Delay.msDelay(50);
		}
		
		cd.close();
		LCD.clear();
		Delay.msDelay(500); // Give time to release the exit button!
	}
}
