package Systems;

import BattleScreen.Enums.COMBAT_ACTION;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * @author mobius
 *         A playable character in the player's party. Has specific overridden methods for certain combat
 *         characteristics, contains its own equipment inventory, and has extra pictures that are needed
 *         in order to make the character walk around on the map screen
 */
public class PartyMember extends Combatant implements Serializable {

	final static int STAMINA_TO_HEALTH_RATIO = 10;
	final static int INTELLECT_TO_MANA_RATIO = 15;
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
	private String gender;
	private int xpThisLevel;
	private int xpTotalEarned;
	private int xpRequirement = 200;
	private Stat strength, agility, intellect, spirit, luck, stamina, special;
	//Partial stats are used primarily for leveling up. Once a value exceeds 10 it will increase the main stat by 1
	private int partialStr, partialAgi, partialInt, partialSpi, partialLck, partialSta;
	private ImageIcon current;
	private ImageIcon portrait;
	private ImageIcon left, up, right, down;
	private ImageIcon walkLeft, walkUp, walkRight, walkDown;
	private ImageIcon leap, death;

	private int kills;
	private int deaths;
	private int damageDealt;
	private int healingDone;
	private int highestCrit;

	public PartyMember(PartyMemberEnum member) {

		name = member.getName();
		gender = member.getGender();
		strength = new Stat(member.getStrength());
		agility = new Stat(member.getAgility());
		intellect = new Stat(member.getIntellect());
		spirit = new Stat(member.getSpirit());
		luck = new Stat(member.getLuck());
		stamina = new Stat(member.getStamina());
		speed = new Stat(member.getSpeed());

		partialStr = member.getpStrength();
		partialAgi = member.getpAgility();
		partialInt = member.getpIntellect();
		partialSpi = member.getpSpirit();
		partialLck = member.getpLuck();
		partialSta = member.getpStamina();

		portrait = new ImageIcon("GUI/Resources/Characters/" + name + " - Portrait.gif");
		left = new ImageIcon("GUI/Resources/Characters/" + name + " (Left).gif");
		up = new ImageIcon("GUI/Resources/Characters/" + name + " (Up).gif");
		right = new ImageIcon("GUI/Resources/Characters/" + name + " (Right).gif");
		down = new ImageIcon("GUI/Resources/Characters/" + name + " (Down).gif");

		walkLeft = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Left).gif");
		walkUp = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Up).gif");
		walkRight = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Right).gif");
		walkDown = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Down).gif");

		battlePicture = new ImageIcon("GUI/Resources/Characters/" + name + " - Action.gif");
		leap = new ImageIcon("GUI/Resources/Characters/" + name + " - Leap.gif");
		death = new ImageIcon("GUI/Resources/Characters/" + name + " - Dead.gif");

		level = 1;
		width = 32;
		height = 48;

		refresh();
		currentHealth.setBase(maximumHealth.getBase());
		currentMana.setBase(maximumMana.getBase());
		special = new Stat(0);
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

	public static int getPartySize() {
		return PartyMember.partySize;
	}

	public static void incrementPartySize() {
		PartyMember.partySize++;
	}

	public static void decrementPartySize() {
		PartyMember.partySize--;
	}

	public boolean levelUpEh() {
		if (xpThisLevel >= xpRequirement) {
			return true;
		}
		return false;
	}

	public void levelUp() {
		xpThisLevel -= xpRequirement;
		if (xpThisLevel < 0) {
			xpThisLevel = 0;
		}
		if (level <= 50) {
			xpRequirement = (int) (7 * Math.pow((double) level, 2.0) + 45 * level + 200);
		} else if (level <= 97) {
			xpRequirement = (int) (7 * Math.pow((double) level, 2.0) + 45 * level + 200);
		} else {

		}
		level++;

		partialStr += 15;
		partialAgi += 15;
		partialInt += 15;
		partialSpi += 15;
		partialLck += 2;
		partialSta += 20;

		if (gender.equals("Male")) {
			partialStr += 3;
			partialAgi += 2;
			partialInt += 1;
			partialSpi += 2;
			partialLck += 0;
			partialSta += 3;
		} else if (gender.equals("Female")) {
			partialStr += 1;
			partialAgi += 2;
			partialInt += 3;
			partialSpi += 2;
			partialLck += 1;
			partialSta += 2;
		}

		updateStats();
	}

	public void updateStats() {
		while (partialStr >= 10) {
			strength.modifyBase(1);
			partialStr -= 10;
		}
		while (partialAgi >= 10) {
			agility.modifyBase(1);
			partialAgi -= 10;
		}
		while (partialInt >= 10) {
			intellect.modifyBase(1);
			partialInt -= 10;
		}
		while (partialSpi >= 10) {
			spirit.modifyBase(1);
			partialSpi -= 10;
		}
		while (partialLck >= 10) {
			luck.modifyBase(1);
			partialLck -= 10;
		}
		while (partialSta >= 10) {
			stamina.modifyBase(1);
			partialSta -= 10;
		}

		refresh();
		currentHealth.setBase(maximumHealth.getBase());
		currentMana.setBase(maximumMana.getBase());
	}

	public void initialize(GameData data) {

		left = new ImageIcon("GUI/Resources/Characters/" + name + " (Left).gif");
		up = new ImageIcon("GUI/Resources/Characters/" + name + " (Up).gif");
		right = new ImageIcon("GUI/Resources/Characters/" + name + " (Right).gif");
		down = new ImageIcon("GUI/Resources/Characters/" + name + " (Down).gif");

		walkLeft = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Left).gif");
		walkUp = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Up).gif");
		walkRight = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Right).gif");
		walkDown = new ImageIcon("GUI/Resources/Characters/" + name + " - Walk (Down).gif");
	}

	public void update() {
		switch (combatAction) {
			case IDLE:
				break;
			case ATTACK:
				if (animationSteps == 75) {
					current = battlePicture;
					animationSteps -= 1;
				} else if (animationSteps > 40) {
					animationSteps -= 1;
				} else if (animationSteps == 40) {
					current = leap;
					animationSteps -= 1;
					combatAction = COMBAT_ACTION.IMPACT;
				} else if (animationSteps > 20) {
					offsetX += 4;
					animationSteps -= 4;
				} else if (animationSteps > 0) {
					offsetX -= 4;
					animationSteps -= 4;
				} else {
					current = right;
					combatAction = COMBAT_ACTION.IDLE;
				}
				break;
			case ITEM:
				if (animationSteps > 35) {
					animationSteps--;
				} else if (animationSteps == 35) {
					current = leap;
					animationSteps--;
				} else if (animationSteps > 15) {
					animationSteps--;
				} else if (animationSteps == 15) {
					current = right;
					animationSteps--;
				} else if (animationSteps > 0) {
					animationSteps--;
				} else if (animationSteps == 0) {
					combatAction = COMBAT_ACTION.IDLE;
				}
				break;
			case IMPACT:
				combatAction = COMBAT_ACTION.ATTACK;
		}
	}

	//--------------------------------------
	public ImageIcon getPortrait() {
		return portrait;
	}

	public ImageIcon getLeft() {
		return left;
	}

	public ImageIcon getUp() {
		return up;
	}

	/**
	 * @return The downward facing sprite for the character
	 */
	public ImageIcon getDown() {
		return down;
	}

	public ImageIcon getRight() {
		return right;
	}

	public ImageIcon getWalkLeft() {
		return walkLeft;
	}

	public ImageIcon getWalkUp() {
		return walkUp;
	}

	public ImageIcon getWalkRight() {
		return walkRight;
	}

	public ImageIcon getWalkDown() {
		return walkDown;
	}

	public ImageIcon getActionImage() {
		return battlePicture;
	}

	public ImageIcon getLeapImage() {
		return leap;
	}

	@Override
	public ImageIcon getPicture() {
		return current;
	}

	public void setCurrent(ImageIcon icon) {
		current = icon;
	}

	public void setName(String name) {
		this.name = name;
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

	public void giveXP(int xp) {
		xpThisLevel += xp;
		xpTotalEarned += xp;
	}

	public Stat getStrength() {
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

	public Stat getLuck() {
		return luck;
	}

	public void setLuck(Stat luck) {
		this.luck = luck;
	}

	public Stat getStamina() {
		return stamina;
	}

	public void setStamina(Stat stamina) {
		this.stamina = stamina;
	}

	public Stat getSpecial() {
		return special;
	}

	public void setSpecial(Stat special) {
		this.special = special;
	}

	public Stat getAttackPower() {
		return attackPower;
	}

	public void setArmor() {
		//TODO THIS^
	}

	public Stat getArmor() {
		//TODO THIS^v
		return new Stat(0);
	}

	//-------------------------------------------
	public void incrementKills() {
		kills++;
		partyKills++;
	}

	public int getKills() {
		return kills;
	}

	public void incrementDeaths() {
		deaths++;
		partyDeaths++;
	}

	public int getDeaths() {
		return deaths;
	}

	public void increaseDamageDealt(int damage) {
		damageDealt += damage;
		partyDamage += damage;
	}

	public int getDamagePercentage() {
		return (int) Math.round(((double) damageDealt / partyDamage) * 100);
	}

	public void increaseHealingDone(int healing) {
		healingDone += healing;
		partyHealing += healing;
	}

	public int getHealingPercentage() {
		return (int) Math.round(((double) healingDone / partyHealing) * 100);
	}

	public int getHighestCrit() {
		return highestCrit;
	}

	public void setHighestCrit(int crit) {
		highestCrit = crit;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public void startItemAnimation() {
		combatAction = COMBAT_ACTION.ITEM;
		animationSteps = 55;
	}

	/**
	 * Recalculates derivative stats (example, health amount being partially calculated based on stamina).
	 */
	public void refresh() {
		attackPower.setBase(strength.getActual());
		if (equipment.getWeapon() != null) {
			attackPower.modifyBase(equipment.getWeapon().getAttack().getActual());
		}
		spellPower.setBase(intellect.getActual());
		maximumHealth = new Stat(stamina.getActual() * STAMINA_TO_HEALTH_RATIO);
		maximumMana = new Stat(intellect.getActual() * INTELLECT_TO_MANA_RATIO);
	}

	@Override
	public int getAttack() {
		return (attackPower.getActual());
	}

	/**
	 * Draws the character on the battle screen.
	 * <p>
	 * Uses different coordinates for the death image to correct for its different dimensions
	 */
	public void drawSelf(Graphics g) {
		if (!alive) {
			g.drawImage(death.getImage(), origin.x - 11, origin.y + 14, null);
		} else {
			g.drawImage(current.getImage(), origin.x + offsetX, origin.y, null);
		}
	}

	@Override
	public void setAlive(boolean b) {
		alive = b;

		if (alive) {
			current = right;
		} else {
			if (combatAction == COMBAT_ACTION.IDLE) {
				current = death;
			}
		}
	}
}
