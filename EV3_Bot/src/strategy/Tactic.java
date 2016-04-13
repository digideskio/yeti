package strategy;

/**
 * Implements a way of realizing a goal, possibly by using
 * a range of other sub-tactics
 */
public interface Tactic {

	/**
	 * Returns a human friendly name for the current tactic
	 */
	String getDisplayName();
	
	/**
	 *  Call when an obstacle is detected in front of us and we need to
	 *  adapt our immediate tactics to avoid it.
	 *  Returns true if the tactic successfully handled the event
	 *  and false if the event still needs further handling.
	 */
	boolean handleObstacle();
	
	/**
	 * Call if we ran into a close obstacle and we need to
	 * back-off immediately from it.
	 * Returns true if the tactic successfully handled the event
	 * and false if the event still needs further handling.
	 */
	boolean handleContact();

	/**
	 * Call periodically to perform the tactic to completion
	 * Returns true if the tactic is completed (not necessarily successfully),
	 * and returns false if the tactic is still ongoing.
	 */
	boolean perform();
	
	/**
	 * Immediately interrupt the current tactic, leaving the robot in a consistent
	 * (but possibly suboptimal) state, so that the tactic may be switched for another one.
	 * perform() must immediately return false as a result of calling abort().
	 */
	void abort();
}
