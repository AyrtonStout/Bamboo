package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author mobius
 * Creates a text box to use for status messages or dialogue from characters or signs. When a text box has been
 * given text to write through the Read() method, it will write a new letter every time it is updated() until
 * it runs out of room or out of text. If it stopped because it ran out of room, it will continue the next time
 * Read() is called.
 */
@SuppressWarnings("serial")
public class TextBox extends JPanel{
	
	private boolean visible = true;
	private boolean writing = false;
	private boolean writeFaster = false;
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private ArrayList<String> dialogue = new ArrayList<String>();
	private ArrayList<String> currentFullSentence = new ArrayList<String>();
	private String currentLabelSentence = "", pendingWord;
	private final String delimiter = "\\ ";
	private final int FRAME_SKIP = 1;
	private int currentFrame = 0;
	private final int FONT_HEIGHT = 28;
	private final int MAX_LINE_LENGTH = 24;
	private int pendingWordLocation, currentLabel;
	
	private JPanel[] panels = new JPanel[] {new JPanel(), new JPanel(), new JPanel()};
	private JLabel[] labels = new JLabel[] {new JLabel("", SwingConstants.LEFT), new JLabel("", SwingConstants.LEFT),
			new JLabel("", SwingConstants.LEFT)};
	
	private Font baseFont;
	private Font gameFont;
	private InputStream stream;

	/**
	 * Formats the text box with panels and labels. Loads the font from file and assigns it to the labels
	 */
	public TextBox()	{
		try {
			stream = new BufferedInputStream(
                    new FileInputStream("GUI/Resources/Game_Font.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			gameFont = baseFont.deriveFont(Font.PLAIN, 22);
			
		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}
		
		for (int i = 0; i < panels.length; i++)	{
			panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
			panels[i].setOpaque(false);
			panels[i].setAlignmentX(LEFT_ALIGNMENT);
			panels[i].add(Box.createHorizontalStrut(20));
			panels[i].setPreferredSize(new Dimension(600, 50));
		}

		this.add(Box.createVerticalStrut(10));
		
		for (int i = 0; i < labels.length; i++)	{
			labels[i].setPreferredSize(new Dimension(555, FONT_HEIGHT));
			labels[i].setMinimumSize(new Dimension(555, FONT_HEIGHT));
			labels[i].setMaximumSize(new Dimension(555, FONT_HEIGHT));			
			labels[i].setFont(gameFont);
			
			panels[i].add(labels[i]);
			this.add(panels[i]);
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(600, 150));
		this.setVisible(false);
	}
	
	 /* 
	 * Paints the background image (border) for the text box
	 */
	@Override
	 protected void paintComponent(Graphics g) {
	     if (visible == true)	{
	    	 g.drawImage(background.getImage(), 0, 0, null);
	     }
	 }

	/**
	 * @return Whether or not the text box is currently visible
	 */
	public boolean visibleEh() {
		if (visible == true)
			return true;
		return false;
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

	/**
	 * Writes another letter to the text box each time this is called, unless frame skip doesn't match the current frame
	 * Increase frame skip to slow down text scrolling
	 */
	public void update()	{
		if (pendingWord == "")	{
			if (currentFullSentence.size() == 0)	{
				writing = false;
				currentLabelSentence = "";
			}
			else	{
				pendingWord = currentFullSentence.remove(0);
				pendingWord += " ";
				pendingWordLocation = 0;

				if (currentLabelSentence.length() + pendingWord.length() > MAX_LINE_LENGTH)	{
					currentLabel++;
					currentLabelSentence = "";
				}
			}
		}
		if (writing)	{
			if (currentFrame == FRAME_SKIP || writeFaster)	{
				writeLine(currentLabel);
				currentFrame = 0;
			}
			else	{
				currentFrame++;
			}
		}
	}

	/**
	 * Writes a word to the specified text box line
	 * 
	 * @param currentLabel The current text box line to be drawn on
 	 */
	private void writeLine(int currentLabel)	{
		labels[currentLabel].setText(currentLabelSentence += pendingWord.charAt(pendingWordLocation));
		pendingWordLocation++;
		if (pendingWordLocation == pendingWord.length())	{
			pendingWord = "";
		}
	}
	
	/**
	 * Gets the text box started on writing the Sign's next sentence
	 */
	public void read() {

		labels[0].setText("");
		labels[1].setText("");
		labels[2].setText("");
		
		String[] tmp = this.dialogue.remove(0).split(delimiter);
		
		for (int i = 0; i < tmp.length; i++)	{
			currentFullSentence.add(tmp[i]);
		}
				
		pendingWord = "";
		currentLabel = 0;
		writing = true;
		writeFaster = false;
		
	}

	/**
	 * Pulls all of the dialogue from a Sign into the text box to be iterated over later
	 * 
	 * @param dialogue The full information contained on a Sign
	 */
	public void setDialogue(ArrayList<String> dialogue) {
		this.dialogue = dialogue;
	}
	/**
	 * @param b Whether or not the sign should be writing
	 */
	public void setWriting(boolean b)	{
		writing = b;
	}
	/**
	 * @return Whether or not the sign is writing
	 */
	public boolean writingEh()	{
		return writing;
	}
	/**
	 * @return Whether or not the text box has more information to give
	 */
	public boolean hasNextLine()	{
		if (dialogue.size() > 0)
			return true;
		else
			return false;
	}

	public void writeFaster() {
		writeFaster = true;		
	}

	public boolean writeFasterEh() {
		return writeFaster;
	}
}
