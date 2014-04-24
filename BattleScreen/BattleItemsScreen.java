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

import Systems.Consumable;
import Systems.GameData;
import Systems.Item;

public class BattleItemsScreen extends JPanel {

	private static final long serialVersionUID = -6022290414676321151L;

	private GameData data;
	private final int LIST_LENGTH = 4;

	private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private int cursorPosition = 0;
	private int scrollOffset = 0;

	private ItemPanel[] itemList = new ItemPanel[] {new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), 
			new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel()};


	public BattleItemsScreen(GameData data)	{
		this.data = data;

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
			leftPanel.add(itemList[i * 2]);
		}	
		for (int i = 0; i < LIST_LENGTH; i++)	{
			rightPanel.add(itemList[1 + i * 2]);
		}	

		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);

	}

	//	@Override
	//	public void setVisible(boolean b)	{
	//		visible = b;
	//	}

	@Override
	protected void paintComponent(Graphics g)	{	
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(cursor.getImage(), 10 + 300 * (cursorPosition % 2), 11 + 35 * (cursorPosition/2), null);
	}

	public void updateList()	{

		if (data.getInventory().getConsumables().size() == 0)	{
			itemList[0].declareEmpty();
		}
		else	{

			for (int i = 0; i < 6; i++)	{
				if (i < data.getInventory().getConsumables().size())	{
					itemList[i].setItem(data.getInventory().getConsumables().get(i + scrollOffset));
					itemList[i].setVisible(true);
				}
				else	{
					itemList[i].setVisible(false);
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
		if (cursorPosition < 6 && (cursorPosition + 2) < data.getInventory().getConsumables().size())	{
			cursorPosition += 2;
		}
	}
	private void raiseCursor()	{
		if (cursorPosition > 1)	{
			cursorPosition -= 2;
		}
	}
	private void moveCursorRight()	{
		if (cursorPosition % 2 == 0 && (cursorPosition + 1) < data.getInventory().getConsumables().size())	{
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
	public Item getSelectedItem()	{
		if (data.getInventory().getConsumables().size() == 0)	{
			return null;
		}
		return data.getInventory().getConsumables().get(cursorPosition + scrollOffset);
	}



	/**
	 * @author mobius
	 * A subpanel inside of the scrollbox that represents an item. Contains the icon, name, and if the item is stackable,
	 * the quantity of the item.
	 */
	private class ItemPanel extends JPanel	{

		private static final long serialVersionUID = 4342436547521865798L;
		private ImageIcon itemIcon = new ImageIcon();
		private JLabel itemName = new JLabel();
		private JLabel itemQuantity = new JLabel();
		private boolean visible = true;

		public ItemPanel()	{
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			Dimension newDimension = new Dimension(300, 35);
			this.setPreferredSize(newDimension);
			this.setMaximumSize(newDimension);
			this.setMinimumSize(newDimension);
			this.setOpaque(false);

			this.setAlignmentX(LEFT_ALIGNMENT);
			itemName.setAlignmentX(LEFT_ALIGNMENT);

			itemName.setMaximumSize(new Dimension(200, 35));
			itemName.setPreferredSize(new Dimension(200, 35));

			this.add(Box.createHorizontalStrut(65));
			this.add(itemName);
			this.add(itemQuantity);
		}

		@Override
		protected void paintComponent(Graphics g)	{
			if (visible)	{
				g.drawImage(itemIcon.getImage(), 25, 0, null);
			}
		}

		public void setItem(Item item)	{
			itemName.setText(item.getName());
			itemIcon = item.getIcon();
			if (((Consumable) item).getQuantity() > 1)	{
				itemQuantity.setText("x" + Integer.toString(((Consumable) item).getQuantity()));
			}
			else	{
				itemQuantity.setText("");
			}
		}

		public void setVisible(boolean b)	{
			visible = b;
			if (b == false)	{
				itemName.setText("");
				itemQuantity.setText("");
			}
		}

		public void declareEmpty()	{
			itemName.setText("No usable items");
			itemQuantity.setText("");
			itemIcon = new ImageIcon();
		}
	}
}