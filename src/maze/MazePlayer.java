package maze;

import java.util.Collection;

import javax.swing.JComponent;

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
		if(maze == null)
			return false;
		Vector2I after = this.position.add(direction.unit());
		Collection<MazeObject> objects = maze.at(after);
		if(objects == null || objects.isEmpty())
			return true;
		for(MazeObject objects_at : maze.at(after))
			if(Maze.isWall(objects_at))
				return false;
		return true;
	}
	
	public void moveIfCan(Maze maze, MoveDirection direction, JComponent repainter)
	{
		if(this.canMove(maze, direction) && !maze.drawing)
		{
			Vector2I previous_position = this.position;
			this.position = this.position.add(direction.unit());
			maze.checkPlayerStates(repainter);
			maze.relocate(this, previous_position);
		}
	}
	
	public int bestDistanceFromExit(Maze maze)
	{
		int minimum_length = Integer.MAX_VALUE;
		for(MazeExit exit : maze.getExits())
		{
			minimum_length = (int) Math.min(minimum_length, exit.position.subtract(this.position).magnitude());
		}
		return minimum_length;
	}

}
