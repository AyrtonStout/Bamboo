package Systems;

import javax.swing.*;

public class Loot implements Item {

	private String name;
	private ImageIcon icon;

	public Loot(String name) {
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
