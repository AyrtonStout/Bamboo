package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.Enums.GAME_STATE;
import Systems.Combatant;
import Systems.Encounter;
import Systems.Enemy;
import Systems.Enums.COMBAT_ACTION;
import Systems.GameData;
import Systems.PartyMember;

public class BattleScreen extends JPanel {

	private static final long serialVersionUID = 9019740276603325359L;
	private GameData data;
	private Encounter enemies;

	private BattleArea battleArea = new BattleArea();
	private TurnOrder turns = new TurnOrder();
	private Menu menu = new Menu();
	private DialogueBox dialogue;

	private final int MAIN = 0;
	private final int SELECT = 1;
	private final int WAIT = 2;
	private final int ATTACK_SELECTION = 3;
	private final int END = 4;
	
	private final int ANIM_ATTACK = 5;
	private final int ANIM_RECOIL = 6;
	
	private PartyMember activeMember;
	private Enemy activeEnemy;
	private boolean playerMove = true;

	private int state = MAIN;
	
	private Font floatingTextFont;
	

	public BattleScreen(GameData data)	{
		this.data = data;
		this.dialogue = data.getDialogueBox();

		InputStream stream;
		Font baseFont;
		
		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Arial.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			floatingTextFont = baseFont.deriveFont(Font.PLAIN, 36);
			
		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}
		
