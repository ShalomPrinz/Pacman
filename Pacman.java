package pacman;

public class Pacman extends MovingCreature {
	
	@Override
	public Type getType() {
		return Type.PACMAN;
	}

	@Override
	void move(Game game) {
		
		changeDirection(game, this.getNextDirection(), this);
		Location nextLocation = game.getNextLocation(this.getLocation(), this.getDirection());
		Creature nextCreature = game.getCreatureAt(nextLocation);
		
		switch ( nextCreature.getType() ){
			case POINT:
			case NULL:
			case BIG_POINT:
				game.set(this);
				break;
				
			case GHOST:
				game.ghostMeetPacman( this, (Ghost) nextCreature, true );
				break;
				
			default:
				break;
		}
	}

}
