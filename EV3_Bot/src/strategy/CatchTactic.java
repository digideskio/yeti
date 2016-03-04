package strategy;


/**
 * Permits 
 */

public class CatchTactic implements Tactic {
	
	FollowLine followLine;
	PaletBackBase paletBackBase;
	AvoidFoe avoidFoe;
	private int numberOfPalets;
	
	CatchTactic(int numberOfPalets) {
		this.numberOfPalets = numberOfPalets;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "CatchPalet";
	}

	@Override
	public boolean handleObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleContact() {
		avoidFoe = new AvoidFoe();
		return true;
	}

	@Override
	public boolean perform() {
		if (numberOfPalets >= 0) {
			
		}
		return true;
	}

	public int getNumberOfPalets() {
		return numberOfPalets;
	}

	public void setNumberOfPalets(int numberOfPalets) {
		this.numberOfPalets = numberOfPalets;
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
