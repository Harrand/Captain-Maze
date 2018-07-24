package maze;

import java.awt.Color;
import java.awt.Graphics;

import utility.Vector2I;

public abstract class MazeObject
{
	public Vector2I position;
	protected Color colour;
	
	public MazeObject(Vector2I position)
	{
		this.position = position;
		this.colour = Color.BLACK;
	}
	
	public void Draw(int width, int height, Graphics gl)
	{
		gl.fillRect(this.position.x, this.position.y, width, height);
	}
}
