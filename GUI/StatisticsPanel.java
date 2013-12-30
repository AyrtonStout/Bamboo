package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Systems.PartyMember;

public class StatisticsPanel extends JPanel {

	private static final long serialVersionUID = -9057800474525049386L;


	public StatisticsPanel()	{
		this.setPreferredSize(new Dimension(600, 600));
		
		this.add(new TopPanel());
		this.add(new CenterPanel());
		this.add(new BottomPanel());

	}

	private class TopPanel extends JPanel	{

		private static final long serialVersionUID = -7385760280733183237L;

		public TopPanel()	{
			this.setPreferredSize(new Dimension(600, 100));
			this.setBackground(Color.RED);
		}

	}

	private class CenterPanel extends JPanel	{

		private static final long serialVersionUID = -4403157000077270536L;

		public CenterPanel()	{
			this.setPreferredSize(new Dimension(600, 100));
			this.setBackground(Color.RED);	
		}

	}

	private class BottomPanel extends JPanel	{

		private static final long serialVersionUID = 6563412072256404570L;

		public BottomPanel()	{

			JTextPane attributeStats, statisticsValues;

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
