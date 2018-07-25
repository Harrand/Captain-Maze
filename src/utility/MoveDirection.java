package utility;

import java.util.Random;

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
	
	public static MoveDirection Random()
	{
		switch(new Random().nextInt(4))
		{
		case 0:
			return MoveDirection.UP;
		case 1:
			return MoveDirection.DOWN;
		case 2:
			return MoveDirection.LEFT;
		case 3:
			return MoveDirection.RIGHT;
		}
		return MoveDirection.STILL;
	}
	
	public static MoveDirection get(Vector2I unit)
	{
		if(unit.x == 0)
		{
			// up or down
			if(unit.y > 0)
				return MoveDirection.DOWN;
			else
				return MoveDirection.UP;
		}
		else if(unit.x > 0)
			return MoveDirection.RIGHT;
		else
			return MoveDirection.LEFT;
	}
}
