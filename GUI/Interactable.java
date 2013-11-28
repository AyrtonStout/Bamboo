package GUI;

import java.io.Serializable;

import javax.swing.ImageIcon;
import GUI.Enums.*;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Decorations may change the behavior of the tile
 * and create some effect when activated
 */
public class Interactable extends Doodad implements Serializable {

	private static final long serialVersionUID = 8958966546162100963L;
	private boolean activated = false;
	private ImageIcon passiveImg;
	private ImageIcon activeImg;
	
	public Interactable(INTERACTABLE type)	{
		if (type == INTERACTABLE.GROUND_TREASURE_CHEST)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed.png");
			activeImg = new ImageIcon("GUI/Resources/Tree_Palm.png");
			background = passiveImg;
			moveBlock = true;
			verticalOffset = 0;
			horizontalOffset = 0;
		}
	}

	public void interact()	{
		background = activeImg;
		activated = true;
	}
	public void revert()	{
		background = passiveImg;
		activated = false;
	}
	public boolean activeEh()	{
		return activated;
	}
	
}
