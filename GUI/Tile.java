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
	private boolean moveBlock;
	private Doodad doodad;
//	private int spawnChance
	
	public Tile(TILE_TYPE type)	{
		createBase(type);
	}
	public Tile(TILE_TYPE type1, DOODAD_TYPE type2)	{
		createBase(type1);
		this.doodad = new Doodad(type2);
		moveBlock = doodad.moveBlockEh();
	}
	
	private void createBase(TILE_TYPE type)	{
		if (type == TILE_TYPE.GRASS)	{
			ImageIcon i = new ImageIcon("GUI/Resources/Tile_Grass.png");
			background = i.getImage();
			moveBlock = false;
		}
		else if (type == TILE_TYPE.WATER)	{
			ImageIcon i = new ImageIcon("GUI/Resources/Tile_Water.png");
			background = i.getImage();
			moveBlock = true;
		}
	}
	
	/**
	 * @return Returns true if the tile is unable to be crossed
	 */
	public boolean moveBlockEh()	{
		return moveBlock;
	}
	
	/**
	 * @return The picture used for the tile
	 */
	public Image getBackground()	{
		return background;
	}
	
	/**
	 * @return The doodad that the tile contains. Returns null if empty
	 */
	public Doodad getDoodad()	{
		return doodad;
	}
}
