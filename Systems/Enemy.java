package Systems;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

import Systems.Enums.COMBAT_ACTION;
import Systems.Enums.MONSTER;

public class Enemy implements Serializable, Combatant {
	
	private static final long serialVersionUID = 8656364136085883106L;
	
	private String name;
	private ImageIcon picture, battlePicture;
	private int width, height;            //Dimensions of the artwork for the enemy
	private int offsetX;                  //Used for animating attack
	private Point origin;                 //The point that the enemy is drawn to on the battle screen
	private boolean alive = true;
	private boolean justDied = false;
	
	private Stat attack = new Stat(0);
	private Stat defense = new Stat(0);
	private Stat magic = new Stat(0);
	private Stat magDefense = new Stat(0);
	private Stat speed = new Stat(0);
	private Stat hit = new Stat(0);
	private Stat critChance = new Stat(0);
	private Stat critDamage = new Stat(0);
	private Stat dodge = new Stat(0);
	private int level; 
	private int xp;
	
	private int turnPriority = 0;
	private int turnMaximum = 0;
	private int turnPrediction = 0;
	
	private Stat currentHealth = new Stat(1), maximumHealth = new Stat(1);
	private Stat currentMana = new Stat(0), maximumMana = new Stat(0);
	
	private COMBAT_ACTION combatAction = COMBAT_ACTION.IDLE;
	private int animationSteps = 0;
	private Combatant target;
	
	public Enemy (MONSTER type)	{
		Random rand = new Random();
		int minLevel = 0, maxLevel = 0;
		int healthBase = 0, healthGrowth = 0;
		double attackBase = 0, defenseBase = 0, magicBase = 0, magDefenseBase = 0;
		double attackGrowth = 0, defenseGrowth = 0, magicGrowth = 0, magDefenseGrowth = 0;
		double speedBase = 0;
		double speedGrowth = 0;
		int xpPerLevel = 0;
		
		switch (type)	{
		case GIANT_RAT:
			name = "Giant Rat";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			battlePicture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw-Small.png");
			width = 162; height = 151;
			xpPerLevel = 40;
			
			minLevel = 1;       maxLevel = 3;
			
			attackBase = 5.2;       attackGrowth = 1.1;
			defenseBase = 3.6;      defenseGrowth = 0.9;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 1.2;   magDefenseGrowth = 0.3;
			speedBase = 5; speedGrowth = 0.4;
			
			healthBase = 0;    healthGrowth = 5;
			
			break;
		case RAZORCLAW_CRAB:
			name = "Razorclaw Crab";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			battlePicture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw-Small.png");
			width = 162; height = 151;
			xpPerLevel = 45;
			
			minLevel = 2;       maxLevel = 4;
			
			attackBase = 2.4;       attackGrowth = 0.7;
			defenseBase = 3.8;      defenseGrowth = 1.7;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 2.2;   magDefenseGrowth = 0.7;
			speedBase = 3; speedGrowth = 0.3;
			
			healthBase = 5;    healthGrowth = 6;
			
			break;
		case DEATHSTALKER_CROW:
			name = "Deathstalker Crow";
			picture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw.png");
			battlePicture = new ImageIcon("GUI/Resources/Enemies/Crab_RazorClaw-Small.png");
			width = 162; height = 151;
			xpPerLevel = 45;
			
			minLevel = 3;       maxLevel = 3;
			
			attackBase = 3.2;       attackGrowth = 1.4;
			defenseBase = 2.6;      defenseGrowth = 0.7;
			magicBase = 0;          magicGrowth = 0;
			magDefenseBase = 1.6;   magDefenseGrowth = 0.4;
			speedBase = 9; speedGrowth = 0.5;
			
			healthBase = 5;    healthGrowth = 5;
			
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
		speed.setBase((int) (speedBase + (speedGrowth * level)));
		
		maximumHealth.setBase((int) (healthBase + (healthGrowth * level)));
		currentHealth.setBase(maximumHealth.getActual());
		
		xp = xpPerLevel * level;
	}

	public void attackTarget(Combatant target)	{
		combatAction = COMBAT_ACTION.ATTACK;
		animationSteps = 80;
		this.target = target;
	}
	
	public void update()	{
		switch (combatAction)	{
		case IDLE:
			break;
		case ATTACK:
			if (animationSteps == 75)	{
				animationSteps -= 1;
			}
			else if (animationSteps > 40)	{
				animationSteps -= 1;
			}
			else if (animationSteps == 40)	{
				combatAction = COMBAT_ACTION.IMPACT;
				animationSteps -= 1;
			}
			else if (animationSteps > 20)	{
				offsetX -= 6;
				animationSteps -= 4;
			}
			else if (animationSteps > 0)	{
				offsetX += 6;
				animationSteps -= 4;
			}
			else {
				combatAction = COMBAT_ACTION.IDLE;
			}
			break;
		case IMPACT:
			combatAction = COMBAT_ACTION.ATTACK;
		}
	}
	
	public void takeAction(PartyMember[] party)	{
		target = party[0];
		combatAction = COMBAT_ACTION.ATTACK;
		animationSteps = 100;
	}

	public String getName()	{
		return name;
	}
	public ImageIcon getPicture()	{
		return picture;
	}
	public ImageIcon getBattlePicture()	{
		return battlePicture;
	}
	public int getOffsetX()	{
		return offsetX;
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
	public Stat getSpeed()	{
		return speed;
	}
	public Stat getCritChance() {
		return critChance;
	}
	public Stat getCritDamage() {
		return critDamage;
	}
	public Stat getDodge() {
		return dodge;
	}
	public Stat getArmor() {
		return defense;
	}
	public int getXP()	{
		return xp;
	}
	public Stat getCurrentHealth()	{
		return currentHealth;
	}
	public int getMaximumHealth()	{
		return maximumHealth.getActual();
	}
	/**
	 * @return A double between 0 and 1
	 */
	public double getHealthPercentage()	{
		return (double) currentHealth.getActual() / maximumHealth.getActual();
	}
	public int getCurrentMana()	{
		return currentMana.getActual();
	}
	public int getMaximumMana()	{
		return maximumMana.getActual();
	}
	/**
	 * @return A number between 0 and 1
	 */
	public double getManaPercentage()	{
		return (double) currentMana.getActual() / maximumMana.getActual();
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
	public Stat getHit() {
		return hit;
	}
	public void modCurrentHealth(int health) {
		currentHealth.modifyBase(health);
	}
	@Override
	public boolean aliveEh()	{
		return alive;
	}
	@Override
	public boolean justDiedEh()	{
		return justDied;
	}
	@Override
	public void setAlive(boolean b)	{
		alive = b;
	}
	@Override
	public void setJustDied(boolean b)	{
		justDied = b;
	}
	@Override
	public int getTurnPriority()	{
		return turnPriority;
	}
	@Override
	public void setTurnPriority(int newPrio)	{
		turnPriority = newPrio;
	}
	@Override
	public int getTurnMaximum()	{
		return turnMaximum;
	}
	@Override
	public void setTurnMaximum(int newMax)	{
		turnMaximum = newMax;
	}
	@Override
	public int getTurnPrediction() {
		return turnPrediction;
	}
	@Override
	public void incrementTurnPrediction() {
		turnPrediction++;
	}
	@Override
	public void clearTurnPrediction() {
		turnPrediction = 0;
	}
	@Override
	public int getPredictiveSpeed()	{
		return turnPriority + turnPrediction * (turnMaximum/2);
	}
	@Override
	public Combatant getTarget()	{
		return target;
	}
	@Override
	public COMBAT_ACTION getCombatAction()	{
		return combatAction;
	}
}
