package strategy;

import sensors.BasicColor;



public class MainTactic implements Tactic {
	PincerTactic pincerTactic;
	Tactic mainTactic;
	PaletBackBase goBackTactic;
	CrossLineTactic crossLine;
	private boolean paletCaptured;
	
	MainTactic() {
		pincerTactic = new PincerTactic();
		//mainTactic = new CatchAllDiscs();
		mainTactic = new FollowLine(BasicColor.Red, BasicColor.Blue, false);
		crossLine = new CrossLineTactic();
		paletCaptured = false;
	}
	
	@Override
	public String getDisplayName() {
		if (paletCaptured && goBackTactic != null)
			return goBackTactic.getDisplayName();
		else
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
			mainTactic.perform();
			paletCaptured = pincerTactic.perform();
			crossLine.perform();
			
		} else {
			if (goBackTactic == null)
				goBackTactic = new PaletBackBase();
			paletCaptured = !goBackTactic.perform();
			if (!paletCaptured) {
				mainTactic = new CatchAllDiscs();
				goBackTactic = null;
			}
		}
		return false;
	}

	@Override
	public void abort() {
		mainTactic.abort();
		mainTactic.perform();
	}
}
