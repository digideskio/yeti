package strategy;

import sensors.BasicColor;

public class MainTactic implements Tactic {
	PincerTactic pincerTactic;
	Tactic mainTactic;
	
	MainTactic() {
		pincerTactic = new PincerTactic();
		mainTactic = new FollowLine(BasicColor.Red, BasicColor.Green);
	}
	
	@Override
	public String getDisplayName() {
		return mainTactic.getDisplayName();
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
		pincerTactic.perform();
		return mainTactic.perform();
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
