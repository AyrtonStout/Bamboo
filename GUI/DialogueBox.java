package GUI;

import Systems.Enums.GAME_STATE;
import Systems.GameData;
import Systems.Item;
import Systems.PartyMember;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author mobius
 *         Creates a text box to use for status messages or dialogue from characters or signs. When a text box has been
 *         given text to write through the Read() method, it will write a new letter every time it is updated() until
 *         it runs out of room or out of text. If it stopped because it ran out of room, it will continue the next time
 *         Read() is called.
 */
@SuppressWarnings("serial")
public class DialogueBox extends JPanel {

	private final String delimiter = "\\ ";
	private final int FRAME_SKIP = 1;
	private final int FONT_HEIGHT = 28;
	private final int MAX_LINE_LENGTH = 24;
	private final int ARROW_FLASH_SPEED = 12;
	private GameData data;
	private GAME_STATE restoreState;
	private boolean visible = true;
	private boolean writing = false;
	private boolean writeFaster = false;
	private boolean instantWrite = false;
	private boolean shrunken = false;
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");
	private ImageIcon textArrow = new ImageIcon("GUI/Resources/Text_Arrow.png");
	private ArrayList<String> dialogue = new ArrayList<String>();
	private ArrayList<String> currentFullSentence = new ArrayList<String>();
	private String currentLabelSentence = "", pendingWord;
	private int currentFrame = 0;
	private int currentArrowFlash = 0;
	private int pendingWordLocation, currentLabel;

	private JPanel[] panels = new JPanel[]{new JPanel(), new JPanel(), new JPanel()};
	private JLabel[] labels = new JLabel[]{new JLabel("", SwingConstants.LEFT), new JLabel("", SwingConstants.LEFT),
			new JLabel("", SwingConstants.LEFT)};

	private Font baseFont;
	private Font gameFont;
	private InputStream stream;

	/**
	 * Formats the text box with panels and labels. Loads the font from file and assigns it to the labels
	 */
	public DialogueBox(GameData data) {

		this.data = data;

		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Main.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			gameFont = baseFont.deriveFont(Font.PLAIN, 22);
		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}

		for (int i = 0; i < panels.length; i++) {
			panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
			panels[i].setOpaque(false);
			panels[i].setAlignmentX(LEFT_ALIGNMENT);
			panels[i].add(Box.createHorizontalStrut(20));
			panels[i].setPreferredSize(new Dimension(600, 50));
		}

		this.add(Box.createVerticalStrut(10));

