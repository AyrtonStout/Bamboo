package Systems;

import java.io.Serializable;

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
	public void setWeapon(Weapon weapon) {
		owner.getStrength().modifyBuff(weapon.getStrength());
		owner.refresh();
		this.weapon = weapon;
	}
	public void setHelmet(Armor helmet) {
		this.helmet = helmet;
	}
	public void setChest(Armor chest) {
		this.chest = chest;
	}
	public void setGloves(Armor gloves) {
		this.gloves = gloves;
	}
	public void setBoots(Armor boots) {
		this.boots = boots;
	}
	public void setRing1(Accessory ring1) {
		this.ring1 = ring1;
	}
	public void setRing2(Accessory ring2) {
		this.ring2 = ring2;
	}
	public void setNecklace(Accessory necklace) {
		this.necklace = necklace;
	}
	public Weapon removeWeapon()	{
		owner.getStrength().modifyBuff(-weapon.getStrength());
		owner.refresh();
		Weapon tmp = weapon;
		weapon = null;
		return tmp;
	}
	public Armor removeHelmet() {
		Armor tmp = helmet;
		helmet = null;
		return tmp;
	}
	public Armor removeChest() {
		Armor tmp = chest;
		chest = null;
		return tmp;
	}
	public Armor removeGloves() {
		Armor tmp = gloves;
		gloves = null;
		return tmp;
	}
	public Armor removeBoots() {
		Armor tmp = boots;
		boots = null;
		return tmp;
	}
	public Accessory removeRing1() {
		Accessory tmp = ring1;
		ring1 = null;
		return tmp;
	}
	public Accessory removeRing2() {
		Accessory tmp = ring2;
		ring2 = null;
		return tmp;
	}
	public Accessory removeNecklace() {
		Accessory tmp = necklace;
		necklace = null;
		return tmp;
	}
}
