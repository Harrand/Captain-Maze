package maze;

import utility.ColourConsts;
import utility.MoveDirection;
import utility.Vector2I;

public class MazePlayer extends MazeObject
{

	public MazePlayer(Vector2I position)
	{
		super(position);
		this.colour = ColourConsts.PLAYER_COLOUR;
	}
	
	public boolean canMove(Maze maze, MoveDirection direction)
	{
		
	}

}
