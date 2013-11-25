package GUI;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Doodads can cause the tile to be a moveblock,
 * create effects when activated, or simply be for decoration
 */
public class Doodad {
	
	private Image background;
	private boolean moveBlock;
	private int verticalOffset, horizontalOffset;
	

	/**
	 * @param type The type of doodad to be overlaid on the tile
	 */
	public Doodad(DOODAD_TYPE type)	{
		if (type == DOODAD_TYPE.TREE_PALM)	{
			ImageIcon i = new ImageIcon("GUI/Resources/Tree_Palm2.png");
			background = i.getImage();
			moveBlock = true;
			verticalOffset = 30;
			horizontalOffset = -6;
		}
		if (type == DOODAD_TYPE.TREASURE_CHEST)		{
			ImageIcon i = new ImageIcon("GUI/Resources/Chest_Closed.png");
			background = i.getImage();
			moveBlock = true;
			verticalOffset = 0;
			horizontalOffset = 0;
		}
	}

	/**
	 * @return The image of the doodad
	 */
	public Image getBackground()	{
		return background;
	}
	/**
	 * @return If the doodad causes the tile to be a moveblock
	 */
	public boolean moveBlockEh()	{
		return moveBlock;
	}
	/**
	 * @return The optimal vertical offset of the doodad's image
	 */
	public int getOffsetY()	{
		return verticalOffset;
	}
	/**
	 * @return The optimal horizontal offset of the doodad's image
	 */
	public int getOffsetX()	{
		return horizontalOffset;
	}
}
