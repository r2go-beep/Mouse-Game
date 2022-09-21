//Written by Hans Hummel, Aug 21, 2020
//Its a simple maze game where the user can make the mouse move forward, or turn right.
//The user must be able to see the board at least once.
//The objective is to reach the top right corner.

import java.util.Scanner;

public class MouseMain {
	private static int sideLength = 5; // Square maze
	private static MouseSprite mouse;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Maze currentMaze = new Maze(sideLength);

		currentMaze.ConstructRandMaze(0, 0); // Construct random maze, with initial point 0,0
		mouse = new MouseSprite(0, sideLength - 1, 3); // Place the mouse in the bottom left, and time outs to 3

		System.out.println(currentMaze.GetMazeString(mouse));
		System.out.println("Type 'Forward' or 'w' to move forward or type 'Turn' or 'd' to turn clockwise. "
				+ "The goal is to reach the top right corner."); // Prompt user
		System.out.println(String.format("The Mouse is facing %s", mouse.numToSide(mouse.dirFacing)));

		while (!(mouse.xPos == sideLength - 1 && mouse.yPos == 0)) { // While not in winning location
			System.out.print("Please give a command: ");
			String userInput = input.next();
			if (userInput.equals("Forward") || userInput.equals("w")) { // Go forward
				mouse.Forward(currentMaze.getWallsAt(mouse.xPos, mouse.yPos));

			} else if (userInput.equals("Turn") || userInput.equals("d")) { // Turn
				mouse.Turn();

			} else {
				System.out.println("Input could not be understood, please limit commands to "
						+ "'Forward' and 'w' or 'Turn' and 'd'.");
			}
			System.out.println(currentMaze.GetMazeString(mouse));
		}
		System.out.println("Congrats! You won"); // Winning message
		input.close();
	}

}
