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
				// score ++
			case NULL:
				game.set(this);
				break;
				
			case GHOST:
				game.stopGame();
				break;
				
			default:
				break;
		}
	}

}
