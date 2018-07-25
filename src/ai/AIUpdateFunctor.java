package ai;

import java.util.TimerTask;

import javax.swing.JComponent;

import maze.Maze;

public class AIUpdateFunctor extends TimerTask
{
	private Maze maze;
	private JComponent repainter;
	
	public AIUpdateFunctor(Maze maze, JComponent repainter)
	{
		this.maze = maze;
		this.repainter = repainter;
	}

	@Override
	public void run()
	{
		for(MazeAIPlayer player : this.maze.getAIPlayers())
				player.makeDecision(this.repainter);
	}

}
