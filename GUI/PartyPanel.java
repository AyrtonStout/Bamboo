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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Systems.GameData;
import Systems.PartyMember;

public class PartyPanel extends JPanel {

	private static final long serialVersionUID = 9030554443534484268L;

	private GameData data;
	private ImageIcon party1, party2, party3, party4;
	private ImageIcon background = new ImageIcon("GUI/Resources/Party_Background.png");
	private ImageIcon[] party = new ImageIcon[] {party1, party2, party3, party4};

	private PartyState partyState = PartyState.OPTIONS;
	private PartyMember selectedMember;

	private final int EQUIP = 0;
	private final int REMOVE = 1;
	private final int AUTO = 2;
	private final int REMOVEALL = 3;

	private Font menuFont, boldFont, statFont;

	private PartyHeader header;
	private PartyInfo info;
	private PartyStats stats;
	private PartyEquipment equipment;
	private PartyItems items;

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


		header = new PartyHeader(menuFont, this);
		info = new PartyInfo(menuFont, boldFont);
		stats = new PartyStats(statFont, boldFont);
		equipment = new PartyEquipment(statFont, boldFont);
		items = new PartyItems(menuFont, data.getInventory());

		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.setOpaque(false);
		wrapper.add(stats, BorderLayout.WEST);
		wrapper.add(equipment, BorderLayout.EAST);

		this.add(header);
		this.add(info);
		this.add(wrapper);

	}

	@Override
	protected void paintComponent(Graphics g)	{
		g.drawImage(background.getImage(), 0, 0, null);
		header.drawOptionsArrow(g);			

		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				g.drawImage(party[i].getImage(), 220 + 50 * i, 10, null);
			}
		}
		if (partyState == PartyState.SLOT_SELECT)	{
			header.drawCharacterArrow(g);
			equipment.drawArrow(g);
		}
		if (partyState == PartyState.CHARACTER_SELECT)	{
			header.drawCharacterArrow(g);
		}
		if (partyState == PartyState.ITEM_SELECT)	{
			items.drawArrow(g);
		}
	}

	public void initialize()	{
		selectedMember = data.getParty()[0];
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				party[i] = data.getParty()[i].getDown()/*getFunky()*/;
			}
		}
		info.update(selectedMember);
		stats.update(selectedMember);
		equipment.update(selectedMember);
	}
	
	public void update()	{
		selectedMember = data.getParty()[header.getCharacterCursorPosition()];
		if (partyState == PartyState.SLOT_SELECT)	{	
			equipment.update(selectedMember);
			info.update(selectedMember);
			stats.update(selectedMember);
		}
		else if (partyState == PartyState.CHARACTER_SELECT)	{
			info.update(selectedMember);
			stats.update(selectedMember);
		}
		else if (partyState == PartyState.ITEM_SELECT)	{
			if (items.getSelectedItem() != null)	{
				stats.updateStatChange(selectedMember, items.getSelectedItem(), equipment.getCursorPosition());
			}
			items.update(equipment.getCursorPosition(), selectedMember);
		}
	}

	private void swapPanels()	{
		if (partyState == PartyState.SLOT_SELECT)	{
			wrapper.remove(equipment);
			wrapper.add(items, BorderLayout.EAST);
			partyState = PartyState.ITEM_SELECT;
		}
		else if (partyState == PartyState.ITEM_SELECT)	{
			wrapper.remove(items);
			wrapper.add(equipment, BorderLayout.EAST);
			partyState = PartyState.SLOT_SELECT;
			items.cursorPosition = 0;
		}
	}

	public void respondToKeyPress(KeyEvent e)	{
		switch (partyState)	{
		case OPTIONS:
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_DOWN || 
					e.getKeyCode() == KeyEvent.VK_LEFT)	{
				header.respondToKeyPress(e);
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				if (header.getOptionsCursorPosition() == EQUIP || header.getOptionsCursorPosition() == REMOVE)	{
					partyState = PartyState.SLOT_SELECT;
				}
				else if (header.getOptionsCursorPosition() == AUTO || header.getOptionsCursorPosition() == REMOVEALL)	{
					partyState = PartyState.CHARACTER_SELECT;
				}
			}
			break;
		case CHARACTER_SELECT:
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_DOWN || 
					e.getKeyCode() == KeyEvent.VK_LEFT)	{
				header.respondToKeyPress(e);
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				if (header.getOptionsCursorPosition() == AUTO)	{
					//TODO AUTO EQUIP BEST EQUIPMENT
				}
				else if (header.getOptionsCursorPosition() == REMOVEALL)	{
					selectedMember.getEquipment().removeAll(data.getInventory());
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				partyState = PartyState.OPTIONS;
			}
			break;
		case SLOT_SELECT:
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
				equipment.respondToKeyPress(e);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)	{
				header.respondToKeyPress(e);
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				if (header.getOptionsCursorPosition() == EQUIP)	{
					swapPanels();
				}
				else if (header.getOptionsCursorPosition() == REMOVE)	{
					if (equipment.getCursorPosition() == 0 && selectedMember.getEquipment().getWeapon() != null)	{
						data.getInventory().getWeapons().add(selectedMember.getEquipment().removeWeapon());
					} //TODO make this universal
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				partyState = PartyState.OPTIONS;
			}

			break;
		case ITEM_SELECT:
			if (e.getKeyCode() == KeyEvent.VK_Z && items.getSelectedItem() != null)	{
				items.respondToKeyPress(e);
				swapPanels();
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)	{
				items.respondToKeyPress(e);
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				swapPanels();
			}
			break;
		}
		update();
	}

	public boolean readyToExitEh()	{
		if (partyState == PartyState.OPTIONS)	{
			header.clear();
			equipment.clear();
			return true;
		}
		else	{
			return false;
		}
	}
	
	public PartyState getState()	{
		return partyState;
	}


}
