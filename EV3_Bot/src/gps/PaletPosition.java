package gps;

import main.Bot;

/** 
 * Gives all free nearest discs and changes disc's state if the disc is captured
 */

public class PaletPosition {

	private static Palet[] palets;		//contains all discs with their state : free or captured
	private static int numberOfDiscs;
	
	int goToX;			//coordinates of the nearest discs found by Yeti
	int goToY;
	
	static int xCapturedDisc = -1;		//coordinates of the captured disc
	static int yCapturedDisc = -1;
	
	
	PaletPosition(int numberOfDiscs) {
		this.palets = new Palet[numberOfDiscs];
		this.palets[0] = new Palet(1084, 2009);
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
	 * @return true if yeti found a disc
	 */
	public boolean nearestPalet() {	
		double distMin = 1000000;	
		int ligne = -1, column = -1;	
		for (int i = 0; i < numberOfDiscs; i++) {
				int compX = palets[i].getXdisc() - Bot.getGPS().getRawX();
				int compY = palets[i].getYdisc() - Bot.getGPS().getRawY();
				double dist = Math.sqrt(compX*compX + compY*compY);
				if (dist < distMin && palets[i].isCaptured() == false) {
					distMin = dist;
					ligne = palets[i].getXdisc();
					column = palets[i].getYdisc();
					
					this.goToX = ligne;
					this.goToY = column;

					return true;		
				}	
		}	
		return false;	
	}
	
	/**
	 * Gets Yeti coordinates when the robot caught a disc
	 * and changes the state of disc
	 */
	public static void discCaptured() {
		xCapturedDisc = Bot.getGPS().getRawX();
		yCapturedDisc = Bot.getGPS().getRawY();	
		double radius, distX, distY;
		for (int i = 0; i < numberOfDiscs; i++)  {
			//distance X between the disc's coordinate and Yeti coordinates when it caught it
			distX = palets[i].getXdisc() - xCapturedDisc;
			//distance Y between the disc's coordinate and Yeti coordinates when it caught it
			distY = palets[i].getYdisc() - yCapturedDisc;
			//Tolerance zone
			radius = Math.sqrt(distX*distX + distY*distY);
			if ( radius <= 420 ) 
				palets[i].setCaptured(true);
		}
	}
	
}
