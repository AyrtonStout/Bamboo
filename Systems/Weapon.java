package Systems;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

import Systems.Enums.WEAPON_TYPE;

/**
 * @author mobius
 * Type of item used to deal damage (duh). Stats will vary depending
 * on the type of item that it is. Different types of weapons will have
 * a different special property that has a % chance of happening determined
 * by the 'special' variable.
 */
public class Weapon implements Item, Serializable {

	private static final long serialVersionUID = 1138349332217177728L;
	
	private WEAPON_TYPE type;
	private String name;
	private ImageIcon icon;
	
//	private int attack;
//	private double attackSpeed;
//	private int special;
//	
//	private int strength;
//	private int agility;
//	private int spirit;
//	private int intellect;
//	private int stamina;
//	
//	private int critChance;
//	private int critDamage;
//	private int hit;
//	private int expertise;
//	private int armorPen;
	
	
	/**
	 * Creates the base skeleton for a weapon. Additional stats should be added individually.
	 * 
	 * @param name The name of the weapon
	 * @param type The type of the weapon (sword, axe, dagger, etc)
	 */
	public Weapon(String name, WEAPON_TYPE type)	{
		this.name = name;
		this.type = type;
	}

	@Override
	public Image getImage() {
		return icon.getImage();
	}
	@Override
	public String getName() {
		return name;
	}
	/**
	 * @return The type of weapon
	 */
	public WEAPON_TYPE getType()	{
		return type;
	}
	
}
