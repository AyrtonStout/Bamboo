package Battle;

import java.awt.Color;
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
	private static final int CONSUMABLES = 3;

	private GameData data;

	private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private int cursorPosition = 0;
	private int scrollOffset = 0;

	private ItemPanel[] itemList = new ItemPanel[] {new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), 
			new ItemPanel(), new ItemPanel(), new ItemPanel()};


	public BattleItemsScreen(GameData data)	{
		this.data = data;

		this.setBackground(Color.GREEN);
		Dimension newDimension = new Dimension(600, 150);
		this.setPreferredSize(newDimension);
		this.setMaximumSize(newDimension);
		this.setMinimumSize(newDimension);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(Box.createVerticalStrut(10));

		for (int i = 0; i < itemList.length; i++)	{
			this.add(itemList[i]);
		}	


	}
	
//	@Override
//	public void setVisible(boolean b)	{
//		visible = b;
//	}

	@Override
	protected void paintComponent(Graphics g)	{	
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(cursor.getImage(), 10, 15 + 25 * cursorPosition, null);
	}

	public void updateList()	{
		
		for (int i = 0; i < 6; i++)	{
			if (i < data.getInventory().getCategory(CONSUMABLES).size())	{
				itemList[i].setItem(data.getInventory().getCategory(CONSUMABLES).get(i + scrollOffset));
				itemList[i].setVisible(true);
			}
			else	{
				itemList[i].setVisible(false);
			}
		}
	}
	
	public void respondToKeyPress(KeyEvent e)	{
		if (e.getKeyCode() == KeyEvent.VK_UP)	{
			raiseCursor();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
			
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
			dropCursor();
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
			
		}
	}

	public void dropCursor()	{
		cursorPosition++;
	}
	public void raiseCursor()	{
		if (cursorPosition > 0)	{
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
	 * @author mobius
	 * A subpanel inside of the scrollbox that represents an item. Contains the icon, name, and if the item is stackable,
	 * the quantity of the item.
	 */
	private class ItemPanel extends JPanel	{

		private static final long serialVersionUID = 4342436547521865798L;
		private ImageIcon itemIcon = new ImageIcon("GUI/Resources/sword_ico.png");
		private JLabel itemName = new JLabel();
		private JLabel itemQuantity = new JLabel();
		private boolean visible = true;

		public ItemPanel()	{
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			Dimension newDimension = new Dimension(250, 35);
			this.setPreferredSize(newDimension);
			this.setMaximumSize(newDimension);
			this.setMinimumSize(newDimension);
			this.setOpaque(false);

			this.setAlignmentX(LEFT_ALIGNMENT);
			itemName.setAlignmentX(LEFT_ALIGNMENT);

			itemName.setMaximumSize(new Dimension(150, 35));
			itemName.setPreferredSize(new Dimension(150, 35));

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
			if (item.getClass() == Consumable.class && ((Consumable) item).getQuantity() > 1)	{
				itemQuantity.setText("x" + Integer.toString(((Consumable) item).getQuantity()));
			}
		}

		public void setVisible(boolean b)	{
			visible = b;
			if (b == false)	{
				itemName.setText("");
				itemQuantity.setText("");
			}
		}
	}
}