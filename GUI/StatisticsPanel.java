package GUI;

import Systems.GameData;
import Systems.PartyMember;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StatisticsPanel extends JPanel {

	private static final long serialVersionUID = -9057800474525049386L;
	TopPanel topPanel = new TopPanel();
	BottomPanel bottomPanel = new BottomPanel();
	private InputStream stream;
	private Font boldFont;
	private Font normalFont;
	private GameData data;
	private int cursorPosition;

	public StatisticsPanel(GameData data) {
		this.setPreferredSize(new Dimension(600, 600));
		this.data = data;

		this.add(topPanel);
		this.add(bottomPanel);
	}

	public void update() {
		bottomPanel.update();
		topPanel.update();
	}

	public void moveCursorLeft() {
		topPanel.moveCursorLeft();
	}

	public void moveCursorRight() {
		topPanel.moveCursorRight();
	}

	public void resetCursor() {
		topPanel.resetCursor();
	}

	private class TopPanel extends JPanel {

		private static final long serialVersionUID = -1231677531208626435L;

		JPanel inner = new JPanel();
		JLabel[] party = new JLabel[]{new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(),
				new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};
		private boolean visible = true;
		private ImageIcon cursor = new ImageIcon("GUI/Resources/Icon_RedArrow.png");
		private int cursorPosition = 0;

		public TopPanel() {

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setPreferredSize(new Dimension(600, 100));
			this.setMaximumSize(new Dimension(600, 100));
			this.setMinimumSize(new Dimension(600, 100));
			this.add(Box.createVerticalStrut(15));
			this.setOpaque(false);

			inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
			inner.setPreferredSize(new Dimension(600, 50));
			inner.setMaximumSize(new Dimension(600, 50));
			inner.setMinimumSize(new Dimension(600, 50));
			inner.setOpaque(false);

			inner.add(Box.createHorizontalStrut(13));
			for (int i = 0; i < party.length; i++) {
				inner.add(Box.createHorizontalStrut(18));
				inner.add(party[i]);
			}

			this.add(inner);
			this.add(Box.createVerticalStrut(30));
		}

		@Override
		protected void paintComponent(Graphics g) {
			if (visible) {
				g.drawImage(cursor.getImage(), 37 + (50 * cursorPosition), 66, null);
			}
		}

		public void moveCursorRight() {
			if (cursorPosition < 10)
				cursorPosition++;
		}

		public void moveCursorLeft() {
			if (cursorPosition > 0)
				cursorPosition--;
		}

		public void resetCursor() {
			cursorPosition = 0;
		}

		public void update() {
//			for (int i = 0; i < data.getPlayableCharacters().size(); i++)  {
			for (int i = 0; i < 11; i++) {
				party[i].setIcon(new ImageIcon("GUI/Resources/Characters/" + data.getPlayableCharacters().get(0) + " (Down).gif"));
			}
		}
	}

	private class BottomPanel extends JPanel {

		private static final long serialVersionUID = 6563412072256404570L;
		JTextPane attributeStats, statisticsValues;

		public BottomPanel() {

			try {
				Font baseFont;

				stream = new BufferedInputStream(
						new FileInputStream("GUI/Resources/Font_Arial.ttf"));
				baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
				boldFont = baseFont.deriveFont(Font.BOLD, 18);
				normalFont = baseFont.deriveFont(Font.PLAIN, 18);
			} catch (FontFormatException | IOException e) {
				System.err.println("Use your words!! Font not found");
				e.printStackTrace();
			}

			int height = 405;

			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setPreferredSize(new Dimension(600, height));
			this.setMaximumSize(new Dimension(600, height));
			this.setMinimumSize(new Dimension(600, height));
			this.setOpaque(false);

			/*
			 * Left Panel
			 */
			JPanel attributes = new JPanel();
			attributes.setLayout(new BoxLayout(attributes, BoxLayout.X_AXIS));
			attributes.setPreferredSize(new Dimension(350, height));
			attributes.setMaximumSize(new Dimension(350, height));
			attributes.setMinimumSize(new Dimension(350, height));
			attributes.setOpaque(false);

			JPanel textWrapper = new JPanel();
			textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.Y_AXIS));
			textWrapper.setOpaque(false);
			textWrapper.add(Box.createVerticalStrut(4));

			//Names
			JTextArea attributeNames = new JTextArea("Most Kills\nMost Damage\nMost Deaths\nMost Healing\nHighest Crit\n" +
					"Highest Participation\nMost Played\n");
			attributeNames.setFont(boldFont);
			attributeNames.setPreferredSize(new Dimension(190, height));
			attributeNames.setMaximumSize(new Dimension(190, height));
			attributeNames.setMinimumSize(new Dimension(190, height));
			attributeNames.setEditable(false);
			attributeNames.setOpaque(false);

			//Values
			attributeStats = new JTextPane();
			attributeStats.setFont(normalFont);
			attributeStats.setPreferredSize(new Dimension(100, height));
			attributeStats.setMaximumSize(new Dimension(100, height));
			attributeStats.setMinimumSize(new Dimension(100, height));
			attributeStats.setEditable(false);
			attributeStats.setOpaque(false);

			SimpleAttributeSet rightAlign = new SimpleAttributeSet();
			StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
			attributeStats.selectAll();
			attributeStats.setParagraphAttributes(rightAlign, false);

			attributeStats.setText("Mercutio\nRobert\nHarrison\nWilliam\nGeorge\nAlbert\nBummington");

			/*
			 * Right Panel
			 */
			JPanel statistics = new JPanel();
			statistics.setLayout(new BoxLayout(statistics, BoxLayout.X_AXIS));
			statistics.setPreferredSize(new Dimension(250, height));
			statistics.setMaximumSize(new Dimension(250, height));
			statistics.setMinimumSize(new Dimension(250, height));
			statistics.setOpaque(false);

			JPanel textWrapper2 = new JPanel();
			textWrapper2.setLayout(new BoxLayout(textWrapper2, BoxLayout.Y_AXIS));
			textWrapper2.setOpaque(false);
			textWrapper2.add(Box.createVerticalStrut(4));

			//Names
			JTextArea statisticsNames = new JTextArea("Kills\nDeaths\n% Damage\n% Healing\nBest Crit\n\n            Party\nKills\nDeaths\n" +
					"Gold Found\nMax Gold\nHunts Done\nSteps Taken\nChests Found\nSigns Read\nDays dayed");
			statisticsNames.setFont(boldFont);
			statisticsNames.setPreferredSize(new Dimension(130, height));
			statisticsNames.setEditable(false);
			statisticsNames.setOpaque(false);

			//Values
			statisticsValues = new JTextPane();
			statisticsValues.setFont(normalFont);
			statisticsValues.setPreferredSize(new Dimension(50, height));
			statisticsValues.setEditable(false);
			statisticsValues.setOpaque(false);
			statisticsValues.selectAll();
			statisticsValues.setParagraphAttributes(rightAlign, false);

			statisticsValues.setText("17\n0\n15%\n5%\n117\n\n\n37\n2\n512\n442\n0\n1258\n4\n6\n0");

			/*
			 * Combining
			 */
			textWrapper.add(attributeNames);
			textWrapper2.add(statisticsNames);

			attributes.add(Box.createHorizontalStrut(40));
			attributes.add(textWrapper);
			attributes.add(attributeStats);
			attributes.add(Box.createHorizontalStrut(45));
			statistics.add(Box.createHorizontalStrut(35));
			statistics.add(textWrapper2);
			statistics.add(statisticsValues);
			statistics.add(Box.createHorizontalStrut(55));

			this.add(attributes);
			this.add(statistics);
		}

		public void update() {
			PartyMember member = data.getParty()[cursorPosition];
			attributeStats.setText("Mercutio\nRobert\nHarrison\nWilliam\nGeorge\nAlbert\nBummington");
			statisticsValues.setText(member.getKills() + "\n" + member.getDeaths() + "\n" + member.getDamagePercentage() + "\n" +
					member.getHealingPercentage() + "\n" + member.getHighestCrit() + "\n\n\n" + PartyMember.getPartyKills() + "\n" +
					PartyMember.getPartyDeaths() + "\n" + PartyMember.getGoldFound() + "\n" + PartyMember.getMaxGold() + "\n" +
					PartyMember.getHuntsDone() + "\n" + PartyMember.getStepsTaken() + "\n" + PartyMember.getChestsFound() + "\n" +
					PartyMember.getSignsRead() + "\n" + PartyMember.getDaysDayed());
		}
	}
}
