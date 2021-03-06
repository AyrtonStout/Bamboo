package Map;

import Map.Enums.DECORATION;
import Map.Enums.DOOR;
import Map.Enums.SIGN;
import Map.Enums.TILE;
import Systems.SpawnGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mobius
 *         One square of the world map
 *         <p>
 *         The type of tile is determined by the enumeration passed into the constructor
 *         Each tile type has its own preset properties
 *         <p>
 *         A tile may contain a Doodad (such as a tree or treasure chest) which could alter those properties
 */
public class Tile implements Serializable {

	private static final long serialVersionUID = -6802373140103976028L;
	public final int SIZE = 40;
	private ImageIcon background;
	private boolean moveBlock;
	private Doodad doodad;
	private SpawnGenerator spawn = null;

	/**
	 * @param type The type of tile to be created
	 *             <p>
	 *             Creates a plain tile without a Doodad
	 */
	public Tile(TILE type) {
		createBase(type);
	}

	/**
	 * @param type1 The type of tile to be created
	 * @param type2 THe type of decoration to add to the tile
	 *              <p>
	 *              Creates a tile with a decoration (such as a tree or rock) on top of it
	 */
	public Tile(TILE type1, DECORATION type2) {
		createBase(type1);
		this.doodad = new Decoration(type2);
		moveBlock = doodad.moveBlockEh();
	}

	/**
	 * @param type1 The type of tile to be created
	 * @param type2 THe type of interactable Doodad to add to the tile
	 *              <p>
	 *              Creates a tile with an interactable element (such as a treasure chest) on top of it
	 */
	public Tile(TILE type1, Chest type2) {
		createBase(type1);
		this.doodad = type2;
		moveBlock = doodad.moveBlockEh();
	}

	/**
	 * @param type1 The type of tile to be created
	 * @param type2 THe type of interactable Doodad to add to the tile
	 *              <p>
	 *              Creates a tile with an interactable element (such as a treasure chest) on top of it
	 */
	public Tile(TILE type1, SIGN type2, ArrayList<String> dialog) {
		createBase(type1);
		this.doodad = new Sign(type2, dialog);
		moveBlock = doodad.moveBlockEh();
	}

	/**
	 * @param type1 The type of tile to be created
	 * @param type2 The type of door to be added
	 * @param x     The X grid location of the door
	 * @param y     The Y grid location of the door
	 *              <p>
	 *              Creates a tile with a Door on top of it. The location of the door / tile must be supplied
	 */
	public Tile(TILE type1, DOOR type2, int x, int y) {
		createBase(type1);
		this.doodad = new Door(type2, x, y);
		moveBlock = doodad.moveBlockEh();
	}

	/**
	 * @param type The type of tile to be created
	 *             <p>
	 *             Assigns the behavior for a tile based on the enumerated type passed in
	 */
	private void createBase(TILE type) {
		if (type == TILE.GROUND_GRASS) {
			background = new ImageIcon("GUI/Resources/Tile_Grass.png");
			moveBlock = false;
		} else if (type == TILE.GROUND_WATER) {
			background = new ImageIcon("GUI/Resources/Tile_Water.png");
			moveBlock = true;
		} else if (type == TILE.WALL_CAVE) {
			background = new ImageIcon("GUI/Resources/Tile_CaveWall.png");
			moveBlock = true;
		} else if (type == TILE.GROUND_CAVE) {
			background = new ImageIcon("GUI/Resources/Tile_CaveFloor.png");
			moveBlock = false;
		}
	}

	/**
	 * @return Returns true if the tile is unable to be crossed
	 */
	public boolean moveBlockEh() {
		return moveBlock;
	}

	/**
	 * @return The picture used for the tile
	 */
	public Image getBackground() {
		return background.getImage();
	}

	/**
	 * @return The doodad that the tile contains. Returns null if empty
	 */
	public Doodad getDoodad() {
		return doodad;
	}

	/**
	 * @param b Whether or not the tile should be a moveblock
	 */
	public void setMoveBlock(boolean b) {
		moveBlock = b;
	}

	public SpawnGenerator getSpawn() {
		return spawn;
	}

	public void setSpawn(SpawnGenerator spawn) {
		this.spawn = spawn;
	}
}
