package Spell;

import Systems.Combatant;
import Systems.Encounter;
import Systems.PartyMember;

public class DirectHeal implements SpellModule {

	private int baseHeal;
	private int spellCoefficient;
	private SPELL_TARGETING targeting;
	
	public DirectHeal(int baseHeal, int spellCoefficient, SPELL_TARGETING targeting)	{
		this.baseHeal = baseHeal;
		this.spellCoefficient = spellCoefficient;
		this.targeting = targeting;
	}
	
	public int getHealingDone(Combatant caster)	{
		return baseHeal + caster.getSpellPower().getActual() * spellCoefficient;
	}
	
	@Override
	public void performAction(Combatant caster, Combatant target, PartyMember[] party, 
			Encounter enemies, boolean targetOther, boolean AoE) {

	}

}
