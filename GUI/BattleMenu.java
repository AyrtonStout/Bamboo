package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import GUI.Enums.BATTLE_STATE;
import Systems.GameData;
import Systems.PartyMember;

public class BattleMenu extends JPanel	{

	private static final long serialVersionUID = 7952290532732938184L;
	private GameData data;
	private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");

	private JLabel attack = new JLabel("Attack");
	private JLabel magic = new JLabel("Magic");
	private JLabel item = new JLabel("Item");
	private JLabel run = new JLabel("Run");

	private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
	private int cursorPosition;

	private Font partyFont, actionFont;

	private partyStatus[] partyStatuses;


	public BattleMenu(GameData data)	{
		this.data = data;
		
		Dimension screenSize = new Dimension(600, 150);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);
		this.setLayout(new BorderLayout());
		this.setOpaque(false);

		try {
			InputStream stream;
			Font baseFont;

			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Arial.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			partyFont = baseFont.deriveFont(Font.PLAIN, 18);
			actionFont = baseFont.deriveFont(Font.BOLD, 24);

		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}

		JLabel[] labels = new JLabel[]{attack, magic, item, run};
		partyStatuses = new partyStatus[] {new partyStatus(), 
				new partyStatus(), new partyStatus(), new partyStatus()}; 

		JPanel status = new JPanel();
		status.setPreferredSize(new Dimension(320, 150));
		status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
		status.setOpaque(false);

		status.add(Box.createVerticalStrut(10));
		for (int i = 0; i < partyStatuses.length - 1; i++)	{
			status.add(partyStatuses[i]);
			status.add(Box.createVerticalStrut(6));
		}
		status.add(partyStatuses[partyStatuses.length - 1]);


		for (int i = 0; i < labels.length; i++)	{
			labels[i].setFont(actionFont);
		}

		JPanel commandsTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		commandsTop.setPreferredSize(new Dimension(280, 50));
		JPanel frontSpacer1 = new JPanel();
		frontSpacer1.setPreferredSize(new Dimension(40, 50));
		frontSpacer1.setOpaque(false);
		commandsTop.add(frontSpacer1);
		commandsTop.add(attack);
		JPanel spacer = new JPanel();
		spacer.setPreferredSize(new Dimension(30, 50));
		spacer.setOpaque(false);
		commandsTop.add(spacer);
		commandsTop.add(item);
		commandsTop.setOpaque(false);

		JPanel commandsBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		commandsBottom.setPreferredSize(new Dimension(280, 50));
		JPanel frontSpacer2 = new JPanel();
		frontSpacer2.setPreferredSize(new Dimension(40, 50));
		frontSpacer2.setOpaque(false);
		commandsBottom.add(frontSpacer2);
		commandsBottom.add(magic);
		JPanel spacer2 = new JPanel();
		spacer2.setPreferredSize(new Dimension(34, 50));
		spacer2.setOpaque(false);
		commandsBottom.add(spacer2);
		commandsBottom.add(run);
		commandsBottom.setOpaque(false);

		JPanel commands = new JPanel();
		commands.setLayout(new BoxLayout(commands, BoxLayout.Y_AXIS));
		commands.setPreferredSize(new Dimension(280, 150));

		commands.add(Box.createVerticalStrut(18));
		commands.add(commandsTop);
		commands.add(commandsBottom);
		commands.add(Box.createVerticalStrut(30));
		commands.setOpaque(false);

		this.add(status, BorderLayout.WEST);
		this.add(commands, BorderLayout.EAST);

	}

	@Override
	protected void paintComponent(Graphics g)	{
		g.drawImage(background.getImage(), 0, 0, null);
		if (data.getBattleScreen().getState() == BATTLE_STATE.MAIN)	{
			switch (cursorPosition)	{
			case 0:
				g.drawImage(cursor.getImage(), 350, 36, null); break;
			case 1:
				g.drawImage(cursor.getImage(), 462, 36, null); break;
			case 2:
				g.drawImage(cursor.getImage(), 350, 86, null); break;
			case 3:
				g.drawImage(cursor.getImage(), 462, 86, null); break;
			}
		}
	}

	public void update()	{
		for (int i = 0; i < partyStatuses.length; i++)	{
			if (data.getParty()[i] != null)	{
				partyStatuses[i].update(data.getParty()[i]);
			}
		}
	}

	public void erase()	{
		for (int i = 0; i < partyStatuses.length; i++)	{
			partyStatuses[i].erase();
		}
	}
	
	public int getCursorPosition()	{
		return cursorPosition;
	}
	
	public void modifyCursorPosition(int i)	{
		cursorPosition += i;
	}


	private class partyStatus extends JPanel	{

		private static final long serialVersionUID = 7766824167479673649L;

		JLabel name = new JLabel();
		JLabel health = new JLabel();
		JLabel mana = new JLabel();

		public partyStatus()	{
			this.setPreferredSize(new Dimension(320, 30));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setOpaque(false);

			JPanel nameLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JPanel healthLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JPanel manaLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JPanel[] panels = new JPanel[]{nameLabel, healthLabel, manaLabel};

			Dimension panelSize = new Dimension();
			for (int i = 0; i < panels.length; i++)	{
				panels[i].setPreferredSize(panelSize);
				panels[i].setFont(partyFont);
				panels[i].setOpaque(false);
			}

			nameLabel.add(name);
			healthLabel.add(health);
			manaLabel.add(mana);

			this.add(Box.createHorizontalStrut(10));
			this.add(nameLabel);
			this.add(Box.createHorizontalStrut(10));
			this.add(healthLabel);
			this.add(Box.createHorizontalStrut(10));
			this.add(manaLabel);
			this.add(Box.createHorizontalStrut(20));


		}

		public void update(PartyMember member)	{
			name.setText(member.getName());
			health.setText(member.getCurrentHealth().getActual() + "/" + member.getMaxHealth().getActual());
			mana.setText(member.getCurrentMana().getActual() + "/" + member.getMaxMana().getActual());

		}

		public void erase()	{
			name.setText("");
			health.setText("");
			mana.setText("");
		}

	}

}