package Systems;

import javax.swing.*;

/**
 * @author mobius
 *         Interface to allow all different items to be listed inside the inventory screen.
 */
public interface Item {

	/**
	 * @return The icon representation of the Item. Used in the inventory screen
	 */
	public ImageIcon getIcon();

	/**
	 * @return Name of the Item
	 */
	public String getName();

	/**
	 * @return A one line explanation of the item
	 */
	public String getDescription();

	/**
	 * @return The components to the item that are always present
	 */
	public JTextArea getMainText();

	/**
	 * @return The possible stat buffs that the item gives
	 */
	public JTextArea getStatText();

	/**
	 * @return The enchantment or other augments the item has
	 */
	public JTextArea getBuffText();
}
