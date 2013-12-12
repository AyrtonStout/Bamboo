package Systems;

import java.util.ArrayList;

/**
 * @author mobius
 * The container for all of the party's items. Sub divided into separate categories
 */
public class Inventory {
	
	private ArrayList<ArrayList<Item>> inventory = new ArrayList<ArrayList<Item>>();
	
	private ArrayList<Item> weapons = new ArrayList<Item>();
	private ArrayList<Item> armor = new ArrayList<Item>();
	private ArrayList<Item> accessories = new ArrayList<Item>();
	private ArrayList<Item> consumables = new ArrayList<Item>();
	private ArrayList<Item> loot = new ArrayList<Item>();
	private ArrayList<Item> keyItems = new ArrayList<Item>();
	
	public Inventory()	{
		inventory.add(weapons);
		inventory.add(armor);
		inventory.add(accessories);
		inventory.add(consumables);
		inventory.add(loot);
		inventory.add(keyItems);
	}
	
	/**
	 * Adds an item to the inventory. Sorts the items into categories as they come in
	 * 
	 * @param newItem The item to be received
	 */
	public void addItem(Item newItem)	{
		if (newItem.getClass() == Weapon.class)	{
			weapons.add(newItem);
		}
		else if (newItem.getClass() == Armor.class)	{
			armor.add(newItem);
		}
		else if (newItem.getClass() == Accessory.class)	{
			accessories.add(newItem);
		}
		else if (newItem.getClass() == Consumable.class)	{
			consumables.add(newItem);
		}
		else if (newItem.getClass() == Loot.class)	{
			loot.add(newItem);
		}
		else if (newItem.getClass() == KeyItem.class)	{
			keyItems.add(newItem);
		}
		else	{
			System.err.print("Whoa! What the hell kind of item is this!");
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

}
