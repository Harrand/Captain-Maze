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
	
	public void draw(int width, int height, Graphics gl)
	{
		gl.setColor(this.colour);
		gl.fillRect(this.position.x * width, this.position.y * height, width, height);
	}
}
