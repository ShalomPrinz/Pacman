package pacman;

import pacman.Game.Direction;

public class SmartGhost extends Ghost{
	
	private int horizonalSpeed = 0;
	private int verticalSpeed = 0;
	private static final int SPD = 1;
	Direction forbiddenDirection;
	
	@Override
	void move(Game game) {
		
		forbiddenDirection = getOppositeDirection( getDirection() );

		Location nextLocation = game.getNextLocation(getLocation(), getDirection());
		Creature nextCreature = game.getCreatureAt(nextLocation);

		if (nextCreature.getType() == Type.WALL)
			reverseSpeeds();

		// if (ghost snaps to grid)
		changeDirection(game);

		nextLocation = game.getNextLocation(getLocation(), getDirection());
		nextCreature = game.getCreatureAt(nextLocation);

		for (int i = 0; i < 30 && (nextCreature.getType() == Type.WALL ||  nextCreature.getType() == Type.REVIVOR); i++) {
            changeDirection(game);
            nextLocation = game.getNextLocation(getLocation(), getDirection());
            nextCreature = game.getCreatureAt(nextLocation);
        }

		if (nextCreature.getType() == Type.WALL){

		}

        if (nextCreature.getType() == Type.WALL ||  nextCreature.getType() == Type.REVIVOR)
        	setDirection(forbiddenDirection);

		if (nextCreature.getType() == Type.PACMAN)
			game.ghostMeetPacman( (Pacman) nextCreature, this, false);
		else
			game.set(this);	
	}
	
	public void changeDirection(Game game) {

		Direction forbiddenDirection = getOppositeDirection( getDirection() ), check;
		
		setSpeeds( getDirection() );
		
		if (horizonalSpeed == 0) {
			
			check = Direction.LEFT;
			if ( chance() && !isThereCollisionsThisWay(game, check) && check != forbiddenDirection) {
				horizonalSpeed = -SPD;
				verticalSpeed = 0;
			}
			
			check = Direction.RIGHT;
			if ( chance() && !isThereCollisionsThisWay(game, check) && check != forbiddenDirection ) {
				horizonalSpeed = SPD;
				verticalSpeed = 0;
			}	
		}
		else {
			
			check = Direction.DOWN;
			if ( chance() && !isThereCollisionsThisWay(game, check) && check != forbiddenDirection ) {
				horizonalSpeed = 0;
				verticalSpeed = -SPD;
			}
			
			check = Direction.UP;
			if ( chance() && !isThereCollisionsThisWay(game, check) && check != forbiddenDirection ) {
				horizonalSpeed = 0;
				verticalSpeed = SPD;
			}
		}
		
		// if no direction chosen
		if (horizonalSpeed == 0 && verticalSpeed == 0) 
			return;
		
		Direction d = getDirection(horizonalSpeed, verticalSpeed);
		setDirection(d);
	}
	
	private Direction getDirection(int hs, int vs) {
		if (hs == 0) 
			return vs == SPD ? Direction.UP : Direction.DOWN;
		
		return hs == SPD ? Direction.RIGHT : Direction.LEFT;
	}
	
	private void setSpeeds(Direction direction) {
		switch (direction) {
		case DOWN:
			horizonalSpeed = 0;
			verticalSpeed = -SPD;
		case RIGHT:
			horizonalSpeed = SPD;
			verticalSpeed = 0;
			break;
		case UP:
			horizonalSpeed = 0;
			verticalSpeed = SPD;
			break;
		default:
			horizonalSpeed = -SPD;
			verticalSpeed = 0;
			break;
		}
	}

	public boolean chance() {
		int chance = (int) (Math.random() * 3);
		return chance == 1; 
	}
	
	private boolean isThereCollisionsThisWay(Game game, Direction direction) {
		Location l = getLocation();
		Location next = game.getNextLocation(l, direction);
		Creature c = game.getCreatureAt(next);
		return c.getType() == Type.WALL;		
	}
	
	private void reverseSpeeds() {
		horizonalSpeed = -horizonalSpeed;
		verticalSpeed = -verticalSpeed;
	}
}
