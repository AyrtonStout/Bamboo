package Systems;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

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
