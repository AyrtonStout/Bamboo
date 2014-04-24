package Spell;

import Systems.Combatant;
import Systems.Encounter;
import Systems.PartyMember;

public interface SpellModule {

	public void performAction(Combatant caster, Combatant target, PartyMember[] party, 
			Encounter enemies, boolean targetOther, boolean AoE);
	
}
