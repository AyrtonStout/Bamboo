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
	public Tile(TILE_TYPE type1, DECORATION_TYPE type2)	{
		createBase(type1);
		this.doodad = new Decoration(type2);
		moveBlock = doodad.moveBlockEh();
	}
	public Tile(TILE_TYPE type1, INTERACTABLE_TYPE type2)	{
		createBase(type1);
		this.doodad = new Interactable(type2);
		moveBlock = doodad.moveBlockEh();
	}
	
	public Tile(TILE_TYPE type1, DOOR_TYPE type2) {
		createBase(type1);
		this.doodad = new Door(type2);
		moveBlock = doodad.moveBlockEh();
	}
	private void createBase(TILE_TYPE type)	{
		if (type == TILE_TYPE.GROUND_GRASS)	{
			background = new ImageIcon("GUI/Resources/Tile_Grass.png").getImage();
			moveBlock = false;
		}
		else if (type == TILE_TYPE.GROUND_WATER)	{
			background = new ImageIcon("GUI/Resources/Tile_Water.png").getImage();
			moveBlock = true;
		}
		else if (type == TILE_TYPE.WALL_CAVE)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveWall.png").getImage();
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
