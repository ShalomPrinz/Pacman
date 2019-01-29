package pacman;

public class Pacman extends MovingCreature{
	
	@Override
	Type getType() {
		return Type.PACMAN;
	}
	
	@Override
	void move(Game game) {
		
		super.changeDirection( this.getNextDirection(), game );
		Location currentLocation = this.getLocation();
		Location nextLocation = game.getNextLocation(this, this.getDirection());
		
		switch ( game.getCreatureAt(nextLocation).getType() ){
			case BIG_POINT:
				game.activateGhostEating();
			case POINT:
				game.setScore( game.getScore() + ( (ScoreIncrement) game.getCreatureAt(nextLocation) ).getValue()  );
			case NULL:
				game.set( currentLocation, nextLocation, this );
				break;
			case GHOST:
				Ghost g = (Ghost) game.getCreatureAt(nextLocation);
				switch ( g.currentMode ) {
					case EATABLE:
						game.set( currentLocation, nextLocation, this );
						break;
					case ALIVE:
						game.pacmanDead();
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
		
	}

}
