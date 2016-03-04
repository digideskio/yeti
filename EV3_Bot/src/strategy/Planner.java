package strategy;

import sensors.BasicColor;

/**
 * Decides what tactics to follow to maximize our performance React to immediate
 * sensory inputs, deviating from the current tactics as needed
 */
public class Planner {
	Tactic currentTactic;

	public Planner() {
		/// TODO: Replace the default by a real tactic
		/// First a GUI that asks for the start coordinate, then a real tactic
		currentTactic = new FollowLine(BasicColor.Red, BasicColor.Green);
	}

	/**
	 * Call when an obstacle is detected in front of us and we need to adapt our
	 * immediate tactics to avoid it
	 */
	public void handleObstacle() {
		currentTactic.handleObstacle();
	}

	/**
	 * Call if we ran into a close obstacle and we need to back-off immediately
	 * from it
	 */
	public void handleContact() {
		currentTactic.handleContact();
	}

	/**
	 * Call periodically to actually perform our tactics
	 */
	public void performTactics() {
		if (currentTactic.perform()) {
			currentTactic = new NullTactic();
		}
	}

	public String getCurrentTacticName() {
		return currentTactic.getDisplayName();
	}
}
