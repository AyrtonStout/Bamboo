package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Systems.EquippableItem;
import Systems.GameData;
import Systems.Item;
import Systems.PartyMember;

public class PartyPanel extends JPanel {

	private static final long serialVersionUID = 9030554443534484268L;

	private GameData data;
	private ImageIcon portrait = new ImageIcon();
	private ImageIcon party1, party2, party3, party4;
	private ImageIcon background = new ImageIcon("GUI/Resources/Party_Background.png");
	private ImageIcon[] party = new ImageIcon[] {party1, party2, party3, party4};

	private PartyState partyState = PartyState.OPTIONS;

	private ImageIcon characterCursor = new ImageIcon("GUI/Resources/Icon_RedArrow.png");
	private int characterCursorPosition = 0;
	private ImageIcon optionsCursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private int optionsCursorPosition = 0;
	private int slotCursorPosition = 0;
	private int itemCursorPosition = 0;

	private final int EQUIP = 0;
	private final int REMOVE = 1;
	private final int AUTO = 2;
	private final int REMOVEALL = 3;

	private InputStream stream;
	private Font menuFont, boldFont, statFont;

	HeaderPanel topPanel;
	CenterPanel midPanel;
	StatPanel bottomLeftPanel;
	EquipmentPanel bottomRightPanel;
	ItemSelectionPanel itemPanel;

	JPanel wrapper;

