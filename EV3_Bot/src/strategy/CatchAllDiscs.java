package strategy;

import java.util.Date;

import gps.PaletPosition;


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
	private Date first;

	
	public CatchAllDiscs() {
		this.disc = new PaletPosition();
		abort = false;
		first = null;
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
		if (avoidfoe != null) {
			boolean result = avoidfoe.perform();
			if (result)
				avoidfoe = null;
		}
		if (goback != null) {
			boolean res = goback.perform();
			if (res)
				goback = null;
		}
		if (first != null && new Date().getTime() - first.getTime() > 500) {
			PaletPosition.discCaptured();
			first = null;
			return true;
		}	
		if (disc.isFreeDiscs()) {		
			if (this.move == null ) {
				this.disc.nearestPalet();
				this.move = new MoveToTactic(disc.getGoToX(),disc.getGoToY());
				name = move.getDisplayName();
			}
			if (move.perform() == true) {
				first = new Date();
				move = null;
			} else {
				return false;	
			}
			name = null;
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