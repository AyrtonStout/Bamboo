package GUI;

import java.awt.Graphics;

/**
 * @author mobius
 * Glorified two dimensional array of Tile objects. Has the ability to draw all of the tiles it contains
 */
public class Map {

	Tile[][] tiles;
	int windowWidth, windowHeight;
	
	public Map(Tile[][] tiles, int windowWidth, int windowHeight)	{
		this.tiles = tiles;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
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
		//Down Center
		if (player.getCoordY() + 1 < tiles[0].length && tiles[player.getCoordX()][player.getCoordY() + 1].getDoodad() != null)	{
			g.drawImage(tiles[player.getCoordX()][player.getCoordY() + 1].getDoodad().getBackground(), 
					player.getCoordX()*40 - player.getBackgroundX() + tiles[player.getCoordX()][player.getCoordY() + 1].getDoodad().getOffsetX(), 
					(player.getCoordY() +1)*40 -player.getBackgroundY() + tiles[player.getCoordX()][player.getCoordY() + 1].getDoodad().getOffsetY(), 
					null);
		}
		//Down Right
		if (player.getCoordY() + 1 < tiles[0].length && player.getCoordX() + 1 < tiles.length && tiles[player.getCoordX() + 1][player.getCoordY() + 1].getDoodad() != null)	{
			g.drawImage(tiles[player.getCoordX() + 1][player.getCoordY() + 1].getDoodad().getBackground(), 
					(player.getCoordX() + 1)*40 - player.getBackgroundX() + tiles[player.getCoordX() + 1][player.getCoordY() + 1].getDoodad().getOffsetX(), 
					(player.getCoordY() +1)*40 -player.getBackgroundY() + tiles[player.getCoordX() + 1][player.getCoordY() + 1].getDoodad().getOffsetY(), 
					null);
		}
		//Down Left
		if (player.getCoordY() + 1 < tiles[0].length && player.getCoordX() - 1 >= 0 &&tiles[player.getCoordX() - 1][player.getCoordY() + 1].getDoodad() != null)	{
			g.drawImage(tiles[player.getCoordX() - 1][player.getCoordY() + 1].getDoodad().getBackground(), 
					(player.getCoordX() - 1)*40 - player.getBackgroundX() + tiles[player.getCoordX() - 1][player.getCoordY() + 1].getDoodad().getOffsetX(), 
					(player.getCoordY() +1)*40 -player.getBackgroundY() + tiles[player.getCoordX() - 1][player.getCoordY() + 1].getDoodad().getOffsetY(), 
					null);
		}
		//Double Down
		if (player.getCoordY() + 2 < tiles[0].length && tiles[player.getCoordX()][player.getCoordY() + 2].getDoodad() != null)	{
			g.drawImage(tiles[player.getCoordX()][player.getCoordY() + 2].getDoodad().getBackground(), 
					player.getCoordX()*40 - player.getBackgroundX() + tiles[player.getCoordX()][player.getCoordY() + 2].getDoodad().getOffsetX(), 
					(player.getCoordY() +2)*40 -player.getBackgroundY() + tiles[player.getCoordX()][player.getCoordY() + 2].getDoodad().getOffsetY(), 
					null);
		}
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
	 * @return Width of the game window
	 */
	public int getWindowWidth()	{
		return windowWidth;
	}
	/**
	 * @return Height of the game window
	 */
	public int getWindowHeight()	{
		return windowHeight;
	}


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
		interact(facedTile);
	}
	private void interact(Tile facedTile)	{
		if (facedTile.getDoodad() != null)	{
			if(facedTile.getDoodad().getClass() == Interactable.class)	{
				((Interactable) facedTile.getDoodad()).interact();
			}
		}
	}
}
