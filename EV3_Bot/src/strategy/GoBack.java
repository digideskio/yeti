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
		
		/**int x = Bot.getGPS().getRawX();
		int y = Bot.getGPS().getRawY();
		if(y>3590 && x < 1080){//half sup of the terrain + 30 
			BasicMotion.moveBy(10);
			int newX = Bot.getGPS().getRawX();
			int newY = Bot.getGPS().getRawY();
			if (newY > y){
				BasicMotion.rotate(180);
			}
			if( newX< 1080*4 && newX>1080*3)
				
			while(y > 3560 ){
				smotion.start(true);
				color = Bot.getSensorsCache().getColor();
				if (color == BasicColor.Black)
					y=3560;
			}
		}
		*/
		return true;
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
