package GUI;

import java.awt.Graphics;

public class Map {

	Tile[][] tiles;
	int drawingX = 0;
	int drawingY = 0;
	int windowWidth, windowHeight;
	
	public Map(Tile[][] tiles, int windowWidth, int windowHeight)	{
		this.tiles = tiles;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	public void drawImage(Graphics g, int charX, int charY)	{
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++)	{
				if (tiles[i][j] != null)	{
					g.drawImage(tiles[i][j].getBackground(), i*40 - charX, j*40 - charY, null);
				}
			}
		}
	}
	
	public Tile[][] getArray()	{
		return tiles;
	}
	public int getWidth()	{
		return tiles.length;
	}
	public int getHeight()	{
		return tiles[0].length;
	}
	public int getDrawingX()	{
		return drawingX;
	}
	public int getDrawingY()	{
		return drawingY;
	}
	public int getWindowWidth()	{
		return windowWidth;
	}
	public int getWindowHeight()	{
		return windowHeight;
	}
	
	public void setDrawingX(int x)	{
		drawingX = x;
	}
	public void setDrawingY(int y)	{
		drawingY = y;
	}
}
