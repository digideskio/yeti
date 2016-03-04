package sensors;

/**
 * Represents a color in HSV format
 * H is between 0-360 and S/V are between 0-100
 * (so that it matches the GIMP color wheel!)
 */
public class ColorHSV {
	private int H, S, V;
	
	public ColorHSV(int H, int S, int V) {
		if (H<0 || H>360
			|| S<0 || S>100
			|| V<0 || V>100)
			throw new IllegalArgumentException("Color out of range");
		
		// For V==0, H and S are meaningless, normalize them to 0
		if (V==0)
			H = S = 0;
		
		this.H = H;
		this.S = S;
		this.V = V;
	}
	
	public int getH() {
		return H;
	}
	
	public int getS() {
		return S;
	}
	
	public int getV() {
		return V;
	}
}
