package Systems;

import javax.swing.ImageIcon;

import GUI.Enums.NAMED_NPC;

public class PartyMember {
	
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
	
	private String name;
	private int level = 1;

	private int xp;
	private int xpToLevel;
	
	private Stat health;
	private Stat secondary;
	
	private Stat strength;
	private Stat agility;
	private Stat intellect;
	private Stat spirit;
	private Stat luck;
	private Stat stamina;
	
	private Stat attackPower = new Stat(0);
	private Stat spellPower = new Stat(0);
	private Stat critChance = new Stat(5);
	private Stat critDamage = new Stat(150);
	private Stat hit = new Stat(0);
	private Stat armorPen = new Stat(0);
	private Stat dodge = new Stat(5);
	private Stat resist = new Stat(0);
	private Stat speed = new Stat(0);
	
	private int STAMINA_TO_HEALTH_RATIO = 5;
	
	private ImageIcon portrait;
	private ImageIcon right;
	private ImageIcon down;
	
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
			portrait = new ImageIcon("GUI/Resources/Characters/" + name + " - Portrait.gif");
			right = new ImageIcon("GUI/Resources/Characters/" + name + " (Right).gif");
			down = new ImageIcon("GUI/Resources/Characters/" + name + " (Down).gif");
			
			strength = new Stat(8);
			agility = new Stat(4);
			spirit = new Stat(5);
			intellect = new Stat(4);
			luck = new Stat(7);
			
			stamina = new Stat(8);
			
			health = new Stat(stamina.getActual() * STAMINA_TO_HEALTH_RATIO);
			secondary = new Stat(100);
		}
		
	}
	
	public ImageIcon getPortrait()	{
		return portrait;
	}
	/**
	 * @return The downward facing sprite for the character
	 */
	public ImageIcon getDown()	{
		return down;
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

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getXpToLevel() {
		return xpToLevel;
	}

	public void setXpToLevel(int xpToLevel) {
		this.xpToLevel = xpToLevel;
	}

	public Stat getHealth() {
		return health;
	}

	public void setHealth(Stat health) {
		this.health = health;
	}

	public Stat getSecondary() {
		return secondary;
	}

	public void setSecondary(Stat secondary) {
		this.secondary = secondary;
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
		
}
