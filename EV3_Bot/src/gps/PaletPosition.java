package gps;

import main.Bot;

/** 
 * Gives all free nearest discs and changes disc's state if the disc is captured
 */

public class PaletPosition {

	private static Palet[] palets;		//contains all discs with their state : free or captured
	private static int numberOfDiscs;
	
	int goToX;							//coordinates of the nearest discs found by Yeti
	int goToY;	
	
	static int xCapturedDisc = -1;		//coordinates of the captured disc
	static int yCapturedDisc = -1;
	
	
	public PaletPosition(int numberOfDiscs) {
		int height = 1260;	//height of a square on the board we have to verify if all squares have the same size
		int width = 1050;	//width of a square
		PaletPosition.palets = new Palet[numberOfDiscs];
		//
		PaletPosition.palets[0] = new Palet(width*1,height*1);
		PaletPosition.palets[1] = new Palet(width*1,height*2);
		PaletPosition.palets[2] = new Palet(width*1,height*3);
		PaletPosition.palets[3] = new Palet(width*2,height*1);
		PaletPosition.palets[4] = new Palet(width*2,height*2);
		PaletPosition.palets[5] = new Palet(width*2,height*3);
		PaletPosition.palets[6] = new Palet(width*3,height*1);
		PaletPosition.palets[7] = new Palet(width*3,height*2);
		PaletPosition.palets[8] = new Palet(width*3,height*3);
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
			//calculates the shortest distance between Yeti and all discs with the absolute value
				int compX = palets[i].getXdisc() - Bot.getGPS().getRawX();
				int compY = palets[i].getYdisc() - Bot.getGPS().getRawY();
				double dist = Math.sqrt(compX*compX + compY*compY);
				//updates distMin
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
	 * Gets Yeti's coordinates when the robot caught a disc
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
			if ( radius <= 420 ) {
				palets[i].setCaptured(true);
				break;
			}
		}
	}

	public static int getNumberOfDiscs() {
		return numberOfDiscs;
	}
	
}
