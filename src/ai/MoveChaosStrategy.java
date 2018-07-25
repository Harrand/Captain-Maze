package ai;

import java.util.ArrayList;

import utility.MoveDirection;

public class MoveChaosStrategy extends MoveStrategy
{
	public MoveChaosStrategy()
	{
		super(new ArrayList<MoveDirection>());
	}
	
	@Override
	public MoveDirection next()
	{
		return MoveDirection.Random();
	}
}
