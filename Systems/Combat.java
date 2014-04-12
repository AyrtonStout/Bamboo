package Systems;

import java.util.Random;

import Battle.Enums.TEXT_TYPE;
import Systems.Enums.CONSUMABLE_TYPE;

/**
 * @author mobius
 * A class used to do combat calculations
 */
public class Combat {

	private GameData data;
	private Random rand = new Random();
	private TEXT_TYPE crit;
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
			data.getBattleScreen().getBattleArea().getCombatText().addBattleText("MISS", victim, TEXT_TYPE.MISS);
		}
		else	{
			int painBrought = damageDealt(aggressor, victim);

			data.getBattleScreen().getBattleArea().getCombatText().addBattleText(Integer.toString(painBrought), victim, crit);
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

	public void usePotion(Combatant caster, Combatant receiver, Consumable usedItem)	{
		if (usedItem.getHealthRestore() > 0)	{
			healTarget(caster, receiver, usedItem);
		}
		if (usedItem.getManaRestore() > 0)	{
			restoreMana(caster, receiver, usedItem);
		}
		addBattleText(receiver, usedItem);
	}

	/**
	 * Uses a specified item on a targeted character. If the item is successfully used it will be consumed and the method
	 * will return true. Else nothing will be executed and it will return false.
	 * 
	 * @param user The character who used the item.
	 * @param target The character targeted by the item
	 * @param usedItem The item being used on the target
	 * @return Whether or not the item was successfully used
	 */
	public boolean useItem(Combatant user, Combatant target, Consumable usedItem)	{
		if (usedItem.getConsumableType() == CONSUMABLE_TYPE.POTION)	{
			if ((usedItem.getHealthRestore() > 0 && validHealTargetEh(target)) || 
					(usedItem.getManaRestore() > 0 && validManaRestoreTargetEh(target)))	{
				if (user.getClass() == PartyMember.class)	{
					((PartyMember) user).startItemAnimation();
				}
				usePotion(user, target, usedItem);
				data.getInventory().removeItem(usedItem);
				return true;
			}
		}
		return false;
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
			crit = TEXT_TYPE.CRIT;
			return true;
		}
		else	{
			crit = TEXT_TYPE.DAMAGE;
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
	
	private boolean validHealTargetEh(Combatant target)	{
		if (target.getCurrentHealth().getActual() == target.getMaxHealth().getActual())	{
			data.getBattleScreen().getBattleInfo().setText(target.getName() + " is already at full health");
			return false;
		}
		if (target.aliveEh() == false)	{
			data.getBattleScreen().getBattleInfo().setText(target.getName() + " is too dead to drink potions");
			return false;
		}
		return true;
	}
	
	private boolean validManaRestoreTargetEh(Combatant target)	{
		if (target.aliveEh() == false)	{
			data.getBattleScreen().getBattleInfo().setText(target.getName() + " is too dead to drink potions");
			return false;
		}
		if (target.getCurrentMana().getActual() == target.getMaxMana().getActual())	{
			data.getBattleScreen().getBattleInfo().setText(target.getName() + " is already at full mana");
			return false;
		}
		return true;
	}
	
	private void healTarget(Combatant caster, Combatant receiver, Consumable usedItem)	{
		if (caster.getClass() == PartyMember.class)	{
			((PartyMember) caster).increaseHealingDone(usedItem.getHealthRestore());
		}

		receiver.modCurrentHealth(usedItem.getHealthRestore());

		if (receiver.getCurrentHealth().getBase() > receiver.getMaxHealth().getActual())	{
			receiver.getCurrentHealth().setBase(receiver.getMaxHealth().getActual());
		}
	}
	
	private void restoreMana(Combatant caster, Combatant receiver, Consumable usedItem) {
		receiver.modCurrentMana(usedItem.getManaRestore());

		if (receiver.getCurrentMana().getBase() > receiver.getMaxMana().getActual())	{
			receiver.getCurrentMana().setBase(receiver.getMaxMana().getActual());
		}	
	}
	
	private void addBattleText(Combatant receiver, Consumable usedItem)	{
		final int POTION_ANIMATION_DELAY = 20;
		if (usedItem.getHealthRestore() > 0)	{
			data.getBattleScreen().getBattleArea().getCombatText().addDelayedBattleText(
					Integer.toString(usedItem.getHealthRestore()), receiver, TEXT_TYPE.HEAL, POTION_ANIMATION_DELAY);
		}
		else	{
			data.getBattleScreen().getBattleArea().getCombatText().addDelayedBattleText(
					Integer.toString(usedItem.getManaRestore()), receiver, TEXT_TYPE.MANA_RESTORE, POTION_ANIMATION_DELAY);
		}
	}

}
