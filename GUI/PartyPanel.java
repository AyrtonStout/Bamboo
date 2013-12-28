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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Systems.PartyMember;

public class PartyPanel extends JPanel {
	
	private static final long serialVersionUID = 9030554443534484268L;

	private GameData data;
	private ImageIcon portrait = new ImageIcon();
	private ImageIcon party1, party2, party3, party4;
	private ImageIcon background = new ImageIcon("GUI/Resources/Party_Background.png");
	private ImageIcon[] party = new ImageIcon[] {party1, party2, party3, party4};
	
	private ImageIcon cursor = new ImageIcon("GUI/Resources/Icon_RedArrow.png");
	private int cursorPosition = 0;
	
	private InputStream stream;
	private Font menuFont, boldFont, statFont;
	
	HeaderPanel topPanel;
	CenterPanel midPanel;
	StatPanel bottomPanel;
	
	public PartyPanel(GameData gameData)	{
		data = gameData;
		
		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Arial.ttf"));
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			menuFont = baseFont.deriveFont(Font.PLAIN, 20);
			boldFont = baseFont.deriveFont(Font.BOLD, 18);
			statFont = baseFont.deriveFont(Font.PLAIN, 18);
		} catch (FontFormatException | IOException e) {
			System.err.println("Where all mah fonts at??");
			e.printStackTrace();
		}


		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.CYAN);
		
		
		topPanel = new HeaderPanel();
		midPanel = new CenterPanel();
		bottomPanel = new StatPanel();
		
		this.add(topPanel);
		this.add(midPanel);
		this.add(bottomPanel);
		
	}

	@Override
	protected void paintComponent(Graphics g)	{
		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(cursor.getImage(), 206 + cursorPosition * 50, 60, null);
	}
	
	public void update()	{
		portrait = data.getParty()[0].getPortrait();
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				party[i] = data.getParty()[i].getDown();
			}
		}
		midPanel.update();
		bottomPanel.update();
		
	}
	
	public void moveCursorLeft()	{
		if (cursorPosition > 0)	{
			cursorPosition--;
			update();
		}
	}
	
	public void moveCursorRight()	{
		if (cursorPosition < PartyMember.getPartySize() - 1)	{
			cursorPosition++;
			update();
		}
	}
	
	private class HeaderPanel extends JPanel	{
		
		private static final long serialVersionUID = 3917011061855068994L;
		private int height = 65;
		
		public HeaderPanel()	{
			this.setPreferredSize(new Dimension(600, height));
			this.setMinimumSize(new Dimension(600, height));
			this.setMaximumSize(new Dimension(600, height));
			this.setOpaque(false);
		}
	
		@Override
		protected void paintComponent(Graphics g)	{
			for (int i = 0; i < data.getParty().length; i++)	{
				if (data.getParty()[i] != null)	{
					g.drawImage(party[i].getImage(), 200 + 50 * i, 10, null);
				}
			}
		}
	}
	
	private class CenterPanel extends JPanel	{
		
		private static final long serialVersionUID = -6021372252484376467L;

		private int height = 130;
		
		private JLabel levelNumberLabel = new JLabel("", SwingConstants.CENTER);
		private JLabel healthNumberLabel = new JLabel();
		private JLabel manaNumberLabel = new JLabel();
		private JLabel xpEarnedText = new JLabel();
		private JLabel xpToGoText = new JLabel();
		
		
		public CenterPanel()	{
			
			this.setPreferredSize(new Dimension(600, height));
			this.setMaximumSize(new Dimension(600, height));
			this.setMinimumSize(new Dimension(600, height));
			this.setOpaque(false);
			
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
			leftPanel.setPreferredSize(new Dimension(100, height));
			leftPanel.setMaximumSize(new Dimension(100, height));
			leftPanel.setMinimumSize(new Dimension(100, height));
			leftPanel.setOpaque(false);
			
			JLabel levelTextLabel = new JLabel("LVL", SwingConstants.CENTER);
			levelTextLabel.setFont(boldFont);
			levelTextLabel.setPreferredSize(new Dimension(57, 20));
			levelTextLabel.setMaximumSize(new Dimension(57, 20));
			levelTextLabel.setMinimumSize(new Dimension(57, 20));
			
			levelNumberLabel.setFont(menuFont);
			levelNumberLabel.setPreferredSize(new Dimension(57, 20));
			levelNumberLabel.setMaximumSize(new Dimension(57, 20));
			levelNumberLabel.setMinimumSize(new Dimension(57, 20));
			
			leftPanel.add(Box.createVerticalStrut(35));
			leftPanel.add(levelTextLabel);
			leftPanel.add(levelNumberLabel);
			
			JPanel spacer = new JPanel();
			spacer.setPreferredSize(new Dimension(60, height));
			spacer.setMaximumSize(new Dimension(60, height));
			spacer.setMinimumSize(new Dimension(60, height));
			spacer.setOpaque(false);
			
			JPanel healthManaLabelPanel = new JPanel();
			healthManaLabelPanel.setLayout(new BoxLayout(healthManaLabelPanel, BoxLayout.Y_AXIS));
			healthManaLabelPanel.setPreferredSize(new Dimension(40, height));
			healthManaLabelPanel.setMaximumSize(new Dimension(40, height));
			healthManaLabelPanel.setMinimumSize(new Dimension(40, height));
			healthManaLabelPanel.setOpaque(false);
			
			JLabel healthLabel = new JLabel("HP");
			healthLabel.setFont(boldFont);
			JLabel manaLabel = new JLabel("MP");
			manaLabel.setFont(boldFont);
			
			healthManaLabelPanel.add(Box.createVerticalStrut(35));
			healthManaLabelPanel.add(healthLabel);
			healthManaLabelPanel.add(manaLabel);
			
			JPanel healthManaTextPanel = new JPanel();
			healthManaTextPanel.setLayout(new BoxLayout(healthManaTextPanel, BoxLayout.Y_AXIS));
			healthManaTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
			healthManaTextPanel.setPreferredSize(new Dimension(100, height));
			healthManaTextPanel.setMaximumSize(new Dimension(100, height));
			healthManaTextPanel.setMinimumSize(new Dimension(100, height));
			healthManaTextPanel.setOpaque(false);
			
			healthNumberLabel.setFont(menuFont);
			manaNumberLabel.setFont(menuFont);
			healthNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);
			manaNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);
			
			healthManaTextPanel.add(Box.createVerticalStrut(35));
			healthManaTextPanel.add(healthNumberLabel);
			healthManaTextPanel.add(manaNumberLabel);
			
			
			JPanel spacer2 = new JPanel();
			spacer2.setPreferredSize(new Dimension(20, height));
			spacer2.setMaximumSize(new Dimension(20, height));
			spacer2.setMinimumSize(new Dimension(20, height));
			spacer2.setOpaque(false);
			
			
			JPanel xpLabelPanel = new JPanel();
			xpLabelPanel.setLayout(new BoxLayout(xpLabelPanel, BoxLayout.Y_AXIS));
			xpLabelPanel.setPreferredSize(new Dimension(100, height));
			xpLabelPanel.setMaximumSize(new Dimension(100, height));
			xpLabelPanel.setMinimumSize(new Dimension(100, height));
			xpLabelPanel.setOpaque(false);
			
			JLabel xpEarnedLabel = new JLabel("XP Earned");
			JLabel xpToGoLabel = new JLabel("XP Level");
			xpEarnedLabel.setFont(boldFont);
			xpToGoLabel.setFont(boldFont);
			
			xpLabelPanel.add(Box.createVerticalStrut(35));
			xpLabelPanel.add(xpEarnedLabel);
			xpLabelPanel.add(xpToGoLabel);
			
			
			JPanel xpTextPanel = new JPanel();
			xpTextPanel.setLayout(new BoxLayout(xpTextPanel, BoxLayout.Y_AXIS));
			xpTextPanel.setPreferredSize(new Dimension(130, height));
			xpTextPanel.setMaximumSize(new Dimension(130, height));
			xpTextPanel.setMinimumSize(new Dimension(130, height));
			xpTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
			xpTextPanel.setOpaque(false);
			
			xpEarnedText.setFont(menuFont);
			xpToGoText.setFont(menuFont);
			xpEarnedText.setAlignmentX(RIGHT_ALIGNMENT);
			xpToGoText.setAlignmentX(RIGHT_ALIGNMENT);
			
			xpTextPanel.add(Box.createVerticalStrut(35));
			xpTextPanel.add(xpEarnedText);
			xpTextPanel.add(xpToGoText);
			
			this.add(leftPanel);
			this.add(spacer);
			this.add(healthManaLabelPanel);
			this.add(healthManaTextPanel);
			this.add(spacer2);
			this.add(xpLabelPanel);
			this.add(xpTextPanel);
		}
		
		protected void paintComponent(Graphics g)	{
			g.drawImage(portrait.getImage(), 60, 30, null);
		}
		
		public void update()	{
			PartyMember member = data.getParty()[cursorPosition];
			portrait = data.getParty()[cursorPosition].getPortrait();
			levelNumberLabel.setText(Integer.toString(member.getLevel()));
			healthNumberLabel.setText(member.getCurrentHealth().toString() + "/" + member.getMaxHealth().toString());
			manaNumberLabel.setText(member.getCurrentMana().toString() + "/" + member.getMaxMana().toString());
			xpEarnedText.setText(member.getXpTotalEarned() + " (-" + 
					Integer.toString(member.getXpRequirement() - member.getXpThisLevel()) + ")");
			xpToGoText.setText(member.getXpThisLevel() + "/" + member.getXpRequirement());
		}
	}
	
	private class StatPanel	extends JPanel {
		
		private static final long serialVersionUID = -6568528749764392354L;

		JTextPane attributeStats, statisticsValues;
		
		private int height = 405;

		
		public StatPanel()	{
			
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
			attributes.setPreferredSize(new Dimension(300, height));
			attributes.setMaximumSize(new Dimension(300, height));
			attributes.setMinimumSize(new Dimension(300, height));
			attributes.setOpaque(false);
			
			JPanel textWrapper = new JPanel();
			textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.Y_AXIS));
			textWrapper.setOpaque(false);
			textWrapper.add(Box.createVerticalStrut(4));
			
			//Names
			JTextArea attributeNames = new JTextArea("Strength\nAgility\nIntellect\nSpirit\nLuck\n\n" +
					"Attack Power\nSpell Power\nCrit Chance\nCrit Damage\nHit\nArmor Pen\nSpeed\n\nArmor\nStamina\nDodge\nResist");
			attributeNames.setFont(boldFont);
			attributeNames.setPreferredSize(new Dimension(130, height));
			attributeNames.setEditable(false);
			attributeNames.setOpaque(false);
			
			//Values
			attributeStats = new JTextPane();
			attributeStats.setFont(statFont);
			attributeStats.setPreferredSize(new Dimension(50, height));
			attributeStats.setMaximumSize(new Dimension(50, height));
			attributeStats.setMinimumSize(new Dimension(50, height));
			attributeStats.setEditable(false);
			attributeStats.setOpaque(false);
			
			SimpleAttributeSet rightAlign = new SimpleAttributeSet();  
			StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT); 
			attributeStats.selectAll();
			attributeStats.setParagraphAttributes(rightAlign, false);
			
			attributeStats.setText("5\n5\n5\n5\n5\n\n5\n5\n5\n200%\n5\n5\n5\n\n5\n5\n5\n5");
			
			/*
			 * Right Panel
			 */
			JPanel statistics = new JPanel();
			statistics.setLayout(new BoxLayout(statistics, BoxLayout.X_AXIS));
			statistics.setPreferredSize(new Dimension(300, height));
			statistics.setMaximumSize(new Dimension(300, height));
			statistics.setMinimumSize(new Dimension(300, height));
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
			statisticsValues.setFont(statFont);
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
			
			attributes.add(Box.createHorizontalStrut(85));
			attributes.add(textWrapper);
			attributes.add(attributeStats);
			attributes.add(Box.createHorizontalStrut(45));
			statistics.add(Box.createHorizontalStrut(35));
			statistics.add(textWrapper2);
			statistics.add(statisticsValues);
			statistics.add(Box.createHorizontalStrut(95));
			
			this.add(attributes);
			this.add(statistics);
					
		}
		
		public void update()	{
			PartyMember member = data.getParty()[cursorPosition];
			attributeStats.setText(member.getStrength().toString() + "\n" + member.getAgility() + "\n" + member.getIntellect() + "\n" +
					member.getSpirit() + "\n" + member.getLuck() + "\n\n" + member.getAttackPower() + "\n" + member.getSpellPower() + "\n" +
					member.getCritChance() + "%\n" + member.getCritDamage() + "%\n" + member.getHit() + "%\n" + member.getArmorPen() + "\n" +
					member.getSpeed() + "\n\n" + member.getArmor() + "\n" + member.getStamina() + "\n" + member.getDodge() + "%\n" + 
					member.getResist() + "%");
			statisticsValues.setText(member.getKills() + "\n" + member.getDeaths() + "\n" + member.getDamagePercentage() + "\n" + 
					member.getHealingPercentage() + "\n" + member.getHighestCrit() + "\n\n\n" + PartyMember.getPartyKills() + "\n" + 
					PartyMember.getPartyDeaths() + "\n" + PartyMember.getGoldFound() + "\n" + PartyMember.getMaxGold() + "\n" + 
					PartyMember.getHuntsDone() + "\n" + PartyMember.getStepsTaken() + "\n" + PartyMember.getChestsFound() + "\n" + 
					PartyMember.getSignsRead() + "\n" + PartyMember.getDaysDayed());
		}
	}
}
