import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Program extends JFrame{
	
	public Program(){
				
		setTitle("Change Pacman Direction");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FlowLayout());
		
		for (Game.Direction d : Game.Direction.values()){
			DirectionChanger change = new DirectionChanger(d.name());
			add(new JButton(change));
		}
		
	}
	
	private String CreatureToString(Board.Creature c){
		switch(c){
			case Ghost1:
			case Ghost2:
			case Ghost3:
			case Ghost4:
				return "G";
			case Null:
				return "-";
			case Pacman:
				return "P";
			case Point:
				return ".";
			case Wall:
				return "W";
		}
		
		return ".";
	}
	
	int round = 1;
	
	public static void main(String[] args){
		Game game = new Game();
		Program program = new Program();
		Timer show = new Timer();
			
		program.setVisible(true);
		
		System.out.println("Game Starts.\nOriginal Game:");
		
		show.scheduleAtFixedRate(new TimerTask() {
						
			@Override
			public void run() {				
				for (int i = 0; i < 30; i++){
					for (int j = 0; j < 30; j++)
						System.out.print( program.CreatureToString( game.getCreatureAt(new Location(i, j)) ) );
					
					System.out.println();
				}		
				
				System.out.println("\nRound: " + program.round);
				program.round ++;
				game.move();
			}
		}, 0, 1000);
	}
	
	class DirectionChanger extends AbstractAction {

		public DirectionChanger(String str) {
			super(str);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(Program.this, "Boo!");
			Board.Creature.Pacman.setDirection(Game.Direction.Down);
		}
		
	}
}
