package Systems;

import java.util.Random;

/**
 * @author mobius
 * A class used to do combat calculations
 */
public class Combat {
	
	private GameData data;
	private Random rand = new Random();
	private boolean crit = false;
	
	public Combat(GameData data)	{
		this.data = data;
	}
	
	/**
	 * Used to do a basic attack calculation between two people
	 * 
	 * @param aggressor The person doing the attack
	 * @param victim The person being attacked
	 */
	public void attack(Combatant aggressor, Combatant victim)	{
		if (!missEh())	{
			
		}
		int painBrought = damageDealt(aggressor, victim);
		
		data.getBattleScreen().getBattleArea().addBattleText(painBrought, victim, crit);
		victim.modCurrentHealth(-painBrought);
		if (victim.getCurrentHealth().getActual() < 1)	{
			victim.setJustDied(true);
			victim.setAlive(false);
		}

		
		if (aggressor.getClass() == PartyMember.class)	{
			((PartyMember) aggressor).increaseDamageDealt(painBrought);
		}
	}
	
	/**
	 * Used for a basic combat calculation to tell whether or not the attack missed.
	 * 
	 * @return Whether or not the attack missed
	 */
	private boolean missEh()	{
		return false;
	}
	/**
	 * Used for a basic combat calculation to tell whether or not the attack was a critical strike
	 * 
	 * @return Whether or not the attack was a critical strike
	 */
	private boolean critEh(Combatant aggressor)	{
		int chance = rand.nextInt(100);
		if (chance < aggressor.getCritChance().getActual())	{
			crit = true;
			return true;
		}
		else	{
			crit = false;
			return false;
		}
	}
	/**
	 * Uses the other private classes to determine what the actual damage done is
	 * 
	 * @param aggressor The character doing the damage
	 * @param victim The character being attacked
	 * @return The amount of damage done
	 */
	private int damageDealt(Combatant aggressor, Combatant victim)	{
		if (critEh(aggressor))	{
			return ((aggressor.getAttack() - victim.getArmor().getActual()) * aggressor.getCritDamage().getActual()/100 < 1) ? 
					1 : (aggressor.getAttack() - victim.getArmor().getActual()) * aggressor.getCritDamage().getActual()/100;
		}
		else	{
			return (aggressor.getAttack() - victim.getArmor().getActual() < 1) ? 
					1 : aggressor.getAttack() - victim.getArmor().getActual();
		}
	}

}
