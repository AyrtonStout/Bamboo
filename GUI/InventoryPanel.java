package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
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
import javax.swing.SwingConstants;

import Systems.Inventory;
import Systems.Item;

/**
 * @author mobius
 * The entire panel displayed inside of the inventory menu. The InventoryPanel class is mostly a container for
 * the other smaller private classes that all come together to form a super panel. Like Voltron. 
 */
public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = -5463392889506186626L;

	private InputStream stream;
	private Font baseFont;
	private Font gameFont;
	private Font headerFont;
	private ItemCategories categories;
	private PartyHeader header;
	private ScrollBox list;
	private StatsBox statsBox;
	private boolean innerBoxActive = false;

	private Inventory inventory;

	public InventoryPanel(Inventory inventory)	{

		this.inventory = inventory;
		
		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Inventory.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			gameFont = baseFont.deriveFont(Font.PLAIN, 36);
			headerFont = baseFont.deriveFont(Font.ITALIC, 48);

		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}

		categories = new ItemCategories();
		header = new PartyHeader();
		list = new ScrollBox();
		statsBox = new StatsBox();

		this.setPreferredSize(new Dimension(500, 600));
		this.setLayout(new BorderLayout());

		//Combine
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(header, BorderLayout.NORTH);
		rightPanel.add(list, BorderLayout.SOUTH);

		this.add(categories, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.CENTER);	
		this.add(statsBox, BorderLayout.SOUTH);
	
		list.updateList(0);
	}
	
	/**
	 * Sets whether or not the scrolling window or the category window is the active window
	 * 
	 * @param b Whether or not the inner window is the active one
	 */
	public void setInnerActive(boolean b)	{
		innerBoxActive = b;
		statsBox.update();
	}
	/**
	 * Allows the inventory list to show up when it is first loaded
	 */
	public void initializeList()	{
		list.updateList(0);
	}


	/**
	 * Moves the item category cursor down by one
	 */
	public void dropCategoryCursor()	{
		categories.dropCursor();
	}
	/**
	 * Moves the item category cursor up by one
	 */
	public void raiseCategoryCursor()	{
		categories.raiseCursor();
	}
	/**
	 * Resets the cursor's position to its initial state
	 */
	public void resetCategoryCursor()	{
		categories.resetCursor();
	}
	/**
	 * @return The category cursor's current position
	 */
	public int getCategoryCursorPosition()	{
		return categories.getCursorPosition();
	}


	/**
	 * Moves the character selection header cursor left by one
	 */
	public void moveHeaderCursorLeft()	{
		header.moveCursorLeft();
	}
	/**
	 * Moves the character selection header cursor right by one
	 */
	public void moveHeaderCursorRight()	{
		header.moveCursorRight();
	}
	/**
	 * Resets the character selection cursor to the far left
	 */
	public void resetHeaderCursor()	{
		header.resetCursor();
	}
	/**
	 * @return The character selection cursor's position
	 */
	public int getHeaderCursorPosition()	{
		return header.getCursorPosition();
	}

	/**
	 * Moves the item list cursor down by one
	 */
	public void dropItemCursor()	{
		list.dropItemCursor();
		statsBox.update();
	}
	/**
	 * Moves the item list cursor up by one
	 */
	public void raiseItemCursor()	{
		list.raiseItemCursor();
		statsBox.update();
	}
	/**
	 * Resets the item list cursor to the top
	 */
	public void resetItemCursor()	{
		list.resetItemCursor();
	}
	/**
	 * @return The current position of the item list's cursor
	 */
	public int getItemCursorPosition()	{
		return list.getItemCursorPosition();
	}
	

	/**
	 * @author mobius
	 * The left-hand side of the inventory panel where the category of items is selected
	 */
	private class ItemCategories extends JPanel	{

		private static final long serialVersionUID = -774331420783363746L;
		private JLabel[] labels = new JLabel[] {new JLabel("Weapons"), new JLabel("Armor"), new JLabel("Accessories"), 
				new JLabel("Consumables"), new JLabel("Loot"), new JLabel("Key Items")};
		private JPanel[] panels = new JPanel[labels.length];
		private JPanel indicator = new JPanel();
		private JLabel indicatorLabel = new JLabel("Inventory");
		private ImageIcon cursor = new ImageIcon("GUI/Resources/Icon_Orange_Diamond.png");
		private ImageIcon background = new ImageIcon("GUI/Resources/Inventory_CategoryBackground.png");
		private ImageIcon titleBackground = new ImageIcon("GUI/Resources/Inventory_TitleBackground.png");
		private boolean visible = true;
		private int cursorPosition = 0;



		public ItemCategories()	{

			//Top panel that labels the screen as "Inventory"
			indicator.setPreferredSize(new Dimension(205, 75));
			indicator.setMaximumSize(new Dimension(205, 75));
			indicator.setMinimumSize(new Dimension(205, 75));
			indicator.setLayout(new BoxLayout(indicator, BoxLayout.X_AXIS));
			indicator.setAlignmentX(LEFT_ALIGNMENT);
			indicator.add(Box.createHorizontalStrut(17));
			indicator.setOpaque(false);
			indicator.add(indicatorLabel);
			indicatorLabel.setFont(headerFont);


			this.setPreferredSize(new Dimension(205, 375));
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(indicator);
			this.add(Box.createVerticalStrut(20));

			for (int i = 0; i < panels.length; i++)	{
				panels[i] = new JPanel();
				panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
				panels[i].setOpaque(false);
				panels[i].setAlignmentX(LEFT_ALIGNMENT);
				panels[i].add(Box.createHorizontalStrut(30));
				panels[i].setPreferredSize(new Dimension(600, 40));
			}

			for (int i = 0; i < labels.length; i++)	{
				labels[i].setPreferredSize(new Dimension(180, 30));
				labels[i].setMinimumSize(new Dimension(180, 30));
				labels[i].setMaximumSize(new Dimension(180, 30));			
				labels[i].setFont(gameFont);

				panels[i].add(labels[i]);
				this.add(panels[i]);
			}

			this.add(Box.createVerticalStrut(100));

		}

		@Override
		protected void paintComponent(Graphics g) {
			if (visible)	{
				g.drawImage(background.getImage(), 0, 75, null);
				g.drawImage(cursor.getImage(), 3, 97 + (42 * cursorPosition), null);
				g.drawImage(titleBackground.getImage(), 0, 0, null);
			}
		}

		public void dropCursor()	{
			if (cursorPosition < labels.length - 1)	{
				cursorPosition++;
				list.updateList(cursorPosition);

			}
		}
		public void raiseCursor()	{
			if (cursorPosition > 0)	{
				cursorPosition--;
				list.updateList(cursorPosition);
			}
		}
		public void resetCursor()	{
			cursorPosition = 0;
		}
		public int getCursorPosition()	{
			return cursorPosition;
		}
	}


	/**
	 * @author mobius
	 *
	 */
	private class PartyHeader extends JPanel	{

		private static final long serialVersionUID = -1231677531208626435L;

		JPanel inner = new JPanel();
		private boolean visible = true;
		private ImageIcon cursor = new ImageIcon("GUI/Resources/Icon_RedArrow.png");
		private ImageIcon background = new ImageIcon("GUI/Resources/Inventory_HeaderBackground.png");
		private int cursorPosition = 0;
		private String[] party;

		public PartyHeader()	{

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setPreferredSize(new Dimension(395, 100));
			this.setMaximumSize(new Dimension(395, 100));
			this.setMinimumSize(new Dimension(395, 100));
			this.add(Box.createVerticalStrut(15));
			this.setOpaque(false);

			inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
			inner.setPreferredSize(new Dimension(395, 50));
			inner.setMaximumSize(new Dimension(395, 50));
			inner.setMinimumSize(new Dimension(395, 50));
			inner.setOpaque(false);

			party = new String[]{"Sabin", "Terra", "Celes", "Locke", "Cyan"};

			for (int i = 0; i < party.length; i++)	{
				inner.add(Box.createHorizontalStrut(20));
				inner.add(new JLabel(new ImageIcon("GUI/Resources/Characters/" + party[i] + " (Down).gif")));
			}

			this.add(inner);
			this.add(Box.createVerticalStrut(30));
		}

		@Override
		protected void paintComponent(Graphics g) {
			if (visible)	{
				g.drawImage(background.getImage(), 0, 0, null);
				g.drawImage(cursor.getImage(), 24 + (52 * cursorPosition), 66, null);
			}
		}

		public void moveCursorRight()	{
			if (cursorPosition < party.length - 1)
				cursorPosition++;
		}
		public void moveCursorLeft()	{
			if (cursorPosition > 0)
				cursorPosition--;
		}
		public void resetCursor()	{
			cursorPosition = 0;
		}
		public int getCursorPosition()	{
			return cursorPosition;
		}
	}

	/**
	 * @author mobius
	 *
	 */
	private class ScrollBox extends JPanel	{

		private static final long serialVersionUID = 3631604066140292636L;

//		private ImageIcon scrollBar = new ImageIcon("GUI/Resources/Tree_Palm.png");
		private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
		private ImageIcon background = new ImageIcon("GUI/Resources/Inventory_ListBackground.png");
		private int itemCursorPosition = 0;
		private int scrollOffset = 0;

		private ItemPanel[] itemList = new ItemPanel[] {new ItemPanel(), new ItemPanel(), new ItemPanel(), new ItemPanel(), 
				new ItemPanel(), new ItemPanel(), new ItemPanel()};


		public ScrollBox()	{	
			this.setPreferredSize(new Dimension(395, 350));
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			this.add(Box.createVerticalStrut(10));

			for (int i = 0; i < itemList.length; i++)	{
				this.add(itemList[i]);
			}
		}
		
		

		@Override
		protected void paintComponent(Graphics g)	{
//			g.drawImage(scrollBar.getImage(), 350, 40, null);
			g.drawImage(background.getImage(), 0, 0, null);
			if (innerBoxActive)	{
				g.drawImage(cursor.getImage(), 10, 20 + (itemCursorPosition * 50), null);
			}
		}

		public void updateList(int outerCursorPosition)	{
			
			for (int i = 0; i < 7; i++)	{
				if (i < inventory.getCategory(outerCursorPosition).size())	{
				itemList[i].setItem(inventory.getCategory(outerCursorPosition).get(i + scrollOffset));
				itemList[i].setVisible(true);
				}
				else	{
					itemList[i].setVisible(false);
				}
			}
		}
		
		public void dropItemCursor()	{
			if (itemCursorPosition < 6 && itemCursorPosition < inventory.getCategory(categories.getCursorPosition()).size() - 1)	{
				itemCursorPosition++;
			}
			else if (itemCursorPosition == 6 && itemCursorPosition + scrollOffset < inventory.getCategory(categories.getCursorPosition()).size() - 1)	{
				scrollOffset++;
				updateList(categories.getCursorPosition());
			}
		}
		public void raiseItemCursor()	{
			if (itemCursorPosition > 0)	{
				itemCursorPosition--;
			}
			else if (itemCursorPosition == 0 && scrollOffset > 0)	{
				scrollOffset--;
				updateList(categories.getCursorPosition());
			}
		}
		public void resetItemCursor()	{
			itemCursorPosition = 0;
			scrollOffset = 0;
		}
		public int getItemCursorPosition()	{
			return itemCursorPosition;
		}


		/**
		 * @author mobius
		 *
		 */
		private class ItemPanel extends JPanel	{

			private static final long serialVersionUID = 4342436547521865798L;
			private ImageIcon itemIcon = new ImageIcon("GUI/Resources/sword_ico.png");
			private JLabel itemName = new JLabel();
			private boolean visible = true;

			public ItemPanel()	{
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setPreferredSize(new Dimension(395, 50));
				this.setMaximumSize(new Dimension(395, 50));
				this.setMinimumSize(new Dimension(395, 50));
				this.setOpaque(true);

				this.setAlignmentX(LEFT_ALIGNMENT);
				itemName.setAlignmentX(LEFT_ALIGNMENT);

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
	private class StatsBox extends JPanel	{

		private static final long serialVersionUID = -5497664525623334310L;
		
		private JLabel itemName = new JLabel("", SwingConstants.LEFT);
		private JTextArea leftText = new JTextArea();
		private JTextArea middleText = new JTextArea();
		private JTextArea rightText = new JTextArea();
		private JLabel itemDescription = new JLabel("", SwingConstants.LEFT);
		private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
		
		private Font defaultFont = leftText.getFont();
		private Font itemNameFont = defaultFont.deriveFont(Font.ITALIC, 24);
		private Font statFont = defaultFont.deriveFont(Font.PLAIN, 17);
		private Font descrptionFont = defaultFont.deriveFont(Font.PLAIN, 18);
		
		private JTextArea[] textAreas = new JTextArea[] {leftText, middleText, rightText};

		public StatsBox()	{
			this.setPreferredSize(new Dimension(600, 150));
			this.setBackground(Color.BLUE);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setAlignmentX(LEFT_ALIGNMENT);
			this.setOpaque(false);

			JPanel namePanel = new JPanel();
			namePanel.setPreferredSize(new Dimension(600, 20));
			namePanel.setMaximumSize(new Dimension(600, 20));
			namePanel.setMinimumSize(new Dimension(600, 20));
			namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
			namePanel.add(Box.createHorizontalStrut(8));
			itemName.setFont(itemNameFont);
			namePanel.add(itemName);
			namePanel.setOpaque(false);
			namePanel.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel textFields = new JPanel();
			textFields.setLayout(new BoxLayout(textFields, BoxLayout.X_AXIS));
			textFields.setPreferredSize(new Dimension(600, 100));
			textFields.setMaximumSize(new Dimension(600, 100));
			textFields.setMinimumSize(new Dimension(600, 100));
			textFields.setOpaque(false);
			textFields.setAlignmentX(LEFT_ALIGNMENT);
			
			textFields.add(Box.createHorizontalStrut(10));
			for (int i = 0; i < textAreas.length; i++)	{
				textAreas[i].setEditable(false);
				textAreas[i].setPreferredSize(new Dimension(198, 100));
				textAreas[i].setFont(statFont);
				textFields.add(textAreas[i]);
			}	
			textFields.add(Box.createHorizontalStrut(10));
			
			JPanel descriptionPanel = new JPanel();
			descriptionPanel.setPreferredSize(new Dimension(600, 20));
			descriptionPanel.setMaximumSize(new Dimension(600, 20));
			descriptionPanel.setMinimumSize(new Dimension(600, 20));
			descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));
			descriptionPanel.add(Box.createHorizontalStrut(8));
			itemDescription.setFont(descrptionFont);
			descriptionPanel.add(itemDescription);
			descriptionPanel.setOpaque(false);
			descriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			
			
			this.add(Box.createVerticalStrut(7));
			this.add(namePanel);
//			this.add(Box.createVerticalStrut(5));
			this.add(textFields);
			this.add(descriptionPanel);

		}
		
		@Override
		public void paintComponent(Graphics g)	{
			g.drawImage(background.getImage(), 0, 0, null);
		}
		
		public void update()	{
			Item queriedItem = inventory.getCategory(categories.getCursorPosition()).get(list.getItemCursorPosition());
			itemName.setText(queriedItem.getName());
			leftText.setText(queriedItem.getMainText().getText());
			middleText.setText(queriedItem.getStatText().getText());
			rightText.setText(queriedItem.getBuffText().getText());
			itemDescription.setText(queriedItem.getDescription());
		}

	}
}
