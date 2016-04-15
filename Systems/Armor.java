package Systems;

import Systems.Enums.ARMOR_SLOT;
import Systems.Enums.HELMET;

import javax.swing.*;

/**
 * @author mobius
 *         Creates a piece of armor to be worn by the player or NPC
 */
public class Armor extends EquippableItem {

	private static final long serialVersionUID = -7775554574627958590L;

	private ARMOR_SLOT slot;

	private Stat armor;

	private Stat[] secondaryStats;
	private String[] secondaryNames = new String[]{"Strength", "Agility", "Spirit", "Intellect", "Stamina", "Crit Chance",
			"Crit Damage", "Hit", "Armor Pen", "Dodge", "Speed"};

	private JTextArea left = new JTextArea();
	private JTextArea middle = new JTextArea();
	private JTextArea right = new JTextArea();

	private boolean statChange1 = true;
	private boolean statChange2 = true;

	/**
	 * Creates a piece of armor depending on what constructor is invoked.
	 *
	 * @param helmet The type of helmet that is being created
	 */
	public Armor(HELMET helmet) {
		slot = ARMOR_SLOT.HELMET;
//		icon = new ImageIcon("GUI/Resources/Icons/Weapons/Sword_Iron.png");
		if (helmet == HELMET.IRON) {
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
	public ARMOR_SLOT getSlot() {
		return slot;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public JTextArea getMainText() {
		if (statChange1) {
			left.append((armor.toString()) + " " + "Armor");
			statChange1 = false;
		}
		return left;
	}

	@Override
	public JTextArea getStatText() {
		if (statChange2) {
			secondaryStats = new Stat[]{strength, agility, spirit, intellect, stamina, critChance, critDamage, hit, armorPen,
					dodge, speed};
			for (int i = 0; i < secondaryStats.length; i++) {
				if (secondaryStats[i] != null && secondaryStats[i].getBase() > 0) {
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
