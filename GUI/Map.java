package GUI;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author mobius
 * Glorified two dimensional array of Tile objects. Has the ability to draw all of the tiles it contains
 */
public class Map extends JPanel implements Serializable {

	private static final long serialVersionUID = 861097006138182602L;
	Tile[][] tiles;
	ArrayList<Door> doors = new ArrayList<Door>();
	ArrayList<NPC> NPCs = new ArrayList<NPC>();
	int windowWidth, windowHeight;
	
	public Map(Tile[][] tiles, ArrayList<NPC> NPCs)	{
		this.tiles = tiles;
		
		for (int row = 0; row < tiles.length; row++)	{
			for (int column = 0; column < tiles[row].length; column++)	{
				if (tiles[row][column].getDoodad() != null)	{
					if (tiles[row][column].getDoodad().getClass() == Door.class)	{
						doors.add((Door) tiles[row][column].getDoodad());
					}
				}
			}
		}
		
		this.NPCs = NPCs;
	}
	
	/**
	 * @return All doors in the map
	 */
	public ArrayList<Door> getDoors()	{
		return doors;
	}
	/**
	 * @param door Adds a Door to the map's ArrayList of Doors
	 */
	public void addDoor(Door door)	{
		doors.add(door);
	}
	public ArrayList<NPC> getNPCs()	{
		return NPCs;
	}
	/**
	 * @param x The X coordinate of the searched Door
	 * @param y The Y coordinate of the searched Door
	 * @return The door at a particular (X,Y) coordinate
	 * 
	 * Finds a door at a particular coordinate. Returns null if no door exists
	 */
	public Door findDoor(int x, int y)	{
		for (int i = 0; i < doors.size(); i++)	{
			if (doors.get(i).getX() == x && doors.get(i).getY() == y)	{
				return doors.get(i);
			}
		}
		return null;
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
	
}
