package strategy;

import sensors.BasicColor;

public class MainTactic implements Tactic {
	PincerTactic pincerTactic;
	Tactic mainTactic;
	PaletBackBase goBackTactic;
	private boolean paletCaptured;
	
	MainTactic() {
		pincerTactic = new PincerTactic();
		mainTactic = new PaletBackBase();
		goBackTactic = new PaletBackBase();
		paletCaptured=false;
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
		if(!paletCaptured){
			paletCaptured = pincerTactic.perform();	
			mainTactic.perform();
		}
		else {
			paletCaptured = !goBackTactic.perform();
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
