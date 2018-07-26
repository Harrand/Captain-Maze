package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.AIUpdateFunctor;
import maze.Maze;
import maze.MazePlayerType;
import utility.Vector2I;

@SuppressWarnings("serial")
public class MazeGame extends JPanel implements KeyListener
{
	private Maze maze;
	
	public MazeGame(String file_path, int width, int height)
	{
		this.setVisible(true);
		this.setSize(width, height);
		
		File file = new File(file_path);
		this.maze = new Maze(file);
		this.maze.spawnPlayer(0, 0, MazePlayerType.HUMAN);
		this.maze.spawnPlayer(1, 0, MazePlayerType.AI);
		this.repaint();
		
		Timer timer = new Timer();
		timer.schedule(new AIUpdateFunctor(this.maze, this), 0, 5);
	}
	
	@Override
	public void paintComponent(Graphics gl)
	{
		super.paintComponent(gl);
		if(this.maze == null)
			return;
		this.maze.draw(new Vector2I(this.getWidth(), this.getHeight()), gl);
	}
	
	public static void main(String[] args)
	{
		//new MazeGame(args[0]);
		JFrame window = new JFrame("Maze Game");
		int width = 500, height = 500;
		window.setSize(width, height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		MazeGame game = new MazeGame("C:/Users/Harry/eclipse-workspace/Captain Maze/maze0.png", width, height);
		window.add(game);
		window.addKeyListener(game);
	}

	@Override
	public void keyPressed(KeyEvent e){}

	@Override
	public void keyReleased(KeyEvent e){}

	@Override
	public void keyTyped(KeyEvent e)
	{
		this.maze.onKeyPress(Character.toUpperCase(e.getKeyChar()), this);
		this.repaint();
	}
}
