package gps;

import lejos.hardware.Button;
import lejos.utility.TextMenu;
import main.Bot;

/**
 * permits to choose the departure position
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
		while (Button.ENTER.isUp()) {
			Bot.println(">",curseur);
			Bot.println("Green/Red",1);	
			Bot.println("Green/Black",2);
			Bot.println("Green/Yellow",3);
			Bot.println("Blue/Red",4);
			Bot.println("Blue/Black",5);
			Bot.println("Blue/Yellow",6);
			if (Button.UP.isDown() && curseur >= 1) {
				curseur++;
			}
			if (Button.DOWN.isDown() && curseur <= 6 ) {
				curseur--;
			}		
		}
		switch(curseur) {
		case 1 : xDeparture = 1053;
				 yDeparture = 1240 + 1248 + 1233 + 1265; break;
		case 2 : xDeparture = 1053 + 1049;
			     yDeparture = 1240 + 1248 + 1233 + 1265;break;
		case 3 : xDeparture = 1053 + 1049 + 1041;
				 yDeparture = 1240 + 1248 + 1233 + 1265;break;
		case 4 : xDeparture = 1053;break;
		case 5 : xDeparture = 1053 + 1049;break;
		case 6 : xDeparture = 1053 + 1049 + 1041;break;
		}
	}
}
