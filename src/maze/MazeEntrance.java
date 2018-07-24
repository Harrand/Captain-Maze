package maze;

import utility.ColourConsts;
import utility.Vector2I;

public class MazeEntrance extends MazeObject
{

	public MazeEntrance(Vector2I position)
	{
		super(position);
		this.colour = ColourConsts.ENTRANCE_COLOUR;
	}

}
