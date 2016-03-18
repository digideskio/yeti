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
	
	public CatchAllDiscs(int xDepart, int yDepart, int numberOfDiscs) {
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
		int nbTour = 0;
		if (PaletPosition.getNumberOfDiscs() > 0 && disc.nearestPalet()) {
			this.move = new MoveToTactic(disc.getGoToX(),disc.getGoToY());
			this.pincer.perform();
			if (nbTour == 0) {
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
				}
			} else {
				this.back.perform();
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