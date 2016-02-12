package gps;

/**
 * Represents the different lines on the terrain,
 * so we know what we're following and where we are.
 * A TerrainZone tells us on what side of a line we are.
 */
public enum TerrainLine {
	None,
	StartWhite,
	Green,
	HBlack,
	Blue,
	GoalWhite,
	Yellow,
	VBlack,
	Red,
}
