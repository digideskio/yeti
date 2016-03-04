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
		if (x<0 || x>3 || (y!=0 && y!=5))
			throw new IllegalArgumentException("Invalid start coordinates");
		
		this.x = x;
		this.y = y;
		isGreen = (y==0);
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
	public void crossLine(BasicColor color, boolean horizontal) {	
		switch(color) {
		case Black:
			if (horizontal)
				crossHLine(2);
			else
				crossVLine(1);
			return;
		}
		
		if (horizontal) {
			switch(color) {
			case White:
				if (y <= 2)
					crossHLine(0);
				else
					crossHLine(4);
				return;
			case Green:
				crossHLine(1);
				return;
			case Blue:
				crossHLine(3);
				return;
			}
		} else {
			switch(color) {
			case Yellow:
				crossVLine(0);
				return;
			case Red:
				crossVLine(2);
				return;
			}
		}
		
		throw new IllegalArgumentException("This line doesn't exist");
	}
	
	/**
	 * Call when we cross a line so that we can update our position
	 * @param linex An index in hlines
	 */
	public void crossHLine(int linex) {
		if (y == linex) {
			y++;
		} else if (x == linex+1) {
			y--;
		} else {
			// We're lost. Assume X didn't change, but that we had a jump in Y
			if (throwWhenLost)
				throw new IllegalArgumentException("We're lost (Err:H/"+linex+'/'+y+')');
			y = linex + (y < linex ? 1 : 0);
		}
	}
	
	/**
	 * Call when we cross a line so that we can update our position
	 * @param liney An index in vlines
	 */
	public void crossVLine(int liney) {
		if (x == liney) {
			x++;
		} else if (x == liney+1) {
			x--;
		} else {
			// We're lost. Assume Y didn't change, but that we had a jump in X
			if (throwWhenLost)
				throw new IllegalArgumentException("We're lost (Err:V/"+liney+'/'+x+')');
			x = liney + (x < liney ? 1 : 0);
		}
	}
	
	/**
	 * Call when we start or stop following a line
	 * @param horizontal True if we think we're following an horizontal line
	 */
	public void followLine(BasicColor line, boolean horizontal) {
		this.followedLine = line;
		// If the line isn't adjacent to where we think we are, we're lost
		if (horizontal) {
			if ((y==0 || hlines[y-1]!=line)
				&& (y==5 || hlines[y]!=line)) {
				if (throwWhenLost)
					throw new IllegalArgumentException("We're lost (Err:FH/"+x+'/'+y+'/'+line+')');
				
				// Try to correct our position based on the line we're following
				int match = -1;
				for (int i=0; i<hlines.length; ++i)
					if (hlines[i] == line)
						match = i;
				if (match != -1)
					y = match + (y<match? 0 : 1);
			}
		} else {
			if ((x==0 || vlines[x-1]!=line)
				&& (x==3 || vlines[x]!=line)) {
				if (throwWhenLost)
					throw new IllegalArgumentException("We're lost (Err:FV/"+x+'/'+y+'/'+line+')');
				
				// Try to correct our position based on the line we're following
				int match = -1;
				for (int i=0; i<vlines.length; ++i)
					if (vlines[i] == line)
						match = i;
				if (match != -1)
					x = match + (x<match? 0 : 1);
			}
		}
	}
}
