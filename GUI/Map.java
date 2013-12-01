package GUI;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mobius
 * Glorified two dimensional array of Tile objects. Has the ability to draw all of the tiles it contains
 */
public class Map implements Serializable {

	private static final long serialVersionUID = 861097006138182602L;
	Tile[][] tiles;
	ArrayList<Door> doors = new ArrayList<Door>();
	int windowWidth, windowHeight;
	
	public Map(Tile[][] tiles)	{
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
	}
	
	
	/**
	 * @param g Graphics component to be drawn on
	 * @param player Player character
	 */
	public void drawImage(Graphics g, Character player)	{
		
		/*
		 * Draws the base texture for all background tiles
		 */
		for (int row = 0; row < tiles.length; row++)	{
			for (int column = 0; column < tiles[row].length; column++)	{
				if (tiles[row][column] != null)	{
					g.drawImage(tiles[row][column].getBackground(), row*40 - player.getBackgroundX(), column*40 - player.getBackgroundY(), null);
				}
			}
		}
		/*
		 * Draws all of the tiles' doodads
		 */
		for (int row = 0; row < tiles.length; row++)	{
			for (int column = 0; column < tiles[row].length; column++)	{
				if (tiles[row][column].getDoodad() != null)	{
					g.drawImage(tiles[row][column].getDoodad().getBackground(), 
							row*40 - player.getBackgroundX() + tiles[row][column].getDoodad().getOffsetX(), 
							column*40 - player.getBackgroundY() + tiles[row][column].getDoodad().getOffsetY(), null);
				}
			}
		}
		/*
		 * Draws the player character
		 */
		g.drawImage(player.getImage(), player.getCharX(), player.getCharY(), null);
		
		/*
		 * Redraws the doodads that are below the character so that they appear above it
		 */
		Tile drawnTile;
		//Down Center
		if (player.getCoordY() + 1 < tiles[0].length)	{
			drawnTile = tiles[player.getCoordX()][player.getCoordY() + 1];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(), player.getCoordX()*40 - player.getBackgroundX() + drawnTile.getDoodad().getOffsetX(),
						(player.getCoordY() + 1)*40 - player.getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}
		//Down Right
		if (player.getCoordY() + 1 < tiles[0].length && player.getCoordX() + 1 < tiles.length)	{
			drawnTile = tiles[player.getCoordX() + 1][player.getCoordY() + 1];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(),	(player.getCoordX() + 1)*40 - player.getBackgroundX() + drawnTile.getDoodad().getOffsetX(), 
						(player.getCoordY() + 1)*40 - player.getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}	
		//Down Left
		if (player.getCoordY() + 1 < tiles[0].length && player.getCoordX() - 1 > 0)	{
			drawnTile = tiles[player.getCoordX() - 1][player.getCoordY() + 1];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(),	(player.getCoordX() - 1)*40 - player.getBackgroundX() + drawnTile.getDoodad().getOffsetX(), 
						(player.getCoordY() + 1)*40 - player.getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}
		//Double Down
		if (player.getCoordY() + 2 < tiles[0].length)	{
			drawnTile = tiles[player.getCoordX()][player.getCoordY() + 2];
			if (drawnTile.getDoodad() != null && drawnTile.getDoodad().dominantEh())	{
				g.drawImage(drawnTile.getDoodad().getBackground(), player.getCoordX()*40 - player.getBackgroundX() + drawnTile.getDoodad().getOffsetX(),
						(player.getCoordY() + 2)*40 - player.getBackgroundY() + drawnTile.getDoodad().getOffsetY(), null);
			}
		}
	}
	
	/**
	 * @param player The player's character that is activating an object
	 * 
	 * Checks if the tile the player is facing is interactable
	 * If it is, the object's interact() method is called
	 */
	@SuppressWarnings("incomplete-switch")
	public void activate(Character player) {
		Tile facedTile = null;
		switch (player.getFacing())	{
		case LEFT:
			facedTile = tiles[player.getCoordX() - 1][player.getCoordY()]; break;
		case UP:
			facedTile = tiles[player.getCoordX()][player.getCoordY() - 1]; break;
		case RIGHT:
			facedTile = tiles[player.getCoordX() + 1][player.getCoordY()]; break;
		case DOWN:
			facedTile = tiles[player.getCoordX()][player.getCoordY() + 1]; break;
		}
		if (facedTile.getDoodad() != null)	{
			if(facedTile.getDoodad().getClass() == Interactable.class)	{
				((Interactable) facedTile.getDoodad()).interact();
			}
		}
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