		for (int i = 0; i < labels.length; i++) {
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
		if (visible == true) {
			g.drawImage(background.getImage(), 0, 0, null);
			if (!writing && dialogue.size() > 0) {
				if (currentArrowFlash > ARROW_FLASH_SPEED) {
					g.drawImage(textArrow.getImage(), 550, 130, null);
					currentArrowFlash++;
					if (currentArrowFlash > ARROW_FLASH_SPEED * 2) {
						currentArrowFlash = 0;
					}
				} else {
					currentArrowFlash++;
				}
			}
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
		if (b == true) {
			this.setOpaque(true);
			for (int i = 0; i < labels.length; i++) {
				labels[i].setVisible(true);
			}
		} else {
			this.setOpaque(false);
			for (int i = 0; i < labels.length; i++) {
				labels[i].setVisible(false);
				labels[i].setText("");
			}
		}
	}

	/**
	 * Writes another letter to the text box each time this is called, unless frame skip doesn't match the current frame
	 * Increase frame skip to slow down text scrolling
	 */
	public void update() {
		/*
		 * Controls writing for signs
		 */
		if (instantWrite) {
			while (writing) {
				if (currentFullSentence.size() == 0) {
					writing = false;
					currentArrowFlash = 0;
					currentLabelSentence = "";
				} else {
					pendingWord = currentFullSentence.remove(0);
					pendingWord += " ";

					if (currentLabelSentence.length() + pendingWord.length() > MAX_LINE_LENGTH) {
						currentLabel++;
						currentLabelSentence = "";
					}

					labels[currentLabel].setText(currentLabelSentence += pendingWord);
					pendingWord = "";
				}
			}
		}
		/*
		 * Controls writing for NPCs
		 */
		else {
			if (pendingWord == "") {
				if (currentFullSentence.size() == 0) {
					writing = false;
					currentArrowFlash = 0;
					currentLabelSentence = "";
				} else {
					pendingWord = currentFullSentence.remove(0);
					pendingWord += " ";
					pendingWordLocation = 0;

					if (currentLabelSentence.length() + pendingWord.length() > MAX_LINE_LENGTH) {
						currentLabel++;
						currentLabelSentence = "";
					}
				}
			}
			if (writing) {
				if (currentFrame == FRAME_SKIP || writeFaster) {
					writeLine(currentLabel);
					currentFrame = 0;
				} else {
					currentFrame++;
				}
			}
		}
	}

	/**
	 * Writes a word to the specified text box line
	 *
	 * @param currentLabel The current text box line to be drawn on
	 */
	private void writeLine(int currentLabel) {
		labels[currentLabel].setText(currentLabelSentence += pendingWord.charAt(pendingWordLocation));
		pendingWordLocation++;
		if (pendingWordLocation == pendingWord.length()) {
			pendingWord = "";
		}
	}

	public void advanceDialogue() {
		if (writing && !writeFaster) {
			writeFaster = true;
		} else if (!writing) {
			if (hasNextLine()) {
				read();
			} else {
				data.setGameState(restoreState);
				if (restoreState == GAME_STATE.BATTLE) {
					data.getBattleScreen().switchToMain();
				} else if (data.getPlayer().getInteractingNPC() != null) {
					data.getPlayer().getInteractingNPC().setTalking(false);
					data.getPlayer().getInteractingNPC().setTalkedTo(true);
					data.getPlayer().setInteractingNPC(null);
				}
				setVisible(false);
			}
		}
	}

	/**
	 * Gets the text box started on writing the Sign's next sentence
	 */
	private void read() {

		labels[0].setText("");
		labels[1].setText("");
		labels[2].setText("");

		String[] tmp = this.dialogue.remove(0).split(delimiter);

		for (int i = 0; i < tmp.length; i++) {
			currentFullSentence.add(tmp[i]);
		}

		pendingWord = "";
		currentLabel = 0;
		writing = true;
		writeFaster = false;
	}

	/**
	 * Pulls all of the dialogue from a Sign into the text box. If supplied "true", the TextBox
	 * will write everything all at once. If "false", it will iterate one character at a time
	 *
	 * @param dialogue     The full information contained on a Sign
	 * @param instantWrite Whether or not the text will come one letter at a time or all at once
	 */
	public void setDialogue(ArrayList<String> dialogue, boolean instantWrite) {
		this.dialogue = dialogue;
		this.instantWrite = instantWrite;
		startDialogue();
	}

	/**
	 * Add a message to the current dialogue box's messages. Instant write will be turned off
	 *
	 * @param str The message to be added
	 */
	public void addDialogue(String str) {
		dialogue.add(str);
		instantWrite = false;
	}

	/**
	 * Uses the text box to show what item has just been looted.
	 *
	 * @param item The item that was just received.
	 */
	public void receiveItem(Item item) {
		this.dialogue.add("You looted " + vowelEh(item.getName()) + item.getName() + "!");
		this.instantWrite = false;
		startDialogue();
	}

	/**
	 * Uses the text box to show who just joined the party.
	 *
	 * @param member The party member that just joined.
	 */
	public void joinParty(PartyMember member) {
		this.dialogue.add(member.getName() + " has joined the party!");
		this.instantWrite = false;
		startDialogue();
	}

	/**
	 * Will start the dialogue that the sign has been provided. This is automatically called
	 * when "setDialogue()" is invoked. Use this only when you have manually added dialogue
	 * via the "addDialogue" method.
	 */
	public void startDialogue() {
		restoreState = data.getGameState();

		setVisible(true);
		data.setGameState(GAME_STATE.TALK);
		advanceDialogue();
	}

	/**
	 * Used to have intelligent sounding dialogue when using the articles "a" and "an".
	 *
	 * @param str The word to be checked for a vowel.
	 * @return The string "a" or "an".
	 */
	private String vowelEh(String str) {

		if (str.charAt(0) == 'a' || str.charAt(0) == 'e' || str.charAt(0) == 'i' || str.charAt(0) == 'o' || str.charAt(0) == 'u' ||
				str.charAt(0) == 'A' || str.charAt(0) == 'E' || str.charAt(0) == 'I' || str.charAt(0) == 'O' || str.charAt(0) == 'U') {
			return "an ";
		} else {
			return "a ";
		}
	}

	/**
	 * @param b Whether or not the sign should be writing
	 */
	public void setWriting(boolean b) {
		writing = b;
	}

	/**
	 * @return Whether or not the sign is writing
	 */
	public boolean writingEh() {
		return writing;
	}

	/**
	 * @return Whether or not the text box has more information to give
	 */
	public boolean hasNextLine() {
		if (dialogue.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * Shrinks the text box down to allow other components to occupy the screen area
	 */
	public void shrink() {
		for (int i = 0; i < panels.length; i++) {
			panels[i].setPreferredSize(new Dimension(0, 50));
		}
		this.setPreferredSize(new Dimension(600, 0));
		shrunken = true;
	}

	/**
	 * Returns the text box to its full size after being shrink()'d
	 */
	public void restore() {
		if (shrunken) {
			for (int i = 0; i < panels.length; i++) {
				panels[i].setPreferredSize(new Dimension(600, 50));
			}
			this.setPreferredSize(new Dimension(600, 150));
			shrunken = false;
		}
	}
}