	public PartyPanel(GameData gameData)	{
		data = gameData;

		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Arial.ttf"));
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			menuFont = baseFont.deriveFont(Font.PLAIN, 20);
			boldFont = baseFont.deriveFont(Font.BOLD, 18);
			statFont = baseFont.deriveFont(Font.PLAIN, 18);
		} catch (FontFormatException | IOException e) {
			System.err.println("Where all mah fonts at??");
			e.printStackTrace();
		}


		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.CYAN);


		topPanel = new HeaderPanel();
		midPanel = new CenterPanel();
		bottomLeftPanel = new StatPanel();
		bottomRightPanel = new EquipmentPanel();
		itemPanel = new ItemSelectionPanel();

		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.setOpaque(false);
		wrapper.add(bottomLeftPanel, BorderLayout.WEST);
		wrapper.add(bottomRightPanel, BorderLayout.EAST);

		this.add(topPanel);
		this.add(midPanel);
		this.add(wrapper);

	}

	@Override
	protected void paintComponent(Graphics g)	{
		g.drawImage(background.getImage(), 0, 0, null);
		if (optionsCursorPosition == EQUIP || optionsCursorPosition == REMOVE)	{
			g.drawImage(optionsCursor.getImage(), 5 + 70 * optionsCursorPosition, 9 , null);
		}
		else if (optionsCursorPosition == AUTO || optionsCursorPosition == REMOVEALL)	{
			g.drawImage(optionsCursor.getImage(), 5 + 70 * (optionsCursorPosition - 2), 39, null);
		}

		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				g.drawImage(party[i].getImage(), 220 + 50 * i, 10, null);
			}
		}
		if (partyState == PartyState.SLOT_SELECT)	{
			g.drawImage(optionsCursor.getImage(), 220, 173 + 22 * slotCursorPosition, null);
		}
		if (partyState == PartyState.SLOT_SELECT || partyState == PartyState.CHARACTER_SELECT)	{
			g.drawImage(characterCursor.getImage(), 226 + characterCursorPosition * 50, 60, null);
		}
		if (partyState == PartyState.ITEM_SELECT)	{
			g.drawImage(optionsCursor.getImage(), 240, 180 + 45 * itemCursorPosition, null);
		}
	}

	public void update()	{
		portrait = data.getParty()[0].getPortrait();
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				party[i] = data.getParty()[i].getDown()/*getFunky()*/;
			}
		}
		midPanel.update();
		bottomLeftPanel.update();
		if (partyState == PartyState.SLOT_SELECT || partyState == PartyState.CHARACTER_SELECT)	{	
			bottomRightPanel.update();
		}
		else if (partyState == PartyState.ITEM_SELECT)	{
			itemPanel.update();
		}
	}

	private void swapPanels()	{
		if (partyState == PartyState.SLOT_SELECT)	{
			wrapper.remove(bottomRightPanel);
			wrapper.add(itemPanel, BorderLayout.EAST);
			partyState = PartyState.ITEM_SELECT;
		}
		else if (partyState == PartyState.ITEM_SELECT)	{
			wrapper.remove(itemPanel);
			wrapper.add(bottomRightPanel, BorderLayout.EAST);
			partyState = PartyState.SLOT_SELECT;
			itemCursorPosition = 0;
		}
	}

	public void respondToKeyPress(KeyEvent e)	{
		switch (e.getKeyCode())	{
		case KeyEvent.VK_LEFT:
			if (partyState == PartyState.OPTIONS)	{
				if (optionsCursorPosition == 1 || optionsCursorPosition == 3)	{
					optionsCursorPosition--;
				}
			}
			else if (partyState == PartyState.SLOT_SELECT || partyState == PartyState.CHARACTER_SELECT)	{
				if (characterCursorPosition > 0)	{
					characterCursorPosition--;
				}
			}
			break;


		case KeyEvent.VK_UP:
			if (partyState == PartyState.OPTIONS)	{
				if (optionsCursorPosition == 2 || optionsCursorPosition == 3)	{
					optionsCursorPosition -= 2;
				}
			}
			else if (partyState == PartyState.SLOT_SELECT)	{
				if (slotCursorPosition > 0)	{
					slotCursorPosition--;
				}
			}
			else if (partyState == PartyState.ITEM_SELECT)	{
				if (itemCursorPosition > 0)	{
					itemCursorPosition--;
				}
				else if (itemCursorPosition == 0 && itemPanel.scrollOffset > 0)	{
					itemPanel.scrollOffset--;
				}
			}
			break;


		case KeyEvent.VK_RIGHT:
			if (partyState == PartyState.OPTIONS)	{
				if (optionsCursorPosition == 0 || optionsCursorPosition == 2)	{
					optionsCursorPosition++;
				}
			}
			else if (partyState == PartyState.SLOT_SELECT || partyState == PartyState.CHARACTER_SELECT)	{
				if (characterCursorPosition < PartyMember.getPartySize() - 1)	{
					characterCursorPosition++;
				}
			}
			break;


		case KeyEvent.VK_DOWN:
			if (partyState == PartyState.OPTIONS)	{
				if (optionsCursorPosition == 0 || optionsCursorPosition == 1)	{
					optionsCursorPosition += 2;
				}
			}
			else if (partyState == PartyState.SLOT_SELECT)	{
				if (slotCursorPosition < 7)	{
					slotCursorPosition++;
				}
			}
			else if (partyState == PartyState.ITEM_SELECT)	{
				if (itemCursorPosition < 8 && itemCursorPosition < itemPanel.elements - 1)	{
					itemCursorPosition++;
				}
				else if (itemCursorPosition == 8 && itemCursorPosition + itemPanel.scrollOffset < itemPanel.elements - 1)	{
					itemPanel.scrollOffset++;
				}
				bottomLeftPanel.updateStatChange(data.getParty()[characterCursorPosition], 
						data.getInventory().getWeapons().get(itemCursorPosition + itemPanel.scrollOffset));
				//TODO
			}

			break;


		case KeyEvent.VK_Z:
			if (partyState == PartyState.OPTIONS)	{
				if (optionsCursorPosition == EQUIP || optionsCursorPosition == REMOVE)	{
					partyState = PartyState.SLOT_SELECT;
				}
				else if (optionsCursorPosition == AUTO || optionsCursorPosition == REMOVEALL)	{
					partyState = PartyState.CHARACTER_SELECT;
				}
			}

			else if (partyState == PartyState.SLOT_SELECT)	{
				if (optionsCursorPosition == EQUIP)	{
					swapPanels();
				}

				else if (optionsCursorPosition == REMOVE)	{
					if (slotCursorPosition == 0 && data.getParty()[characterCursorPosition].getEquipment().getWeapon() != null)	{
						data.getInventory().getWeapons().add(data.getParty()[characterCursorPosition].getEquipment().removeWeapon());
					}
				}
			}

			else if (partyState == PartyState.ITEM_SELECT)	{
				if (slotCursorPosition == 0 && data.getInventory().getWeapons().size() > 0)	{
					data.getParty()[characterCursorPosition].getEquipment().equipItem(data.getInventory().getWeapons().get(
							itemCursorPosition + itemPanel.scrollOffset), data.getInventory());
				}
				else if	(slotCursorPosition >= 1 && slotCursorPosition <= 4 && data.getInventory().getArmor().size() > 0)	{
					data.getParty()[characterCursorPosition].getEquipment().equipItem(data.getInventory().getArmor().get(
							itemCursorPosition + itemPanel.scrollOffset), data.getInventory());	
				}
				else if (slotCursorPosition == 5 && slotCursorPosition == 8 && data.getInventory().getAccessories().size() > 0)	{
					System.out.println(data.getInventory().getAccessories().size());
					data.getParty()[characterCursorPosition].getEquipment().equipItem(data.getInventory().getAccessories().get(
							itemCursorPosition + itemPanel.scrollOffset), data.getInventory());
				}
				else if (slotCursorPosition == 6 && data.getInventory().getAccessories().size() > 0)	{
					data.getParty()[characterCursorPosition].getEquipment().equipItem(data.getInventory().getAccessories().get(
							itemCursorPosition + itemPanel.scrollOffset), data.getInventory(), 1);
				}
				else if (slotCursorPosition == 7 && data.getInventory().getAccessories().size() > 0)	{
					data.getParty()[characterCursorPosition].getEquipment().equipItem(data.getInventory().getAccessories().get(
							itemCursorPosition + itemPanel.scrollOffset), data.getInventory(), 2);
				}
				swapPanels();
			}

			else if (partyState == PartyState.CHARACTER_SELECT)	{
				if (optionsCursorPosition == AUTO)	{
					//TODO AUTO EQUIP BEST EQUIPMENT
				}
				else if (optionsCursorPosition == REMOVEALL)	{
					data.getParty()[characterCursorPosition].getEquipment().removeAll(data.getInventory());
				}
			}
			break;


		case KeyEvent.VK_X:
			if (partyState == PartyState.CHARACTER_SELECT || partyState == PartyState.SLOT_SELECT)	{
				partyState = PartyState.OPTIONS;
			}
			else if (partyState == PartyState.ITEM_SELECT)	{
				swapPanels();
			}
			break;
		}
		update();
	}

	public boolean readyToExitEh()	{
		if (partyState == PartyState.OPTIONS)	{
			optionsCursorPosition = 0;
			characterCursorPosition = 0;
			slotCursorPosition = 0;
			bottomLeftPanel.update();
			bottomRightPanel.update();
			return true;
		}
		else	{
			return false;
		}
	}

	private class HeaderPanel extends JPanel	{

		private static final long serialVersionUID = 3917011061855068994L;
		private final int HEIGHT = 60;
		private JLabel equip = new JLabel("Equip");
		private JLabel remove = new JLabel("Remove");
		private JLabel removeAll = new JLabel("Remove All");
		private JLabel auto = new JLabel("Auto");

		public HeaderPanel()	{
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

	private class CenterPanel extends JPanel	{

		private static final long serialVersionUID = -6021372252484376467L;

		private final int HEIGHT = 110;
		private final int TOP_SPACE = 25;

		private JLabel levelNumberLabel = new JLabel("", SwingConstants.CENTER);
		private JLabel healthNumberLabel = new JLabel();
		private JLabel manaNumberLabel = new JLabel();
		private JLabel xpEarnedText = new JLabel();
		private JLabel xpToGoText = new JLabel();


		public CenterPanel()	{

			this.setPreferredSize(new Dimension(600, HEIGHT));
			this.setMaximumSize(new Dimension(600, HEIGHT));
			this.setMinimumSize(new Dimension(600, HEIGHT));
			this.setOpaque(false);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
			leftPanel.setPreferredSize(new Dimension(100, HEIGHT));
			leftPanel.setMaximumSize(new Dimension(100, HEIGHT));
			leftPanel.setMinimumSize(new Dimension(100, HEIGHT));
			leftPanel.setOpaque(false);

			JLabel levelTextLabel = new JLabel("LVL", SwingConstants.CENTER);
			levelTextLabel.setFont(boldFont);
			levelTextLabel.setPreferredSize(new Dimension(57, 20));
			levelTextLabel.setMaximumSize(new Dimension(57, 20));
			levelTextLabel.setMinimumSize(new Dimension(57, 20));

			levelNumberLabel.setFont(menuFont);
			levelNumberLabel.setPreferredSize(new Dimension(57, 20));
			levelNumberLabel.setMaximumSize(new Dimension(57, 20));
			levelNumberLabel.setMinimumSize(new Dimension(57, 20));

			leftPanel.add(Box.createVerticalStrut(TOP_SPACE));
			leftPanel.add(levelTextLabel);
			leftPanel.add(levelNumberLabel);

			JPanel healthManaLabelPanel = new JPanel();
			healthManaLabelPanel.setLayout(new BoxLayout(healthManaLabelPanel, BoxLayout.Y_AXIS));
			healthManaLabelPanel.setPreferredSize(new Dimension(40, HEIGHT));
			healthManaLabelPanel.setMaximumSize(new Dimension(40, HEIGHT));
			healthManaLabelPanel.setMinimumSize(new Dimension(40, HEIGHT));
			healthManaLabelPanel.setOpaque(false);

			JLabel healthLabel = new JLabel("HP");
			healthLabel.setFont(boldFont);
			healthLabel.setOpaque(false);
			JLabel manaLabel = new JLabel("MP");
			manaLabel.setFont(boldFont);
			manaLabel.setOpaque(false);
			
			healthManaLabelPanel.add(Box.createVerticalStrut(TOP_SPACE));
			healthManaLabelPanel.add(healthLabel);
			healthManaLabelPanel.add(manaLabel);

			JPanel healthManaTextPanel = new JPanel();
			healthManaTextPanel.setLayout(new BoxLayout(healthManaTextPanel, BoxLayout.Y_AXIS));
			healthManaTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
			healthManaTextPanel.setPreferredSize(new Dimension(100, HEIGHT));
			healthManaTextPanel.setMaximumSize(new Dimension(100, HEIGHT));
			healthManaTextPanel.setMinimumSize(new Dimension(100, HEIGHT));
			healthManaTextPanel.setOpaque(false);

			healthNumberLabel.setFont(menuFont);
			manaNumberLabel.setFont(menuFont);
			healthNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);
			manaNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);

			healthManaTextPanel.add(Box.createVerticalStrut(TOP_SPACE));
			healthManaTextPanel.add(healthNumberLabel);
			healthManaTextPanel.add(manaNumberLabel);


			JPanel xpLabelPanel = new JPanel();
			xpLabelPanel.setLayout(new BoxLayout(xpLabelPanel, BoxLayout.Y_AXIS));
			xpLabelPanel.setPreferredSize(new Dimension(100, HEIGHT));
			xpLabelPanel.setMaximumSize(new Dimension(100, HEIGHT));
			xpLabelPanel.setMinimumSize(new Dimension(100, HEIGHT));
			xpLabelPanel.setOpaque(false);

			JLabel xpEarnedLabel = new JLabel("XP Earned");
			JLabel xpToGoLabel = new JLabel("XP Level");
			xpEarnedLabel.setFont(boldFont);
			xpToGoLabel.setFont(boldFont);

			xpLabelPanel.add(Box.createVerticalStrut(TOP_SPACE));
			xpLabelPanel.add(xpEarnedLabel);
			xpLabelPanel.add(xpToGoLabel);


			JPanel xpTextPanel = new JPanel();
			xpTextPanel.setLayout(new BoxLayout(xpTextPanel, BoxLayout.Y_AXIS));
			xpTextPanel.setPreferredSize(new Dimension(130, HEIGHT));
			xpTextPanel.setMaximumSize(new Dimension(130, HEIGHT));
			xpTextPanel.setMinimumSize(new Dimension(130, HEIGHT));
			xpTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
			xpTextPanel.setOpaque(false);

			xpEarnedText.setFont(menuFont);
			xpToGoText.setFont(menuFont);
			xpEarnedText.setAlignmentX(RIGHT_ALIGNMENT);
			xpToGoText.setAlignmentX(RIGHT_ALIGNMENT);

			xpTextPanel.add(Box.createVerticalStrut(TOP_SPACE));
			xpTextPanel.add(xpEarnedText);
			xpTextPanel.add(xpToGoText);

			this.add(leftPanel);
			this.add(Box.createHorizontalStrut(60));
			this.add(healthManaLabelPanel);
			this.add(healthManaTextPanel);
			this.add(Box.createHorizontalStrut(20));
			this.add(xpLabelPanel);
			this.add(xpTextPanel);
		}

		protected void paintComponent(Graphics g)	{
			super.paintComponent(g);
			g.drawImage(portrait.getImage(), 60, TOP_SPACE - 10, null);
		}

		public void update()	{
			PartyMember member = data.getParty()[characterCursorPosition];
			portrait = data.getParty()[characterCursorPosition].getPortrait();
			levelNumberLabel.setText(Integer.toString(member.getLevel()));
			healthNumberLabel.setText(member.getCurrentHealth().toString() + "/" + member.getMaxHealth().toString());
			manaNumberLabel.setText(member.getCurrentMana().toString() + "/" + member.getMaxMana().toString());
			xpEarnedText.setText(member.getXpTotalEarned() + " (-" + 
					Integer.toString(member.getXpRequirement() - member.getXpThisLevel()) + ")");
			xpToGoText.setText(member.getXpThisLevel() + "/" + member.getXpRequirement());
		}
	}


	private class StatPanel	extends JPanel {

		private static final long serialVersionUID = -6568528749764392354L;

		JTextPane attributeStats, statChange;
		StyledDocument doc;
		Style style;

		private final int HEIGHT = 425;
		private final int WIDTH = 230;
		
		public StatPanel()	{

			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
			this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
			this.setOpaque(false);
//			this.setBackground(Color.GREEN);

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
			statChange.setPreferredSize(new Dimension(50, HEIGHT));
			statChange.setMaximumSize(new Dimension(50, HEIGHT));
			statChange.setMinimumSize(new Dimension(50, HEIGHT));
			doc = statChange.getStyledDocument();
			
			style = statChange.addStyle("I'm a Style", null);
	        StyleConstants.setForeground(style, Color.red);
	        
	        try { doc.insertString(doc.getLength(), "BLAH ",style); }
	        catch (BadLocationException e){}
	        
	        StyleConstants.setForeground(style, Color.blue);

	        try { doc.insertString(doc.getLength(), "BLEH",style); }
	        catch (BadLocationException e){}

			textWrapper.add(attributeNames);
			attributes.add(Box.createHorizontalStrut(10));
			attributes.add(textWrapper);
			attributes.add(attributeStats);
			attributes.add(statChange);
			attributes.add(Box.createHorizontalStrut(10));

			this.add(attributes);
		}

		public void update()	{
			PartyMember member = data.getParty()[characterCursorPosition];
			attributeStats.setText(member.getStrength().toString() + "\n" + member.getAgility() + "\n" + member.getIntellect() + "\n" +
					member.getSpirit() + "\n" + member.getLuck() + "\n\n" + member.getAttackPower() + "\n" + member.getSpellPower() + "\n" +
					member.getCritChance() + "%\n" + member.getCritDamage() + "%\n" + member.getHit() + "%\n" + member.getArmorPen() + "\n" +
					member.getSpeed() + "\n" + member.getSpecial() + "\n\n" + member.getArmor() + "\n" + member.getStamina() + "\n" + 
					member.getDodge() + "%\n" +	member.getResist() + "%");
		}
		public void updateStatChange(PartyMember member, EquippableItem newItem)	{
			statChange.setText("");
			if (member.getStrength().getActual() > member.getStrength().getActual() - member.getEquipment().getWeapon().getStrength().getActual()
					+ newItem.getStrength().getActual())	{
				try {
			        StyleConstants.setForeground(style, Color.red);
					doc.insertString(doc.getLength(), "➤" + Integer.toString(member.getStrength().getActual() - member.getEquipment().getWeapon().getStrength().getActual()
							+ newItem.getStrength().getActual()) + "\n", style);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
			else	{
				try {
			        StyleConstants.setForeground(style, Color.green);
					doc.insertString(doc.getLength(), "➤" + Integer.toString(member.getStrength().getActual() - member.getEquipment().getWeapon().getStrength().getActual()
							+ newItem.getStrength().getActual()) + "\n", style);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class EquipmentPanel extends JPanel{

		private static final long serialVersionUID = -6841005924579611279L;

		final int HEIGHT = 265;
		final int WIDTH = 370;
		JTextPane equipmentNames;

		public EquipmentPanel()	{


			this.setLayout(new BorderLayout());
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
			this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
			this.setOpaque(false);
//			this.setBackground(Color.RED);

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

		public void update()	{
			PartyMember member = data.getParty()[characterCursorPosition];
			Item[] equipment = member.getEquipment().toArray();
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

	private class ItemSelectionPanel	extends JPanel {

		private static final long serialVersionUID = 8869703204051577618L;

		private int elements;
		private int scrollOffset = 0;
		final int WIDTH = 370;
		final int HEIGHT = 405;

		private ItemPanel[] itemList = new ItemPanel[] {new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), 
				new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel()};

		private ItemSelectionPanel()	{
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
			this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

			this.setBackground(Color.RED);
			this.setOpaque(false);

			for (int i = 0; i < itemList.length; i++)	{
				this.add(itemList[i]);
			}

		}

		public void update()	{
			if (slotCursorPosition == 0)	{
				elements = data.getInventory().getWeapons().size();
				for (int i = 0; i < itemList.length; i++)	{
					if (i < data.getInventory().getWeapons().size())	{
						itemList[i].setItem(data.getInventory().getWeapons().get(i + scrollOffset));
						itemList[i].setVisible(true);
					}
					else	{
						itemList[i].setVisible(false);
					}
				}
			}
			else if (slotCursorPosition > 0 && slotCursorPosition <= 4)	{
				elements = data.getInventory().getArmor().size();
				for (int i = 0; i < itemList.length; i++)	{
					if (i < data.getInventory().getArmor().size())	{
						itemList[i].setItem(data.getInventory().getArmor().get(i + scrollOffset));
						itemList[i].setVisible(true);
					}
					else	{
						itemList[i].setVisible(false);
					}
				}
			}
			else {
				elements = data.getInventory().getAccessories().size();
				for (int i = 0; i < itemList.length; i++)	{
					if (i < data.getInventory().getAccessories().size())	{
						itemList[i].setItem(data.getInventory().getAccessories().get(i + scrollOffset));
						itemList[i].setVisible(true);
					}
					else	{
						itemList[i].setVisible(false);
					}
				}
			}

			if (itemList[0].visibleEh() == false)	{
				itemList[0].clear();
				itemList[0].setVisible(true);
			}
		}
		
		public EquippableItem getSelectedItem()	{
			return null;
		}

		private class ItemPanel extends JPanel	{

			private static final long serialVersionUID = 4342436547521865798L;
			private ImageIcon itemIcon = new ImageIcon();
			private JLabel itemName = new JLabel();
			private boolean visible = true;

			public ItemPanel()	{
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setPreferredSize(new Dimension(355, 40));
				this.setMaximumSize(new Dimension(395, 40));
				this.setMinimumSize(new Dimension(395, 40));
				this.setOpaque(false);

				this.setAlignmentX(LEFT_ALIGNMENT);
				itemName.setAlignmentX(LEFT_ALIGNMENT);
				itemName.setFont(statFont);

				itemName.setMaximumSize(new Dimension(260, 40));
				itemName.setPreferredSize(new Dimension(260, 40));

				this.add(Box.createHorizontalStrut(100));
				this.add(itemName);
			}

			@Override
			protected void paintComponent(Graphics g)	{
				if (visible)	{
					g.drawImage(itemIcon.getImage(), 50, 0, null);
				}
			}

			public void setItem(Item item)	{
				itemName.setText(item.getName());
				itemIcon = item.getIcon();
			}
			public void setVisible(boolean b)	{
				visible = b;
				if (b == false)	{
					itemName.setText("");
				}
			}
			public void clear()	{
				itemName.setText("EMPTY");
				itemIcon = new ImageIcon();
			}
			public boolean visibleEh()	{
				return visible;
			}
		}
	}

	private enum PartyState	{

		OPTIONS, SLOT_SELECT, ITEM_SELECT, CHARACTER_SELECT,

	}

}
