package Systems;

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

public class Inventory extends JPanel {

	private static final long serialVersionUID = -5463392889506186626L;
	
	private InputStream stream;
	private Font baseFont;
	private Font gameFont;
	private Font headerFont;
	ItemCategories categories;
	PartyHeader header;

	public Inventory()	{
		
		try {
			stream = new BufferedInputStream(
                    new FileInputStream("GUI/Resources/Font_Inventory.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			gameFont = baseFont.deriveFont(Font.PLAIN, 40);
			headerFont = baseFont.deriveFont(Font.ITALIC, 48);
			
		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}

		categories = new ItemCategories();
		header = new PartyHeader();
		
		this.setPreferredSize(new Dimension(500, 450));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		
		//BOTTOM
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(395, 450));
		bottomPanel.setBackground(Color.BLUE);
		
		//Combine
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(header, BorderLayout.NORTH);
		rightPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		this.add(categories, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.CENTER);
		
	}
	
	public void dropLeftCursor()	{
		categories.dropCursor();
	}
	public void raiseLeftCursor()	{
		categories.raiseCursor();
	}
	public void resetLeftCursor()	{
		categories.resetCursor();
	}
	public int getCursorLeftPosition()	{
		return categories.getCursorPosition();
	}
	
	
	
	private class ItemCategories extends JPanel	{

		private static final long serialVersionUID = -774331420783363746L;
		private JLabel[] labels = new JLabel[] {new JLabel("Weapons"), new JLabel("Armor"), new JLabel("Accessories"), 
				new JLabel("Consumables"), new JLabel("Loot"), new JLabel("Key Items")};
		private JPanel[] panels = new JPanel[labels.length];
		private JPanel indicator = new JPanel();
		private JLabel indicatorLabel = new JLabel("Inventory");
		private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
		private boolean visible = true;
		private int cursorPosition = 0;

		
		
		public ItemCategories()	{
			
			//Top panel
			indicator.setPreferredSize(new Dimension(205, 75));
			indicator.setMaximumSize(new Dimension(205, 75));
			indicator.setMinimumSize(new Dimension(205, 75));
			indicator.setLayout(new BoxLayout(indicator, BoxLayout.X_AXIS));
			indicator.setAlignmentX(LEFT_ALIGNMENT);
			indicator.add(Box.createHorizontalStrut(17));
			indicator.add(indicatorLabel);
			indicatorLabel.setFont(headerFont);

			
			this.setPreferredSize(new Dimension(205, 375));
			this.setBackground(Color.GREEN);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(indicator);
			this.add(Box.createVerticalStrut(20));
			
			for (int i = 0; i < panels.length; i++)	{
				panels[i] = new JPanel();
				panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
				panels[i].setOpaque(false);
				panels[i].setAlignmentX(LEFT_ALIGNMENT);
				panels[i].add(Box.createHorizontalStrut(30));
				panels[i].setPreferredSize(new Dimension(600, 50));
			}
			
			for (int i = 0; i < labels.length; i++)	{
				labels[i].setPreferredSize(new Dimension(180, 32));
				labels[i].setMinimumSize(new Dimension(180, 32));
				labels[i].setMaximumSize(new Dimension(180, 32));			
				labels[i].setFont(gameFont);
//				labels[i].setAlignmentX(LEFT_ALIGNMENT);
				
				panels[i].add(labels[i]);
				this.add(panels[i]);
			}
			
			this.add(Box.createVerticalStrut(15));

		}
		
		@Override
		protected void paintComponent(Graphics g) {
			if (visible)	{
//				g.drawImage(background.getImage(), 0, 0, null);
				g.drawImage(cursor.getImage(), 9, 108 + (56 * cursorPosition), null);
			}
		}
		
		public void dropCursor()	{
			if (cursorPosition < labels.length - 1)
				cursorPosition++;
		}
		public void raiseCursor()	{
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
	
	
	private class PartyHeader extends JPanel	{
		
		JPanel inner = new JPanel();
		
		public PartyHeader()	{
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setPreferredSize(new Dimension(395, 100));
			this.setMaximumSize(new Dimension(395, 100));
			this.setMinimumSize(new Dimension(395, 100));
			this.setBackground(Color.YELLOW);
			this.add(Box.createVerticalStrut(25));
			
			inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
			inner.setPreferredSize(new Dimension(395, 50));
			inner.setMaximumSize(new Dimension(395, 50));
			inner.setMinimumSize(new Dimension(395, 50));
			inner.setBackground(Color.RED);
			
			String[] party = new String[]{"Sabin", "Terra", "Celes", "Locke", "Cyan"};
			
			for (int i = 0; i < party.length; i++)	{
			inner.add(Box.createHorizontalStrut(20));
			inner.add(new JLabel(new ImageIcon("GUI/Resources/Characters/" + party[i] + " (Down).gif")));
			}
			
			this.add(inner);
			this.add(Box.createVerticalStrut(30));
		}
	}
	
}
