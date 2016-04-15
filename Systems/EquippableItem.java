package Systems;

import javax.swing.*;
import java.io.Serializable;

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
	protected Stat luck;

	protected Stat critChance;
	protected Stat critDamage;
	protected Stat hit;
	protected Stat armorPen;
	protected Stat dodge;
	protected Stat speed;
	protected Stat special;
	protected Stat resist;

	@Override
	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * @param icon The icon representation of the weapon
	 */
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
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
		if (strength == null) {
			return new Stat(0);
		}
		return strength;
	}

	public void setStrength(Stat strength) {
		this.strength = strength;
	}

	public Stat getAgility() {
		if (agility == null) {
			return new Stat(0);
		}
		return agility;
	}

	public void setAgility(Stat agility) {
		this.agility = agility;
	}

	public Stat getSpirit() {
		if (spirit == null) {
			return new Stat(0);
		}
		return spirit;
	}

	public void setSpirit(Stat spirit) {
		this.spirit = spirit;
	}

	public Stat getIntellect() {
		if (intellect == null) {
			return new Stat(0);
		}
		return intellect;
	}

	public void setIntellect(Stat intellect) {
		this.intellect = intellect;
	}

	public Stat getStamina() {
		if (stamina == null) {
			return new Stat(0);
		}
		return stamina;
	}

	public void setStamina(Stat stamina) {
		this.stamina = stamina;
	}

	public Stat getLuck() {
		if (luck == null) {
			return new Stat(0);
		}
		return luck;
	}

	public void setLuck(Stat luck) {
		this.luck = luck;
	}

	public Stat getCritChance() {
		if (critChance == null) {
			return new Stat(0);
		}
		return critChance;
	}

	public void setCritChance(Stat critChance) {
		this.critChance = critChance;
	}

	public Stat getCritDamage() {
		if (critDamage == null) {
			return new Stat(0);
		}
		return critDamage;
	}

	public void setCritDamage(Stat critDamage) {
		this.critDamage = critDamage;
	}

	public Stat getHit() {
		if (hit == null) {
			return new Stat(0);
		}
		return hit;
	}

	public void setHit(Stat hit) {
		this.hit = hit;
	}

	public Stat getArmorPen() {
		if (armorPen == null) {
			return new Stat(0);
		}
		return armorPen;
	}

	public void setArmorPen(Stat armorPen) {
		this.armorPen = armorPen;
	}

	public Stat getDodge() {
		if (dodge == null) {
			return new Stat(0);
		}
		return dodge;
	}

	public void setDodge(Stat dodge) {
		this.dodge = dodge;
	}

	public Stat getSpeed() {
		if (speed == null) {
			return new Stat(0);
		}
		return speed;
	}

	/**
	 * @param speed The combo chance of the weapon
	 */
	public void setSpeed(Stat speed) {
		this.speed = speed;
	}

	public Stat getSpecial() {
		if (special == null) {
			return new Stat(0);
		}
		return special;
	}

	/**
	 * @param chance The chance that the weapon procs its special effect
	 */
	public void setSpecial(Stat special) {
		this.special = special;
	}

	public Stat getResist() {
		if (resist == null) {
			return new Stat(0);
		}
		return resist;
	}

	public void setResist(Stat resist) {
		this.resist = resist;
	}
}
