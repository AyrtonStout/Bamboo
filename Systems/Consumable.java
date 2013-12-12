package Systems;

import java.awt.Image;

import javax.swing.ImageIcon;

import Systems.Enums.CONSUMABLE_TYPE;

/**
 * @author mobius
 * A one-use type of item. Can have a large variety of effects from health / mana restoration
 * to a permanent stat increase.
 */
public class Consumable implements Item {
	
	private CONSUMABLE_TYPE type;
	private String name;
	private ImageIcon icon;

	
	public Consumable(String name, CONSUMABLE_TYPE type)	{
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
	public CONSUMABLE_TYPE getType()	{
		return type;
	}

}
