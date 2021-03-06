package Systems;

import Systems.Enums.ACCESSORY_TYPE;

import javax.swing.*;

/**
 * @author mobius
 *         A type of equippable item with specific benefits
 */
public class Accessory extends EquippableItem {

	private static final long serialVersionUID = -4442101290715158333L;

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
	public Accessory(String name, ACCESSORY_TYPE type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public ImageIcon getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

	public ACCESSORY_TYPE getType() {
		return type;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JTextArea getMainText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JTextArea getStatText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JTextArea getBuffText() {
		// TODO Auto-generated method stub
		return null;
	}
}