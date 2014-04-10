package Battle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.DialogueBox;
import Battle.Enums.BATTLE_STATE;
import Systems.Consumable;
import Systems.Enums.CONSUMABLE_TYPE;
import Systems.Enums.GAME_STATE;
import Systems.Combatant;
import Systems.Encounter;
import Systems.Enemy;
import Battle.Enums.COMBAT_ACTION;
import Battle.Enums.COMBAT_START;
import Systems.GameData;

/**
 * @author mobius
 * 
 * The screen that the player conducts combat in. Combines the other smaller battle classes together, 
 * controls the keyboard input during battle, controls large scale events like the player entering or
 * leaving the battle, and controls the flow of each battle turn.
 */
public class BattleScreen extends JPanel {

	private static final long serialVersionUID = 9019740276603325359L;
	private ImageIcon background = new ImageIcon("GUI/Resources/Backgrounds/Grassland.png");

	private GameData data;
	private Encounter enemies;
	private COMBAT_START startCondition;

	private BattleArea battleArea;
	private BattleTurnOrder turnOrder;
	private BattleMenu menu;
	private BattleInfo info;
	private DialogueBox dialogue;
	private BattleItemsScreen itemScreen;

	private Combatant activeMember;
	private boolean playerMove = true;
	
	private final int PARTY_BUFFER = 140;        //Distance between the top of the screen and the first drawn party member
	private final int PARTY_WIDTH = 65;          //Distance between the party members

	private BATTLE_STATE state = BATTLE_STATE.MAIN;
	
	public BattleScreen(GameData data)	{
		this.data = data;
		this.dialogue = data.getDialogueBox();
		menu = new BattleMenu(data);
		turnOrder = new BattleTurnOrder(data, this);
		battleArea = new BattleArea(data, this);
		info = new BattleInfo();
		itemScreen = new BattleItemsScreen(data);
		
		Dimension screenSize = new Dimension(600, 600);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);

		this.setLayout(new BorderLayout());
		this.add(info, BorderLayout.NORTH);
		
