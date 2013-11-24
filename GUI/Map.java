package GUI;

import java.awt.Graphics;

/**
 * @author mobius
 * Glorified two dimensional array of Tile objects. Has the ability to draw all of the tiles it contains
 */
public class Map {

	Tile[][] tiles;
	int windowWidth, windowHeight;
	
	public Map(Tile[][] tiles, int windowWidth, int windowHeight)	{
		this.tiles = tiles;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	/**
	 * @param g Graphics component to be drawn on
	 * @param backgroundX The offset of the background's X coordinate based on the player's movement
	 * @param backgroundY The offset of the background's Y coordinate based on the player's movement
	 */
	public void drawImage(Graphics g, int backgroundX, int backgroundY)	{
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				if (tiles[i][j] != null)	{
					g.drawImage(tiles[i][j].getBackground(), i*40 - backgroundX, j*40 - backgroundY, null);
				}
			}
		}
	}
	
	/**
	 * @return Array representation of the map
	 */
	public Tile[][] getArray()	{
		return tiles;
	}
	/**
	 * @return Coordinate width (X) of the map
	 */
	public int getWidth()	{
		return tiles.length;
	}
	/**
	 * @return Coordinate height (Y) of the map
	 */
	public int getHeight()	{
		return tiles[0].length;
	}
	/**
	 * @return Pixel width (X) of the map
	 */
	public int getDrawingX()	{
		return tiles.length * 40;
	}
	/**
	 * @return Pixel height (Y) of the map
	 */
	public int getDrawingY()	{
		return tiles[0].length * 40;
	}
	/**
	 * @return Width of the game window
	 */
	public int getWindowWidth()	{
		return windowWidth;
	}
	/**
	 * @return Height of the game window
	 */
	public int getWindowHeight()	{
		return windowHeight;
	}
}
