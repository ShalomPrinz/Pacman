package pacman;

public class Point extends Creature implements ScoreIncrement{

	private final static int VALUE = 10;
	
	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	Type getType() {
		return Type.POINT;
	}

}
