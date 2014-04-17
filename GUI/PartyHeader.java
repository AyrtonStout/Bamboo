package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Systems.PartyMember;

public class PartyHeader extends JPanel	{

	private static final long serialVersionUID = 3917011061855068994L;
	private final int HEIGHT = 60;
	private JLabel equip = new JLabel("Equip");
	private JLabel remove = new JLabel("Remove");
	private JLabel removeAll = new JLabel("Remove All");
	private JLabel auto = new JLabel("Auto");
	
	private ImageIcon characterCursor = new ImageIcon("GUI/Resources/Icon_RedArrow.png");
	private ImageIcon optionsCursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	
	public int optionsCursorPosition;
	public int characterCursorPosition;
	
	
	private PartyPanel partyPanel;

	public PartyHeader(Font menuFont, PartyPanel partyPanel)	{
		this.setPreferredSize(new Dimension(600, HEIGHT));
		this.setMinimumSize(new Dimension(600, HEIGHT));
		this.setMaximumSize(new Dimension(600, HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);

		this.partyPanel = partyPanel;
		
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
	
	public void respondToKeyPress(KeyEvent e)	{
		if (partyPanel.getState() == PartyState.OPTIONS)	{
			if (e.getKeyCode() == KeyEvent.VK_UP)	{
				if (optionsCursorPosition == 2 || optionsCursorPosition == 3)	{
					optionsCursorPosition -= 2;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
				if (optionsCursorPosition == 0 || optionsCursorPosition == 2)	{
					optionsCursorPosition += 1;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
				if (optionsCursorPosition == 0 || optionsCursorPosition == 1)	{
					optionsCursorPosition += 2;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
				if (optionsCursorPosition == 1 || optionsCursorPosition == 3)	{
					optionsCursorPosition -= 1;
				}
			}
		}
		if (partyPanel.getState() == PartyState.SLOT_SELECT || partyPanel.getState() == PartyState.CHARACTER_SELECT)	{
			if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
				if (characterCursorPosition > 0)	{
					characterCursorPosition--;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
				if (characterCursorPosition < PartyMember.getPartySize() - 1)	{
					characterCursorPosition++;
				}
			}
		}
	}
	
	public void clear()	{
		optionsCursorPosition = 0;
		characterCursorPosition = 0;
	}
	
	public int getOptionsCursorPosition()	{
		return optionsCursorPosition;
	}
	public int getCharacterCursorPosition()	{
		return characterCursorPosition;
	}

	public void drawOptionsArrow(Graphics g) {
		if (optionsCursorPosition == 0 || optionsCursorPosition == 1)	{
			g.drawImage(optionsCursor.getImage(), 5 + 70 * optionsCursorPosition, 9 , null);
		}
		else if (optionsCursorPosition == 2 || optionsCursorPosition == 3)	{
			g.drawImage(optionsCursor.getImage(), 5 + 70 * (optionsCursorPosition - 2), 39, null);
		}
		
	}

	public void drawCharacterArrow(Graphics g) {
		g.drawImage(characterCursor.getImage(), 226 + characterCursorPosition * 50, 60, null);
	}
}