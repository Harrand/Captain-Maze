package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import ai.MazeAIPlayer;
import ai.GeneticAlgorithm;
import utility.ColourConsts;
import utility.MoveDirection;
import utility.Vector2I;

public class Maze
{
	private BufferedImage maze_image;
	private int width, height;
	private Map<Vector2I, Collection<MazeObject>> maze_map;
	private List<MazeEntrance> maze_entrances;
	private List<MazeExit> maze_exits;
	private List<MazePlayer> maze_players;
	private GeneticAlgorithm neural_evolution;
	
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
		this.maze_map = new HashMap<Vector2I, Collection<MazeObject>>();
		this.maze_entrances = new ArrayList<MazeEntrance>();
		this.maze_exits = new ArrayList<MazeExit>();
		this.maze_players = new ArrayList<MazePlayer>();
		
		int[][] pixels = this.updatePixelData();
		this.populateMazeMap(pixels);
		this.neural_evolution = new GeneticAlgorithm(20);
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public final Collection<MazeEntrance> getEntrances()
	{
		return this.maze_entrances;
	}
	
	public final Collection<MazeExit> getExits()
	{
		return this.maze_exits;
	}
	
	public final Collection<MazePlayer> getPlayers()
	{
		return this.maze_players;
	}
	
	public final MazeHumanPlayer getHumanPlayer()
	{
		for(MazePlayer player : this.getPlayers())
			if(player instanceof MazeHumanPlayer)
				return (MazeHumanPlayer) player;
		return null;
	}
	
	public final Collection<MazeAIPlayer> getAIPlayers()
	{
		List<MazeAIPlayer> ai_players = new ArrayList<MazeAIPlayer>();
		for(MazePlayer player : this.getPlayers())
			if(player instanceof MazeAIPlayer)
				ai_players.add((MazeAIPlayer) player);
		return ai_players;
	}
	
	public final GeneticAlgorithm getGeneticAlgorithm()
	{
		return this.neural_evolution;
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
				this.addMazeObject(to_emplace);
			}
		}
		return true;
	}
	
	public final Collection<MazeObject> at(Vector2I position)
	{
		return this.maze_map.get(position);
	}
	
	private void removeObjectAt(Vector2I position, MazeObject object)
	{
		Collection<MazeObject> at = this.at(position);
		if(at != null)
			at.remove(object);
	}
	
	private void addMazeObject(MazeObject object)
	{
		Vector2I position = object.position;
		Collection<MazeObject> objects = this.maze_map.get(position);
		if(objects == null)
		{
			// need to add the new collection.
			objects = new ArrayList<MazeObject>();
			this.maze_map.put(position, objects);
		}
		objects.add(object);
	}
	
	public void draw(Vector2I viewport_dimensions, Graphics gl)
	{
		// We need to calculate how many pixels each texel of the Maze should take up.
		// texel size = viewport-width / texels-per-row
		Vector2I texel_size = new Vector2I(viewport_dimensions.x / this.width, viewport_dimensions.y / this.height);
		Collection<Collection<MazeObject>> collection_collection = new ArrayList<Collection<MazeObject>>(this.maze_map.values());
		for(Collection<MazeObject> objects : collection_collection)
		{
			Collection<MazeObject> objects_copy = new ArrayList<MazeObject>(objects);
			for(MazeObject object : objects_copy)
				object.draw(texel_size.x, texel_size.y, gl);
		}
	}
	
	public MazePlayer spawnPlayer(int player_id, int entrance_id, MazePlayerType type)
	{
		MazePlayer new_player = null;
		switch(type)
		{
		case HUMAN:
			new_player = new MazeHumanPlayer(Vector2I.NAN, this);
			break;
		case AI:
			new_player = new MazeAIPlayer(Vector2I.NAN, this, this.neural_evolution.getNewStrategy());
			break;
		}
		// set the new player position.
		try
		{
			new_player.position = this.maze_entrances.get(entrance_id).position;
		}catch(Exception e) {new_player.position = Vector2I.ZERO;}
		
		if(this.maze_players.size() <= player_id)
		{
			// we need to add it as a new player.
			this.maze_players.add(new_player);
			this.addMazeObject(new_player);
		}
		else
		{
			// edit the existing player at that index.
			this.maze_players.set(player_id, new_player);
		}
		return new_player;
	}
	
	public void relocate(MazeObject object, Vector2I previous_position)
	{
		this.removeObjectAt(previous_position, object);
		this.addMazeObject(object);
	}
	
	public void onKeyPress(char c, JComponent repainter)
	{
		MazeHumanPlayer human = null;
		for(MazePlayer player : this.maze_players)
			if(player instanceof MazeHumanPlayer)
				human = (MazeHumanPlayer) player;
		if(human == null)
			return;
		switch(c)
		{
		case 'W':
			human.moveIfCan(this, MoveDirection.UP, repainter);
			break;
		case 'A':
			human.moveIfCan(this, MoveDirection.LEFT, repainter);
			break;
		case 'S':
			human.moveIfCan(this, MoveDirection.DOWN, repainter);
			break;
		case 'D':
			human.moveIfCan(this, MoveDirection.RIGHT, repainter);
			break;
		}
	}
	
	public void checkPlayerStates(JComponent repainter)
	{
		for(MazePlayer player : this.maze_players)
			for(MazeExit exit : this.maze_exits)
				if(player.position.equals(exit.position))
					this.onPlayerReachExit(player);
		boolean ai_players_done = true;
		for(MazeAIPlayer ai_player : this.getAIPlayers())
		{
			if(ai_player.getStrategy().finished())
				this.onAIPlayerFinish(ai_player, repainter);
			else
				ai_players_done = false;
		}
		if(ai_players_done)
		{
			this.neural_evolution.nextGeneration();
			this.createNextGenome(repainter);
		}
	}
	
	public void onAIPlayerFinish(MazeAIPlayer player, JComponent repainter)
	{
		boolean found_exit = false;
		for(MazeExit exit : this.maze_exits)
			if(player.position.subtract(exit.position).magnitude() <= 1.0f)
				found_exit = true;
		if(!found_exit)
		{
			this.maze_players.remove(player);
			this.neural_evolution.rateStrategy(player.getStrategy(), player.fitness());
			repainter.repaint();
		}
		else
			System.out.println("AI Player reached the end! Finished at generation " + this.neural_evolution.getGeneration());
	}
	
	private void deleteAIPlayers()
	{
		for(MazePlayer player : this.maze_players)
		{
			if(player instanceof MazeAIPlayer)
			{
				this.maze_players.remove(player);
				this.removeObjectAt(player.position, player);
			}
		}
	}
	
	private void createAIPlayers(int quantity)
	{
		for(int i = 0; i < quantity; i++)
			this.spawnPlayer(this.maze_players.size(), 0, MazePlayerType.AI);
	}
	
	private void createNextGenome(JComponent repainter)
	{
		this.deleteAIPlayers();
		repainter.repaint();
		this.createAIPlayers(this.neural_evolution.getPopulationSize());
	}
	
	public void onPlayerReachExit(MazePlayer player)
	{
		Vector2I previous_position = player.position;
		player.position = this.getEntrances().iterator().next().position;
		this.relocate(player, previous_position);
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
