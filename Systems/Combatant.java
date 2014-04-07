package Systems;

import java.awt.Point;

import javax.swing.ImageIcon;

import Systems.Enums.COMBAT_ACTION;

/**
 * @author mobius
 * A participant in combat. Contains the images, stats, and dimensional information needed to carry out
 * all battle calculations as well as to draw themselves. 
 */
public abstract class Combatant {
	
	protected String name;
	protected ImageIcon picture, battlePicture;
	protected int width, height;            //Dimensions of the artwork for the enemy
	protected int offsetX;                  //Used for animating attack
	protected Point origin;                 //The point that the enemy is drawn to on the battle screen
	protected boolean alive = true;
	protected boolean justDied = false;
	
	protected Stat attackPower = new Stat(0);
	protected Stat spellPower = new Stat(0);
	protected Stat defense = new Stat(0);
	protected Stat resist = new Stat(0);
	protected Stat speed = new Stat(0);
	protected Stat hit = new Stat(0);
	protected Stat critChance = new Stat(75);
	protected Stat critDamage = new Stat(150);
	protected Stat dodge = new Stat(0);
	protected Stat armorPen = new Stat(0);
	protected int level; 
	
	protected int turnPriority = 0;
	protected int turnMaximum = 0;
	
	private int turnPrediction = 0;           /* This is the number of turns in advance that the BattleScreen is using to 
                                               * calculate turn order. When the BattleScreen isn't calculating this it is always 0 */
	private int turnPriorityPrediction = 0;   /* Changes the starting calculation point when deciding turn order
                                               * This is useful for moves that have a delay after they are used, or moves/actions
                                               * that are not intended to take up an entire full turn length */
	private int turnMaximumPrediction = 0;    /* This changes the ending point calculation (making all moves either faster or slower)
                                               * Useful for semi-permanent speed changes like Haste or Slow */
	
	protected Stat currentHealth = new Stat(1), maximumHealth = new Stat(1);
	protected Stat currentMana = new Stat(0), maximumMana = new Stat(0);
	
	protected COMBAT_ACTION combatAction = COMBAT_ACTION.IDLE;
	protected int animationSteps = 0;
	protected Combatant target;

	
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
	public abstract int getAttack();
	public Stat getAttackPower()	{
		return attackPower;
	}
	public void setAttackPower(Stat attackPower) {
		this.attackPower = attackPower;
	}
	public Stat getDefense()	{
		return defense;
	}
	public Stat getSpellPower()	{
		return spellPower;
	}
	public void setSpellPower(Stat spellPower) {
		this.spellPower = spellPower;
	}
	public void setHit(Stat hit) {
		this.hit = hit;
	}
	public Stat getResist()	{
		return resist;
	}
	public void setResist(Stat resist) {
		this.resist = resist;
	}
	public Stat getSpeed()	{
		return speed;
	}
	public void setSpeed(Stat speed)	{
		this.speed = speed;
	}
	/**
	 * Returns an integer representation of the combatant's crit chance. For example, for a crit chance of 
	 * of 5%, this will return the integer value 5.
	 * 
	 * @return The critical strike chance as an integer
	 */
	public Stat getCritChance() {
		return critChance;
	}
	/**
	 * Returns an integer representation of the combatant's crit damage multiplier. For example, for the normal crit
	 * multiplier of 150%, this will return the integer value 150. 
	 * 
	 * @return The critical damage multiplier as an integer
	 */
	public Stat getCritDamage() {
		return critDamage;
	}
	public void setCritChance(Stat critChance) {
		this.critChance = critChance;
	}
	public void setCritDamage(Stat critDamage) {
		this.critDamage = critDamage;
	}
	public Stat getDodge() {
		return dodge;
	}
	public void setDodge(Stat dodge) {
		this.dodge = dodge;
	}
	public Stat getArmor() {
		return defense;
	}
	public Stat getArmorPen() {
		return armorPen;
	}
	public void setArmorPen(Stat armorPen) {
		this.armorPen = armorPen;
	}
	public Stat getCurrentHealth()	{
		return currentHealth;
	}
	public Stat getMaxHealth()	{
		return maximumHealth;
	}
	/**
	 * @return A double between 0 and 1
	 */
	public double getHealthPercentage()	{
		return (double) currentHealth.getActual() / maximumHealth.getActual();
	}
	public Stat getCurrentMana()	{
		return currentMana;
	}
	public void setCurrentMana(Stat currentMana) {
		this.currentMana = currentMana;
	}
	public Stat getMaxMana()	{
		return maximumMana;
	}
	public void setMaxMana(Stat mana) {
		this.maximumMana = mana;
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
	public boolean aliveEh()	{
		return alive;
	}
	public boolean justDiedEh()	{
		return justDied;
	}
	public void setAlive(boolean b)	{
		alive = b;
	}
	public void setJustDied(boolean b)	{
		justDied = b;
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
	public int getTurnMaximumPrediction()	{
		return turnMaximumPrediction;
	}
	public void setTurnMaximum(int newMax)	{
		turnMaximum = newMax;
	}
	public int getTurnPrediction() {
		return turnPrediction;
	}
	/**
	 * This is used to calculate the turn order of the combatants in advance. This integer saves the prediction
	 * time by making it so it doesn't have to look through the turn order array every time it is calculating who
	 * will go next.
	 */
	public void incrementTurnPrediction() {
		turnPrediction++;
	}
	/**
	 * Used to reset turn order prediction to 0 after turn order calculations are made.
	 */
	public void clearTurnPrediction() {
		turnPrediction = 0;
	}
	public int getPredictiveSpeedMod()	{
		return turnPriorityPrediction + turnPrediction * (turnMaximumPrediction/2);
	}
	public int getPredictiveSpeedTrue()	{
		return turnPriority + turnPrediction * (turnMaximum/2);
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
	public Combatant getTarget()	{
		return target;
	}
	public void attackTarget(Combatant target)	{
		combatAction = COMBAT_ACTION.ATTACK;
		animationSteps = 80;
		this.target = target;
	}
	public COMBAT_ACTION getCombatAction()	{
		return combatAction;
	}
	public void setCombatState(COMBAT_ACTION action)	{
		combatAction = action;
	}
	
	@Override
	public String toString()	{
		return name;
	}
}
