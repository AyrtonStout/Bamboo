package Systems;

import java.io.Serializable;

public class Equipment implements Serializable {

	private static final long serialVersionUID = -8207154870997336759L;

	Weapon weapon;
	Armor helmet, chest, gloves, boots;
	Accessory ring1, ring2, necklace;
	
	public Item[] toArray()	{
		return new Item[] {weapon, helmet, chest, gloves, boots, ring1, ring2, necklace};
	}
	public Weapon getWeapon()	{
		return weapon;
	}
	public Armor gethelmet()	{
		return helmet;
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
}
