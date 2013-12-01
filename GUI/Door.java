package GUI;

import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Enums.*;

/**
 * @author mobius
 * 
 * A type of Doodad overlaid onto a tile that will teleport the player into another map or location within the same map
 * 
 * After a door is added to a map and the map is created, the door must be linked to the map that it's in
 * Likewise the door must be then be linked to its counterpart after the map is created
 */
public class Door extends Doodad implements Serializable {

	private static final long serialVersionUID = -4451983441299961170L;
	
	private int x;
	private int y;
	private Door link;
	private Map parentMap;
	
	/**
	 * @param type The visual style of the Door
	 * @param x The Door's X coordinate
	 * @param y The Door's Y coordinate
	 */
	public Door(DOOR type, int x, int y)	{
		this.x = x;
		this.y = y;
		if (type == DOOR.WALL_CAVE_DOOR)	{
			background = new ImageIcon("GUI/Resources/Tile_CaveDoor.png");
		}
		else if (type == DOOR.TRANSITION_HORIZONTAL)	{
			background = new ImageIcon("GUI/Resources/Transition_Horizontal.png");
		}
	}
	
	/**
	 * @param door The Door that this door links to
	 * 
	 * Set the Door's paired door so it knows where to send the player
	 */
	public void setLink(Door door)	{
		link = door;
	}
	/**
	 * @return The door that this door leads to
	 */
	public Door getLink()	{
		return link;
	}
	/**
	 * @param map The map that this Door is contained within
	 */
	public void setParentMap(Map map)	{
		parentMap = map;
	}

	/**
	 *  @return map The map that this Door is contained within
	 */
	public Map getParentMap() {
		return parentMap;
	}
	
	/**
	 * @return The X coordinate of the Door
	 */
	public int getX()	{
		return x;
	}
	/**
	 * @return The Y coordinate of the Door
	 */
	public int getY()	{
		return y;
	}
	
}
