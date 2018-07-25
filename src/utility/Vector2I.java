package utility;

import java.util.Objects;

public class Vector2I
{
	public int x, y;
	
	public static final Vector2I ZERO = new Vector2I(0, 0);
	public static final Vector2I NAN = new Vector2I(-1, -1);
	
	public Vector2I(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2I add(Vector2I rhs)
	{
		return new Vector2I(this.x + rhs.x, this.y + rhs.y);
	}
	
	public Vector2I subtract(Vector2I rhs)
	{
		return new Vector2I(this.x - rhs.x, this.y - rhs.y);
	}
	
	public Vector2I minus()
	{
		return new Vector2I(-this.x, -this.y);
	}
	
	public float magnitude()
	{
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	@Override
	public int hashCode()
	{
	    return Objects.hash(this.x, this.y);
	}
	
	@Override
	public boolean equals(Object rhs_)
	{
		if(!(rhs_ instanceof Vector2I))
			return false;
		Vector2I rhs = (Vector2I)rhs_;
		return this.x == rhs.x && this.y == rhs.y;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.x + ", " + this.y + "]";
	}
}
