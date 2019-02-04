package pacman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
	
	public Board() {
		this(null);
	}
	
	public Board( String[] rowsArray ) {
		this.ghostsNumber = 0;
		
		for (Creature c : MovingCreature.values()){
			MovingCreature Mc = (MovingCreature) c;
			Mc.setLocation(null);
			Mc.setNextDirection(null);
		}
			
		if (rowsArray == null){
			this.board = new Creature[30][30];
			
			try {
				this.board = setBoard();
			} catch (FileNotFoundException e) {
				// no such file
			}
		}
		else {
			this.board = new Creature[rowsArray.length][rowsArray[0].length()];
			this.board = setBoardWithStringArray(rowsArray);
		}
		
	}

	public static class MovingCreature extends Creature implements Moveable {
		public static MovingCreature PACMAN = new MovingCreature();
		public static MovingCreature GHOST_1 = new MovingCreature();
		public static MovingCreature GHOST_2 = new MovingCreature();
		public static MovingCreature GHOST_3 = new MovingCreature();
		public static MovingCreature GHOST_4 = new MovingCreature();
		
		private Location location;
		private Game.Direction direction;
		private Game.Direction nextDirection = Game.Direction.LEFT;
		
		public static void init() {
			PACMAN.type = Type.PACMAN;
			GHOST_1.type = Type.GHOST;
			GHOST_2.type = Type.GHOST;
			GHOST_3.type = Type.GHOST;
			GHOST_4.type = Type.GHOST;
		}
		
		public static Creature[] values() {
			return new Creature[] {
				PACMAN, GHOST_1, GHOST_2, GHOST_3, GHOST_4
			};
		}
		
		@Override
		public Location getLocation() {
			return this.location;
		}

		@Override
		public void setLocation(Location location) {
			this.location = location;
		}

		@Override
		public Game.Direction getDirection() {
			return this.direction;
		}

		@Override
		public void setDirection(Game.Direction direction) {
			this.direction = direction;
		}

		@Override
		public Game.Direction getNextDirection() {
			return nextDirection;
		}

		@Override
		public void setNextDirection(Game.Direction goHere) {
			this.nextDirection = goHere;
		}
	}
	
	public static class Creature {
		public static Creature POINT = new Creature();
		public static Creature WALL = new Creature();
		public static Creature NULL = new Creature();
		
		protected Type type;

		public enum Type {
			PACMAN,
			GHOST,
			POINT,
			WALL,
			NULL
		}
		
		public Type getType() {
			return this.type;
		}
		
		public static void init() {
			POINT.type = Type.POINT;
			WALL.type = Type.WALL;
			NULL.type = Type.NULL;
			MovingCreature.init();
		}
		
	}
	
	private Creature[][] board;
	private int ghostsNumber;
	
	public Creature[][] get() {
		return board.clone();
	}

	public Creature get( Location l ) {
		return board[l.getX()][l.getY()];
	}
	
	public void set( Location l, Creature c ) {
		board[l.getX()][l.getY()] = c;
		
		if ( isStaticCreature(c) )
			return;
		
		MovingCreature Mc = (MovingCreature) c;
		
		Mc.setLocation(l);
		
		if (Mc.getDirection() == null)
			Mc.setDirection(Game.defaultDirection);
	}
	
	public void limitToSpecificCreatures( Creature[] CreaturesForThisBoard ) {
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				if ( !Arrays.stream( CreaturesForThisBoard ).anyMatch( get( new Location(i, j) ) :: equals ) ) 
					set( new Location(i, j), Creature.NULL );
			}
		}
	}
	
	public int getDimensions() {
		if (board.length != board[0].length)
			return Integer.parseInt( new String( board.length + "" + board[0].length ) );
		
		return board.length;
	}
	
	public int getGhostNum() {
		return this.ghostsNumber;
	}
	
	public boolean isStaticCreature( Creature c ) {
		return ( c == Creature.WALL || c == Creature.NULL || c == Creature.POINT ) ? true : false;
	}
	
	private Creature[][] setBoard() throws FileNotFoundException {
		// I didn't find the relative path for the file Board.txt so i used this
		Scanner sc = new Scanner(new BufferedReader(new FileReader("E:\\Docs\\ωμεν\\A android project\\Pacman\\Board.txt")));
	    while(sc.hasNextLine()) {
	    	for (int i = 0; i < board.length && sc.hasNextLine(); i++) {
    			String[] line = sc.nextLine().trim().split("	");
	        	for (int j = 0; j < line.length; j++)
					initialize( new Location(i, j), StringToCreature(line[j]) );
	        }
	    }
	    sc.close();
	    return board;
	}
	
	private Creature StringToCreature( String st ) {
		switch(st){
			case "P":
				return MovingCreature.PACMAN;
			case "W":
				return Creature.WALL;
			case "-":
				return Creature.POINT;
			case "G":				
				this.ghostsNumber++;
				switch(this.ghostsNumber){
					case 1:
						return MovingCreature.GHOST_1;
					case 2:
						return MovingCreature.GHOST_2;
					case 3:
						return MovingCreature.GHOST_3;
					case 4:
						return MovingCreature.GHOST_4;
				}
				
			default:
				return Creature.NULL;
		}
	}

	private Creature[][] setBoardWithStringArray( String[] sts ) {
		
		for (int i = 0; i < sts.length; i++){
			for (int j = 0; j < sts[i].length(); j++)
				initialize( new Location(i, j), StringToCreature(sts[i].charAt(j) + "") );
		}
		
		return this.board;
	}
	
	private void initialize( Location l, Creature c ) {
		
		if ( !isStaticCreature(c) ) {
			MovingCreature Mc = (MovingCreature) c;
			Mc.setLocation(null);
			Mc.setDirection(null);
		}
		set( l, c );
	}
	
}
