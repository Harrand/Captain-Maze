package utility;

public enum MoveDirection
{
	UP, DOWN, LEFT, RIGHT, STILL;
	
	public Vector2I unit()
	{
		if(this == UP)
			return new Vector2I(0, -1);
		else if(this == DOWN)
			return new Vector2I(0, 1);
		else if(this == LEFT)
			return new Vector2I(-1, 0);
		else if(this == RIGHT)
			return new Vector2I(1, 0);
		else
			return new Vector2I(0, 0);
	}
}
