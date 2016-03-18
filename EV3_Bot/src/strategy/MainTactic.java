package strategy;

import sensors.BasicColor;

public class MainTactic implements Tactic {
	PincerTactic pincerTactic;
	Tactic mainTactic;
	PaletBackBase goBackTactic;
	private boolean paletCaptured;
	
	MainTactic() {
		pincerTactic = new PincerTactic();
		mainTactic = new MoveToTactic(3*1050, 3*1280);
		goBackTactic = new PaletBackBase();
		paletCaptured = false;
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
		} else {
			paletCaptured = !goBackTactic.perform();
			if (!paletCaptured)
				mainTactic = new NullTactic();
		}
		return false;
	}

	@Override
	public void abort() {
	}

	@Override
	public void stop() {
		mainTactic.stop();
		mainTactic.perform();
	}

}
