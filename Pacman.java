package pacman;

public class Pacman extends MovingCreature {
	
	@Override
	public Type getType() {
		return Type.PACMAN;
	}

	@Override
	void move(Game game) {
		
		game.changeDirection(this.getNextDirection(), this);
		
		Location currentLocation = this.getLocation();
		Location nextLocation = game.getNextLocation(this, this.getDirection());
		
		switch ( game.getCreatureAt(nextLocation).getType() ){
			case POINT:
				// score ++
			case NULL:
				game.set( currentLocation, nextLocation, this );
				break;
				
			case GHOST:
				game.stopGame();
				break;
				
			default:
				break;
		}
	}

}
