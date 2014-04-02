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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.Enums.BATTLE_STATE;
import GUI.Enums.GAME_STATE;
import Systems.Combatant;
import Systems.Encounter;
import Systems.Enemy;
import Systems.Enums.COMBAT_ACTION;
import Systems.Enums.COMBAT_START;
import Systems.GameData;
import Systems.PartyMember;

public class BattleScreen extends JPanel {

	private static final long serialVersionUID = 9019740276603325359L;
	private GameData data;
	private Encounter enemies;
	private COMBAT_START startCondition;

	private BattleArea battleArea;
	private BattleTurnOrder turnOrder;
	private BattleMenu menu;
	private DialogueBox dialogue;

	private PartyMember activeMember;
	private Enemy activeEnemy;
	private boolean playerMove = true;

	private BATTLE_STATE state = BATTLE_STATE.MAIN;
	
	public BattleScreen(GameData data)	{
		this.data = data;
		this.dialogue = data.getDialogueBox();
		menu = new BattleMenu(data);
		turnOrder = new BattleTurnOrder(data, this);
		battleArea = new BattleArea(data, this);
		
		Dimension screenSize = new Dimension(600, 600);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);

		this.setLayout(new BorderLayout());
		this.add(battleArea, BorderLayout.NORTH);
		this.add(turnOrder, BorderLayout.CENTER);
		this.add(menu, BorderLayout.SOUTH);

	}

	public void enterBattle(Encounter enemies)	{
		menu.erase();
		menu.update();
		data.setGameState(GAME_STATE.BATTLE);
		state = BATTLE_STATE.MAIN;
		data.getMenu().setVisible(true);         //Needed for some unknown stupid reason
		data.getMenu().setVisible(false);
		data.getMenu().shrink();
		data.getDialogueBox().shrink();
		
		startCondition = COMBAT_START.NORMAL;
		
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				data.getParty()[i].setCurrent(data.getParty()[i].getRight());
				data.getParty()[i].setOrigin(new Point(80, 90 + 80 * i));
			}
		}
		
		data.getGameBoard().add(this);
		activeMember = data.getParty()[0];

		this.enemies = enemies;
		
		turnOrder.initialize();
		
	}

	public void leaveBattle()	{
		data.setGameState(GAME_STATE.WALK);
		data.getMenu().restore();
		data.getDialogueBox().restore();
		data.getGameBoard().remove(this);
		data.getGameBoard().add(data.getDialogueBox(), BorderLayout.SOUTH);
		turnOrder.clear();
	}

	public void respondToInput(KeyEvent e)	{
		if (state == BATTLE_STATE.MAIN)	{
			if (e.getKeyCode() == KeyEvent.VK_UP)	{
				if (menu.getCursorPosition() == 2 || menu.getCursorPosition() == 3)	{
					menu.modifyCursorPosition(-2);
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
				if (menu.getCursorPosition() == 0 || menu.getCursorPosition() == 2)	{
					menu.modifyCursorPosition(1);
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
				if (menu.getCursorPosition() == 0 || menu.getCursorPosition() == 1)	{
					menu.modifyCursorPosition(2);
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
				if (menu.getCursorPosition() == 1 || menu.getCursorPosition() == 3)	{
					menu.modifyCursorPosition(-1);
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				switch (menu.getCursorPosition())	{
				case 0:
					battleArea.setTargetAlly(false);
					state = BATTLE_STATE.ATTACK_SELECTION; 
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
		else if (state == BATTLE_STATE.SELECT)	{

		}
		else if (state == BATTLE_STATE.ATTACK_SELECTION)	{
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT
					|| e.getKeyCode() == KeyEvent.VK_LEFT)	{
				battleArea.respondToInput(e);	
			}
			
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				startAttack();
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				state = BATTLE_STATE.MAIN;
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
		aggressionScore += 1.3; //buffer to make earlier levels easy to escape
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
		battleArea.update();
		if (state == BATTLE_STATE.ANIM_ATTACK)	{
			if (turnOverEh())	{
				checkForDeaths();
				state = BATTLE_STATE.MAIN;
				if (enemies.allDefeated())	{
					awardXP();
					checkForLevelUps();
					state = BATTLE_STATE.END;
				}
				turnOrder.calculateTurnOrder();
				
			}
			
		}
		else if (state == BATTLE_STATE.MAIN || state == BATTLE_STATE.END)	{
			if (dialogue.hasNextLine()){
				if (data.getGameState() != GAME_STATE.TALK)	{
					switchToTalk();
					dialogue.startDialogue();
				}
			}
			else if (state == BATTLE_STATE.END)	{
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
		state = BATTLE_STATE.ANIM_ATTACK;
		activeMember.attackTarget(enemies.toArrayList().get(battleArea.getEnemyCursorPosition()));
	}
	
	public void addBattleText(int damage, Combatant target)	{
		battleArea.addBattleText(damage, target);
	}

	@Override
	protected void paintComponent(Graphics g)	{
		super.paintComponent(g);
	}
	
	public BATTLE_STATE getState() {
		return state;
	}
	public COMBAT_START getStartingCondition()	{
		return startCondition;
	}
	public Encounter getEnemies()	{
		return enemies;
	}
	public boolean playerMoveEh()	{
		return playerMove;
	}
}
