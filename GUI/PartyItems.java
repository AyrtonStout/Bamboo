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

import Systems.EquippableItem;
import Systems.Inventory;
import Systems.Item;

public class PartyItems extends JPanel {

	private static final long serialVersionUID = 8869703204051577618L;

	private Inventory inventory;

	public int elements;
	public int cursorPosition = 0;
	public int scrollOffset = 0;
	private int itemCategory;

	final int WIDTH = 370;
	final int HEIGHT = 405;
	private Font statFont;

	private ItemPanel[] itemList; 
			
	public PartyItems(Font statFont, Inventory inventory)	{
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setOpaque(false);

		this.statFont = statFont;
		this.inventory = inventory;
		
		itemList = new ItemPanel[] {new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), 
				new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel()};


		for (int i = 0; i < itemList.length; i++)	{
			this.add(itemList[i]);
		}

	}

	public void update(int itemCategory)	{
		this.itemCategory = itemCategory;
		
		if (itemCategory == 0)	{
			elements = inventory.getWeapons().size();
			for (int i = 0; i < itemList.length; i++)	{
				if (i < inventory.getWeapons().size())	{
					itemList[i].setItem(inventory.getWeapons().get(i + scrollOffset));
					itemList[i].setVisible(true);
				}
				else	{
					itemList[i].setVisible(false);
				}
			}
		}
		else if (itemCategory > 0 && itemCategory <= 4)	{
			elements = inventory.getArmor().size();
			for (int i = 0; i < itemList.length; i++)	{
				if (i < inventory.getArmor().size())	{
					itemList[i].setItem(inventory.getArmor().get(i + scrollOffset));
					itemList[i].setVisible(true);
				}
				else	{
					itemList[i].setVisible(false);
				}
			}
		}
		else {
			elements = inventory.getAccessories().size();
			for (int i = 0; i < itemList.length; i++)	{
				if (i < inventory.getAccessories().size())	{
					itemList[i].setItem(inventory.getAccessories().get(i + scrollOffset));
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
		if (itemCategory == 0 && inventory.getWeapons().size() > 0)	{
			return inventory.getWeapons().get(cursorPosition + scrollOffset);
		}
		else if	(itemCategory >= 1 && itemCategory <= 4 && inventory.getArmor().size() > 0)	{
			return inventory.getArmor().get(cursorPosition + scrollOffset);
		}
		else if (itemCategory >= 5 && itemCategory <= 8 && inventory.getAccessories().size() > 0)	{
			return inventory.getAccessories().get(cursorPosition + scrollOffset);
		}
		else	{
			return null;
		}
	}

	public void respondToKeyPress(KeyEvent e)	{

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
