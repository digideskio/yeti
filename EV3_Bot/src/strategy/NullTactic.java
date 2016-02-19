package strategy;

/**
 * A tactic that consists of doing nothing at all and throwing away all inputs
 */
public class NullTactic implements Tactic {
	private boolean stopped;
	
	NullTactic() {
		stopped = false;
	}
	
	@Override
	public String getDisplayName() {
		return "Nothing";
	}
	
	@Override
	public boolean handleObstacle() {
		return true;
	}

	@Override
	public boolean handleContact() {
		return true;
	}

	@Override
	public boolean perform() {
		return stopped;
	}

	@Override
	public void abort() {
		stopped = true;
	}

	@Override
	public void stop() {
		stopped = true;
	}
}
