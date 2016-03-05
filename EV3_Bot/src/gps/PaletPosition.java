package gps;

import main.Bot;

/** 
 * Gives all free nearest palets
 */

public class PaletPosition {

	private Palet[] palets;		//contains all discs 
	private static int numberOfDiscs;
	
	int goToX;	
	int goToY;
	
	static int xCapturedDisc = -1;
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

	public boolean nearestPalet() {	
		upDateCapturedDiscs();
		double distMin = 1000000;		//mettre la plus grande distance + 1 ici 	
		int ligne = -1, column = -1;		
		for (int i = 0; i < numberOfDiscs; i++) {
				int compX = palets[i].getXdisc() - Bot.getGPS().getRawX();
				int compY = palets[i].getYdisc() - Bot.getGPS().getRawY();
				double dist = Math.sqrt(compX*compX + compY*compY);
				if (dist < distMin && palets[i].isCaptured() == false) {
					distMin = dist;
					ligne = palets[i].getXdisc();
					column = palets[i].getYdisc();
				}	
		}	
		this.goToX = ligne;
		this.goToY = column;

		return true;	
	}
	
	public static void position() {
		xCapturedDisc = Bot.getGPS().getRawX();
		yCapturedDisc = Bot.getGPS().getRawY();	
	}
	
	public void upDateCapturedDiscs() {		// /!\ GERER LES MARGES D'ERREUR ICI !!!!!!! Egalites fausses
		for (int i = 0; i < numberOfDiscs; i++) 
			if ( palets[i].getXdisc() == xCapturedDisc && 
					palets[i].getYdisc() == yCapturedDisc) 
				palets[i].setCaptured(true);				
	}	
}
