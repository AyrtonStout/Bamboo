package Systems;

import java.awt.Image;

/**
 * @author mobius
 * Interface to allow all different items to be listed inside the inventory screen.
 */
public interface Item {
	
	/**
	 * @return The icon representation of the Item. Used in the inventory screen
	 */
	public Image getImage();
	/**
	 * @return Name of the Item
	 */
	public String getName();
	
}
