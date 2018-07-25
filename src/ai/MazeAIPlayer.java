package ai;

import javax.swing.JComponent;

import maze.Maze;
import maze.MazeExit;
import maze.MazePlayer;
import utility.ColourConsts;
import utility.MoveDirection;
import utility.Vector2I;

public class MazeAIPlayer extends MazePlayer
{
	private Maze maze;
	private MoveStrategy strategy;

	public MazeAIPlayer(Vector2I position, Maze maze, MoveStrategy strategy)
	{
		super(position);
		this.colour = ColourConsts.AI_COLOUR;
		this.maze = maze;
		this.strategy = strategy;
	}
	
	public final MoveStrategy getStrategy()
	{
		return this.strategy;
	}
	
	public float fitness()
	{
		float average_size = (this.maze.getWidth() + this.maze.getHeight()) / 2.0f;
		float best_distance = Float.MAX_VALUE;
		for(MazeExit exit : this.maze.getExits())
		{
			float x = exit.position.x;
			float y = exit.position.y;
			float px = this.position.x;
			float py = this.position.y;
			float distance = (float) Math.sqrt(Math.pow(px - x, 2) + Math.pow(py - y, 2));
			best_distance = Math.min(best_distance, distance);
		}
		return average_size / best_distance;
	}
	
	public void makeDecision(JComponent repainter)
	{
		MoveDirection direction = this.strategy.next();
		if(this.canMove(maze, direction))
			this.moveIfCan(this.maze, direction, repainter);
		else
			this.strategy.discardThis();
		maze.checkPlayerStates(repainter);
		repainter.repaint();
	}

}
