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


	private InputStream stream;
	private Font baseFont;

	public BattleArea(GameData data, BattleScreen screen)	{
		this.data = data;
		this.battleScreen = screen;

		Dimension screenSize = new Dimension(600, 400);
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

	public void update()	{
		if (battleScreen.playerMoveEh())	{
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
		battleScreen.getEnemies().drawEnemies(g);
		if (battleScreen.getState() == BATTLE_STATE.ATTACK_SELECTION)	{
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
			Enemy target = battleScreen.getEnemies().toArrayList().get(enemyCursorPosition);
			g.drawImage(enemyCursor.getImage(), target.getOrigin().x - 10, 
					target.getOrigin().y + target.getHeight() / 2 + 10, null);
		}
		else	{
			g.drawImage(friendlyCursor.getImage(), 110, 105 + 80 * friendlyCursorPosition, null);
		}
	}

	public void addBattleText(int damage, Combatant target)	{
		battleText.add(new BattleText(Integer.toString(damage), target));
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

	public void setTargetAlly(boolean b)	{
		targetAlly = b;
	}

	public void respondToInput(KeyEvent e) {
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
	}

	public int getEnemyCursorPosition() {
		return enemyCursorPosition;
	}
}