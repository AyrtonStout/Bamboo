package GUI;

import java.awt.Image;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Doodads can cause the tile to be a moveblock,
 * create effects when activated, or simply be for decoration
 */
public abstract class Doodad {
	
	protected Image background;
	protected boolean moveBlock;
	protected int verticalOffset, horizontalOffset;

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
