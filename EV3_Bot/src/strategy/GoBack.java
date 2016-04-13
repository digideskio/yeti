package strategy;

import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;
import sensors.BasicColor;

public class GoBack implements Tactic {

	StraightMotion smotion;
	BasicColor color;

	public GoBack() {
		smotion = new StraightMotion();
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "GoBack";
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

	/**
	 * The robot needs to back off before chose another strategy.
	 * 
	 */
	@Override
	public boolean perform() {

		BasicMotion.rotate(180);
		BasicMotion.moveBy(10);
		/**
		 * REVOIR FAIRE DEUX CAS -- si on est soit en a dte soit a gche soit en
		 * haut soit en bas faire ramen� au centre (ligne noir) puis aller
		 * soit a bleu/ vert puis stop
		 */
		// BasicColor[] isBorG = { BasicColor.Blue, BasicColor.Green };
		int x = Bot.getGPS().getRawX(); // Position of Yeti
		float orientation = 0;
		if (x > 1053 + 1049) // if Yeti is in the right part
			orientation = Bot.getGPS().getOrientation().diff(-1, 0);
		else
			orientation = Bot.getGPS().getOrientation().diff(1, 0);
		BasicMotion.rotate((int) orientation);
		smotion.start(true);
		if (Bot.getSensorsCache().getColor() == BasicColor.Black) {
			FollowLine fL = new FollowLine(BasicColor.Black,BasicColor.Blue, true);
			FollowLine fL2 = new FollowLine(BasicColor.Black,BasicColor.Green, true);
			fL.perform();
			if (Bot.getSensorsCache().getColor() == BasicColor.White) {
				BasicMotion.rotate(180);
				fL2.perform();
			}
		}
		return true;
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub

	}
}
