package Systems;

import javax.swing.*;

public class KeyItem implements Item {

	private String name;
	private String description;
	private ImageIcon icon;

	public KeyItem(String name, String description) {
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
		return description;
	}

	@Override
	public JTextArea getMainText() {
		return null;
	}

	@Override
	public JTextArea getStatText() {
		return null;
	}

	@Override
	public JTextArea getBuffText() {
		return null;
	}
}
