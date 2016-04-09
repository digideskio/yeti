package gps;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import main.Bot;

/**
 * permits to choose the departure position
 * 
 * @author decerle
 *
 */

public class DeparturePosition {

	private static int xDeparture;
	private static int yDeparture;
	private static int curseur = 1;

	public static int getxDeparture() {
		return xDeparture;
	}

	public static int getyDeparture() {
		return yDeparture;
	}

	public DeparturePosition() {
		xDeparture = 1053;
		yDeparture = 0;
	}

	public static void choosePosition() {
		Bot.println(">Green/Red", 1);
		Bot.println(" Green/Black", 2);
		Bot.println(" Green/Yellow", 3);
		Bot.println(" Blue/Red", 4);
		Bot.println(" Blue/Black", 5);
		Bot.println(" Blue/Yellow", 6);

		while (Button.ENTER.isUp()) {
			if (Button.ESCAPE.isDown()) {
				return;
			} else if (Button.UP.isDown() && curseur > 1) {
				LCD.drawString(" ", 0, curseur);
				curseur--;
				LCD.drawString(">", 0, curseur);
			} else if (Button.DOWN.isDown() && curseur < 6) {
				LCD.drawString(" ", 0, curseur);
				curseur++;
				LCD.drawString(">", 0, curseur);
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		switch (curseur) {
		case 1:
			xDeparture = 1053;
			yDeparture = 1240 + 1248 + 1233 + 1265;
			break;
		case 2:
			xDeparture = 1053 + 1049;
			yDeparture = 1240 + 1248 + 1233 + 1265;
			break;
		case 3:
			xDeparture = 1053 + 1049 + 1041;
			yDeparture = 1240 + 1248 + 1233 + 1265;
			break;
		case 4:
			xDeparture = 1053;
			break;
		case 5:
			xDeparture = 1053 + 1049;
			break;
		case 6:
			xDeparture = 1053 + 1049 + 1041;
			break;
		}
	}
}
