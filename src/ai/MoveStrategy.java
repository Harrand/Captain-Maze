package ai;

import java.util.ArrayList;
import java.util.Collection;

import utility.MoveDirection;

public class MoveStrategy
{
	private Collection<MoveDirection> moves;
	private int move_index;
	
	public MoveStrategy(Collection<MoveDirection> moves)
	{
		this.moves = moves;
		this.move_index = 0;
	}
	
	public MoveStrategy(MoveStrategy copy)
	{
		//this.moves = copy.moves;
		this.moves = new ArrayList<MoveDirection>();
		for(MoveDirection move : copy.moves)
			this.moves.add(move);
		this.move_index = 0;
	}
	
	public void addMove(MoveDirection direction)
	{
		this.moves.add(direction);
	}
	
	public int size()
	{
		return this.moves.size();
	}
	
	public MoveDirection top()
	{
		return (MoveDirection) this.moves.toArray()[this.moves.size() - 1];
	}
	
	public MoveDirection pop()
	{
		MoveDirection top = this.top();
		this.moves.remove(top);
		return top;
	}
	
	public boolean finished()
	{
		return this.move_index >= this.moves.size();
	}
	
	public MoveDirection next()
	{
		try
		{
		return (MoveDirection) this.moves.toArray()[this.move_index++];
		}catch(Exception e)
		{
			return MoveDirection.STILL;
		}
	}
	
	public void discardThis()
	{
		try
		{
		this.moves.remove(this.moves.toArray()[--this.move_index]);
		}catch(Exception e) {}
	}
	
	@Override
	public String toString()
	{
		String result = "[";
		for(MoveDirection move : this.moves)
			result += move.toString() + " ";
		result += "]";
		return result;
	}
}
