package Systems;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.ImageIcon;

import GUI.Enums.NAMED_NPC;
import Systems.Enums.COMBAT_ACTION;

public class PartyMember implements Serializable, Combatant {

	private static final long serialVersionUID = -2547966350818847472L;
	
	private static int partyKills;
	private static int partyDeaths;
	private static int goldFound;
	private static int maxGold;
	private static int huntsDone;
	private static int stepsTaken;
	private static int chestsFound;
	private static int signsRead;
	private static int daysDayed;
	private static int partyDamage;
	private static int partyHealing;
	private static int partySize = 1;
	
	private Equipment equipment = new Equipment(this);
	private GameData data;
	
	private String name;
	private String gender;
	private int level = 1;

	private int xpThisLevel;
	private int xpTotalEarned;
	private int xpRequirement = 200;
	
	private Stat currentHealth = new Stat(1), maximumHealth = new Stat(1);
	private Stat currentMana = new Stat(1), maximumMana = new Stat(1);
	
	private Stat strength, agility, intellect, spirit, luck, stamina;
	//Partial stats are used primarily for leveling up. Once a value exceeds 10 it will increase the main stat by 1
	private int partialStr, partialAgi, partialInt, partialSpi, partialLuck, partialSta;
	
	private Stat attackPower = new Stat(0);
	private Stat spellPower = new Stat(0);
	private Stat critChance = new Stat(5);
	private Stat critDamage = new Stat(150);
	private Stat hit = new Stat(0);
	private Stat armorPen = new Stat(0);
	private Stat dodge = new Stat(5);
	private Stat resist = new Stat(0);
	private Stat speed = new Stat(0);
	
	private int STAMINA_TO_HEALTH_RATIO = 10;
	
	private COMBAT_ACTION combatAction = COMBAT_ACTION.IDLE;
	private int animationSteps;
	private Combatant target;
	private Point origin;
	private int width = 32;
	private int height = 48;
	private int offsetX;
	
	private int turnPriority = 0;
	private int turnMaximum = 0;
	
	private int turnPrediction = 0;           /* This is the number of turns in advance that the BattleScreen is using to 
	                                           * calculate turn order. When the BattleScreen isn't calculating this it is always 0 */
	private int turnPriorityPrediction = 0;   /* Changes the starting calculation point when deciding turn order
	                                           * This is useful for moves that have a delay after they are used, or moves/actions
	                                           * that are not intended to take up an entire full turn length */
	private int turnMaximumPrediction = 0;    /* This changes the ending point calculation (making all moves either faster or slower)
	                                           * Useful for semi-permanent speed changes like Haste or Slow */
	
	private boolean alive = true;
	private boolean justDied = false;
	
	private ImageIcon current;
	private ImageIcon portrait;
	private ImageIcon left, up, right, down;
	private ImageIcon walkLeft, walkUp, walkRight, walkDown;
	private ImageIcon actionIMG, leap, death;
	
	private int kills;
	private int deaths;
	private int damageDealt;
	private int healingDone;
	private int highestCrit;
	
	
	@SuppressWarnings("incomplete-switch")
	public PartyMember(NAMED_NPC member)	{
		
		switch (member)	{
		case SABIN:
			name = "Sabin";
			gender = "Male";

			strength = new Stat(8);
			agility = new Stat(4);
			spirit = new Stat(5);
			intellect = new Stat(4);
			luck = new Stat(7);
			stamina = new Stat(8);
			
			speed = new Stat(9);
			
			partialStr = 3;
			partialAgi = 6;
			partialInt = 4;
			partialSpi = 0;
			partialLuck = 5;
			partialSta = 5;
			
			maximumMana = new Stat(100);
			break;
			
		case TERRA:
			name = "Terra";
			gender = "Female";
			
			strength = new Stat(4);
			agility = new Stat(5);
			spirit = new Stat(6);
			intellect = new Stat(8);
			luck = new Stat(8);
			stamina = new Stat(7);
			
			speed = new Stat(8);
			
			partialStr = 6;
			partialAgi = 6;
			partialInt = 9;
			partialSpi = 2;
			partialLuck = 7;
			partialSta = 1;
			
			maximumMana = new Stat(150);
			break;
		}
		portrait = new ImageIcon("GUI/Resources/Characters/" + name + " - Portrait.gif");
		left = new ImageIcon("GUI/Resources/Characters/" + name + " (Left).gif");
		up = new ImageIcon("GUI/Resources/Characters/" + name + " (Up).gif");
		right = new ImageIcon("GUI/Resources/Characters/" + name + " (Right).gif");
		down = new ImageIcon("GUI/Resources/Characters/" + name + " (Down).gif");
		
		walkLeft = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Left).gif");
		walkUp = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Up).gif");
		walkRight = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Right).gif");
		walkDown = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Down).gif");
		
		actionIMG = new ImageIcon("GUI/Resources/Characters/" + name + " - Action.gif");
		leap = new ImageIcon("GUI/Resources/Characters/" + name + " - Leap.gif");
