package pacman;

import pacman.Creature.Type;

public class Game{
	
	private Board topBoard, botBoard;
	
	private int score, lives;
	
	boolean gameOn;
	
	public static final Direction DEFAULT_DIRECTION = Direction.LEFT;
	public static final long GHOST_EATING_PERIOD = 10000; // 10 seconds
		
	public Game(){
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game(final String[] b){
		this.gameOn = false;
		this.score = 0;
		this.lives = 3;
				
		this.topBoard = new Board(b);
		this.topBoard.limitToSpecificCreatures(new Type[]{Type.GHOST, Type.PACMAN});
		
		this.botBoard = new Board(b);
		this.botBoard.limitToSpecificCreatures(new Type[]{Type.WALL, Type.POINT, Type.BIG_POINT, Type.NULL, Type.REVIVOR} );
	
	}	
	
	public enum Direction{
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	public void pacmanDead(){
		
		this.lives --;
		
		if (this.lives > 0) {
			restartBoard();
			return;
		}
		
		this.gameOn = false;
		// add score to highest_score list, with the player name
	}
	
	public Creature getCreatureAt(Location l){
		
		// If the creature is null on the top, it will return the creature on the bottom board
		// Otherwise method returns pacman / ghost from top board
		
		Creature topBoardCreature = this.topBoard.get(l);
		
		if (topBoardCreature.getType() == Type.NULL) 
			return this.botBoard.get(l);
		else
			return topBoardCreature;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getLives() {
		return this.lives;
	}

	public void set(Location current, Location next, MovingCreature creature) {
		topBoard.set( current, new Null() );
		topBoard.set( next, creature );
		if (creature.getType() == Type.PACMAN)
			botBoard.set( next, new Null() );
	}
	
	public void move( MovingCreature[] creatures ){
		
		// If not sending which creatures move, all of them will move
		if (creatures == null)
			creatures = topBoard.getMovingCreatures();
		
		for (MovingCreature c : creatures)
			c.move(this);		
	}
	
	public void activateGhostEating() {
		setModeForAllGhosts( Ghost.Mode.EATABLE );
		
		new Thread( () ->  {
			
			long end = System.currentTimeMillis() + Game.GHOST_EATING_PERIOD; 
			while (System.currentTimeMillis() < end);
			setModeForAllGhosts( Ghost.Mode.ALIVE ); // unless dead (inside method)	
			
		}).start();
	}

	private void setModeForAllGhosts( Ghost.Mode m ) {
		for ( MovingCreature Mc : topBoard.getMovingCreatures() ) {
			if (Mc.getType() == Type.GHOST) {
				Ghost g = (Ghost) Mc;
				if (g.currentMode != Ghost.Mode.DEAD)
					g.currentMode = m;
			}	
		}
	}
	
	public Location getNextLocation(MovingCreature Mc, Direction d) {
		Location nextLocation = changeLocationByDirection(Mc.getLocation().getX(), Mc.getLocation().getY(), d);
		
		int Xdim = topBoard.getDimensions('X'), Ydim = topBoard.getDimensions('Y');
		
		nextLocation.setX( getLocationInBoard( nextLocation.getX(), Xdim ) );
		nextLocation.setY( getLocationInBoard( nextLocation.getY(), Ydim ) );
		
		return nextLocation;
	}

	private int getLocationInBoard(int location, int dimension) {
		if (location >= dimension)
			return 0;
		
		if (location < 0)
			return dimension - 1;
		
		return location;
	}

	private Location changeLocationByDirection(int x, int y, Direction d){
		switch (d){
		case RIGHT:
			y ++;
			break;
		case LEFT:
			y --;
			break;
		case DOWN:
			x ++;
			break;
		case UP:
			x --;
			break;
		}
		return new Location(x, y);
	}
	
	private void restartBoard() {
		for ( MovingCreature c : topBoard.getMovingCreatures() ) {
			topBoard.set( c.getLocation(), new Null() );
			topBoard.set( c.getInitialLocation() , c );
			c.setDirection( DEFAULT_DIRECTION );
		}
	}
	
}
