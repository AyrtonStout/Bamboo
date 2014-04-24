package BattleScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import BattleScreen.Enums.TEXT_TYPE;
import Systems.Combatant;

public class FloatingCombatText {

	private ArrayList<BattleText> floatingText = new ArrayList<BattleText>();

	private Font floatingTextFont;

	public FloatingCombatText()	{
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

	}

	/**
	 * Adds battle text above a character that was affected by damage or healing
	 * 
	 * @param damage The amount of damage the character took as well as the number that will appear
	 * @param target The character that will have the number placed over their head
	 * @param textType The kind of attack or effect that caused the text to appear. i.e. crit, miss, heal 
	 */
	public void addBattleText(String damage, Combatant target, TEXT_TYPE textType)	{
		floatingText.add(new BattleText(damage, target, textType));
	}

	/**
	 * Adds battle text above a character that was affected by damage or healing but adds a delay to its appearance
	 * 
	 * @param damage The amount of damage the character took as well as the number that will appear
	 * @param target The character that will have the number placed over their head
	 * @param textType The kind of attack or effect that caused the text to appear. i.e. crit, miss, heal 
	 * @param delay The amount of time before the text appears and before the duration begins counting down
	 */
	public void addDelayedBattleText(String damage, Combatant target, TEXT_TYPE textType, int delay)	{
		floatingText.add(new BattleText(damage, target, textType, delay));
	}

	public void drawText(Graphics g)	{
		g.setFont(floatingTextFont);
		for (int i = 0; i < floatingText.size(); i++)	{
			if (floatingText.get(i).delay == 0)	{
				switch (floatingText.get(i).textType)	{
				case CRIT:
					g.setColor(Color.YELLOW);
					break;
				case DAMAGE:
					g.setColor(Color.RED);
					break;
				case HEAL:
					g.setColor(Color.GREEN);
					break;
				case MANA_RESTORE:
					g.setColor(Color.BLUE);
					break;
				case MISS:
					g.setColor(Color.WHITE);
					break;
				}

				g.drawString(floatingText.get(i).text, floatingText.get(i).xCoordinate, floatingText.get(i).yCoordinate);
			}
			floatingText.get(i).update();
			if (floatingText.get(i).duration == 0)	{
				floatingText.remove(i);
			}
		}
	}

	/**
	 * Removes all pending battle text
	 */
	public void clear()	{
		while (!floatingText.isEmpty())	{
			floatingText.remove(0);
		}
	}

	/**
	 * The text that appears above a character when they take damage
	 */
	private class BattleText	{

		private String text;
		private int xCoordinate;
		private int yCoordinate;
		private int duration;          //60 = 1 second
		private int delay;             //Amount of time before the text is displayed and the duration begins counting down
		private TEXT_TYPE textType;

		private static final int TEXT_SIZE = 20;

		public BattleText(String str, Combatant target, TEXT_TYPE textType)	{

			text = str;
			this.xCoordinate = target.getOrigin().x + (target.getWidth() / 2) - ((TEXT_SIZE * text.length()) / 2);
			this.yCoordinate = target.getOrigin().y - 10;
			duration = 65;
			delay = 0;
			this.textType = textType;

		}

		public BattleText(String str, Combatant target, TEXT_TYPE textType, int delay)	{

			text = str;
			this.xCoordinate = target.getOrigin().x + (target.getWidth() / 2) - ((TEXT_SIZE * text.length()) / 2);
			this.yCoordinate = target.getOrigin().y - 10;
			duration = 65;
			this.delay = delay;
			this.textType = textType;

		}


		/**
		 * Counts down the duration of the battle text and causes a shake if the text is a crit
		 */
		public void update()	{
			if (delay > 0)	{
				delay--;
			}
			else	{
				if (textType == TEXT_TYPE.CRIT)	{
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


}
