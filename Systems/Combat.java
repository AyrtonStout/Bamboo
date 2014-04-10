package Systems;

import java.util.Random;

import Systems.Enums.CONSUMABLE_TYPE;

/**
 * @author mobius
 * A class used to do combat calculations
 */
public class Combat {

	private GameData data;
	private Random rand = new Random();
	private boolean crit = false;
	private final int BASE_HIT_CHANCE = 85;

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
		if (missEh(aggressor, victim))	{
			data.getBattleScreen().getBattleArea().addBattleText("MISS", victim, false);
		}
		else	{
			int painBrought = damageDealt(aggressor, victim);

			data.getBattleScreen().getBattleArea().addBattleText(Integer.toString(painBrought), victim, crit);
			victim.modCurrentHealth(-painBrought);
			if (victim.getCurrentHealth().getActual() < 1)	{
				victim.setJustDied(true);
				victim.setAlive(false);
			}


			if (aggressor.getClass() == PartyMember.class)	{
				((PartyMember) aggressor).increaseDamageDealt(painBrought);
			}
		}
	}

	public void heal(Combatant caster, Combatant receiver)	{

	}

	public void heal(Combatant caster, Combatant receiver, Consumable usedItem)	{
		if (usedItem.getHealthRestore() > 0)	{
			if (caster.getClass() == PartyMember.class)	{
				((PartyMember) caster).increaseHealingDone(usedItem.getHealthRestore());
			}

			receiver.modCurrentHealth(usedItem.getHealthRestore());
			
			if (receiver.getCurrentHealth().getBase() > receiver.getMaxHealth().getActual())	{
				receiver.getCurrentHealth().setBase(receiver.getMaxHealth().getActual());
			}
		}
		
		if (usedItem.getManaRestore() > 0)	{
			
			receiver.modCurrentMana(usedItem.getManaRestore());
		
			if (receiver.getCurrentHealth().getBase() > receiver.getMaxHealth().getActual())	{
				receiver.getCurrentHealth().setBase(receiver.getMaxHealth().getActual());
			}
		}
	}

	public void useItem(Combatant user, Combatant target, Consumable usedItem)	{
		if (usedItem.getType() == CONSUMABLE_TYPE.POTION)	{
			heal(user, target, usedItem);
		}
	}

	/**
	 * Used for a basic combat calculation to tell whether or not the attack missed.
	 * 
	 * @return Whether or not the attack missed
	 */
	private boolean missEh(Combatant aggressor, Combatant victim)	{
		int chance = rand.nextInt(100);
		if (chance < BASE_HIT_CHANCE + aggressor.getHit().getActual() - victim.getDodge().getActual())	{
			return false;
		}
		return true;
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
			return ((aggressor.getAttack() - victim.getArmor().getActual()) * aggressor.getCritDamage().getActual()/100 < 2) ? 
					2 : (int) Math.round((aggressor.getAttack() - victim.getArmor().getActual()) * aggressor.getCritDamage().getActual()/100.0);
		}
		else	{
			return (aggressor.getAttack() - victim.getArmor().getActual() < 1) ? 
					1 : aggressor.getAttack() - victim.getArmor().getActual();
		}
	}

}
