package GUI;

import java.io.Serializable;

import javax.swing.ImageIcon;
import GUI.Enums.*;
import Systems.Enums.SWORD;
import Systems.Enums.WEAPON_TYPE;
import Systems.Item;
import Systems.Weapon;

/**
 * @author mobius
 * A visual that is overlaid on top of an existing tile. Decorations may change the behavior of the tile
 * and create some effect when activated
 */
public class Chest extends Doodad implements Serializable {

	private static final long serialVersionUID = 8958966546162100963L;
	private boolean activated = false;
	private ImageIcon passiveImg;
	private ImageIcon activeImg;
	private TREASURE_CHEST type;
	private Item reward;
	
	/**
	 * @param type The type of interactable Doodad to be created
	 */
	public Chest(TREASURE_CHEST type)	{
		this.type = type;
		if (type == TREASURE_CHEST.TREASURE_CHEST_SMALL)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed.png");
			activeImg = new ImageIcon("GUI/Resources/Chest_Open.png");
		}
		else if (type == TREASURE_CHEST.TREASURE_CHEST_BIG)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed_Big.png");
			activeImg = new ImageIcon("GUI/Resources/Chest_Open_Big.png");
		}
		moveBlock = true;
		super.background = passiveImg;
	}

	/**
	 * Performs the object's activated action
	 */
	public void interact()	{
		super.background = activeImg;
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
	public void setLoot(Item item)	{
		reward = item;
	}
	public Item lootChest()	{
		return reward;
	}
}
