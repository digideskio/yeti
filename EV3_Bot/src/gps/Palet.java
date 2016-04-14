package gps;

import sensors.BasicColor;

public class Palet {
	
	private int xdisc;		//coordinate X of disc
	private int ydisc;		//coordinate Y of disc
	boolean isCaptured;
	private BasicColor colorVDisc;	//color vertical
	//private BasicColor colorHDisc;  //color horizontal
	
	
	Palet(int x, int y, BasicColor colorVDisc/*, BasicColor colorHDisc*/) {
		this.xdisc = x;
		this.ydisc = y;
		this.colorVDisc = colorVDisc;
		this.isCaptured = false;
	}
	
	

	public BasicColor getColorVDisc() {
		return colorVDisc;
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
