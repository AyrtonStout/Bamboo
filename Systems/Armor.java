package Systems;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import Systems.Enums.ARMOR_TYPE;

/**
 * @author mobius
 * Creates a piece of armor to be worn by the player or NPC
 */
public class Armor implements Item {
	
	private ARMOR_TYPE type;
	private String name;
	private ImageIcon icon;

	
	/**
	 * Creates the base skeleton for some armor. Additional stats should be added individually.
	 * 
	 * @param name The name of the piece of armor
	 * @param type The type of armor (helmet, chest, etc)
	 */
	public Armor(String name, ARMOR_TYPE type)	{
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
	/**
	 * @return The type of slot that the armor is (helmet, chest, etc)
	 */
	public ARMOR_TYPE type()	{
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
