package gps;

import main.Bot;

/**
 * Gives all free nearest discs and changes disc's state if the disc is captured
 */

public class PaletPosition {

	private static Palet[] palets; // contains all discs with their state : free
									// or captured
	private static int numberOfDiscs = 9;
	int goToX; // coordinates of the nearest discs found by Yeti
	int goToY;
	static int xCapturedDisc = -1; // coordinates of the captured disc
	static int yCapturedDisc = -1;
	static int captureAttempts;

	public PaletPosition() {
		if (palets == null) {
			captureAttempts = 1;
			palets = new Palet[numberOfDiscs];
			palets[0] = new Palet(1053, 1240); // Intersection(Horizontal/Vertical)
												// : Blue/Red     conversion 1cm ~ 21 robot units
			palets[1] = new Palet(1053 + 1049, 1240); // Blue/Black
			palets[2] = new Palet(1053 + 1049 + 1041, 1240); // Blue/Yellow
			palets[3] = new Palet(1053, 1240 + 1248); // Black/Red
			palets[4] = new Palet(1053 + 1049, 1240 + 1248); // Black/Blue
			palets[5] = new Palet(1053 + 1049 + 1041, 1240 + 1248); // Black/Yellow
			palets[6] = new Palet(1053, 1240 + 1248 + 1233); // Green/Red
			palets[7] = new Palet(1053 + 1049, 1240 + 1248 + 1233); // Green/Black
			palets[8] = new Palet(1053 + 1049 + 1041, 1240 + 1248 + 1233); // Green/Yellow
		}
	}

	public int getGoToX() {
		return goToX;
	}

	public int getGoToY() {
		return goToY;
	}

	public int getxPalet() {
		return xCapturedDisc;
	}

	public int getyPalet() {
		return yCapturedDisc;
	}

	/**
	 * Searches which disc is the nearest of yeti
	 * 
	 * @return true if yeti found a disc
	 */
	public void nearestPalet() {
		double distMin = 1000000;
		for (int i = 0; i < numberOfDiscs; i++) {
			// calculates the shortest distance between Yeti and all discs with
			// the absolute value
			if (palets[i].isCaptured() == false) {
				int compX = palets[i].getXdisc() - Bot.getGPS().getRawX();
				int compY = palets[i].getYdisc() - Bot.getGPS().getRawY();
				double dist = Math.sqrt(compX * compX + compY * compY);
				// updates distMin
				if (dist < distMin) {
					distMin = dist;
					goToX = palets[i].getXdisc();
					goToY = palets[i].getYdisc();
				}
			}
		}
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
		xCapturedDisc = Bot.getGPS().getRawX();
		yCapturedDisc = Bot.getGPS().getRawY();
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
