package GUI;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Enums.*;

/**
 * @author mobius
 * One square of the world map
 * 
 * The type of tile is determined by the enumeration passed into the constructor
 * Each tile type has its own preset properties
 * 
 * A tile may contain a Doodad (such as a tree or treasure chest) which could alter those properties
 */
public class Tile implements Serializable {
	
	private static final long serialVersionUID = -6802373140103976028L;
	public final int SIZE = 40;
	private ImageIcon background;
	private boolean moveBlock;
	private Doodad doodad;
//	private int spawnChance
	
	public Tile(TILE type)	{
		createBase(type);
	}
	public Tile(TILE type1, DECORATION type2)	{
		createBase(type1);
		this.doodad = new Decoration(type2);
		moveBlock = doodad.moveBlockEh();
	}
	public Tile(TILE type1, INTERACTABLE type2)	{
		createBase(type1);
		this.doodad = new Interactable(type2);
		moveBlock = doodad.moveBlockEh();
	}
	
	public Tile(TILE type1, DOOR type2, int x, int y) {
		createBase(type1);
		this.doodad = new Door(type2, x, y);
		moveBlock = doodad.moveBlockEh();
	}
	
	
	private void createBase(TILE type)	{
		if (type == TILE.GROUND_GRASS)	{
			background = new ImageIcon("GUI/Resources/Tile_Grass.png");
			moveBlock = false;
		}
		else if (type == TILE.GROUND_WATER)	{
			background = new ImageIcon("GUI/Resources/Tile_Water.png");
			moveBlock = true;
		}
		else if (type == TILE.WALL_CAVE)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveWall.png");
			moveBlock = true;
		}
		else if (type == TILE.GROUND_CAVE)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveFloor.png");
			moveBlock = false;
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
		return background.getImage();
	}
	
	/**
	 * @return The doodad that the tile contains. Returns null if empty
	 */
	public Doodad getDoodad()	{
		return doodad;
	}
}
