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
}
