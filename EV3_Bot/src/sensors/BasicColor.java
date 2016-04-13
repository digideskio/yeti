package sensors;


/**
 * Enum of colors present in the environment
 */
public enum BasicColor {
	None("None"), // None if we couldn't detect a color
	Black("Black"),
	Gray("Gray"),
	White("White"),
	Red("Red"),
	Blue("Blue"),
	Green("Green"),
	Yellow("Yellow");
	
	private String name;
	
	private BasicColor(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	/**
	 * Returns an approximate HSV value for this color
	 */
	public ColorHSV toHSV() {
		/// TODO: Get reference values from the test environment instead of guessing here
		
		switch(this) {
		case White:
			return new ColorHSV(0,0,50);
		case Black:
			return new ColorHSV(0,15,2);
		case Gray:
			return new ColorHSV(0,10,15);
		case Red:
			return new ColorHSV(0,85,25);
		case Blue:
			return new ColorHSV(200,70,20);
		case Green:
			return new ColorHSV(110,65,20);
		case Yellow:
			return new ColorHSV(45,77,40);
		default:
			return new ColorHSV(0,0,0);
		}
	}
	
	/**
	 * Returns true if this is a foreground line color of the terrain
	 */
	public boolean isForeground() {
		switch(this) {
		case Gray:
		case None:
			return false;
		default:
			return true;
		}
	}
}
