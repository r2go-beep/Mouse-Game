public class MouseSprite {
	private int timesHit = 0; // The number of times the mouse has hit a wall
	private int hitsUntilTimeOut; // The number of hits until timeout
	public int xPos; // The x or horizontal position of the mouse
	public int yPos; // The y or vertical position of the mouse
	public int dirFacing; // The direction mouse is facing

	/**
	 * Default constructor of Mouse Sprite, sets xPos, yPos to 0 and
	 * hitsUntilTimeOut to -1 and looking up.
	 */
	public MouseSprite() {
		hitsUntilTimeOut = -1;
		xPos = 0;
		yPos = 0;
		dirFacing = 0;
	}

	/**
	 * Constructor of Mouse Sprite, sets initial starting position and hit timeout,
	 * and mouse is looking up.
	 * 
	 * @param xStartPos           The x-axis of the starting position
	 * @param yStartPos           The y-axis of the starting position
	 * @param newHitsUntilTimeOut The number of hits until the mouse gets a timeout
	 */
	public MouseSprite(int xStartPos, int yStartPos, int newHitsUntilTimeOut) {
		hitsUntilTimeOut = newHitsUntilTimeOut;
		xPos = xStartPos;
		yPos = yStartPos;
		dirFacing = 0;
	}

	/**
	 * Turns the mouse clockwise (to its right).
	 */
	public void Turn() {
		dirFacing = (dirFacing + 1) % 4; // Continuously incrementing between 0 through 3
		System.out.println("The Mouse is now facing: " + numToSide(dirFacing));
	}

	/**
	 * Moves the Sprite Forward 1 position, hitting a wall if it is unable to.
	 * 
	 * @param currentSurroundingWalls
	 */
	public void Forward(Boolean[] currentSurroundingWalls) {
		if (currentSurroundingWalls[dirFacing] == false) // If the mouse doesn't have a wall in the current direction
		{
			System.out.println("The Mouse went forward");
			switch (dirFacing) {
				case 0:
					yPos += -1;
					break;
				case 1:
					xPos += 1;
					break;
				case 2:
					yPos += 1;
					break;
				case 3:
					xPos += -1;
					break;
				default:
					System.out.println("Something went wrong in direction");
			}
		} else {// If the mouse did run into a wall
			Hit();
		}
	}

	/**
	 * Gives a message when the mouse hits a wall. Gives a special message upon the
	 * mouse hitting the wall count reaches hitsUntilTimeout.
	 */
	public void Hit() { // When the mouse runs into a wall
		timesHit++;
		System.out.println("The mouse hit a wall, ouch");
		if (timesHit % hitsUntilTimeOut == 0) { // Every timesHit the mouse hits a wall say a message
			System.out.println("You hit the wall too much and the mouse took a break");
		}
	}

	/**
	 * Enumerates an integer value 0-3 to a direction: Up, Right, Down, Left.
	 * 
	 * @param side The side to be enumerated
	 * @return String the string corresponding to the side
	 */
	public String numToSide(int side) {
		switch (side) {
			case 0:
				return ("Up");
			case 1:
				return ("Right");
			case 2:
				return ("Down");
			case 3:
				return ("Left");
			default:
				return ("This is broken");
		}
	}

}
