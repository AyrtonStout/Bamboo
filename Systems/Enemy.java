package Systems;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

import Systems.Enums.MONSTER;

public class Enemy implements Serializable {
	
	private String name;
	private ImageIcon picture;
	private int width, height;
	private Point origin;                 //The point that the enemy is drawn to on the battle screen
	
	private Stat attack = new Stat(0);
	private Stat defense = new Stat(0);
	private Stat magic = new Stat(0);
	private Stat magDefense = new Stat(0);
	private Stat crit = new Stat(0);
	private Stat speed = new Stat(0);
	private int level; 
	private int xpPerLevel;
	
	private Stat currentHealth = new Stat(1), maximumHealth = new Stat(1);
	private Stat currentMana = new Stat(0), maximumMana = new Stat(0);
	
	public Enemy (MONSTER type)	{
		Random rand = new Random();
		int minLevel = 0, maxLevel = 0;
		int healthBase = 0, healthGrowth = 0;
		double attackBase = 0, defenseBase = 0, magicBase = 0, magDefenseBase = 0;
		double attackGrowth = 0, defenseGrowth = 0, magicGrowth = 0, magDefenseGrowth = 0;
		
		switch (type)	{
		case GIANT_RAT:
			name = "Giant Rat";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			width = 100; height = 150;
			xpPerLevel = 40;
			
			minLevel = 1;       maxLevel = 3;
			
			attackBase = 5.2;       attackGrowth = 1.1;
			defenseBase = 3.6;      defenseGrowth = 0.9;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 1.2;   magDefenseGrowth = 0.3;
			
			healthBase = 30;    healthGrowth = 5;
			
			break;
		case RAZORCLAW_CRAB:
			name = "Razorclaw Crab";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			width = 150; height = 150;
			xpPerLevel = 45;
			
			minLevel = 2;       maxLevel = 4;
			
			attackBase = 2.4;       attackGrowth = 0.5;
			defenseBase = 4.8;      defenseGrowth = 1.9;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 2.2;   magDefenseGrowth = 0.7;
			
			healthBase = 35;    healthGrowth = 6;
			
			break;
		case DEATHSTALKER_CROW:
			name = "Deathstalker Crow";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			width = 100; height = 150;
			xpPerLevel = 45;
			
			minLevel = 3;       maxLevel = 3;
			
			attackBase = 3.2;       attackGrowth = 1.4;
			defenseBase = 2.6;      defenseGrowth = 0.7;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 1.6;   magDefenseGrowth = 0.4;
			
			healthBase = 25;    healthGrowth = 5;
			
			break;
		}
		
		int variance = maxLevel - minLevel;
		if (variance == 0)	{
			level = minLevel;
		}
		else	{
			level = Math.abs(rand.nextInt()) % variance + minLevel;
		}
		attack.setBase((int) (attackBase + (attackGrowth * level)));
		defense.setBase((int) (defenseBase + (defenseGrowth * level)));
		magic.setBase((int) (magicBase + (magicGrowth * level)));
		magDefense.setBase((int) (magDefenseBase + (magDefenseGrowth * level)));
		
		maximumHealth.setBase((int) (healthBase + (healthGrowth * level)));
		
	}
	
	public String getName()	{
		return name;
	}
	public ImageIcon getPicture()	{
		return picture;
	}
	public int getLevel()	{
		return level;
	}
	public int getAttack()	{
		return attack.getActual();
	}
	public int getDefense()	{
		return defense.getActual();
	}
	public int getMagic()	{
		return magic.getActual();
	}
	public int getMagDefense()	{
		return magDefense.getActual();
	}
	public int getCrit()	{
		return crit.getActual();
	}
	public int getSpeed()	{
		return speed.getActual();
	}
	public int getXP()	{
		return xpPerLevel * level;
	}
	public int getCurrentHealth()	{
		return currentHealth.getActual();
	}
	public int getMaximumHealth()	{
		return maximumHealth.getActual();
	}
	public int getCurrentMana()	{
		return currentMana.getActual();
	}
	public int getMaximumMana()	{
		return maximumMana.getActual();
	}
	public int getWidth()	{
		return width;
	}
	public int getHeight()	{
		return height;
	}
	public void setOrigin(Point origin)	{
		this.origin = origin;
	}
	public Point getOrigin()	{
		return origin;
	}
}
