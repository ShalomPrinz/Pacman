package pacman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import pacman.Creature.Type;

public class Board {
	
	public Board() {
		this(null);
	}
	
	public Board( String[] rowsArray ) {
		this.ghostsNumber = 0;
		this.movingCreatures = new Vector<MovingCreature>(0, 1);	
		this.revivorLocation = null;
		
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
		
		// In a board without Revivor, (0, 0) is the fake location of the Revivor
		if (revivorLocation == null)
			revivorLocation = new Location(0, 0);
	}
	
	private Creature[][] board;
	private int ghostsNumber;
	private Vector<MovingCreature> movingCreatures; 
	private Location revivorLocation; 
	
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
		
		MovingCreature mC = (MovingCreature) c;
		
		mC.setLocation(l);
		
		if (mC.getDirection() == null)
			mC.setDirection(Game.defaultDirection);
	}
	
	public void limitToSpecificCreatures( Type[] typesAllowed ) {
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				if ( !Arrays.stream( typesAllowed ).anyMatch( get( new Location(i, j) ).getType() :: equals ) ) 
					set( new Location(i, j), new Null() );
			}
		}
	}
	
	public int getDimensions( char dim ) {
		return (dim == 'X') ? board.length : board[0].length;
	}
	
	public int getGhostNum() {
		return this.ghostsNumber;
	}
	
	public boolean isStaticCreature( Creature c ) {
		Type[] staticCreatures = { Type.NULL, Type.POINT, Type.WALL, Type.BIG_POINT, Type.REVIVOR };
		return Arrays.stream( staticCreatures ).anyMatch( c.getType() :: equals );
	}
	
	public MovingCreature[] getMovingCreatures() {
		return movingCreatures.toArray( new MovingCreature[ movingCreatures.size() ]);
	}
	
	public Location getRevivorLocation() {
		return this.revivorLocation;
	}
	
	private Creature[][] setBoard() throws FileNotFoundException {
		Scanner sc = new Scanner(new BufferedReader(new FileReader("./Board.txt")));
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
			case "W":
				return new Wall();
			case "-":
				return new Point();
			case "B":
				return new BigPoint();
			case "P":
				return new Pacman();
			case "G":
				return new Ghost();
			case "D":
				return new DeadGhost();
			case "R":
				return new Revivor();
			default:
				return new Null();
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
		if (!isStaticCreature(c)) {
			MovingCreature mC = (MovingCreature) c;
			movingCreatures.add(mC);
			mC.setInitialLocation(l);
		}
		
		if (c.getType() == Type.REVIVOR)
			this.revivorLocation = l;
		
		set( l, c );
	}
	
}
