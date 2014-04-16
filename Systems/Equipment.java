package Systems;

import java.io.Serializable;

import Systems.Enums.ACCESSORY_TYPE;


public class Equipment implements Serializable {

	private static final long serialVersionUID = -8207154870997336759L;

	PartyMember owner;

	Weapon weapon;
	Armor helmet, chest, gloves, boots;
	Accessory ring1, ring2, necklace;

	public Equipment(PartyMember partyMember) {
		owner = partyMember;
	}
	public Item[] toArray()	{
		return new Item[] {weapon, helmet, chest, gloves, boots, ring1, ring2, necklace};
	}
	public void removeAll(Inventory inventory)	{
		if (weapon != null) inventory.addItem(weapon);
		if (helmet != null) inventory.addItem(helmet);
		if (chest != null) inventory.addItem(chest);
		if (gloves != null) inventory.addItem(gloves);
		if (boots != null) inventory.addItem(boots);
		if (ring1 != null) inventory.addItem(ring1);
		if (ring2 != null) inventory.addItem(ring2);
		if (necklace != null) inventory.addItem(necklace);

		weapon = null;
		helmet = null;
		chest = null;
		gloves = null;
		boots = null;
		ring1 = null;
		ring2 = null;
		necklace = null;
	}

	public void equipItem(EquippableItem equippedItem, Inventory inventory)	{
		if (equippedItem.getClass() == Weapon.class)	{
			if (weapon != null)	{
				inventory.addItem(removeWeapon());
			}
			weapon = (Weapon) equippedItem;
			owner.getAttackPower().modifyBase(weapon.getAttack().getActual());
		}
		else if (equippedItem.getClass() == Armor.class)	{
			switch (((Armor) equippedItem).getSlot())	{
			case HELMET:
				if (helmet != null)	{
					inventory.addItem(removeHelmet());
				}
				helmet = (Armor) equippedItem;
				break;
			case CHEST:
				if (chest != null)	{
					inventory.addItem(removeChest());
				}
				chest = (Armor) equippedItem;
				break;
			case GLOVES:
				if (gloves != null)	{
					inventory.addItem(removeGloves());
				}
				gloves = (Armor) equippedItem;
				break;
			case BOOTS:
				if (boots != null)	{
					inventory.addItem(removeBoots());
				}
				boots = (Armor) equippedItem;
				break;
			}
		}
		else if (equippedItem.getClass() == Accessory.class)	{
			switch (((Accessory) equippedItem).getType())	{
			case NECKLACE:
				if (necklace != null)	{
					inventory.addItem(removeNecklace());
				}
				necklace = (Accessory) equippedItem;
				break;
			case RING:
				System.err.println("When equipping a ring, provide an integer as a third argument with 1 for ring1 and 2 for ring2");
				return;
			}
		}
		addStats(equippedItem);
		owner.refresh();
		inventory.removeItem(equippedItem);
	}
	
	public void equipItem(Item equippedItem, Inventory inventory, int ringSlot)	{
		if (equippedItem.getClass() != Accessory.class)	{
			System.err.println("Third argument to be used only with rings");
		}
		else if (((Accessory) equippedItem).getType() != ACCESSORY_TYPE.RING)	{
			System.err.println("Third argument to be used only with rings");
		}
		else if (ringSlot == 1)	{
			if (ring1 != null)	{
				inventory.addItem(removeRing1());
			}
			ring1 = (Accessory) equippedItem;
		}
		else if (ringSlot == 2)	{
			if (ring2 != null)	{
				inventory.addItem(removeRing2());
			}
			ring2 = (Accessory) equippedItem;
		}
		else	{
			System.err.println("Invalid ring slot provided. Use 1 for first ring and 2 for second ring");
		}
	}

