package pacman;

public abstract class Creature {
	
	public enum Type {
		POINT,
		WALL,
		NULL,
		PACMAN,
		GHOST
	}
	
	abstract Type getType();
	
}
