package BattleScreen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import BattleScreen.Enums.BATTLE_STATE;
import Systems.Combatant;
import Systems.Enemy;
import Systems.GameData;
import Systems.PartyMember;

/**
 * @author mobius
 * Represents the area on the battle screen where the combat actually takes place. This is the region of the screen
 * where the party and the enemies are displayed; as well this class also handles the appearance of the battle text
 */
public class BattleArea extends JPanel	{

	private static final long serialVersionUID = 1081923729370436576L;
	private BattleScreen battleScreen;
	private GameData data;

	private FloatingCombatText battleText = new FloatingCombatText();
	
	private int enemyCursorPosition = 0;
	private int friendlyCursorPosition = 0;
	private boolean targetAlly = false;

	private ImageIcon enemyCursor = new ImageIcon("GUI/Resources/Sideways_RedArrow.png");
	private ImageIcon friendlyCursor = new ImageIcon("GUI/Resources/Sideways_GreenArrow.png");
	private final int ARROW_HEIGHT = 20;

	public BattleArea(GameData data, BattleScreen screen)	{
		this.data = data;
		this.battleScreen = screen;

		Dimension screenSize = new Dimension(600, 320);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);

	}

	/**
	 * Updates all of the actors in the battle by calling their individual update() methods.
	 * This is mostly used for animating purposes where the actors are told to modify their
	 * position and redraw themselves.
	 */
	public void update()	{
//		if (battleScreen.playerMoveEh())	{
			for (int i = 0; i < data.getParty().length; i++)	{
				if (data.getParty()[i] != null)	{
					data.getParty()[i].update();
				}
			}
//		}
//		else	{
			ArrayList<Enemy> enemies = battleScreen.getEnemies().toArrayList();
			for (int i = 0; i < enemies.size(); i++)	{
				enemies.get(i).update();
			}
//		}
	}

	@Override
	protected void paintComponent(Graphics g)	{
		drawParty(g);
		battleScreen.getEnemies().drawEnemies(g);
		if (battleScreen.getState() == BATTLE_STATE.ATTACK_SELECTION || battleScreen.getState() == BATTLE_STATE.ITEM_USE_SELECTION ||
				battleScreen.getState() == BATTLE_STATE.SPELL_TARGET)	{
			drawTargetCursor(g);
		}
		battleText.drawText(g);
	}

	/**
	 * Tells all of the party members to draw themselves
	 */
	private void drawParty(Graphics g)	{
		for (int i = 0; i < data.getParty().length; i++)	{
			if (data.getParty()[i] != null)	{
				data.getParty()[i].drawSelf(g);
			}
		}
	}

	/**
	 * Draws the targeting cursor for attacks and abilities. Cursor is red for targeting an enemy
	 * and green when targeting a friendly.
	 */
	private void drawTargetCursor(Graphics g)	{
		if (!targetAlly)	{
			Enemy target = battleScreen.getEnemies().toArrayList().get(enemyCursorPosition);
			g.drawImage(enemyCursor.getImage(), target.getOrigin().x - (ARROW_HEIGHT / 2), 
					target.getOrigin().y + target.getHeight() / 2 - 10, null);
		}
		else	{
			PartyMember target = data.getParty()[friendlyCursorPosition];
			g.drawImage(friendlyCursor.getImage(), target.getOrigin().x + target.getWidth(), 
					target.getOrigin().y + target.getHeight() / 2 - (ARROW_HEIGHT / 2), null);
		}
	}


	/**
	 * Sets whether or not the targeting cursor is targeting an ally or an enemy
	 */
	public void setTargetAlly(boolean b)	{
		targetAlly = b;
	}
	
	/**
	 * @return Whether or not the targeting cursor is on an ally or enemy
	 */
	public boolean getTargetAlly()	{
		return targetAlly;
	}
	
	public Combatant getTarget()	{
		if (targetAlly)	{
			return data.getParty()[friendlyCursorPosition];
		}
		else	{
			return battleScreen.getEnemies().toArrayList().get(enemyCursorPosition);
		}
	}

	/**
	 * Controls the actions that can happen if a key is pressed while targeting characters
	 * 
	 * @param e The key pressed during selection
	 * @param info The info bar that needs to be updated after a successful key press
	 */
	public void respondToInput(KeyEvent e, BattleInfo info) {
		if (e.getKeyCode() == KeyEvent.VK_UP)	{
			if (targetAlly)	{
				if (friendlyCursorPosition > 0)	{
					friendlyCursorPosition--;
				}
			}
			else	{
				if (enemyCursorPosition > 0)	{
					enemyCursorPosition--;
				}
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)	{
			if (targetAlly)	{
				if (data.getParty()[friendlyCursorPosition + 1] != null)	{
					friendlyCursorPosition++;
				}
			}
			else	{
				if (enemyCursorPosition + 1 < battleScreen.getEnemies().toArrayList().size())	{
					enemyCursorPosition--;
				}
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)	{
			targetAlly = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)	{
			targetAlly = false;
		}
		
		if (targetAlly)	{
			info.setTarget(data.getParty()[friendlyCursorPosition]);
		}
		else	{
			info.setTarget(battleScreen.getEnemies().toArrayList().get(enemyCursorPosition));
		}
	}

	/**
	 * @return The position of the player's cursor on a group of enemies
	 */
	public int getEnemyCursorPosition() {
		return enemyCursorPosition;
	}
	
	/**
	 * @return The position of the player's cursor on the friendly party
	 */
	public int getFriendlyCursorPosition()	{
		return friendlyCursorPosition;
	}
	
	public FloatingCombatText getCombatText()	{
		return battleText;
	}
	
}