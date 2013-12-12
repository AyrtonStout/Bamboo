package GUI;

import java.io.Serializable;

import javax.swing.ImageIcon;
import GUI.Enums.*;
import Systems.Enums.WEAPON_TYPE;
import Systems.Item;
import Systems.Weapon;

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
	private INTERACTABLE type;
	private Weapon reward = new Weapon("Sword of Swords", WEAPON_TYPE.SWORD);
	private Weapon reward2 = new Weapon("Dagger of Maces", WEAPON_TYPE.SWORD);
	
	/**
	 * @param type The type of interactable Doodad to be created
	 */
	public Interactable(INTERACTABLE type)	{
		this.type = type;
		if (type == INTERACTABLE.TREASURE_CHEST)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed.png");
			activeImg = new ImageIcon("GUI/Resources/Chest_Open.png");
			moveBlock = true;
		}
		else if (type == INTERACTABLE.TREASURE_CHEST_BIG)		{
			passiveImg = new ImageIcon("GUI/Resources/Chest_Closed_Big.png");
			activeImg = new ImageIcon("GUI/Resources/Chest_Open_Big.png");
			moveBlock = true;
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
	public Item lootChest()	{
		if (type == INTERACTABLE.TREASURE_CHEST)	{
			return reward;
		}
		else	{
			return reward2;
		}
	}
	
}
