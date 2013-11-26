package GUI;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Decorations may change the behavior of the tile
 * and create some effect when activated
 */
public class Interactable extends Doodad {

	private boolean activated = false;
	private Image passiveImg;
	private Image activeImg;
	
	public Interactable(INTERACTABLE_TYPE type)	{
		if (type == INTERACTABLE_TYPE.GROUND_TREASURE_CHEST)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed.png").getImage();
			activeImg = new ImageIcon("GUI/Resources/Tree_Palm.png").getImage();
			background = passiveImg;
			moveBlock = true;
			verticalOffset = 0;
			horizontalOffset = 0;
		}
	}

	public void interact()	{
		background = activeImg;
	}
	public void revert()	{
		background = passiveImg;
	}
	public boolean activeEh()	{
		return activated;
	}
	
}
