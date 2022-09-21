import java.util.*;

public class MazeCell {
	public boolean hasMouse; // Has a mouse
	private Boolean[] walls; // Walls go in order of Up, Right, Down, Left

	/**
	 * Default constructor for MazeCell, sets hasMouse to True and walls surrounding
	 * the current cell.
	 */
	public MazeCell() {
		hasMouse = true;
		walls = new Boolean[] { true, true, true, true };
	}

	/**
	 * Sets the wall value in a given direction.
	 * 
	 * @param direction The direction of the wall to manipulate
	 * @param hasWall   Boolean value of wall 0-Up, 1-Right, 2-Down, 3-Left
	 */
	public void SetWall(int direction, boolean hasWall) // Direction 0 is up and 1 is right...
	{
		walls[direction] = hasWall;
	}

	/**
	 * Gets the state of walls for a cell.
	 * 
	 * @return Boolean [] of size 4 with the value all 4 walls
	 */
	public Boolean[] GetWalls() {
		return walls;
	}

	/**
	 * Gets the number of walls around the cell
	 * 
	 * @return int, 0-4 the number of walls
	 */
	public int GetNumWalls() {
		return (int) Arrays.stream(walls).filter(c -> c == true).count();
	}
}
