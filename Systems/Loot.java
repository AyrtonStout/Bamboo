package Systems;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Loot implements Item {
	
	private String name;
	private ImageIcon icon;

	
	public Loot(String name)	{
		this.name = name;
	}

	@Override
	public Image getImage() {
		return icon.getImage();
	}

	@Override
	public String getName() {
		return name;
	}

}
