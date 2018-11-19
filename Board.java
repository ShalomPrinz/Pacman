
public class Board {
	// 0 - Pacman. 1 - Ghost.
	// 2 - Wall. 3 - Point.
	
	public int[][] locations;
	
	public int getDimensions(){
		return locations.length*100 + locations[0].length;
	}
}
