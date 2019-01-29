package pacman;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

import pacman.Game.Direction;

public class Program extends JFrame{
		
	/**
	 * 
	 */
	Game game;
	Pacman pacman;
	Ghost someGhost;
	static final int Xdim = 30, Ydim = 30;
	Timer show;
	
	public Program(){
				
		setTitle("Change Pacman Direction");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FlowLayout());
		
		this.game = new Game();
		this.pacman = findPacman();
		this.someGhost = findGhost();
		
		for (Game.Direction d : Game.Direction.values()){
			DirectionChanger change = new DirectionChanger(d.name(), game, pacman);
			add(new JButton(change));
		}
		
		show = new Timer();
	}
	
	private String CreatureToString(Creature c){
		switch( c.getType() ){
			case GHOST:
				Ghost g = (Ghost) c;
				if (g.currentMode == Ghost.Mode.ALIVE)
					return "G";
				if (g.currentMode == Ghost.Mode.EATABLE)
					return "g";
				return "D";
			case NULL:
				return "-";
			case PACMAN:
				return "P";
			case POINT:
				return ".";
			case BIG_POINT:
				return "o";
			case WALL:
				return "W";
			case REVIVOR:
				return "R";
		}
		
		return "-";
	}
	
	private void showBoard() {
		for (int i = 0; i < Xdim; i++){
			for (int j = 0; j < Ydim; j++)
				System.out.print( this.CreatureToString( this.game.getCreatureAt( new Location(i, j) ) ) );
			
			System.out.println();
		}
	}
	
	int round = 1;
	
	public static void main(String[] args){
		Program program = new Program();
		program.game.gameOn = false;
		program.setVisible(true);		
		
		System.out.println("Game Starts.\nOriginal Board:");
		program.showBoard();		

		program.startProgram();
	}
	
	class DirectionChanger extends AbstractAction {

		/**
		 * 
		 */
		Game game;
		Pacman pacman;
		
		public DirectionChanger(String str, Game game, Pacman pacman) {
			super(str);
			this.game = game;
			this.pacman = pacman;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (Game.Direction d : Game.Direction.values()){
				if ( arg0.getActionCommand().equals(d.name()) ){
					this.pacman.changeDirection( Direction.valueOf( d.name() ), this.game );
				}
			}
		}
		
	}

	private void startProgram() {
		
		if (game.gameOn)
			return;
		
		game.gameOn = true;
		
		show.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
								
				System.out.println( "\nRound: " + round + ", Score: " + game.getScore() + ", Pacman " + ( someGhost.currentMode == Ghost.Mode.EATABLE ? "Can" : "Can't" ) + " Eat a Ghost" );
				round ++;
				game.move( null );
				showBoard();
				
				if (game.getLives() <= 0) {
					System.out.println("\nGame Over! Well Played");
					System.exit(0);
				}
					
			}

		}, 0, 300);
	}

	public Pacman findPacman() {
		for (int i = 0; i < Xdim; i++) {
			for (int j = 0; j < Ydim; j++) {
				if (game.getCreatureAt( new Location(i, j) ).getType() == Creature.Type.PACMAN)
					return (Pacman) game.getCreatureAt( new Location(i, j));
			}
		}
		
		return null;
	}
	
	public Ghost findGhost() {
		for (int i = 0; i < Xdim; i++) {
			for (int j = 0; j < Ydim; j++) {
				if (game.getCreatureAt( new Location(i, j) ).getType() == Creature.Type.GHOST)
					return (Ghost) game.getCreatureAt( new Location(i, j));
			}
		}
		
		return null;
	}
	
}