		JPanel center = new JPanel();
		center.add(battleArea); center.add(turnOrder);
		center.setOpaque(false);
		this.add(center, BorderLayout.CENTER);
		this.add(menu, BorderLayout.SOUTH);
		
	}

	public void enterBattle(Encounter enemies)	{
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
				data.getParty()[i].setOrigin(new Point(80, PARTY_BUFFER + PARTY_WIDTH * i));
			}
		}
		
		data.getGameBoard().add(this);
		this.enemies = enemies;
		
		turnOrder.initialize();
		turnOrder.setVisible(true);
		activeMember = turnOrder.getActiveCombatant();		
	}

	public void leaveBattle()	{
		data.setGameState(GAME_STATE.WALK);
		data.getMenu().restore();
		data.getDialogueBox().restore();
		data.getGameBoard().remove(this);
		data.getGameBoard().add(data.getDialogueBox(), BorderLayout.SOUTH);
		menu.clear();
		turnOrder.clear();
		battleArea.clear();
	}

	public void respondToInput(KeyEvent e)	{
		if (state == BATTLE_STATE.MAIN)	{
			int startPos = menu.getCursorPosition();
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || 
					e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT)	{
				menu.modifyCursorPosition(e);
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				switch (menu.getCursorPosition())	{
				case 0:
					switchToAttack();
					break;
				case 1:
					switchToItems();
					break;
				case 2:
					break;
				case 3:
					attemptToRun();
					break;
				}
			}
			if ((startPos == 1 || startPos == 2) && menu.getCursorPosition() == 3)	{
				turnOrder.predictTurnOrder(0);
			}
			else if (startPos == 3 && (menu.getCursorPosition() == 1 || menu.getCursorPosition() == 2))	{
				turnOrder.calculateTurnOrder();
			}
		}
		
		else if (state == BATTLE_STATE.ITEM_SELECTION)	{
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT
					|| e.getKeyCode() == KeyEvent.VK_LEFT)	{
				itemScreen.respondToKeyPress(e);
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				state = BATTLE_STATE.ITEM_USE_SELECTION;
				battleArea.setTargetAlly(!((Consumable) itemScreen.getSelectedItem()).harmfulEh());
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				exitItemMenu();
			}
		}
		else if (state == BATTLE_STATE.ITEM_USE_SELECTION)	{
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT
					|| e.getKeyCode() == KeyEvent.VK_LEFT)	{
				battleArea.respondToInput(e, info);	
			}
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				startAttack();
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				info.setVisible(false);
				state = BATTLE_STATE.ITEM_SELECTION;
			}
		}
		else if (state == BATTLE_STATE.ATTACK_SELECTION)	{
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT
					|| e.getKeyCode() == KeyEvent.VK_LEFT)	{
				battleArea.respondToInput(e, info);	
			}
			
			else if (e.getKeyCode() == KeyEvent.VK_Z)	{
				startAttack();
			}
			else if (e.getKeyCode() == KeyEvent.VK_X)	{
				info.setVisible(false);
				state = BATTLE_STATE.MAIN;
			}
		}
	}
	
	private void attemptToRun()	{
		double partyEscape = calculatePartyEscapeScore();
		double enemyAggro = enemies.calculateAggressionScore();
		
		Random rand = new Random();
		partyEscape *= ((0.7 + rand.nextDouble()) / 2);
		enemyAggro *= ((0.5 + rand.nextDouble()) / 2);
		
		if (partyEscape > enemyAggro)	{
			leaveBattle(); 
		}
		else	{
			turnOrder.endCombatantTurn(0);
			dialogue.addDialogue("Can't escape!");
			switchToTalk();
			dialogue.startDialogue();
			activeMember = turnOrder.getActiveCombatant();
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
		aggressionScore += 1.5; //buffer to make earlier levels easy to escape
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
	private void switchToAttack()	{
		battleArea.setTargetAlly(false);
		state = BATTLE_STATE.ATTACK_SELECTION;
		info.setTarget(enemies.toArrayList().get(battleArea.getEnemyCursorPosition()));
		info.setVisible(true);
	}
	private void switchToItems()	{
		state = BATTLE_STATE.ITEM_SELECTION;
		this.remove(menu);
		this.add(itemScreen, BorderLayout.SOUTH);
		itemScreen.setVisible(false);                  //Sigh
		itemScreen.setVisible(true);
		itemScreen.updateList();
	}
	private void exitItemMenu()	{
		state = BATTLE_STATE.MAIN;
		itemScreen.setVisible(false);
		this.remove(itemScreen);
		this.add(menu, BorderLayout.SOUTH);
	}
	
	
	
	public void update()	{
		battleArea.update();
		if (activeMember.getClass() == Enemy.class && state == BATTLE_STATE.MAIN)	{
			state = BATTLE_STATE.ENEMY_MOVE;
		}
		if (state == BATTLE_STATE.ENEMY_MOVE)	{
			((Enemy) activeMember).takeAction(data.getParty());
			state = BATTLE_STATE.ANIM_ATTACK;
		}
		if (state == BATTLE_STATE.ANIM_ATTACK)	{
			if (activeMember.getCombatAction() == COMBAT_ACTION.IMPACT)	{
				data.getCombat().attack(activeMember, activeMember.getTarget());
				menu.update();
			}
			if (turnOverEh())	{
				checkForDeaths();
				state = BATTLE_STATE.MAIN;
				if (enemies.allDefeated())	{
					turnOrder.setVisible(false);
					awardXP();
					checkForLevelUps();
					state = BATTLE_STATE.END;
				}
				turnOrder.endCombatantTurn(1);
				activeMember = turnOrder.getActiveCombatant();
			}
			
		}
		if (state == BATTLE_STATE.MAIN || state == BATTLE_STATE.END || state == BATTLE_STATE.ENEMY_MOVE)	{
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
			if (data.getParty()[i] != null && data.getParty()[i].getCombatAction() != COMBAT_ACTION.IDLE)	{
				return false;
			}
		}
		for (int i = 0; i < enemies.toArrayList().size(); i++)	{
			if (enemies.toArrayList().get(i).getCombatAction() != COMBAT_ACTION.IDLE)	{
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
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				if (data.getParty()[i].justDiedEh())	{
					dialogue.addDialogue(data.getParty()[i].getName() + " died!");
					data.getParty()[i].setJustDied(false);
					data.getParty()[i].setAlive(false);
				}
			}
		}
	}
	
	private void awardXP()	{
		int partySize = 0;
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null && data.getParty()[i].aliveEh())	{
				partySize++;
			}
		}
		int xpEarned = (int) Math.round((double) enemies.getEarnedXP() / partySize);
		for (int i = 0; i < partySize; i++)	{
			data.getParty()[i].giveXP(xpEarned);
		}
		
		dialogue.addDialogue("Your party earned " + xpEarned + " XP!");
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
		info.setVisible(false);
		state = BATTLE_STATE.ANIM_ATTACK;
		if (battleArea.getTargetAlly() == true)	{
			activeMember.attackTarget(data.getParty()[battleArea.getFriendlyCursorPosition()]);
		}
		else	{
			activeMember.attackTarget(enemies.toArrayList().get(battleArea.getEnemyCursorPosition()));
		}
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
	
	@Override
	protected void paintComponent(Graphics g)	{
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);
	}
	
	public BattleArea getBattleArea()	{
		return battleArea;
	}
	public BattleMenu getBattleMenu()	{
		return menu;
	}
	
}
