package maze;

import utility.ColourConsts;
import utility.Vector2I;

public class MazeWall extends MazeObject
{
	public MazeWall(Vector2I position)
	{
		super(position);
		this.colour = ColourConsts.WALL_COLOUR;
	}
}
