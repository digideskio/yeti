package strategy;

import gps.PaletPosition;
import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import sensors.BasicColor;

/**
 * strategy which permits to catch all discs if Yeti is alone
 * on board and if the discs are on the junctions of color line
 * @author decerle
 *
 */

public class CatchAllDiscs implements Tactic {
	
	MoveToTactic move;
	PaletPosition disc;
	String name;
	boolean abort;
	private AvoidFoe avoidfoe;
	private GoBack goback;

	
	public CatchAllDiscs() {
		this.disc = new PaletPosition();
		abort = false;
	}

	@Override
	public String getDisplayName() {
		if (name != null)
			return name;
		return "CatchAllDiscs";
	}
	@Override
	public boolean handleObstacle() {
		avoidfoe = new AvoidFoe();
		return true;
	}

	@Override
	public boolean handleContact() {
		goback = new GoBack();
		return true;
	}

	@Override
	public boolean perform() {
		if (abort == true) {
			if (move != null) {
				move.abort();
				move.perform();
			}
			return true;
		}
		
		//this variable permits to consider the first disc to catch 
		//because the first moving is different that others
		int nbTour = PaletPosition.getNumberOfDiscs();
		if (nbTour > 0 && disc.isFreeDiscs()) {		
			if (this.move == null ) {
				this.disc.nearestPalet();
				this.move = new MoveToTactic(disc.getGoToX(),disc.getGoToY());
				name = move.getDisplayName();
			}
			if (move.perform() == true)
				move = null;
			else
				return false;
			
			name = null;
			/*
			//just for the first disc
			if (nbTour == PaletPosition.numberOfFreeDiscs()) {
				//avoids discs which follow the first caught disc
				BasicMotion.rotate(90);
				BasicMotion.moveBy(180 * 4);
				BasicMotion.rotate(-90);
			}
			*/
			return true;
		} else {
			return true;
		}
		
	}

	@Override
	public void abort() {
		abort = true;
	}

	@Override
	public void stop() {
		abort = true;
	}

}