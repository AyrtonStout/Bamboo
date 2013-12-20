package Systems;

import javax.swing.ImageIcon;

public class KeyItem implements Item {
	
	private String name;
	private ImageIcon icon;

	
	public KeyItem(String name)	{
		this.name = name;
	}

	@Override
	public ImageIcon getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

}
