package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import utility.ColourConsts;
import utility.MoveDirection;
import utility.Vector2I;

public class Maze
{
	private BufferedImage maze_image;
	private int width, height;
	private Map<Vector2I, MazeObject> maze_map;
	private List<MazeEntrance> maze_entrances;
	private List<MazeExit> maze_exits;
	private MazePlayer maze_player;
	
	public Maze(File maze_image_file)
	{
		try
		{
			this.maze_image = ImageIO.read(maze_image_file);
		} catch (IOException e)
		{
			this.maze_image = null;
			e.printStackTrace();
		}
		// Check through the pixels and check for the right colours.
		this.maze_map = new HashMap<Vector2I, MazeObject>();
		this.maze_entrances = new ArrayList<MazeEntrance>();
		this.maze_exits = new ArrayList<MazeExit>();
		
		int[][] pixels = this.updatePixelData();
		this.populateMazeMap(pixels);
	}
	
	private int[][] updatePixelData()
	{
		if(this.maze_image == null)
		{
			this.width = this.height = -1;
			return null;
		}
		this.width = this.maze_image.getWidth();
		this.height = this.maze_image.getHeight();
		int[][] data = new int[width][height];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				data[x][y] = this.maze_image.getRGB(x, y);
		return data;
	}
	
	private boolean populateMazeMap(int[][] data)
	{
		if(data == null || this.width < 0 || this.height < 0)
			return false;
		for(int x = 0; x < this.width; x++)
		{
			for(int y = 0; y < this.height; y++)
			{
				Color pixel_colour = new Color(data[x][y]);
				MazeObject to_emplace = null;
				Vector2I position = new Vector2I(x, y);
				if(pixel_colour.equals(ColourConsts.WALL_COLOUR))
				{
					// Put a wall at this position.
					to_emplace = new MazeWall(position);
				}
				else if(pixel_colour.equals(ColourConsts.ENTRANCE_COLOUR))
				{
					// Put an entrance at this position.
					MazeEntrance entrance = new MazeEntrance(position);
					to_emplace = entrance;
					this.maze_entrances.add(entrance);
				}
				else if(pixel_colour.equals(ColourConsts.EXIT_COLOUR))
				{
					MazeExit exit = new MazeExit(position);
					to_emplace = exit;
					this.maze_exits.add(exit);
				}
				else
					continue;
				// Add the object to the map.
				this.maze_map.put(position, to_emplace);
			}
		}
		return true;
	}
	
	public final MazeObject at(Vector2I position)
	{
		MazeObject object = this.maze_map.get(position);
		return object;
	}
	
	public void draw(Vector2I viewport_dimensions, Graphics gl)
	{
		// We need to calculate how many pixels each texel of the Maze should take up.
		// texel size = viewport-width / texels-per-row
		Vector2I texel_size = new Vector2I(viewport_dimensions.x / this.width, viewport_dimensions.y / this.height);
		for(MazeObject object : this.maze_map.values())
			object.draw(texel_size.x, texel_size.y, gl);
	}
	
	public void respawnPlayer(int entrance_id)
	{
		boolean found = false;
		for(MazeObject object : this.maze_map.values())
		{
			if(object instanceof MazePlayer)
			{
				this.maze_player = (MazePlayer) object;
				found = true;
			}
		}
		if(!found)
		{
			// wasn't found, create the player and add it to the map.
			this.maze_player = new MazeHumanPlayer(Vector2I.NAN, this);
			this.maze_map.put(this.maze_player.position, this.maze_player);
		}
		try
		{
		this.maze_player.position = this.maze_entrances.get(entrance_id).position;
		}catch(IndexOutOfBoundsException exception){}
	}
	
	public void onKeyPress(char c)
	{
		if(this.maze_player != null && this.maze_player instanceof MazeHumanPlayer)
		{
			switch(c)
			{
			case 'W':
				this.maze_player.moveIfCan(this, MoveDirection.UP);
				break;
			case 'A':
				this.maze_player.moveIfCan(this, MoveDirection.LEFT);
				break;
			case 'S':
				this.maze_player.moveIfCan(this, MoveDirection.DOWN);
				break;
			case 'D':
				this.maze_player.moveIfCan(this, MoveDirection.RIGHT);
				break;
			}
		}
	}
	
	public void checkPlayerState()
	{
		for(MazeExit exit : this.maze_exits)
			if(this.maze_player.position.equals(exit.position))
				this.onPlayerReachExit();
	}
	
	public void onPlayerReachExit()
	{
		System.out.println("Player reached exit! Again!");
		this.respawnPlayer(0);
	}
	
	public static boolean isEmpty(MazeObject object)
	{
		return object == null;
	}
	
	public static boolean isWall(MazeObject object)
	{
		return object instanceof MazeWall;
	}
	
	public static boolean isEntrance(MazeObject object)
	{
		return object instanceof MazeEntrance;
	}
	
	public static boolean isExit(MazeObject object)
	{
		return object instanceof MazeExit;
	}
}
