package BattleScreen;

import Spell.SpellAnimation;
import Systems.Combatant;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BattleAnimations {

	private ArrayList<CharacterAnimation> characterAnimations = new ArrayList<CharacterAnimation>();
	private ArrayList<SpellAnimation> spellAnimations = new ArrayList<SpellAnimation>();

	public void addAnimation(Combatant combatant, CHARACTER_ANIMATION_TYPE type) {
		characterAnimations.add(new CharacterAnimation(combatant, type));
	}

	public void addAnimation(SpellAnimation animation) {
		spellAnimations.add(animation);
	}

	public void update() {
		for (int i = 0; i < characterAnimations.size(); i++) {
			characterAnimations.get(i).step();
		}
		for (int i = 0; i < spellAnimations.size(); i++) {
			spellAnimations.get(i).update();
		}
	}

	public void drawAnimations(Graphics g) {
		for (int i = 0; i < characterAnimations.size(); i++) {
			characterAnimations.get(i).step();
		}
		Iterator<SpellAnimation> iter = spellAnimations.iterator();
		while (iter.hasNext()) {
			SpellAnimation anim = iter.next();
			anim.drawAnimation(g);
			anim.update();
			if (anim.doneEh())
				iter.remove();
		}
	}

	private class CharacterAnimation {

		private Combatant combatant;
		private CHARACTER_ANIMATION_TYPE type;

		public CharacterAnimation(Combatant combatant, CHARACTER_ANIMATION_TYPE type) {
			this.combatant = combatant;
			this.type = type;
		}

		public void step() {

		}
	}
}