		Dimension screenSize = new Dimension(600, 600);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);

		this.setLayout(new BorderLayout());
		this.add(battleArea, BorderLayout.NORTH);
		this.add(turns, BorderLayout.CENTER);
		this.add(menu, BorderLayout.SOUTH);

	}

	public void enterBattle(Encounter enemies)	{
		menu.erase();
		menu.update();
		data.setGameState(GAME_STATE.BATTLE);
		state = MAIN;
		data.getMenu().setVisible(true);         //Needed for some unknown stupid reason
		data.getMenu().setVisible(false);
		data.getMenu().shrink();
		data.getDialogueBox().shrink();
		
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				data.getParty()[i].setCurrent(data.getParty()[i].getRight());
				data.getParty()[i].setOrigin(new Point(80, 90 + 80 * i));
			}
		}
		
		data.getGameBoard().add(this);
		activeMember = data.getParty()[0];

		this.enemies = enemies;
		
	}

	public void leaveBattle()	{
//		switchToMain();
		data.setGameState(GAME_STATE.WALK);
		data.getMenu().restore();
		data.getDialogueBox().restore();
		data.getGameBoard().remove(this);
		data.getGameBoard().add(data.getDialogueBox(), BorderLayout.SOUTH);
//		data.getDialogueBox().setVisible(true);
//		data.getDialogueBox().restore();
	}

	public void respondToInput(KeyEvent e)	{
		if (state == MAIN)	{
			if (e.getKeyCode() == KeyEvent.VK_UP)	{
				if (menu.cursorPosition == 2 || menu.cursorPosition == 3)	{
					menu.cursorPosition -= 2;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
				if (menu.cursorPosition == 0 || menu.cursorPosition == 2)	{
					menu.cursorPosition += 1;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
				if (menu.cursorPosition == 0 || menu.cursorPosition == 1)	{
					menu.cursorPosition += 2;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
				if (menu.cursorPosition == 1 || menu.cursorPosition == 3)	{
					menu.cursorPosition -= 1;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				switch (menu.cursorPosition)	{
				case 0:
					battleArea.targetAlly = false;
					state = ATTACK_SELECTION; 
					break;
				case 1:
					switchToTalk(); 
					ArrayList<String> string = new ArrayList<String>();
					string.add("Hi brozinsky!!! Words!!");
					string.add("MORE WORDS");
					dialogue.setDialogue(string, false); 
					break;
				case 2:
					break;
				case 3:
					attemptToRun();
					break;
				}
			}
		}
		else if (state == SELECT)	{

		}
		else if (state == ATTACK_SELECTION)	{
			if (e.getKeyCode() == KeyEvent.VK_UP)	{
				if (battleArea.targetAlly)	{
					if (battleArea.friendlyCursorPosition > 0)	{
						battleArea.friendlyCursorPosition--;
					}
				}
				else	{
					if (battleArea.enemyCursorPosition > 0)	{
						battleArea.enemyCursorPosition--;
					}
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
				if (battleArea.targetAlly)	{
					if (data.getParty()[battleArea.friendlyCursorPosition + 1] != null)	{
						battleArea.friendlyCursorPosition++;
					}
				}
				else	{
					if (battleArea.enemyCursorPosition + 1 < enemies.toArrayList().size())	{
						battleArea.enemyCursorPosition--;
					}
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
				battleArea.targetAlly = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
				battleArea.targetAlly = false;
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				startAttack();
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				state = MAIN;
			}
		}
	}
	
	private void attemptToRun()	{
		double partyEscape = calculatePartyEscapeScore();
		double enemyAggro = enemies.calculateAggressionScore();
		
		System.out.println(partyEscape);
		System.out.println(enemyAggro+"\n");
		
		Random rand = new Random();
		partyEscape *= ((0.7 + rand.nextDouble()) / 2);
		enemyAggro *= ((0.5 + rand.nextDouble()) / 2);
		
		System.out.println(partyEscape);
		System.out.println(enemyAggro);
		System.out.println();
		
		if (partyEscape > enemyAggro)	{
			leaveBattle(); 
		}
	}
	
	private double calculatePartyEscapeScore()	{
		double aggressionScore = 0;
		int highestLevel = 0;
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null && data.getParty()[i].aliveEh())	{
				aggressionScore += (data.getParty()[i].getLevel() / 2.0) * data.getParty()[i].getHealthPercentage();
				if (data.getParty()[i].getLevel() > highestLevel)	{
					highestLevel = data.getParty()[i].getLevel();
				}
			}
		}
		aggressionScore += highestLevel / 2.0;
		return aggressionScore;
	}

	private void switchToTalk()	{
		this.remove(menu);
		dialogue.restore();
		this.add(dialogue, BorderLayout.SOUTH);
	}
	public void switchToMain()	{
		this.remove(dialogue);
		this.add(menu, BorderLayout.SOUTH);
	}
	
	public void update()	{
//		System.out.println(enemies.toArrayList().get(0).getCurrentHealth().getActual());
		battleArea.update();
		if (state == ANIM_ATTACK)	{
			if (turnOverEh())	{
				checkForDeaths();
				state = MAIN;
				if (enemies.allDefeated())	{
					awardXP();
					checkForLevelUps();
					state = END;
				}
			}
			
		}
		else if (state == MAIN || state == END)	{
			if (dialogue.hasNextLine()){
				if (data.getGameState() != GAME_STATE.TALK)	{
					switchToTalk();
					dialogue.startDialogue();
				}
			}
			else if (state == END)	{
				leaveBattle();
			}
		}
	}
	
	private boolean turnOverEh()	{
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null && data.getParty()[i].getAction() != COMBAT_ACTION.IDLE)	{
				return false;
			}
		}
		return true;
	}
	
	private void checkForDeaths()	{
		for (int i = 0; i < enemies.toArrayList().size(); i++)	{
			if (enemies.toArrayList().get(i).justDiedEh())	{
				dialogue.addDialogue(enemies.toArrayList().get(i).getName() + " has been killed!");
				enemies.toArrayList().get(i).setJustDied(false);
			}
		}
	}
	
	private void awardXP()	{
		dialogue.addDialogue("Your party earned " + enemies.earnedXP() + " XP!");
		enemies.giveXP(data.getParty());
	}
	
	private void checkForLevelUps()	{
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null && data.getParty()[i].levelUpEh())	{
				while (data.getParty()[i].levelUpEh())	{
					data.getParty()[i].levelUp();
				}
				dialogue.addDialogue(data.getParty()[i].getName() + " is now level " + data.getParty()[i].getLevel() + "!");
			}
		}
	}
	
	private void startAttack()	{
		state = ANIM_ATTACK;
		activeMember.attackTarget(enemies.toArrayList().get(battleArea.enemyCursorPosition));
	}
	
	public void addBattleText(int damage, Combatant target)	{
		battleArea.addBattleText(damage, target);
	}

	@Override
	protected void paintComponent(Graphics g)	{
		super.paintComponent(g);
	}


	private class BattleArea extends JPanel	{

		private static final long serialVersionUID = 1081923729370436576L;

		private int enemyCursorPosition = 0;
		private int friendlyCursorPosition = 0;
		private boolean targetAlly = false;

		private ArrayList<BattleText> battleText = new ArrayList<BattleText>();
		private ImageIcon enemyCursor = new ImageIcon("GUI/Resources/Sideways_RedArrow.png");
		private ImageIcon friendlyCursor = new ImageIcon("GUI/Resources/Sideways_GreenArrow.png");


		public BattleArea()	{
			Dimension screenSize = new Dimension(600, 400);
			this.setPreferredSize(screenSize);
			this.setMaximumSize(screenSize);
			this.setMinimumSize(screenSize);

		}
		
		public void update()	{
			if (playerMove)	{
				for (int i = 0; i < data.getParty().length; i++)	{
					if (data.getParty()[i] != null)	{
						data.getParty()[i].update();
					}
				}
			}
		}

		@Override
		protected void paintComponent(Graphics g)	{
			drawBattleText(g);
			drawParty(g);
			enemies.drawEnemies(g);
			if (state == ATTACK_SELECTION)	{
				drawTargetCursor(g);
			}
		}
		
		private void drawBattleText(Graphics g)	{
			g.setColor(Color.RED);
			g.setFont(floatingTextFont);
			for (int i = 0; i < battleText.size(); i++)	{
				g.drawString(battleText.get(i).text, battleText.get(i).x, battleText.get(i).y);
				battleText.get(i).duration--;
				if (battleText.get(i).duration == 0)	{
					battleText.remove(i);
				}
			}
		}
		private void drawParty(Graphics g)	{
			for (int i = 0; i < data.getParty().length; i++)	{
				if (data.getParty()[i] != null)	{
					data.getParty()[i].drawSelf(g);
				}
			}
		}
		
		private void drawTargetCursor(Graphics g)	{
			if (!targetAlly)	{
				Enemy target = enemies.toArrayList().get(enemyCursorPosition);
				g.drawImage(enemyCursor.getImage(), target.getOrigin().x - 10, 
						target.getOrigin().y + target.getHeight() / 2 + 10, null);
			}
			else	{
				g.drawImage(friendlyCursor.getImage(), 110, 105 + 80 * friendlyCursorPosition, null);
			}
		}
		
		public void addBattleText(int damage, Combatant target)	{
			battleArea.battleText.add(new BattleText(Integer.toString(damage), target));
		}
		
		private class BattleText	{

			private String text;
			private int x;
			private int y;
			private int duration;
			
			public BattleText(String str, Combatant target)	{
				text = str;
				this.x = target.getOrigin().x + (target.getWidth() / 2) + (25 - 10 * text.length());
				this.y = target.getOrigin().y + 10;
				duration = 55;
			}
			
		}

	}

	private class TurnOrder extends JPanel	{

		private static final long serialVersionUID = -4464976184980976130L;

		public TurnOrder()	{
			Dimension screenSize = new Dimension(600, 50);
			this.setPreferredSize(screenSize);
			this.setMaximumSize(screenSize);
			this.setMinimumSize(screenSize);

			this.setBackground(Color.BLUE);
		}
		
		private class TurnIcon extends JPanel	{
			
//			private 
			
		}

	}

	private class Menu extends JPanel	{

		private static final long serialVersionUID = 7952290532732938184L;
		private ImageIcon background = new ImageIcon("GUI/Resources/TextBox_Background.png");

		private JLabel attack = new JLabel("Attack");
		private JLabel magic = new JLabel("Magic");
		private JLabel item = new JLabel("Item");
		private JLabel run = new JLabel("Run");

		private ImageIcon cursor = new ImageIcon("GUI/Resources/Sideways_Arrow.png");
		private int cursorPosition;

		private Font partyFont, actionFont;

		private partyStatus[] partyStatuses;


		public Menu()	{
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
			if (state == MAIN)	{
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

}
