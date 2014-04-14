package Systems;

import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class EquippableItem implements Item, Serializable {
	
	private static final long serialVersionUID = 9097998745683365731L;
	
	protected String name;
	protected String description;
	protected ImageIcon icon;
	
	protected Stat strength;
	protected Stat agility;
	protected Stat spirit;
	protected Stat intellect;
	protected Stat stamina;
	
	protected Stat critChance;
	protected Stat critDamage;
	protected Stat hit;
	protected Stat armorPen;
	protected Stat dodge;
	protected Stat speed;
	protected Stat special;
	
	@Override
	public ImageIcon getIcon()	{
		return icon;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getDescription() {
		return description;
	}
	
	public Stat getStrength() {
		if (strength == null)	{
			return new Stat(0);
		}
		return strength;
	}
	public Stat getAgility() {
		return agility;
	}
	public Stat getSpirit() {
		return spirit;
	}
	public Stat getIntellect() {
		return intellect;
	}
	public Stat getStamina() {
		return stamina;
	}
	public Stat getCritChance() {
		return critChance;
	}
	public Stat getCritDamage() {
		return critDamage;
	}
	public Stat getHit() {
		return hit;
	}
	public Stat getArmorPen() {
		return armorPen;
	}
	public Stat getDodge() {
		return dodge;
	}
	public Stat getSpeed() {
		return speed;
	}
	public Stat getSpecial()	{
		return special;
	}

	public void setStrength(Stat strength) {
		this.strength = strength;
	}
	public void setAgility(Stat agility) {
		this.agility = agility;
	}
	public void setSpirit(Stat spirit) {
		this.spirit = spirit;
	}
	public void setIntellect(Stat intellect) {
		this.intellect = intellect;
	}
	public void setStamina(Stat stamina) {
		this.stamina = stamina;
	}
	public void setCritChance(Stat critChance) {
		this.critChance = critChance;
	}
	public void setCritDamage(Stat critDamage) {
		this.critDamage = critDamage;
	}
	public void setHit(Stat hit) {
		this.hit = hit;
	}
	public void setArmorPen(Stat armorPen) {
		this.armorPen = armorPen;
	}
	public void setDodge(Stat dodge) {
		this.dodge = dodge;
	}
	/**
	 * @param speed The combo chance of the weapon
	 */
	public void setSpeed(Stat speed) {
		this.speed = speed;
	}
	/**
	 * @param chance The chance that the weapon procs its special effect
	 */
	public void setSpecial(Stat special)	{
		this.special = special;
	}
	/**
	 * @param icon The icon representation of the weapon
	 */
	public void setIcon(ImageIcon icon)	{
		this.icon = icon;
	}

}
