package GUI;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import Quests.Trigger;

/**
 * @author mobius
 * A single world location. Contains a two dimensional array of tiles and an ArrayList of all the doors,
 * NPCs, and triggers in the zone.
 */
public class Map extends JPanel implements Serializable {

	private static final long serialVersionUID = 861097006138182602L;
	Tile[][] tiles;
	boolean[][] moveBlocks;
	ArrayList<Door> doors = new ArrayList<Door>();
	ArrayList<NPC> NPCs = new ArrayList<NPC>();
	ArrayList<Trigger> triggers = new ArrayList<Trigger>();
	int windowWidth, windowHeight;
	
	public Map(Tile[][] tiles, ArrayList<NPC> NPCs)	{
		mutualConstructor(tiles, NPCs);
	}
	public Map(Tile[][] tiles, ArrayList<NPC> NPCs, ArrayList<Trigger> triggers)	{
		mutualConstructor(tiles, NPCs);
		this.triggers = triggers;
	}
	
	/**
	 * All things a map has to do to update regardless of whether or not it has triggers
	 * 
	 * @param tiles The map's tiles
	 * @param NPCs The map's NPCs
	 */
	private void mutualConstructor(Tile[][] tiles, ArrayList<NPC> NPCs)	{
		this.tiles = tiles;
		moveBlocks = new boolean[tiles.length][tiles[0].length];
		
		for (int row = 0; row < tiles.length; row++)	{
			for (int column = 0; column < tiles[row].length; column++)	{
				if (tiles[row][column].getDoodad() != null)	{
					if (tiles[row][column].getDoodad().getClass() == Door.class)	{
						doors.add((Door) tiles[row][column].getDoodad());
						
					}
				}
				if (tiles[row][column].moveBlockEh())	{
					moveBlocks[row][column] = true;
				}
			}
		}
		
		this.NPCs = NPCs;
		
		for (int i = 0; i < NPCs.size(); i++)	{
			moveBlocks[NPCs.get(i).getCoordX()][NPCs.get(i).getCoordY()] = true;
		}
	}
	
	/**
	 * Calls all of the map's NPCs to update and checks all triggers to see if they need to fire
	 */
	public void updateAll()	{
		for (int i = 0; i < NPCs.size(); i++)	{
			NPCs.get(i).update();
		}
		for (int i = 0; i < triggers.size(); i++)	{
			triggers.get(i).fire();
		}
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
	
	public void addNPC(NPC addedNPC)	{
		addedNPC.initializeImages();
		addedNPC.setMap(this);
		NPCs.add(addedNPC);
		this.moveBlocks[addedNPC.getCoordX()][addedNPC.getCoordY()] = true;
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
	 * @return The tiles the map has that are flagged as moveblocks
	 */
	public boolean[][] getMoveblocks()	{
		return moveBlocks;
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
	/**
	 * @return An ArrayList of all of the map's NPCs
	 */
	public ArrayList<NPC> getNPCs()	{
		return NPCs;
	}
	/**
	 * @param triggers Set all of the map's possible triggered events
	 */
	public void setTriggers(ArrayList<Trigger> triggers)	{
		this.triggers = triggers;
	}
	/**
	 * @return An ArrayList of all of a map's possible triggered events
	 */
	public ArrayList<Trigger> getTriggers()	{
		return triggers;
	}
	
	//TODO Figure out why I have to do this and can't in the MapWriter
	public void initializeMap(Map map, GameData data)	{
		for (int i = 0; i < doors.size(); i++)	{
			doors.get(i).setParentMap(map);
		}
		for (int i = 0; i < NPCs.size(); i++)	{
			NPCs.get(i).setMap(map);
			NPCs.get(i).initializeImages();
		}
		for (int i = 0; i < triggers.size(); i++)	{
			triggers.get(i).initialize(map, data);
		}
	}
	
}
