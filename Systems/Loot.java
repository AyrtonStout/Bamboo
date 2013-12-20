package Systems;

import javax.swing.ImageIcon;

public class Loot implements Item {
	
	private String name;
	private ImageIcon icon;

	
	public Loot(String name)	{
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
