package pacman;

public abstract class Creature {
	
	public enum Type {
		PACMAN,
		GHOST,
		POINT,
		BIG_POINT,
		NULL,
		WALL
	}
	
	abstract Type getType();
	
}
