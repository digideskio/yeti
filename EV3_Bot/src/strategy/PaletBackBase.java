package strategy;

import lejos.ev3.tools.SensorPanel;
import lejos.robotics.Color;
import lejos.utility.Delay;
import main.Bot;
import motor.BasicMotion;
import motor.StraightMotion;

public class PaletBackBase implements Tactic{
	
	SensorPanel sensors;
	int pauseTime=5000;
	boolean pause=false;
	boolean pinceClosed=false;

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
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

	/** Taking the first palet and bringin it to the camp
	 * 
	 */
	public void takeFirstPalet(){
		// methode avancer ??
		BasicMotion.setSpeed(20);
		BasicMotion.moveBy(10); // test avancer?	
		BasicMotion.openClaw();
		getPalet();
		BasicMotion.closeClaw();
		BasicMotion.rotate(90);
		BasicMotion.moveBy(50); // methode avancer ?
		BasicMotion.rotate(-90);
		boolean cond = true;
		putPalet(true);
		
		
	}
	
	/** The bot go on till he finds a palet
	 **/
	public void getPalet(){
		while(!Bot.getSensorsCache().isButtonPressed() && sensors.getHSV() != Color.WHITE){
			pause();
			BasicMotion.moveBy(50);
		}
	}
	/** The bot check if he needs to stop if there's some obstacle in front
	 **/
	public void pause(){
		if(pause){
			//BasicMotion.moveBy(10).stop();  methode stop marche pas
			System.out.println("i'm waiting");
			Delay.msDelay(pauseTime);
			System.out.println(" I ended up waiting");		
			pause=false;
		}
	}
	/** this methode that allows to put the palet in our camp
	 * 
	 * 
	 */
	public void putPalet(boolean cond){
		if(cond){
			BasicMotion.moveBy(10);
			BasicMotion.openClaw();
			BasicMotion.moveBy(-10);
			if(pinceClosed) BasicMotion.closeClaw();
		}
		else {
			BasicMotion.moveBy(1);
		}
	}
	@Override
	public boolean perform() {
		// TODO Auto-generated method stub
		takeFirstPalet();
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
