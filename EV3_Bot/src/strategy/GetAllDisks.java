package strategy;

import gps.PaletPosition;

public class GetAllDisks implements Tactic {
	
	MoveToTactic move;
	PaletPosition disk;
	GoBack back;
	
	public GetAllDisks(int xDepart, int yDepart, int numberOfDisks) {
		this.move = new MoveToTactic(xDepart, yDepart);
		this.disk = new PaletPosition(numberOfDisks);
		this.back = new GoBack();
	}

	@Override
	public String getDisplayName() {
		return "GetAllDisks";
	}

	@Override
	public boolean handleObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleContact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform() {
		
		return false;
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
		
	}

}
