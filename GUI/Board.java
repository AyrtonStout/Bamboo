package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import GUI.Enums.*;
 
/**
 * @author mobius
 * Contains the main game loop. Instantiates the player object and updates it with the timer
 * Player object's actions to be called when updated determined by the key adapter
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private ArrayList<Character> characters = new ArrayList<Character>();	//Will be used to update all of a map's characters
	private int gameState = 0;
	private Character player = new Character();
	private Timer time;
	private int FPS = 60;
	private int windowWidth, windowHeight;

	private Map map;
	private Tile grassTile = new Tile(TILE.GROUND_GRASS);
	private Tile waterTile = new Tile(TILE.GROUND_WATER);
	private Tile treeTile = new Tile(TILE.GROUND_GRASS, DECORATION.TREE_PALM);
	
	public Board(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		addKeyListener(new AL());
		setFocusable(true);
		time = new Timer((int)(1000 / FPS), this);
		time.start();
		
		characters.add(player);
		
		/*
		 * Creation of background map
		 * Loop fills the map with grass and subsequent statements add detail
		 */
		Tile[][] tiles = new Tile[22][18];
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				tiles[i][j] = grassTile;
			}
		}
		tiles[6][7] = waterTile;
		tiles[6][8] = waterTile;
		tiles[6][9] = waterTile;
		tiles[5][8] = waterTile;
		tiles[5][9] = waterTile;
		tiles[7][7] = waterTile;
		tiles[7][8] = waterTile;
		tiles[8][7] = waterTile;
		tiles[15][7] = waterTile;
		tiles[21][7] = waterTile;
		tiles[21][6] = waterTile;
		tiles[21][5] = waterTile;
		tiles[14][12] = waterTile;
		tiles[13][17] = waterTile;
		tiles[12][17] = waterTile;
		tiles[14][17] = waterTile;
		tiles[7][9] = treeTile;
		tiles[10][8] = treeTile;
		tiles[11][8] = treeTile;
		tiles[9][12] = new Tile(TILE.GROUND_GRASS, INTERACTABLE.GROUND_TREASURE_CHEST);
		tiles[13][12] = new Tile(TILE.GROUND_GRASS, DECORATION.TREE_PALM);
		
		for (int i = 0; i < tiles.length; i++)	{
			tiles[i][0] = new Tile(TILE.WALL_CAVE);
			tiles[i][1] = new Tile(TILE.WALL_CAVE);
		}
		tiles[12][1] = new Tile(TILE.WALL_CAVE, DOOR.WALL_CAVE_DOOR);
		
		map = new Map(tiles, this.windowWidth, this.windowHeight);
		
		player.setMap(map);
	}

	//Timer's event
	public void actionPerformed(ActionEvent e) {
		player.update();
		repaint();
	}

	public void paint(Graphics g) {

		map.drawImage(g, player);
	}

	/**
	 * @author mobius
	 * If the key pressed during the overworld screen (gamestate 0) is an arrow key, this will send the key event
	 * to the character object to queue up its corresponding next action to perform when update() is called. Releasing
	 * the key will clear the queued command
	 */
	private class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if (gameState == 0)	{
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || 
						e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
					player.move(e);
				}
				else if (e.getKeyCode() == KeyEvent.VK_Z)	{
					map.activate(player);
				}
			}
		}

		/* 
		 * Cancels the character's queued action causing it to stand still after its current action completes
		 */
		public void keyReleased(KeyEvent e) {
			player.cancelMove();
			//TODO Fix bug where releasing a key other than the arrow keys will cause the player to quit moving
		}
	}
}