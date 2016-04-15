package Spell;

import Systems.Combatant;

import javax.swing.*;

public class Spell {

	private String name;
	private ImageIcon icon;
	private int manaCost;
	private int priority;
	private SpellModule[] effects;
	private String description;
	private ANIMATION_TYPE type;
	private boolean harmful;

	/**
	 * Creates a spell with the specified values. The first module that is passed into the spell determines a lot of the overall
	 * spell behavior. For example, if the first module is a damaging module, the spell is considered harmful. This also prevents
	 * the spell from being used on anybody that could not be harmed by it. Similarly, a healing spell as the first module marks
	 * the spell as helpful, and it would be unusable if the target was already at full HP, even if the spell also had a damaging
	 * component attached to it.
	 *
	 * @param name        The actual name of the spell
	 * @param manaCost    The mana required to cast the spell
	 * @param description The description of the spell that is seen in the battle and spell screens
	 * @param type        The archetype that the spell falls into. Example being a fireball or a heal. This determines what the spell
	 *                    icon is as well as the animation
	 * @param modules     All of the different effects that a spell has. Any number greater than 1 is acceptable and effects will be
	 *                    executed in the order they're passed in. Whether or not the spell is considered harmful depends on the type of the first module
	 *                    as well as whether or not it can even be executed.
	 * @Param priority The turn priority that the move takes in order to cast
	 */
	public Spell(String name, int manaCost, int priority, String description, ANIMATION_TYPE type, SpellModule... modules) {
		this.name = name;
		icon = new ImageIcon("GUI/Resources/Icons/Spells/" + type + ".png");
		this.type = type;
		this.manaCost = manaCost;
		this.priority = priority;
		this.description = description;
		effects = modules;

		if (modules[0].getClass() == DirectDamage.class) {
			harmful = true;
		} else if (modules[0].getClass() == DirectHeal.class) {
			harmful = false;
		}
	}

	public String getName() {
		return name;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getPriority() {
		return priority;
	}

	public SpellModule[] getModules() {
		return effects;
	}

	public String getDescription() {
		return description;
	}

	public boolean harmfulEh() {
		return harmful;
	}

	public SpellAnimation generateAnimation(Combatant caster, Combatant target) {
		if (type.dynamicEh()) {
			return new MovingAnimation(type, caster.getOrigin(), target.getOrigin());
		} else {
			return new StaticAnimation(type, target.getOrigin());
		}
	}
}

