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
		Vector2I after = this.position.add(direction.unit());
		return !Maze.isWall(maze.at(after));
	}
	
	public void moveIfCan(Maze maze, MoveDirection direction)
	{
		if(this.canMove(maze, direction))
		{
			this.position = this.position.add(direction.unit());
			maze.checkPlayerState();
		}
	}

}
