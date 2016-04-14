package strategy;

import java.util.Date;

import sensors.BasicColor;

import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;

import gps.PaletPosition;


/**
 * strategy which permits to catch all discs if Yeti is alone
 * on board and if the discs are on the junctions of color line
 * @author decerle
 *
 */

public class CatchAllDiscs implements Tactic {
	
	PaletPosition disc;
	String name;
	boolean abort;
	private AvoidFoe avoidfoe;
	private GoBack goback;
	private boolean followFromLeft;
	private FindVLine findLine;
	private FollowLine followLine;
	private BasicColor hcolor, vcolor;
	private StraightMotion firstMove;
	private boolean discMissing;

	
	public CatchAllDiscs() {
		this.disc = new PaletPosition();
		abort = false;
		firstMove = new StraightMotion();
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
		name = avoidfoe.getDisplayName();
		return true;
	}

	@Override
	public boolean handleContact() {
		goback = new GoBack();
		name = goback.getDisplayName();
		return true;
	}

	@Override
	public boolean perform() {
		if (abort == true) {
			if (findLine != null) {
				findLine.abort();
				findLine.perform();
			}
			if (followLine != null) {
				followLine.abort();
				followLine.perform();
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
		
		if (disc.isFreeDiscs()) {
			if (disc.getFreeDiscs() == 9) {
				if (firstMove != null) {
					if (!firstMove.isMoving())
						firstMove.start(true);
					firstMove.updateGPS();
					name = "CAD_first";
				}
			} else if (discMissing) {
				PaletPosition.discCaptured();
				BasicMotion.moveBy(-500);
				discMissing = false;
			} else if (followLine != null) {
				if (followLine.perform()) {
					followLine = null;
					findLine = null;
					name = null;
					discMissing = true;
				}
			} else if (findLine != null) {
				if (findLine.perform()) {
					followLine = new FollowLine(vcolor, hcolor, followFromLeft);
					name = followLine.getDisplayName();
					BasicMotion.moveBy(100);
					if (followFromLeft) {
						BasicMotion.rotate(80);
						Bot.getGPS().rotatedBy(10);
					} else {
						BasicMotion.rotate(-80);
						Bot.getGPS().rotatedBy(-10);
					}
				}
			} else {
				this.disc.nearestLine();				
				followFromLeft = !((Bot.getGPS().getRawX() < disc.getGoToX())
										^ (Bot.getGPS().getRawY() < disc.getGoToY()));
			
				if (disc.getGoToY() == 1240 + 1248 + 1233)
					hcolor = BasicColor.Green;
				else if (disc.getGoToY() == 1240 + 1248)
					hcolor = BasicColor.Black;
				else if (disc.getGoToY() == 1240)
					hcolor = BasicColor.Blue;
				else
					throw new IllegalArgumentException("Invalid Y position");
				
				if (disc.getGoToX() == 1053 + 1049 + 1041)
					vcolor = BasicColor.Yellow;
				else if (disc.getGoToX() == 1053 + 1049)
					vcolor = BasicColor.Black;
				else if (disc.getGoToX() == 1053)
					vcolor = BasicColor.Red;
				else
					throw new IllegalArgumentException("Invalid X position");
				
				findLine = new FindVLine(vcolor);
				name = findLine.getDisplayName();
			}

			return false;
		} else {
			return true;
		}	
	}

	@Override
	public void abort() {
		abort = true;
		if (firstMove != null) {
			firstMove.stop();
			firstMove = null;
		}
		if (findLine != null) {
			findLine.abort();
			findLine = null;
		}
		if (followLine != null) {
			followLine.abort();
			followLine = null;
		}
	}
}