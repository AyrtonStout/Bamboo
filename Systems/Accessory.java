package Systems;

import java.awt.Image;

import javax.swing.ImageIcon;

import Systems.Enums.ACCESSORY_TYPE;

/**
 * @author mobius
 * A type of equippable item with specific benefits
 */
public class Accessory implements Item {
	
	private ACCESSORY_TYPE type;
	private String name;
	private ImageIcon icon;

	
	/**
	 * Creates an accessory with the specified name and type.
	 * Create the base item and add properties on afterwards
	 * 
	 * @param name The name of the accessory
	 * @param type The type of accessory (ring, necklace, trinket)
	 */
	public Accessory(String name, ACCESSORY_TYPE type)	{
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
	public ACCESSORY_TYPE type()	{
		return type;
	}

}