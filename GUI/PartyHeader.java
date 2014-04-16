package GUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PartyHeader extends JPanel	{

	private static final long serialVersionUID = 3917011061855068994L;
	private final int HEIGHT = 60;
	private JLabel equip = new JLabel("Equip");
	private JLabel remove = new JLabel("Remove");
	private JLabel removeAll = new JLabel("Remove All");
	private JLabel auto = new JLabel("Auto");

	public PartyHeader(Font menuFont)	{
		this.setPreferredSize(new Dimension(600, HEIGHT));
		this.setMinimumSize(new Dimension(600, HEIGHT));
		this.setMaximumSize(new Dimension(600, HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);

		JPanel optionsWrapper = new JPanel();
		optionsWrapper.setPreferredSize(new Dimension(200, HEIGHT));
		optionsWrapper.setMaximumSize(new Dimension(200, HEIGHT));
		optionsWrapper.setMinimumSize(new Dimension(200, HEIGHT));
		optionsWrapper.setOpaque(false);

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.setPreferredSize(new Dimension(200, HEIGHT/3 + 10));
		top.setOpaque(false);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.setPreferredSize(new Dimension(200, HEIGHT/3 + 2));
		bottom.setOpaque(false);

		equip.setFont(menuFont);
		remove.setFont(menuFont);
		removeAll.setFont(menuFont);
		auto.setFont(menuFont);

		top.add(equip); 
		top.add(Box.createHorizontalStrut(20)); 
		top.add(remove);

		bottom.add(Box.createHorizontalStrut(1));
		bottom.add(auto); 
		bottom.add(Box.createHorizontalStrut(28));
		bottom.add(removeAll);

		optionsWrapper.add(top);
		optionsWrapper.add(bottom);

		this.add(Box.createHorizontalStrut(20));
		this.add(optionsWrapper);


	}
}