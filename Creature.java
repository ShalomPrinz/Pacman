package pacman;

public abstract class Creature {
	
	public enum Type {
		PACMAN,
		GHOST,
		POINT,
		NULL,
		WALL
	}
	
	abstract Type getType();
	
}
