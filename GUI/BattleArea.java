package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.Enums.BATTLE_STATE;
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

	private Font floatingTextFont;
	private int enemyCursorPosition = 0;
	private int friendlyCursorPosition = 0;
	private boolean targetAlly = false;

	private ArrayList<BattleText> battleText = new ArrayList<BattleText>();
	private ImageIcon enemyCursor = new ImageIcon("GUI/Resources/Sideways_RedArrow.png");
	private ImageIcon friendlyCursor = new ImageIcon("GUI/Resources/Sideways_GreenArrow.png");
	private final int ARROW_HEIGHT = 20;

	private InputStream stream;
	private Font baseFont;

	public BattleArea(GameData data, BattleScreen screen)	{
		this.data = data;
		this.battleScreen = screen;

		Dimension screenSize = new Dimension(600, 320);
		this.setPreferredSize(screenSize);
		this.setMaximumSize(screenSize);
		this.setMinimumSize(screenSize);

		try {
			stream = new BufferedInputStream(
					new FileInputStream("GUI/Resources/Font_Arial.ttf"));
			baseFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			floatingTextFont = baseFont.deriveFont(Font.PLAIN, 36);

		} catch (FontFormatException | IOException e) {
			System.err.println("Use your words!! Font not found");
			e.printStackTrace();
		}
		
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
		if (battleScreen.getState() == BATTLE_STATE.ATTACK_SELECTION)	{
			drawTargetCursor(g);
		}
		drawBattleText(g);
	}

	/**
	 * Draws all of the recent floating combat text
	 */
	private void drawBattleText(Graphics g)	{
		g.setFont(floatingTextFont);
		for (int i = 0; i < battleText.size(); i++)	{
			if (battleText.get(i).text.compareTo("MISS") == 0)	{
				g.setColor(Color.WHITE);
			}
			else if (battleText.get(i).crit == true)	{
				g.setColor(Color.YELLOW);
			}
			else	{
				g.setColor(Color.RED);
			}
			g.drawString(battleText.get(i).text, battleText.get(i).xCoordinate, battleText.get(i).yCoordinate);
			battleText.get(i).update();
			if (battleText.get(i).duration == 0)	{
				battleText.remove(i);
			}
		}
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
	 * Adds battle text above the character that took damage
	 * 
	 * @param damage The amount of damage the character took as well as the number that will appear
	 * @param target The character that will have the number placed over their head
	 */
	public void addBattleText(String damage, Combatant target, boolean crit)	{
		battleText.add(new BattleText(damage, target, crit));
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
	
	/**
	 * Removes all pending battle text
	 */
	public void clear()	{
		int size = battleText.size();
		for (int i = 0; i < size; i++)	{
			battleText.remove(0);
		}
	}
	
	
	
	/**
	 * The text that appears above a character when they take damage
	 */
	private class BattleText	{

		private String text;
		private int xCoordinate;
		private int yCoordinate;
		private int duration;
		private boolean crit;

		private static final int TEXT_SIZE = 20;
		
		public BattleText(String str, Combatant target, boolean crit)	{
			
			text = str;
			this.xCoordinate = target.getOrigin().x + (target.getWidth() / 2) - ((TEXT_SIZE * text.length()) / 2);
			this.yCoordinate = target.getOrigin().y - 10;
			duration = 65;
			this.crit = crit;
			
		}
		
		/**
		 * Counts down the duration of the battle text and causes a shake if the text is a crit
		 */
		public void update()	{
			if (crit)	{
				if (duration > 63)	{
					xCoordinate -= 2;
					yCoordinate -= 2;
				}
				else if (duration > 61)	{
					xCoordinate -= 2;
					yCoordinate += 2;
				}
				else if (duration > 59)	{
					xCoordinate += 4;
					yCoordinate += 2;
				}
				else if (duration > 57)	{
					xCoordinate += 2;
					yCoordinate -= 4;
				}
				else if (duration > 55)	{
					xCoordinate += 2;
					yCoordinate += 2;
				}
				else if (duration > 53)	{
					xCoordinate -= 4;
				}
				else if (duration > 51)	{
					xCoordinate += 2;
					yCoordinate += 2;
				}
				else if (duration > 49)	{
					xCoordinate -= 2;
					yCoordinate -= 2;
				}
			}
			duration--;
		}
	}
}