package Map;

import java.io.Serializable;

import javax.swing.ImageIcon;

import Map.Enums.*;
import Systems.Item;
import Systems.PartyMember;

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
	/**
	 * Sets the loot that the chest will contain when it is opened
	 * 
	 * @param item The chests's rewarded item
	 */
	public void setLoot(Item item)	{
		reward = item;
	}
	/**
	 * Returns the item that is contained within the chest. This does not remove the chest's item or flag the chest
	 * as being unable to be looted. It is essentially just a loot getter method.
	 * 
	 * @return The item the chest contains
	 */
	public Item lootChest()	{
		PartyMember.incrementChestsFound();
		return reward;
	}
	/**
	 * @return The type of treasure chest it is. Wooden small/iron big etc.
	 */
	public TREASURE_CHEST getType()	{
		return type;
	}
}
