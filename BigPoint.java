package pacman;

public class BigPoint extends Creature implements ScoreIncrement{

	private final static int VALUE = 50;
	
	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	Type getType() {
		return Type.BIG_POINT;
	}

}