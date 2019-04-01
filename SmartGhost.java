package pacman;

import java.util.Vector;

import pacman.Game.Direction;

public class SmartGhost extends Ghost{

	private final static int
		CONTINUE = 4,
		SIDE = 2,
		BACK = 0;
	
	private Direction current, opposite;
	
	private Vector<Direction> possible;
	
	@Override
	void move(Game game) {
		
		current = getDirection();
		opposite = getOppositeDirection(current);
		
		possible = new Vector<Game.Direction>(0, 1);
		
		for (Direction d : findNewPath(game)) {
			
			int chance = (current == d) ? CONTINUE :
						(opposite == d) ? BACK : SIDE;
			
			for (int i = 0; i < chance; i++)
				possible.add(d);
				
		}
		
		int random = (int) (Math.random() * possible.size());
		
		setDirection( possible.get(random) );
	}
	
}
