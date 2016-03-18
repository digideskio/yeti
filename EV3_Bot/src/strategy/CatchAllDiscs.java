package strategy;

import gps.PaletPosition;
import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import sensors.BasicColor;

public class CatchAllDiscs implements Tactic {
	
	MoveToTactic move;
	PaletPosition disc;
	PaletBackBase back;
	PincerTactic pincer;
	StraightMotion sm;
	
	public CatchAllDiscs(int xDepart, int yDepart) {
		this.disc = new PaletPosition();
		this.back = new PaletBackBase();
		this.pincer = new PincerTactic();
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
		int nbTour = PaletPosition.getNumberOfDiscs();
		if (nbTour > 0 && disc.nearestPalet() && disc.isFreeDiscs()) {
			this.move = new MoveToTactic(disc.getGoToX(),disc.getGoToY());
			this.pincer.perform();
			if (nbTour == PaletPosition.getNumberOfDiscs()) {
				//avoids discs which follow the first caught disc
				BasicMotion.rotate(-90);
				BasicMotion.moveBy(180 * 4);
				BasicMotion.rotate(90);
				if (!sm.isMoving()) {
					sm.start(true);
				}
				if (Bot.getSensorsCache().getColor() == BasicColor.White) {
					sm.stop();
					BasicMotion.openClaw(false);
					BasicMotion.moveBy(-180 * 3);
					BasicMotion.closeClaw(false);
					BasicMotion.rotate(180);
					nbTour--;
				}
			} else {
				this.back.perform();
				nbTour--;
			}			
			return true;
		}
		return false;
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