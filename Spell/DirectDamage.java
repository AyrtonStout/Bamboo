package Spell;

import Systems.Combatant;
import Systems.Encounter;
import Systems.PartyMember;

public class DirectDamage implements SpellModule {

	private int baseDamage;
	private int spellCoefficient;
	private SPELL_TARGETING targeting;
	
	public DirectDamage(int baseDamage, int spellCoefficient, SPELL_TARGETING targeting)	{
		this.baseDamage = baseDamage;
		this.spellCoefficient = spellCoefficient;
		this.targeting = targeting;
	}
	
	public int getRawDamageDealt(Combatant caster)	{
		return baseDamage + caster.getSpellPower().getActual() * spellCoefficient;
	}
	
	@Override
	public void performAction(Combatant caster, Combatant target, PartyMember[] party, 
			Encounter enemies, boolean targetOther, boolean AoE) {

	}

}
