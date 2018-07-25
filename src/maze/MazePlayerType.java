package maze;

import ai.MazeAIPlayer;

public enum MazePlayerType
{
	HUMAN, AI;
	
	public static MazePlayerType getType(MazePlayer player)
	{
		if(player == null)
			return null;
		else if(player instanceof MazeHumanPlayer)
			return HUMAN;
		else if(player instanceof MazeAIPlayer)
			return AI;
		else
			return null;
	}
}
