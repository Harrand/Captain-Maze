package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;

import maze.Maze;
import utility.Vector2I;

@SuppressWarnings("serial")
public class MazeGame extends JFrame implements KeyListener
{
	private Maze maze;
	
	public MazeGame(String file_path)
	{
		super("Maze Game");
		this.setVisible(true);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);
		
		File file = new File(file_path);
		this.maze = new Maze(file);
		this.maze.respawnPlayer(0);
		this.repaint();
	}
	
	@Override
	public void paint(Graphics gl)
	{
		super.paint(gl);
		if(this.maze == null)
			return;
		this.maze.draw(new Vector2I(this.getWidth(), this.getHeight()), gl);
	}
	
	public static void main(String[] args)
	{
		//new MazeGame(args[0]);
		new MazeGame("C:/Users/Harry/eclipse-workspace/Captain Maze/maze.png");
	}

	@Override
	public void keyPressed(KeyEvent e){}

	@Override
	public void keyReleased(KeyEvent e){}

	@Override
	public void keyTyped(KeyEvent e)
	{
		this.maze.onKeyPress(Character.toUpperCase(e.getKeyChar()));
		this.repaint();
	}
}
