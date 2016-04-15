package GUI;

import Systems.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class PartyStats extends JPanel {

	private static final long serialVersionUID = -6568528749764392354L;
	private final int HEIGHT = 425;
	private final int WIDTH = 235;
	JTextPane attributeStats, statChange;
	StyledDocument doc;
	Style style;
	private String statArrow = "\u2192";

	public PartyStats(Font statFont, Font boldFont) {

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setOpaque(false);

			/*
			 * Left Panel
			 */
		JPanel attributes = new JPanel();
		attributes.setLayout(new BoxLayout(attributes, BoxLayout.X_AXIS));
		attributes.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		attributes.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		attributes.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		attributes.setOpaque(false);

		JPanel textWrapper = new JPanel();
		textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.Y_AXIS));
		textWrapper.setOpaque(false);
		textWrapper.add(Box.createVerticalStrut(4));

		//Names
		JTextArea attributeNames = new JTextArea("Strength\nAgility\nIntellect\nSpirit\nLuck\n\n" +
				"Attack Power\nSpell Power\nCrit Chance\nCrit Damage\nHit\nArmor Pen\nSpeed\nSpecial\n\nArmor\nStamina\nDodge\nResist");
		attributeNames.setFont(boldFont);
		attributeNames.setPreferredSize(new Dimension(130, HEIGHT));
		attributeNames.setEditable(false);
		attributeNames.setOpaque(false);

		//Values
		attributeStats = new JTextPane();
		attributeStats.setFont(statFont);
		attributeStats.setPreferredSize(new Dimension(50, HEIGHT));
		attributeStats.setMaximumSize(new Dimension(50, HEIGHT));
		attributeStats.setMinimumSize(new Dimension(50, HEIGHT));
		attributeStats.setEditable(false);
		attributeStats.setOpaque(false);

		SimpleAttributeSet rightAlign = new SimpleAttributeSet();
		StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
		attributeStats.selectAll();
		attributeStats.setParagraphAttributes(rightAlign, false);

		//Change
		statChange = new JTextPane();
		statChange.setFont(statFont);
		statChange.setPreferredSize(new Dimension(55, HEIGHT));
		statChange.setMaximumSize(new Dimension(55, HEIGHT));
		statChange.setMinimumSize(new Dimension(55, HEIGHT));
		statChange.setOpaque(false);
		doc = statChange.getStyledDocument();
		style = statChange.addStyle("", null);

		textWrapper.add(attributeNames);
		attributes.add(Box.createHorizontalStrut(10));
		attributes.add(textWrapper);
		attributes.add(attributeStats);
		attributes.add(statChange);
		attributes.add(Box.createHorizontalStrut(10));

		this.add(attributes);
	}

	public void update(PartyMember displayedMember) {
		attributeStats.setText(displayedMember.getStrength().toString() + "\n" + displayedMember.getAgility() + "\n" +
				displayedMember.getIntellect() + "\n" + displayedMember.getSpirit() + "\n" + displayedMember.getLuck() + "\n\n" +
				displayedMember.getAttackPower() + "\n" + displayedMember.getSpellPower() + "\n" + displayedMember.getCritChance() + "%\n" +
				displayedMember.getCritDamage() + "%\n" + displayedMember.getHit() + "%\n" + displayedMember.getArmorPen() + "\n" +
				displayedMember.getSpeed() + "\n" + displayedMember.getSpecial() + "\n\n" + displayedMember.getArmor() + "\n" +
				displayedMember.getStamina() + "\n" + displayedMember.getDodge() + "%\n" + displayedMember.getResist() + "%");
		statChange.setText("");
	}

	public void updateStatChange(PartyMember member, EquippableItem newItem, int itemSlot) {
		statChange.setText("");

		if (newItem.getClass() == Weapon.class) {
			compareItems(member, member.getEquipment().getWeapon(), newItem);
		} else if (newItem.getClass() == Armor.class) {
			switch (((Armor) newItem).getSlot()) {
				case HELMET:
					compareItems(member, member.getEquipment().getHelmet(), newItem);
					break;
				case CHEST:
					compareItems(member, member.getEquipment().getChest(), newItem);
					break;
				case GLOVES:
					compareItems(member, member.getEquipment().getGloves(), newItem);
					break;
				case BOOTS:
					compareItems(member, member.getEquipment().getBoots(), newItem);
					break;
			}
		} else if (newItem.getClass() == Accessory.class) {
			switch (((Accessory) newItem).getType()) {
				case NECKLACE:
					compareItems(member, member.getEquipment().getNecklace(), newItem);
					break;
				case RING:
					if (itemSlot == 6) {
						compareItems(member, member.getEquipment().getRing1(), newItem);
						break;
					} else if (itemSlot == 7) {
						compareItems(member, member.getEquipment().getRing2(), newItem);
						break;
					}
			}
		}
	}

	private void compareItems(PartyMember member, EquippableItem original, EquippableItem newItem) {
		appendStatChangeText(member.getStrength(), original == null ? null : original.getStrength(), newItem.getStrength());
		appendStatChangeText(member.getAgility(), original == null ? null : original.getAgility(), newItem.getAgility());
		appendStatChangeText(member.getIntellect(), original == null ? null : original.getIntellect(), newItem.getIntellect());
		appendStatChangeText(member.getSpirit(), original == null ? null : original.getSpirit(), newItem.getSpirit());
		appendStatChangeText(member.getLuck(), original == null ? null : original.getLuck(), newItem.getLuck());

		try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e) {
		}

		AppendAttackPowerChange(member.getAttackPower(), original, newItem);
		AppendSpellPowerChange(member.getSpellPower(), original, newItem);
		appendStatChangeText(member.getCritChance(), original == null ? null : original.getCritChance(), newItem.getCritChance());
		appendStatChangeText(member.getCritDamage(), original == null ? null : original.getCritDamage(), newItem.getCritDamage());
		appendStatChangeText(member.getHit(), original == null ? null : original.getHit(), newItem.getHit());
		appendStatChangeText(member.getArmorPen(), original == null ? null : original.getArmorPen(), newItem.getArmorPen());
		appendStatChangeText(member.getSpeed(), original == null ? null : original.getSpeed(), newItem.getSpeed());
		appendStatChangeText(member.getSpecial(), original == null ? null : original.getSpecial(), newItem.getSpecial());

		try {
			doc.insertString(doc.getLength(), "\n", style);
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e) {
		}
		//TODO ARMOR
		appendStatChangeText(member.getStamina(), original == null ? null : original.getStamina(), newItem.getStamina());
		appendStatChangeText(member.getDodge(), original == null ? null : original.getDodge(), newItem.getDodge());
		appendStatChangeText(member.getResist(), original == null ? null : original.getResist(), newItem.getResist());
	}

	private void appendStatChangeText(Stat characterStat, Stat originalItem, Stat newItem) {
		final int COLUMN_WIDTH = 3;
		int change;

		if (originalItem == null) {
			change = newItem.getActual();
		} else {
			change = -originalItem.getActual() + newItem.getActual();
		}
		String tmp = Integer.toString(change + characterStat.getActual());
		String statText = statArrow;
		for (int i = tmp.length(); i < COLUMN_WIDTH; i++) {
			statText += "  ";
		}
		statText += tmp;

		if (change > 0) {
			try {
				StyleConstants.setForeground(style, new Color(34, 139, 34));
				doc.insertString(doc.getLength(), statText, style);
			} catch (BadLocationException e) {
			}
		} else if (change < 0) {
			try {
				StyleConstants.setForeground(style, Color.RED);
				doc.insertString(doc.getLength(), statText, style);
			} catch (BadLocationException e) {
			}
		}
		try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e) {
		}
	}

	/**
	 * Calculates the change to attack power based on the items passed into the method.
	 *
	 * @param characterStat
	 * @param originalItem
	 * @param newItem
	 */
	private void AppendAttackPowerChange(Stat characterStat, EquippableItem originalItem, EquippableItem newItem) {
		final int COLUMN_WIDTH = 3;
		int change;

		if (originalItem == null) {
			change = newItem.getStrength().getActual();
			if (newItem.getClass() == Weapon.class) {
				change += ((Weapon) (newItem)).getAttack().getActual();
			}
		} else {
			change = -originalItem.getStrength().getActual() + newItem.getStrength().getActual();
			if (newItem.getClass() == Weapon.class) {
				change += -((Weapon) originalItem).getAttack().getActual() + ((Weapon) newItem).getAttack().getActual();
			}
		}
		String tmp = Integer.toString(change + characterStat.getActual());
		String statText = statArrow;
		for (int i = tmp.length(); i < COLUMN_WIDTH; i++) {
			statText += "  ";
		}
		statText += tmp;

		if (change > 0) {
			try {
				StyleConstants.setForeground(style, new Color(34, 139, 34));
				doc.insertString(doc.getLength(), statText, style);
			} catch (BadLocationException e) {
			}
		} else if (change < 0) {
			try {
				StyleConstants.setForeground(style, Color.RED);
				doc.insertString(doc.getLength(), statText, style);
			} catch (BadLocationException e) {
			}
		}
		try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e) {
		}
	}

	/**
	 * Calculates the change to spell power based on the items passed into the method. This calculation is trivial and not
	 * currently needing its own method, but will so later when spell power is calculated on more than intellect.
	 *
	 * @param characterStat
	 * @param originalItem
	 * @param newItem
	 */
	private void AppendSpellPowerChange(Stat characterStat, EquippableItem originalItem, EquippableItem newItem) {
		final int COLUMN_WIDTH = 3;
		int change;

		if (originalItem == null) {
			change = newItem.getIntellect().getActual();
		} else {
			change = -originalItem.getIntellect().getActual() + newItem.getIntellect().getActual();
		}
		String tmp = Integer.toString(change + characterStat.getActual());
		String statText = statArrow;
		for (int i = tmp.length(); i < COLUMN_WIDTH; i++) {
			statText += "  ";
		}
		statText += tmp;

		if (change > 0) {
			try {
				StyleConstants.setForeground(style, new Color(34, 139, 34));
				doc.insertString(doc.getLength(), statText, style);
			} catch (BadLocationException e) {
			}
		} else if (change < 0) {
			try {
				StyleConstants.setForeground(style, Color.RED);
				doc.insertString(doc.getLength(), statText, style);
			} catch (BadLocationException e) {
			}
		}
		try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch (BadLocationException e) {
		}
	}
}
