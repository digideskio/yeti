package gps;

import main.Bot;
import sensors.BasicColor;

/**
 * Gives all free nearest discs and changes disc's state if the disc is captured
 */

public class PaletPosition {

	private static Palet[] palets; // contains all discs with their state : free
									// or captured
	private static int numberOfDiscs = 9;
	static int goToX; // coordinates of the nearest discs found by Yeti
	static int goToY;
	BasicColor vColor; 
	static BasicColor goToLine;
	boolean isGreen;
	static int xCapturedDisc = -1; // coordinates of the captured disc
	static int yCapturedDisc = -1;
	static int captureAttempts;

	public PaletPosition() {
		if (palets == null) {
			captureAttempts = 1;
			goToX = Bot.getGPS().getRawX();
			if (Bot.getGPS().isGreen) {
				goToY = Bot.getGPS().getRawY() - 1265;
			} else {
				goToY = Bot.getGPS().getRawY() + 1240;
			}
			palets = new Palet[numberOfDiscs];
			palets[0] = new Palet(1053, 1240, BasicColor.Red); // Intersection(Horizontal/Vertical)
												// : Blue/Red     conversion 1cm ~ 21 robot units
			palets[1] = new Palet(1053 + 1049, 1240, BasicColor.Black); // Blue/Black
			palets[2] = new Palet(1053 + 1049 + 1041, 1240, BasicColor.Yellow); // Blue/Yellow
			palets[3] = new Palet(1053, 1240 + 1248, BasicColor.Red); // Black/Red
			palets[4] = new Palet(1053 + 1049, 1240 + 1248, BasicColor.Black); // Black/Black
			palets[5] = new Palet(1053 + 1049 + 1041, 1240 + 1248, BasicColor.Yellow); // Black/Yellow
			palets[6] = new Palet(1053, 1240 + 1248 + 1233, BasicColor.Red); // Green/Red
			palets[7] = new Palet(1053 + 1049, 1240 + 1248 + 1233, BasicColor.Black); // Green/Black
			palets[8] = new Palet(1053 + 1049 + 1041, 1240 + 1248 + 1233, BasicColor.Yellow); // Green/Yellow
		}
	}

	public BasicColor getVColor() {
		return vColor;
	}
	
	public int getGoToX() {
		return goToX;
	}

	public int getGoToY() {
		return goToY;
	}
	
	public BasicColor getGoToLine() {
		return goToLine;
	}

	public int getxPalet() {
		return xCapturedDisc;
	}

	public int getyPalet() {
		return yCapturedDisc;
	}

	/**
	 * brings Yeti to the nearest disc only on the area 
	 * trouve le palet le plus proche en excluant la ligne sur 
	 * laquelle Yeti se trouve
	 */
	public void nearestPalet(BasicColor excludedColor) {
		double distMin = 1000000;
		for (int i = 0; i < numberOfDiscs; i++) {
			// calculates the shortest distance between Yeti and all discs with
			// the absolute value
			if (palets[i].isCaptured() == false && palets[i].getColorVDisc() != excludedColor) {
				int compX = palets[i].getXdisc() - Bot.getGPS().getRawX();
				int compY = palets[i].getYdisc() - Bot.getGPS().getRawY();
				double dist = Math.sqrt(compX * compX + compY * compY);
				// updates distMin
				if (dist < distMin) {
					distMin = dist;
					goToX = palets[i].getXdisc();
					goToY = palets[i].getYdisc();
					goToLine = palets[i].getColorVDisc();
				}
			}
		}
	}
	
	public void nearestLine() {
			nearestPalet(goToLine);
	}
	
	public static int getFreeDiscs() {
		int n = numberOfDiscs;
		for (int i = 0; i < numberOfDiscs; i++) {
			if (palets[i].isCaptured)
				n--;
		}
		return n;
	}

	/**
	 * Gets Yeti's coordinates when the robot caught a disc and changes the
	 * state of disc
	 */
	public static void discCaptured() {
		captureAttempts++;
		xCapturedDisc = goToX;
		yCapturedDisc = goToY;
		double radius, distX, distY;
		for (int i = 0; i < numberOfDiscs; i++) {
			// distance X between the disc's coordinate and Yeti coordinates
			// when it caught it
			distX = palets[i].getXdisc() - xCapturedDisc;
			// distance Y between the disc's coordinate and Yeti coordinates
			// when it caught it
			distY = palets[i].getYdisc() - yCapturedDisc;
			// Tolerance zone
			radius = Math.sqrt(distX * distX + distY * distY);
			if (radius <= 420) {
				palets[i].setCaptured(true);
				break;
			}
		}
	}

	public boolean isFreeDiscs() {
		for (int i = 0; i < numberOfDiscs; i++) {
			if (palets[i].isCaptured() == false)
				return true;
		}
		return false;
	}

	public static boolean hasFirstCapture() {
		return captureAttempts == 1;
	}
}
