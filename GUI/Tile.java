package GUI;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Tile {
	
	public final int SIZE = 40;
	private Image background;
	private boolean moveBlockEh;
//	private Doodad doodad;
	
	public Tile(TILE_TYPE type)	{
		if (type == TILE_TYPE.GRASS)	{
			ImageIcon i = new ImageIcon("GUI/GrassTile.png");
			background = i.getImage();
			moveBlockEh = false;
		}
		else if (type == TILE_TYPE.WATER)	{
			ImageIcon i = new ImageIcon("GUI/WaterTile.png");
			background = i.getImage();
			moveBlockEh = true;
		}
	}
	
	public boolean moveBlockEh()	{
		return moveBlockEh;
	}
	
	public Image getBackground()	{
		return background;
	}
	
//	public Doodad getDoodad();
}
