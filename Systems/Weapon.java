package Systems;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import Systems.Enums.AXE;
import Systems.Enums.MACE;
import Systems.Enums.WEAPON_TYPE;
import Systems.Enums.SWORD;
import Systems.Enums.DAGGER;

/**
 * @author mobius
 * Type of item used to deal damage (duh). Stats will vary depending
 * on the type of item that it is. Different types of weapons will have
 * a different special property that has a % chance of happening determined
 * by the 'special' variable.
 */
public class Weapon extends EquippableItem implements Serializable {

	private static final long serialVersionUID = 1138349332217177728L;
	
	private WEAPON_TYPE type;
	
	private Stat attack;
	private double attackSpeed;
	
	private Stat[] secondaryStats;
	private String[] secondaryNames = new String[] {"Strength", "Agility", "Intellect", "Spirit", "Stamina", "Crit Chance",
			"Crit Damage", "Hit", "Armor Pen", "Dodge", "Speed"};
	private JTextArea left = new JTextArea();
	private JTextArea middle = new JTextArea();
	private JTextArea right = new JTextArea();
	
	private boolean statChange1 = true;
	private boolean statChange2 = true;
	
	/**
	 * Creates the base skeleton for a weapon. Additional stats should be added individually.
	 * 
	 * @param name The name of the weapon
	 * @param type The type of the weapon (sword, axe, dagger, etc)
	 */
	public Weapon(SWORD sword)	{
		type = WEAPON_TYPE.SWORD;
		icon = new ImageIcon("GUI/Resources/Icons/Weapons/Sword_Iron.png");
		if (sword == SWORD.IRON)	{
			name = "Iron Sword";
			attack = new Stat(10);
			attackSpeed = 1.2;
			special = new Stat(5);
			
			description = "A sturdy sword made from piecing together several iron daggers";
		}
	}
	
	public Weapon(DAGGER dagger)	{
		type = WEAPON_TYPE.DAGGER;
		icon = new ImageIcon("GUI/Resources/Icons/Weapons/Dagger_Gold.png");
		if (dagger == DAGGER.GOLD)	{
			name = "Golden Dagger";
			attack = new Stat(12);
			attackSpeed = 1.8;
			special = new Stat(15);		
			description = "Balanced for throwing but that's not really a good idea";
		}
	}
	
	public Weapon(MACE mace)	{
		type = WEAPON_TYPE.MACE;
		icon = new ImageIcon("GUI/Resources/Icons/Weapons/Mace_Wood.png");
		if (mace == MACE.WOOD)	{
			name = "Club";
			attack = new Stat(35);
			attackSpeed = 2.2;
			special = new Stat(50);
			
			strength = new Stat(10);
			agility = new Stat(5);
			intellect = new Stat(3);
			spirit = new Stat(2);
			stamina = new Stat(7);
			
			description = "Let's go clubbing";
		}
	}
	
	public Weapon(AXE axe)	{
		type = WEAPON_TYPE.AXE;
		icon = new ImageIcon("GUI/Resources/Icons/Weapons/Axe_Iron.png");
		if (axe == AXE.IRON)	{
			name = "Iron Axe";
			attack = new Stat(10);
			attackSpeed = 1.0;
			special = new Stat(10);
			description = "There's a little bit of blue in there";
		}
	}
	
	/**
	 * @return The type of weapon
	 */
	public WEAPON_TYPE getType()	{
		return type;
	}
	/**
	 * @param attack The base attack value of the weapon
	 */
	public void setAttack(int attack)	{
		this.attack.setBase(attack);
	}
	
	public double getAttackSpeed()	{
		return attackSpeed;
	}

	@Override
	public JTextArea getMainText() {
		if (statChange1)	{
			left.append((attack.toString()) + " " + "Damage");
			left.append("\n" + Double.toString(attackSpeed) + " " + "Speed");
			left.append("\n" + (special.toString()) + " " + "Special");
			statChange1 = false;
		}
		return left;
	}
	@Override
	public JTextArea getStatText() {
		if (statChange2)	{
			secondaryStats = new Stat[] {strength, agility, spirit, intellect, stamina, critChance, critDamage, hit, armorPen, 
					dodge, speed};
			for (int i = 0; i < secondaryStats.length; i++)	{
				if (secondaryStats[i] != null && secondaryStats[i].getBase() > 0)	{
					middle.append((secondaryStats[i].toString()) + " " + secondaryNames[i] + "\n");
				}
			}
			statChange2 = false;
		}
		return middle;
	}
	@Override
	public JTextArea getBuffText() {
		return right;
	}
	
	public Stat getAttack()	{
		return attack;
	}
	
}
