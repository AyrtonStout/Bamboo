package GUI;

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
import javax.swing.SwingConstants;

public class Menu extends JPanel {

	private boolean visible;
	private ImageIcon background;
	private ImageIcon cursor;
	private int cursorPosition;
	
	private final int FONT_HEIGHT = 28;
	private JPanel[] panels = new JPanel[] {new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), 
			new JPanel(), new JPanel(), new JPanel(), new JPanel()};
	private JLabel[] labels = new JLabel[] {new JLabel("Party"), new JLabel("Items"), new JLabel("Map"), new JLabel("Quests"), 
			new JLabel("Hunts"), new JLabel("Save"), new JLabel("Options"), new JLabel("Box8"), new JLabel("0:00")};
	
	private Font baseFont;
	private Font gameFont;
	private InputStream stream;
	
	public Menu()	{
		
		try {
			stream = new BufferedInputStream(
                    new FileInputStream("GUI/Resources/Game_Font.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			gameFont = baseFont.deriveFont(Font.PLAIN, 19);
			
		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}
		
		background = new ImageIcon("GUI/Resources/Menu_Background.png");
		cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
		

		for (int i = 0; i < panels.length; i++)	{
			panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
			panels[i].setOpaque(false);
			panels[i].setAlignmentX(LEFT_ALIGNMENT);
			panels[i].add(Box.createHorizontalStrut(30));
			panels[i].setPreferredSize(new Dimension(600, 50));
		}
		panels[labels.length-1].add(Box.createHorizontalStrut(20));
		this.add(Box.createVerticalStrut(15));
		
		for (int i = 0; i < labels.length; i++)	{
			labels[i].setPreferredSize(new Dimension(180, FONT_HEIGHT));
			labels[i].setMinimumSize(new Dimension(180, FONT_HEIGHT));
			labels[i].setMaximumSize(new Dimension(180, FONT_HEIGHT));			
			labels[i].setFont(gameFont);
			labels[i].setAlignmentX(LEFT_ALIGNMENT);
			
			panels[i].add(labels[i]);
		}
		for (int i = 0; i < labels.length - 1; i++)	{
			this.add(panels[i]);			
		}

		this.add(Box.createVerticalStrut(8));
		

		this.add(panels[labels.length-1]);
		
//		this.add(Box.createVerticalStrut(10));
		
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		
		this.setPreferredSize(new Dimension(180, 400));
		this.setMaximumSize(new Dimension(180, 400));
		this.setMinimumSize(new Dimension(180, 400));
		
		this.setVisible(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (visible)	{
			g.drawImage(background.getImage(), 0, 0, null);
			g.drawImage(cursor.getImage(), 10, 23 + (47 * cursorPosition), null);
		}
	}
	
	/* 
	 * Sets whether or not the text box (and its components) are visible
	 */
	@Override
	public void setVisible(boolean b) {
		visible = b;
		if (b == true)	{
			this.setOpaque(true);
			for (int i = 0; i < labels.length; i++)	{
				labels[i].setVisible(true);
			}
		}
		else	{
			this.setOpaque(false);
			for (int i = 0; i < labels.length; i++)	{
				labels[i].setVisible(false);
			}
		}
		
	}
	
	public void dropCursor()	{
		if (cursorPosition < labels.length - 2)
			cursorPosition++;
	}
	public void raiseCursor()	{
		if (cursorPosition > 0)
			cursorPosition--;
	}
	public void resetCursor()	{
		cursorPosition = 0;
	}
}
