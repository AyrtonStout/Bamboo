package Map;

import java.io.Serializable;

import javax.swing.ImageIcon;

import Map.Enums.*;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Decorations may change the behavior of the tile
 * such as causing it to become a moveblock. 
 */
public class Decoration extends Doodad implements Serializable {
	
	private static final long serialVersionUID = -3360583090635536017L;

	/**
	 * @param type The type of decoration to be overlaid on the tile
	 */
	public Decoration(DECORATION type)	{
		if (type == DECORATION.TREE_PALM)	{
			background = new ImageIcon("GUI/Resources/Tree_Palm.png");
			moveBlock = true;
			verticalOffset = -35;
			horizontalOffset = 6;
			dominant = true;
		}
	}
}
