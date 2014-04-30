package Spell;

import javax.swing.ImageIcon;


public class Spell {
	
	private String name;
	private ImageIcon icon;
	private int manaCost;
	private int priority;
	private SpellModule[] effects;
	private String description;
	private Animation animation;
	private boolean harmful;
	
	/**
	 * Creates a spell with the specified values.
	 * 
	 * @param name The actual name of the spell
	 * @param manaCost The mana required to cast the spell
	 * @Param priority The turn priority that the move takes in order to cast
	 * @param description The description of the item that is seen in the battle and spell screens
	 * @param type The archetype that the spell falls into. Example being a fireball or a heal. This determines what the spell
	 * icon is as well as the animation
	 * @param modules All of the different effects that a spell has. Any number greater than 1 is acceptable and effects will be
	 * executed in the order they're passed in. Whether or not the spell is considered harmful depends on the type of the first module
	 */
	public Spell(String name, int manaCost, int priority, String description, ANIMATION_TYPE type, SpellModule ...modules)	{
		this.name = name;
		icon = new ImageIcon("GUI/Resources/Icons/Spells/" + type + ".png");
		this.manaCost = manaCost;
		this.priority = priority;
		this.description = description;
		effects = modules;
		
		if (modules[0].getClass() == DirectDamage.class)	{
			harmful = true;
		}
		else if (modules[0].getClass() == DirectHeal.class)	{
			harmful = false;
		}
	}
	
	public String getName()	{
		return name;
	}
	
	public ImageIcon getIcon()	{
		return icon;
	}
	
	public int getManaCost()	{
		return manaCost;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public SpellModule[] getModules()	{
		return effects;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean harmfulEh()	{
		return harmful;
	}

	

}

