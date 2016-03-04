package gps;

/**
 * Zones delimited by the lines of the terrain.
 * We take the bottom-left corner on the green side
 * as origin and {A,B,C,D},{0,1,2,3,4,5} are x,y coordinates.
 * Our start zone can be on the green or blue side, behind
 * the respective white lines.
 */
public class TerrainZone {
	private static char[] xunits = {'A', 'B', 'C', 'D'},
					      yunits = {'0', '1', '2', '3', '4', '5'}; 
	private int x, y;
	boolean isGreen; // True if we started on the green side
	
	public TerrainZone(int x, int y) {
		if (x<0 || x>3 || (y!=0 && y!=5))
			throw new IllegalArgumentException("Invalid start coordinates");
		
		this.x = x;
		this.y = y;
		isGreen = (y==0);
	}
	
	public TerrainZone() {
		this(0, 0);
	}
	
	public boolean isUnknown() {
		return x==-1 && y==-1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		if (x<0 || x>3)
			throw new IllegalArgumentException("Invalid coordinate");
		this.x = x;
	}
	
	public void setY(int y) {
		if (y<0 || y>5)
			throw new IllegalArgumentException("Invalid coordinate");
		this.y = y;
	}
	
	public String toString() {
		return "" + xunits[x] + yunits[y];
	}
}
