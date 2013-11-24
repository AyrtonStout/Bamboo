package GUI;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author mobius
 * One square of the world map
 * 
 * The type of tile is determined by the enumeration passed into the constructor
 * Each tile type has its own preset properties
 * 
 * A tile may contain a Doodad (such as a tree or treasure chest) which could alter those properties
 */
public class Tile {
	
	public final int SIZE = 40;
	private Image background;
	private boolean moveBlockEh;
//	private Doodad doodad;
//	private int spawnChance
	
	public Tile(TILE_TYPE type)	{
		if (type == TILE_TYPE.GRASS)	{
			ImageIcon i = new ImageIcon("GUI/Resources/GrassTile.png");
			background = i.getImage();
			moveBlockEh = false;
		}
		else if (type == TILE_TYPE.WATER)	{
			ImageIcon i = new ImageIcon("GUI/Resources/WaterTile.png");
			background = i.getImage();
			moveBlockEh = true;
		}
	}
	
	/**
	 * @return Returns true if the tile is unable to be crossed
	 */
	public boolean moveBlockEh()	{
		return moveBlockEh;
	}
	
	/**
	 * @return The picture used for the tile
	 */
	public Image getBackground()	{
		return background;
	}
	
//	public Doodad getDoodad();
}
