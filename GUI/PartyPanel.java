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

import Systems.Accessory;
import Systems.Armor;
import Systems.EquippableItem;
import Systems.GameData;
import Systems.Item;
import Systems.PartyMember;
import Systems.Stat;
import Systems.Weapon;

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

	private final int EQUIP = 0;
	private final int REMOVE = 1;
	private final int AUTO = 2;
	private final int REMOVEALL = 3;

	private Font menuFont, boldFont, statFont;

	private PartyHeader topPanel;
	private PartyInfo midPanel;
	private PartyStats bottomLeftPanel;
	private PartyEquipment bottomRightPanel;
	private PartyItems itemPanel;

	JPanel wrapper;

	public PartyPanel(GameData gameData)	{
		data = gameData;

		InputStream stream;
		
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


		topPanel = new PartyHeader(menuFont);
		midPanel = new PartyInfo(menuFont, boldFont);
		bottomLeftPanel = new PartyStats(statFont, boldFont);
		bottomRightPanel = new PartyEquipment(statFont, boldFont);
		itemPanel = new PartyItems(menuFont, data.getInventory());

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
			g.drawImage(optionsCursor.getImage(), 240, 173 + 22 * slotCursorPosition, null);
		}
		if (partyState == PartyState.SLOT_SELECT || partyState == PartyState.CHARACTER_SELECT)	{
			g.drawImage(characterCursor.getImage(), 226 + characterCursorPosition * 50, 60, null);
		}
		if (partyState == PartyState.ITEM_SELECT)	{
			g.drawImage(optionsCursor.getImage(), 260, 180 + 45 * itemPanel.cursorPosition, null);
		}
	}

	public void initialize()	{
		portrait = data.getParty()[0].getPortrait();
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				party[i] = data.getParty()[i].getDown()/*getFunky()*/;
			}
		}
		midPanel.update(data.getParty()[characterCursorPosition]);
		bottomLeftPanel.update(data.getParty()[characterCursorPosition]);
		bottomRightPanel.update(data.getParty()[characterCursorPosition]);
	}
	
	public void update()	{
		if (partyState == PartyState.SLOT_SELECT)	{	
			bottomRightPanel.update(data.getParty()[characterCursorPosition]);
			midPanel.update(data.getParty()[characterCursorPosition]);
			bottomLeftPanel.update(data.getParty()[characterCursorPosition]);
		}
		else if (partyState == PartyState.CHARACTER_SELECT)	{
			bottomRightPanel.update(data.getParty()[characterCursorPosition]);
		}
		else if (partyState == PartyState.ITEM_SELECT)	{
			if (itemPanel.getSelectedItem() != null)	{
				bottomLeftPanel.updateStatChange(data.getParty()[characterCursorPosition], itemPanel.getSelectedItem(), slotCursorPosition);
			}
			itemPanel.update(slotCursorPosition);
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
			itemPanel.cursorPosition = 0;
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
				if (itemPanel.cursorPosition > 0)	{
					itemPanel.cursorPosition--;
				}
				else if (itemPanel.cursorPosition == 0 && itemPanel.scrollOffset > 0)	{
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
				if (itemPanel.cursorPosition < 8 && itemPanel.cursorPosition < itemPanel.elements - 1)	{
					itemPanel.cursorPosition++;
				}
				else if (itemPanel.cursorPosition == 8 && itemPanel.cursorPosition + itemPanel.scrollOffset < itemPanel.elements - 1)	{
					itemPanel.scrollOffset++;
				}
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
				if (itemPanel.getSelectedItem() != null)	{
					if (slotCursorPosition != 6 && slotCursorPosition != 7)	{
						data.getParty()[characterCursorPosition].getEquipment().equipItem(itemPanel.getSelectedItem(), data.getInventory());
					}
					else if (slotCursorPosition == 6)	{
						data.getParty()[characterCursorPosition].getEquipment().equipItem(itemPanel.getSelectedItem(), data.getInventory(), 1);
					}
					else if (slotCursorPosition == 7)	{
						data.getParty()[characterCursorPosition].getEquipment().equipItem(itemPanel.getSelectedItem(), data.getInventory(), 2);
					}
					swapPanels();
				}
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
			return true;
		}
		else	{
			return false;
		}
	}



	private enum PartyState	{

		OPTIONS, SLOT_SELECT, ITEM_SELECT, CHARACTER_SELECT,

	}

}