	public Weapon getWeapon()	{
		return weapon;
	}
	public Armor getHelmet() {
		return helmet;
	}
	public Armor getChest() {
		return chest;
	}
	public Armor getGloves() {
		return gloves;
	}
	public Armor getBoots() {
		return boots;
	}
	public Accessory getRing1() {
		return ring1;
	}
	public Accessory getRing2() {
		return ring2;
	}
	public Accessory getNecklace() {
		return necklace;
	}

	public Weapon removeWeapon()	{
		removeStats(weapon);
		owner.getAttackPower().modifyBase(-weapon.getAttack().getActual());
		owner.refresh();
		Weapon tmp = weapon;
		weapon = null;
		return tmp;
	}
	public Armor removeHelmet() {
		removeStats(helmet);
		owner.refresh();
		Armor tmp = helmet;
		helmet = null;
		return tmp;
	}
	public Armor removeChest() {
		removeStats(chest);
		owner.refresh();
		Armor tmp = chest;
		chest = null;
		return tmp;
	}
	public Armor removeGloves() {
		removeStats(gloves);
		owner.refresh();
		Armor tmp = gloves;
		gloves = null;
		return tmp;
	}
	public Armor removeBoots() {
		removeStats(boots);
		owner.refresh();
		Armor tmp = boots;
		boots = null;
		return tmp;
	}
	public Accessory removeRing1() {
		removeStats(ring1);
		owner.refresh();
		Accessory tmp = ring1;
		ring1 = null;
		return tmp;
	}
	public Accessory removeRing2() {
		removeStats(ring2);
		owner.refresh();
		Accessory tmp = ring2;
		ring2 = null;
		return tmp;
	}
	public Accessory removeNecklace() {
		removeStats(necklace);
		owner.refresh();
		Accessory tmp = necklace;
		necklace = null;
		return tmp;
	}
	
	private void addStats(EquippableItem item)	{
		owner.getStrength().modifyBuff(item.getStrength().getActual());
		owner.getAgility().modifyBuff(item.getAgility().getActual());
		owner.getIntellect().modifyBuff(item.getIntellect().getActual());
		owner.getSpirit().modifyBuff(item.getSpirit().getActual());
		owner.getStamina().modifyBuff(item.getStamina().getActual());
		owner.getLuck().modifyBuff(item.getLuck().getActual());
		
		owner.getCritChance().modifyBuff(item.getCritChance().getActual());
		owner.getCritDamage().modifyBuff(item.getCritDamage().getActual());
		owner.getHit().modifyBuff(item.getHit().getActual());
		owner.getArmorPen().modifyBuff(item.getArmorPen().getActual());
		owner.getDodge().modifyBuff(item.getDodge().getActual());
		owner.getSpeed().modifyBuff(item.getSpeed().getActual());
		owner.getSpecial().modifyBuff(item.getSpecial().getActual());
		owner.getResist().modifyBuff(item.getResist().getActual());
	}
	
	private void removeStats(EquippableItem item)	{
		owner.getStrength().modifyBuff(-item.getStrength().getActual());
		owner.getAgility().modifyBuff(-item.getAgility().getActual());
		owner.getIntellect().modifyBuff(-item.getIntellect().getActual());
		owner.getSpirit().modifyBuff(-item.getSpirit().getActual());
		owner.getStamina().modifyBuff(-item.getStamina().getActual());
		owner.getLuck().modifyBuff(-item.getLuck().getActual());
		
		owner.getCritChance().modifyBuff(-item.getCritChance().getActual());
		owner.getCritDamage().modifyBuff(-item.getCritDamage().getActual());
		owner.getHit().modifyBuff(-item.getHit().getActual());
		owner.getArmorPen().modifyBuff(-item.getArmorPen().getActual());
		owner.getDodge().modifyBuff(-item.getDodge().getActual());
		owner.getSpeed().modifyBuff(-item.getSpeed().getActual());
		owner.getSpecial().modifyBuff(-item.getSpecial().getActual());
		owner.getResist().modifyBuff(-item.getResist().getActual());
	}
}
