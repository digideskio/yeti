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
	
	public CatchAllDiscs() {
		this.disc = new PaletPosition();
	}

	@Override
	public String getDisplayName() {
		return "CatchAllDiscs";
	}

	@Override
	public boolean handleObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleContact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform() {
		//this variable permits to consider the first disc to catch 
		//because the first moving is different that others
		int nbTour = PaletPosition.getNumberOfDiscs();
		if (nbTour > 0 && disc.isFreeDiscs()) {		
			if (this.move == null ) {
				this.disc.nearestPalet();
			
				this.move = new MoveToTactic(disc.getGoToX(),disc.getGoToY());
			}
			if (move.perform() == true)
				move = null;
			else
				return false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
		
	}

}