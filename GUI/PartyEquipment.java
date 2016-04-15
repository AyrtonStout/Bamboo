package GUI;

import Systems.Item;
import Systems.PartyMember;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PartyEquipment extends JPanel {

	private static final long serialVersionUID = -6841005924579611279L;

	private final int HEIGHT = 265;
	private final int WIDTH = 365;
	private JTextPane equipmentNames;

	private int cursorPosition;
	private ImageIcon optionsCursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");

	public PartyEquipment(Font statFont, Font boldFont) {

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setOpaque(false);
		
		/*
		 * Top
		 */
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.setPreferredSize(new Dimension(WIDTH - 20, HEIGHT));
		top.setMaximumSize(new Dimension(WIDTH - 20, HEIGHT));
		top.setMinimumSize(new Dimension(WIDTH - 20, HEIGHT));
		top.setOpaque(false);

		JPanel textWrapper = new JPanel();
		textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.Y_AXIS));
		textWrapper.setOpaque(false);
		textWrapper.add(Box.createVerticalStrut(4));

		//Names
		JTextArea equipmentSlots = new JTextArea("Weapon\nHelmet\nChest\nGloves\nBoots\nNecklace\nRing 1\nRing 2\n\n");
		equipmentSlots.setFont(boldFont);
		equipmentSlots.setPreferredSize(new Dimension(100, HEIGHT));
		equipmentSlots.setEditable(false);
		equipmentSlots.setOpaque(false);

		//Values
		equipmentNames = new JTextPane();
		equipmentNames.setFont(statFont);
		equipmentNames.setPreferredSize(new Dimension(230, HEIGHT));
		equipmentNames.setEditable(false);
		equipmentNames.setOpaque(false);
		equipmentNames.selectAll();

		/*
		 * Bottom
		 */
		JPanel status = new JPanel();
		status.setOpaque(false);
		status.setPreferredSize(new Dimension(WIDTH - 30, 405 - HEIGHT));
		status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("                       Status");
		JLabel buffs = new JLabel("Buffs");
		JLabel debuffs = new JLabel("Debuffs");
		JLabel effects = new JLabel("Effects");

		title.setFont(boldFont);
		buffs.setFont(boldFont);
		debuffs.setFont(boldFont);
		effects.setFont(boldFont);

		status.add(title);
		status.add(buffs);
		status.add(debuffs);
		status.add(effects);

		JPanel bottomWrapper = new JPanel();
		bottomWrapper.setLayout(new BorderLayout());
		bottomWrapper.setOpaque(false);
		JPanel bottomSpacer = new JPanel();
		bottomSpacer.setPreferredSize(new Dimension(40, 405 - HEIGHT));
		bottomSpacer.setOpaque(false);

		bottomWrapper.add(bottomSpacer, BorderLayout.WEST);
		bottomWrapper.add(status, BorderLayout.EAST);

		/*
		 * Combining
		 */

		textWrapper.add(equipmentSlots);

		top.add(Box.createHorizontalStrut(30));
		top.add(textWrapper);
		top.add(equipmentNames);
		top.add(Box.createHorizontalStrut(15));

		this.add(top, BorderLayout.NORTH);
		this.add(bottomWrapper, BorderLayout.SOUTH);
	}

	public void update(PartyMember displayedMember) {
		Item[] equipment = displayedMember.getEquipment().toArray();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			if (equipment[i] != null) {
				builder.append(equipment[i].getName() + "\n");
			} else {
				builder.append("Empty\n");
			}
		}
		equipmentNames.setText(builder.toString());
	}

	public void respondToKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (cursorPosition < 7) {
				cursorPosition++;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (cursorPosition > 0) {
				cursorPosition--;
			}
		}
	}

	public void clear() {
		cursorPosition = 0;
	}

	public int getCursorPosition() {
		return cursorPosition;
	}

	public void drawArrow(Graphics g) {
		g.drawImage(optionsCursor.getImage(), 240, 173 + 22 * cursorPosition, null);
	}
}
