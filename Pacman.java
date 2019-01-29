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
			case POINT:
				game.setScore( game.getScore() + Game.POINT_SCORE );
			case NULL:
				game.set( currentLocation, nextLocation, this );
				break;
			case GHOST:
				game.pacmanDead();
			default:
				break;
		}
		
	}

}
