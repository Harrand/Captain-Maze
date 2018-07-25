package ai;

import javax.swing.JComponent;

import maze.Maze;
import maze.MazePlayer;
import utility.ColourConsts;
import utility.MoveDirection;
import utility.Vector2I;

public class MazeAIPlayer extends MazePlayer
{
	private Maze maze;

	public MazeAIPlayer(Vector2I position, Maze maze)
	{
		super(position);
		this.colour = ColourConsts.AI_COLOUR;
		this.maze = maze;
	}
	
	public void makeDecision(JComponent repainter)
	{
		//System.out.println("i am making a decision");
		this.moveIfCan(this.maze, MoveDirection.RIGHT);
		this.moveIfCan(this.maze, MoveDirection.UP);
		repainter.repaint();
	}

}
