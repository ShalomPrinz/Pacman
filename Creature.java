package pacman;

public abstract class Creature {
	
	public enum Type {
		POINT,
		BIG_POINT,
		WALL,
		NULL,
		PACMAN,
		GHOST,
		REVIVOR
	}
	
	abstract Type getType();
	
	// abstract (picture) getPicture();
}
