package gps;

import sensors.BasicColor;

/**
 * Keeps track of the current zone/line of the terrain
 * we think we're in by looking at lines crossed
 * TODO: Keep better track of world coordinates (rawx,rawy)
 * 		 In particular we need to know the size of a terrain cell
 * 		 and assume that we start in the middle of a cell, not at 0,0
 */
public class PositionTracker {
	static final double DEG2RAD = (2*Math.PI)/360.;
	// Defines the vertical and horizontal lines of the terrain
	static final BasicColor[] hlines = {BasicColor.White, BasicColor.Green, BasicColor.Black, 
							 			BasicColor.Blue, BasicColor.White},
							 vlines = {BasicColor.Yellow, BasicColor.Black, BasicColor.Red};
	Orientation orientation;
	int rawx, rawy; // World coordinates we believe we're close to
	int x, y; // Current zone we believe we're in
	boolean isGreen; // True if we started on the green side
	BasicColor followedLine; // None if we're not currently following a line
	// If true, we throw instead of correcting our position when getting inconsistent input
	static final boolean throwWhenLost = false;
	
	/**
	 * (x,y) must be a valid coordinate of a start point,
	 * in either the green or blue start zone.
	 */
	public PositionTracker(int x, int y) {
		this.rawx = x;
		this.rawy = y;
		isGreen = (rawy>=1280*2);
		orientation = new Orientation(isGreen);
		followedLine = BasicColor.None;
	}
	
	/**
	 * Returns our current orientation
	 * Callers may modify the orientation directly as long
	 * as they keep the state consistent
	 */
	public Orientation getOrientation() {
		return orientation;
	}
	
	public int getRawX() {
		return rawx;
	}
	
	public int getRawY() {
		return rawy;
	}
	
	public boolean isGreen() {
		return isGreen;
	}

	public void setGreen(boolean isGreen) {
		this.isGreen = isGreen;
	}

	/**
	 * Call when the robot moved by distance units in the current direction  
	 */
	public void movedBy(int distance) {
		rawy += Math.sin(orientation.getAngle() * DEG2RAD) * distance;
		rawx += Math.cos(orientation.getAngle() * DEG2RAD) * distance;
	}
	
	/**
	 * Call when the robot rotated by deg degrees
	 */
	public void rotatedBy(int deg) {
		orientation.rotate(deg);
	}

	/**
	 * Get a printable description of our current position
	 */
	public String getPosDescription() {
		String pos = String.valueOf((char)('A'+x)) + String.valueOf((char)('0'+y));
		if (followedLine != BasicColor.None)
			pos += '/'+followedLine.toString();
		return pos;
	}
	
	/**
	 * Gets a printable raw x/y position
	 */
	public String getRawPosString() {
		return ""+rawx+";"+rawy;
	}
	
	/**
	 * Call when we cross a line so that we can update our position
	 * horizontal is true if we believe that we crossed an hozirontal line,
	 * false if we believe it was a vertical line
	 */
	@SuppressWarnings("incomplete-switch")
	public void crossLine(BasicColor color) {
		boolean horizontal;
		if (orientation.getAngle() > 45 && orientation.getAngle() < 135
				|| orientation.getAngle() > 225 && orientation.getAngle() < 315 ) {
			horizontal = false;
		} else {
			horizontal = true;
		}
		switch(color) {
		case Black :
			if (horizontal)
				rawy = 1240 + 1248;
			else
				rawx = 1053 + 1049;
			return;
		}
		
		if (horizontal) {
			switch(color) {
			case White:
				if (rawy < 1240)
					rawy = 0;
				else
					rawy = 1240 + 1248 + 1233 + 1265;
				return;
			case Green:
				rawy = 1240 + 1248 + 1233;
				return;
			case Blue:
				rawy = 1240;
				return;
			}
		} else {
			switch(color) {
			case Yellow:
				rawx = 1053 + 1049 + 1041;
				return;
			case Red:
				rawx = 1053;
				return;
			}
		}
		
		throw new IllegalArgumentException("This line doesn't exist");
	}

}
