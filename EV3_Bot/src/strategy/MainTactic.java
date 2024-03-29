package strategy;

public class MainTactic implements Tactic {
	PincerTactic pincerTactic;
	Tactic mainTactic;
	PaletBackBase goBackTactic;
	CrossLineTactic crossLine;
	private boolean paletCaptured;

	MainTactic() {
		pincerTactic = new PincerTactic();
		mainTactic = new CatchAllDiscs();
		// mainTactic = new FollowLine(BasicColor.Yellow, BasicColor.Green,
		// true);
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
		return mainTactic.handleObstacle();
	}

	@Override
	public boolean handleContact() {
		return mainTactic.handleContact();
	}

	@Override
	public boolean perform() {

		if (!paletCaptured) {
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
