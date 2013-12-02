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
	
	/**
	 * @param type The type of interactable Doodad to be created
	 */
	public Interactable(INTERACTABLE type)	{
		if (type == INTERACTABLE.TREASURE_CHEST)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed.png");
			activeImg = new ImageIcon("GUI/Resources/Tree_Palm.png");
			moveBlock = true;
		}
		else if (type == INTERACTABLE.SIGN_WOOD)	{
			passiveImg = new ImageIcon("GUI/Resources/Sign_Wood.png");
			activeImg = new ImageIcon("GUI/Resources/Sign_Wood.png");
			moveBlock = true;
			verticalOffset = 7;
			horizontalOffset = 3;
		}
		background = passiveImg;
	}

	/**
	 * Performs the object's activated action
	 */
	public void interact()	{
		background = activeImg;
		activated = true;
	}
	/**
	 * Returns the object to its deactivated state
	 */
	public void revert()	{
		background = passiveImg;
		activated = false;
	}
	/**
	 * @return Whether or not the object is activated
	 */
	public boolean activeEh()	{
		return activated;
	}
	
}
