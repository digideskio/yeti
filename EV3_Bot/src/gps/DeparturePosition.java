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

	private TextMenu textMenu;
	private static int xDeparture;
	private static int yDeparture;
	
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
			
			//pas trouvé comment faire la sélection
			
			Bot.println("Green/Red",1);
			xDeparture = 1053;
			yDeparture = 1240 + 1248 + 1233 + 1265;
			
			Bot.println("Green/Black",2);
			xDeparture = 1053 + 1049;
			yDeparture = 1240 + 1248 + 1233 + 1265;
			
			Bot.println("Green/Yellow",3);
			xDeparture = 1053 + 1049 + 1041;
			yDeparture = 1240 + 1248 + 1233 + 1265;
			
			Bot.println("Blue/Red",4);
			xDeparture = 1053;
			
			Bot.println("Blue/Black",5);
			xDeparture = 1053 + 1049;
			
			Bot.println("Blue/Yellow",6);
			xDeparture = 1053 + 1049 + 1041;		
		}
	}
}
