package GUI;

import Systems.EquippableItem;
import Systems.Inventory;
import Systems.Item;
import Systems.PartyMember;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PartyItems extends JPanel {

	private static final long serialVersionUID = 8869703204051577618L;
	final int WIDTH = 370;
	final int HEIGHT = 405;
	public int elements;
	public int cursorPosition = 0;
	public int scrollOffset = 0;
	private Inventory inventory;
	private int itemCategory;
	private PartyMember selectedMember;
	private Font statFont;

	private ImageIcon optionsCursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");

	private ItemPanel[] itemList;

	public PartyItems(Font statFont, Inventory inventory) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setOpaque(false);

		this.statFont = statFont;
		this.inventory = inventory;

		itemList = new ItemPanel[]{new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(),
				new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel()};

		for (int i = 0; i < itemList.length; i++) {
			this.add(itemList[i]);
		}
	}

	public void update(int itemCategory, PartyMember selectedMember) {
		this.itemCategory = itemCategory;
		this.selectedMember = selectedMember;

		if (itemCategory == 0) {
			elements = inventory.getWeapons().size();
			for (int i = 0; i < itemList.length; i++) {
				if (i < inventory.getWeapons().size()) {
					itemList[i].setItem(inventory.getWeapons().get(i + scrollOffset));
					itemList[i].setVisible(true);
				} else {
					itemList[i].setVisible(false);
				}
			}
		} else if (itemCategory > 0 && itemCategory <= 4) {
			elements = inventory.getArmor().size();
			for (int i = 0; i < itemList.length; i++) {
				if (i < inventory.getArmor().size()) {
					itemList[i].setItem(inventory.getArmor().get(i + scrollOffset));
					itemList[i].setVisible(true);
				} else {
					itemList[i].setVisible(false);
				}
			}
		} else {
			elements = inventory.getAccessories().size();
			for (int i = 0; i < itemList.length; i++) {
				if (i < inventory.getAccessories().size()) {
					itemList[i].setItem(inventory.getAccessories().get(i + scrollOffset));
					itemList[i].setVisible(true);
				} else {
					itemList[i].setVisible(false);
				}
			}
		}

		if (itemList[0].visibleEh() == false) {
			itemList[0].clear();
			itemList[0].setVisible(true);
		}
	}

	public EquippableItem getSelectedItem() {
		if (itemCategory == 0 && inventory.getWeapons().size() > 0) {
			return inventory.getWeapons().get(cursorPosition + scrollOffset);
		} else if (itemCategory >= 1 && itemCategory <= 4 && inventory.getArmor().size() > 0) {
			return inventory.getArmor().get(cursorPosition + scrollOffset);
		} else if (itemCategory >= 5 && itemCategory <= 8 && inventory.getAccessories().size() > 0) {
			return inventory.getAccessories().get(cursorPosition + scrollOffset);
		} else {
			return null;
		}
	}

	public void respondToKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (cursorPosition < 8 && cursorPosition < elements - 1) {
				cursorPosition++;
			} else if (cursorPosition == 8 && cursorPosition + scrollOffset < elements - 1) {
				scrollOffset++;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (cursorPosition > 0) {
				cursorPosition--;
			} else if (cursorPosition == 0 && scrollOffset > 0) {
				scrollOffset--;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_Z) {
			if (itemCategory != 6 && itemCategory != 7) {
				selectedMember.getEquipment().equipItem(getSelectedItem(), inventory);
			} else if (itemCategory == 6) {
				selectedMember.getEquipment().equipItem(getSelectedItem(), inventory, 1);
			} else if (itemCategory == 7) {
				selectedMember.getEquipment().equipItem(getSelectedItem(), inventory, 2);
			}
		}
	}

	public void drawArrow(Graphics g) {
		g.drawImage(optionsCursor.getImage(), 260, 180 + 45 * cursorPosition, null);
	}

	private class ItemPanel extends JPanel {

		private static final long serialVersionUID = 4342436547521865798L;
		private ImageIcon itemIcon = new ImageIcon();
		private JLabel itemName = new JLabel();
		private boolean visible = true;

		public ItemPanel() {
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
		protected void paintComponent(Graphics g) {
			if (visible) {
				g.drawImage(itemIcon.getImage(), 50, 0, null);
			}
		}

		public void setItem(Item item) {
			itemName.setText(item.getName());
			itemIcon = item.getIcon();
		}

		public void setVisible(boolean b) {
			visible = b;
			if (b == false) {
				itemName.setText("");
			}
		}

		public void clear() {
			itemName.setText("EMPTY");
			itemIcon = new ImageIcon();
		}

		public boolean visibleEh() {
			return visible;
		}
	}
}
