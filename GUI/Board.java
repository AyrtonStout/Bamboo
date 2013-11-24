package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
 
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private ArrayList<Character> characters = new ArrayList<Character>();
	private int gameState = 0;
	private Character player = new Character();
	private Timer time;
	private int FPS = 60;
	private int windowWidth, windowHeight;

	private Map map;
	private Tile grassTile = new Tile(TILE_TYPE.GRASS);
	private Tile waterTile = new Tile(TILE_TYPE.WATER);
	
	public Board(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		addKeyListener(new AL());
		setFocusable(true);
		time = new Timer((int)(1000 / FPS), this);
		time.start();
		
		characters.add(player);
		
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
		map = new Map(tiles, this.windowWidth, this.windowHeight);
		
		player.setMap(map);
	}

	//Timer's event
	public void actionPerformed(ActionEvent e) {
		player.update();
		repaint();
	}

	public void paint(Graphics g) {

		map.drawImage(g, player.getBackgroundX(), player.getBackgroundY());
		g.drawImage(player.getImage(), player.getCharX(), player.getCharY(), null);
		
	}

	private class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if (gameState == 0)	{
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || 
						e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
					player.move(e);
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			player.cancelMove();
		}
	}
}