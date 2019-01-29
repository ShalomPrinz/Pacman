package pacman;

public class Wall extends Creature{

	@Override
	Type getType() {
		return Type.WALL;
	}

	// Wall class design pattern: Singleton
	
	private static Wall wall = null;
	
	public Wall() {	};
	
	public static Wall getInstance() {
		if (wall == null)
			wall = new Wall();
		
		return wall;
	}
}
