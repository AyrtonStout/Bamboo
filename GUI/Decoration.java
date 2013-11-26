package GUI;

import java.awt.Image;
import javax.swing.ImageIcon;

import GUI.Enums.*;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Decorations may change the behavior of the tile
 * such as causing it to become a moveblock. 
 */
public class Decoration extends Doodad {
	
	protected Image background;
	private boolean moveBlock;
	private int verticalOffset, horizontalOffset;
	

	/**
	 * @param type The type of decoration to be overlaid on the tile
	 */
	public Decoration(DECORATION type)	{
		if (type == DECORATION.TREE_PALM)	{
			ImageIcon i = new ImageIcon("GUI/Resources/Tree_Palm.png");
			background = i.getImage();
			moveBlock = true;
			verticalOffset = -30;
			horizontalOffset = 6;
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
