package Systems;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import Systems.Enums.ARMOR_SLOT;
import Systems.Enums.HELMET;

/**
 * @author mobius
 * Creates a piece of armor to be worn by the player or NPC
 */
public class Armor implements Item {
	
	private ARMOR_SLOT slot;
	private String name;
	private String description;
	private ImageIcon icon;
	
	private Stat armor;
	
	private Stat strength;
	private Stat agility;
	private Stat spirit;
	private Stat intellect;
	private Stat stamina;
	
	private Stat critChance;
	private Stat critDamage;
	private Stat hit;
	private Stat armorPen;
	private Stat dodge;
	private Stat speed;
	
	private Stat[] secondaryStats;
	private String[] secondaryNames = new String[] {"Strength", "Agility", "Spirit", "Intellect", "Stamina", "Crit Chance",
			"Crit Damage", "Hit", "Armor Pen", "Dodge", "Speed"};
	
	private JTextArea left = new JTextArea();
	private JTextArea middle = new JTextArea();
	private JTextArea right = new JTextArea();
	
	private boolean statChange1 = true;
	private boolean statChange2 = true;

	
	/**
	 * Creates the base skeleton for some armor. Additional stats should be added individually.
	 * 
	 * @param name The name of the piece of armor
	 * @param type The type of armor (iron, gold, etc)
	 * @param slot The armor slot the item is equipped to (feet, helm, chest, etc)
	 */
	public Armor(HELMET helmet)	{
		slot = ARMOR_SLOT.HELMET;
//		icon = new ImageIcon("GUI/Resources/Icons/Weapons/Sword_Iron.png");
		if (helmet == HELMET.IRON)	{
			name = "Iron Helm";
			armor = new Stat(10);
			
			description = "Unpleasantly noisy when struck";
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
	 * @return The type of slot that the armor is (helmet, chest, etc)
	 */
	public ARMOR_SLOT getSlot()	{
		return slot;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public JTextArea getMainText() {
		if (statChange1)	{
			left.append((armor.toString()) + " " + "Armor");
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

}
