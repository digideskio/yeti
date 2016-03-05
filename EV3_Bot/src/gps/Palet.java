package gps;

public class Palet {
	
	private int xdisc;		//coordinate X of disc
	private int ydisc;		//coordinate Y of disc
	boolean isCaptured;
	
	Palet(int x, int y) {
		this.xdisc = x;
		this.ydisc = y;
		this.isCaptured = false;
	}

	public boolean isCaptured() {
		return isCaptured;
	}

	public void setCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public int getXdisc() {
		return xdisc;
	}

	public int getYdisc() {
		return ydisc;
	}
	
	
	
}
