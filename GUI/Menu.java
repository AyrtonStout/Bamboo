package GUI;

import Systems.GameData;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mobius
 *         Side menu accessed from the world map screen. Has options to enter many other menus (like inventory or party screens)
 *         as well as check the game time or save the game.
 */
public class Menu extends JPanel {

	private static final long serialVersionUID = 7128111428003212233L;
	private final int FONT_HEIGHT = 28;
	private boolean visible;
	private ImageIcon background = new ImageIcon("GUI/Resources/Menu_Background.png");
	private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private int cursorPosition;
	private JPanel[] panels = new JPanel[]{new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(),
			new JPanel(), new JPanel(), new JPanel(), new JPanel()};
	private JLabel[] labels = new JLabel[]{new JLabel("Party"), new JLabel("Items"), new JLabel("Map"), new JLabel("Quests"),
			new JLabel("Hunts"), new JLabel("Stats"), new JLabel("Save"), new JLabel("Options"), new JLabel("0:00")};

	private Font baseFont;
	private Font gameFont;
	private InputStream stream;

	private GameData data;

	public Menu(GameData data) {

		this.data = data;

		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Main.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			gameFont = baseFont.deriveFont(Font.PLAIN, 19);
		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}

		for (int i = 0; i < panels.length; i++) {
			panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
			panels[i].setOpaque(false);
			panels[i].setAlignmentX(LEFT_ALIGNMENT);
			panels[i].add(Box.createHorizontalStrut(30));
			panels[i].setPreferredSize(new Dimension(600, 50));
		}
		panels[labels.length - 1].add(Box.createHorizontalStrut(10));
		this.add(Box.createVerticalStrut(15));

		for (int i = 0; i < labels.length; i++) {
			labels[i].setPreferredSize(new Dimension(180, FONT_HEIGHT));
			labels[i].setMinimumSize(new Dimension(180, FONT_HEIGHT));
			labels[i].setMaximumSize(new Dimension(180, FONT_HEIGHT));
			labels[i].setFont(gameFont);

			panels[i].add(labels[i]);
		}
		for (int i = 0; i < labels.length - 1; i++) {
			this.add(panels[i]);
		}

		this.add(Box.createVerticalStrut(8));

		this.add(panels[labels.length - 1]);

		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setPreferredSize(new Dimension(180, 400));
		this.setMaximumSize(new Dimension(180, 400));
		this.setMinimumSize(new Dimension(180, 400));

		this.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (visible) {
			g.drawImage(background.getImage(), 0, 0, null);
			g.drawImage(cursor.getImage(), 10, 23 + (47 * cursorPosition), null);
		}
	}

	/**
	 * Updates the menu to display the current time
	 */
	public void update() {
		labels[labels.length - 1].setText(data.getTime().toString());
	}

	/* 
	 * Sets whether or not the text box (and its components) are visible
	 */
	@Override
	public void setVisible(boolean b) {
		visible = b;
		if (b == true) {
			this.setOpaque(true);
			for (int i = 0; i < labels.length; i++) {
				labels[i].setVisible(true);
			}
		} else {
			this.setOpaque(false);
			for (int i = 0; i < labels.length; i++) {
				labels[i].setVisible(false);
			}
		}
	}

	/**
	 * Sets the size of the menu and all of its components to 0. Allows other components to use up the entire screen
	 * without removing the panel from the game entirely
	 */
	public void shrink() {
		for (int i = 0; i < panels.length; i++) {
			panels[i].setPreferredSize(new Dimension(0, 50));
		}
		this.setPreferredSize(new Dimension(0, 450));
	}

	/**
	 * Undoes the shrink() method and returns the menu to its former glory
	 */
	public void restore() {
		for (int i = 0; i < panels.length; i++) {
			panels[i].setPreferredSize(new Dimension(180, 50));
		}
		this.setPreferredSize(new Dimension(180, 450));
	}

	/**
	 * Moves the menu's cursor down by one
	 */
	public void dropCursor() {
		if (cursorPosition < labels.length - 2)
			cursorPosition++;
	}

	/**
	 * Moves the menu's cursor up by one
	 */
	public void raiseCursor() {
		if (cursorPosition > 0)
			cursorPosition--;
	}

	/**
	 * Resets the cursor to the top of the menu
	 */
	public void resetCursor() {
		cursorPosition = 0;
	}

	/**
	 * @return The position of the menu's cursor. Top option is 0. Bottom option is 7.
	 */
	public int getCursorPosition() {
		return cursorPosition;
	}
}
