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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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
	private boolean swapped = false;

	private ImageIcon characterCursor = new ImageIcon("GUI/Resources/Icon_RedArrow.png");
	private int characterCursorPosition = 0;
	private ImageIcon optionsCursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private int optionsCursorPosition = 0;
	private int slotCursorPosition = 0;
	private int itemCursorPosition = 0;

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
		if (optionsCursorPosition == 0 || optionsCursorPosition == 1)	{
			g.drawImage(optionsCursor.getImage(), 5 + 70 * optionsCursorPosition, 15 , null);
		}
		else if (optionsCursorPosition == 2 || optionsCursorPosition == 3)	{
			g.drawImage(optionsCursor.getImage(), 5 + 70 * (optionsCursorPosition - 2), 41, null);
		}

		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				g.drawImage(party[i].getImage(), 220 + 50 * i, 10, null);
			}
		}
		if (partyState == PartyState.SLOT_SELECT)	{
			g.drawImage(optionsCursor.getImage(), 220, 200 + 22 * slotCursorPosition, null);
		}
		if (partyState == PartyState.SLOT_SELECT || partyState == PartyState.CHARACTER_SELECT)	{
			g.drawImage(characterCursor.getImage(), 226 + characterCursorPosition * 50, 60, null);
		}
		if (partyState == PartyState.ITEM_SELECT)	{
			g.drawImage(optionsCursor.getImage(), 240, 205 + 45 * itemCursorPosition, null);
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
		if (!swapped)	{	
			bottomRightPanel.update();
		}
		else	{
			itemPanel.update();
		}
	}
	
	private void swapPanels()	{
		if (!swapped)	{
			wrapper.remove(bottomRightPanel);
			wrapper.add(itemPanel, BorderLayout.EAST);
			swapped = true;
		}
		else	{
			wrapper.remove(itemPanel);
			wrapper.add(bottomRightPanel, BorderLayout.EAST);
			swapped = false;
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
			}
			
			break;

		case KeyEvent.VK_Z:
			if (partyState == PartyState.OPTIONS)	{
				if (optionsCursorPosition == 0 || optionsCursorPosition == 1)	{
					partyState = PartyState.SLOT_SELECT;
				}
				else if (optionsCursorPosition == 2 || optionsCursorPosition == 3)	{
					partyState = PartyState.CHARACTER_SELECT;
				}
			}
			else if (partyState == PartyState.SLOT_SELECT)	{
				swapPanels();
				partyState = PartyState.ITEM_SELECT;
			}
			break;

		case KeyEvent.VK_X:
			if (partyState == PartyState.CHARACTER_SELECT || partyState == PartyState.SLOT_SELECT)	{
				partyState = PartyState.OPTIONS;
			}
			else if (partyState == PartyState.ITEM_SELECT)	{
				swapPanels();
				partyState = PartyState.SLOT_SELECT;
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
			return true;
		}
		else	{
			return false;
		}
	}

	private class HeaderPanel extends JPanel	{

		private static final long serialVersionUID = 3917011061855068994L;
		private int height = 66;
		private JLabel equip = new JLabel("Equip");
		private JLabel remove = new JLabel("Remove");
		private JLabel removeAll = new JLabel("Remove All");
		private JLabel auto = new JLabel("Auto");

		public HeaderPanel()	{
			this.setPreferredSize(new Dimension(600, height));
			this.setMinimumSize(new Dimension(600, height));
			this.setMaximumSize(new Dimension(600, height));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setOpaque(false);

			JPanel optionsWrapper = new JPanel();
			optionsWrapper.setPreferredSize(new Dimension(200, height));
			optionsWrapper.setMaximumSize(new Dimension(200, height));
			optionsWrapper.setMinimumSize(new Dimension(200, height));
			optionsWrapper.setOpaque(false);

			JPanel spacer = new JPanel();
			spacer.setPreferredSize(new Dimension(200, 5));
			spacer.setOpaque(false);

			JPanel top = new JPanel();
			top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
			top.setPreferredSize(new Dimension(200, height/3));
			top.setOpaque(false);

			JPanel bottom = new JPanel();
			bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
			bottom.setPreferredSize(new Dimension(200, height/3));
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

			optionsWrapper.add(spacer);
			optionsWrapper.add(top);
			optionsWrapper.add(bottom);

			this.add(Box.createHorizontalStrut(20));
			this.add(optionsWrapper);

		}
	}

	private class CenterPanel extends JPanel	{

		private static final long serialVersionUID = -6021372252484376467L;

		private int height = 130;

		private JLabel levelNumberLabel = new JLabel("", SwingConstants.CENTER);
		private JLabel healthNumberLabel = new JLabel();
		private JLabel manaNumberLabel = new JLabel();
		private JLabel xpEarnedText = new JLabel();
		private JLabel xpToGoText = new JLabel();


		public CenterPanel()	{

			this.setPreferredSize(new Dimension(600, height));
			this.setMaximumSize(new Dimension(600, height));
			this.setMinimumSize(new Dimension(600, height));
			this.setOpaque(false);

			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
			leftPanel.setPreferredSize(new Dimension(100, height));
			leftPanel.setMaximumSize(new Dimension(100, height));
			leftPanel.setMinimumSize(new Dimension(100, height));
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

			leftPanel.add(Box.createVerticalStrut(35));
			leftPanel.add(levelTextLabel);
			leftPanel.add(levelNumberLabel);

			JPanel spacer = new JPanel();
			spacer.setPreferredSize(new Dimension(60, height));
			spacer.setMaximumSize(new Dimension(60, height));
			spacer.setMinimumSize(new Dimension(60, height));
			spacer.setOpaque(false);

			JPanel healthManaLabelPanel = new JPanel();
			healthManaLabelPanel.setLayout(new BoxLayout(healthManaLabelPanel, BoxLayout.Y_AXIS));
			healthManaLabelPanel.setPreferredSize(new Dimension(40, height));
			healthManaLabelPanel.setMaximumSize(new Dimension(40, height));
			healthManaLabelPanel.setMinimumSize(new Dimension(40, height));
			healthManaLabelPanel.setOpaque(false);

			JLabel healthLabel = new JLabel("HP");
			healthLabel.setFont(boldFont);
			JLabel manaLabel = new JLabel("MP");
			manaLabel.setFont(boldFont);

			healthManaLabelPanel.add(Box.createVerticalStrut(35));
			healthManaLabelPanel.add(healthLabel);
			healthManaLabelPanel.add(manaLabel);

			JPanel healthManaTextPanel = new JPanel();
			healthManaTextPanel.setLayout(new BoxLayout(healthManaTextPanel, BoxLayout.Y_AXIS));
			healthManaTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
			healthManaTextPanel.setPreferredSize(new Dimension(100, height));
			healthManaTextPanel.setMaximumSize(new Dimension(100, height));
			healthManaTextPanel.setMinimumSize(new Dimension(100, height));
			healthManaTextPanel.setOpaque(false);

			healthNumberLabel.setFont(menuFont);
			manaNumberLabel.setFont(menuFont);
			healthNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);
			manaNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);

			healthManaTextPanel.add(Box.createVerticalStrut(35));
			healthManaTextPanel.add(healthNumberLabel);
			healthManaTextPanel.add(manaNumberLabel);


			JPanel spacer2 = new JPanel();
			spacer2.setPreferredSize(new Dimension(20, height));
			spacer2.setMaximumSize(new Dimension(20, height));
			spacer2.setMinimumSize(new Dimension(20, height));
			spacer2.setOpaque(false);


			JPanel xpLabelPanel = new JPanel();
			xpLabelPanel.setLayout(new BoxLayout(xpLabelPanel, BoxLayout.Y_AXIS));
			xpLabelPanel.setPreferredSize(new Dimension(100, height));
			xpLabelPanel.setMaximumSize(new Dimension(100, height));
			xpLabelPanel.setMinimumSize(new Dimension(100, height));
			xpLabelPanel.setOpaque(false);

			JLabel xpEarnedLabel = new JLabel("XP Earned");
			JLabel xpToGoLabel = new JLabel("XP Level");
			xpEarnedLabel.setFont(boldFont);
			xpToGoLabel.setFont(boldFont);

			xpLabelPanel.add(Box.createVerticalStrut(35));
			xpLabelPanel.add(xpEarnedLabel);
			xpLabelPanel.add(xpToGoLabel);


			JPanel xpTextPanel = new JPanel();
			xpTextPanel.setLayout(new BoxLayout(xpTextPanel, BoxLayout.Y_AXIS));
			xpTextPanel.setPreferredSize(new Dimension(130, height));
			xpTextPanel.setMaximumSize(new Dimension(130, height));
			xpTextPanel.setMinimumSize(new Dimension(130, height));
			xpTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
			xpTextPanel.setOpaque(false);

			xpEarnedText.setFont(menuFont);
			xpToGoText.setFont(menuFont);
			xpEarnedText.setAlignmentX(RIGHT_ALIGNMENT);
			xpToGoText.setAlignmentX(RIGHT_ALIGNMENT);

			xpTextPanel.add(Box.createVerticalStrut(35));
			xpTextPanel.add(xpEarnedText);
			xpTextPanel.add(xpToGoText);

			this.add(leftPanel);
			this.add(spacer);
			this.add(healthManaLabelPanel);
			this.add(healthManaTextPanel);
			this.add(spacer2);
			this.add(xpLabelPanel);
			this.add(xpTextPanel);
		}

		protected void paintComponent(Graphics g)	{
			g.drawImage(portrait.getImage(), 60, 30, null);
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

		JTextPane attributeStats;

		private int leftHeight = 405;

		public StatPanel()	{

			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setPreferredSize(new Dimension(600, leftHeight));
			this.setMaximumSize(new Dimension(600, leftHeight));
			this.setMinimumSize(new Dimension(600, leftHeight));
			this.setOpaque(false);

			/*
			 * Left Panel
			 */
			JPanel attributes = new JPanel();
			attributes.setLayout(new BoxLayout(attributes, BoxLayout.X_AXIS));
			attributes.setPreferredSize(new Dimension(200, leftHeight));
			attributes.setMaximumSize(new Dimension(200, leftHeight));
			attributes.setMinimumSize(new Dimension(200, leftHeight));
			attributes.setOpaque(false);

			JPanel textWrapper = new JPanel();
			textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.Y_AXIS));
			textWrapper.setOpaque(false);
			textWrapper.add(Box.createVerticalStrut(4));

			//Names
			JTextArea attributeNames = new JTextArea("Strength\nAgility\nIntellect\nSpirit\nLuck\n\n" +
					"Attack Power\nSpell Power\nCrit Chance\nCrit Damage\nHit\nArmor Pen\nSpeed\n\nArmor\nStamina\nDodge\nResist");
			attributeNames.setFont(boldFont);
			attributeNames.setPreferredSize(new Dimension(130, leftHeight));
			attributeNames.setEditable(false);
			attributeNames.setOpaque(false);

			//Values
			attributeStats = new JTextPane();
			attributeStats.setFont(statFont);
			attributeStats.setPreferredSize(new Dimension(50, leftHeight));
			attributeStats.setMaximumSize(new Dimension(50, leftHeight));
			attributeStats.setMinimumSize(new Dimension(50, leftHeight));
			attributeStats.setEditable(false);
			attributeStats.setOpaque(false);

			SimpleAttributeSet rightAlign = new SimpleAttributeSet();  
			StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT); 
			attributeStats.selectAll();
			attributeStats.setParagraphAttributes(rightAlign, false);

			attributeStats.setText("5\n5\n5\n5\n5\n\n5\n5\n5\n200%\n5\n5\n5\n\n5\n5\n5\n5");

			textWrapper.add(attributeNames);
			attributes.add(Box.createHorizontalStrut(10));
			attributes.add(textWrapper);
			attributes.add(attributeStats);
			attributes.add(Box.createHorizontalStrut(20));

			this.add(attributes);
		}
		
		public void update()	{
			PartyMember member = data.getParty()[characterCursorPosition];
			attributeStats.setText(member.getStrength().toString() + "\n" + member.getAgility() + "\n" + member.getIntellect() + "\n" +
					member.getSpirit() + "\n" + member.getLuck() + "\n\n" + member.getAttackPower() + "\n" + member.getSpellPower() + "\n" +
					member.getCritChance() + "%\n" + member.getCritDamage() + "%\n" + member.getHit() + "%\n" + member.getArmorPen() + "\n" +
					member.getSpeed() + "\n\n" + member.getArmor() + "\n" + member.getStamina() + "\n" + member.getDodge() + "%\n" + 
					member.getResist() + "%");
		}
	}

	private class EquipmentPanel	extends JPanel{

		private static final long serialVersionUID = -6841005924579611279L;
		
		private int height = 265;
		JTextPane equipmentNames;

		public EquipmentPanel()	{


			this.setLayout(new BorderLayout());
			this.setOpaque(false);

			/*
			 * Top
			 */
			JPanel top = new JPanel();
			top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
			top.setPreferredSize(new Dimension(400, height));
			top.setMaximumSize(new Dimension(400, height));
			top.setMinimumSize(new Dimension(400, height));
			top.setOpaque(false);

			JPanel textWrapper = new JPanel();
			textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.Y_AXIS));
			textWrapper.setOpaque(false);
			textWrapper.add(Box.createVerticalStrut(4));

			//Names
			JTextArea equipmentSlots = new JTextArea("Weapon\nHelmet\nChest\nGloves\nBoots\nNecklace\nRing 1\nRing 2\n\n");
			equipmentSlots.setFont(boldFont);
			equipmentSlots.setPreferredSize(new Dimension(100, height));
			equipmentSlots.setEditable(false);
			equipmentSlots.setOpaque(false);

			//Values
			equipmentNames = new JTextPane();
			equipmentNames.setFont(statFont);
			equipmentNames.setPreferredSize(new Dimension(230, height));
			equipmentNames.setEditable(false);
			equipmentNames.setOpaque(false);
			equipmentNames.selectAll();

			equipmentNames.setText("Empty\nEmpty\nEmpty\nEmpty\nEmpty\nEmpty\nEmpty\nEmpty\n");

			/*
			 * Bottom
			 */
			JPanel status = new JPanel();
			status.setOpaque(false);
			status.setPreferredSize(new Dimension(360, 405 - height));
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
			bottomSpacer.setPreferredSize(new Dimension(40, 405 - height));
			bottomSpacer.setOpaque(false);

			bottomWrapper.add(bottomSpacer, BorderLayout.WEST);
			bottomWrapper.add(status, BorderLayout.EAST);

			/*
			 * Combining
			 */

			textWrapper.add(equipmentSlots);



			top.add(Box.createHorizontalStrut(40));
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

		private ItemPanel[] itemList = new ItemPanel[] {new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), 
				new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel()};

		private ItemSelectionPanel()	{
			this.setPreferredSize(new Dimension(400, 405));
			this.setMaximumSize(new Dimension(400, 405));
			this.setMinimumSize(new Dimension(400, 405));
			
			this.setBackground(Color.RED);
			this.setOpaque(false);
			
			for (int i = 0; i < itemList.length; i++)	{
				this.add(itemList[i]);
			}
			
		}
		
		public void update()	{
			int category;
			if (slotCursorPosition == 0)	{
				category = 0;
			}
			else if (slotCursorPosition > 0 && slotCursorPosition <= 4)	{
				category = 1;
			}
			else {
				category = 2;
			}
			elements = data.getInventory().getCategory(category).size();
			for (int i = 0; i < itemList.length; i++)	{
				if (i < elements)	{
					itemList[i].setItem(data.getInventory().getCategory(category).get(i + scrollOffset));
					itemList[i].setVisible(true);
				}
				else	{
					itemList[i].setVisible(false);
				}
			}
		}
		
		private class ItemPanel extends JPanel	{

			private static final long serialVersionUID = 4342436547521865798L;
			private ImageIcon itemIcon = new ImageIcon("GUI/Resources/sword_ico.png");
			private JLabel itemName = new JLabel();
			private boolean visible = true;

			public ItemPanel()	{
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setPreferredSize(new Dimension(355, 40));
				this.setMaximumSize(new Dimension(395, 40));
				this.setMinimumSize(new Dimension(395, 40));
				this.setBackground(Color.GREEN);
				this.setOpaque(true);

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
		}
	}

	private enum PartyState	{

		OPTIONS, SLOT_SELECT, ITEM_SELECT, CHARACTER_SELECT,

	}

}
