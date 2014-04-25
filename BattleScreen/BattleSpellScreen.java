package BattleScreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Spell.Spell;
import Systems.GameData;
import Systems.PartyMember;

public class BattleSpellScreen extends JPanel {

	private static final long serialVersionUID = -6022290414676321151L;

//	private GameData data;
	private PartyMember caster;
	private final int LIST_LENGTH = 4;

	private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private int cursorPosition = 0;
	private int scrollOffset = 0;

	private SpellPanel[] spellList = new SpellPanel[] {new SpellPanel(), new SpellPanel(), new SpellPanel(), new SpellPanel(), 
			new SpellPanel(), new SpellPanel(), new SpellPanel(), new SpellPanel()};


	public BattleSpellScreen(GameData data)	{
//		this.data = data;

		this.setLayout(new BorderLayout());

		Dimension newDimension = new Dimension(600, 150);
		this.setPreferredSize(newDimension);
		this.setMaximumSize(newDimension);
		this.setMinimumSize(newDimension);

		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel[] leftAndRightList = new JPanel[] {leftPanel, rightPanel};
		Dimension subDimension = new Dimension(300, 150);

		for (int i = 0; i < leftAndRightList.length; i++)	{
			leftAndRightList[i].setLayout(new BoxLayout(leftAndRightList[i], BoxLayout.Y_AXIS));
			leftAndRightList[i].setOpaque(false);
			leftAndRightList[i].setPreferredSize(subDimension);
			leftAndRightList[i].setMaximumSize(subDimension);
			leftAndRightList[i].setMinimumSize(subDimension);
			leftAndRightList[i].add(Box.createVerticalStrut(6));

		}

		for (int i = 0; i < LIST_LENGTH; i++)	{
			leftPanel.add(spellList[i * 2]);
		}	
		for (int i = 0; i < LIST_LENGTH; i++)	{
			rightPanel.add(spellList[1 + i * 2]);
		}	

		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g)	{	
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(cursor.getImage(), 10 + 300 * (cursorPosition % 2), 11 + 35 * (cursorPosition/2), null);
	}

	public void updateList(PartyMember caster)	{
		this.caster = caster;
		System.out.println(caster.getKnownSpells().size());
		
		if (caster.getKnownSpells().size() == 0)	{
			spellList[0].declareEmpty();
		}
		else	{
			for (int i = 0; i < 6; i++)	{
				if (i < caster.getKnownSpells().size())	{
					spellList[i].setSpell(caster.getKnownSpells().get(i + scrollOffset));
					spellList[i].setVisible(true);
				}
				else	{
					spellList[i].setVisible(false);
				}
			}
		}
	}

	public void respondToKeyPress(KeyEvent e)	{
		if (e.getKeyCode() == KeyEvent.VK_UP)	{
			raiseCursor();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
			moveCursorRight();
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
			dropCursor();
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
			moveCursorLeft();
		}
	}

	private void dropCursor()	{
		if (cursorPosition < 6 && (cursorPosition + 2) < caster.getKnownSpells().size())	{
			cursorPosition += 2;
		}
	}
	private void raiseCursor()	{
		if (cursorPosition > 1)	{
			cursorPosition -= 2;
		}
	}
	private void moveCursorRight()	{
		if (cursorPosition % 2 == 0 && (cursorPosition + 1) < caster.getKnownSpells().size())	{
			cursorPosition++;
		}
	}
	private void moveCursorLeft()	{
		if (cursorPosition %2 == 1)	{
			cursorPosition--;
		}
	}
	public void resetItemCursor()	{
		cursorPosition = 0;
		scrollOffset = 0;
	}
	public int getCursorPosition()	{
		return cursorPosition;
	}
	/**
	 * Returns the item that is currently selected by the item panel. If there are no usable items, this method
	 * will return null;
	 * 
	 * @return The item at the selected cursor position
	 */
	public Spell getSelectedSpell()	{
		if (caster.getKnownSpells().size() == 0)	{
			return null;
		}
		return caster.getKnownSpells().get(cursorPosition + scrollOffset);
	}



	/**
	 * @author mobius
	 * A subpanel inside of the scrollbox that represents an item. Contains the icon, name, and if the item is stackable,
	 * the quantity of the item.
	 */
	private class SpellPanel extends JPanel	{

		private static final long serialVersionUID = 4342436547521865798L;
		private ImageIcon spellIcon = new ImageIcon();
		private JLabel spellName = new JLabel();
		private JLabel spellCost = new JLabel();
		private boolean visible = true;

		public SpellPanel()	{
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			Dimension newDimension = new Dimension(300, 35);
			this.setPreferredSize(newDimension);
			this.setMaximumSize(newDimension);
			this.setMinimumSize(newDimension);
			this.setOpaque(false);

			this.setAlignmentX(LEFT_ALIGNMENT);
			spellName.setAlignmentX(LEFT_ALIGNMENT);

			spellName.setMaximumSize(new Dimension(200, 35));
			spellName.setPreferredSize(new Dimension(200, 35));
			
			this.add(Box.createHorizontalStrut(65));
			this.add(spellName);
			this.add(spellCost);
		}

		@Override
		protected void paintComponent(Graphics g)	{
			if (visible)	{
				g.drawImage(spellIcon.getImage(), 25, 0, null);
			}
		}

		public void setSpell(Spell spell)	{
			spellName.setText(spell.getName());
			spellIcon = spell.getIcon();
			spellCost.setText(Integer.toString(spell.getManaCost()));
		}

		public void setVisible(boolean b)	{
			visible = b;
			if (b == false)	{
				spellName.setText("");
				spellCost.setText("");
			}
		}

		public void declareEmpty()	{
			spellName.setText("You know no spells :(");
			spellCost.setText("");
			spellIcon = new ImageIcon();
		}
	}
}