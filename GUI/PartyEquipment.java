package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import Systems.Item;
import Systems.PartyMember;

public class PartyEquipment extends JPanel{

	private static final long serialVersionUID = -6841005924579611279L;

	final int HEIGHT = 265;
	final int WIDTH = 365;
	JTextPane equipmentNames;

	public PartyEquipment(Font statFont, Font boldFont)	{


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

		title.setFont(boldFont); buffs.setFont(boldFont); debuffs.setFont(boldFont); effects.setFont(boldFont);

		status.add(title); status.add(buffs); status.add(debuffs); status.add(effects);

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

	public void update(PartyMember displayedMember)	{
		Item[] equipment = displayedMember.getEquipment().toArray();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++)	{
			if (equipment[i] != null)	{
				builder.append(equipment[i].getName() + "\n");
			}
			else	{
				builder.append("Empty\n");
			}
		}
		equipmentNames.setText(builder.toString());
	}
}
