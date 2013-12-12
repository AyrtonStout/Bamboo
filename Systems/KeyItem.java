package Systems;

import java.awt.Image;

import javax.swing.ImageIcon;

public class KeyItem implements Item {
	
	private String name;
	private ImageIcon icon;

	
	public KeyItem(String name)	{
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
