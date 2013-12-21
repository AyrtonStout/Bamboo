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
	private boolean walkTransition = false;
	private boolean directionTransition = false;
	private ACTION transitionDirection;
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
			walkTransition = true;
		}
		else if (type == DOOR.TRANSITION_RIGHT)	{
			background = new ImageIcon("GUI/Resources/Transition_Right.png");
			walkTransition = true;
			directionTransition = true;
			transitionDirection = ACTION.RIGHT;
		}
		else if (type == DOOR.TRANSITION_LEFT)	{
			background = new ImageIcon("GUI/Resources/Transition_Left.png");
			walkTransition = true;
			directionTransition = true;
			transitionDirection = ACTION.LEFT;
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
	/**
	 * If this method returns true, it means that the door will warp the character as soon as they step into it
	 * 
	 * @return Whether or not this door warps people who walk onto it
	 */
	public boolean walkTransitionEh()	{
		return walkTransition;
	}
	/**
	 * If this method returns true, it means that the door will warp the character if they push a pre-set direction
	 * while standing on top of the door. This is useful for doors that are implied rather than shown (on the sides of 
	 * buildings or for zone transitions). The required direction can be called by the getDirection() method.
	 * 
	 * @return Whether or not this door has a directional method of changing maps
	 */
	public boolean directionTransitionEh()	{
		return directionTransition;
	}

	/**
	 * The direction requirement the character must press in order to invoke the walkTransition method of changing maps while
	 * they are standing on top of a door with a "true" walkTransition boolean.
	 * 
	 * @return The direction pressed in order to change maps
	 */
	public ACTION getDirection() {
		return transitionDirection;
	}
}
