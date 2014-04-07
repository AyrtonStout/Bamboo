package Map;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Doodads can cause the tile to be a moveblock,
 * create effects when activated, or simply be for decoration
 */
public abstract class Doodad implements Serializable {
	
	private static final long serialVersionUID = -4580163551311470198L;
	protected ImageIcon background;
	protected boolean moveBlock = false; 
	protected boolean dominant = false;               //Whether or not this will appear in the foreground relative to the player
	protected int verticalOffset = 0, horizontalOffset = 0;

	/**
	 * @return The image of the doodad
	 */
	public Image getBackground()	{
		return background.getImage();
	}
	/**
	 * @return If the doodad causes the tile to be a moveblock
	 */
	public boolean moveBlockEh()	{
		return moveBlock;
	}
	/**
	 * @return Whether or not the Doodad is redrawn into the foreground
	 */
	public boolean dominantEh()	{
		return dominant;
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
