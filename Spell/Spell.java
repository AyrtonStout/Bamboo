package Spell;

import javax.swing.ImageIcon;


public class Spell {
	
	private String name;
	private ImageIcon icon;
	private int manaCost;
	private SpellModule[] effects;
	private String description;
	
	public Spell(String name, int manaCost, String description, SpellModule ...modules)	{
		this.name = name;
		icon = new ImageIcon();
		this.manaCost = manaCost;
		this.description = description;
		effects = modules;
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
	
	public SpellModule[] getModules()	{
		return effects;
	}

	public String getDescription() {
		return description;
	}

}

