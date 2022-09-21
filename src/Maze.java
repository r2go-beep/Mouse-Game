import java.util.*;

public class Maze {
    private static MazeCell[][] mazeArena; // The 2d maze arena
    private static int sideLength; // The length of the square maze arena

    /**
     * Default constructor for Maze class, initializing the cells to empty cells and
     * sets the side length to 5.
     */
    public Maze() {
        sideLength = 5;
        mazeArena = new MazeCell[sideLength][sideLength];
        ConstructBoundaries();
    }

    /**
     * Constructor for Maze class, initializing the cells to empty cells and sets
     * the side length to param inpSideLength
     * 
     * @param inpSideLength The size of the edges of the square maze
     */
    public Maze(int inpSideLength) {
        sideLength = inpSideLength;
        mazeArena = new MazeCell[sideLength][sideLength];
        ConstructBoundaries();
    }

    /**
     * Initializes the mazeArena to empty cells
     */
    private void ConstructBoundaries() {
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                mazeArena[i][j] = new MazeCell();
            }
        }
    }

    /**
     * Gets the array of walls at position xPos, yPos. Returns a Boolean[4] of all
     * false if x,y position is out of bounds.
     * 
     * @param xPos The xPosition of interest to get the walls
     * @param yPos The yPosition of interest to get the walls
     * @return Boolean[] a length 4 array of the wall status'
     */
    public Boolean[] getWallsAt(int xPos, int yPos) {
        if ((xPos >= 0 || xPos < sideLength) && (yPos >= 0 || yPos < sideLength)) {
            return mazeArena[yPos][xPos].GetWalls();
        } else {
            System.out.println(String.format("Unable to get walls at x: %s, y: %s", xPos, yPos));
            return new Boolean[] { false, false, false, false };
        }

    }

    /**
     * Removes the walls between two cell's in a given direction. Does nothing if
     * x,y position is out of bounds.
     * 
     * @param x   The cell's x position
     * @param y   The cell's y position
     * @param dir The direction of the wall to deconstruct
     */
    private void RemoveWall(int x, int y, int dir) {
        if ((x >= 0 || x < sideLength) && (y >= 0 || y < sideLength)) {
            mazeArena[y][x].SetWall(dir, false);
            switch (dir) {
                case 0:
                    mazeArena[y - 1][x].SetWall((dir + 2) % 4, false);
                    break;
                case 1:
                    mazeArena[y][x + 1].SetWall((dir + 2) % 4, false);
                    break;
                case 2:
                    mazeArena[y + 1][x].SetWall((dir + 2) % 4, false);
                    break;
                case 3:
                    mazeArena[y][x - 1].SetWall((dir + 2) % 4, false);
                    break;
                default:
                    System.out.println("Unknown Direction. Cannot remove wall here.");
            }
        } else {
            System.out.println(String.format("Unable to remove the cell neighboring x: %i, y: %i, dir: %i", x, y, dir));
        }
    }

    /**
     * Constructs a random maze recursively by:
     * 1. Marking the current cell as visited
     * 2. While the current cell has any unvisited neighbors:
     * ----a. Choose a random unvisited neighboring cell
     * ----b. Remove the wall between the current cell and the chosen cell
     * ----c. Call the function recursively for the chosen cell
     * 
     * @param y The current cells y position.
     * @param x The current cells x position.
     */
    public void ConstructRandMaze(int y, int x) { // This constructs a random maze recursively
        mazeArena[y][x].hasMouse = false; // Mark cell as visited
        int[] unvisited;
        int[] unvisitedNeighbors = { // Find unvisited neighbors
                y > 0 ? (mazeArena[y - 1][x].hasMouse ? 0 : -1) : -1,
                x < sideLength - 1 ? (mazeArena[y][x + 1].hasMouse ? 1 : -1) : -1,
                y < sideLength - 1 ? (mazeArena[y + 1][x].hasMouse ? 2 : -1) : -1,
                x > 0 ? (mazeArena[y][x - 1].hasMouse ? 3 : -1) : -1 };

        while (!Arrays.equals(unvisitedNeighbors, new int[] { -1, -1, -1, -1 })) { // While there is still unvisited
            unvisitedNeighbors[0] = y > 0 ? (mazeArena[y - 1][x].hasMouse ? 0 : -1) : -1; // Find unvisited neighbor
            unvisitedNeighbors[1] = x < sideLength - 1 ? (mazeArena[y][x + 1].hasMouse ? 1 : -1) : -1;
            unvisitedNeighbors[2] = y < sideLength - 1 ? (mazeArena[y + 1][x].hasMouse ? 2 : -1) : -1;
            unvisitedNeighbors[3] = x > 0 ? (mazeArena[y][x - 1].hasMouse ? 3 : -1) : -1;

            unvisited = Arrays.stream(unvisitedNeighbors).filter(c -> c != -1).toArray(); // Make an array of unvisited

            if (unvisited.length != 0) {
                int unvisitedDir = unvisited[(int) Math.floor(Math.random() * unvisited.length)];// Choose rnd unvisited

                RemoveWall(x, y, unvisitedDir); // Remove the wall between the two
                switch (unvisitedDir) { // Call recursively
                    case 0:
                        ConstructRandMaze(y - 1, x);
                        break;
                    case 1:
                        ConstructRandMaze(y, x + 1);
                        break;
                    case 2:
                        ConstructRandMaze(y + 1, x);
                        break;
                    case 3:
                        ConstructRandMaze(y, x - 1);
                        break;
                    default:
                        System.out.println("Unknown Direction. Cannot Proceed to build maze.");
                }
            }

        }
    }

    /**
     * Gets the maze in text form. Translates the current maze into ASCII symbols.
     * 
     * @param mouse The mouse in the maze.
     * @return StringBuilder of the current maze
     */
    public StringBuilder GetMazeString(MouseSprite mouse) {
        StringBuilder tempMaze = new StringBuilder(sideLength * sideLength + sideLength); // Make buffer large
        for (int i = -1; i < sideLength; i++) { // Vertical loop and is set to -1 so top row can put its roof on

            tempMaze.append("\n"); // line break

            for (int j = 0; j < sideLength; j++) { // Horizontal loop
                if (i == -1 && mazeArena[0][j].GetWalls()[0]) { // Top row only ones with the top
                    tempMaze.append(" _");
                } else {

                    if (mazeArena[i][j].GetWalls()[3]) { // Left/right and is only used for vert lines
                        tempMaze.append("|");
                    } else {
                        tempMaze.append(" ");
                    }

                    if (mazeArena[i][j].GetWalls()[2] && i == mouse.yPos && j == mouse.xPos) { // Mouse and wall below
                        tempMaze.append("M̲");
                    } else if (mazeArena[i][j].GetWalls()[2]) { // Wall below
                        tempMaze.append("_");
                    } else if (i == mouse.yPos && j == mouse.xPos) { // Mouse located here
                        tempMaze.append("M");
                    } else {
                        tempMaze.append(" "); // No wall below
                    }

                    if (mazeArena[i][j].GetWalls()[1] && j == sideLength - 1) {// Very right side
                        tempMaze.append("|");
                    } else {
                        tempMaze.append("");
                    }
                }
            }
        }
        return tempMaze;
    }
}
