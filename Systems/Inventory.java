package Systems;

import java.awt.Component;
import java.util.ArrayList;

/**
 * @author mobius
 * The container for all of the party's items. Sub divided into separate categories
 */
public class Inventory {

	private GameData data;

//	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
//	private ArrayList<Armor> armor = new ArrayList<Armor>();
//	private ArrayList<Accessory> accessories = new ArrayList<Accessory>();
//	private ArrayList<Consumable> consumables = new ArrayList<Consumable>();
//	private ArrayList<Loot> loot = new ArrayList<Loot>();
//	private ArrayList<KeyItem> keyItems = new ArrayList<KeyItem>();
	
	private ArrayList<ArrayList<Item>> inventory = new ArrayList<ArrayList<Item>>();
	
	private ArrayList<Item> weapons = new ArrayList<Item>();
	private ArrayList<Item> armor = new ArrayList<Item>();
	private ArrayList<Item> accessories = new ArrayList<Item>();
	private ArrayList<Item> consumables = new ArrayList<Item>();
	private ArrayList<Item> loot = new ArrayList<Item>();
	private ArrayList<Item> keyItems = new ArrayList<Item>();

	public Inventory(GameData data)	{

		this.data = data;
		
		inventory.add(weapons);
		inventory.add(armor);
		inventory.add(accessories);
		inventory.add(consumables);
		inventory.add(loot);
		inventory.add(keyItems);

	}

	/**
	 * Adds an item to the inventory. Sorts the items into categories as they come in. This method calls the dialogue box
	 * and sets the GameState to "TALK". Do not use for anything else but receiving loot.
	 * 
	 * @param newItem The item to be received
	 */
	public void lootItem(Item newItem)	{
		sortItem(newItem);
		data.getDialogueBox().receiveItem(newItem);
	}

	/**
	 * Adds the item to the inventory. Does nothing else. Use "lootItem()" to call the dialogue box as well.
	 * 
	 * @param newItem
	 */
	public void addItem(Item newItem)	{
		sortItem(newItem);
	}

	private void sortItem(Item newItem)	{
		if (newItem.getClass() == Weapon.class)	{
			weapons.add((Weapon) newItem);
		}
		else if (newItem.getClass() == Armor.class)	{
			armor.add((Armor) newItem);
		}
		else if (newItem.getClass() == Accessory.class)	{
			accessories.add((Accessory) newItem);
		}
		else if (newItem.getClass() == Consumable.class)	{
			boolean found = false;
			for (int i = 0; i < consumables.size(); i++)	{
				if (((Consumable) consumables.get(i)).getPotionType() == ((Consumable) newItem).getPotionType())	{
					((Consumable) consumables.get(i)).raiseQuantity();
					found = true;
					break;
				}
			}
			if (!found)	{
				consumables.add((Consumable) newItem);
			}
		}
		else if (newItem.getClass() == Loot.class)	{
			loot.add((Loot) newItem);
		}
		else if (newItem.getClass() == KeyItem.class)	{
			keyItems.add((KeyItem) newItem);
		}
		else	{
			System.err.print("Whoa! What the hell kind of item is this!");
		}
	}
	
	public void removeItem(Item removedItem)	{
		if (removedItem.getClass() == Weapon.class)	{
			weapons.remove(removedItem);
		}
		else if (removedItem.getClass() == Armor.class)	{
			armor.remove(removedItem);
		}
		else if (removedItem.getClass() == Accessory.class)	{
			accessories.remove(removedItem);
		}
		else if (removedItem.getClass() == Consumable.class)	{
			for (int i = 0; i < consumables.size(); i++)	{
				if (((Consumable) consumables.get(i)).getPotionType() == ((Consumable) removedItem).getPotionType())	{
					((Consumable) consumables.get(i)).dropQuantity();
					if (((Consumable) consumables.get(i)).getQuantity() == 0)	{
						consumables.remove(i);
					}
					break;
				}
			}
		}
		else if (removedItem.getClass() == Loot.class)	{
			loot.remove(removedItem);
		}
		else if (removedItem.getClass() == KeyItem.class)	{
			keyItems.remove(removedItem);
		}
	}

	/**
	 * Returns a particular section of the inventory
	 * 
	 * 0 - Weapon.
	 * 1 - Armor.
	 * 2 - Accessories.
	 * 3 - Consumables.
	 * 4 - Loot.
	 * 5 - Key Items.
	 * 
	 * @param category Integer representation of the category
	 * @return The requested category
	 */
		public ArrayList<Item> getCategory(int category)	{
			return inventory.get(category);
		}

	public int getCategorySize(int category)	{
		switch (category)	{
		case 0:
			return weapons.size();
		case 1:
			return armor.size();
		case 2:
			return accessories.size();
		case 3:
			return consumables.size();
		case 4:
			return loot.size();
		case 5:
			return keyItems.size();
		default:
			return 0;
		}
	}

//	public ArrayList<Weapon> getWeapons()	{
//		return weapons;
//	}
//	public ArrayList<Armor> getArmor()	{
//		return armor;
//	}
//	public ArrayList<Accessory> getAccessories()	{
//		return accessories;
//	}
//	public ArrayList<Consumable> getConsumables()	{
//		return consumables;
//	}
//	public ArrayList<Loot> getLoot()	{
//		return loot;
//	}
//	public ArrayList<KeyItem> getKeyItems()	{
//		return keyItems;
//	}

}
