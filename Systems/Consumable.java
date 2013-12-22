package Systems;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import Systems.Enums.CONSUMABLE_TYPE;
import Systems.Enums.POTION;

/**
 * @author mobius
 * A one-use type of item. Can have a large variety of effects from health / mana restoration
 * to a permanent stat increase.
 */
public class Consumable implements Item, Serializable {
	
	private static final long serialVersionUID = -1162361705759888121L;
	
	private CONSUMABLE_TYPE type;
	private String name;
	private String description;
	private ImageIcon icon;
	
	private int healthRestore;
	private int manaRestore;
	private int stackQuantity = 0;

	private JTextArea left = new JTextArea();
	
	private boolean statChange = true;
	
	public Consumable(POTION potion)	{
		type = CONSUMABLE_TYPE.POTION;
		switch (potion)	{
		case HEALTH_SMALL:
			name = "Lesser Health Potion";
			icon = new ImageIcon("GUI/Resources/Icons/Consumables/HealthPot_Small.png");
			healthRestore = 20;
			
			description = "A small health potion. Tastes tangy";
			break;
		case HEALTH_LARGE:
			name = "Greater Health Potion";
			icon = new ImageIcon("GUI/Resources/Icons/Consumables/HealthPot_Large.png");
			healthRestore = 50;
			
			description = "A large health potion for a manly thirst";
			break;
		case MANA_SMALL:
			name = "Lesser Mana Potion";
			icon = new ImageIcon("GUI/Resources/Icons/Consumables/ManaPot_Small.png");
			manaRestore = 20;
			
			description = "Brought back as Lesser Mana Potion: Classic";
			break;
		case MANA_LARGE:
			name = "Greater Mana Potion";
			icon = new ImageIcon("GUI/Resources/Icons/Consumables/ManaPot_Large.png");
			manaRestore = 50;
			
			description = "A cure for that burning mana craving";
			break;
		case YELLOW_SMALL:
			name = "Lesser Hybrid Potion";
			icon = new ImageIcon("GUI/Resources/Icons/Consumables/YellowPot_Small.png");
			healthRestore = 15;
			manaRestore = 15;
			
			description = "More than the sum of its parts?";
			break;
		case YELLOW_LARGE:
			name = "Greater Hybrid Potion";
			icon = new ImageIcon("GUI/Resources/Icons/Consumables/YellowPot_Large.png");
			healthRestore = 35;
			manaRestore = 35;
			
			description = "Since when do red + blue make yellow?";
			break;
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
	public CONSUMABLE_TYPE getType()	{
		return type;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public JTextArea getMainText() {
		if (statChange)	{
			if (healthRestore > 0)	{
				left.append(Integer.toString(healthRestore) + " Health\n");
			}
			if (manaRestore > 0)	{
				left.append(Integer.toString(manaRestore) + " Mana\n");
			}
			statChange = false;
		}
		return left;
	}

	@Override
	public JTextArea getStatText() {
		return null;
	}

	@Override
	public JTextArea getBuffText() {
		return null;
	}
	public void raiseQuantity()	{
		stackQuantity++;
	}
	public void dropQuantity()	{
		stackQuantity--;
	}
	public int getQuantity()	{
		return stackQuantity;
	}
	

}
