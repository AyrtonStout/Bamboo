package Systems;

import Systems.Enums.CONSUMABLE_TYPE;
import Systems.Enums.POTION;

import javax.swing.*;
import java.io.Serializable;

/**
 * @author mobius
 *         A one-use type of item. Can have a large variety of effects from health / mana restoration
 *         to a permanent stat increase.
 */
public class Consumable implements Item, Serializable {

	private static final long serialVersionUID = -1162361705759888121L;

	private CONSUMABLE_TYPE consumableType;
	private POTION potionType;                    //TODO Fix this class from being horrible
	private String name;
	private String description;
	private ImageIcon icon;

	private int healthRestore;
	private int manaRestore;
	private int stackQuantity = 1;

	private JTextArea left = new JTextArea();

	private boolean statChange = true;          //Prevents the getters from needlessly recalculating the text boxes every time

	public Consumable(POTION potion) {
		consumableType = CONSUMABLE_TYPE.POTION;
		potionType = potion;
		switch (potion) {
			case HEALTH_SMALL:
				name = "Lesser Health Potion";
				icon = new ImageIcon("GUI/Resources/Icons/Consumables/HealthPot_Small.png");
				healthRestore = 20;

				description = "A small health potion. Tastes tangy";
				break;
			case HEALTH_LARGE:
				name = "Greater Health Potion";
				icon = new ImageIcon("GUI/Resources/Icons/Consumables/HealthPot_Large.png");
				healthRestore = 50;

				description = "A large health potion for a manly thirst";
				break;
			case MANA_SMALL:
				name = "Lesser Mana Potion";
				icon = new ImageIcon("GUI/Resources/Icons/Consumables/ManaPot_Small.png");
				manaRestore = 20;

				description = "Brought back as Lesser Mana Potion: Classic";
				break;
			case MANA_LARGE:
				name = "Greater Mana Potion";
				icon = new ImageIcon("GUI/Resources/Icons/Consumables/ManaPot_Large.png");
				manaRestore = 50;

				description = "A cure for that burning mana craving";
				break;
			case YELLOW_SMALL:
				name = "Lesser Hybrid Potion";
				icon = new ImageIcon("GUI/Resources/Icons/Consumables/YellowPot_Small.png");
				healthRestore = 15;
				manaRestore = 15;

				description = "More than the sum of its parts?";
				break;
			case YELLOW_LARGE:
				name = "Greater Hybrid Potion";
				icon = new ImageIcon("GUI/Resources/Icons/Consumables/YellowPot_Large.png");
				healthRestore = 35;
				manaRestore = 35;

				description = "Since when do red + blue make yellow?";
				break;
		}
	}

	/**
	 * Whether or not the item has a harmful or a helpful effect. An example of a helpful item
	 * would be a potion and an example of a harmful item would be one that inflicts a status ailment.
	 *
	 * @return Whether or not the item is harmful.
	 */
	public boolean harmfulEh() {
		switch (consumableType) {
			case POTION:
				return false;
			case FOOD:
				return false;
			case WATER:
				return false;
			case POISON:
				return true;
			default:
				System.err.println("Consumable type not categorized");
				return false;
		}
	}

	@Override
	public ImageIcon getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return The type of consumable item this is (potion, food, etc)
	 */
	public CONSUMABLE_TYPE getConsumableType() {
		return consumableType;
	}

	public POTION getPotionType() {
		return potionType;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public int getHealthRestore() {
		return healthRestore;
	}

	public int getManaRestore() {
		return manaRestore;
	}

	@Override
	public JTextArea getMainText() {
		if (statChange) {
			if (healthRestore > 0) {
				left.append(Integer.toString(healthRestore) + " Health\n");
			}
			if (manaRestore > 0) {
				left.append(Integer.toString(manaRestore) + " Mana\n");
			}
			statChange = false;
		}
		return left;
	}

	@Override
	public JTextArea getStatText() {
		return null;
	}

	@Override
	public JTextArea getBuffText() {
		return null;
	}

	/**
	 * Increases the amount of this item in the player's inventory by one
	 */
	public void raiseQuantity() {
		stackQuantity++;
	}

	/**
	 * Decreases the amount of this item in the player's inventory by one
	 */
	public void dropQuantity() {
		stackQuantity--;
	}

	/**
	 * @return The number of this item that the player has in their inventory
	 */
	public int getQuantity() {
		return stackQuantity;
	}
}
