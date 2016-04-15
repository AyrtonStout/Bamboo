package GUI;

import Systems.PartyMember;

import javax.swing.*;
import java.awt.*;

public class PartyInfo extends JPanel {

	private static final long serialVersionUID = -6021372252484376467L;

	private final int HEIGHT = 110;
	private final int TOP_SPACE = 25;

	private JLabel levelNumberLabel = new JLabel("", SwingConstants.CENTER);
	private JLabel healthNumberLabel = new JLabel();
	private JLabel manaNumberLabel = new JLabel();
	private JLabel xpEarnedText = new JLabel();
	private JLabel xpToGoText = new JLabel();

	private ImageIcon portrait;

	public PartyInfo(Font menuFont, Font boldFont) {

		this.setPreferredSize(new Dimension(600, HEIGHT));
		this.setMaximumSize(new Dimension(600, HEIGHT));
		this.setMinimumSize(new Dimension(600, HEIGHT));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setPreferredSize(new Dimension(100, HEIGHT));
		leftPanel.setMaximumSize(new Dimension(100, HEIGHT));
		leftPanel.setMinimumSize(new Dimension(100, HEIGHT));
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

		leftPanel.add(Box.createVerticalStrut(TOP_SPACE));
		leftPanel.add(levelTextLabel);
		leftPanel.add(levelNumberLabel);

		JPanel healthManaLabelPanel = new JPanel();
		healthManaLabelPanel.setLayout(new BoxLayout(healthManaLabelPanel, BoxLayout.Y_AXIS));
		healthManaLabelPanel.setPreferredSize(new Dimension(40, HEIGHT));
		healthManaLabelPanel.setMaximumSize(new Dimension(40, HEIGHT));
		healthManaLabelPanel.setMinimumSize(new Dimension(40, HEIGHT));
		healthManaLabelPanel.setOpaque(false);

		JLabel healthLabel = new JLabel("HP");
		healthLabel.setFont(boldFont);
		healthLabel.setOpaque(false);
		JLabel manaLabel = new JLabel("MP");
		manaLabel.setFont(boldFont);
		manaLabel.setOpaque(false);

		healthManaLabelPanel.add(Box.createVerticalStrut(TOP_SPACE));
		healthManaLabelPanel.add(healthLabel);
		healthManaLabelPanel.add(manaLabel);

		JPanel healthManaTextPanel = new JPanel();
		healthManaTextPanel.setLayout(new BoxLayout(healthManaTextPanel, BoxLayout.Y_AXIS));
		healthManaTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
		healthManaTextPanel.setPreferredSize(new Dimension(100, HEIGHT));
		healthManaTextPanel.setMaximumSize(new Dimension(100, HEIGHT));
		healthManaTextPanel.setMinimumSize(new Dimension(100, HEIGHT));
		healthManaTextPanel.setOpaque(false);

		healthNumberLabel.setFont(menuFont);
		manaNumberLabel.setFont(menuFont);
		healthNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);
		manaNumberLabel.setAlignmentX(RIGHT_ALIGNMENT);

		healthManaTextPanel.add(Box.createVerticalStrut(TOP_SPACE));
		healthManaTextPanel.add(healthNumberLabel);
		healthManaTextPanel.add(manaNumberLabel);

		JPanel xpLabelPanel = new JPanel();
		xpLabelPanel.setLayout(new BoxLayout(xpLabelPanel, BoxLayout.Y_AXIS));
		xpLabelPanel.setPreferredSize(new Dimension(100, HEIGHT));
		xpLabelPanel.setMaximumSize(new Dimension(100, HEIGHT));
		xpLabelPanel.setMinimumSize(new Dimension(100, HEIGHT));
		xpLabelPanel.setOpaque(false);

		JLabel xpEarnedLabel = new JLabel("XP Earned");
		JLabel xpToGoLabel = new JLabel("XP Level");
		xpEarnedLabel.setFont(boldFont);
		xpToGoLabel.setFont(boldFont);

		xpLabelPanel.add(Box.createVerticalStrut(TOP_SPACE));
		xpLabelPanel.add(xpEarnedLabel);
		xpLabelPanel.add(xpToGoLabel);

		JPanel xpTextPanel = new JPanel();
		xpTextPanel.setLayout(new BoxLayout(xpTextPanel, BoxLayout.Y_AXIS));
		xpTextPanel.setPreferredSize(new Dimension(130, HEIGHT));
		xpTextPanel.setMaximumSize(new Dimension(130, HEIGHT));
		xpTextPanel.setMinimumSize(new Dimension(130, HEIGHT));
		xpTextPanel.setAlignmentX(RIGHT_ALIGNMENT);
		xpTextPanel.setOpaque(false);

		xpEarnedText.setFont(menuFont);
		xpToGoText.setFont(menuFont);
		xpEarnedText.setAlignmentX(RIGHT_ALIGNMENT);
		xpToGoText.setAlignmentX(RIGHT_ALIGNMENT);

		xpTextPanel.add(Box.createVerticalStrut(TOP_SPACE));
		xpTextPanel.add(xpEarnedText);
		xpTextPanel.add(xpToGoText);

		this.add(leftPanel);
		this.add(Box.createHorizontalStrut(60));
		this.add(healthManaLabelPanel);
		this.add(healthManaTextPanel);
		this.add(Box.createHorizontalStrut(20));
		this.add(xpLabelPanel);
		this.add(xpTextPanel);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(portrait.getImage(), 60, TOP_SPACE - 10, null);
	}

	public void update(PartyMember displayedMember) {
		portrait = displayedMember.getPortrait();
		levelNumberLabel.setText(Integer.toString(displayedMember.getLevel()));
		healthNumberLabel.setText(displayedMember.getCurrentHealth().toString() + "/" + displayedMember.getMaxHealth().toString());
		manaNumberLabel.setText(displayedMember.getCurrentMana().toString() + "/" + displayedMember.getMaxMana().toString());
		xpEarnedText.setText(displayedMember.getXpTotalEarned() + " (-" +
				Integer.toString(displayedMember.getXpRequirement() - displayedMember.getXpThisLevel()) + ")");
		xpToGoText.setText(displayedMember.getXpThisLevel() + "/" + displayedMember.getXpRequirement());
	}
}
