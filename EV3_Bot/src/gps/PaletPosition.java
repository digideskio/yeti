package gps;

import main.Bot;

/** 
 * Gives all free nearest palets
 */

public class PaletPosition {

	private int nbLigne;
	private int coordX;
	private int coordY;
	private Palet[] palets;
	
	int goToX;
	int goToY;
	
	static int xPalet = -1;
	static int yPalet = -1;
	
	
	PaletPosition(int nbLigne) {
		this.palets = new Palet[nbLigne];
	}

	public int getGoToX() {
		return goToX;
	}

	public int getGoToY() {
		return goToY;
	}
	
	public int getxPalet() {
		return xPalet;
	}

	public int getyPalet() {
		return yPalet;
	}

	public boolean nearestPalet() {		
		int distMin = -1;
		int ligne = -1, column = -1;		
		for (int l = 0; l < nbLigne; l++) {
			for (int c = 0; c < nbLigne ; c++) {
				int dist = Math.max(Math.abs(l - coordX), Math.abs(c - coordY));
				if (dist < distMin) {
					distMin = dist;
					ligne = l;
					column = c;
				}	
			}
		}	
		this.goToX = ligne;
		this.goToY = column;

		return true;	
	}
	
	public static void position() {
		xPalet = Bot.getGPS().getRawX();
		yPalet = Bot.getGPS().getRawY();
		
	}
	
	
}