//		battlePicture = new ImageIcon("GUI/Resources/Characters/" + name + " - Battle.png");
		death = new ImageIcon("GUI/Resources/Characters/" + name + " - Dead.gif");
		
		refresh();
	}
	
	public boolean levelUpEh() {
		if (xpThisLevel >= xpRequirement)	{
			return true;
		}
		return false;
	}
	
	public void levelUp()	{
		xpThisLevel -= xpRequirement;
		if (xpThisLevel < 0)	{
			xpThisLevel = 0;
		}
		if (level <= 50)	{
			xpRequirement = (int) (7 * Math.pow((double) level, 2.0) + 45 * level + 200);	
		}
		else if (level <= 97)	{
			xpRequirement = (int) (7 * Math.pow((double) level, 2.0) + 45 * level + 200);
		}
		else	{
			
		}
		level++;
		
		partialStr += 15;
		partialAgi += 15;
		partialInt += 15;
		partialSpi += 15;
		partialLuck += 2;
		partialSta += 20;
		
		if (gender.equals("Male"))	{
			partialStr += 3;
			partialAgi += 2;
			partialInt += 1;
			partialSpi += 2;
			partialLuck += 0;
			partialSta += 3;
		}
		else if (gender.equals("Female"))	{
			partialStr += 1;
			partialAgi += 2;
			partialInt += 3;
			partialSpi += 2;
			partialLuck += 1;
			partialSta += 2;
		}
		
		updateStats();
		
	}
	
	public void updateStats()	{
		while (partialStr >= 10)	{
			strength.modifyBase(1);
			partialStr -= 10;
		}
		while (partialAgi >= 10)	{
			agility.modifyBase(1);
			partialAgi -= 10;
		}
		while (partialInt >= 10)	{
			intellect.modifyBase(1);
			partialInt -= 10;
		}
		while (partialSpi >= 10)	{
			spirit.modifyBase(1);
			partialSpi -= 10;
		}
		while (partialLuck >= 10)	{
			luck.modifyBase(1);
			partialLuck -= 10;
		}
		while (partialSta >= 10)	{
			stamina.modifyBase(1);
			partialSta -= 10;
		}
		
		refresh();
	}
	
	public void initialize(GameData data)	{
		
		this.data = data;
		
		left = new ImageIcon("GUI/Resources/Characters/" + name + " (Left).gif");
		up= new ImageIcon("GUI/Resources/Characters/" + name + " (Up).gif");
		right= new ImageIcon("GUI/Resources/Characters/" + name + " (Right).gif");
		down= new ImageIcon("GUI/Resources/Characters/" + name + " (Down).gif");

		
		walkLeft = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Left).gif");
		walkUp = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Up).gif");
		walkRight = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Right).gif");
		walkDown = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Down).gif");
	}
	
	public void update()	{
		switch (combatAction)	{
		case IDLE:
			break;
		case ATTACK:
			if (animationSteps == 75)	{
				current = actionIMG;
				animationSteps -= 1;
			}
			else if (animationSteps > 40)	{
				animationSteps -= 1;
			}
			else if (animationSteps == 40)	{
				current = leap;
				animationSteps -= 1;
				combatAction = COMBAT_ACTION.IMPACT;
			}
			else if (animationSteps > 20)	{
				offsetX += 4;
				animationSteps -= 4;
			}
			else if (animationSteps > 0)	{
				offsetX -= 4;
				animationSteps -= 4;
			}
			else {
				current = right;
				combatAction = COMBAT_ACTION.IDLE;
			}
			break;
		case IMPACT:
			combatAction = COMBAT_ACTION.ATTACK;
		}	
	}
	
	//--------------------------------------
	public ImageIcon getPortrait()	{
		return portrait;
	}
	
	public ImageIcon getBattlePicture()	{
		return actionIMG;
	}
	
	public ImageIcon getLeft()	{
		return left;
	}
	
	public ImageIcon getUp()	{
		return up;
	}
	/**
	 * @return The downward facing sprite for the character
	 */
	public ImageIcon getDown()	{
		return down;
	}
	
	public ImageIcon getRight()	{
		return right;
	}
	
	public ImageIcon getWalkLeft()	{
		return walkLeft;
	}
	
	public ImageIcon getWalkUp()	{
		return walkUp;
	}
	
	public ImageIcon getWalkRight()	{
		return walkRight;
	}
	
	public ImageIcon getWalkDown()	{
		return walkDown;
	}
	
	public ImageIcon getActionImage()	{
		return actionIMG;
	}
	public ImageIcon getLeapImage()	{
		return leap;
	}
	public ImageIcon getCurrent()	{
		return current;
	}
	public void setCurrent(ImageIcon icon)	{
		current = icon;
	}
	public Point getOrigin()	{
		return origin;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXpThisLevel() {
		return xpThisLevel;
	}

	public void setXpThisLevel(int xp) {
		this.xpThisLevel = xp;
	}

	public int getXpTotalEarned() {
		return xpTotalEarned;
	}

	public void setXpTotalEarned(int xp) {
		this.xpTotalEarned = xp;
	}

	public int getXpRequirement() {
		return xpRequirement;
	}

	public void setXpRequirement(int xpToLevel) {
		this.xpRequirement = xpToLevel;
	}
	
	public void giveXP(int xp)	{
		xpThisLevel += xp;
		xpTotalEarned += xp;
	}

	public Stat getMaxHealth() {
		return maximumHealth;
	}

	public void setMaximumHealth(Stat health) {
		this.maximumHealth = health;
	}

	public Stat getCurrentHealth() {
		return currentHealth;
	}

	public void modCurrentHealth(int health) {
		currentHealth.modifyBase(health);
	}

	public double getHealthPercentage()	{
		return (double) currentHealth.getActual() / maximumHealth.getActual();
	}
	
	public Stat getMaxMana() {
		return maximumMana;
	}

	public void setMaxMana(Stat mana) {
		this.maximumMana = mana;
	}

	public Stat getCurrentMana() {
		return currentMana;
	}

	public void setCurrentMana(Stat currentMana) {
		this.currentMana = currentMana;
	}
	
	public double getManaPercentage()	{
		return (double) currentMana.getActual() / maximumMana.getActual();
	}
	
	public Stat getStrength()	{
		return strength;
	}
	
	public void setStrength(Stat strength) {
		this.strength = strength;
	}

	public Stat getAgility() {
		return agility;
	}

	public void setAgility(Stat agility) {
		this.agility = agility;
	}

	public Stat getIntellect() {
		return intellect;
	}

	public void setIntellect(Stat intellect) {
		this.intellect = intellect;
	}

	public Stat getSpirit() {
		return spirit;
	}

	public void setSpirit(Stat spirit) {
		this.spirit = spirit;
	}
	
	public Stat getLuck()	{
		return luck;
	}
	
	public void setLuck(Stat luck)	{
		this.luck = luck;
	}

	public Stat getStamina() {
		return stamina;
	}

	public void setStamina(Stat stamina) {
		this.stamina = stamina;
	}

	public Stat getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(Stat attackPower) {
		this.attackPower = attackPower;
	}

	public Stat getSpellPower() {
		return spellPower;
	}

	public void setSpellPower(Stat spellPower) {
		this.spellPower = spellPower;
	}

	public Stat getCritChance() {
		return critChance;
	}

	public void setCritChance(Stat critChance) {
		this.critChance = critChance;
	}

	public Stat getCritDamage() {
		return critDamage;
	}

	public void setCritDamage(Stat critDamage) {
		this.critDamage = critDamage;
	}

	public Stat getHit() {
		return hit;
	}

	public void setHit(Stat hit) {
		this.hit = hit;
	}

	public Stat getArmorPen() {
		return armorPen;
	}

	public void setArmorPen(Stat armorPen) {
		this.armorPen = armorPen;
	}
	
	public void setSpeed(Stat speed)	{
		this.speed = speed;
	}
	
	public Stat getSpeed()	{
		return speed;
	}
	
	public void setArmor()	{
		//TODO THIS^
	}
	
	public Stat getArmor()	{
		//TODO THIS^v
		return new Stat(0);
	}

	public Stat getDodge() {
		return dodge;
	}

	public void setDodge(Stat dodge) {
		this.dodge = dodge;
	}

	public Stat getResist() {
		return resist;
	}

	public void setResist(Stat resist) {
		this.resist = resist;
	}
	
	public int getWidth()	{
		return width;
	}
	public int getHeight()	{
		return height;
	}
	
	
	public int getTurnPriority()	{
		return turnPriority;
	}
	public int getTurnPriorityPrediction()	{
		return turnPriorityPrediction;
	}
	public void setTurnPriority(int newPrio)	{
		turnPriority = newPrio;
	}	
	public int getTurnMaximum()	{
		return turnMaximum;
	}
	public void setTurnMaximum(int newMax)	{
		turnMaximum = newMax;
	}
	public int getTurnMaximumPrediction()	{
		return turnMaximumPrediction;
	}
	public int getTurnPrediction()	{
		return turnPrediction;
	}
	@Override
	public void incrementTurnPrediction()	{
		turnPrediction++;
	}
	@Override
	public void clearTurnPrediction()	{
		turnPrediction = 0;
	}
	@Override
	public int getPredictiveSpeedTrue()	{
		return turnPriority + turnPrediction * (turnMaximum/2);
	}
	@Override
	public int getPredictiveSpeedMod()	{
		return turnPriorityPrediction + turnPrediction * (turnMaximumPrediction/2);
	}
	public void clearTurnPriorityPrediction()	{
		turnPriorityPrediction = 0;
	}
	public void clearTurnMaximumPrediction()	{
		turnMaximumPrediction = 0;
	}
	public void setTurnPriorityPrediction(int i)	{
		if (i == 0)	{
			turnPriorityPrediction = turnMaximumPrediction / 4;
		}
		else if (i == 1)	{
			turnPriorityPrediction = turnMaximumPrediction / 2;
		}
		else if (i == 2)	{
			turnPriorityPrediction = turnMaximumPrediction;
		}
		else	{
			turnPriorityPrediction = turnPriority;
		}
	}
	public void setTurnMaximumPrediction(int i)	{
		if (i == 0)	{
			turnMaximumPrediction /= 2;
		}
		else if (i == 1)	{
			turnMaximumPrediction = turnMaximum;
		}
		else if (i == 2)	{
			turnMaximumPrediction *= 2;
		}
	}
	
	
	//-------------------------------------------
	public void incrementKills()	{
		kills++;
		partyKills++;
	}
	
	public int getKills()	{
		return kills;
	}
	
	public void incrementDeaths()	{
		deaths++;
		partyDeaths++;
	}
	
	public int getDeaths()	{
		return deaths;
	}
	
	public void increaseDamageDealt(int damage)	{
		damageDealt += damage;
		partyDamage += damage;
	}
	
	public int getDamagePercentage()	{
		return (int) Math.round(((double) damageDealt / partyDamage) * 100);
	}
	
	public void increaseHealingDone(int healing)	{
		healingDone += healing;
		partyHealing += healing;
	}
	
	public int getHealingPercentage()	{
		return (int) Math.round(((double) healingDone / partyHealing) * 100);
	}
	
	public void setHighestCrit(int crit)	{
		highestCrit = crit;
	}
	
	public int getHighestCrit()	{
		return highestCrit;
	}
	
	
	//-------------------------------------------
	public static int getPartyKills() {
		return partyKills;
	}

	public static int getPartyDeaths() {
		return partyDeaths;
	}

	public static int getGoldFound() {
		return goldFound;
	}

	public static void increaseGoldFound(int goldFound) {
		PartyMember.goldFound += goldFound;
	}

	public static int getMaxGold() {
		return maxGold;
	}

	public static void setMaxGold(int maxGold) {
		PartyMember.maxGold = maxGold;
	}

	public static int getHuntsDone() {
		return huntsDone;
	}

	public static void incrementHuntsDone() {
		PartyMember.huntsDone++;
	}

	public static int getStepsTaken() {
		return stepsTaken;
	}

	public static void incrementStepsTaken() {
		PartyMember.stepsTaken++;
	}

	public static int getChestsFound() {
		return chestsFound;
	}

	public static void incrementChestsFound() {
		PartyMember.chestsFound++;
	}

	public static int getSignsRead() {
		return signsRead;
	}

	public static void incrementSignsRead() {
		PartyMember.signsRead++;
	}

	public static int getDaysDayed() {
		return daysDayed;
	}

	public static void incrementDaysDayed() {
		PartyMember.daysDayed++;
	}
	
	public static int getPartySize()	{
		return PartyMember.partySize;
	}
	
	public static void incrementPartySize()	{
		PartyMember.partySize++;
	}
	
	public static void decrementPartySize()	{
		PartyMember.partySize--;
	}
	
	@Override
	public String toString()	{
		return name;
	}
	public Equipment getEquipment()	{
		return equipment;
	}
	public void setEquipment(Equipment equipment)	{
		this.equipment = equipment;
	}

	/**
	 * Recalculates derivative stats (example, health amount being partially calculated based on stamina).
	 */
	public void refresh() {
		attackPower.setBase(strength.getActual());
		maximumHealth = new Stat(stamina.getActual() * STAMINA_TO_HEALTH_RATIO);
		currentHealth.setBase(maximumHealth.getBase());
		currentMana.setBase(maximumMana.getBase());
	}

	@Override
	public int getAttack() {
		return (strength.getActual());
	}
	public COMBAT_ACTION getAction()	{
		return combatAction;
	}
	/**
	 * Draws the character on the battle screen.
	 * 
	 * Uses different coordinates for the death image to correct for its different dimensions
	 */
	public void drawSelf(Graphics g)	{
		if (current == death)	{
			g.drawImage(current.getImage(), origin.x - 11, origin.y + 14, null);
		}
		else	{
			g.drawImage(current.getImage(), origin.x + offsetX, origin.y, null);	
		}
	}
	
	public void attackTarget(Combatant target)	{
		combatAction = COMBAT_ACTION.ATTACK;
		animationSteps = 80;
		this.target = target;
	}
	
	public void setCombatState(COMBAT_ACTION action)	{
		combatAction = action;
	}
	
	public void setOrigin(Point origin)	{
		this.origin = origin;
	}

	@Override
	public boolean aliveEh() {
		return alive;
	}

	@Override
	public boolean justDiedEh() {
		return justDied;
	}

	@Override
	public void setAlive(boolean b) {
		alive = b;
		
		if (alive)	{
			current = right;
		}
		else	{
			if (combatAction == COMBAT_ACTION.IDLE)	{
				current = death;
			}
		}
		
	}

	@Override
	public void setJustDied(boolean b) {
		justDied = b;
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
