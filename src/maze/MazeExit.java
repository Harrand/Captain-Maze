package maze;

import utility.ColourConsts;
import utility.Vector2I;

public class MazeExit extends MazeObject
{

	public MazeExit(Vector2I position)
	{
		super(position);
		this.colour = ColourConsts.EXIT_COLOUR;
	}

}
